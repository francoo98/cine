package ar.edu.um.programacion2.service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.um.programacion2.domain.Pelicula;
import ar.edu.um.programacion2.domain.Proyeccion;
import ar.edu.um.programacion2.repository.PeliculaRepository;
import ar.edu.um.programacion2.repository.ProyeccionRepository;
import ar.edu.um.programacion2.service.dto.PeliculaDisponibilidadesDTO;

@Service
public class PeliculaService {

	private final Logger log = LoggerFactory.getLogger(PeliculaService.class);
	
	@Autowired
	private PeliculaRepository peliculaRespository;
	@Autowired
	private ProyeccionRepository proyeccionRepository;

	public PeliculaDisponibilidadesDTO findPeliculaAvailabilityBetween(Long id, LocalDate inicio, LocalDate fin)
			throws NoSuchElementException {
		Pelicula pelicula = peliculaRespository.findById(id).orElseThrow();
		List<Proyeccion> proyecciones = null;
		Stream<LocalDate> fechasDisponibles = inicio.datesUntil(fin.plusDays(1));
		List<LocalDate> fechas = fechasDisponibles.filter(new Predicate<LocalDate>() {
			@Override
			public boolean test(LocalDate t) {
				LocalDate fin = pelicula.getFechaFin().plusDays(1);
				LocalDate inicio = pelicula.getFechaInicio().minusDays(1);
				return t.isBefore(fin) && t.isAfter(inicio);
			}
		}).collect(Collectors.toList());
		
		proyecciones = proyeccionRepository.findProyeccionsByPeliculaAndFechaFinGreaterThanEqualAndFechaInicioLessThanEqualAndEstadoTrue(pelicula, fechas.get(0).minusDays(1L), fechas.get(fechas.size() - 1).plusDays(1L));
		log.debug("Proyecciones: " + proyecciones);
		
		return new PeliculaDisponibilidadesDTO(pelicula, fechas, proyecciones);
	}
}
