package cibertec.pe.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import cibertec.pe.dto.ResultadoDTO;
import cibertec.pe.entity.Matricula;
import cibertec.pe.feignclient.AsistenciaFeignClient;
import cibertec.pe.feignclient.MatriculaFeignClient;
import cibertec.pe.model.Nota;
import cibertec.pe.repository.INotaRepository;
import feign.FeignException;
import jakarta.jws.WebService;

@WebService
@Component
public class NotaServiceImpl implements INotaService {
	
	@Autowired
	private INotaRepository notaRepository;
	
	@Autowired
	private MatriculaFeignClient matriculaClient;
	
	@Autowired
	private AsistenciaFeignClient asistenciaClient;
	
	@Override
	public List<Nota> listarNotas() {
		return notaRepository.findAll();
	}
	
	@Override
	public Nota crearNota(Nota nota) {
		
		try {
			ResponseEntity<Matricula> response = matriculaClient.obtenerMatricula(nota.getCodMatricula());
			if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
				
				return notaRepository.save(nota);
			} else {
				throw new RuntimeException("Error: La matrícula con código " + nota.getCodMatricula() + " no existe");
			}
		} catch (FeignException.NotFound e) {
			throw new RuntimeException("Error: La matrícula con código " + nota.getCodMatricula() + " no existe");
		} catch (Exception e) {
			throw new RuntimeException("Error al validar la matrícula: " + e.getMessage());
		}
	}
	
	@Override
	public Optional<Nota> buscarNota(int codNota) {
		return notaRepository.findById(codNota);
	}
	
	@Override
	public String editarNota(int codNota, Nota nota) {
		Nota notaExistente = notaRepository.findById(codNota).orElse(null);
		if (notaExistente != null) {
			notaExistente.setTipoEvaluacion(nota.getTipoEvaluacion());
			notaExistente.setNota(nota.getNota());
			notaExistente.setFechaEvaluacion(nota.getFechaEvaluacion());
			notaExistente.setObservaciones(nota.getObservaciones());
			notaRepository.save(notaExistente);
			return "Nota actualizada correctamente";
		} else {
			return "Error: Nota no encontrada";
		}
	}
	
	@Override
	public void eliminarNota(int codNota) {
		notaRepository.deleteById(codNota);
	}
	
	@Override
	public List<Nota> listarNotasPorMatricula(int codMatricula) {
		return notaRepository.findByCodMatricula(codMatricula);
	}
	
	@Override
	public List<Nota> listarNotasPorAlumno(int codAlumno) {
		
		List<Nota> todasLasNotas = notaRepository.findAll();
		List<Nota> notasDelAlumno = new ArrayList<>();
		
		for (Nota nota : todasLasNotas) {
			try {
				ResponseEntity<Matricula> response = matriculaClient.obtenerMatricula(nota.getCodMatricula());
				if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
					Matricula matricula = response.getBody();
					if (matricula.getCodAlumno() == codAlumno) {
						notasDelAlumno.add(nota);
					}
				}
			} catch (Exception e) {
				
			}
		}
		
		return notasDelAlumno;
	}
	
	@Override
	public List<Nota> listarNotasPorCurso(int codCurso) {
		
		List<Nota> todasLasNotas = notaRepository.findAll();
		List<Nota> notasDelCurso = new ArrayList<>();
		
		for (Nota nota : todasLasNotas) {
			try {
				ResponseEntity<Matricula> response = matriculaClient.obtenerMatricula(nota.getCodMatricula());
				if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
					Matricula matricula = response.getBody();
					if (matricula.getCodCurso() == codCurso) {
						notasDelCurso.add(nota);
					}
				}
			} catch (Exception e) {
				
			}
		}
		
		return notasDelCurso;
	}
	
	
	@Override
	public ResultadoDTO calcularResultado(int codMatricula) {

	    List<Nota> notas = notaRepository.findByCodMatricula(codMatricula);

	    double t1 = 0, t2 = 0, ef = 0;

	    for (Nota n : notas) {
	        switch (n.getTipoEvaluacion()) {
	            case "T1": t1 = n.getNota(); break;
	            case "T2": t2 = n.getNota(); break;
	            case "EF": ef = n.getNota(); break;
	        }
	    }

	    double promedio = (t1 * 0.3) + (t2 * 0.3) + (ef * 0.4);

	    
	    double asistencia = 0;
	    try {
	        String response = asistenciaClient.obtenerPorcentaje(codMatricula);
	        asistencia = Double.parseDouble(response.replaceAll("[^0-9.]", ""));
	    } catch (Exception e) {
	        asistencia = 0;
	    }

	    String estado;
	    if (ef < 10) {
	        estado = "DESAPROBADO (EF bajo)";
	    } else if (asistencia < 70) {
	        estado = "DESAPROBADO (inasistencias)";
	    } else if (promedio >= 13) {
	        estado = "APROBADO";
	    } else {
	        estado = "DESAPROBADO";
	    }

	    ResultadoDTO r = new ResultadoDTO();
	    r.setPromedio(promedio);
	    r.setEstado(estado);
	    r.setAsistencia(asistencia);

	    return r;
	}
}