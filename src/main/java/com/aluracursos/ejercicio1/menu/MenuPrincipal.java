package com.aluracursos.ejercicio1.menu;

import com.aluracursos.ejercicio1.model.Anime;
import com.aluracursos.ejercicio1.model.DatosAnime;
import com.aluracursos.ejercicio1.model.DatosEpisodio;
import com.aluracursos.ejercicio1.model.Episodio;
import com.aluracursos.ejercicio1.service.ConsumoApi;
import com.aluracursos.ejercicio1.service.ConvierteDatos;
import com.aluracursos.logger.loggerbase.LoggerBase;
import com.aluracursos.logger.loggerbase.LoggerBaseImpl;

import java.io.IOException;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MenuPrincipal  {

    // Declaración de las variables de instancia
    private final Scanner scanner = new Scanner(System.in);  // Escáner para leer entrada del usuario
    private final ConsumoApi consumoApi = new ConsumoApi();  // Objeto para consumir la API
    private final ConvierteDatos conversor = new ConvierteDatos();  // Conversor de datos JSON
    private static final String URL_API = "https://api.jikan.moe/v4/anime";  // URL base de la API de animes
    private static final String URL_BASE = "https://api.jikan.moe/v4/anime?q=";  // URL para realizar búsquedas de animes
    private static final String URL_EPISODES = "https://api.jikan.moe/v4/anime/";  // URL para obtener episodios de un anime específico
    private final LoggerBase logger;

    public MenuPrincipal() {
        this.logger = new LoggerBaseImpl(MenuPrincipal.class.getName());
    }

    public void muestraElMenu() throws IOException, InterruptedException {
        // Obtener datos desde la API y reemplazar caracteres escapados en la respuesta JSON
        String json = consumoApi.obtenerDatos(URL_API).replace("\\/", "/");

        // Mostrar un fragmento del JSON obtenido (500 caracteres) en la consola
        logger.logInfo("Fragmento del JSON obtenido:");
        logger.logInfo(json.substring(0, Math.min(json.length(), 500)) + "...");

        // Convertir el JSON en una lista de objetos 'Anime' a partir de 'DatosAnime'
        List<Anime> listaAnimesGeneral = conversor.obtenerListaDatos(json, DatosAnime.class).stream()
                .map(anime -> new Anime(anime.id(), anime.titulos(), anime.totalDeEpisodios(), anime.evaluaciones()))  // Mapeo de 'DatosAnime' a 'Anime'
                .collect(Collectors.toList());  // Recoger los resultados en una lista

        // Verificar si se encontraron animes en la API
        if (listaAnimesGeneral.isEmpty()) {
            logger.logInfo("No se encontraron animes en la API.");
            return;  // Terminar el método si no se encontraron animes
        }

        // Obtener los datos del primer anime desde el JSON
        DatosAnime datosAnime = conversor.obtenerDatos(json, DatosAnime.class);

        // Mostrar los primeros 5 animes si hay episodios disponibles
        if (datosAnime != null && datosAnime.episodios() != null && !datosAnime.episodios().isEmpty()) {
            logger.logInfo(String.format("%nPrimeros 5 Animes de la API:"));
            datosAnime.episodios().stream().limit(5).forEach(logger::logInfo);  // Limitar la salida a los primeros 5 episodios
        } else {
            logger.logInfo("No se encontraron episodios.");
        }

        // Mostrar los 5 animes con mejor evaluación
        logger.logInfo(String.format("%nLos 5 Animes más votados con sus títulos:"));
        listaAnimesGeneral.stream()
                .sorted((a1, a2) -> Double.compare(a2.getEvaluaciones(), a1.getEvaluaciones()))  // Ordenar por evaluación de mayor a menor
                .limit(5)  // Limitar a los 5 primeros
                .forEach(anime -> {
                    logger.logInfo(String.format("ID: %s%nTítulos: %s%nEvaluación: %.2f%n-------------------------------%n",
                            anime.getId(), anime.getTitulos(), anime.getEvaluaciones()));  // Mostrar ID, títulos y evaluación
                });

        // Solicitar el nombre de un anime al usuario
        logger.logInfo("Por favor escribe el nombre del Anime que deseas buscar");
        String nombreAnime = scanner.nextLine().replace(" ", "+");  // Leer y procesar el nombre del anime

        // Realizar la búsqueda con el nombre ingresado por el usuario
        String jsonBusqueda = consumoApi.obtenerDatos(URL_BASE + nombreAnime);
        List<Anime> listaAnimesBusqueda = conversor.obtenerListaDatos(jsonBusqueda, DatosAnime.class).stream()
                .map(anime -> new Anime(anime.id(), anime.titulos(), anime.totalDeEpisodios(), anime.evaluaciones()))  // Mapeo de 'DatosAnime' a 'Anime'
                .collect(Collectors.toList());  // Recoger los resultados en una lista

        // Buscar el primer anime que coincida con el título ingresado
        Optional<Anime> animeBuscado = listaAnimesBusqueda.stream()
                .filter(anime -> anime.getTitulos().toUpperCase().contains(nombreAnime.replace("+", " ").toUpperCase()))  // Verificar coincidencias en el título
                .findFirst();  // Encontrar el primer resultado que coincida

        // Si el anime fue encontrado, proceder con el cálculo de episodios y estadísticas
        if (animeBuscado.isPresent()) {
            Anime anime = animeBuscado.get();  // Obtener el anime encontrado
            logger.logInfo("Anime encontrado: " + anime.getTitulos());  // Mostrar los títulos del anime encontrado

            // Obtener los episodios del anime usando el ID
            String jsonEpisodios = consumoApi.obtenerDatos(URL_EPISODES + anime.getId() + "/episodes");
            List<DatosEpisodio> episodiosDatos = conversor.obtenerListaDatos(jsonEpisodios, DatosEpisodio.class);  // Convertir el JSON en objetos 'DatosEpisodio'

            // Verificar si se encontraron episodios
            if (episodiosDatos == null || episodiosDatos.isEmpty()) {
                logger.logInfo("No se encontraron episodios para este anime.");
                return;  // Terminar el método si no hay episodios
            }

            // Convertir 'DatosEpisodio' en 'Episodio', eliminando aquellos con evaluación 0.0
            List<Episodio> listaEpisodios = episodiosDatos.stream()
                    .map(ep -> new Episodio(ep.titulos(), ep.numeroEpisodios(), ep.evaluacion()))  // Mapeo de 'DatosEpisodio' a 'Episodio'
                    .filter(ep -> ep.getEvaluacion() != 0.0)  // Filtrar episodios con evaluación 0.0
                    .collect(Collectors.toList());  // Recoger los resultados en una lista

            // Verificar si hay episodios con evaluaciones válidas
            if (listaEpisodios.isEmpty()) {
                logger.logInfo("No se encontraron episodios con evaluaciones válidas.");
                return;  // Terminar el método si no hay episodios válidos
            }

            // Calcular las estadísticas de las evaluaciones de los episodios
            DoubleSummaryStatistics estadisticasEvaluacion = listaEpisodios.stream()
                    .mapToDouble(Episodio::getEvaluacion)  // Extraer las evaluaciones de los episodios
                    .summaryStatistics();  // Calcular estadísticas

            // Mostrar las estadísticas de las evaluaciones de los episodios
            logger.logInfo(String.format("""
                            %nEstadísticas de las evaluaciones de los episodios del anime:
                            Promedio de evaluaciones: %.2f
                            Evaluación máxima: %.2f
                            Evaluación mínima: %.2f
                            Total de episodios evaluados: %d
                            """, estadisticasEvaluacion.getAverage(), estadisticasEvaluacion.getMax(), estadisticasEvaluacion.getMin(), estadisticasEvaluacion.getCount()));
        } else {
            // Si no se encontró el anime, mostrar un mensaje de error
            logger.logInfo("No se encontraron animes que coincidan con la búsqueda.");
        }
    }
}