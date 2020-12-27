package com.clone.fccreddit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FccredditApplication {

	public static void main(String[] args) {
		SpringApplication.run(FccredditApplication.class, args);
	}

}
