package cibertec.pe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

import cibertec.pe.service.AlumnoImplement;
import jakarta.xml.ws.Endpoint;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class RestAlumnoApplication {

	public static void main(String[] args) {
		
      SpringApplication.run(RestAlumnoApplication.class, args);
		
		
	}
}

