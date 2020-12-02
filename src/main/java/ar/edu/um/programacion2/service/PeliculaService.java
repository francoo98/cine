package ar.edu.um.programacion2.service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import ar.edu.um.programacion2.domain.Pelicula;
import ar.edu.um.programacion2.repository.PeliculaRepository;
import ar.edu.um.programacion2.service.dto.PeliculaDisponibilidadesDTO;

@Service
public class PeliculaService {

	private final PeliculaRepository peliculaRespository;

	public PeliculaService(PeliculaRepository peliculaRepository) {
		this.peliculaRespository = peliculaRepository;
	}

	public PeliculaDisponibilidadesDTO findPeliculaAvailabilityBetween(Long id, LocalDate inicio, LocalDate fin)
			throws NoSuchElementException {
		Pelicula pelicula = peliculaRespository.findById(id).orElseThrow();
		Stream<LocalDate> fechasDisponibles = inicio.datesUntil(fin.plusDays(1));
		List<LocalDate> fechas = fechasDisponibles.filter(new Predicate<LocalDate>() {
			@Override
			public boolean test(LocalDate t) {
				LocalDate fin = pelicula.getFechaFin().plusDays(1);
				LocalDate inicio = pelicula.getFechaInicio().minusDays(1);
				return t.isBefore(fin) && t.isAfter(inicio);
			}
		}).collect(Collectors.toList());
		return new PeliculaDisponibilidadesDTO(pelicula, fechas);
	}
}
