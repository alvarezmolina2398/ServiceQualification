/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qualification.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Desarrollo
 */
@Embeddable
public class PromocionCooperativaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "cooperativa_idcooperativa")
    private int cooperativaIdcooperativa;
    @Basic(optional = false)
    @Column(name = "promocion_idpromocion")
    private int promocionIdpromocion;

    public PromocionCooperativaPK() {
    }

    public PromocionCooperativaPK(int cooperativaIdcooperativa, int promocionIdpromocion) {
        this.cooperativaIdcooperativa = cooperativaIdcooperativa;
        this.promocionIdpromocion = promocionIdpromocion;
    }

    public int getCooperativaIdcooperativa() {
        return cooperativaIdcooperativa;
    }

    public void setCooperativaIdcooperativa(int cooperativaIdcooperativa) {
        this.cooperativaIdcooperativa = cooperativaIdcooperativa;
    }

    public int getPromocionIdpromocion() {
        return promocionIdpromocion;
    }

    public void setPromocionIdpromocion(int promocionIdpromocion) {
        this.promocionIdpromocion = promocionIdpromocion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) cooperativaIdcooperativa;
        hash += (int) promocionIdpromocion;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PromocionCooperativaPK)) {
            return false;
        }
        PromocionCooperativaPK other = (PromocionCooperativaPK) object;
        if (this.cooperativaIdcooperativa != other.cooperativaIdcooperativa) {
            return false;
        }
        if (this.promocionIdpromocion != other.promocionIdpromocion) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.qualification.modelo.PromocionCooperativaPK[ cooperativaIdcooperativa=" + cooperativaIdcooperativa + ", promocionIdpromocion=" + promocionIdpromocion + " ]";
    }
    
}
