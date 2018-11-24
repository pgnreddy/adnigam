/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cnbitsols.adrobe.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author santosh.r
 */
@Entity
@Table(name = "paymenttransactions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PaymentTransactions.findAll", query = "SELECT p FROM PaymentTransactions p"),
    @NamedQuery(name = "PaymentTransactions.findById", query = "SELECT p FROM PaymentTransactions p WHERE p.id = :id"),
    @NamedQuery(name = "PaymentTransactions.findByOrderId", query = "SELECT p FROM PaymentTransactions p WHERE p.orderId = :orderId"),
    @NamedQuery(name = "PaymentTransactions.findByCreatedDate", query = "SELECT p FROM PaymentTransactions p WHERE p.createdDate = :createdDate")})
public class PaymentTransactions implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "orderId")
    private int orderId;
    @Column(name = "createdDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Lob
    @Size(max = 65535)
    @Column(name = "request")
    private String request;

    public PaymentTransactions() {
    }

    public PaymentTransactions(Integer id) {
        this.id = id;
    }

    public PaymentTransactions(Integer id, int orderId) {
        this.id = id;
        this.orderId = orderId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
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
        if (!(object instanceof PaymentTransactions)) {
            return false;
        }
        PaymentTransactions other = (PaymentTransactions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cnbitsols.adrobe.entities.PaymentTransactions[ id=" + id + " ]";
    }
    
}
