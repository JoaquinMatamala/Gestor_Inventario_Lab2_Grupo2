package com.example.Lab1TBD.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sql2o.Sql2o;

@Configuration
public class DatabaseContext {
    @Bean
    // Configuración básica de la base de datos
    // Procura colocar tus datos para conectarla con tu bases de datos local
    public Sql2o sql2o() {
        return new Sql2o("jdbc:postgresql://localhost:5432/Lab2TBD", "postgres", "admin");
    }
}
