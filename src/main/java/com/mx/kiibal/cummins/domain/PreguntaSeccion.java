package com.mx.kiibal.cummins.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.mx.kiibal.cummins.domain.enumeration.TipoProyecto;

/**
 * A PreguntaSeccion.
 */
@Entity
@Table(name = "pregunta_seccion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PreguntaSeccion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "pregunta_titulo")
    private String preguntaTitulo;

    @Column(name = "consecutivo_seccion")
    private Integer consecutivoSeccion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_proyecto")
    private TipoProyecto tipoProyecto;

    @Column(name = "minimo_respuesta")
    private Integer minimoRespuesta;

    @Column(name = "maximo_respuesta")
    private Integer maximoRespuesta;

    @OneToMany(mappedBy = "seccion")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Pregunta> preguntas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPreguntaTitulo() {
        return preguntaTitulo;
    }

    public void setPreguntaTitulo(String preguntaTitulo) {
        this.preguntaTitulo = preguntaTitulo;
    }

    public Integer getConsecutivoSeccion() {
        return consecutivoSeccion;
    }

    public void setConsecutivoSeccion(Integer consecutivoSeccion) {
        this.consecutivoSeccion = consecutivoSeccion;
    }

    public TipoProyecto getTipoProyecto() {
        return tipoProyecto;
    }

    public void setTipoProyecto(TipoProyecto tipoProyecto) {
        this.tipoProyecto = tipoProyecto;
    }

    public Integer getMinimoRespuesta() {
        return minimoRespuesta;
    }

    public void setMinimoRespuesta(Integer minimoRespuesta) {
        this.minimoRespuesta = minimoRespuesta;
    }

    public Integer getMaximoRespuesta() {
        return maximoRespuesta;
    }

    public void setMaximoRespuesta(Integer maximoRespuesta) {
        this.maximoRespuesta = maximoRespuesta;
    }

    public Set<Pregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(Set<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PreguntaSeccion preguntaSeccion = (PreguntaSeccion) o;
        if(preguntaSeccion.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, preguntaSeccion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PreguntaSeccion{" +
            "id=" + id +
            ", preguntaTitulo='" + preguntaTitulo + "'" +
            ", consecutivoSeccion='" + consecutivoSeccion + "'" +
            ", tipoProyecto='" + tipoProyecto + "'" +
            ", minimoRespuesta='" + minimoRespuesta + "'" +
            ", maximoRespuesta='" + maximoRespuesta + "'" +
            '}';
    }
}
