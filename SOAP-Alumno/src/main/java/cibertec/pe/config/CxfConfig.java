package cibertec.pe.config;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cibertec.pe.security.GlobalApiKeyInterceptor;
import cibertec.pe.service.AlumnoImplement;
import jakarta.xml.ws.Endpoint;

@Configuration
public class CxfConfig {

    private final Bus bus;
    private final AlumnoImplement alumnoService;
    private final GlobalApiKeyInterceptor interceptor;

    public CxfConfig(Bus bus, AlumnoImplement alumnoService, GlobalApiKeyInterceptor interceptor) {
        this.bus = bus;
        this.alumnoService = alumnoService;
        this.interceptor = interceptor;
    }

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, alumnoService);
        endpoint.getInInterceptors().add(interceptor);
        endpoint.publish("/Alumno");
        return endpoint;
    }
}