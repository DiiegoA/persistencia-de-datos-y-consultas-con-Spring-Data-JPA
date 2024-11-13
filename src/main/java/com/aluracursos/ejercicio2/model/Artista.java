package com.aluracursos.ejercicio2.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "artistas")
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private final String tipoGrupo;

    @Column(unique = true)
    private final String nombreArtista;

    // Almacena el nombre en lugar de una referencia a otra entidad
    private final String area;
    private final String ciudadOrigen;
    private final String inicioActividad;

    @Enumerated(EnumType.STRING)
    private final GeneroMusical generoMusical;

    private final Integer evaluacion;

    @OneToMany(mappedBy = "artista", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private final List<Cancion> canciones;

    public Artista() {
        this.tipoGrupo = "Información no disponible";
        this.nombreArtista = "Información no disponible";
        this.area = "Información no disponible";
        this.ciudadOrigen = "Información no disponible";
        this.inicioActividad = "Información no disponible";
        this.generoMusical = null;
        this.evaluacion = 0;
        this.canciones = new ArrayList<>();
    }

    public Artista(DatosArtista datosArtista) {
        this.tipoGrupo = verificarValor(datosArtista.tipoGrupo());
        this.nombreArtista = verificarValor(datosArtista.nombreArtista());
        this.area = datosArtista.area() != null ? datosArtista.area().nombreArea() : "Información no disponible";
        this.ciudadOrigen = datosArtista.ciudadOrigen() != null ? datosArtista.ciudadOrigen().nombreCiudad() : "Información no disponible";
        this.inicioActividad = datosArtista.inicioActividad() != null ? datosArtista.inicioActividad().inicio() : "Información no disponible";
        this.generoMusical = encontrarGenero(datosArtista.genero());
        this.evaluacion = verificarEntero(datosArtista.evaluacion());
        this.canciones = new ArrayList<>();
    }

    // Constructor completo para mantener los campos final
    public Artista(long id, String tipoGrupo, String nombreArtista, String area, String ciudadOrigen, String inicioActividad, GeneroMusical generoMusical, Integer evaluacion, List<Cancion> canciones) {
        this.id = id;
        this.tipoGrupo = tipoGrupo;
        this.nombreArtista = nombreArtista;
        this.area = area;
        this.ciudadOrigen = ciudadOrigen;
        this.inicioActividad = inicioActividad;
        this.generoMusical = generoMusical;
        this.evaluacion = evaluacion;
        this.canciones = canciones != null ? new ArrayList<>(canciones) : new ArrayList<>();
    }


    private String verificarValor(String valor) {
        if (valor == null || valor.isEmpty() || valor.equalsIgnoreCase("N/A")) {
            return "Información no disponible";
        }
        return valor;
    }

    private Integer verificarEntero(Integer valor) {
        return valor != null ? valor : 0;
    }

    private GeneroMusical encontrarGenero(List<DatosArtista.Tag> tags) {
        if (tags == null || tags.isEmpty()) {
            return null;
        }
        return tags.stream()
                .map(tag -> {
                    try {
                        return GeneroMusical.fromString(tag.nombre());
                    } catch (IllegalArgumentException e) {
                        return null;
                    }
                })
                .filter(genero -> genero != null)
                .findFirst()
                .orElse(null);
    }

    public Artista agregarCanciones(List<Cancion> nuevasCanciones) {
        List<Cancion> cancionesConArtistas = nuevasCanciones.stream()
                .map(e -> e.asociarConArtista(this))
                .toList();

        List<Cancion> cancionesActualizadas = new ArrayList<>(this.canciones);
        cancionesActualizadas.addAll(cancionesConArtistas);

        Artista artistaActualizada = new Artista(this.id, this.tipoGrupo, this.nombreArtista, this.area,
                this.ciudadOrigen, this.inicioActividad, this.generoMusical,
                this.evaluacion, cancionesActualizadas);
        return artistaActualizada;
    }

    public Long getId() {
        return id;
    }

    public String getTipoGrupo() {
        return tipoGrupo;
    }

    public String getNombreArtista() {
        return nombreArtista;
    }

    public String getArea() {
        return area;
    }

    public String getCiudadOrigen() {
        return ciudadOrigen;
    }

    public String getInicioActividad() {
        return inicioActividad;
    }

    public GeneroMusical getGeneroMusical() {
        return generoMusical;
    }

    public Integer getEvaluacion() {
        return evaluacion;
    }

    public List<Cancion> getCanciones() {
        return canciones;
    }

    @Override
    public String toString() {
        return "tipoGrupo= '" + tipoGrupo + '\'' +
                ", nombreArtista= '" + nombreArtista + '\'' +
                ", area= " + area +
                ", ciudadOrigen= " + ciudadOrigen +
                ", inicioActividad= " + inicioActividad +
                ", generoMusical= " + generoMusical +
                ", evaluacion= " + evaluacion +
                ", canciones= " + canciones;
    }
}