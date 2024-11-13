package com.aluracursos.screenmatch.model;

import com.aluracursos.screenmatch.service.ConsultaDeepL;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "series")
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final long id;
    @Enumerated(EnumType.STRING)
    private final Categoria genero;
    @Column(unique = true)
    private final String titulo;
    private final Integer totalDeTemporadas;
    private final Double evaluacion;
    private final String actores;
    private final String duracion;
    private final String poster;
    private final String trama;

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private final List<Episodio> episodios;

    // Constructor sin parámetros
    public Serie() {
        this.id = 0;
        this.titulo = "Información no disponible";
        this.totalDeTemporadas = 0;
        this.evaluacion = 0.0;
        this.genero = null; // O el valor predeterminado que desees
        this.actores = "Información no disponible";
        this.duracion = "Información no disponible";
        this.poster = "Información no disponible";
        this.trama = "Información no disponible";
        this.episodios = new ArrayList<>();
    }

    // Constructor que inicializa la serie con datos
    public Serie(DatosSerie datosSerie) {
        this.id = 0;
        this.titulo = verificarValor(datosSerie.titulo());
        this.totalDeTemporadas = verificarEntero(datosSerie.totalDeTemporadas());
        this.evaluacion = verificarEvaluaciones(datosSerie.evaluacion());
        this.genero = Categoria.fromString(verificarValor(datosSerie.genero()).split(",")[0].trim());
        this.actores = verificarValor(datosSerie.actores());
        this.duracion = verificarValor(datosSerie.duracion());
        this.poster = verificarValor(datosSerie.poster());
        this.trama = verificarValor(ConsultaDeepL.obtenerTraduccion(datosSerie.trama()));
        this.episodios = new ArrayList<>();
    }

    // Método para agregar episodios a la serie
    public Serie agregarEpisodios(List<Episodio> nuevosEpisodios) {
        List<Episodio> episodiosConSerie = nuevosEpisodios.stream()
                .map(e -> e.asociarConSerie(this))
                .toList();

        List<Episodio> episodiosActualizados = new ArrayList<>(this.episodios);
        episodiosActualizados.addAll(episodiosConSerie);

        Serie serieActualizada = new Serie(this.id, this.titulo, this.genero, this.totalDeTemporadas,
                this.evaluacion, this.actores, this.duracion, this.poster,
                this.trama, episodiosActualizados);
        return serieActualizada;
    }

    // Constructor privado para crear una copia con episodios actualizados
    private Serie(long id, String titulo, Categoria genero, Integer totalDeTemporadas, Double evaluacion,
                  String actores, String duracion, String poster, String trama, List<Episodio> episodios) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.totalDeTemporadas = totalDeTemporadas;
        this.evaluacion = evaluacion;
        this.actores = actores;
        this.duracion = duracion;
        this.poster = poster;
        this.trama = trama;
        this.episodios = episodios != null ? new ArrayList<>(episodios) : new ArrayList<>();
    }

    // Método para verificar si un valor es null, vacío o "N/A" y devolver un string adecuado
    private String verificarValor(String valor) {
        if (valor == null || valor.isEmpty() || valor.equalsIgnoreCase("N/A")) {
            return "Información no disponible";
        }
        return valor;
    }

    // Método para verificar si el número entero es null y devolver un valor por defecto
    private Integer verificarEntero(Integer valor) {
        return valor != null ? valor : 0;
    }

    // Método para verificar las evaluaciones y cambiar a 0.0 si es null o "N/A"
    private Double verificarEvaluaciones(Double evaluacion) {
        return (evaluacion == null) ? 0.0 : evaluacion;
    }

    // Getters
    public long getId() {
        return id;
    }

    public Categoria getGenero() {
        return genero;
    }

    public String getTitulo() {
        return titulo;
    }

    public Integer getTotalDeTemporadas() {
        return totalDeTemporadas;
    }

    public Double getEvaluacion() {
        return evaluacion;
    }

    public String getActores() {
        return actores;
    }

    public String getDuracion() {
        return duracion;
    }

    public String getPoster() {
        return poster;
    }

    public String getTrama() {
        return trama;
    }

    public List<Episodio> getEpisodios() {
        return Collections.unmodifiableList(episodios); // Retorna una copia inmutable para mantener la inmutabilidad
    }

    @Override
    public String toString() {
        return "genero = '" + genero + '\'' +
                ", titulo = '" + titulo + '\'' +
                ", totalDeTemporadas = " + totalDeTemporadas +
                ", evaluacion = '" + evaluacion + '\'' +
                ", actores = '" + actores + '\'' +
                ", duracion = '" + duracion + '\'' +
                ", poster = '" + poster + '\'' +
                ", trama = '" + trama + '\'' +
                ", episodios = '" + episodios + '\'';
    }
}