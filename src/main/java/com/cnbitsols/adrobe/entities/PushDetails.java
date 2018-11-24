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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author enixtainnovationspvtltd
 */

@Entity
@Table(name = "push_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PushDetails.findAll", query = "SELECT f FROM PushDetails f"),
     @NamedQuery(name = "PushDetails.findById", query = "SELECT f FROM PushDetails f WHERE f.id = :id"),
    @NamedQuery(name = "PushDetails.findByCustomersId", query = "SELECT f FROM PushDetails f WHERE f.customer_id = :customer_id"),
   
    })    
public class PushDetails implements Serializable{
 private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer pushid;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "customer_id")
    private Integer customer_id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pushkey")
    private String pushkey;
    @Basic(optional = false)
    @NotNull
    @Column(name = "deviceos")
    private String device_os;
    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private int status;

    public PushDetails() {
    }

    public PushDetails(Integer custID, String pushkey, String device_os, int status) {
        this.customer_id = custID;
        this.pushkey = pushkey;
        this.device_os = device_os;
        this.status = status;
    }

    public Integer getPushid() {
        return pushid;
    }

    public void setPushid(Integer pushid) {
        this.pushid = pushid;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Integer customer_id) {
        this.customer_id = customer_id;
    }

    


    public String getPushkey() {
        return pushkey;
    }

    public void setPushkey(String pushkey) {
        this.pushkey = pushkey;
    }

    public String getDevice_os() {
        return device_os;
    }

    public void setDevice_os(String device_os) {
        this.device_os = device_os;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
     @Override
    public int hashCode() {
        int hash = 0;
        hash += (pushid != null ? pushid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PushDetails)) {
            return false;
        }
        PushDetails other = (PushDetails) object;
        if ((this.pushid == null && other.pushid != null) || (this.pushid != null && !this.pushid.equals(other.pushid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cnbitsols.adrobe.entities.PushDetails[ id=" + pushid + " ]";
    }
    
}
