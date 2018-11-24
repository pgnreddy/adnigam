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
@Table(name = "email_queue")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmailQueue.findAll", query = "SELECT e FROM EmailQueue e"),
    @NamedQuery(name = "EmailQueue.findById", query = "SELECT e FROM EmailQueue e WHERE e.id = :id"),
    @NamedQuery(name = "EmailQueue.findByCustomerId", query = "SELECT e FROM EmailQueue e WHERE e.customerId = :customerId"),
    @NamedQuery(name = "EmailQueue.findByCreatedDate", query = "SELECT e FROM EmailQueue e WHERE e.createdDate = :createdDate"),
    @NamedQuery(name = "EmailQueue.findByUpdatedDate", query = "SELECT e FROM EmailQueue e WHERE e.updatedDate = :updatedDate"),
    @NamedQuery(name = "EmailQueue.findByStatus", query = "SELECT e FROM EmailQueue e WHERE e.status = :status"),
    @NamedQuery(name = "EmailQueue.findByFrom", query = "SELECT e FROM EmailQueue e WHERE e.from = :from"),
    @NamedQuery(name = "EmailQueue.findByTo", query = "SELECT e FROM EmailQueue e WHERE e.to = :to"),
    @NamedQuery(name = "EmailQueue.findByBcc", query = "SELECT e FROM EmailQueue e WHERE e.bcc = :bcc"),
    @NamedQuery(name = "EmailQueue.findBySubject", query = "SELECT e FROM EmailQueue e WHERE e.subject = :subject")})
public class EmailQueue implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "customerId")
    private int customerId;
    @Column(name = "createdDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "updatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    @Column(name = "status")
    private Integer status;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "fromAddress")
    private String from;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "toAddress")
    private String to;
    @Size(max = 256)
    @Column(name = "bcc")
    private String bcc;
    @Size(max = 512)
    @Column(name = "email_subject")
    private String subject;
    @Lob
    @Size(max = 16777215)
    @Column(name = "message")
    private String message;

    @Size(max = 300)
    @Column(name = "response")
    private String response;
     
    public EmailQueue() {
    }

    public EmailQueue(Long id) {
        this.id = id;
    }

    public EmailQueue(Long id, int customerId, String from, String to) {
        this.id = id;
        this.customerId = customerId;
        this.from = from;
        this.to = to;
    }

    public EmailQueue(int customerId, Date createdDate, Date updatedDate, Integer status, String from, String to, String bcc, String subject, String message) {
        this.customerId = customerId;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.status = status;
        this.from = from;
        this.to = to;
        this.bcc = bcc;
        this.subject = subject;
        this.message = message;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
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

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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
        if (!(object instanceof EmailQueue)) {
            return false;
        }
        EmailQueue other = (EmailQueue) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    
    @Override
    public String toString() {
        return "com.cnbitsols.adrobe.entities.EmailQueue[ id=" + id + " ]";
    }
    
}
