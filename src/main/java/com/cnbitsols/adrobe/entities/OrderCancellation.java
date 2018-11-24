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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author santosh.r
 */
@Entity
@Table(name = "order_cancellation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrderCancellation.findAll", query = "SELECT o FROM OrderCancellation o"),
    @NamedQuery(name = "OrderCancellation.findByOrderId", query = "SELECT o FROM OrderCancellation o WHERE o.orderId = :orderId"),
    @NamedQuery(name = "OrderCancellation.findByCancelId", query = "SELECT o FROM OrderCancellation o WHERE o.cancelId = :cancelId"),
    @NamedQuery(name = "OrderCancellation.findByCreatedDate", query = "SELECT o FROM OrderCancellation o WHERE o.createdDate = :createdDate")})
public class OrderCancellation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "order_id")
    private Integer orderId;
    @Lob
    @Size(max = 65535)
    @Column(name = "comments")
    private String comments;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cancel_id")
    private Integer cancelId;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public OrderCancellation() {
    }

    public OrderCancellation(Integer cancelId) {
        this.cancelId = cancelId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getCancelId() {
        return cancelId;
    }

    public void setCancelId(Integer cancelId) {
        this.cancelId = cancelId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cancelId != null ? cancelId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderCancellation)) {
            return false;
        }
        OrderCancellation other = (OrderCancellation) object;
        if ((this.cancelId == null && other.cancelId != null) || (this.cancelId != null && !this.cancelId.equals(other.cancelId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cnbitsols.adrobe.entities.OrderCancellation[ cancelId=" + cancelId + " ]";
    }
    
}
