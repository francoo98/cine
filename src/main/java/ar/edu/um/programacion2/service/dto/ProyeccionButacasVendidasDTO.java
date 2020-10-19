package ar.edu.um.programacion2.service.dto;

import ar.edu.um.programacion2.domain.Proyeccion;

// DTO usado en el reporte de proyecciones mas vendidas
public class ProyeccionButacasVendidasDTO {
	private Proyeccion proyeccion;
	private int butacasVendidas;
	
	public ProyeccionButacasVendidasDTO(Proyeccion proyeccion, int butacasVendidas) {
		this.proyeccion = proyeccion;
		this.butacasVendidas = butacasVendidas;
	}
	
	public ProyeccionButacasVendidasDTO() { }

	public Proyeccion getProyeccion() {
		return proyeccion;
	}

	public void setProyeccion(Proyeccion proyeccion) {
		this.proyeccion = proyeccion;
	}

	public int getButacasVendidas() {
		return butacasVendidas;
	}

	public void setButacasVendidas(int butacasVendidas) {
		this.butacasVendidas = butacasVendidas;
	}
}
