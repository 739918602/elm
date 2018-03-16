package com.zys.elm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class ElmApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElmApplication.class, args);
	}
}
