package ar.edu.um.programacion2.web.rest;

import ar.edu.um.programacion2.CineApp;
import ar.edu.um.programacion2.domain.Butaca;
import ar.edu.um.programacion2.domain.Proyeccion;
import ar.edu.um.programacion2.repository.ButacaRepository;

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

import ar.edu.um.programacion2.domain.enumeration.EstadosButaca;
/**
 * Integration tests for the {@link ButacaResource} REST controller.
 */
@SpringBootTest(classes = CineApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ButacaResourceIT {

    private static final LocalDate DEFAULT_FECHA_DE_VENTA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_DE_VENTA = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_FILA = 1;
    private static final Integer UPDATED_FILA = 2;

    private static final Integer DEFAULT_ASIENTO = 1;
    private static final Integer UPDATED_ASIENTO = 2;

    private static final EstadosButaca DEFAULT_ESTADO = EstadosButaca.Reservada;
    private static final EstadosButaca UPDATED_ESTADO = EstadosButaca.Vendida;

    @Autowired
    private ButacaRepository butacaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restButacaMockMvc;

    private Butaca butaca;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Butaca createEntity(EntityManager em) {
        Butaca butaca = new Butaca()
            .fechaDeVenta(DEFAULT_FECHA_DE_VENTA)
            .fila(DEFAULT_FILA)
            .asiento(DEFAULT_ASIENTO)
            .estado(DEFAULT_ESTADO);
        // Add required entity
        Proyeccion proyeccion;
        if (TestUtil.findAll(em, Proyeccion.class).isEmpty()) {
            proyeccion = ProyeccionResourceIT.createEntity(em);
            em.persist(proyeccion);
            em.flush();
        } else {
            proyeccion = TestUtil.findAll(em, Proyeccion.class).get(0);
        }
        butaca.setProyeccion(proyeccion);
        return butaca;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Butaca createUpdatedEntity(EntityManager em) {
        Butaca butaca = new Butaca()
            .fechaDeVenta(UPDATED_FECHA_DE_VENTA)
            .fila(UPDATED_FILA)
            .asiento(UPDATED_ASIENTO)
            .estado(UPDATED_ESTADO);
        // Add required entity
        Proyeccion proyeccion;
        if (TestUtil.findAll(em, Proyeccion.class).isEmpty()) {
            proyeccion = ProyeccionResourceIT.createUpdatedEntity(em);
            em.persist(proyeccion);
            em.flush();
        } else {
            proyeccion = TestUtil.findAll(em, Proyeccion.class).get(0);
        }
        butaca.setProyeccion(proyeccion);
        return butaca;
    }

    @BeforeEach
    public void initTest() {
        butaca = createEntity(em);
    }

    @Test
    @Transactional
    public void createButaca() throws Exception {
        int databaseSizeBeforeCreate = butacaRepository.findAll().size();
        // Create the Butaca
        restButacaMockMvc.perform(post("/api/butacas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(butaca)))
            .andExpect(status().isCreated());

        // Validate the Butaca in the database
        List<Butaca> butacaList = butacaRepository.findAll();
        assertThat(butacaList).hasSize(databaseSizeBeforeCreate + 1);
        Butaca testButaca = butacaList.get(butacaList.size() - 1);
        assertThat(testButaca.getFechaDeVenta()).isEqualTo(DEFAULT_FECHA_DE_VENTA);
        assertThat(testButaca.getFila()).isEqualTo(DEFAULT_FILA);
        assertThat(testButaca.getAsiento()).isEqualTo(DEFAULT_ASIENTO);
        assertThat(testButaca.getEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createButacaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = butacaRepository.findAll().size();

        // Create the Butaca with an existing ID
        butaca.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restButacaMockMvc.perform(post("/api/butacas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(butaca)))
            .andExpect(status().isBadRequest());

        // Validate the Butaca in the database
        List<Butaca> butacaList = butacaRepository.findAll();
        assertThat(butacaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFechaDeVentaIsRequired() throws Exception {
        int databaseSizeBeforeTest = butacaRepository.findAll().size();
        // set the field null
        butaca.setFechaDeVenta(null);

        // Create the Butaca, which fails.


        restButacaMockMvc.perform(post("/api/butacas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(butaca)))
            .andExpect(status().isBadRequest());

        List<Butaca> butacaList = butacaRepository.findAll();
        assertThat(butacaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFilaIsRequired() throws Exception {
        int databaseSizeBeforeTest = butacaRepository.findAll().size();
        // set the field null
        butaca.setFila(null);

        // Create the Butaca, which fails.


        restButacaMockMvc.perform(post("/api/butacas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(butaca)))
            .andExpect(status().isBadRequest());

        List<Butaca> butacaList = butacaRepository.findAll();
        assertThat(butacaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAsientoIsRequired() throws Exception {
        int databaseSizeBeforeTest = butacaRepository.findAll().size();
        // set the field null
        butaca.setAsiento(null);

        // Create the Butaca, which fails.


        restButacaMockMvc.perform(post("/api/butacas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(butaca)))
            .andExpect(status().isBadRequest());

        List<Butaca> butacaList = butacaRepository.findAll();
        assertThat(butacaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = butacaRepository.findAll().size();
        // set the field null
        butaca.setEstado(null);

        // Create the Butaca, which fails.


        restButacaMockMvc.perform(post("/api/butacas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(butaca)))
            .andExpect(status().isBadRequest());

        List<Butaca> butacaList = butacaRepository.findAll();
        assertThat(butacaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllButacas() throws Exception {
        // Initialize the database
        butacaRepository.saveAndFlush(butaca);

        // Get all the butacaList
        restButacaMockMvc.perform(get("/api/butacas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(butaca.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaDeVenta").value(hasItem(DEFAULT_FECHA_DE_VENTA.toString())))
            .andExpect(jsonPath("$.[*].fila").value(hasItem(DEFAULT_FILA)))
            .andExpect(jsonPath("$.[*].asiento").value(hasItem(DEFAULT_ASIENTO)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }
    
    @Test
    @Transactional
    public void getButaca() throws Exception {
        // Initialize the database
        butacaRepository.saveAndFlush(butaca);

        // Get the butaca
        restButacaMockMvc.perform(get("/api/butacas/{id}", butaca.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(butaca.getId().intValue()))
            .andExpect(jsonPath("$.fechaDeVenta").value(DEFAULT_FECHA_DE_VENTA.toString()))
            .andExpect(jsonPath("$.fila").value(DEFAULT_FILA))
            .andExpect(jsonPath("$.asiento").value(DEFAULT_ASIENTO))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingButaca() throws Exception {
        // Get the butaca
        restButacaMockMvc.perform(get("/api/butacas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateButaca() throws Exception {
        // Initialize the database
        butacaRepository.saveAndFlush(butaca);

        int databaseSizeBeforeUpdate = butacaRepository.findAll().size();

        // Update the butaca
        Butaca updatedButaca = butacaRepository.findById(butaca.getId()).get();
        // Disconnect from session so that the updates on updatedButaca are not directly saved in db
        em.detach(updatedButaca);
        updatedButaca
            .fechaDeVenta(UPDATED_FECHA_DE_VENTA)
            .fila(UPDATED_FILA)
            .asiento(UPDATED_ASIENTO)
            .estado(UPDATED_ESTADO);

        restButacaMockMvc.perform(put("/api/butacas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedButaca)))
            .andExpect(status().isOk());

        // Validate the Butaca in the database
        List<Butaca> butacaList = butacaRepository.findAll();
        assertThat(butacaList).hasSize(databaseSizeBeforeUpdate);
        Butaca testButaca = butacaList.get(butacaList.size() - 1);
        assertThat(testButaca.getFechaDeVenta()).isEqualTo(UPDATED_FECHA_DE_VENTA);
        assertThat(testButaca.getFila()).isEqualTo(UPDATED_FILA);
        assertThat(testButaca.getAsiento()).isEqualTo(UPDATED_ASIENTO);
        assertThat(testButaca.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingButaca() throws Exception {
        int databaseSizeBeforeUpdate = butacaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restButacaMockMvc.perform(put("/api/butacas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(butaca)))
            .andExpect(status().isBadRequest());

        // Validate the Butaca in the database
        List<Butaca> butacaList = butacaRepository.findAll();
        assertThat(butacaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteButaca() throws Exception {
        // Initialize the database
        butacaRepository.saveAndFlush(butaca);

        int databaseSizeBeforeDelete = butacaRepository.findAll().size();

        // Delete the butaca
        restButacaMockMvc.perform(delete("/api/butacas/{id}", butaca.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Butaca> butacaList = butacaRepository.findAll();
        assertThat(butacaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
