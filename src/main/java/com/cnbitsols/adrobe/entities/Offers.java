/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cnbitsols.adrobe.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "offers")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Offers.findAll", query = "SELECT o FROM Offers o"),
    @NamedQuery(name = "Offers.findByOfferID", query = "SELECT o FROM Offers o WHERE o.offerID = :offerID and o.vendorID = :vendorID order by o.offerID desc"),
    @NamedQuery(name = "Offers.findByOfferIDOnly", query = "SELECT o FROM Offers o WHERE o.offerID = :offerID "),
    @NamedQuery(name = "Offers.findByCategoryID", query = "SELECT o FROM Offers o WHERE o.categoryID = :categoryID"),
    @NamedQuery(name = "Offers.findByVendorID", query = "SELECT o FROM Offers o WHERE o.vendorID = :vendorID and o.status=1 order by o.sort_order, o.offerID desc"),
    @NamedQuery(name = "Offers.findByState", query = "SELECT o FROM Offers o WHERE  o.type=:type and o.status=1 order by o.offerID desc"),
    @NamedQuery(name = "Offers.findBySEOTITLE", query = "SELECT o FROM Offers o WHERE o.seo_title = :seo_title"),
    @NamedQuery(name = "Offers.findUnapproved", query = "SELECT o FROM Offers o WHERE o.status = 0 order by o.offerID desc")})
public class Offers implements Serializable {

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Price")
    //@JsonSerialize(using = CurrencySerializer.class)
    private BigDecimal price;
    @Basic(optional = false)
    @NotNull
    @Column(name = "offerPrice")
    //@JsonSerialize(using = CurrencySerializer.class)
    private BigDecimal offerPrice;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "OfferID")
    private Integer offerID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CategoryID")
    private int categoryID;
    @Size(max = 1024)
    @Column(name = "Title")
    private String title;

    @Column(name = "Description")
    private String description;

    @Column(name = "Ishot")
    private int ishot;

    @Size(max = 1024)
    @Column(name = "Image")
    private String image;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VendorID")
    private int vendorID;

    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private int status = 0;

    @Basic(optional = false)
    @NotNull
    @Column(name = "quantity")
    private int quantity;

    @Column(name = "type")
    private int type;

    @Size(max = 1024)
    @Column(name = "seo_title")
    private String seo_title;

    @Size(max = 1024)
    @Column(name = "image_original")
    private String image_original;

    @Column(name = "coupon_price")
    @NotNull
    private BigDecimal couponPrice;

    @Column(name = "sort_order")
    @NotNull
    private Integer sort_order;

    @Column(name = "locationId")
    private Integer locationId;

    @Column(name = "keywords")
    private String keywords;

    @Column(name = "startDate")
    private String startDate;

    @Column(name = "endDate")
    private String endDate;

    @Column(name = "scheduleStatus")
    private Integer scheduleStatus = 0; // 0 for default; 1 - yet to be scheduled; 2 - scheduled and waiting to be unscheduled

    @Column(name = "colors")
    private String colors;

    @Transient
    public String[] colorstemp;

    public String[] getColorstemp() {
        return colorstemp;
    }

    public void setColorstemp(String[] colorstemp) {
        this.colorstemp = colorstemp;
    }
    
   public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
        this.colors = colors;
    }
    
    public Offers() {
    }

    public Offers(Integer offerID) {
        this.offerID = offerID;
    }

    public Offers(Integer offerID, int categoryID, int vendorID) {
        this.offerID = offerID;
        this.categoryID = categoryID;
        this.vendorID = vendorID;
    }

    public Integer getOfferID() {
        return offerID;
    }

    public void setOfferID(Integer offerID) {
        this.offerID = offerID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
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

    public int getIshot() {
        return ishot;
    }

    public void setIshot(int ishot) {
        this.ishot = ishot;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getVendorID() {
        return vendorID;
    }

    public void setVendorID(int vendorID) {
        this.vendorID = vendorID;
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
        hash += (offerID != null ? offerID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Offers)) {
            return false;
        }
        Offers other = (Offers) object;
        if ((this.offerID == null && other.offerID != null) || (this.offerID != null && !this.offerID.equals(other.offerID))) {
            return false;
        }
        return true;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Transient
    private MultipartFile uploadImage;

    public MultipartFile getUploadImage() {
        return uploadImage;
    }

    public void setUploadImage(MultipartFile uploadImage) {
        this.uploadImage = uploadImage;
    }

    public String getSeo_title() {
        return seo_title;
    }

    public void setSeo_title(String seo_title) {
        this.seo_title = seo_title;
    }

    @Override
    public String toString() {
        return "com.cnbitsols.adrobe.entities.Offers[ offerID=" + offerID + " ]";
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(BigDecimal offerPrice) {
        this.offerPrice = offerPrice;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImage_original() {
        return image_original;
    }

    public void setImage_original(String image_original) {
        this.image_original = image_original;
    }

    public BigDecimal getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(BigDecimal couponPrice) {
        this.couponPrice = couponPrice;
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

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

}
