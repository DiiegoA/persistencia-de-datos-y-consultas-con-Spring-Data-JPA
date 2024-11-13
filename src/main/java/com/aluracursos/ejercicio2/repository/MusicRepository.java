package com.aluracursos.ejercicio2.repository;

import com.aluracursos.ejercicio2.model.Artista;
import com.aluracursos.ejercicio2.model.GeneroMusical;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MusicRepository extends JpaRepository<Artista, Long> {

    @Query("SELECT a FROM Artista a WHERE LOWER(a.nombreArtista) = LOWER(:nombreArtista)")
    Optional<Artista> findByNombreArtista(String nombreArtista);

    @Query("SELECT a FROM Artista a WHERE LOWER(a.nombreArtista) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Artista> findByNombreContainsIgnoreCase(String nombre);


    @Query("SELECT a FROM Artista a ORDER BY a.evaluacion DESC")
    List<Artista> findTop5ByOrderByEvaluacionDesc(Pageable pageable);

    @Query("SELECT a FROM Artista a WHERE a.generoMusical = :genero")
    List<Artista> findByGenero(GeneroMusical genero);
}