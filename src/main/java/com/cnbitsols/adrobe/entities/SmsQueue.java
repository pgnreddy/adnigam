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
@Table(name = "sms_queue")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SmsQueue.findAll", query = "SELECT s FROM SmsQueue s"),
    @NamedQuery(name = "SmsQueue.findById", query = "SELECT s FROM SmsQueue s WHERE s.id = :id"),
    @NamedQuery(name = "SmsQueue.findByCustomerId", query = "SELECT s FROM SmsQueue s WHERE s.customerId = :customerId"),
    @NamedQuery(name = "SmsQueue.findByCreatedDate", query = "SELECT s FROM SmsQueue s WHERE s.createdDate = :createdDate"),
    @NamedQuery(name = "SmsQueue.findByUpdatedDate", query = "SELECT s FROM SmsQueue s WHERE s.updatedDate = :updatedDate"),
    @NamedQuery(name = "SmsQueue.findByStatus", query = "SELECT s FROM SmsQueue s WHERE s.status = :status"),
    @NamedQuery(name = "SmsQueue.findByResponse", query = "SELECT s FROM SmsQueue s WHERE s.response = :response"),
    @NamedQuery(name = "SmsQueue.findByMsisdn", query = "SELECT s FROM SmsQueue s WHERE s.msisdn = :msisdn"),
    @NamedQuery(name = "SmsQueue.findByMessage", query = "SELECT s FROM SmsQueue s WHERE s.message = :message")})
public class SmsQueue implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "customerId")
    private Integer customerId;
    @Column(name = "createdDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "updatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    @Column(name = "status")
    private Integer status;
    @Size(max = 100)
    @Column(name = "response")
    private String response;
    @Size(max = 20)
    @Column(name = "msisdn")
    private String msisdn;
    @Size(max = 512)
    @Column(name = "message")
    private String message;

    public SmsQueue() {
    }

    public SmsQueue(Long id) {
        this.id = id;
    }

    public SmsQueue(Integer customerId, Date createdDate, Date updatedDate, Integer status, String msisdn, String message) {
        this.customerId = customerId;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.status = status;
        this.msisdn = msisdn;
        this.message = message;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
        if (!(object instanceof SmsQueue)) {
            return false;
        }
        SmsQueue other = (SmsQueue) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cnbitsols.adrobe.entities.SmsQueue[ id=" + id + " ]";
    }
    
}
