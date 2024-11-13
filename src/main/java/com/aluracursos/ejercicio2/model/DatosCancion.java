package com.aluracursos.ejercicio2.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosCancion(
        @JsonAlias("title") String nombreCancion,
        @JsonAlias("disambiguation") String descripcion,
        @JsonAlias("length") Integer duracion,
        @JsonAlias("score") Integer evaluacion,
        @JsonProperty("artist-credit") ArtistCredit[] artistCredits
) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ArtistCredit(
            @JsonAlias("artist") Artist artist
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Artist(
            @JsonAlias("disambiguation") String disambiguation
    ) {}
}