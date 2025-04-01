package com.Medical_Recirds2.MEdical;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.Medical_Recirds2.MEdical.service")
public class MEdicalApplication {

	public static void main(String[] args) {
		SpringApplication.run(MEdicalApplication.class, args);
	}

}
