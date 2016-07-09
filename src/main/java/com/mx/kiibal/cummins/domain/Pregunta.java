package com.mx.kiibal.cummins.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.mx.kiibal.cummins.domain.enumeration.TipoPregunta;

/**
 * A Pregunta.
 */
@Entity
@Table(name = "pregunta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pregunta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pregunta")
    private TipoPregunta tipoPregunta;

    @Column(name = "consecutivo")
    private Integer consecutivo;

    @Column(name = "pregunta")
    private String pregunta;

    @Column(name = "ayuda")
    private String ayuda;

    @Column(name = "max")
    private Integer max;

    @Column(name = "min")
    private Integer min;

    @ManyToOne
    private PreguntaSeccion seccion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoPregunta getTipoPregunta() {
        return tipoPregunta;
    }

    public void setTipoPregunta(TipoPregunta tipoPregunta) {
        this.tipoPregunta = tipoPregunta;
    }

    public Integer getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(Integer consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getAyuda() {
        return ayuda;
    }

    public void setAyuda(String ayuda) {
        this.ayuda = ayuda;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public PreguntaSeccion getSeccion() {
        return seccion;
    }

    public void setSeccion(PreguntaSeccion preguntaSeccion) {
        this.seccion = preguntaSeccion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pregunta pregunta = (Pregunta) o;
        if(pregunta.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pregunta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Pregunta{" +
            "id=" + id +
            ", tipoPregunta='" + tipoPregunta + "'" +
            ", consecutivo='" + consecutivo + "'" +
            ", pregunta='" + pregunta + "'" +
            ", ayuda='" + ayuda + "'" +
            ", max='" + max + "'" +
            ", min='" + min + "'" +
            '}';
    }
}
