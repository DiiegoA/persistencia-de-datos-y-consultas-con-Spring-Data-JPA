package com.aluracursos.ejercicio2.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosArtista(
        @JsonAlias("type") String tipoGrupo,
        @JsonAlias("name") String nombreArtista,
        @JsonAlias("area") AreaData area,
        @JsonAlias("begin-area") BeginAreaData ciudadOrigen,
        @JsonAlias("life-span") LifeSpanData inicioActividad,
        @JsonAlias("tags") List<Tag> genero,
        @JsonAlias("score") Integer evaluacion
) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Tag(
            @JsonAlias("name") String nombre
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record AreaData(
            @JsonAlias("name") String nombreArea
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record BeginAreaData(
            @JsonAlias("name") String nombreCiudad
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record LifeSpanData(
            @JsonAlias("begin") String inicio
    ) {}
}