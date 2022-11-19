package edu.migsw.sueldo;

import javax.annotation.Resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import edu.migsw.sueldo.services.SueldoService;

@SpringBootApplication
@EnableEurekaClient
public class SueldoApplication {

	@Resource
	private SueldoService marcaService;

	public static void main(String[] args) {
		SpringApplication.run(SueldoApplication.class, args);
	}


}
