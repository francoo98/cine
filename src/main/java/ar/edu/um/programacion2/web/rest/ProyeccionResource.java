package ar.edu.um.programacion2.web.rest;

import ar.edu.um.programacion2.domain.Proyeccion;
import ar.edu.um.programacion2.repository.ProyeccionRepository;
import ar.edu.um.programacion2.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link ar.edu.um.programacion2.domain.Proyeccion}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProyeccionResource {

    private final Logger log = LoggerFactory.getLogger(ProyeccionResource.class);

    private static final String ENTITY_NAME = "proyeccion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProyeccionRepository proyeccionRepository;
    
    public ProyeccionResource(ProyeccionRepository proyeccionRepository) {
        this.proyeccionRepository = proyeccionRepository;
    }

    /**
     * {@code POST  /proyeccions} : Create a new proyeccion.
     *
     * @param proyeccion the proyeccion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new proyeccion, or with status {@code 400 (Bad Request)} if the proyeccion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/proyeccions")
    public ResponseEntity<Proyeccion> createProyeccion(@RequestBody Proyeccion proyeccion) throws URISyntaxException {
        log.debug("REST request to save Proyeccion : {}", proyeccion);
        if (proyeccion.getId() != null) {
            throw new BadRequestAlertException("A new proyeccion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Proyeccion result = proyeccionRepository.save(proyeccion);
        return ResponseEntity.created(new URI("/api/proyeccions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /proyeccions} : Updates an existing proyeccion.
     *
     * @param proyeccion the proyeccion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated proyeccion,
     * or with status {@code 400 (Bad Request)} if the proyeccion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the proyeccion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/proyeccions")
    public ResponseEntity<Proyeccion> updateProyeccion(@RequestBody Proyeccion proyeccion) throws URISyntaxException {
        log.debug("REST request to update Proyeccion : {}", proyeccion);
        if (proyeccion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Proyeccion result = proyeccionRepository.save(proyeccion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, proyeccion.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /proyeccions} : get all the proyeccions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of proyeccions in body.
     */
    @GetMapping("/proyeccions")
    public List<Proyeccion> getAllProyeccions() {
        log.debug("REST request to get all Proyeccions");
        return proyeccionRepository.findAll();
    }
    
    @GetMapping("/proyeccions/hoy") // 1
    public List<Proyeccion> getAllProyeccionsActivasHoy() {
    	log.debug("REST request to get all Proyeccions activas");
        return proyeccionRepository.findAllByEstadoTrue();
    }
    
    @GetMapping("/proyeccions/{inicio}/{fin}") // 2
    public List<Proyeccion> getAllProyeccionsBetween(@PathVariable LocalDate inicio, @PathVariable LocalDate fin) {
    	log.debug("REST request to get all Proyeccions between : {} and {}", inicio, fin);
    	return proyeccionRepository.findProyeccionsByFechaFinGreaterThanEqualAndFechaInicioLessThanEqualAndEstadoTrue(inicio, fin);
    }
    
    @GetMapping("/proyeccions/pelicula/{id}")
    public List<Proyeccion> getProyeccionesDePelicula(@PathVariable Long id) {
    	log.debug("REST request to get all Proyeccions of pelicula : {}", id);
    	return proyeccionRepository.findProyeccionsByPeliculaId(id);
    }

    /**
     * {@code GET  /proyeccions/:id} : get the "id" proyeccion.
     *
     * @param id the id of the proyeccion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the proyeccion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/proyeccions/{id}")
    public ResponseEntity<Proyeccion> getProyeccion(@PathVariable Long id) {
        log.debug("REST request to get Proyeccion : {}", id);
        Optional<Proyeccion> proyeccion = proyeccionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(proyeccion);
    }

    /**
     * {@code DELETE  /proyeccions/:id} : delete the "id" proyeccion.
     *
     * @param id the id of the proyeccion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/proyeccions/{id}")
    public ResponseEntity<Void> deleteProyeccion(@PathVariable Long id) {
        log.debug("REST request to delete Proyeccion : {}", id);
        proyeccionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
