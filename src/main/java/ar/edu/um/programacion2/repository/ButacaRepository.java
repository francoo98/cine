package ar.edu.um.programacion2.repository;

import ar.edu.um.programacion2.domain.Butaca;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Butaca entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ButacaRepository extends JpaRepository<Butaca, Long> {
}
