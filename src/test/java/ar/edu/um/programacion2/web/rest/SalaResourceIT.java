package ar.edu.um.programacion2.web.rest;

import ar.edu.um.programacion2.CineApp;
import ar.edu.um.programacion2.domain.Sala;
import ar.edu.um.programacion2.repository.SalaRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.edu.um.programacion2.domain.enumeration.EstadosSala;

/**
 * Integration tests for the {@link SalaResource} REST controller.
 */
@SpringBootTest(classes = CineApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SalaResourceIT {

	private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
	private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

	private static final EstadosSala DEFAULT_ESTADO = EstadosSala.Habilitada;
	private static final EstadosSala UPDATED_ESTADO = EstadosSala.Deshabilitada;

	private static final Integer DEFAULT_FILAS = 1;
	private static final Integer UPDATED_FILAS = 2;

	private static final Integer DEFAULT_ASIENTOS = 1;
	private static final Integer UPDATED_ASIENTOS = 2;

	@Autowired
	private SalaRepository salaRepository;

	@Autowired
	private EntityManager em;

	@Autowired
	private MockMvc restSalaMockMvc;

	private Sala sala;

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Sala createEntity(EntityManager em) {
		Sala sala = new Sala().nombre(DEFAULT_NOMBRE).estado(DEFAULT_ESTADO).filas(DEFAULT_FILAS)
				.asientos(DEFAULT_ASIENTOS);
		return sala;
	}

	/**
	 * Create an updated entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Sala createUpdatedEntity(EntityManager em) {
		Sala sala = new Sala().nombre(UPDATED_NOMBRE).estado(UPDATED_ESTADO).filas(UPDATED_FILAS)
				.asientos(UPDATED_ASIENTOS);
		return sala;
	}

	@BeforeEach
	public void initTest() {
		sala = createEntity(em);
	}

	@Test
	@Transactional
	public void createSala() throws Exception {
		int databaseSizeBeforeCreate = salaRepository.findAll().size();
		// Create the Sala
		restSalaMockMvc.perform(post("/api/salas").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(sala))).andExpect(status().isCreated());

		// Validate the Sala in the database
		List<Sala> salaList = salaRepository.findAll();
		assertThat(salaList).hasSize(databaseSizeBeforeCreate + 1);
		Sala testSala = salaList.get(salaList.size() - 1);
		assertThat(testSala.getNombre()).isEqualTo(DEFAULT_NOMBRE);
		assertThat(testSala.getEstado()).isEqualTo(DEFAULT_ESTADO);
		assertThat(testSala.getFilas()).isEqualTo(DEFAULT_FILAS);
		assertThat(testSala.getAsientos()).isEqualTo(DEFAULT_ASIENTOS);
	}

	@Test
	@Transactional
	public void createSalaWithExistingId() throws Exception {
		int databaseSizeBeforeCreate = salaRepository.findAll().size();

		// Create the Sala with an existing ID
		sala.setId(1L);

		// An entity with an existing ID cannot be created, so this API call must fail
		restSalaMockMvc.perform(post("/api/salas").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(sala))).andExpect(status().isBadRequest());

		// Validate the Sala in the database
		List<Sala> salaList = salaRepository.findAll();
		assertThat(salaList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	@Transactional
	public void getAllSalas() throws Exception {
		// Initialize the database
		salaRepository.saveAndFlush(sala);

		// Get all the salaList
		restSalaMockMvc.perform(get("/api/salas?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(sala.getId().intValue())))
				.andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
				.andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
				.andExpect(jsonPath("$.[*].filas").value(hasItem(DEFAULT_FILAS)))
				.andExpect(jsonPath("$.[*].asientos").value(hasItem(DEFAULT_ASIENTOS)));
	}

	@Test
	@Transactional
	public void getSala() throws Exception {
		// Initialize the database
		salaRepository.saveAndFlush(sala);

		// Get the sala
		restSalaMockMvc.perform(get("/api/salas/{id}", sala.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(sala.getId().intValue()))
				.andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
				.andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
				.andExpect(jsonPath("$.filas").value(DEFAULT_FILAS))
				.andExpect(jsonPath("$.asientos").value(DEFAULT_ASIENTOS));
	}

	@Test
	@Transactional
	public void getNonExistingSala() throws Exception {
		// Get the sala
		restSalaMockMvc.perform(get("/api/salas/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void updateSala() throws Exception {
		// Initialize the database
		salaRepository.saveAndFlush(sala);

		int databaseSizeBeforeUpdate = salaRepository.findAll().size();

		// Update the sala
		Sala updatedSala = salaRepository.findById(sala.getId()).get();
		// Disconnect from session so that the updates on updatedSala are not directly
		// saved in db
		em.detach(updatedSala);
		updatedSala.nombre(UPDATED_NOMBRE).estado(UPDATED_ESTADO).filas(UPDATED_FILAS).asientos(UPDATED_ASIENTOS);

		restSalaMockMvc.perform(put("/api/salas").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(updatedSala))).andExpect(status().isOk());

		// Validate the Sala in the database
		List<Sala> salaList = salaRepository.findAll();
		assertThat(salaList).hasSize(databaseSizeBeforeUpdate);
		Sala testSala = salaList.get(salaList.size() - 1);
		assertThat(testSala.getNombre()).isEqualTo(UPDATED_NOMBRE);
		assertThat(testSala.getEstado()).isEqualTo(UPDATED_ESTADO);
		assertThat(testSala.getFilas()).isEqualTo(UPDATED_FILAS);
		assertThat(testSala.getAsientos()).isEqualTo(UPDATED_ASIENTOS);
	}

	@Test
	@Transactional
	public void updateNonExistingSala() throws Exception {
		int databaseSizeBeforeUpdate = salaRepository.findAll().size();

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restSalaMockMvc.perform(put("/api/salas").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(sala))).andExpect(status().isBadRequest());

		// Validate the Sala in the database
		List<Sala> salaList = salaRepository.findAll();
		assertThat(salaList).hasSize(databaseSizeBeforeUpdate);
	}

	@Test
	@Transactional
	public void deleteSala() throws Exception {
		// Initialize the database
		salaRepository.saveAndFlush(sala);

		int databaseSizeBeforeDelete = salaRepository.findAll().size();

		// Delete the sala
		restSalaMockMvc.perform(delete("/api/salas/{id}", sala.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		// Validate the database contains one less item
		List<Sala> salaList = salaRepository.findAll();
		assertThat(salaList).hasSize(databaseSizeBeforeDelete - 1);
	}
}
