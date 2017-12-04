package com.example.ri_test.test_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:svc-context.xml")
public class TestTestApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(TestTestApplication.class, args);

	}
}
