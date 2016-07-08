package com.mx.kiibal.cummins.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ParticipanteDescripcion.
 */
@Entity
@Table(name = "participante_descripcion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ParticipanteDescripcion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "mision_vision")
    private String misionVision;

    @Column(name = "anios_operacion")
    private Integer aniosOperacion;

    @Column(name = "programas_servcios")
    private String programasServcios;

    @Column(name = "resultados_logros")
    private String resultadosLogros;

    @Column(name = "numero_beneficiarios")
    private Integer numeroBeneficiarios;

    @Column(name = "comentarios")
    private String comentarios;

    @Lob
    @Column(name = "logotipo")
    private byte[] logotipo;

    @Column(name = "logotipo_content_type")
    private String logotipoContentType;

    @OneToOne
    @JoinColumn(unique = true)
    private Participante participante;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMisionVision() {
        return misionVision;
    }

    public void setMisionVision(String misionVision) {
        this.misionVision = misionVision;
    }

    public Integer getAniosOperacion() {
        return aniosOperacion;
    }

    public void setAniosOperacion(Integer aniosOperacion) {
        this.aniosOperacion = aniosOperacion;
    }

    public String getProgramasServcios() {
        return programasServcios;
    }

    public void setProgramasServcios(String programasServcios) {
        this.programasServcios = programasServcios;
    }

    public String getResultadosLogros() {
        return resultadosLogros;
    }

    public void setResultadosLogros(String resultadosLogros) {
        this.resultadosLogros = resultadosLogros;
    }

    public Integer getNumeroBeneficiarios() {
        return numeroBeneficiarios;
    }

    public void setNumeroBeneficiarios(Integer numeroBeneficiarios) {
        this.numeroBeneficiarios = numeroBeneficiarios;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public byte[] getLogotipo() {
        return logotipo;
    }

    public void setLogotipo(byte[] logotipo) {
        this.logotipo = logotipo;
    }

    public String getLogotipoContentType() {
        return logotipoContentType;
    }

    public void setLogotipoContentType(String logotipoContentType) {
        this.logotipoContentType = logotipoContentType;
    }

    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ParticipanteDescripcion participanteDescripcion = (ParticipanteDescripcion) o;
        if(participanteDescripcion.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, participanteDescripcion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ParticipanteDescripcion{" +
            "id=" + id +
            ", misionVision='" + misionVision + "'" +
            ", aniosOperacion='" + aniosOperacion + "'" +
            ", programasServcios='" + programasServcios + "'" +
            ", resultadosLogros='" + resultadosLogros + "'" +
            ", numeroBeneficiarios='" + numeroBeneficiarios + "'" +
            ", comentarios='" + comentarios + "'" +
            ", logotipo='" + logotipo + "'" +
            ", logotipoContentType='" + logotipoContentType + "'" +
            '}';
    }
}
