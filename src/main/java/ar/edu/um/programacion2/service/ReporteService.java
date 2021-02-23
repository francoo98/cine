package ar.edu.um.programacion2.service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import ar.edu.um.programacion2.domain.Proyeccion;
import ar.edu.um.programacion2.domain.enumeration.EstadosButaca;
import ar.edu.um.programacion2.repository.ButacaRepository;
import ar.edu.um.programacion2.repository.ProyeccionRepository;
import ar.edu.um.programacion2.service.dto.ProyeccionButacasVendidasDTO;
import ar.edu.um.programacion2.web.rest.ProyeccionResource;

@Service
public class ReporteService {

	private final Logger log = LoggerFactory.getLogger(ProyeccionResource.class);

	final private ProyeccionRepository proyeccionRepository;
	final private ButacaRepository butacaRepository;

	public ReporteService(ProyeccionRepository proyeccionRepository, ButacaRepository butacaRepository) {
		this.proyeccionRepository = proyeccionRepository;
		this.butacaRepository = butacaRepository;
	}

	public List<ProyeccionButacasVendidasDTO> getProyeccionesMasVendidasBetween(LocalDate inicio, LocalDate fin) {
		List<Proyeccion> proyecciones = proyeccionRepository.masVendidas(inicio, fin, PageRequest.of(0, 5)).toList();
		List<ProyeccionButacasVendidasDTO> top5 = new LinkedList<ProyeccionButacasVendidasDTO>();
		int butacasVendidas = 0;
		if (proyecciones.size() > 0) {
			for (Proyeccion pr : proyecciones) {
				butacasVendidas = butacaRepository.countButacaByProyeccionAndEstado(pr, EstadosButaca.Vendida);
				ProyeccionButacasVendidasDTO p = new ProyeccionButacasVendidasDTO(pr, butacasVendidas);
				top5.add(p);
			}
		}
		return top5;
	}
}