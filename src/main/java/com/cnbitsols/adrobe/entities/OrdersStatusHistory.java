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
@Table(name = "orders_status_history")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrdersStatusHistory.findAll", query = "SELECT o FROM OrdersStatusHistory o"),
    @NamedQuery(name = "OrdersStatusHistory.findByOrdersStatusHistoryId", query = "SELECT o FROM OrdersStatusHistory o WHERE o.ordersStatusHistoryId = :ordersStatusHistoryId"),
    @NamedQuery(name = "OrdersStatusHistory.findByOrdersId", query = "SELECT o FROM OrdersStatusHistory o WHERE o.ordersId = :ordersId"),
    @NamedQuery(name = "OrdersStatusHistory.findByOrdersStatusId", query = "SELECT o FROM OrdersStatusHistory o WHERE o.ordersStatusId = :ordersStatusId"),
    @NamedQuery(name = "OrdersStatusHistory.findByDateAdded", query = "SELECT o FROM OrdersStatusHistory o WHERE o.dateAdded = :dateAdded"),
    @NamedQuery(name = "OrdersStatusHistory.findByCustomerNotified", query = "SELECT o FROM OrdersStatusHistory o WHERE o.customerNotified = :customerNotified")})
public class OrdersStatusHistory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "orders_status_history_id")
    private Integer ordersStatusHistoryId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "orders_id")
    private int ordersId;
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

    public OrdersStatusHistory() {
    }

    public OrdersStatusHistory(Integer ordersStatusHistoryId) {
        this.ordersStatusHistoryId = ordersStatusHistoryId;
    }

    public OrdersStatusHistory(Integer ordersStatusHistoryId, int ordersId, int ordersStatusId, Date dateAdded) {
        this.ordersStatusHistoryId = ordersStatusHistoryId;
        this.ordersId = ordersId;
        this.ordersStatusId = ordersStatusId;
        this.dateAdded = dateAdded;
    }

    public Integer getOrdersStatusHistoryId() {
        return ordersStatusHistoryId;
    }

    public void setOrdersStatusHistoryId(Integer ordersStatusHistoryId) {
        this.ordersStatusHistoryId = ordersStatusHistoryId;
    }

    public int getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(int ordersId) {
        this.ordersId = ordersId;
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
        hash += (ordersStatusHistoryId != null ? ordersStatusHistoryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrdersStatusHistory)) {
            return false;
        }
        OrdersStatusHistory other = (OrdersStatusHistory) object;
        if ((this.ordersStatusHistoryId == null && other.ordersStatusHistoryId != null) || (this.ordersStatusHistoryId != null && !this.ordersStatusHistoryId.equals(other.ordersStatusHistoryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cnbitsols.adrobe.entities.OrdersStatusHistory[ ordersStatusHistoryId=" + ordersStatusHistoryId + " ]";
    }
    
}
