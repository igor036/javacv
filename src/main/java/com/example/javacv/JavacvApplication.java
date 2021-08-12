package com.example.javacv;

import com.example.javacv.service.OCRService;
import com.example.javacv.service.OpenCvService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class JavacvApplication implements CommandLineRunner {

	@Autowired
	private OpenCvService openCvService;

	@Autowired
	private OCRService oCRService;

	public static void main(String[] args) {
		SpringApplication.run(JavacvApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// static tests
	}
}