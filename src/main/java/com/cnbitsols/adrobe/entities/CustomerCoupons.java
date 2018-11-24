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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author santosh.r
 */
@Entity
@Table(name = "customer_coupons")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CustomerCoupons.findAll", query = "SELECT c FROM CustomerCoupons c"),
    @NamedQuery(name = "CustomerCoupons.findByCustomerCouponId", query = "SELECT c FROM CustomerCoupons c WHERE c.customerCouponId = :customerCouponId"),
    @NamedQuery(name = "CustomerCoupons.findByCouponId", query = "SELECT c FROM CustomerCoupons c WHERE c.couponId = :couponId"),
    @NamedQuery(name = "CustomerCoupons.findByCustomerId", query = "SELECT c FROM CustomerCoupons c WHERE c.customerId = :customerId"),
    @NamedQuery(name = "CustomerCoupons.findByCustomerIdAndCouponId", query = "SELECT c FROM CustomerCoupons c WHERE c.customerId = :customerId and c.couponId = :couponId"),
    @NamedQuery(name = "CustomerCoupons.findByNumberOfTimesUsed", query = "SELECT c FROM CustomerCoupons c WHERE c.numberOfTimesUsed = :numberOfTimesUsed"),
    @NamedQuery(name = "CustomerCoupons.findByCouponStartDate", query = "SELECT c FROM CustomerCoupons c WHERE c.couponStartDate = :couponStartDate"),
    @NamedQuery(name = "CustomerCoupons.findByCouponEndDate", query = "SELECT c FROM CustomerCoupons c WHERE c.couponEndDate = :couponEndDate")})
public class CustomerCoupons implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "customer_coupon_id")
    private Integer customerCouponId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "coupon_id")
    private int couponId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "customer_id")
    private int customerId;
    @Column(name = "number_of_times_used")
    private Integer numberOfTimesUsed;
    @Column(name = "coupon_start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date couponStartDate;
    @Column(name = "coupon_end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date couponEndDate;

    public CustomerCoupons() {
    }

    public CustomerCoupons(Integer customerCouponId) {
        this.customerCouponId = customerCouponId;
    }

    public CustomerCoupons(Integer customerCouponId, int couponId, int customerId) {
        this.customerCouponId = customerCouponId;
        this.couponId = couponId;
        this.customerId = customerId;
    }

    public Integer getCustomerCouponId() {
        return customerCouponId;
    }

    public void setCustomerCouponId(Integer customerCouponId) {
        this.customerCouponId = customerCouponId;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Integer getNumberOfTimesUsed() {
        return numberOfTimesUsed;
    }

    public void setNumberOfTimesUsed(Integer numberOfTimesUsed) {
        this.numberOfTimesUsed = numberOfTimesUsed;
    }

    public Date getCouponStartDate() {
        return couponStartDate;
    }

    public void setCouponStartDate(Date couponStartDate) {
        this.couponStartDate = couponStartDate;
    }

    public Date getCouponEndDate() {
        return couponEndDate;
    }

    public void setCouponEndDate(Date couponEndDate) {
        this.couponEndDate = couponEndDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerCouponId != null ? customerCouponId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomerCoupons)) {
            return false;
        }
        CustomerCoupons other = (CustomerCoupons) object;
        if ((this.customerCouponId == null && other.customerCouponId != null) || (this.customerCouponId != null && !this.customerCouponId.equals(other.customerCouponId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cnbitsols.adrobe.entities.CustomerCoupons[ customerCouponId=" + customerCouponId + " ]";
    }
    
}
