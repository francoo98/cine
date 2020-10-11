package ar.edu.um.programacion2.domain.dto;

import java.time.LocalDate;
import java.util.List;

import ar.edu.um.programacion2.domain.Pelicula;

public class PeliculaDisponibilidadesDTO {
	private Pelicula pelicula;
	private List<LocalDate> fechas;
	
	public PeliculaDisponibilidadesDTO() {
		super();
	}
	
	public PeliculaDisponibilidadesDTO(Pelicula pelicula, List<LocalDate> fechas) {
		super();
		this.pelicula = pelicula;
		this.fechas = fechas;
	}
	
	public Pelicula getPelicula() {
		return pelicula;
	}
	
	public void setPelicula(Pelicula pelicula) {
		this.pelicula = pelicula;
	}
	
	public List<LocalDate> getSecuenciaDeFechas() {
		return fechas;
	}
	
	public void setSecuenciaDeFechas(List<LocalDate> fechas) {
		this.fechas = fechas;
	}
}
