package ar.edu.um.programacion2.repository;

import ar.edu.um.programacion2.domain.Butaca;
import ar.edu.um.programacion2.domain.Proyeccion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the Butaca entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ButacaRepository extends JpaRepository<Butaca, Long> {
	
	int countButacaByProyeccion(Proyeccion proyeccion);

	List<Butaca> findByfechaDeVentaBetween(LocalDateTime inicio, LocalDateTime fin);
	
	List<Butaca> findByfechaDeVentaBetweenAndProyeccionId(LocalDateTime inicio, LocalDateTime fin, Long id_proyeccion);
	
	List<Butaca> findByProyeccionEstadoTrue();
}
