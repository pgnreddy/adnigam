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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author santosh.r
 */
@Entity
@Table(name = "premiumoffers")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PremiumOffers.findAll", query = "SELECT p FROM PremiumOffers p order by id desc"),
    @NamedQuery(name = "PremiumOffers.findById", query = "SELECT p FROM PremiumOffers p WHERE p.id = :id"),
    @NamedQuery(name = "PremiumOffers.findByTitle", query = "SELECT p FROM PremiumOffers p WHERE p.title = :title"),
    @NamedQuery(name = "PremiumOffers.findByDescription", query = "SELECT p FROM PremiumOffers p WHERE p.description = :description"),
    @NamedQuery(name = "PremiumOffers.findByImage", query = "SELECT p FROM PremiumOffers p WHERE p.image = :image"),
    @NamedQuery(name = "PremiumOffers.findByUrl", query = "SELECT p FROM PremiumOffers p WHERE p.url = :url"),
    @NamedQuery(name = "PremiumOffers.findByStatus", query = "SELECT p FROM PremiumOffers p WHERE p.status = :status ORDER BY p.id DESC")})
public class PremiumOffers implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 1024)
    @Column(name = "title")
    private String title;
    @Size(max = 2000)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1024)
    @Column(name = "image")
    private String image;
    @Size(max = 2000)
    @Column(name = "url")
    private String url;
    @Column(name = "status")
    private Integer status=1;
    @Column(name = "sort_order")
    private Integer sort_order;
    @Column(name = "locationId")
    private Integer locationId;
    @Column(name = "startDate")
    private String startDate;
    @Column(name = "endDate")
    private String endDate;
    @Column(name = "scheduleStatus")
    private Integer scheduleStatus = 0;     // 0 - default, 1 - yet to be scheduled, 2 - scheduled and active; needs to be unscheduled

    public PremiumOffers() {
    }

    public PremiumOffers(Integer id) {
        this.id = id;
    }

    public PremiumOffers(Integer id, String image) {
        this.id = id;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
     @Transient   
    private MultipartFile uploadImage;

    public MultipartFile getUploadImage() {
        return uploadImage;
    }

    public void setUploadImage(MultipartFile uploadImage) {
        this.uploadImage = uploadImage;
    }

    public Integer getSort_order() {
        return sort_order;
    }

    public void setSort_order(Integer sort_order) {
        this.sort_order = sort_order;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getScheduleStatus() {
        return scheduleStatus;
    }

    public void setScheduleStatus(Integer scheduleStatus) {
        this.scheduleStatus = scheduleStatus;
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
        if (!(object instanceof PremiumOffers)) {
            return false;
        }
        PremiumOffers other = (PremiumOffers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "com.cnbitsols.adrobe.entities.PremiumOffers[ id=" + id + " ]";
    }
    
}
