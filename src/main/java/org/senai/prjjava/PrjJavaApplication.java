package org.senai.prjjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class PrjJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrjJavaApplication.class, args);
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "nome", defaultValue = "World") String nome) {
	return String.format("Hello %s!", nome);
	}
}
