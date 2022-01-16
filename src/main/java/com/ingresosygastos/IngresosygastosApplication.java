package com.ingresosygastos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ingresosygastos.errores.WebException;

@SpringBootApplication
public class IngresosygastosApplication {

	public static void main(String[] args) throws WebException {
		SpringApplication.run(IngresosygastosApplication.class, args);	
	}

}
