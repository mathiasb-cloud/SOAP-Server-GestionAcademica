package cibertec.pe.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "SOAP-Asistencia", url = "${feign.cuenta.url}")
public interface AsistenciaFeignClient {

    @GetMapping("/api/asistencias/porcentaje/{codMatricula}")
    String obtenerPorcentaje(@PathVariable("codMatricula") int codMatricula);
}