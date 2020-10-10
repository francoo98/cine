package ar.edu.um.programacion2.service;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import ar.edu.um.programacion2.domain.Pelicula;
import ar.edu.um.programacion2.repository.PeliculaRepository;

@Service
public class PeliculaService {
	
	private final PeliculaRepository peliculaRespository;
	
	public PeliculaService(PeliculaRepository peliculaRepository) {
		this.peliculaRespository = peliculaRepository;
	}
	
	public String findPeliculaAvailabilityBetween(Long id, LocalDate inicio, LocalDate fin)
	throws NoSuchElementException, JSONException 
	{
		JSONObject jsonRespuesta = new JSONObject();
		Pelicula pelicula = peliculaRespository.findById(id).orElseThrow();
		Stream<LocalDate> secuenciaDeFechas = inicio.datesUntil(fin.plusDays(1));
		secuenciaDeFechas.filter(new Predicate<LocalDate>() {
			@Override
			public boolean test(LocalDate t) {
				return t.isBefore(pelicula.getFechaFin()) && t.isAfter(pelicula.getFechaInicio()); 
			}
		});
		jsonRespuesta.put("Pelicula", pelicula);
		jsonRespuesta.put("Disponibilidad", secuenciaDeFechas);
		return jsonRespuesta.toString(4);
	}
}
