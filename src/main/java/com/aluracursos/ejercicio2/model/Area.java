package com.aluracursos.ejercicio2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private final String nombreArea;

    // Constructor protegido sin argumentos para JPA
    protected Area() {
        this("Información no valida");
    }

    public Area(String nombreArea) {
        this.nombreArea = nombreArea;
    }

    public String getNombreArea() {
        return nombreArea;
    }
}