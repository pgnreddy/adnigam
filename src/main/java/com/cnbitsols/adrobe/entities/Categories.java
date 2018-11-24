/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cnbitsols.adrobe.entities;

import java.io.Serializable;
import java.util.List;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author santosh.r
 */
@Entity
@Table(name = "categories")
@XmlRootElement
@NamedQueries({//
    @NamedQuery(name = "Categories.findAll", query = "SELECT c FROM Categories c"),
    @NamedQuery(name = "Categories.findByCategoryID", query = "SELECT c FROM Categories c WHERE c.categoryID = :categoryID"),
    @NamedQuery(name = "Categories.findAllStatus", query = "SELECT c FROM Categories c WHERE c.status = :status order by categoryID desc "),
    @NamedQuery(name = "Categories.findByName", query = "SELECT c FROM Categories c WHERE c.name = :name"),
    @NamedQuery(name = "Categories.findByDescription", query = "SELECT c FROM Categories c WHERE c.description = :description"),
    @NamedQuery(name = "Categories.findByParentID", query = "SELECT c FROM Categories c WHERE c.parentID = :parentID"),
    @NamedQuery(name = "Categories.findByCategoryIcon", query = "SELECT c FROM Categories c WHERE c.categoryIcon = :categoryIcon"),
    @NamedQuery(name = "Categories.findBySEOTITLE", query = "SELECT c FROM Categories c WHERE c.seo_title = :seo_title")})
public class Categories implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CategoryID")
    private Integer categoryID;
    @Size(max = 45)
    @Column(name = "Name")
    private String name;
    @Size(max = 300)
    @Column(name = "Description")
    private String description;
    @Column(name = "ParentID")
    private Integer parentID=0;
    @Size(max = 100)
    @Column(name = "CategoryIcon")
    private String categoryIcon;

    @Size(max = 512)
    @Column(name = "seo_title")
    private String seo_title;
    
       
    @Column(name = "status")
    private Integer status=1;
    
    @Column(name = "sort_order")
    private Integer sortOrder;

    
    @Column(name = "home_display")
    private Integer homeDisplay=0;
        
    @Transient
    List<Categories> subCategories = null;
    
    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    
    public Categories() {
    }

    public Categories(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getParentID() {
        return parentID;
    }

    public void setParentID(Integer parentID) {
        this.parentID = parentID;
    }

    public String getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(String categoryIcon) {
        this.categoryIcon = categoryIcon;
    }

    public String getSeo_title() {
        return seo_title;
    }

    public void setSeo_title(String seo_title) {
        this.seo_title = seo_title;
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

    public Integer getHomeDisplay() {
        return homeDisplay;
    }

    public void setHomeDisplay(Integer homeDisplay) {
        this.homeDisplay = homeDisplay;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (categoryID != null ? categoryID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categories)) {
            return false;
        }
        Categories other = (Categories) object;
        if ((this.categoryID == null && other.categoryID != null) || (this.categoryID != null && !this.categoryID.equals(other.categoryID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cnbitsols.adrobe.entities.Categories[ categoryID=" + categoryID + " ]";
    }

    public List<Categories> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<Categories> subCategories) {
        this.subCategories = subCategories;
    }    
    
}
