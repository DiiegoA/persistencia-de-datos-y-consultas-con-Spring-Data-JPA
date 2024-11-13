package com.aluracursos.ejercicio2.model;

public enum GeneroMusical {

    POP("pop", "pop"),
    ROCK("rock", "rock"),
    HIP_HOP("hip hop", "hip hop"),
    JAZZ("jazz", "jazz"),
    CLASSICAL("classical", "clásica"),
    ELECTRONIC("electronic", "electrónica"),
    REGGAETON("reggaetón", "reggaetón"),
    COUNTRY("country", "country"),
    REGGAE("reggae", "reggae"),
    BLUES("blues", "blues");

    private final String nombreGenero;
    private final String nombreGeneroEspanol;

    GeneroMusical(String nombreGenero, String nombreGeneroEspanol) {
        this.nombreGenero = nombreGenero;
        this.nombreGeneroEspanol = nombreGeneroEspanol;
    }

    // Método para obtener el género musical por nombre en inglés o español (sin diferenciar mayúsculas y minúsculas)
    public static GeneroMusical fromString(String text) {
        for (GeneroMusical genero : GeneroMusical.values()) {
            if (genero.nombreGenero.equalsIgnoreCase(text) || genero.nombreGeneroEspanol.equalsIgnoreCase(text)) {
                return genero;
            }
        }
        throw new IllegalArgumentException("Género no encontrado: " + text);
    }

    public String getNombreGenero() {
        return nombreGenero;
    }

    public String getNombreGeneroEspanol() {
        return nombreGeneroEspanol;
    }


}