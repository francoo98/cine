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
	
	int countButacaByProyeccionAndEstado(Proyeccion proyeccion, EstadosButaca estadoButaca);

	List<Butaca> findByfechaDeVentaBetweenAndEstadoIs(LocalDate inicio, LocalDate fin, EstadosButaca estado);
	
	List<Butaca> findByfechaDeVentaBetweenAndProyeccionIdAndEstadoEquals(LocalDate inicio, LocalDate fin, Long id_proyeccion, EstadosButaca estado);
	
	@Query(value = "select b from Butaca b where estado = 'Vendida' and proyeccion.pelicula.estado = true")
	List<Butaca> findByPeliculaEstadoTrueAndEstadoIsVendida();

	List<Butaca> findByProyeccion(Proyeccion proyeccion);

	boolean existsByProyeccionAndFilaAndAsiento(Proyeccion proyeccion, int fila, int asiento);
}
