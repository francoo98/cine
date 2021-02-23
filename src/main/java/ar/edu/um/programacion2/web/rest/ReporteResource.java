package ar.edu.um.programacion2.web.rest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.um.programacion2.domain.Butaca;
import ar.edu.um.programacion2.domain.Proyeccion;
import ar.edu.um.programacion2.domain.enumeration.EstadosButaca;
import ar.edu.um.programacion2.repository.ButacaRepository;
import ar.edu.um.programacion2.repository.ProyeccionRepository;
import ar.edu.um.programacion2.service.ReporteService;
import ar.edu.um.programacion2.service.dto.ProyeccionButacasVendidasDTO;

@RestController
@RequestMapping("/api/reportes")
public class ReporteResource {

	final private ReporteService reporteService;

	final private ButacaRepository butacaRepository;
	final private ProyeccionRepository proyeccionRepository;

	public ReporteResource(ButacaRepository butacaRepository, ProyeccionRepository proyeccionRepository,
			ReporteService reporteService) {
		this.butacaRepository = butacaRepository;
		this.proyeccionRepository = proyeccionRepository;
		this.reporteService = reporteService;
	}

	@GetMapping("/butacas_vendidas/{inicio}/{fin}")
	public List<Butaca> getButacasVendidasBetween(@PathVariable LocalDate inicio, @PathVariable LocalDate fin) {
		return butacaRepository.findByfechaDeVentaBetweenAndEstadoIs(inicio, fin, EstadosButaca.Vendida);
	}

	@GetMapping("/butacas_vendidas/{id_proyeccion}/{inicio}/{fin}")
	public List<Butaca> getButacasVendidasDeProyeccionBetween(@PathVariable Long id_proyeccion,
															  @PathVariable LocalDate inicio, 
															  @PathVariable LocalDate fin) {
		return butacaRepository.findByfechaDeVentaBetweenAndProyeccionIdAndEstadoEquals(inicio, fin, id_proyeccion, EstadosButaca.Vendida);
	}

	@GetMapping("/masvendidas/{inicio}/{fin}")
	public List<ProyeccionButacasVendidasDTO> getProyeccionesMasVendidasBetween(@PathVariable LocalDate inicio,
			@PathVariable LocalDate fin) {
		return reporteService.getProyeccionesMasVendidasBetween(inicio, fin);
	}

	@GetMapping("/butacas_vendidas")
	public List<Butaca> getButacasVendidasDeProyeccionesActivas() {
		return butacaRepository.findByPeliculaEstadoTrueAndEstadoIsVendida();
	}
}
