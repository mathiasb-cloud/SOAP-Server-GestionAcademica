package cibertec.pe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

import cibertec.pe.service.NotaServiceImpl;
import jakarta.xml.ws.Endpoint;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class RestNotaApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(RestNotaApplication.class, args);

		NotaServiceImpl service = context.getBean(NotaServiceImpl.class);

		Endpoint.publish("http://localhost:8089/ws/nota", service);
	}
}