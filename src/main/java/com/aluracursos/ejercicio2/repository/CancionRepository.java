package com.aluracursos.ejercicio2.repository;

import com.aluracursos.ejercicio2.model.Cancion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CancionRepository extends JpaRepository<Cancion, Long> {

    @Query("SELECT c FROM Artista a JOIN a.canciones c WHERE c.nombreCancion ILIKE %:nombre%")
    List<Cancion> episodiosPorNombre(String nombre);
}
