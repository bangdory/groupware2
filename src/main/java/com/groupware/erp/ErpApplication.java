package com.groupware.erp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ErpApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ErpApplication.class)
				.properties("spring.config.location=classpath:application.yml") // yml 설정파일을 읽어오는 부분
				.run(args);
		// SpringApplication.run(ErpApplication.class, args);
	}

}
