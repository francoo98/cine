package ar.edu.um.programacion2.repository;

import ar.edu.um.programacion2.domain.Pelicula;
import ar.edu.um.programacion2.domain.Proyeccion;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Proyeccion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProyeccionRepository extends JpaRepository<Proyeccion, Long> {
	List<Proyeccion> findAllByEstadoTrue();
	List<Proyeccion> findProyeccionsByPeliculaId(Long id);
	List<Proyeccion> findProyeccionsByFechaFinGreaterThanEqualAndFechaInicioLessThanEqualAndEstadoTrue(LocalDate inicio, LocalDate fin);
	@Query("select p from Proyeccion p where fechaInicio <= ?1 and fechaFin >= ?1")
	List<Proyeccion> findAllByFechaInicioBeforeAndFechaFinAfterAndEstadoTrue(LocalDate hoy);
	/*@Query(value = "select butaca.proyeccion_id, count(butaca.id) as cantidad, "
				 + "proyeccion.id, proyeccion.fecha_inicio, proyeccion.fecha_fin, proyeccion.estado, proyeccion.hora, proyeccion.sala_id, proyeccion.pelicula_id "
				 + "from butaca join proyeccion on proyeccion.id=butaca.proyeccion_id "
				 + "where proyeccion.fecha_fin >= ?1 and proyeccion.fecha_inicio <= ?2 "
				 + "group by butaca.proyeccion_id "
				 + "order by cantidad desc "
				 + "limit 2", nativeQuery = true)*/
	@Query("select p, count(b) as cantidad "
		 + "from Butaca b "
		 + "join Proyeccion p on b.proyeccion = p "
		 + "group by b.proyeccion "
		 + "order by cantidad desc")
	List<Proyeccion> asd(LocalDate inicio, LocalDate fin);
	
}
