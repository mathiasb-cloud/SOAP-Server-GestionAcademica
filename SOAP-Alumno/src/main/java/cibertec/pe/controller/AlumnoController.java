package cibertec.pe.controller;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cibertec.pe.entity.Cuenta;
import cibertec.pe.model.Alumno;
import cibertec.pe.model.AlumnoConCuentaRequest;
import cibertec.pe.service.IAlumnoService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/alumno")
public class AlumnoController {

	@Autowired
	private IAlumnoService alumnoService;

	@GetMapping("/listAllAlumnos")
	public List<Alumno> listAllAlumnos(){
		return alumnoService.listarAlumnos();
	}

	@PostMapping("/createAlumno")
	public Alumno createAlumno(@RequestBody Alumno alumno) {
		return alumnoService.crearAlumno(alumno);
	}

	@PutMapping("/editAlumnos/{codigo}")
	public String editAlumno(@PathVariable int codigo, @RequestBody Alumno alumno) {
		return alumnoService.editarAlumno(codigo, alumno);
	}

	@PostMapping("/createAlumnoConCuenta")
	public Cuenta createAlumnoConCuenta(@RequestBody AlumnoConCuentaRequest request) {
		return alumnoService.crearAlumnoConCuenta(request.getAlumno(), request.getCuenta());
	}
	
	@DeleteMapping("/deleteAlumno/{codigo}")
	public String deleteAlumno(@PathVariable int codigo) {
		alumnoService.eliminarAlumno(codigo);
		return "Alumno y su cuenta eliminados correctamente";
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Alumno> getAlumnoById(@PathVariable int id) {
	    Alumno alumno = alumnoService.buscarAlumno(id);
	    if (alumno != null) {
	        return ResponseEntity.ok(alumno);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
}