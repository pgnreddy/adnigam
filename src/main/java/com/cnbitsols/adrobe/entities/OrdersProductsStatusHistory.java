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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author santosh.r
 */
@Entity
@Table(name = "orders_products_status_history")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrdersProductsStatusHistory.findAll", query = "SELECT o FROM OrdersProductsStatusHistory o"),
    @NamedQuery(name = "OrdersProductsStatusHistory.findByOrdersProductsStatusHistoryId", query = "SELECT o FROM OrdersProductsStatusHistory o WHERE o.ordersProductsStatusHistoryId = :ordersProductsStatusHistoryId"),
    @NamedQuery(name = "OrdersProductsStatusHistory.findByOrdersProductsId", query = "SELECT o FROM OrdersProductsStatusHistory o WHERE o.ordersProductsId = :ordersProductsId"),
    @NamedQuery(name = "OrdersProductsStatusHistory.findByOrdersStatusId", query = "SELECT o FROM OrdersProductsStatusHistory o WHERE o.ordersStatusId = :ordersStatusId"),
    @NamedQuery(name = "OrdersProductsStatusHistory.findByDateAdded", query = "SELECT o FROM OrdersProductsStatusHistory o WHERE o.dateAdded = :dateAdded"),
    @NamedQuery(name = "OrdersProductsStatusHistory.findByCustomerNotified", query = "SELECT o FROM OrdersProductsStatusHistory o WHERE o.customerNotified = :customerNotified")})
public class OrdersProductsStatusHistory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "orders_products_status_history_id")
    private Integer ordersProductsStatusHistoryId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "orders_products_id")
    private int ordersProductsId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "orders_status_id")
    private int ordersStatusId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_added")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAdded;
    @Column(name = "customer_notified")
    private Integer customerNotified;
    @Lob
    @Size(max = 65535)
    @Column(name = "comments")
    private String comments;

    public OrdersProductsStatusHistory() {
    }

    public OrdersProductsStatusHistory(Integer ordersProductsStatusHistoryId) {
        this.ordersProductsStatusHistoryId = ordersProductsStatusHistoryId;
    }

    public OrdersProductsStatusHistory(Integer ordersProductsStatusHistoryId, int ordersProductsId, int ordersStatusId, Date dateAdded) {
        this.ordersProductsStatusHistoryId = ordersProductsStatusHistoryId;
        this.ordersProductsId = ordersProductsId;
        this.ordersStatusId = ordersStatusId;
        this.dateAdded = dateAdded;
    }

    public Integer getOrdersProductsStatusHistoryId() {
        return ordersProductsStatusHistoryId;
    }

    public void setOrdersProductsStatusHistoryId(Integer ordersProductsStatusHistoryId) {
        this.ordersProductsStatusHistoryId = ordersProductsStatusHistoryId;
    }

    public int getOrdersProductsId() {
        return ordersProductsId;
    }

    public void setOrdersProductsId(int ordersProductsId) {
        this.ordersProductsId = ordersProductsId;
    }

    public int getOrdersStatusId() {
        return ordersStatusId;
    }

    public void setOrdersStatusId(int ordersStatusId) {
        this.ordersStatusId = ordersStatusId;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Integer getCustomerNotified() {
        return customerNotified;
    }

    public void setCustomerNotified(Integer customerNotified) {
        this.customerNotified = customerNotified;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ordersProductsStatusHistoryId != null ? ordersProductsStatusHistoryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrdersProductsStatusHistory)) {
            return false;
        }
        OrdersProductsStatusHistory other = (OrdersProductsStatusHistory) object;
        if ((this.ordersProductsStatusHistoryId == null && other.ordersProductsStatusHistoryId != null) || (this.ordersProductsStatusHistoryId != null && !this.ordersProductsStatusHistoryId.equals(other.ordersProductsStatusHistoryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cnbitsols.adrobe.entities.OrdersProductsStatusHistory[ ordersProductsStatusHistoryId=" + ordersProductsStatusHistoryId + " ]";
    }
    
}
