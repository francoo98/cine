package ar.edu.um.programacion2.web.rest;

import ar.edu.um.programacion2.domain.Butaca;
import ar.edu.um.programacion2.domain.Pelicula;
import ar.edu.um.programacion2.domain.Proyeccion;
import ar.edu.um.programacion2.repository.ButacaRepository;
import ar.edu.um.programacion2.repository.PeliculaRepository;
import ar.edu.um.programacion2.repository.ProyeccionRepository;
import ar.edu.um.programacion2.service.ButacaService;
import ar.edu.um.programacion2.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

/**
 * REST controller for managing {@link ar.edu.um.programacion2.domain.Butaca}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ButacaResource {

	private final Logger log = LoggerFactory.getLogger(ButacaResource.class);
	private static final String ENTITY_NAME = "butaca";
	@Value("${jhipster.clientApp.name}")
	private String applicationName;
	
	private final ButacaRepository butacaRepository;
	private final ProyeccionRepository proyeccionRepository;
	private final PeliculaRepository peliculaRepository;
	
	private final ButacaService butacaService;

	public ButacaResource(ButacaRepository butacaRepository, ProyeccionRepository proyeccionRepository,
						  ButacaService butacaService, PeliculaRepository peliculaRepository) {
		this.butacaRepository = butacaRepository;
		this.proyeccionRepository = proyeccionRepository;
		this.butacaService = butacaService;
		this.peliculaRepository = peliculaRepository;
	}

	/**
	 * {@code POST  /butacas} : Create a new butaca.
	 *
	 * @param butaca the butaca to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new butaca, or with status {@code 400 (Bad Request)} if the
	 *         butaca has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/butacas")
	public ResponseEntity<Butaca> createButaca(@RequestBody @Valid Butaca butaca) throws URISyntaxException {
		log.debug("REST request to save Butaca : {}", butaca);
		Proyeccion proyeccion;
		Pelicula pelicula;
		LocalDate fechaProyeccion;
		if (butaca.getId() != null) {
			throw new BadRequestAlertException("A new butaca cannot already have an ID", ENTITY_NAME, "idexists");
		}
		if(!proyeccionRepository.existsById(butaca.getProyeccion().getId())) {
			throw new BadRequestAlertException("Proyeccion id doesn't exist", ENTITY_NAME, "proyeccionid");
		}
		proyeccion = proyeccionRepository.findById(butaca.getProyeccion().getId()).get();
		pelicula = proyeccion.getPelicula();
		fechaProyeccion = LocalDate.ofInstant(proyeccion.getHora(), ZoneId.systemDefault());
		if(!(pelicula.getFechaInicio().isBefore(fechaProyeccion) && pelicula.getFechaFin().isAfter(fechaProyeccion)
				|| pelicula.getFechaInicio().isEqual(fechaProyeccion) || pelicula.getFechaFin().isEqual(fechaProyeccion))) {
			throw new BadRequestAlertException("Pelicula is not active", ENTITY_NAME, "non-activepelicula");
		}
		Butaca result = butacaService.save(butaca);
		return ResponseEntity
				.created(new URI("/api/butacas/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /butacas} : Updates an existing butaca.
	 *
	 * @param butaca the butaca to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated butaca, or with status {@code 400 (Bad Request)} if the
	 *         butaca is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the butaca couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/butacas")
	public ResponseEntity<Butaca> updateButaca(@RequestBody @Valid Butaca butaca) throws URISyntaxException {
		log.debug("REST request to update Butaca : {}", butaca);
		if (butaca.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		Butaca result = butacaRepository.save(butaca);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, butaca.getId().toString()))
				.body(result);
	}

	/**
	 * {@code GET  /butacas} : get all the butacas.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of butacas in body.
	 */
	@GetMapping("/butacas")
	public List<Butaca> getAllButacas() {
		log.debug("REST request to get all Butacas");
		return butacaRepository.findAll();
	}

	/**
	 * {@code GET  /butacas/:id} : get the "id" butaca.
	 *
	 * @param id the id of the butaca to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the butaca, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/butacas/{id}")
	public ResponseEntity<Butaca> getButaca(@PathVariable Long id) {
		log.debug("REST request to get Butaca : {}", id);
		Optional<Butaca> butaca = butacaRepository.findById(id);
		return ResponseUtil.wrapOrNotFound(butaca);
	}

	/**
	 * {@code DELETE  /butacas/:id} : delete the "id" butaca.
	 *
	 * @param id the id of the butaca to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/butacas/{id}")
	public ResponseEntity<String> deleteButaca(@PathVariable Long id) {
		log.debug("REST request to delete Butaca : {}", id);
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("DELETE no es soportado para butacas.");
	}
}
