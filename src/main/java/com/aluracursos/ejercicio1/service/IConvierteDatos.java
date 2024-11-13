package com.aluracursos.ejercicio1.service;

import java.util.List;

public interface IConvierteDatos {
    /**
     * Convierte un String JSON en un objeto del tipo especificado.
     *
     * @param <T>    Tipo de objeto al que se va a convertir el JSON.
     * @param json   Cadena de texto en formato JSON que contiene los datos a convertir.
     * @param clase  Clase del tipo al que se quiere convertir el JSON.
     * @return Objeto del tipo especificado.
     */
    <T> T obtenerDatos(String json, Class<T> clase);

    /**
     * Convierte un String JSON en una lista de objetos del tipo especificado.
     *
     * @param <T>    Tipo de los objetos a los que se va a convertir el JSON.
     * @param json   Cadena de texto en formato JSON que contiene los datos a convertir.
     * @param clase  Clase del tipo al que se quiere convertir el JSON.
     * @return Lista de objetos del tipo especificado.
     */
    <T> List<T> obtenerListaDatos(String json, Class<T> clase);
}