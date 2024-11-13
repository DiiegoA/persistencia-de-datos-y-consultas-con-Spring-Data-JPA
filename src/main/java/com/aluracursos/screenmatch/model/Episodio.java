package com.aluracursos.screenmatch.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Entity
@Table(name = "episodios")
public class Episodio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final long id;
    private final Integer temporada;
    private final String titulo;
    private final Integer numeroEpisodio;
    private final Double evaluacion;
    private final String fechaDelanzamiento; // Se usa como String para manejar "Fecha no disponible"

    @ManyToOne
    @JoinColumn(name = "serie_id")
    private final Serie serie;

    // Constructor sin parámetros con valores predeterminados
    public Episodio() {
        this.id = 0;
        this.temporada = 1;
        this.titulo = "Título no disponible";
        this.numeroEpisodio = 0;
        this.evaluacion = 0.0;
        this.fechaDelanzamiento = "Fecha no disponible";
        this.serie = null;
    }

    // Constructor para crear un episodio con temporada y datos, sin serie
    public Episodio(Integer numero, DatosEpisodio d) {
        this.id = 0;
        this.temporada = numero;
        this.titulo = obtenerTextoSeguro(d.titulo());
        this.numeroEpisodio = d.numeroEpisodio() != null ? d.numeroEpisodio() : 0;
        this.evaluacion = convertirEvaluacion(d.evaluacion());
        this.fechaDelanzamiento = convertirFecha(d.fechaDeLanzamiento());
        this.serie = null;
    }

    private Double convertirEvaluacion(String evaluacion) {
        if (evaluacion == null || evaluacion.equalsIgnoreCase("N/A")) {
            return 0.0;
        }
        try {
            return Double.valueOf(evaluacion);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private String convertirFecha(String fecha) {
        if (fecha == null || fecha.equalsIgnoreCase("N/A")) {
            return "Fecha no disponible";
        }
        try {
            LocalDate.parse(fecha); // Verifica si la fecha es válida
            return fecha;
        } catch (DateTimeParseException e) {
            return "Fecha no disponible";
        }
    }

    private String obtenerTextoSeguro(String valor) {
        if (valor == null || valor.isEmpty() || valor.equalsIgnoreCase("N/A")) {
            return "Información no disponible";
        }
        return valor;
    }

    // Método auxiliar para asociar un episodio con una serie
    public Episodio asociarConSerie(Serie serie) {
        if (this.serie == null) {
            return new Episodio(this.id, this.temporada, this.titulo, this.numeroEpisodio,
                    this.evaluacion, this.fechaDelanzamiento, serie);
        } else {
            throw new IllegalStateException("La serie ya ha sido asignada.");
        }
    }

    // Constructor privado para crear una instancia con una serie asignada
    private Episodio(long id, Integer temporada, String titulo, Integer numeroEpisodio,
                     Double evaluacion, String fechaDelanzamiento, Serie serie) {
        this.id = id;
        this.temporada = temporada;
        this.titulo = titulo;
        this.numeroEpisodio = numeroEpisodio;
        this.evaluacion = evaluacion;
        this.fechaDelanzamiento = fechaDelanzamiento;
        this.serie = serie;
    }

    // Getters
    public long getId() {
        return id;
    }

    public Integer getTemporada() {
        return temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public Integer getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public Double getEvaluacion() {
        return evaluacion;
    }

    public String getFechaDelanzamiento() {
        return fechaDelanzamiento;
    }

    public Serie getSerie() {
        return serie;
    }

    @Override
    public String toString() {
        return "temporada=" + temporada +
                ", titulo='" + titulo + '\'' +
                ", numeroEpisodio=" + numeroEpisodio +
                ", evaluacion=" + evaluacion +
                ", fechaDelanzamiento=" + fechaDelanzamiento;
    }
}