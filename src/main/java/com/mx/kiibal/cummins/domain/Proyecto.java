package com.mx.kiibal.cummins.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.mx.kiibal.cummins.domain.enumeration.Tematica;

import com.mx.kiibal.cummins.domain.enumeration.TipoProyecto;

/**
 * A Proyecto.
 */
@Entity
@Table(name = "proyecto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Proyecto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "proyecto")
    private String proyecto;

    @Enumerated(EnumType.STRING)
    @Column(name = "tematica")
    private Tematica tematica;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_proyectto")
    private TipoProyecto tipoProyectto;

    @ManyToOne
    private Participante participante;

    @ManyToOne
    private VoluntarioVisitador visitador;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProyecto() {
        return proyecto;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    public Tematica getTematica() {
        return tematica;
    }

    public void setTematica(Tematica tematica) {
        this.tematica = tematica;
    }

    public TipoProyecto getTipoProyectto() {
        return tipoProyectto;
    }

    public void setTipoProyectto(TipoProyecto tipoProyectto) {
        this.tipoProyectto = tipoProyectto;
    }

    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }

    public VoluntarioVisitador getVisitador() {
        return visitador;
    }

    public void setVisitador(VoluntarioVisitador voluntarioVisitador) {
        this.visitador = voluntarioVisitador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Proyecto proyecto = (Proyecto) o;
        if(proyecto.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, proyecto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Proyecto{" +
            "id=" + id +
            ", proyecto='" + proyecto + "'" +
            ", tematica='" + tematica + "'" +
            ", tipoProyectto='" + tipoProyectto + "'" +
            '}';
    }
}
