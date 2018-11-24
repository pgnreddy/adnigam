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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author santosh.r
 */
@Entity
@Table(name = "customers_basket")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CustomersBasket.findAll", query = "SELECT c FROM CustomersBasket c"),
    @NamedQuery(name = "CustomersBasket.findByCustomersBasketId", query = "SELECT c FROM CustomersBasket c WHERE c.customersBasketId = :customersBasketId"),
    @NamedQuery(name = "CustomersBasket.findByCustomersId", query = "SELECT c FROM CustomersBasket c WHERE c.customersId = :customersId"),
    @NamedQuery(name = "CustomersBasket.findByProductsId", query = "SELECT c FROM CustomersBasket c WHERE c.productsId = :productsId"),
    @NamedQuery(name = "CustomersBasket.findByCustomersBasketQuantity", query = "SELECT c FROM CustomersBasket c WHERE c.customersBasketQuantity = :customersBasketQuantity"),
    @NamedQuery(name = "CustomersBasket.findByFinalPrice", query = "SELECT c FROM CustomersBasket c WHERE c.finalPrice = :finalPrice"),
    @NamedQuery(name = "CustomersBasket.findByCustomersBasketDateAdded", query = "SELECT c FROM CustomersBasket c WHERE c.customersBasketDateAdded = :customersBasketDateAdded")})
public class CustomersBasket implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "customers_basket_id")
    private Integer customersBasketId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "customers_id")
    private int customersId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "products_id")
    private int productsId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "customers_basket_quantity")
    private int customersBasketQuantity;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "final_price")
    private BigDecimal finalPrice;
    @Size(max = 8)
    @Column(name = "customers_basket_date_added")
    private String customersBasketDateAdded;

    public CustomersBasket() {
    }

    public CustomersBasket(Integer customersBasketId) {
        this.customersBasketId = customersBasketId;
    }

    public CustomersBasket(Integer customersBasketId, int customersId, int productsId, int customersBasketQuantity) {
        this.customersBasketId = customersBasketId;
        this.customersId = customersId;
        this.productsId = productsId;
        this.customersBasketQuantity = customersBasketQuantity;
    }

    public Integer getCustomersBasketId() {
        return customersBasketId;
    }

    public void setCustomersBasketId(Integer customersBasketId) {
        this.customersBasketId = customersBasketId;
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

    public int getCustomersBasketQuantity() {
        return customersBasketQuantity;
    }

    public void setCustomersBasketQuantity(int customersBasketQuantity) {
        this.customersBasketQuantity = customersBasketQuantity;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getCustomersBasketDateAdded() {
        return customersBasketDateAdded;
    }

    public void setCustomersBasketDateAdded(String customersBasketDateAdded) {
        this.customersBasketDateAdded = customersBasketDateAdded;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customersBasketId != null ? customersBasketId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomersBasket)) {
            return false;
        }
        CustomersBasket other = (CustomersBasket) object;
        if ((this.customersBasketId == null && other.customersBasketId != null) || (this.customersBasketId != null && !this.customersBasketId.equals(other.customersBasketId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cnbitsols.adrobe.entities.CustomersBasket[ customersBasketId=" + customersBasketId + " ]";
    }
    
}
