package ar.edu.um.programacion2.service.dto;

import ar.edu.um.programacion2.domain.enumeration.EstadosButaca;

public class ButacaEstadoDTO {
	EstadosButaca estado;
	int fila;
	int asiento;
	
	public ButacaEstadoDTO(EstadosButaca estado, int fila, int asiento) {
		super();
		this.estado = estado;
		this.fila = fila;
		this.asiento = asiento;
	}
	
	public EstadosButaca getEstado() {
		return estado;
	}
	
	public void setEstado(EstadosButaca estado) {
		this.estado = estado;
	}
	
	public int getFila() {
		return fila;
	}
	
	public void setFila(int fila) {
		this.fila = fila;
	}
	
	public int getAsiento() {
		return asiento;
	}
	
	public void setAsiento(int asiento) {
		this.asiento = asiento;
	}
}
