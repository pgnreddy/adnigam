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
 * @author santosh.r
 */
@Entity
@Table(name = "favourite_customer_products")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FavouriteCustomerProducts.findAll", query = "SELECT f FROM FavouriteCustomerProducts f"),
    @NamedQuery(name = "FavouriteCustomerProducts.findByFavouritesId", query = "SELECT f FROM FavouriteCustomerProducts f WHERE f.favouritesId = :favouritesId"),
    @NamedQuery(name = "FavouriteCustomerProducts.findByCustomersId", query = "SELECT f FROM FavouriteCustomerProducts f WHERE f.customersId = :customersId"),
    @NamedQuery(name = "FavouriteCustomerProducts.findByProductsId", query = "SELECT f FROM FavouriteCustomerProducts f WHERE f.productsId = :productsId")})
public class FavouriteCustomerProducts implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "favourites_id")
    private Integer favouritesId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "customers_id")
    private int customersId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "products_id")
    private int productsId;

    public FavouriteCustomerProducts() {
    }

    public FavouriteCustomerProducts(Integer favouritesId) {
        this.favouritesId = favouritesId;
    }

    public FavouriteCustomerProducts(Integer favouritesId, int customersId, int productsId) {
        this.favouritesId = favouritesId;
        this.customersId = customersId;
        this.productsId = productsId;
    }

    public Integer getFavouritesId() {
        return favouritesId;
    }

    public void setFavouritesId(Integer favouritesId) {
        this.favouritesId = favouritesId;
    }

    public int getCustomersId() {
        return customersId;
    }

    public void setCustomersId(int customersId) {
        this.customersId = customersId;
    }

    public int getProductsId() {
        return productsId;
    }

    public void setProductsId(int productsId) {
        this.productsId = productsId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (favouritesId != null ? favouritesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FavouriteCustomerProducts)) {
            return false;
        }
        FavouriteCustomerProducts other = (FavouriteCustomerProducts) object;
        if ((this.favouritesId == null && other.favouritesId != null) || (this.favouritesId != null && !this.favouritesId.equals(other.favouritesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cnbitsols.adrobe.entities.FavouriteCustomerProducts[ favouritesId=" + favouritesId + " ]";
    }
    
}
