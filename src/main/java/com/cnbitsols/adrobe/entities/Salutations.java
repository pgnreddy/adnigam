/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cnbitsols.adrobe.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author santosh.r
 */
@Entity
@Table(name = "salutations")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Salutations.findAll", query = "SELECT s FROM Salutations s"),
    @NamedQuery(name = "Salutations.findBySalutationId", query = "SELECT s FROM Salutations s WHERE s.salutationId = :salutationId"),
    @NamedQuery(name = "Salutations.findByTitle", query = "SELECT s FROM Salutations s WHERE s.title = :title")})
public class Salutations implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "salutation_id")
    private Integer salutationId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "title")
    private String title;

    public Salutations() {
    }

    public Salutations(Integer salutationId) {
        this.salutationId = salutationId;
    }

    public Salutations(Integer salutationId, String title) {
        this.salutationId = salutationId;
        this.title = title;
    }

    public Integer getSalutationId() {
        return salutationId;
    }

    public void setSalutationId(Integer salutationId) {
        this.salutationId = salutationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (salutationId != null ? salutationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Salutations)) {
            return false;
        }
        Salutations other = (Salutations) object;
        if ((this.salutationId == null && other.salutationId != null) || (this.salutationId != null && !this.salutationId.equals(other.salutationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cnbitsols.adrobe.entities.Salutations[ salutationId=" + salutationId + " ]";
    }
    
}
