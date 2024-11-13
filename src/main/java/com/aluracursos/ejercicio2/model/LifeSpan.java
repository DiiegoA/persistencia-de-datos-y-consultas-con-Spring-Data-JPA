package com.aluracursos.ejercicio2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class LifeSpan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private final String inicio;

    // Constructor protegido sin argumentos para JPA
    protected LifeSpan() {
        this("Información no valida");
    }

    public LifeSpan(String inicio) {
        this.inicio = inicio;
    }

    public String getInicio() {
        return inicio;
    }
}

