/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cnbitsols.adrobe.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author santosh.r
 */
@Entity
@Table(name = "orders")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orders.findAll", query = "SELECT o FROM Orders o order by o.ordersId desc"),
    @NamedQuery(name = "Orders.findByOrdersId", query = "SELECT o FROM Orders o WHERE o.ordersId = :ordersId"),
    @NamedQuery(name = "Orders.findByCustomersId", query = "SELECT o FROM Orders o WHERE o.customersId = :customersId"),
    @NamedQuery(name = "Orders.findByCustomersName", query = "SELECT o FROM Orders o WHERE o.customersName = :customersName"),
    @NamedQuery(name = "Orders.findByCustomersCompany", query = "SELECT o FROM Orders o WHERE o.customersCompany = :customersCompany"),
    @NamedQuery(name = "Orders.findByCustomersStreetAddress", query = "SELECT o FROM Orders o WHERE o.customersStreetAddress = :customersStreetAddress"),
    @NamedQuery(name = "Orders.findByCustomersSuburb", query = "SELECT o FROM Orders o WHERE o.customersSuburb = :customersSuburb"),
    @NamedQuery(name = "Orders.findByCustomersCity", query = "SELECT o FROM Orders o WHERE o.customersCity = :customersCity"),
    @NamedQuery(name = "Orders.findByCustomersPostcode", query = "SELECT o FROM Orders o WHERE o.customersPostcode = :customersPostcode"),
    @NamedQuery(name = "Orders.findByCustomersState", query = "SELECT o FROM Orders o WHERE o.customersState = :customersState"),
    @NamedQuery(name = "Orders.findByCustomersCountry", query = "SELECT o FROM Orders o WHERE o.customersCountry = :customersCountry"),
    @NamedQuery(name = "Orders.findByCustomersTelephone", query = "SELECT o FROM Orders o WHERE o.customersTelephone = :customersTelephone"),
    @NamedQuery(name = "Orders.findByCustomersEmailAddress", query = "SELECT o FROM Orders o WHERE o.customersEmailAddress = :customersEmailAddress"),
    @NamedQuery(name = "Orders.findByCustomersAddressFormatId", query = "SELECT o FROM Orders o WHERE o.customersAddressFormatId = :customersAddressFormatId"),
    @NamedQuery(name = "Orders.findByDeliveryName", query = "SELECT o FROM Orders o WHERE o.deliveryName = :deliveryName"),
    @NamedQuery(name = "Orders.findByDeliveryCompany", query = "SELECT o FROM Orders o WHERE o.deliveryCompany = :deliveryCompany"),
    @NamedQuery(name = "Orders.findByDeliveryStreetAddress", query = "SELECT o FROM Orders o WHERE o.deliveryStreetAddress = :deliveryStreetAddress"),
    @NamedQuery(name = "Orders.findByDeliverySuburb", query = "SELECT o FROM Orders o WHERE o.deliverySuburb = :deliverySuburb"),
    @NamedQuery(name = "Orders.findByDeliveryCity", query = "SELECT o FROM Orders o WHERE o.deliveryCity = :deliveryCity"),
    @NamedQuery(name = "Orders.findByDeliveryPostcode", query = "SELECT o FROM Orders o WHERE o.deliveryPostcode = :deliveryPostcode"),
    @NamedQuery(name = "Orders.findByDeliveryState", query = "SELECT o FROM Orders o WHERE o.deliveryState = :deliveryState"),
    @NamedQuery(name = "Orders.findByDeliveryCountry", query = "SELECT o FROM Orders o WHERE o.deliveryCountry = :deliveryCountry"),
    @NamedQuery(name = "Orders.findByDeliveryAddressFormatId", query = "SELECT o FROM Orders o WHERE o.deliveryAddressFormatId = :deliveryAddressFormatId"),
    @NamedQuery(name = "Orders.findByBillingName", query = "SELECT o FROM Orders o WHERE o.billingName = :billingName"),
    @NamedQuery(name = "Orders.findByBillingCompany", query = "SELECT o FROM Orders o WHERE o.billingCompany = :billingCompany"),
    @NamedQuery(name = "Orders.findByBillingStreetAddress", query = "SELECT o FROM Orders o WHERE o.billingStreetAddress = :billingStreetAddress"),
    @NamedQuery(name = "Orders.findByBillingSuburb", query = "SELECT o FROM Orders o WHERE o.billingSuburb = :billingSuburb"),
    @NamedQuery(name = "Orders.findByBillingCity", query = "SELECT o FROM Orders o WHERE o.billingCity = :billingCity"),
    @NamedQuery(name = "Orders.findByBillingPostcode", query = "SELECT o FROM Orders o WHERE o.billingPostcode = :billingPostcode"),
    @NamedQuery(name = "Orders.findByBillingState", query = "SELECT o FROM Orders o WHERE o.billingState = :billingState"),
    @NamedQuery(name = "Orders.findByBillingCountry", query = "SELECT o FROM Orders o WHERE o.billingCountry = :billingCountry"),
    @NamedQuery(name = "Orders.findByBillingAddressFormatId", query = "SELECT o FROM Orders o WHERE o.billingAddressFormatId = :billingAddressFormatId"),
    @NamedQuery(name = "Orders.findByPaymentMethod", query = "SELECT o FROM Orders o WHERE o.paymentMethod = :paymentMethod"),
    @NamedQuery(name = "Orders.findByCcType", query = "SELECT o FROM Orders o WHERE o.ccType = :ccType"),
    @NamedQuery(name = "Orders.findByCcOwner", query = "SELECT o FROM Orders o WHERE o.ccOwner = :ccOwner"),
    @NamedQuery(name = "Orders.findByCcNumber", query = "SELECT o FROM Orders o WHERE o.ccNumber = :ccNumber"),
    @NamedQuery(name = "Orders.findByCcExpires", query = "SELECT o FROM Orders o WHERE o.ccExpires = :ccExpires"),
    @NamedQuery(name = "Orders.findByLastModified", query = "SELECT o FROM Orders o WHERE o.lastModified = :lastModified"),
    @NamedQuery(name = "Orders.findByDatePurchased", query = "SELECT o FROM Orders o WHERE o.datePurchased = :datePurchased"),
    @NamedQuery(name = "Orders.findByOrdersStatus", query = "SELECT o FROM Orders o WHERE o.ordersStatus = :ordersStatus"),
    @NamedQuery(name = "Orders.findByOrdersDateFinished", query = "SELECT o FROM Orders o WHERE o.ordersDateFinished = :ordersDateFinished"),
    @NamedQuery(name = "Orders.findByCurrency", query = "SELECT o FROM Orders o WHERE o.currency = :currency"),
    @NamedQuery(name = "Orders.findByCurrencyValue", query = "SELECT o FROM Orders o WHERE o.currencyValue = :currencyValue")})
