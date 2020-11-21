package ar.edu.um.programacion2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * A Proyeccion.
 */
@Entity
@Table(name = "proyeccion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Proyeccion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "fecha_inicio")
	private LocalDate fechaInicio;

	@Column(name = "fecha_fin")
	private LocalDate fechaFin;

	@Column(name = "hora")
	private LocalDateTime hora;

	@Column(name = "estado")
	private Boolean estado;

	@OneToOne
	@JoinColumn(unique = true)
	private Sala sala;

	@ManyToOne
	@JsonIgnoreProperties(value = "proyeccions", allowSetters = true)
	private Pelicula pelicula;

	// jhipster-needle-entity-add-field - JHipster will add fields here
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public Proyeccion fechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
		return this;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public Proyeccion fechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
		return this;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	public LocalDateTime getHora() {
		return hora;
	}

	public Proyeccion hora(LocalDateTime hora) {
		this.hora = hora;
		return this;
	}

	public void setHora(LocalDateTime hora) {
		this.hora = hora;
	}

	public Boolean isEstado() {
		return estado;
	}

	public Proyeccion estado(Boolean estado) {
		this.estado = estado;
		return this;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Sala getSala() {
		return sala;
	}

	public Proyeccion sala(Sala sala) {
		this.sala = sala;
		return this;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public Pelicula getPelicula() {
		return pelicula;
	}

	public Proyeccion pelicula(Pelicula pelicula) {
		this.pelicula = pelicula;
		return this;
	}

	public void setPelicula(Pelicula pelicula) {
		this.pelicula = pelicula;
	}
	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Proyeccion)) {
			return false;
		}
		return id != null && id.equals(((Proyeccion) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "Proyeccion{" + "id=" + getId() + ", fechaInicio='" + getFechaInicio() + "'" + ", fechaFin='"
				+ getFechaFin() + "'" + ", hora='" + getHora() + "'" + ", estado='" + isEstado() + "'" + "}";
	}
}
