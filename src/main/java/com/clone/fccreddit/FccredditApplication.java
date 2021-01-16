package com.clone.fccreddit;

import com.clone.fccreddit.config.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration.class)
public class FccredditApplication {

	public static void main(String[] args) {
		SpringApplication.run(FccredditApplication.class, args);
	}

}
