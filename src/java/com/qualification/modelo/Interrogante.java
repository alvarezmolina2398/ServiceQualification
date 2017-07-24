/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qualification.modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Desarrollo
 */
@Entity
@Table(name = "interrogante")
@NamedQueries({
    @NamedQuery(name = "Interrogante.findAll", query = "SELECT i FROM Interrogante i")})
public class Interrogante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idinterrogante")
    private Integer idinterrogante;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "estado")
    private Character estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "interrogante")
    private List<ParametroInterrogante> parametroInterroganteList;

    public Interrogante() {
    }

    public Interrogante(Integer idinterrogante) {
        this.idinterrogante = idinterrogante;
    }

    public Interrogante(Integer idinterrogante, String descripcion, Character estado) {
        this.idinterrogante = idinterrogante;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public Integer getIdinterrogante() {
        return idinterrogante;
    }

    public void setIdinterrogante(Integer idinterrogante) {
        this.idinterrogante = idinterrogante;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }

    public List<ParametroInterrogante> getParametroInterroganteList() {
        return parametroInterroganteList;
    }

    public void setParametroInterroganteList(List<ParametroInterrogante> parametroInterroganteList) {
        this.parametroInterroganteList = parametroInterroganteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idinterrogante != null ? idinterrogante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Interrogante)) {
            return false;
        }
        Interrogante other = (Interrogante) object;
        if ((this.idinterrogante == null && other.idinterrogante != null) || (this.idinterrogante != null && !this.idinterrogante.equals(other.idinterrogante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.qualification.modelo.Interrogante[ idinterrogante=" + idinterrogante + " ]";
    }
    
}
