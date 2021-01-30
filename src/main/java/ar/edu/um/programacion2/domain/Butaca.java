package ar.edu.um.programacion2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

import ar.edu.um.programacion2.domain.enumeration.EstadosButaca;
import ar.edu.um.programacion2.service.dto.ButacaEstadoDTO;

/**
 * A Butaca.
 */
@Entity
@Table(name = "butaca")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Butaca implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fecha_de_venta", nullable = false)
    private LocalDate fechaDeVenta;

    @NotNull
    @Min(value = 10)
    @Max(value = 15)
    @Column(name = "fila", nullable = false)
    private Integer fila;

    @NotNull
    @Min(value = 10)
    @Max(value = 15)
    @Column(name = "asiento", nullable = false)
    private Integer asiento;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadosButaca estado;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "butacas", allowSetters = true)
    private Proyeccion proyeccion;

    public ButacaEstadoDTO toButacaEstadoDTO() {
    	return new ButacaEstadoDTO(this.estado, this.fila, this.asiento);
    }
    
    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaDeVenta() {
        return fechaDeVenta;
    }

    public Butaca fechaDeVenta(LocalDate fechaDeVenta) {
        this.fechaDeVenta = fechaDeVenta;
        return this;
    }

    public void setFechaDeVenta(LocalDate fechaDeVenta) {
        this.fechaDeVenta = fechaDeVenta;
    }

    public Integer getFila() {
        return fila;
    }

    public Butaca fila(Integer fila) {
        this.fila = fila;
        return this;
    }

    public void setFila(Integer fila) {
        this.fila = fila;
    }

    public Integer getAsiento() {
        return asiento;
    }

    public Butaca asiento(Integer asiento) {
        this.asiento = asiento;
        return this;
    }

    public void setAsiento(Integer asiento) {
        this.asiento = asiento;
    }

    public EstadosButaca getEstado() {
        return estado;
    }

    public Butaca estado(EstadosButaca estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(EstadosButaca estado) {
        this.estado = estado;
    }

    public Proyeccion getProyeccion() {
        return proyeccion;
    }

    public Butaca proyeccion(Proyeccion proyeccion) {
        this.proyeccion = proyeccion;
        return this;
    }

    public void setProyeccion(Proyeccion proyeccion) {
        this.proyeccion = proyeccion;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Butaca)) {
            return false;
        }
        return id != null && id.equals(((Butaca) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Butaca{" +
            "id=" + getId() +
            ", fechaDeVenta='" + getFechaDeVenta() + "'" +
            ", fila=" + getFila() +
            ", asiento=" + getAsiento() +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
