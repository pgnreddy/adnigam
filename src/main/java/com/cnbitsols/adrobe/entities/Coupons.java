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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author santosh.r
 */
@Entity
@Table(name = "coupons")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Coupons.findAll", query = "SELECT c FROM Coupons c"),
    @NamedQuery(name = "Coupons.findById", query = "SELECT c FROM Coupons c WHERE c.id = :id"),
    @NamedQuery(name = "Coupons.findByCode", query = "SELECT c FROM Coupons c WHERE c.code = :code")    
    })
public class Coupons implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "code")
    private String code;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "title")
    private String title;
    @Size(max = 512)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "discount_type")
    private int discount_type;
    @Basic(optional = false)
    @NotNull
    @Column(name = "discount_value")
    private long discount_value;
    @Basic(optional = false)
    @NotNull
    @Column(name = "usage_type")
    private int usage_type;
    @Column(name = "max_redeem_count")
    private Integer max_redeem_count;
    @Column(name = "number_of_redeems")
    private Integer number_of_redeems=0;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "min_amount")
    private Double min_amount;
    @Column(name = "status")
    private Integer status;
    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date start_date;
    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date end_date;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_date;
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_date;
    @Column(name = "allow_multiple_redeems_by_same_user")
    private Integer allow_multiple_redeems_by_same_user;
    @Column(name = "number_of_times_to_use")
    private Integer number_of_times_to_use;

    public Coupons() {
    }

    public Coupons(Integer id) {
        this.id = id;
    }

    public Coupons(Integer id, String code, String title, int discountType, long discountValue, int usageType) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.discount_type = discountType;
        this.discount_value = discountValue;
        this.usage_type = usageType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public int getDiscount_type() {
        return discount_type;
    }

    public void setDiscount_type(int discount_type) {
        this.discount_type = discount_type;
    }

    public long getDiscount_value() {
        return discount_value;
    }

    public void setDiscount_value(long discount_value) {
        this.discount_value = discount_value;
    }

    public int getUsage_type() {
        return usage_type;
    }

    public void setUsage_type(int usage_type) {
        this.usage_type = usage_type;
    }

    public Integer getMax_redeem_count() {
        return max_redeem_count;
    }

    public void setMax_redeem_count(Integer max_redeem_count) {
        this.max_redeem_count = max_redeem_count;
    }

    public Integer getNumber_of_redeems() {
        return number_of_redeems;
    }

    public void setNumber_of_redeems(Integer number_of_redeems) {
        this.number_of_redeems = number_of_redeems;
    }

    public Double getMin_amount() {
        return min_amount;
    }

    public void setMin_amount(Double min_amount) {
        this.min_amount = min_amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public Date getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(Date updated_date) {
        this.updated_date = updated_date;
    }

    public Integer getAllow_multiple_redeems_by_same_user() {
        return allow_multiple_redeems_by_same_user;
    }

    public void setAllow_multiple_redeems_by_same_user(Integer allow_multiple_redeems_by_same_user) {
        this.allow_multiple_redeems_by_same_user = allow_multiple_redeems_by_same_user;
    }

    public Integer getNumber_of_times_to_use() {
        return number_of_times_to_use;
    }

    public void setNumber_of_times_to_use(Integer number_of_times_to_use) {
        this.number_of_times_to_use = number_of_times_to_use;
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
        if (!(object instanceof Coupons)) {
            return false;
        }
        Coupons other = (Coupons) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cnbitsols.adrobe.entities.Coupons[ id=" + id + " ]";
    }
    
}
