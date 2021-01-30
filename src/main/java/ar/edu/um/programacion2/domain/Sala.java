package ar.edu.um.programacion2.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import ar.edu.um.programacion2.domain.enumeration.EstadosSala;

/**
 * A Sala.
 */
@Entity
@Table(name = "sala")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Sala implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadosSala estado;

    @NotNull
    @Min(value = 7)
    @Max(value = 10)
    @Column(name = "filas", nullable = false)
    private Integer filas;

    @NotNull
    @Min(value = 8)
    @Max(value = 10)
    @Column(name = "asientos", nullable = false)
    private Integer asientos;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Sala nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public EstadosSala getEstado() {
        return estado;
    }

    public Sala estado(EstadosSala estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(EstadosSala estado) {
        this.estado = estado;
    }

    public Integer getFilas() {
        return filas;
    }

    public Sala filas(Integer filas) {
        this.filas = filas;
        return this;
    }

    public void setFilas(Integer filas) {
        this.filas = filas;
    }

    public Integer getAsientos() {
        return asientos;
    }

    public Sala asientos(Integer asientos) {
        this.asientos = asientos;
        return this;
    }

    public void setAsientos(Integer asientos) {
        this.asientos = asientos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sala)) {
            return false;
        }
        return id != null && id.equals(((Sala) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sala{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", estado='" + getEstado() + "'" +
            ", filas=" + getFilas() +
            ", asientos=" + getAsientos() +
            "}";
    }
}
