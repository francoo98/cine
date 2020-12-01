package ar.edu.um.programacion2.repository;

import ar.edu.um.programacion2.domain.Pelicula;
import ar.edu.um.programacion2.domain.Proyeccion;

import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the Proyeccion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProyeccionRepository extends JpaRepository<Proyeccion, Long> {
	List<Proyeccion> findAllByEstadoTrue();

	List<Proyeccion> findProyeccionsByPeliculaId(Long id);

	List<Proyeccion> findProyeccionsByFechaFinGreaterThanEqualAndFechaInicioLessThanEqualAndEstadoTrue(LocalDate inicio,
																									   LocalDate fin);
	
	@Query("select p from Proyeccion p where curdate() between fechaInicio and fechaFin and estado = True")
	List<Proyeccion> findProyeccionsActiveToday();
	
	@Query("select p from Proyeccion p where fechaInicio <= ?1 and fechaFin >= ?1")
	List<Proyeccion> findAllByFechaInicioBeforeAndFechaFinAfterAndEstadoTrue(LocalDate hoy);

	@Query("select p from Proyeccion p join Butaca b on b.proyeccion = p "
		 + "where p.fechaInicio < ?2 and p.fechaFin > ?1 "
		 + "group by b.proyeccion order by count(b) desc")
	Page<Proyeccion> masVendidas(LocalDate inicio, LocalDate fin, Pageable pageable);

	@Query("select p from Proyeccion p where pelicula.id = ?1 and ?2 between fechaInicio and fechaFin and estado = True")
	Optional<List<Proyeccion>> findProyeccionsByPeliculaIdAndFechaInicioBeforeAndFechaFinAfter(Long pelicula_id,
																						 LocalDate fecha);

}
