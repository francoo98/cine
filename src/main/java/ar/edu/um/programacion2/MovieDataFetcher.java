package ar.edu.um.programacion2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ar.edu.um.programacion2.domain.Pelicula;
import ar.edu.um.programacion2.repository.PeliculaRepository;

@Component
public class MovieDataFetcher implements CommandLineRunner {

	private HttpURLConnection con;
	private JSONArray movies;
	@Autowired
	private PeliculaRepository peliculaRepository;
	
	
	public void run(String... args) throws Exception {
		try {
			this.con = this.connect();
			this.movies = this.getMovies();
			this.savePeliculas();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private JSONArray getMovies() throws IOException {
		StringBuilder content;
		JSONArray response;
		try(BufferedReader input = new BufferedReader(new InputStreamReader(this.con.getInputStream()))) {
		    String line;
		    content = new StringBuilder();
		    while ((line = input.readLine()) != null) {
		        content.append(line);
		        content.append(System.lineSeparator());
		    }
		    response = new JSONArray(content.toString());
		}
		return response;
	}

	private HttpURLConnection connect() throws MalformedURLException, IOException {
		URL url = new URL("http://localhost:8000/api/pelicula/");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setConnectTimeout(1000);
		con.setReadTimeout(1000);
		return con;
	}
	
	private void savePeliculas() {
		JSONObject peliculaJson = null;
		Iterator<Object> i = this.movies.iterator();
		while(i.hasNext()) {
			Pelicula p = new Pelicula();
			peliculaJson = (JSONObject) i.next();
			p.setNombre(peliculaJson.getString("nombre"));
			p.setClasificacion(peliculaJson.getString("clasificacion"));
			p.setDescripcion(peliculaJson.getString("descripcion"));
			p.setDetalle(peliculaJson.getString("detalle"));
			p.setDuracion(peliculaJson.getInt("duracion"));
			p.setEstado(true);
			p.setFechaFin(LocalDate.of(2021, 06, 01));
			p.setFechaInicio(LocalDate.of(2021, 01, 01));
			p.setGenero(peliculaJson.getString("genero"));
			peliculaRepository.save(p);
		}

	}
	
}
