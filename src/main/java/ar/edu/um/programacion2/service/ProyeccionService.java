package ar.edu.um.programacion2.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.um.programacion2.domain.Proyeccion;
import ar.edu.um.programacion2.repository.ProyeccionRepository;

@Service
public class ProyeccionService {

	private final ProyeccionRepository proyeccionRepository;
	
	public ProyeccionService(ProyeccionRepository proyeccionRepository) {
		this.proyeccionRepository = proyeccionRepository;
	}
	
	public List<Proyeccion> getAllProyeccionsBetween(LocalDate inicio, LocalDate fin) {
		
		return proyeccionRepository.findProyeccionsByFechaFinGreaterThanEqualAndFechaInicioLessThanEqualAndEstadoTrue(inicio, fin);
	}

}
