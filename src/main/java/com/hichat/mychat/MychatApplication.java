package com.hichat.mychat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class MychatApplication {

	public static void main(String[] args) {
		SpringApplication.run(MychatApplication.class, args);
	}

}
