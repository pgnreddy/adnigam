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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author santosh.r
 */
@Entity
@Table(name = "orders_status")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrdersStatus.findAll", query = "SELECT o FROM OrdersStatus o"),
    @NamedQuery(name = "OrdersStatus.findByOrdersStatusId", query = "SELECT o FROM OrdersStatus o WHERE o.ordersStatusId = :ordersStatusId"),
    @NamedQuery(name = "OrdersStatus.findByLanguageId", query = "SELECT o FROM OrdersStatus o WHERE o.languageId = :languageId"),
    @NamedQuery(name = "OrdersStatus.findByOrdersStatusName", query = "SELECT o FROM OrdersStatus o WHERE o.ordersStatusName = :ordersStatusName"),
    @NamedQuery(name = "OrdersStatus.findByPublicFlag", query = "SELECT o FROM OrdersStatus o WHERE o.publicFlag = :publicFlag"),
    @NamedQuery(name = "OrdersStatus.findByDownloadsFlag", query = "SELECT o FROM OrdersStatus o WHERE o.downloadsFlag = :downloadsFlag")})
public class OrdersStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "orders_status_id")
    private Integer ordersStatusId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "language_id")
    private int languageId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "orders_status_name")
    private String ordersStatusName;
    @Column(name = "public_flag")
    private Integer publicFlag;
    @Column(name = "downloads_flag")
    private Integer downloadsFlag;

    public OrdersStatus() {
    }

    public OrdersStatus(Integer ordersStatusId) {
        this.ordersStatusId = ordersStatusId;
    }

    public OrdersStatus(Integer ordersStatusId, int languageId, String ordersStatusName) {
        this.ordersStatusId = ordersStatusId;
        this.languageId = languageId;
        this.ordersStatusName = ordersStatusName;
    }

    public Integer getOrdersStatusId() {
        return ordersStatusId;
    }

    public void setOrdersStatusId(Integer ordersStatusId) {
        this.ordersStatusId = ordersStatusId;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public String getOrdersStatusName() {
        return ordersStatusName;
    }

    public void setOrdersStatusName(String ordersStatusName) {
        this.ordersStatusName = ordersStatusName;
    }

    public Integer getPublicFlag() {
        return publicFlag;
    }

    public void setPublicFlag(Integer publicFlag) {
        this.publicFlag = publicFlag;
    }

    public Integer getDownloadsFlag() {
        return downloadsFlag;
    }

    public void setDownloadsFlag(Integer downloadsFlag) {
        this.downloadsFlag = downloadsFlag;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ordersStatusId != null ? ordersStatusId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrdersStatus)) {
            return false;
        }
        OrdersStatus other = (OrdersStatus) object;
        if ((this.ordersStatusId == null && other.ordersStatusId != null) || (this.ordersStatusId != null && !this.ordersStatusId.equals(other.ordersStatusId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cnbitsols.adrobe.entities.OrdersStatus[ ordersStatusId=" + ordersStatusId + " ]";
    }
    
}
