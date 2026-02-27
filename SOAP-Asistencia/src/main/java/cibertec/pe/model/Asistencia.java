package cibertec.pe.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import cibertec.pe.model.EstadoAsistencia;

@Entity
@Table(name = "tbl_asistencia")
public class Asistencia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codAsistencia;
	private Integer codMatricula;
	private String fecha;
	@Enumerated(EnumType.STRING)
	private EstadoAsistencia estado;
	private String observaciones;
	
	public Asistencia() {
	}
	
	public Asistencia(Integer codMatricula, String fecha, EstadoAsistencia estado, String observaciones) {
		this.codMatricula = codMatricula;
		this.fecha = fecha;
		this.estado = estado;
		this.observaciones = observaciones;
	}
	
	public int getCodAsistencia() {
		return codAsistencia;
	}
	
	public void setCodAsistencia(int codAsistencia) {
		this.codAsistencia = codAsistencia;
	}
	
	public Integer getCodMatricula() {
		return codMatricula;
	}
	
	public void setCodMatricula(Integer codMatricula) {
		this.codMatricula = codMatricula;
	}
	
	public String getFecha() {
		return fecha;
	}
	
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	public EstadoAsistencia getEstado() {
	    return estado;
	}

	public void setEstado(EstadoAsistencia estado) {
	    this.estado = estado;
	}
	public String getObservaciones() {
		return observaciones;
	}
	
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
}