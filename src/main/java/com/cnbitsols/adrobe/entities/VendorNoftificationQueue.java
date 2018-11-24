/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cnbitsols.adrobe.entities;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author santosh.r
 */
@Entity
@Table(name = "vendornoftificationqueue")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VendorNoftificationQueue.findAll", query = "SELECT v FROM VendorNoftificationQueue v"),
    @NamedQuery(name = "VendorNoftificationQueue.findById", query = "SELECT v FROM VendorNoftificationQueue v WHERE v.id = :id"),
    @NamedQuery(name = "VendorNoftificationQueue.findByOrderId", query = "SELECT v FROM VendorNoftificationQueue v WHERE v.orderId = :orderId")})
public class VendorNoftificationQueue implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "orderId")
    private BigInteger orderId;

    public VendorNoftificationQueue() {
    }

    public VendorNoftificationQueue(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigInteger getOrderId() {
        return orderId;
    }

    public void setOrderId(BigInteger orderId) {
        this.orderId = orderId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VendorNoftificationQueue)) {
            return false;
        }
        VendorNoftificationQueue other = (VendorNoftificationQueue) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cnbitsols.adrobe.entities.VendorNoftificationQueue[ id=" + id + " ]";
    }
    
}
