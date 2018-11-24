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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author rkumar
 */
@Entity
@Table(name = "product_coupon")
@XmlRootElement
@NamedQueries ({
    @NamedQuery(name = "ProductCoupon.findAll", query = "SELECT pc FROM ProductCoupon pc"),
    @NamedQuery(name = "ProductCoupon.findByCode", query = "SELECT pc FROM ProductCoupon pc WHERE pc.productCouponCode=:productCouponCode"),
    @NamedQuery(name = "ProductCoupon.findByVendorId", query = "SELECT pc FROM ProductCoupon pc WHERE pc.vendorID=:vendorID"),
    @NamedQuery(name = "ProductCoupon.findByOrderIdAndProductId", query = "SELECT pc FROM ProductCoupon pc WHERE pc.orderID=:orderID AND pc.productID=:productID")
})
public class ProductCoupon implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Basic(optional = false)
    @Column(name = "product_coupon_code")
    private String productCouponCode;
    
    @Column(name = "VendorID")
    private Integer vendorID;
    
    @Column(name = "productID")
    private Integer productID;
    
    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    
    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    
    @Column(name = "quantity")
    private Integer quantity;
    
    @Column(name = "orderID")
    private Integer orderID;
    
    @Column(name = "sold")
    private Integer sold = 0;
    
    @Column(name = "product_name", length = 1024)
    private String productName;
    
    public ProductCoupon() {
        
    }
    
    public ProductCoupon(String productCouponCode) {
        this.productCouponCode = productCouponCode;
    }

    public ProductCoupon(String productCouponCode, Integer vendorID, Integer productID, Date startDate, Date endDate) {
        this.productCouponCode = productCouponCode;
        this.vendorID = vendorID;
        this.productID = productID;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getProductCouponCode() {
        return productCouponCode;
    }

    public void setProductCouponCode(String productCouponCode) {
        this.productCouponCode = productCouponCode;
    }

    public Integer getVendorID() {
        return vendorID;
    }

    public void setVendorID(Integer vendorID) {
        this.vendorID = vendorID;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public Integer getSold() {
        return sold;
    }

    public void setSold(Integer sold) {
        this.sold = sold;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        
        hash += (productCouponCode == null ? productCouponCode.hashCode() : 0);
        
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ProductCoupon))
            return false;
        
        ProductCoupon other = (ProductCoupon)obj;
        if ((other.productCouponCode == null && this.productCouponCode != null)
                || (other.productCouponCode != null && !this.productCouponCode.equals(other.productCouponCode)))
            return false;
        
        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getCanonicalName() + "[productCouponCode = " + productCouponCode +"]";
    }
    
}
