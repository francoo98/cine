package ar.edu.um.programacion2.web.rest;

import ar.edu.um.programacion2.CineApp;
import ar.edu.um.programacion2.domain.Pelicula;
import ar.edu.um.programacion2.repository.PeliculaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PeliculaResource} REST controller.
 */
@SpringBootTest(classes = CineApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PeliculaResourceIT {

	private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
	private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

	private static final String DEFAULT_DESCRIPTCION = "AAAAAAAAAA";
	private static final String UPDATED_DESCRIPTCION = "BBBBBBBBBB";

	private static final String DEFAULT_DETALLE = "AAAAAAAAAA";
	private static final String UPDATED_DETALLE = "BBBBBBBBBB";

	private static final Integer DEFAULT_DURACION = 0;
	private static final Integer UPDATED_DURACION = 1;

	private static final String DEFAULT_GENERO = "AAAAAAAAAA";
	private static final String UPDATED_GENERO = "BBBBBBBBBB";

	private static final String DEFAULT_CLASIFICACION = "AAAAAAAAAA";
	private static final String UPDATED_CLASIFICACION = "BBBBBBBBBB";

	private static final Boolean DEFAULT_ESTADO = false;
	private static final Boolean UPDATED_ESTADO = true;

	private static final LocalDate DEFAULT_FECHA_INICIO = LocalDate.ofEpochDay(0L);
	private static final LocalDate UPDATED_FECHA_INICIO = LocalDate.now(ZoneId.systemDefault());

	private static final LocalDate DEFAULT_FECHA_FIN = LocalDate.ofEpochDay(0L);
	private static final LocalDate UPDATED_FECHA_FIN = LocalDate.now(ZoneId.systemDefault());

	@Autowired
	private PeliculaRepository peliculaRepository;

	@Autowired
	private EntityManager em;

	@Autowired
	private MockMvc restPeliculaMockMvc;

	private Pelicula pelicula;

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Pelicula createEntity(EntityManager em) {
		Pelicula pelicula = new Pelicula().nombre(DEFAULT_NOMBRE).descripcion(DEFAULT_DESCRIPTCION)
				.detalle(DEFAULT_DETALLE).duracion(DEFAULT_DURACION).genero(DEFAULT_GENERO)
				.clasificacion(DEFAULT_CLASIFICACION).estado(DEFAULT_ESTADO).fechaInicio(DEFAULT_FECHA_INICIO)
				.fechaFin(DEFAULT_FECHA_FIN);
		return pelicula;
	}

	/**
	 * Create an updated entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Pelicula createUpdatedEntity(EntityManager em) {
		Pelicula pelicula = new Pelicula().nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPTCION)
				.detalle(UPDATED_DETALLE).duracion(UPDATED_DURACION).genero(UPDATED_GENERO)
				.clasificacion(UPDATED_CLASIFICACION).estado(UPDATED_ESTADO).fechaInicio(UPDATED_FECHA_INICIO)
				.fechaFin(UPDATED_FECHA_FIN);
		return pelicula;
	}

	@BeforeEach
	public void initTest() {
		pelicula = createEntity(em);
	}

	@Test
	@Transactional
	public void createPelicula() throws Exception {
		int databaseSizeBeforeCreate = peliculaRepository.findAll().size();
		// Create the Pelicula
		restPeliculaMockMvc.perform(post("/api/peliculas").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(pelicula))).andExpect(status().isCreated());

		// Validate the Pelicula in the database
		List<Pelicula> peliculaList = peliculaRepository.findAll();
		assertThat(peliculaList).hasSize(databaseSizeBeforeCreate + 1);
		Pelicula testPelicula = peliculaList.get(peliculaList.size() - 1);
		assertThat(testPelicula.getNombre()).isEqualTo(DEFAULT_NOMBRE);
		assertThat(testPelicula.getDescripcion()).isEqualTo(DEFAULT_DESCRIPTCION);
		assertThat(testPelicula.getDetalle()).isEqualTo(DEFAULT_DETALLE);
		assertThat(testPelicula.getDuracion()).isEqualTo(DEFAULT_DURACION);
		assertThat(testPelicula.getGenero()).isEqualTo(DEFAULT_GENERO);
		assertThat(testPelicula.getClasificacion()).isEqualTo(DEFAULT_CLASIFICACION);
		assertThat(testPelicula.isEstado()).isEqualTo(DEFAULT_ESTADO);
		assertThat(testPelicula.getFechaInicio()).isEqualTo(DEFAULT_FECHA_INICIO);
		assertThat(testPelicula.getFechaFin()).isEqualTo(DEFAULT_FECHA_FIN);
	}

	@Test
	@Transactional
	public void createPeliculaWithExistingId() throws Exception {
		int databaseSizeBeforeCreate = peliculaRepository.findAll().size();

		// Create the Pelicula with an existing ID
		pelicula.setId(1L);

		// An entity with an existing ID cannot be created, so this API call must fail
		restPeliculaMockMvc.perform(post("/api/peliculas").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(pelicula))).andExpect(status().isBadRequest());

		// Validate the Pelicula in the database
		List<Pelicula> peliculaList = peliculaRepository.findAll();
		assertThat(peliculaList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	@Transactional
	public void getAllPeliculas() throws Exception {
		// Initialize the database
		peliculaRepository.saveAndFlush(pelicula);

		// Get all the peliculaList
		restPeliculaMockMvc.perform(get("/api/peliculas?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(pelicula.getId().intValue())))
				.andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
				.andExpect(jsonPath("$.[*].descriptcion").value(hasItem(DEFAULT_DESCRIPTCION)))
				.andExpect(jsonPath("$.[*].detalle").value(hasItem(DEFAULT_DETALLE)))
				.andExpect(jsonPath("$.[*].duracion").value(hasItem(DEFAULT_DURACION)))
				.andExpect(jsonPath("$.[*].genero").value(hasItem(DEFAULT_GENERO)))
				.andExpect(jsonPath("$.[*].clasificacion").value(hasItem(DEFAULT_CLASIFICACION)))
				.andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())))
				.andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(DEFAULT_FECHA_INICIO.toString())))
				.andExpect(jsonPath("$.[*].fechaFin").value(hasItem(DEFAULT_FECHA_FIN.toString())));
	}

	@Test
	@Transactional
	public void getPelicula() throws Exception {
		// Initialize the database
		peliculaRepository.saveAndFlush(pelicula);

		// Get the pelicula
		restPeliculaMockMvc.perform(get("/api/peliculas/{id}", pelicula.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(pelicula.getId().intValue()))
				.andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
				.andExpect(jsonPath("$.descriptcion").value(DEFAULT_DESCRIPTCION))
				.andExpect(jsonPath("$.detalle").value(DEFAULT_DETALLE))
				.andExpect(jsonPath("$.duracion").value(DEFAULT_DURACION))
				.andExpect(jsonPath("$.genero").value(DEFAULT_GENERO))
				.andExpect(jsonPath("$.clasificacion").value(DEFAULT_CLASIFICACION))
				.andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()))
				.andExpect(jsonPath("$.fechaInicio").value(DEFAULT_FECHA_INICIO.toString()))
				.andExpect(jsonPath("$.fechaFin").value(DEFAULT_FECHA_FIN.toString()));
	}

	@Test
	@Transactional
	public void getNonExistingPelicula() throws Exception {
		// Get the pelicula
		restPeliculaMockMvc.perform(get("/api/peliculas/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void updatePelicula() throws Exception {
		// Initialize the database
		peliculaRepository.saveAndFlush(pelicula);

		int databaseSizeBeforeUpdate = peliculaRepository.findAll().size();

		// Update the pelicula
		Pelicula updatedPelicula = peliculaRepository.findById(pelicula.getId()).get();
		// Disconnect from session so that the updates on updatedPelicula are not
		// directly saved in db
		em.detach(updatedPelicula);
		updatedPelicula.nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPTCION).detalle(UPDATED_DETALLE)
				.duracion(UPDATED_DURACION).genero(UPDATED_GENERO).clasificacion(UPDATED_CLASIFICACION)
				.estado(UPDATED_ESTADO).fechaInicio(UPDATED_FECHA_INICIO).fechaFin(UPDATED_FECHA_FIN);

		restPeliculaMockMvc.perform(put("/api/peliculas").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(updatedPelicula))).andExpect(status().isOk());

		// Validate the Pelicula in the database
		List<Pelicula> peliculaList = peliculaRepository.findAll();
		assertThat(peliculaList).hasSize(databaseSizeBeforeUpdate);
		Pelicula testPelicula = peliculaList.get(peliculaList.size() - 1);
		assertThat(testPelicula.getNombre()).isEqualTo(UPDATED_NOMBRE);
		assertThat(testPelicula.getDescripcion()).isEqualTo(UPDATED_DESCRIPTCION);
		assertThat(testPelicula.getDetalle()).isEqualTo(UPDATED_DETALLE);
		assertThat(testPelicula.getDuracion()).isEqualTo(UPDATED_DURACION);
		assertThat(testPelicula.getGenero()).isEqualTo(UPDATED_GENERO);
		assertThat(testPelicula.getClasificacion()).isEqualTo(UPDATED_CLASIFICACION);
		assertThat(testPelicula.isEstado()).isEqualTo(UPDATED_ESTADO);
		assertThat(testPelicula.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
		assertThat(testPelicula.getFechaFin()).isEqualTo(UPDATED_FECHA_FIN);
	}

	@Test
	@Transactional
	public void updateNonExistingPelicula() throws Exception {
		int databaseSizeBeforeUpdate = peliculaRepository.findAll().size();

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restPeliculaMockMvc.perform(put("/api/peliculas").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(pelicula))).andExpect(status().isBadRequest());

		// Validate the Pelicula in the database
		List<Pelicula> peliculaList = peliculaRepository.findAll();
		assertThat(peliculaList).hasSize(databaseSizeBeforeUpdate);
	}

	@Test
	@Transactional
	public void deletePelicula() throws Exception {
		// Initialize the database
		peliculaRepository.saveAndFlush(pelicula);

		int databaseSizeBeforeDelete = peliculaRepository.findAll().size();

		// Delete the pelicula
		restPeliculaMockMvc.perform(delete("/api/peliculas/{id}", pelicula.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		// Validate the database contains one less item
		List<Pelicula> peliculaList = peliculaRepository.findAll();
		assertThat(peliculaList).hasSize(databaseSizeBeforeDelete - 1);
	}
}
