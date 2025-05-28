package edu.tecnologico.programaweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("edu.tecnologico")
@EnableJpaRepositories("edu.tecnologico.repository")
@EntityScan("edu.tecnologico.model")
public class VentaCatalogoApplication {

	public static void main(String[] args) {
		SpringApplication.run(VentaCatalogoApplication.class, args);
	}
}
