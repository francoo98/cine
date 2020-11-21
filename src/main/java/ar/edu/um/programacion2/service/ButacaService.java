package ar.edu.um.programacion2.service;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.stereotype.Service;

import ar.edu.um.programacion2.domain.Butaca;
import ar.edu.um.programacion2.domain.Pelicula;
import ar.edu.um.programacion2.domain.Proyeccion;
import ar.edu.um.programacion2.repository.ButacaRepository;
import ar.edu.um.programacion2.repository.PeliculaRepository;
import ar.edu.um.programacion2.repository.ProyeccionRepository;
import ar.edu.um.programacion2.web.rest.errors.BadRequestAlertException;

@Service
public class ButacaService {

	private static final String ENTITY_NAME = "butaca";
	
	private final ButacaRepository butacaRepository;
	private final ProyeccionRepository proyeccionRepository;
	private final PeliculaRepository peliculaRepository;
	
	public ButacaService(PeliculaRepository peliculaRepository, ProyeccionRepository proyeccionRepository,
						ButacaRepository butacaRepository) {
		this.butacaRepository = butacaRepository;
		this.peliculaRepository = peliculaRepository;
		this.proyeccionRepository = proyeccionRepository;
	}
	
	public Butaca save(Butaca butaca) throws BadRequestAlertException {
		Proyeccion proyeccion = proyeccionRepository.findById(butaca.getProyeccion().getId()).get();
		Pelicula pelicula = proyeccion.getPelicula();
		Boolean isButacaBetween = isDateBetween(butaca.getFechaDeVenta(), 
												LocalDateTime.of(pelicula.getFechaInicio(), LocalTime.MAX), 
												LocalDateTime.of(pelicula.getFechaFin(), LocalTime.MAX));
		if(butaca.getFechaDeVenta().isEqual(proyeccion.getHora()) || !isButacaBetween) {
			throw new BadRequestAlertException("Can't make this today", ENTITY_NAME, "BadDate");
		}
		if(!pelicula.isEstado()) {
			throw new BadRequestAlertException("Pelicula is not active", ENTITY_NAME, "BadPelicula");
		}
		return butacaRepository.save(butaca);
	}
	
	private Boolean isDateBetween(LocalDateTime date, LocalDateTime beggining, LocalDateTime end) {
		return date.isBefore(end) && date.isAfter(beggining);
	}
	
}
