package com.aluracursos.screenmatch.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class ConsultaDeepL {

    public static String obtenerTraduccion(String texto) {
        // Tu clave API de DeepL
        String apiKey = System.getenv("DEEPL_API_KEY");  // Reemplaza con tu clave de API de DeepL

        // Endpoint de la API de DeepL
        String url = "https://api-free.deepl.com/v2/translate";

        // Crear un cliente HTTP
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            // Crear la solicitud POST
            HttpPost post = new HttpPost(url);
            post.setHeader("Authorization", "DeepL-Auth-Key " + apiKey);
            post.setHeader("Content-Type", "application/x-www-form-urlencoded");

            // Definir los parámetros de la solicitud (texto, idioma de origen y destino)
            String params = "text=" + texto + "&target_lang=ES";  // 'ES' es para español
            post.setEntity(new StringEntity(params));

            // Ejecutar la solicitud y obtener la respuesta
            CloseableHttpResponse response = httpClient.execute(post);
            String jsonResponse = EntityUtils.toString(response.getEntity());

            // Parsear la respuesta JSON
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            String traduccion = jsonObject
                    .getAsJsonArray("translations")
                    .get(0)
                    .getAsJsonObject()
                    .get("text")
                    .getAsString();

            return traduccion;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error al traducir el texto";
        }
    }
}