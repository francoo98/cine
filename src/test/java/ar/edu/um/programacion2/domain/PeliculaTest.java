package ar.edu.um.programacion2.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ar.edu.um.programacion2.web.rest.TestUtil;

public class PeliculaTest {

	@Test
	public void equalsVerifier() throws Exception {
		TestUtil.equalsVerifier(Pelicula.class);
		Pelicula pelicula1 = new Pelicula();
		pelicula1.setId(1L);
		Pelicula pelicula2 = new Pelicula();
		pelicula2.setId(pelicula1.getId());
		assertThat(pelicula1).isEqualTo(pelicula2);
		pelicula2.setId(2L);
		assertThat(pelicula1).isNotEqualTo(pelicula2);
		pelicula1.setId(null);
		assertThat(pelicula1).isNotEqualTo(pelicula2);
	}
}
