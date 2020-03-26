package com.qy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.qy.datasource.DynamicDataSourceRegister;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@Import({DynamicDataSourceRegister.class})
public class Application {
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
