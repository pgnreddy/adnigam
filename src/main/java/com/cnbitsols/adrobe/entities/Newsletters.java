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
@Table(name = "newsletters")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Newsletters.findAll", query = "SELECT n FROM Newsletters n"),
    @NamedQuery(name = "Newsletters.findByNewslettersId", query = "SELECT n FROM Newsletters n WHERE n.newslettersId = :newslettersId"),
    @NamedQuery(name = "Newsletters.findByTitle", query = "SELECT n FROM Newsletters n WHERE n.title = :title"),
    @NamedQuery(name = "Newsletters.findByModule", query = "SELECT n FROM Newsletters n WHERE n.module = :module"),
    @NamedQuery(name = "Newsletters.findByDateAdded", query = "SELECT n FROM Newsletters n WHERE n.dateAdded = :dateAdded"),
    @NamedQuery(name = "Newsletters.findByDateSent", query = "SELECT n FROM Newsletters n WHERE n.dateSent = :dateSent"),
    @NamedQuery(name = "Newsletters.findByStatus", query = "SELECT n FROM Newsletters n WHERE n.status = :status"),
    @NamedQuery(name = "Newsletters.findByLocked", query = "SELECT n FROM Newsletters n WHERE n.locked = :locked")})
public class Newsletters implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "newsletters_id")
    private Integer newslettersId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "content")
    private String content;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "module")
    private String module;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_added")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAdded;
    @Column(name = "date_sent")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateSent;
    @Column(name = "status")
    private Integer status;
    @Column(name = "locked")
    private Integer locked;

    public Newsletters() {
    }

    public Newsletters(Integer newslettersId) {
        this.newslettersId = newslettersId;
    }

    public Newsletters(Integer newslettersId, String title, String content, String module, Date dateAdded) {
        this.newslettersId = newslettersId;
        this.title = title;
        this.content = content;
        this.module = module;
        this.dateAdded = dateAdded;
    }

    public Integer getNewslettersId() {
        return newslettersId;
    }

    public void setNewslettersId(Integer newslettersId) {
        this.newslettersId = newslettersId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Date getDateSent() {
        return dateSent;
    }

    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (newslettersId != null ? newslettersId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Newsletters)) {
            return false;
        }
        Newsletters other = (Newsletters) object;
        if ((this.newslettersId == null && other.newslettersId != null) || (this.newslettersId != null && !this.newslettersId.equals(other.newslettersId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cnbitsols.adrobe.entities.Newsletters[ newslettersId=" + newslettersId + " ]";
    }
    
}
