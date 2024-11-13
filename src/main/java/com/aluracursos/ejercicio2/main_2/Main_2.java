package com.aluracursos.ejercicio2.main_2;

import com.aluracursos.ejercicio2.menu.MenuPrincipal;
import com.aluracursos.ejercicio2.repository.CancionRepository;
import com.aluracursos.ejercicio2.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication()
@EnableJpaRepositories(basePackages = "com.aluracursos.ejercicio2.repository")
@EntityScan(basePackages = "com.aluracursos.ejercicio2.model")
public class Main_2 implements CommandLineRunner {

    @Autowired
    private MusicRepository artistRepository;

    @Autowired
    private CancionRepository cancionRepository;

    public static void main(String[] args) {
        SpringApplication.run(Main_2.class, args);
    }

    @Override
    public void run(String... args) {
        MenuPrincipal menu = new MenuPrincipal(artistRepository, cancionRepository);
        menu.muestraElMenu();
    }
}