public class Orders implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "orders_id")
    private Integer ordersId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "customers_id")
    private int customersId;
    @Size(max = 255)
    @Column(name = "customers_name")
    private String customersName;
    @Size(max = 255)
    @Column(name = "customers_company")
    private String customersCompany;
    @Size(max = 255)
    @Column(name = "customers_street_address")
    private String customersStreetAddress;
    @Size(max = 255)
    @Column(name = "customers_suburb")
    private String customersSuburb;
    @Size(max = 255)
    @Column(name = "customers_city")
    private String customersCity;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "customers_postcode")
    private String customersPostcode;
    @Size(max = 255)
    @Column(name = "customers_state")
    private String customersState;
    @Size(max = 255)
    @Column(name = "customers_country")
    private String customersCountry;
    @Size(max = 255)
    @Column(name = "customers_telephone")
    private String customersTelephone;
    @Size(max = 255)
    @Column(name = "customers_email_address")
    private String customersEmailAddress;
    @Basic(optional = false)
    @NotNull
    @Column(name = "customers_address_format_id")
    private int customersAddressFormatId;
    @Size(max = 255)
    @Column(name = "delivery_name")
    private String deliveryName;
    @Size(max = 255)
    @Column(name = "delivery_company")
    private String deliveryCompany;
    @Size(max = 255)
    @Column(name = "delivery_street_address")
    private String deliveryStreetAddress;
    @Size(max = 255)
    @Column(name = "delivery_suburb")
    private String deliverySuburb;
    @Size(max = 255)
    @Column(name = "delivery_city")
    private String deliveryCity;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "delivery_postcode")
    private String deliveryPostcode;
    @Size(max = 255)
    @Column(name = "delivery_state")
    private String deliveryState;
    @Size(max = 255)
    @Column(name = "delivery_country")
    private String deliveryCountry;
    @Basic(optional = false)
    @NotNull
    @Column(name = "delivery_address_format_id")
    private int deliveryAddressFormatId;
    @Size(max = 255)
    @Column(name = "billing_name")
    private String billingName;
    @Size(max = 255)
    @Column(name = "billing_company")
    private String billingCompany;
    @Size(max = 255)
    @Column(name = "billing_street_address")
    private String billingStreetAddress;
    @Size(max = 255)
    @Column(name = "billing_suburb")
    private String billingSuburb;
    @Size(max = 255)
    @Column(name = "billing_city")
    private String billingCity;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "billing_postcode")
    private String billingPostcode;
    @Size(max = 255)
    @Column(name = "billing_state")
    private String billingState;
    @Size(max = 255)
    @Column(name = "billing_country")
    private String billingCountry;
    @Basic(optional = false)
    @NotNull
    @Column(name = "billing_address_format_id")
    private int billingAddressFormatId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "payment_method")
    private String paymentMethod;
    @Size(max = 255)
    @Column(name = "cc_type")
    private String ccType;
    @Size(max = 255)
    @Column(name = "cc_owner")
    private String ccOwner;
    @Size(max = 32)
    @Column(name = "cc_number")
    private String ccNumber;
    @Size(max = 4)
    @Column(name = "cc_expires")
    private String ccExpires;
    @Column(name = "last_modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified;
    @Column(name = "date_purchased")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datePurchased;
    @Basic(optional = false)
    @NotNull
    @Column(name = "orders_status")
    private int ordersStatus;
    @Column(name = "orders_date_finished")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ordersDateFinished;
    @Size(max = 3)
    @Column(name = "currency")
    private String currency;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "currency_value")
    private BigDecimal currencyValue;
    
    @Column(name = "couponId")
    private int couponId;
    
    @Column(name = "admin_read")
    private int read;
    
    @Transient
    private List<OrdersTotal> orderTotalList;
    @Transient
    private List<OrdersProducts> orderProducts;
    @Transient
    private List<UserRequests> userRequests;
    
    @Transient
    private OrdersStatus orderStatus;

    public List<OrdersProducts> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrdersProducts> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public OrdersStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrdersStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
    
    

    public Orders() {
    }

    public Orders(Integer ordersId) {
        this.ordersId = ordersId;
    }

    public Orders(Integer ordersId, int customersId, String customersPostcode, int customersAddressFormatId, String deliveryPostcode, int deliveryAddressFormatId, String billingPostcode, int billingAddressFormatId, String paymentMethod, int ordersStatus) {
        this.ordersId = ordersId;
        this.customersId = customersId;
        this.customersPostcode = customersPostcode;
        this.customersAddressFormatId = customersAddressFormatId;
        this.deliveryPostcode = deliveryPostcode;
        this.deliveryAddressFormatId = deliveryAddressFormatId;
        this.billingPostcode = billingPostcode;
        this.billingAddressFormatId = billingAddressFormatId;
        this.paymentMethod = paymentMethod;
        this.ordersStatus = ordersStatus;
    }

    public Integer getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Integer ordersId) {
        this.ordersId = ordersId;
    }

    public int getCustomersId() {
        return customersId;
    }

    public void setCustomersId(int customersId) {
        this.customersId = customersId;
    }

    public String getCustomersName() {
        return customersName;
    }

    public void setCustomersName(String customersName) {
        this.customersName = customersName;
    }

    public String getCustomersCompany() {
        return customersCompany;
    }

    public void setCustomersCompany(String customersCompany) {
        this.customersCompany = customersCompany;
    }

    public String getCustomersStreetAddress() {
        return customersStreetAddress;
    }

    public void setCustomersStreetAddress(String customersStreetAddress) {
        this.customersStreetAddress = customersStreetAddress;
    }

    public String getCustomersSuburb() {
        return customersSuburb;
    }

    public void setCustomersSuburb(String customersSuburb) {
        this.customersSuburb = customersSuburb;
    }

    public String getCustomersCity() {
        return customersCity;
    }

    public void setCustomersCity(String customersCity) {
        this.customersCity = customersCity;
    }

    public String getCustomersPostcode() {
        return customersPostcode;
    }

    public void setCustomersPostcode(String customersPostcode) {
        this.customersPostcode = customersPostcode;
    }

    public String getCustomersState() {
        return customersState;
    }

    public void setCustomersState(String customersState) {
        this.customersState = customersState;
    }

    public String getCustomersCountry() {
        return customersCountry;
    }

    public void setCustomersCountry(String customersCountry) {
        this.customersCountry = customersCountry;
    }

    public String getCustomersTelephone() {
        return customersTelephone;
    }

    public void setCustomersTelephone(String customersTelephone) {
        this.customersTelephone = customersTelephone;
    }

    public String getCustomersEmailAddress() {
        return customersEmailAddress;
    }

    public void setCustomersEmailAddress(String customersEmailAddress) {
        this.customersEmailAddress = customersEmailAddress;
    }

    public int getCustomersAddressFormatId() {
        return customersAddressFormatId;
    }

    public void setCustomersAddressFormatId(int customersAddressFormatId) {
        this.customersAddressFormatId = customersAddressFormatId;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public String getDeliveryCompany() {
        return deliveryCompany;
    }

    public void setDeliveryCompany(String deliveryCompany) {
        this.deliveryCompany = deliveryCompany;
    }

    public String getDeliveryStreetAddress() {
        return deliveryStreetAddress;
    }

    public void setDeliveryStreetAddress(String deliveryStreetAddress) {
        this.deliveryStreetAddress = deliveryStreetAddress;
    }

    public String getDeliverySuburb() {
        return deliverySuburb;
    }

    public void setDeliverySuburb(String deliverySuburb) {
        this.deliverySuburb = deliverySuburb;
    }

    public String getDeliveryCity() {
        return deliveryCity;
    }

    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
    }

    public String getDeliveryPostcode() {
        return deliveryPostcode;
    }

    public void setDeliveryPostcode(String deliveryPostcode) {
        this.deliveryPostcode = deliveryPostcode;
    }

    public String getDeliveryState() {
        return deliveryState;
    }

    public void setDeliveryState(String deliveryState) {
        this.deliveryState = deliveryState;
    }

    public String getDeliveryCountry() {
        return deliveryCountry;
    }

    public void setDeliveryCountry(String deliveryCountry) {
        this.deliveryCountry = deliveryCountry;
    }

    public int getDeliveryAddressFormatId() {
        return deliveryAddressFormatId;
    }

    public void setDeliveryAddressFormatId(int deliveryAddressFormatId) {
        this.deliveryAddressFormatId = deliveryAddressFormatId;
    }

    public String getBillingName() {
        return billingName;
    }

    public void setBillingName(String billingName) {
        this.billingName = billingName;
    }

    public String getBillingCompany() {
        return billingCompany;
    }

    public void setBillingCompany(String billingCompany) {
        this.billingCompany = billingCompany;
    }

    public String getBillingStreetAddress() {
        return billingStreetAddress;
    }

    public void setBillingStreetAddress(String billingStreetAddress) {
        this.billingStreetAddress = billingStreetAddress;
    }

    public String getBillingSuburb() {
        return billingSuburb;
    }

    public void setBillingSuburb(String billingSuburb) {
        this.billingSuburb = billingSuburb;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    public String getBillingPostcode() {
        return billingPostcode;
    }

    public void setBillingPostcode(String billingPostcode) {
        this.billingPostcode = billingPostcode;
    }

    public String getBillingState() {
        return billingState;
    }

    public void setBillingState(String billingState) {
        this.billingState = billingState;
    }

    public String getBillingCountry() {
        return billingCountry;
    }

    public void setBillingCountry(String billingCountry) {
        this.billingCountry = billingCountry;
    }

    public int getBillingAddressFormatId() {
        return billingAddressFormatId;
    }

    public void setBillingAddressFormatId(int billingAddressFormatId) {
        this.billingAddressFormatId = billingAddressFormatId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCcType() {
        return ccType;
    }

    public void setCcType(String ccType) {
        this.ccType = ccType;
    }

    public String getCcOwner() {
        return ccOwner;
    }

    public void setCcOwner(String ccOwner) {
        this.ccOwner = ccOwner;
    }

    public String getCcNumber() {
        return ccNumber;
    }

    public void setCcNumber(String ccNumber) {
        this.ccNumber = ccNumber;
    }

    public String getCcExpires() {
        return ccExpires;
    }

    public void setCcExpires(String ccExpires) {
        this.ccExpires = ccExpires;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Date getDatePurchased() {
        return datePurchased;
    }

    public void setDatePurchased(Date datePurchased) {
        this.datePurchased = datePurchased;
    }

    public int getOrdersStatus() {
        return ordersStatus;
    }

    public void setOrdersStatus(int ordersStatus) {
        this.ordersStatus = ordersStatus;
    }

    public Date getOrdersDateFinished() {
        return ordersDateFinished;
    }

    public void setOrdersDateFinished(Date ordersDateFinished) {
        this.ordersDateFinished = ordersDateFinished;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getCurrencyValue() {
        return currencyValue;
    }

    public void setCurrencyValue(BigDecimal currencyValue) {
        this.currencyValue = currencyValue;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ordersId != null ? ordersId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orders)) {
            return false;
        }
        Orders other = (Orders) object;
        if ((this.ordersId == null && other.ordersId != null) || (this.ordersId != null && !this.ordersId.equals(other.ordersId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cnbitsols.adrobe.entities.Orders[ ordersId=" + ordersId + " ]";
    }

    public void setOrderTotalList(List<OrdersTotal> orderTotalList) {
        this.orderTotalList = orderTotalList;
    }

    public void setOrdersProducts(List<OrdersProducts> orderProducts) {
        this.orderProducts = orderProducts;
    }
    
    public List<OrdersTotal> getOrderTotalList() {
        return this.orderTotalList ;
    }

    public List<OrdersProducts> getOrdersProducts() {
        return this.orderProducts ;
    }

    public void setUserRequests(List<UserRequests> userRequests) {
        this.userRequests=userRequests;
    }
    
     public List<UserRequests> getUserRequests() {
        return this.userRequests ;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

}
