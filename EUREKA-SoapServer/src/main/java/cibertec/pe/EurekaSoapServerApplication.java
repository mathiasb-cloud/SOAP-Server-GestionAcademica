package cibertec.pe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaSoapServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaSoapServerApplication.class, args);
	}

}
