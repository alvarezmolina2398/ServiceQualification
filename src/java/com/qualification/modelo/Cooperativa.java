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
@Table(name = "cooperativa")
@NamedQueries({
    @NamedQuery(name = "Cooperativa.findAll", query = "SELECT c FROM Cooperativa c")})
public class Cooperativa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcooperativa")
    private Integer idcooperativa;
    @Basic(optional = false)
    @Column(name = "nombre_cooperativa")
    private String nombreCooperativa;
    @Basic(optional = false)
    @Column(name = "estado")
    private Character estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cooperativa")
    private List<PromocionCooperativa> promocionCooperativaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cooperativaIdcooperativa")
    private List<Agencia> agenciaList;

    public Cooperativa() {
    }

    public Cooperativa(Integer idcooperativa) {
        this.idcooperativa = idcooperativa;
    }

    public Cooperativa(Integer idcooperativa, String nombreCooperativa, Character estado) {
        this.idcooperativa = idcooperativa;
        this.nombreCooperativa = nombreCooperativa;
        this.estado = estado;
    }

    public Integer getIdcooperativa() {
        return idcooperativa;
    }

    public void setIdcooperativa(Integer idcooperativa) {
        this.idcooperativa = idcooperativa;
    }

    public String getNombreCooperativa() {
        return nombreCooperativa;
    }

    public void setNombreCooperativa(String nombreCooperativa) {
        this.nombreCooperativa = nombreCooperativa;
    }

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }

    public List<PromocionCooperativa> getPromocionCooperativaList() {
        return promocionCooperativaList;
    }

    public void setPromocionCooperativaList(List<PromocionCooperativa> promocionCooperativaList) {
        this.promocionCooperativaList = promocionCooperativaList;
    }

    public List<Agencia> getAgenciaList() {
        return agenciaList;
    }

    public void setAgenciaList(List<Agencia> agenciaList) {
        this.agenciaList = agenciaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcooperativa != null ? idcooperativa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cooperativa)) {
            return false;
        }
        Cooperativa other = (Cooperativa) object;
        if ((this.idcooperativa == null && other.idcooperativa != null) || (this.idcooperativa != null && !this.idcooperativa.equals(other.idcooperativa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.qualification.modelo.Cooperativa[ idcooperativa=" + idcooperativa + " ]";
    }
    
}
