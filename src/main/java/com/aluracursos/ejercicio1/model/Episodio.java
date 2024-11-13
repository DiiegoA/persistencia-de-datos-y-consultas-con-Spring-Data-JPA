package com.aluracursos.ejercicio1.model;

import java.util.List;
import java.util.stream.Collectors;

public class Episodio {

    // Atributos privados y finales para los datos del episodio
    private final String titulos;  // Almacena los títulos del episodio en formato de cadena
    private final int numeroEpisodios;  // Almacena el número de episodios, como un entero
    private final double evaluacion;  // Almacena la evaluación del episodio, como un double

    // Constructor que recibe los títulos, número de episodios y evaluación
    public Episodio(List<DatosTitulo> titulos, Integer numeroEpisodios, Double evaluacion) {
        // Si la lista de títulos es nula o está vacía, asigna un mensaje de "No se encontraron títulos", de lo contrario concatena los títulos
        this.titulos = (titulos == null || titulos.isEmpty()) ? "No se encontraron títulos" : concatenarTitulos(titulos);
        // Si el número de episodios es nulo, asigna 0, de lo contrario asigna el valor recibido
        this.numeroEpisodios = (numeroEpisodios != null) ? numeroEpisodios : 0;
        // Si la evaluación es nula, asigna 0.0, de lo contrario asigna el valor recibido
        this.evaluacion = (evaluacion != null) ? evaluacion : 0.0;
    }

    // Método privado que concatena los títulos en una sola cadena separados por comas
    private String concatenarTitulos(List<DatosTitulo> titulos) {
        // Usa stream para mapear cada título y los concatena con ", "
        return titulos.stream()
                .map(DatosTitulo::titulo)  // Obtiene el campo 'titulo' de cada objeto DatosTitulo
                .collect(Collectors.joining(", "));  // Une todos los títulos en una sola cadena, separados por comas
    }

    // Método getter para obtener la cadena de títulos concatenados
    public String getTitulos() {
        return titulos;  // Retorna el valor de 'titulos'
    }

    // Método getter para obtener el número de episodios
    public int getNumeroEpisodios() {
        return numeroEpisodios;  // Retorna el valor de 'numeroEpisodios'
    }

    // Método getter para obtener la evaluación
    public double getEvaluacion() {
        return evaluacion;  // Retorna el valor de 'evaluacion'
    }

    // Método toString para representar el objeto como una cadena de texto
    @Override
    public String toString() {
        // Usa String.format para crear una cadena que contiene los valores de los atributos
        return String.format("titulos='%s', numeroEpisodios=%d, evaluacion=%.1f", titulos, numeroEpisodios, evaluacion);
    }
}