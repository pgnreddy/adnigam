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
@Table(name = "user_requests")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserRequests.findAll", query = "SELECT u FROM UserRequests u"),
    @NamedQuery(name = "UserRequests.findById", query = "SELECT u FROM UserRequests u WHERE u.id = :id"),
    @NamedQuery(name = "UserRequests.findByVendorId", query = "SELECT u FROM UserRequests u WHERE u.vendorId = :vendorId"),
    @NamedQuery(name = "UserRequests.findByRequest", query = "SELECT u FROM UserRequests u WHERE u.request = :request"),
    @NamedQuery(name = "UserRequests.findByOrderProductId", query = "SELECT u FROM UserRequests u WHERE u.orderProductId = :orderProductId"),
    @NamedQuery(name = "UserRequests.findByRead", query = "SELECT u FROM UserRequests u WHERE u.read = :read"),
    @NamedQuery(name = "UserRequests.findByUpdatedDate", query = "SELECT u FROM UserRequests u WHERE u.updatedDate = :updatedDate")})
public class UserRequests implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "vendorId")
    private Integer vendorId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5000)
    @Column(name = "request")
    private String request;
    @Column(name = "orderProductId")
    private Integer orderProductId;
    @Column(name = "isRead")
    private Integer read;
    @Column(name = "updatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    public UserRequests() {
    }

    public UserRequests(Integer id) {
        this.id = id;
    }

    public UserRequests(Integer id, String request) {
        this.id = id;
        this.request = request;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public Integer getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(Integer orderProductId) {
        this.orderProductId = orderProductId;
    }

    public Integer getRead() {
        return read;
    }

    public void setRead(Integer read) {
        this.read = read;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
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
        if (!(object instanceof UserRequests)) {
            return false;
        }
        UserRequests other = (UserRequests) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cnbitsols.adrobe.entities.UserRequests[ id=" + id + " ]";
    }
    
}
