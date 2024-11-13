package com.aluracursos.ejercicio1.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*Esta anotación indica que si el JSON contiene campos desconocidos que no están mapeados en esta clase, deben ser ignorados en
lugar de lanzar una excepción.*/
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosTitulo(
        // Se usa la anotación @JsonAlias para mapear el campo "title" del JSON a la variable "titulo" en esta clase.
        @JsonAlias("title") String titulo
) {
    /*La clase `DatosTitulo` es un record, que es una forma concisa de declarar clases en Java que contienen solo datos.
    Los records automáticamente generan constructores, getters, equals(), hashCode() y toString().*/
}
