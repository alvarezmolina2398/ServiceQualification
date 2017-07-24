/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qualification.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Desarrollo
 */
@Entity
@Table(name = "parametro_interrogante")
@NamedQueries({
    @NamedQuery(name = "ParametroInterrogante.findAll", query = "SELECT p FROM ParametroInterrogante p")})
public class ParametroInterrogante implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ParametroInterrogantePK parametroInterrogantePK;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @JoinColumn(name = "interrogante_idinterrogante", referencedColumnName = "idinterrogante", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Interrogante interrogante;
    @JoinColumn(name = "parametro_idparametro", referencedColumnName = "idparametro", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Parametro parametro;

    public ParametroInterrogante() {
    }

    public ParametroInterrogante(ParametroInterrogantePK parametroInterrogantePK) {
        this.parametroInterrogantePK = parametroInterrogantePK;
    }

    public ParametroInterrogante(ParametroInterrogantePK parametroInterrogantePK, Date fecha) {
        this.parametroInterrogantePK = parametroInterrogantePK;
        this.fecha = fecha;
    }

    public ParametroInterrogante(int interroganteIdinterrogante, Character parametroIdparametro) {
        this.parametroInterrogantePK = new ParametroInterrogantePK(interroganteIdinterrogante, parametroIdparametro);
    }

    public ParametroInterrogantePK getParametroInterrogantePK() {
        return parametroInterrogantePK;
    }

    public void setParametroInterrogantePK(ParametroInterrogantePK parametroInterrogantePK) {
        this.parametroInterrogantePK = parametroInterrogantePK;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Interrogante getInterrogante() {
        return interrogante;
    }

    public void setInterrogante(Interrogante interrogante) {
        this.interrogante = interrogante;
    }

    public Parametro getParametro() {
        return parametro;
    }

    public void setParametro(Parametro parametro) {
        this.parametro = parametro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (parametroInterrogantePK != null ? parametroInterrogantePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParametroInterrogante)) {
            return false;
        }
        ParametroInterrogante other = (ParametroInterrogante) object;
        if ((this.parametroInterrogantePK == null && other.parametroInterrogantePK != null) || (this.parametroInterrogantePK != null && !this.parametroInterrogantePK.equals(other.parametroInterrogantePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.qualification.modelo.ParametroInterrogante[ parametroInterrogantePK=" + parametroInterrogantePK + " ]";
    }
    
}
