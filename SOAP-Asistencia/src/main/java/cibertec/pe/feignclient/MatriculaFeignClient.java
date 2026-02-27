package cibertec.pe.feignclient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import cibertec.pe.entity.Matricula;

@FeignClient(name = "SOAP-Matricula", url = "http://localhost:9005")
public interface MatriculaFeignClient {
	
	@GetMapping("/api/matriculas/{id}")
	ResponseEntity<Matricula> obtenerMatricula(@PathVariable("id") int id);
}