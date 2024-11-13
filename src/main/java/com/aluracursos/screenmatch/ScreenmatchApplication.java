package com.aluracursos.screenmatch;

import com.aluracursos.screenmatch.menu.MenuPrincipal;
import com.aluracursos.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	// Inyección de dependencias
	@Autowired
	private SerieRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		MenuPrincipal menu = new MenuPrincipal(repository);
		menu.muestraElMenu();
		/*EjemploStreams streams = new EjemploStreams();
		streams.muestraEjemplo();*/
	}
}
