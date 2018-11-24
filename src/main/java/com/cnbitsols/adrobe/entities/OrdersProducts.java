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
@Table(name = "orders_products")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrdersProducts.findAll", query = "SELECT o FROM OrdersProducts o"),
    @NamedQuery(name = "OrdersProducts.findByOrdersProductsId", query = "SELECT o FROM OrdersProducts o WHERE o.ordersProductsId = :ordersProductsId"),
    @NamedQuery(name = "OrdersProducts.findByOrdersId", query = "SELECT o FROM OrdersProducts o WHERE o.ordersId = :ordersId"),
    @NamedQuery(name = "OrdersProducts.findByProductsId", query = "SELECT o FROM OrdersProducts o WHERE o.productsId = :productsId"),
    @NamedQuery(name = "OrdersProducts.findByProductsModel", query = "SELECT o FROM OrdersProducts o WHERE o.productsModel = :productsModel"),
    @NamedQuery(name = "OrdersProducts.findByProductsName", query = "SELECT o FROM OrdersProducts o WHERE o.productsName = :productsName"),
    @NamedQuery(name = "OrdersProducts.findByProductsPrice", query = "SELECT o FROM OrdersProducts o WHERE o.productsPrice = :productsPrice"),
    @NamedQuery(name = "OrdersProducts.findByFinalPrice", query = "SELECT o FROM OrdersProducts o WHERE o.finalPrice = :finalPrice"),
    @NamedQuery(name = "OrdersProducts.findByProductsTax", query = "SELECT o FROM OrdersProducts o WHERE o.productsTax = :productsTax"),
    @NamedQuery(name = "OrdersProducts.findByProductsQuantity", query = "SELECT o FROM OrdersProducts o WHERE o.productsQuantity = :productsQuantity"),
    @NamedQuery(name = "OrdersProducts.updateStatus", query = "update OrdersProducts set ordersProductsStatus=:ordersProductsStatus where ordersId=:ordersId"),
    @NamedQuery(name = "OrdersProducts.findByReserved", query = "SELECT o FROM OrdersProducts o WHERE o.reserved = :reserved")})
public class OrdersProducts implements Serializable {
    @Column(name = "vendorId")
    private Integer vendorId;
    @Column(name = "reserved_price")
    private BigDecimal reservedPrice;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "orders_products_id")
    private Integer ordersProductsId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "orders_id")
    private int ordersId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "products_id")
    private int productsId;
    @Size(max = 255)
    @Column(name = "products_model")
    private String productsModel;
    @Size(max = 255)
    @Column(name = "products_name")
    private String productsName;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "products_price")
    private BigDecimal productsPrice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "final_price")
    private BigDecimal finalPrice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "products_tax")
    private BigDecimal productsTax;
    @Basic(optional = false)
    @NotNull
    @Column(name = "products_quantity")
    private int productsQuantity;
    @Column(name = "reserved")
    private Integer reserved;
    @Column(name = "coupon")
    private Integer coupon;

    @Basic(optional = false)
    @NotNull
    @Column(name = "orders_products_status")
    private int ordersProductsStatus;
    
    public OrdersProducts() {
    }

    public OrdersProducts(Integer ordersProductsId) {
        this.ordersProductsId = ordersProductsId;
    }

    public OrdersProducts(Integer ordersProductsId, int ordersId, int productsId, BigDecimal productsPrice, BigDecimal finalPrice, BigDecimal productsTax, int productsQuantity) {
        this.ordersProductsId = ordersProductsId;
        this.ordersId = ordersId;
        this.productsId = productsId;
        this.productsPrice = productsPrice;
        this.finalPrice = finalPrice;
        this.productsTax = productsTax;
        this.productsQuantity = productsQuantity;
    }

    public Integer getOrdersProductsId() {
        return ordersProductsId;
    }

    public void setOrdersProductsId(Integer ordersProductsId) {
        this.ordersProductsId = ordersProductsId;
    }

    public int getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(int ordersId) {
        this.ordersId = ordersId;
    }

    public int getProductsId() {
        return productsId;
    }

    public void setProductsId(int productsId) {
        this.productsId = productsId;
    }

    public String getProductsModel() {
        return productsModel;
    }

    public void setProductsModel(String productsModel) {
        this.productsModel = productsModel;
    }

    public String getProductsName() {
        return productsName;
    }

    public void setProductsName(String productsName) {
        this.productsName = productsName;
    }

    public BigDecimal getProductsPrice() {
        return productsPrice;
    }

    public void setProductsPrice(BigDecimal productsPrice) {
        this.productsPrice = productsPrice;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    public BigDecimal getProductsTax() {
        return productsTax;
    }

    public void setProductsTax(BigDecimal productsTax) {
        this.productsTax = productsTax;
    }

    public int getProductsQuantity() {
        return productsQuantity;
    }

    public void setProductsQuantity(int productsQuantity) {
        this.productsQuantity = productsQuantity;
    }

    public Integer getReserved() {
        return reserved;
    }

    public void setReserved(Integer reserved) {
        this.reserved = reserved;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ordersProductsId != null ? ordersProductsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrdersProducts)) {
            return false;
        }
        OrdersProducts other = (OrdersProducts) object;
        if ((this.ordersProductsId == null && other.ordersProductsId != null) || (this.ordersProductsId != null && !this.ordersProductsId.equals(other.ordersProductsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cnbitsols.adrobe.entities.OrdersProducts[ ordersProductsId=" + ordersProductsId + " ]";
    }

    public BigDecimal getReservedPrice() {
        return reservedPrice;
    }

    public void setReservedPrice(BigDecimal reservedPrice) {
        this.reservedPrice = reservedPrice;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public int getOrdersProductsStatus() {
        return ordersProductsStatus;
    }

    public void setOrdersProductsStatus(int ordersProductsStatus) {
        this.ordersProductsStatus = ordersProductsStatus;
    }

    public Integer getCoupon() {
        return coupon;
    }

    public void setCoupon(Integer coupon) {
        this.coupon = coupon;
    }
    
    
    
}
