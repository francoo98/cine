package ar.edu.um.programacion2.service.dto;

import java.time.LocalDate;
import java.util.List;

import ar.edu.um.programacion2.domain.Pelicula;
import ar.edu.um.programacion2.domain.Proyeccion;

public class PeliculaDisponibilidadesDTO {
	private Pelicula pelicula;
	private List<LocalDate> fechas;
	private List<Proyeccion> proyecciones;

	public PeliculaDisponibilidadesDTO() {
		super();
	}

	public PeliculaDisponibilidadesDTO(Pelicula pelicula, List<LocalDate> fechas, List<Proyeccion> proyecciones) {
		super();
		this.pelicula = pelicula;
		this.fechas = fechas;
		this.proyecciones = proyecciones;
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
	
	public List<Proyeccion> getProyecciones() {
		return this.proyecciones;
	}
	
	public void setProyecciones(List<Proyeccion> proyecciones) {
		this.proyecciones = proyecciones;
	}
}
