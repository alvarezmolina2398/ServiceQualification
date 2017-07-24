/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qualification.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "parametro")
@NamedQueries({
    @NamedQuery(name = "Parametro.findAll", query = "SELECT p FROM Parametro p")})
public class Parametro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idparametro")
    private String idparametro;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "rango_minimo")
    private BigDecimal rangoMinimo;
    @Basic(optional = false)
    @Column(name = "rango_maximo")
    private BigDecimal rangoMaximo;
    @Column(name = "dir_img")
    private String dirImg;
    @Basic(optional = false)
    @Column(name = "estado")
    private Character estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parametro")
    private List<ParametroInterrogante> parametroInterroganteList;

    public Parametro() {
    }

    public Parametro(String idparametro) {
        this.idparametro = idparametro;
    }

    public Parametro(String idparametro, String descripcion, BigDecimal rangoMinimo, BigDecimal rangoMaximo, Character estado) {
        this.idparametro = idparametro;
        this.descripcion = descripcion;
        this.rangoMinimo = rangoMinimo;
        this.rangoMaximo = rangoMaximo;
        this.estado = estado;
    }

    public String getIdparametro() {
        return idparametro;
    }

    public void setIdparametro(String idparametro) {
        this.idparametro = idparametro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getRangoMinimo() {
        return rangoMinimo;
    }

    public void setRangoMinimo(BigDecimal rangoMinimo) {
        this.rangoMinimo = rangoMinimo;
    }

    public BigDecimal getRangoMaximo() {
        return rangoMaximo;
    }

    public void setRangoMaximo(BigDecimal rangoMaximo) {
        this.rangoMaximo = rangoMaximo;
    }

    public String getDirImg() {
        return dirImg;
    }

    public void setDirImg(String dirImg) {
        this.dirImg = dirImg;
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
        hash += (idparametro != null ? idparametro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parametro)) {
            return false;
        }
        Parametro other = (Parametro) object;
        if ((this.idparametro == null && other.idparametro != null) || (this.idparametro != null && !this.idparametro.equals(other.idparametro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.qualification.modelo.Parametro[ idparametro=" + idparametro + " ]";
    }
    
}
