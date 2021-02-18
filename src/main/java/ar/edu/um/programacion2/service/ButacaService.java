package ar.edu.um.programacion2.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Autowired
	private ButacaRepository butacaRepository;
	@Autowired
	private ProyeccionRepository proyeccionRepository;
	
	public Butaca save(Butaca butaca) throws BadRequestAlertException {
		Proyeccion proyeccion = proyeccionRepository.findById(butaca.getProyeccion().getId()).get();
		Pelicula pelicula = proyeccion.getPelicula();
		if(butaca.getFechaDeVenta().isEqual(LocalDate.ofInstant(proyeccion.getHora(), ZoneId.systemDefault()))) {
			throw new BadRequestAlertException("Can't make this today", ENTITY_NAME, "BadDate");
		}
		if(!pelicula.isEstado()) {
			throw new BadRequestAlertException("Pelicula is not active", ENTITY_NAME, "BadPelicula");
		}
		return butacaRepository.save(butaca);
	}
}
