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
@Table(name = "orders_total")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrdersTotal.findAll", query = "SELECT o FROM OrdersTotal o"),
    @NamedQuery(name = "OrdersTotal.findByOrdersTotalId", query = "SELECT o FROM OrdersTotal o WHERE o.ordersTotalId = :ordersTotalId"),
    @NamedQuery(name = "OrdersTotal.findByOrdersId", query = "SELECT o FROM OrdersTotal o WHERE o.ordersId = :ordersId"),
    @NamedQuery(name = "OrdersTotal.findByTitle", query = "SELECT o FROM OrdersTotal o WHERE o.title = :title"),
    @NamedQuery(name = "OrdersTotal.findByText", query = "SELECT o FROM OrdersTotal o WHERE o.text = :text"),
    @NamedQuery(name = "OrdersTotal.findByValue", query = "SELECT o FROM OrdersTotal o WHERE o.value = :value"),
    @NamedQuery(name = "OrdersTotal.findByClass1", query = "SELECT o FROM OrdersTotal o WHERE o.class1 = :class1"),
    @NamedQuery(name = "OrdersTotal.findBySortOrder", query = "SELECT o FROM OrdersTotal o WHERE o.sortOrder = :sortOrder")})
public class OrdersTotal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "orders_total_id")
    private Integer ordersTotalId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "orders_id")
    private int ordersId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "text")
    private String text;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "value")
    private BigDecimal value;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "class")
    private String class1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sort_order")
    private int sortOrder;

    public OrdersTotal() {
    }

    public OrdersTotal(Integer ordersTotalId) {
        this.ordersTotalId = ordersTotalId;
    }

    public OrdersTotal(Integer ordersTotalId, int ordersId, String title, String text, BigDecimal value, String class1, int sortOrder) {
        this.ordersTotalId = ordersTotalId;
        this.ordersId = ordersId;
        this.title = title;
        this.text = text;
        this.value = value;
        this.class1 = class1;
        this.sortOrder = sortOrder;
    }
    
    public OrdersTotal( int ordersId, String title, String text, BigDecimal value, String class1, int sortOrder) {        
        this.ordersId = ordersId;
        this.title = title;
        this.text = text;
        this.value = value;
        this.class1 = class1;
        this.sortOrder = sortOrder;
    }

    public Integer getOrdersTotalId() {
        return ordersTotalId;
    }

    public void setOrdersTotalId(Integer ordersTotalId) {
        this.ordersTotalId = ordersTotalId;
    }

    public int getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(int ordersId) {
        this.ordersId = ordersId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getClass1() {
        return class1;
    }

    public void setClass1(String class1) {
        this.class1 = class1;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ordersTotalId != null ? ordersTotalId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrdersTotal)) {
            return false;
        }
        OrdersTotal other = (OrdersTotal) object;
        if ((this.ordersTotalId == null && other.ordersTotalId != null) || (this.ordersTotalId != null && !this.ordersTotalId.equals(other.ordersTotalId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cnbitsols.adrobe.entities.OrdersTotal[ ordersTotalId=" + ordersTotalId + " ]";
    }
    
}
