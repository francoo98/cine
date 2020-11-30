package ar.edu.um.programacion2.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ar.edu.um.programacion2.web.rest.TestUtil;

public class ProyeccionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Proyeccion.class);
        Proyeccion proyeccion1 = new Proyeccion();
        proyeccion1.setId(1L);
        Proyeccion proyeccion2 = new Proyeccion();
        proyeccion2.setId(proyeccion1.getId());
        assertThat(proyeccion1).isEqualTo(proyeccion2);
        proyeccion2.setId(2L);
        assertThat(proyeccion1).isNotEqualTo(proyeccion2);
        proyeccion1.setId(null);
        assertThat(proyeccion1).isNotEqualTo(proyeccion2);
    }
}
