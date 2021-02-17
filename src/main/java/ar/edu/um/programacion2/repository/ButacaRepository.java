package ar.edu.um.programacion2.repository;

import ar.edu.um.programacion2.domain.Butaca;
import ar.edu.um.programacion2.domain.Proyeccion;
import ar.edu.um.programacion2.domain.enumeration.EstadosButaca;

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

	List<Butaca> findByfechaDeVentaBetween(LocalDate inicio, LocalDate fin);
	
	List<Butaca> findByfechaDeVentaBetweenAndProyeccionIdAndEstadoEquals(LocalDate inicio, LocalDate fin, Long id_proyeccion, EstadosButaca estado);
	
	List<Butaca> findByProyeccionEstadoTrueAndEstadoIs(EstadosButaca estado);

	List<Butaca> findByProyeccion(Proyeccion proyeccion);

	boolean existsByProyeccionAndFilaAndAsiento(Proyeccion proyeccion, int fila, int asiento);
}
