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
@Table(name = "promocion_cooperativa")
@NamedQueries({
    @NamedQuery(name = "PromocionCooperativa.findAll", query = "SELECT p FROM PromocionCooperativa p")})
public class PromocionCooperativa implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PromocionCooperativaPK promocionCooperativaPK;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @JoinColumn(name = "cooperativa_idcooperativa", referencedColumnName = "idcooperativa", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cooperativa cooperativa;
    @JoinColumn(name = "promocion_idpromocion", referencedColumnName = "idpromocion", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Promocion promocion;

    public PromocionCooperativa() {
    }

    public PromocionCooperativa(PromocionCooperativaPK promocionCooperativaPK) {
        this.promocionCooperativaPK = promocionCooperativaPK;
    }

    public PromocionCooperativa(PromocionCooperativaPK promocionCooperativaPK, Date fecha) {
        this.promocionCooperativaPK = promocionCooperativaPK;
        this.fecha = fecha;
    }

    public PromocionCooperativa(int cooperativaIdcooperativa, int promocionIdpromocion) {
        this.promocionCooperativaPK = new PromocionCooperativaPK(cooperativaIdcooperativa, promocionIdpromocion);
    }

    public PromocionCooperativaPK getPromocionCooperativaPK() {
        return promocionCooperativaPK;
    }

    public void setPromocionCooperativaPK(PromocionCooperativaPK promocionCooperativaPK) {
        this.promocionCooperativaPK = promocionCooperativaPK;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Cooperativa getCooperativa() {
        return cooperativa;
    }

    public void setCooperativa(Cooperativa cooperativa) {
        this.cooperativa = cooperativa;
    }

    public Promocion getPromocion() {
        return promocion;
    }

    public void setPromocion(Promocion promocion) {
        this.promocion = promocion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (promocionCooperativaPK != null ? promocionCooperativaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PromocionCooperativa)) {
            return false;
        }
        PromocionCooperativa other = (PromocionCooperativa) object;
        if ((this.promocionCooperativaPK == null && other.promocionCooperativaPK != null) || (this.promocionCooperativaPK != null && !this.promocionCooperativaPK.equals(other.promocionCooperativaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.qualification.modelo.PromocionCooperativa[ promocionCooperativaPK=" + promocionCooperativaPK + " ]";
    }
    
}
