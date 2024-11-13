package com.aluracursos.ejercicio1.main_1;

import com.aluracursos.ejercicio1.menu.MenuPrincipal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Anotación que marca esta clase como una aplicación Spring Boot
@SpringBootApplication
public class Main_1 implements CommandLineRunner {
    // Método principal que se ejecuta al iniciar la aplicación
    public static void main(String[] args) {
        // Ejecuta la aplicación Spring Boot, que inicializa el contexto de la aplicación
        SpringApplication.run(Main_1.class, args);
    }

    // Método 'run' que se ejecuta automáticamente cuando la aplicación inicia
    @Override
    public void run(String... args) throws Exception {
        // Crea una instancia de la clase MenuPrincipal
        MenuPrincipal menu = new MenuPrincipal();
        // Llama al método muestraElMenu() para mostrar el menú de la aplicación
        menu.muestraElMenu();
    }
}