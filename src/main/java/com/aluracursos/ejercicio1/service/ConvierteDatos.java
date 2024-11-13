package com.aluracursos.ejercicio1.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.IOException;
import java.util.List;

// La clase ConvierteDatos implementa la interfaz IConvierteDatos, encargada de convertir datos de JSON a objetos Java

public class ConvierteDatos implements IConvierteDatos {

    // Se declara un ObjectMapper final para mapear los datos JSON
    private final ObjectMapper objectMapper;

    // Constructor que inicializa el ObjectMapper
    public ConvierteDatos() {
        this.objectMapper = new ObjectMapper();  // Se crea una nueva instancia de ObjectMapper
    }

    // Método genérico para obtener un solo objeto a partir de un JSON y una clase
    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            // Convierte el JSON recibido en un objeto de la clase proporcionada
            return objectMapper.readValue(json, clase);  // Usa ObjectMapper para leer el JSON y mapearlo a la clase dada
        } catch (IOException e) {  // Si ocurre una excepción al procesar el JSON
            // Lanza una excepción específica con un mensaje indicando que hubo un error en el procesamiento del JSON
            throw new IllegalArgumentException("Error al procesar el JSON", e);  // Lanza una excepción personalizada con el mensaje de error
        }
    }

    // Método genérico para obtener una lista de objetos a partir de un JSON y una clase
    @Override
    public <T> List<T> obtenerListaDatos(String json, Class<T> clase) {
        try {
            // Convierte el JSON recibido en un nodo y busca el nodo 'data' que contiene la lista de datos
            JsonNode dataNode = objectMapper.readTree(json).path("data");  // Extrae el nodo 'data' del JSON

            // Verifica si el nodo 'data' es un array, si no lo es lanza una excepción
            if (!dataNode.isArray()) {
                // Si 'data' no contiene una lista, lanza una excepción
                throw new IllegalArgumentException("El nodo 'data' no contiene una lista.");  // Mensaje de error si no es una lista
            }

            // Define el tipo de colección que será retornada (List de la clase especificada)
            CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, clase);  // Crea el tipo de colección

            // Convierte el nodo 'data' en una lista de objetos de la clase especificada
            return objectMapper.readValue(dataNode.traverse(), listType);  // Lee y convierte el nodo 'data' en una lista de objetos de tipo clase
        } catch (IOException e) {  // Si ocurre una excepción al procesar el JSON
            // Lanza una excepción personalizada con un mensaje indicando que hubo un error en el procesamiento del JSON
            throw new IllegalArgumentException("Error al procesar el JSON", e);  // Lanza una excepción personalizada con el mensaje de error
        }
    }
}