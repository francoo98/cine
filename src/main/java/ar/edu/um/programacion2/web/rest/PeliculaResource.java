package ar.edu.um.programacion2.web.rest;

import ar.edu.um.programacion2.domain.Pelicula;
import ar.edu.um.programacion2.repository.PeliculaRepository;
import ar.edu.um.programacion2.service.PeliculaService;
import ar.edu.um.programacion2.service.dto.PeliculaDisponibilidadesDTO;
import ar.edu.um.programacion2.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * REST controller for managing {@link ar.edu.um.programacion2.domain.Pelicula}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PeliculaResource {

	private final Logger log = LoggerFactory.getLogger(PeliculaResource.class);

	private static final String ENTITY_NAME = "pelicula";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final PeliculaRepository peliculaRepository;
	private final PeliculaService peliculaService;

	public PeliculaResource(PeliculaRepository peliculaRepository, PeliculaService peliculaService) {
		this.peliculaRepository = peliculaRepository;
		this.peliculaService = peliculaService;
	}

	/**
	 * {@code POST  /peliculas} : Create a new pelicula.
	 *
	 * @param pelicula the pelicula to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new pelicula, or with status {@code 400 (Bad Request)} if
	 *         the pelicula has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/peliculas")
	public ResponseEntity<Pelicula> createPelicula(@Valid @RequestBody Pelicula pelicula) throws URISyntaxException {
		log.debug("REST request to save Pelicula : {}", pelicula);
		if (pelicula.getId() != null) {
			throw new BadRequestAlertException("A new pelicula cannot already have an ID", ENTITY_NAME, "idexists");
		}
		Pelicula result = peliculaRepository.save(pelicula);
		return ResponseEntity
				.created(new URI("/api/peliculas/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /peliculas} : Updates an existing pelicula.
	 *
	 * @param pelicula the pelicula to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated pelicula, or with status {@code 400 (Bad Request)} if the
	 *         pelicula is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the pelicula couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/peliculas")
	public ResponseEntity<Pelicula> updatePelicula(@Valid @RequestBody Pelicula pelicula) throws URISyntaxException {
		log.debug("REST request to update Pelicula : {}", pelicula);
		if (pelicula.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		Pelicula result = peliculaRepository.save(pelicula);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pelicula.getId().toString()))
				.body(result);
	}

	/**
	 * {@code GET  /peliculas} : get all the peliculas.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of peliculas in body.
	 */
	@GetMapping("/peliculas")
	public List<Pelicula> getAllPeliculas() {
		log.debug("REST request to get all Peliculas");
		return peliculaRepository.findAll();
	}

	@GetMapping("/peliculas/{inicio}/{fin}")
	public List<Pelicula> getAllPeliculasBetween(@PathVariable LocalDate inicio, @PathVariable LocalDate fin) {
		log.debug("REST request to get all Peliculas between : {} - {}", inicio, fin);
		return peliculaRepository.findAllByFechaFinAfterAndFechaInicioBeforeAndEstadoTrue(inicio, fin);
	}

	@GetMapping("/peliculas/{id}/{inicio}/{fin}")
	public ResponseEntity<PeliculaDisponibilidadesDTO> getPeliculaAvailabilityBetween(@PathVariable Long id,
			@PathVariable LocalDate inicio, @PathVariable LocalDate fin) {
		log.debug("REST request to get Pelicula availability in : {} - {}", inicio, fin);
		try {
			return new ResponseEntity<PeliculaDisponibilidadesDTO>(
					peliculaService.findPeliculaAvailabilityBetween(id, inicio, fin), HttpStatus.OK);
		} catch (NoSuchElementException NoSuchElement) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * {@code GET  /peliculas/:id} : get the "id" pelicula.
	 *
	 * @param id the id of the pelicula to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the pelicula, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/peliculas/{id}")
	public ResponseEntity<Pelicula> getPelicula(@PathVariable Long id) {
		log.debug("REST request to get Pelicula : {}", id);
		Optional<Pelicula> pelicula = peliculaRepository.findById(id);
		return ResponseUtil.wrapOrNotFound(pelicula);
	}

	/**
	 * {@code DELETE  /peliculas/:id} : delete the "id" pelicula.
	 *
	 * @param id the id of the pelicula to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/peliculas/{id}")
	public ResponseEntity<Void> deletePelicula(@PathVariable Long id) {
		log.debug("REST request to delete Pelicula : {}", id);
		peliculaRepository.deleteById(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}
}
