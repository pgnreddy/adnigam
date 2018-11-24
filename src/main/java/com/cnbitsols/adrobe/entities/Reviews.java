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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author santosh.r
 */
@Entity
@Table(name = "reviews")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reviews.findAll", query = "SELECT r FROM Reviews r"),    
    @NamedQuery(name = "Reviews.findByProductsId", query = "SELECT r FROM Reviews r WHERE r.productsId = :productsId and r.approved=1 order by r.dateAdded desc"),
   })
public class Reviews implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "reviews_id")
    private Integer reviewsId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "products_id")
    private int productsId;
    @Column(name = "customers_id")
    private Integer customersId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "customers_name")
    private String customersName;
    @Column(name = "reviews_rating")
    private Integer reviewsRating;
    @Column(name = "date_added")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAdded;
    @Column(name = "last_modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified;
    @Basic(optional = false)
    @NotNull
    @Column(name = "reviews_read")
    private int reviewsRead;
    @Column(name = "approved")
    private Short approved=0;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "reviews_text")
    private String reviewsText;
    
    @Transient
    private Offers offers;

    public Reviews() {
    }

    public Reviews(Integer reviewsId) {
        this.reviewsId = reviewsId;
    }

    public Reviews(Integer reviewsId, int productsId, String customersName, int reviewsRead, String reviewsText) {
        this.reviewsId = reviewsId;
        this.productsId = productsId;
        this.customersName = customersName;
        this.reviewsRead = reviewsRead;
        this.reviewsText = reviewsText;
    }

    public Integer getReviewsId() {
        return reviewsId;
    }

    public void setReviewsId(Integer reviewsId) {
        this.reviewsId = reviewsId;
    }

    public int getProductsId() {
        return productsId;
    }

    public void setProductsId(int productsId) {
        this.productsId = productsId;
    }

    public Integer getCustomersId() {
        return customersId;
    }

    public void setCustomersId(Integer customersId) {
        this.customersId = customersId;
    }

    public String getCustomersName() {
        return customersName;
    }

    public void setCustomersName(String customersName) {
        this.customersName = customersName;
    }

    public Integer getReviewsRating() {
        return reviewsRating;
    }

    public void setReviewsRating(Integer reviewsRating) {
        this.reviewsRating = reviewsRating;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public int getReviewsRead() {
        return reviewsRead;
    }

    public void setReviewsRead(int reviewsRead) {
        this.reviewsRead = reviewsRead;
    }

    public Short getApproved() {
        return approved;
    }

    public void setApproved(Short approved) {
        this.approved = approved;
    }

    public String getReviewsText() {
        return reviewsText;
    }

    public void setReviewsText(String reviewsText) {
        this.reviewsText = reviewsText;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reviewsId != null ? reviewsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reviews)) {
            return false;
        }
        Reviews other = (Reviews) object;
        if ((this.reviewsId == null && other.reviewsId != null) || (this.reviewsId != null && !this.reviewsId.equals(other.reviewsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cnbitsols.adrobe.entities.Reviews[ reviewsId=" + reviewsId + " ]";
    }

    public void setProducts(Offers p) {
       this.offers = p;
    }
    
     public Offers getProducts(){
         return this.offers;
     }
    
}
