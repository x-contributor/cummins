package com.mx.kiibal.cummins.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Participante.
 */
@Entity
@Table(name = "participante")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Participante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nombre_ac")
    private String nombreAc;

    @OneToMany(mappedBy = "participante")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Proyecto> proyectos = new HashSet<>();

    @OneToOne(mappedBy = "participante")
    @JsonIgnore
    private Contacto contacto;

    @OneToOne(mappedBy = "participante")
    @JsonIgnore
    private ParticipanteDescripcion participanteDescripcion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreAc() {
        return nombreAc;
    }

    public void setNombreAc(String nombreAc) {
        this.nombreAc = nombreAc;
    }

    public Set<Proyecto> getProyectos() {
        return proyectos;
    }

    public void setProyectos(Set<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }

    public Contacto getContacto() {
        return contacto;
    }

    public void setContacto(Contacto contacto) {
        this.contacto = contacto;
    }

    public ParticipanteDescripcion getParticipanteDescripcion() {
        return participanteDescripcion;
    }

    public void setParticipanteDescripcion(ParticipanteDescripcion participanteDescripcion) {
        this.participanteDescripcion = participanteDescripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Participante participante = (Participante) o;
        if(participante.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, participante.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Participante{" +
            "id=" + id +
            ", nombreAc='" + nombreAc + "'" +
            '}';
    }
}
