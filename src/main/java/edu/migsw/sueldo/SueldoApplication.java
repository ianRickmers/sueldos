package edu.migsw.sueldo;

import javax.annotation.Resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import edu.migsw.sueldo.services.SueldoService;

@SpringBootApplication
public class SueldoApplication {

	@Resource
	private SueldoService marcaService;

	public static void main(String[] args) {
		SpringApplication.run(SueldoApplication.class, args);
	}


}
