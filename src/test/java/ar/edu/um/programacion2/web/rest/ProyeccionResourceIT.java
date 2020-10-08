package ar.edu.um.programacion2.web.rest;

import ar.edu.um.programacion2.CineApp;
import ar.edu.um.programacion2.domain.Proyeccion;
import ar.edu.um.programacion2.repository.ProyeccionRepository;

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
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProyeccionResource} REST controller.
 */
@SpringBootTest(classes = CineApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProyeccionResourceIT {

    private static final LocalDate DEFAULT_FECHA_INICIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_INICIO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_HORA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HORA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    @Autowired
    private ProyeccionRepository proyeccionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProyeccionMockMvc;

    private Proyeccion proyeccion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Proyeccion createEntity(EntityManager em) {
        Proyeccion proyeccion = new Proyeccion()
            .fechaInicio(DEFAULT_FECHA_INICIO)
            .fechaFin(DEFAULT_FECHA_FIN)
            .hora(DEFAULT_HORA)
            .estado(DEFAULT_ESTADO);
        return proyeccion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Proyeccion createUpdatedEntity(EntityManager em) {
        Proyeccion proyeccion = new Proyeccion()
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .hora(UPDATED_HORA)
            .estado(UPDATED_ESTADO);
        return proyeccion;
    }

    @BeforeEach
    public void initTest() {
        proyeccion = createEntity(em);
    }

    @Test
    @Transactional
    public void createProyeccion() throws Exception {
        int databaseSizeBeforeCreate = proyeccionRepository.findAll().size();
        // Create the Proyeccion
        restProyeccionMockMvc.perform(post("/api/proyeccions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(proyeccion)))
            .andExpect(status().isCreated());

        // Validate the Proyeccion in the database
        List<Proyeccion> proyeccionList = proyeccionRepository.findAll();
        assertThat(proyeccionList).hasSize(databaseSizeBeforeCreate + 1);
        Proyeccion testProyeccion = proyeccionList.get(proyeccionList.size() - 1);
        assertThat(testProyeccion.getFechaInicio()).isEqualTo(DEFAULT_FECHA_INICIO);
        assertThat(testProyeccion.getFechaFin()).isEqualTo(DEFAULT_FECHA_FIN);
        assertThat(testProyeccion.getHora()).isEqualTo(DEFAULT_HORA);
        assertThat(testProyeccion.isEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createProyeccionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = proyeccionRepository.findAll().size();

        // Create the Proyeccion with an existing ID
        proyeccion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProyeccionMockMvc.perform(post("/api/proyeccions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(proyeccion)))
            .andExpect(status().isBadRequest());

        // Validate the Proyeccion in the database
        List<Proyeccion> proyeccionList = proyeccionRepository.findAll();
        assertThat(proyeccionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProyeccions() throws Exception {
        // Initialize the database
        proyeccionRepository.saveAndFlush(proyeccion);

        // Get all the proyeccionList
        restProyeccionMockMvc.perform(get("/api/proyeccions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proyeccion.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(DEFAULT_FECHA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fechaFin").value(hasItem(DEFAULT_FECHA_FIN.toString())))
            .andExpect(jsonPath("$.[*].hora").value(hasItem(DEFAULT_HORA.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getProyeccion() throws Exception {
        // Initialize the database
        proyeccionRepository.saveAndFlush(proyeccion);

        // Get the proyeccion
        restProyeccionMockMvc.perform(get("/api/proyeccions/{id}", proyeccion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(proyeccion.getId().intValue()))
            .andExpect(jsonPath("$.fechaInicio").value(DEFAULT_FECHA_INICIO.toString()))
            .andExpect(jsonPath("$.fechaFin").value(DEFAULT_FECHA_FIN.toString()))
            .andExpect(jsonPath("$.hora").value(DEFAULT_HORA.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingProyeccion() throws Exception {
        // Get the proyeccion
        restProyeccionMockMvc.perform(get("/api/proyeccions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProyeccion() throws Exception {
        // Initialize the database
        proyeccionRepository.saveAndFlush(proyeccion);

        int databaseSizeBeforeUpdate = proyeccionRepository.findAll().size();

        // Update the proyeccion
        Proyeccion updatedProyeccion = proyeccionRepository.findById(proyeccion.getId()).get();
        // Disconnect from session so that the updates on updatedProyeccion are not directly saved in db
        em.detach(updatedProyeccion);
        updatedProyeccion
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .hora(UPDATED_HORA)
            .estado(UPDATED_ESTADO);

        restProyeccionMockMvc.perform(put("/api/proyeccions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProyeccion)))
            .andExpect(status().isOk());

        // Validate the Proyeccion in the database
        List<Proyeccion> proyeccionList = proyeccionRepository.findAll();
        assertThat(proyeccionList).hasSize(databaseSizeBeforeUpdate);
        Proyeccion testProyeccion = proyeccionList.get(proyeccionList.size() - 1);
        assertThat(testProyeccion.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testProyeccion.getFechaFin()).isEqualTo(UPDATED_FECHA_FIN);
        assertThat(testProyeccion.getHora()).isEqualTo(UPDATED_HORA);
        assertThat(testProyeccion.isEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingProyeccion() throws Exception {
        int databaseSizeBeforeUpdate = proyeccionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProyeccionMockMvc.perform(put("/api/proyeccions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(proyeccion)))
            .andExpect(status().isBadRequest());

        // Validate the Proyeccion in the database
        List<Proyeccion> proyeccionList = proyeccionRepository.findAll();
        assertThat(proyeccionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProyeccion() throws Exception {
        // Initialize the database
        proyeccionRepository.saveAndFlush(proyeccion);

        int databaseSizeBeforeDelete = proyeccionRepository.findAll().size();

        // Delete the proyeccion
        restProyeccionMockMvc.perform(delete("/api/proyeccions/{id}", proyeccion.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Proyeccion> proyeccionList = proyeccionRepository.findAll();
        assertThat(proyeccionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
