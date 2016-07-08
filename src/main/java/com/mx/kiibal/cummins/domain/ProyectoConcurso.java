package com.mx.kiibal.cummins.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.mx.kiibal.cummins.domain.enumeration.EstatusProyecto;

import com.mx.kiibal.cummins.domain.enumeration.EtapaProyecto;

/**
 * A ProyectoConcurso.
 */
@Entity
@Table(name = "proyecto_concurso")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProyectoConcurso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "estatus")
    private EstatusProyecto estatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "etapa")
    private EtapaProyecto etapa;

    @OneToOne
    @JoinColumn(unique = true)
    private Proyecto proyecto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EstatusProyecto getEstatus() {
        return estatus;
    }

    public void setEstatus(EstatusProyecto estatus) {
        this.estatus = estatus;
    }

    public EtapaProyecto getEtapa() {
        return etapa;
    }

    public void setEtapa(EtapaProyecto etapa) {
        this.etapa = etapa;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProyectoConcurso proyectoConcurso = (ProyectoConcurso) o;
        if(proyectoConcurso.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, proyectoConcurso.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProyectoConcurso{" +
            "id=" + id +
            ", estatus='" + estatus + "'" +
            ", etapa='" + etapa + "'" +
            '}';
    }
}
