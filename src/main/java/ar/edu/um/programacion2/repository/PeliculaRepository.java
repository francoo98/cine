package ar.edu.um.programacion2.repository;

import ar.edu.um.programacion2.domain.Pelicula;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the Pelicula entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {
	@Query(value = "SELECT p FROM Pelicula p " + "WHERE (?1 < p.fechaFin AND ?2 > p.fechaInicio AND (p.estado = true))")
	List<Pelicula> findAllBetween(LocalDate inicio, LocalDate fin);

}
