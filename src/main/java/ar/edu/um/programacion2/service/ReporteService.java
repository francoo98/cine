package ar.edu.um.programacion2.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import ar.edu.um.programacion2.domain.Proyeccion;
import ar.edu.um.programacion2.repository.ButacaRepository;
import ar.edu.um.programacion2.repository.ProyeccionRepository;
import ar.edu.um.programacion2.service.dto.ProyeccionButacasVendidasDTO;

@Service
public class ReporteService {
	
	final private ProyeccionRepository proyeccionRepository;
	final private ButacaRepository butacaRepository;
	
	public ReporteService(ProyeccionRepository proyeccionRepository, ButacaRepository butacaRepository) {
		this.proyeccionRepository = proyeccionRepository;
		this.butacaRepository = butacaRepository;
	}

	public List<ProyeccionButacasVendidasDTO> getProyeccionesMasVendidasBetween(LocalDate inicio, LocalDate fin) {
		List<Proyeccion> proyecciones  = proyeccionRepository.masVendidas(inicio, fin, PageRequest.of(0, 5)).toList();
		List<ProyeccionButacasVendidasDTO> top5 = new ArrayList<ProyeccionButacasVendidasDTO>();
		Integer cantidad;
		for(Proyeccion proyeccion : proyecciones) {
			cantidad = butacaRepository.countByProyeccionId(proyeccion.getId());
			top5.add(new ProyeccionButacasVendidasDTO(proyeccion, cantidad));
		}
		return top5;
	}
}
