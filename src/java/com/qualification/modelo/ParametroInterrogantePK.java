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
public class ParametroInterrogantePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "interrogante_idinterrogante")
    private int interroganteIdinterrogante;
    @Basic(optional = false)
    @Column(name = "parametro_idparametro")
    private Character parametroIdparametro;

    public ParametroInterrogantePK() {
    }

    public ParametroInterrogantePK(int interroganteIdinterrogante, Character parametroIdparametro) {
        this.interroganteIdinterrogante = interroganteIdinterrogante;
        this.parametroIdparametro = parametroIdparametro;
    }

    public int getInterroganteIdinterrogante() {
        return interroganteIdinterrogante;
    }

    public void setInterroganteIdinterrogante(int interroganteIdinterrogante) {
        this.interroganteIdinterrogante = interroganteIdinterrogante;
    }

    public Character getParametroIdparametro() {
        return parametroIdparametro;
    }

    public void setParametroIdparametro(Character parametroIdparametro) {
        this.parametroIdparametro = parametroIdparametro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) interroganteIdinterrogante;
        hash += (parametroIdparametro != null ? parametroIdparametro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParametroInterrogantePK)) {
            return false;
        }
        ParametroInterrogantePK other = (ParametroInterrogantePK) object;
        if (this.interroganteIdinterrogante != other.interroganteIdinterrogante) {
            return false;
        }
        if ((this.parametroIdparametro == null && other.parametroIdparametro != null) || (this.parametroIdparametro != null && !this.parametroIdparametro.equals(other.parametroIdparametro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.qualification.modelo.ParametroInterrogantePK[ interroganteIdinterrogante=" + interroganteIdinterrogante + ", parametroIdparametro=" + parametroIdparametro + " ]";
    }
    
}
