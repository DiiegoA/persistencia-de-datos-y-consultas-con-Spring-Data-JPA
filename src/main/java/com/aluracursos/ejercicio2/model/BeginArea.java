package com.aluracursos.ejercicio2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class BeginArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private final String nombreCiudad;

    // Constructor protegido sin argumentos para JPA
    protected BeginArea() {
        this("Información no valida");
    }

    public BeginArea(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }
}
