package com.aluracursos.screenmatch.menu;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class EjemploStreams {

    public void muestraEjemplo(){
        List <String> nombres = Arrays.asList("Diego", "Katherine", "Catalina", "Alejandra");
        nombres.stream()
                .sorted()
                .limit(3)
                .filter(n -> n.startsWith("C"))
                .map(n -> n.toUpperCase(Locale.ROOT))
                .forEach(System.out::println);
    }
}
