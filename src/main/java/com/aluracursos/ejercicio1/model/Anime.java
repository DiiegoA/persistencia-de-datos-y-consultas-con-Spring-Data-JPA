package com.aluracursos.ejercicio1.model;

import java.util.List;
import java.util.stream.Collectors;

// Clase Anime que representa un anime con atributos como id, títulos, total de episodios y evaluaciones
public class Anime {

    // Atributos privados y finales para los datos del anime
    private final int id;  // Identificador único del anime
    private final String titulos;  // Títulos del anime concatenados en una cadena
    private final int totalDeEpisodios;  // Número total de episodios
    private final double evaluaciones;  // Evaluación del anime

    // Constructor que inicializa el anime con los valores proporcionados, gestionando posibles valores nulos
    public Anime(Integer id, List<DatosTitulo> titulos, Integer totalDeEpisodios, Double evaluaciones) {
        // Asigna -1 si el id es nulo, de lo contrario asigna el valor recibido
        this.id = (id != null) ? id : -1;
        // Si la lista de títulos es nula o está vacía, asigna "No se encontraron títulos", de lo contrario concatena los títulos
        this.titulos = (titulos == null || titulos.isEmpty()) ? "No se encontraron títulos" : concatenarTitulos(titulos);
        // Si el total de episodios es nulo, asigna 0, de lo contrario asigna el valor recibido
        this.totalDeEpisodios = (totalDeEpisodios != null) ? totalDeEpisodios : 0;
        // Si la evaluación es nula, asigna 0.0, de lo contrario asigna el valor recibido
        this.evaluaciones = (evaluaciones != null) ? evaluaciones : 0.0;
    }

    // Método privado para concatenar los títulos en una sola cadena de texto, separados por comas
    private String concatenarTitulos(List<DatosTitulo> titulos) {
        // Usa stream para mapear cada título y los concatena con ", "
        return titulos.stream()
                .map(DatosTitulo::titulo)  // Obtiene el campo 'titulo' de cada objeto DatosTitulo
                .collect(Collectors.joining(", "));  // Une los títulos en una sola cadena
    }

    // Método getter para obtener el id del anime
    public int getId() {
        return id;  // Retorna el valor de 'id'
    }

    // Método getter para obtener los títulos concatenados del anime
    public String getTitulos() {
        return titulos;  // Retorna el valor de 'titulos'
    }

    // Método getter para obtener el total de episodios del anime
    public int getTotalDeEpisodios() {
        return totalDeEpisodios;  // Retorna el valor de 'totalDeEpisodios'
    }

    // Método getter para obtener la evaluación del anime
    public double getEvaluaciones() {
        return evaluaciones;  // Retorna el valor de 'evaluaciones'
    }

    // Método toString para representar el objeto Anime como una cadena de texto
    @Override
    public String toString() {
        // Usa String.format para construir una cadena que muestra los valores de los atributos del anime
        return String.format("ID del Anime: %d%nTítulos: %s%nTotal de Episodios: %d%nEvaluación del Anime: %.1f", id, titulos, totalDeEpisodios, evaluaciones);
    }
}