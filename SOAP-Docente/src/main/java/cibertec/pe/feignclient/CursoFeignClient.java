package cibertec.pe.feignclient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import cibertec.pe.entity.Curso;

@FeignClient(name ="REST-CURSO", url= "${feign.cuenta.url}")
public interface CursoFeignClient {
	
	@PostMapping("/api/curso/createCurso")
	Curso crearCurso(@RequestBody Curso curso);
}