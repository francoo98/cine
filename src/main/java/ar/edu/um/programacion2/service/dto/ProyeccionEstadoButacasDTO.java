package ar.edu.um.programacion2.service.dto;

import java.util.List;

import ar.edu.um.programacion2.domain.Proyeccion;

public class ProyeccionEstadoButacasDTO {
	Proyeccion proyeccion;
	List<ButacaEstadoDTO> butacas;
	
	public ProyeccionEstadoButacasDTO(Proyeccion proyeccion, List<ButacaEstadoDTO> butacas) {
		this.proyeccion = proyeccion;
		this.butacas = butacas;
	}

	public Proyeccion getProyeccion() {
		return proyeccion;
	}

	public void setProyeccion(Proyeccion proyeccion) {
		this.proyeccion = proyeccion;
	}

	public List<ButacaEstadoDTO> getButacas() {
		return butacas;
	}

	public void setButacas(List<ButacaEstadoDTO> butacas) {
		this.butacas = butacas;
	}
	
}
