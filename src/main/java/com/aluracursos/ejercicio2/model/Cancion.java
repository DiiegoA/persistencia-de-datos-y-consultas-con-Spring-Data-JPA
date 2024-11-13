package com.aluracursos.ejercicio2.model;

import jakarta.persistence.*;

@Entity
@Table(name = "canciones")
public class Cancion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private final String nombreCancion;
    private final String descripcion;
    private final Integer duracion;
    private final Integer evaluacion;

    @ManyToOne
    @JoinColumn(name = "artista_id")
    private final Artista artista;

    public Cancion() {
        this.nombreCancion = "Información no disponible";
        this.descripcion = "Información no disponible";
        this.duracion = 0;
        this.evaluacion = 0;
        this.artista = null;
    }

    public Cancion(DatosCancion datosCancion) {
        System.out.println("Datos recibidos para la canción: " + datosCancion);
        this.nombreCancion = verificarValor(datosCancion.nombreCancion());
        this.descripcion = verificarValor(datosCancion.descripcion()); // Usar directamente la descripción sin verificación
        this.duracion = verificarEntero(datosCancion.duracion());
        this.evaluacion = verificarEntero(datosCancion.evaluacion());
        this.artista = null;
    }

    private Cancion(Long id, String nombreCancion, String descripcion, Integer duracion, Integer evaluacion, Artista artista) {
        this.id = id;
        this.nombreCancion = nombreCancion;
        this.descripcion = descripcion;
        this.duracion = duracion;
        this.evaluacion = evaluacion;
        this.artista = artista;
    }

    public double getDuracionEnMinutos() {
        return duracion != null ? duracion / 60000.0 : 0.0;
    }

    public Cancion asociarConArtista(Artista artista) {
        if (artista.getId() == null) {
            throw new IllegalStateException("El Artista debe estar guardado en la base de datos antes de asignarlo a una Cancion.");
        }
        return new Cancion(null, this.nombreCancion, this.descripcion, this.duracion, this.evaluacion, artista);
    }

    private String verificarValor(String valor) {
        return (valor == null || valor.isEmpty() || valor.equalsIgnoreCase("N/A"))
                ? "Información no disponible" : valor;
    }

    private Integer verificarEntero(Integer valor) {
        return valor != null ? valor : 0;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getNombreCancion() {
        return nombreCancion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public Integer getEvaluacion() {
        return evaluacion;
    }

    public Artista getArtista() {
        return artista;
    }

    @Override
    public String toString() {
        return "nombreCancion= '" + nombreCancion + '\'' +
                ", descripcion= '" + descripcion + '\'' +
                ", duracion= " + getDuracionEnMinutos() + " minutos" +
                ", evaluacion= " + evaluacion +
                ", artista= " + (artista != null ? artista.getNombreArtista() : "Ninguno");
    }
}