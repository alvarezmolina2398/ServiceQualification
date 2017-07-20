/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qualification.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Desarrollo
 */
@Entity
@Table(name = "asociado")
@NamedQueries({
    @NamedQuery(name = "Asociado.findAll", query = "SELECT a FROM Asociado a")})
public class Asociado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "numero_telefono")
    private String numeroTelefono;
    @Column(name = "nombre")
    private String nombre;

    public Asociado() {
    }

    public Asociado(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numeroTelefono != null ? numeroTelefono.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Asociado)) {
            return false;
        }
        Asociado other = (Asociado) object;
        if ((this.numeroTelefono == null && other.numeroTelefono != null) || (this.numeroTelefono != null && !this.numeroTelefono.equals(other.numeroTelefono))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.qualification.modelo.Asociado[ numeroTelefono=" + numeroTelefono + " ]";
    }
    
}
