package cibertec.pe.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import cibertec.pe.model.Asistencia;

public interface IAsistenciaRepository extends JpaRepository<Asistencia, Integer> {
	List<Asistencia> findByCodMatricula(int codMatricula);
	List<Asistencia> findByFecha(String fecha);
	
	boolean existsByCodMatriculaAndFecha(int codMatricula, String fecha);
}