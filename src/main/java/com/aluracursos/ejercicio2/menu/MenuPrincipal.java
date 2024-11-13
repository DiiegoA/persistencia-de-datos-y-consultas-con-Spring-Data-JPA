package com.aluracursos.ejercicio2.menu;

import com.aluracursos.ejercicio2.model.*;
import com.aluracursos.ejercicio2.repository.MusicRepository;
import com.aluracursos.ejercicio2.repository.CancionRepository;
import com.aluracursos.ejercicio2.service.ConsumoApi;
import com.aluracursos.ejercicio2.service.ConvierteDatos;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.aluracursos.logger.loggerbase.LoggerBase;
import com.aluracursos.logger.loggerbase.LoggerBaseImpl;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class MenuPrincipal {

    private static final String URL_BASE_1 = "https://musicbrainz.org/ws/2/artist/?query=artist:";
    private static final String FORMAT_JSON = "&fmt=json";
    private static final String URL_BASE_2 = "https://musicbrainz.org/ws/2/recording?query=artist:";
    private static final String URL_BASE_2_1 = "%20AND%20recording:";

    private final Scanner scanner = new Scanner(System.in);
    private final ConsumoApi consumoApi = new ConsumoApi();
    private final ConvierteDatos conversor = new ConvierteDatos();
    private final MusicRepository artistRepository;
    private final CancionRepository cancionRepository;
    private final List<Artista> artistas;
    private final List<Cancion> canciones;
    private final AtomicReference<String> json = new AtomicReference<>("");
    private final LoggerBase logger;

    private String nombre;

    public MenuPrincipal(){
        this.logger = new LoggerBaseImpl(MenuPrincipal .class.getName());
        this.artistRepository = null;
        this.cancionRepository = null;
        this.artistas = null;
        this.canciones = null;
    }

    // Constructor para inyectar los repositorios
    // Constructor para inyectar los repositorios
    public MenuPrincipal(MusicRepository artistRepository, CancionRepository cancionRepository) {
        this.artistRepository = artistRepository;
        this.cancionRepository = cancionRepository;
        this.artistas = new ArrayList<>();
        this.canciones = new ArrayList<>();
        this.logger = new LoggerBaseImpl(MenuPrincipal.class.getName()); // Inicializa el logger
    }


    // Método para mostrar el menú principal
    public void muestraElMenu() {
        int opcion;
        do {
            logger.logInfo(String.format("""
                        %nEscribe una opción:
                        1 - Buscar Artista
                        2 - Buscar Canción
                        3 - Mostrar Artistas Buscados
                        4 - Buscar Artista Por Nombre
                        5 - Top 5 De Mejores Artistas
                        6 - Buscar Artistas Por Género Musical
                        7 - Buscar Canciones Por Nombre
                        0 - Salir
                    """));
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1 -> buscarArtista();
                case 2 -> buscarCancionPorArtista();
                case 3 -> mostrarArtistasBuscados();
                case 4 -> buscarArtistaPorNombre();
                case 5 -> buscarTop5Artistas();
                case 6 -> buscarArtistasPorGenero();
                case 7 -> buscarCancionesPorNombre();
                case 0 -> logger.logInfo("Cerrando la Aplicación");
                default -> logger.logInfo("Opción Inválida");
            }
        } while (opcion != 0);
    }

    // Método para buscar y guardar un artista
    private void buscarArtista() {
        try {
            DatosArtista datosArtista = getDatosArtista();
            Artista artista = new Artista(datosArtista);
            artistRepository.save(artista);
            logger.logInfo("Artista agregado con éxito: " + datosArtista);
        } catch (DataIntegrityViolationException e) {
            logger.logInfo("Error: " + e.getMessage());
        }
    }

    // Método para buscar y asociar una canción con un artista
    private void buscarCancionPorArtista() {
        DatosArtista datosArtista = getDatosArtista();
        if (datosArtista != null) {
            Artista artista = saveOrRetrieveArtist(datosArtista); // Garantiza que el artista tenga un ID asignado

            DatosCancion datosCancion = getDatosCancion(datosArtista.nombreArtista()); // Pasa el nombre del artista como parámetro
            if (datosCancion != null) {
                Cancion cancion = new Cancion(datosCancion).asociarConArtista(artista);
                cancionRepository.save(cancion); // Guarda la canción con el artista asociado
                logger.logInfo("Canción guardada y asociada al artista: " + cancion);
            } else {
                logger.logInfo("No se encontró la canción especificada.");
            }
        } else {
            logger.logInfo("No se encontró información sobre el artista.");
        }
    }

    // Método auxiliar para obtener los datos de un artista desde la API
    private DatosArtista getDatosArtista() {
        logger.logInfo("Por favor escribe el nombre del artista que deseas buscar");
        nombre = scanner.nextLine();
        String nombreArtista = nombre.replace(" ", "+");
        String url = URL_BASE_1 + nombreArtista + FORMAT_JSON;
        json.set(consumoApi.obtenerDatos(url));

        try {
            JsonNode rootNode = conversor.getObjectMapper().readTree(json.get());
            JsonNode artistsArray = rootNode.path("artists");

            if (artistsArray.isArray() && artistsArray.size() > 0) {
                return conversor.getObjectMapper().treeToValue(artistsArray.get(0), DatosArtista.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método auxiliar para obtener los datos de una canción desde la API
    private DatosCancion getDatosCancion(String nombreArtista) {
        logger.logInfo("Por favor escribe el nombre de la canción:");
        String nombreCancion = scanner.nextLine();
        String nombreCancionConMas = nombreCancion.replace(" ", "+");

        String url = URL_BASE_2 + nombreArtista.replace(" ", "+") + URL_BASE_2_1 + nombreCancionConMas + FORMAT_JSON;
        int maxRetries = 5;
        int attempts = 0;
        DatosCancion datosCancion = null;

        while (attempts < maxRetries) {
            String jsonResponse = consumoApi.obtenerDatos(url);

            datosCancion = parseDatosCancion(jsonResponse);

            // Si datosCancion no es nulo y tiene una descripción válida, salir del ciclo
            if (datosCancion != null && datosCancion.descripcion() != null && !datosCancion.descripcion().isEmpty()) {
                break;
            }

            attempts++;
            logger.logInfo("Intento " + attempts + " fallido. Reintentando...");

            try {
                Thread.sleep(2000); // Espera de 2 segundos antes de reintentar
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Después de 5 intentos, si no se encontró una descripción, usar "Información no disponible"
        if (datosCancion != null && (datosCancion.descripcion() == null || datosCancion.descripcion().isEmpty())) {
            datosCancion = new DatosCancion(
                    datosCancion.nombreCancion(),
                    "Información no disponible",  // Asigna "Información no disponible" a la descripción
                    datosCancion.duracion(),
                    datosCancion.evaluacion(),
                    datosCancion.artistCredits()
            );
        }

        return datosCancion;
    }

    // Método auxiliar para analizar la respuesta JSON y extraer datos de la canción
    private DatosCancion parseDatosCancion(String jsonResponse) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonResponse);
            JsonNode recordingNode = rootNode.path("recordings").get(0);

            // Extraer título y descripción de la canción
            String title = recordingNode.path("title").asText();
            String description = recordingNode.path("disambiguation").asText(null);
            int score = recordingNode.path("score").asInt();
            int length = recordingNode.path("length").asInt();

            DatosCancion.ArtistCredit[] artistCredits = new DatosCancion.ArtistCredit[0];

            return new DatosCancion(title, description, length, score, artistCredits);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Guardar o recuperar un artista existente en la base de datos
    private Artista saveOrRetrieveArtist(DatosArtista datosArtista) {
        Optional<Artista> artistaExistente = artistRepository.findByNombreArtista(datosArtista.nombreArtista());

        if (artistaExistente.isPresent()) {
            return artistaExistente.get();
        } else {
            Artista nuevoArtista = artistRepository.save(new Artista(datosArtista));
            // Recupera el artista guardado para asegurarse de que el ID esté presente
            return artistRepository.findById(nuevoArtista.getId()).orElseThrow();
        }
    }

    // Método para mostrar todos los artistas almacenados
    private void mostrarArtistasBuscados() {
        artistas.clear();
        artistas.addAll(artistRepository.findAll());

        // Ordenar lista y manejar posibles valores null
        artistas.stream()
                .sorted(Comparator.comparing(Artista::getGeneroMusical, Comparator.nullsLast(Comparator.naturalOrder())))
                .forEach(logger::logInfo);
    }


    private void buscarArtistaPorNombre() {
        logger.logInfo("Escribe el nombre del artista que deseas buscar");
        String nombreArtista = scanner.nextLine();

        // Limpiar la lista global `series` y llenarla con el top 5 obtenido del repositorio
        artistas.clear();
        artistas.addAll(artistRepository.findByNombreContainsIgnoreCase(nombreArtista));

        // Verificar si la lista está vacía o contiene artistas
        if (artistas.isEmpty()) {
            logger.logInfo("Artista no encontrado");
        } else {
            artistas.forEach(artista -> logger.logInfo("Artista encontrado: " + artista));
        }
    }

    private void buscarTop5Artistas() {

        logger.logInfo("Buscando el Top 5 de artistas mejor evaluados...");

        Pageable topFive = PageRequest.of(0, 5);

        // Limpiar la lista global `series` y llenarla con el top 5 obtenido del repositorio
        artistas.clear();
        artistas.addAll(artistRepository.findTop5ByOrderByEvaluacionDesc(topFive));

        if (!artistas.isEmpty()) {
            logger.logInfo("Top 5 de artistas mejor evaluados:");
            artistas.forEach(artista ->
                    logger.logInfo(String.format("Artista: %s, Evaluación: %.2f%n", artista.getNombreArtista(), artista.getEvaluacion().doubleValue())
            ));
        } else {
            logger.logInfo("No se encontraron artistas en la base de datos.");
        }
    }

    private void buscarArtistasPorGenero() {

        logger.logInfo("Escriba el genero musical del artista a buscar a buscar");
        nombre = scanner.nextLine();
        var generoMusical = GeneroMusical.fromString(nombre);


        // Limpiar la lista global `series` y llenarla con el top 5 obtenido del repositorio
        artistas.clear();
        artistas.addAll(artistRepository.findByGenero(generoMusical));

        logger.logInfo("Los artistas de la categoria: " + nombre);
        artistas.forEach(logger::logInfo);

    }

    private void buscarCancionesPorNombre() {
        logger.logInfo("Escribe el nombre de la canción que deseas buscar");
        nombre = scanner.nextLine();

        // Limpiar la lista antes de agregar nuevos resultados
        canciones.clear();
        canciones.addAll(cancionRepository.episodiosPorNombre(nombre));
        canciones.forEach(e ->
                logger.logInfo(String.format("Cancion: %s, Duracion: %.2f minutos, Evaluación: %d, Artista: %s%n",
                        e.getNombreCancion(), e.getDuracionEnMinutos(), e.getEvaluacion(), e.getArtista().getNombreArtista())));
    }
}


