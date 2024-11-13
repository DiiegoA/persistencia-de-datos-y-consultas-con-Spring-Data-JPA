package com.aluracursos.ejercicio1.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/*Esta anotación indica que cualquier campo en el JSON que no esté mapeado en esta clase será ignorado,lo que permite que la
deserialización sea más flexible y no cause errores si hay más datos en el JSON de los que la clase necesita.*/
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAnime(
        // El campo "mal_id" en el JSON se mapeará a la variable "id" en esta clase, que es el identificador del anime en MyAnimeList.
        @JsonAlias("mal_id") Integer id,
        // El campo "titles" en el JSON se mapeará a una lista de objetos DatosTitulo, que contiene los diferentes títulos del anime.
        @JsonAlias("titles") List<DatosTitulo> titulos,
        // El campo "episodes" en el JSON se mapeará a la variable "totalDeEpisodios", que representa el número total de episodios del anime.
        @JsonAlias("episodes") Integer totalDeEpisodios,
        // El campo "score" en el JSON se mapeará a la variable "evaluaciones", que representa la puntuación promedio o evaluación del anime.
        @JsonAlias("score") Double evaluaciones,
        // El campo "data" en el JSON se mapeará a una lista de objetos DatosEpisodio, que contiene los datos de cada episodio del anime.
        @JsonAlias("data") List<DatosEpisodio> episodios
) {
    // Al ser un record, esta clase es inmutable y automáticamente genera un constructor, getters, equals(), hashCode() y toString().
}