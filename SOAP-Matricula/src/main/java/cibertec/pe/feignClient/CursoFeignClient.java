package cibertec.pe.feignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "SOAP-Curso", url = "{feign.cuenta.url}")
public interface CursoFeignClient {
    
    @GetMapping("/api/curso/{id}")
    Object obtenerCurso(@PathVariable("id") int id);
}