package com.aluracursos.ejercicio1.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoApi {

    // Declaración del cliente HttpClient como variable de instancia final
    private final HttpClient client;

    // Constructor que inicializa el cliente HttpClient
    public ConsumoApi() {
        this.client = HttpClient.newHttpClient();  // Se crea una nueva instancia de HttpClient
    }

    // Método para obtener datos de una URL específica
    public String obtenerDatos(String url) throws IOException, InterruptedException {
        // Construcción de la solicitud HTTP usando la URL proporcionada
        HttpRequest request = HttpRequest.newBuilder()  // Construir una nueva solicitud
                .uri(URI.create(url))  // Establecer la URI con la URL proporcionada
                .GET()  // Indicar que la solicitud es de tipo GET
                .build();  // Construir la solicitud

        // Enviar la solicitud y obtener la respuesta en formato String
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());  // Enviar la solicitud y recibir la respuesta como una cadena
        return response.body();  // Retornar el cuerpo de la respuesta en formato String
    }
}