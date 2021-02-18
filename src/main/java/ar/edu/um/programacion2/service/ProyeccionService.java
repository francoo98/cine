package ar.edu.um.programacion2.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.um.programacion2.domain.Butaca;
import ar.edu.um.programacion2.domain.Proyeccion;
import ar.edu.um.programacion2.domain.Sala;
import ar.edu.um.programacion2.domain.enumeration.EstadosButaca;
import ar.edu.um.programacion2.repository.ButacaRepository;
import ar.edu.um.programacion2.repository.ProyeccionRepository;
import ar.edu.um.programacion2.service.dto.ButacaEstadoDTO;
import ar.edu.um.programacion2.service.dto.ProyeccionEstadoButacasDTO;

@Service
public class ProyeccionService {

	private final Logger log = LoggerFactory.getLogger(ProyeccionService.class);
	
	@Autowired
	private ProyeccionRepository proyeccionRepository;
	@Autowired
	private ButacaRepository butacaRepository;
	
	public List<ProyeccionEstadoButacasDTO> getProyeccionDePelicula(Long peliculaID, LocalDate dia) {
		List<ProyeccionEstadoButacasDTO> estados = new ArrayList<ProyeccionEstadoButacasDTO>();
		Optional<List<Proyeccion>> proyecciones = proyeccionRepository.
												  findProyeccionsByPeliculaIdAndFechaInicioBeforeAndFechaFinAfter(
												  peliculaID, dia);
		List<Butaca> butacasOcupadas;
		List<ButacaEstadoDTO> estadosButacas = new ArrayList<ButacaEstadoDTO>();
		Sala sala;
		if(proyecciones.isPresent()) {
			for(Proyeccion proyeccion : proyecciones.get()) {
				sala = proyeccion.getSala();
				butacasOcupadas = butacaRepository.findByProyeccion(proyeccion);
				for(Butaca butaca : butacasOcupadas) {
					estadosButacas.add(butaca.toButacaEstadoDTO());
				}
				
				for(int fila = 1; fila < sala.getFilas(); fila++) {
					for(int asiento = 1; asiento < sala.getAsientos(); asiento++) {
						if(!this.butacaRepository.existsByProyeccionAndFilaAndAsiento(proyeccion, fila, asiento)) {
							estadosButacas.add(new ButacaEstadoDTO(EstadosButaca.Libre, fila, asiento));
						}
					}
				}
				
				estados.add(new ProyeccionEstadoButacasDTO(proyeccion, estadosButacas));
			}
		}
		return estados;
	}	
}
