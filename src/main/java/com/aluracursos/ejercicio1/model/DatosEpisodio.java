package com.aluracursos.ejercicio1.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/*Esta anotación indica que si hay campos desconocidos en el JSON que no están mapeados en esta clase, deben ser ignorados en
lugar de lanzar una excepción.*/
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosEpisodio(
        /*Este campo corresponde a una lista de títulos. La anotación @JsonAlias("titles") indica que cuando se deserializa el
        JSON, si aparece un campo llamado "titles", se mapeará a este campo.*/
        @JsonAlias("titles") List<DatosTitulo> titulos,
        // Este campo almacena el número de episodios. Se mapeará desde el campo "episodes" en el JSON.
        @JsonAlias("episodes") Integer numeroEpisodios,
        // Este campo almacena la evaluación del episodio. Se mapeará desde el campo "score" en el JSON.
        @JsonAlias("score") Double evaluacion
) {
    /*La clase `DatosEpisodio` es un record, que es una forma concisa de declarar clases en Java que solo contienen datos.
    Se encarga automáticamente de crear constructores, getters, `equals()`, `hashCode()` y `toString()` sin necesidad de definirlos
    manualmente.*/
}