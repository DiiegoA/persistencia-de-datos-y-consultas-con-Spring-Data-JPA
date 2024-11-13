package com.aluracursos.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosSerie(

        @JsonAlias("Genre") String genero,
        @JsonAlias("Title") String titulo,
        @JsonAlias("totalSeasons") Integer totalDeTemporadas,
        @JsonAlias("imdbRating") Double evaluacion,
        @JsonAlias("Actors") String actores,
        @JsonAlias("Runtime") String duracion,
        @JsonAlias("Poster") String poster,
        @JsonAlias("Plot") String trama

) {
}
