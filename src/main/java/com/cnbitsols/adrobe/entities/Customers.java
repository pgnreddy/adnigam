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
@Table(name = "customers")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Customers.findAll", query = "SELECT c FROM Customers c"),
    @NamedQuery(name = "Customers.findByCustomersId", query = "SELECT c FROM Customers c WHERE c.customersId = :customersId"),
    @NamedQuery(name = "Customers.findByGender", query = "SELECT c FROM Customers c WHERE c.gender = :gender"),
    @NamedQuery(name = "Customers.findByFirstname", query = "SELECT c FROM Customers c WHERE c.firstname = :firstname"),
    @NamedQuery(name = "Customers.findByLastname", query = "SELECT c FROM Customers c WHERE c.lastname = :lastname"),
    @NamedQuery(name = "Customers.findByDob", query = "SELECT c FROM Customers c WHERE c.dob = :dob"),
    @NamedQuery(name = "Customers.findByEmailAddress", query = "SELECT c FROM Customers c WHERE c.emailAddress = :emailAddress"),
    @NamedQuery(name = "Customers.findByDefaultAddressId", query = "SELECT c FROM Customers c WHERE c.defaultAddressId = :defaultAddressId"),
    @NamedQuery(name = "Customers.findByTelephone", query = "SELECT c FROM Customers c WHERE c.telephone = :telephone"),
    @NamedQuery(name = "Customers.findByFax", query = "SELECT c FROM Customers c WHERE c.fax = :fax"),
    @NamedQuery(name = "Customers.findByPassword", query = "SELECT c FROM Customers c WHERE c.password = :password"),
    @NamedQuery(name = "Customers.findByNewsletter", query = "SELECT c FROM Customers c WHERE c.newsletter = :newsletter"),
    @NamedQuery(name = "Customers.findByStatus", query = "SELECT c FROM Customers c WHERE c.status = :status"),
    @NamedQuery(name = "Customers.findByDateOfLastLogon", query = "SELECT c FROM Customers c WHERE c.dateOfLastLogon = :dateOfLastLogon"),
    @NamedQuery(name = "Customers.findByNumberOfLogons", query = "SELECT c FROM Customers c WHERE c.numberOfLogons = :numberOfLogons"),
    @NamedQuery(name = "Customers.findByAccountCreated", query = "SELECT c FROM Customers c WHERE c.accountCreated = :accountCreated"),
    @NamedQuery(name = "Customers.findByAccountLastModified", query = "SELECT c FROM Customers c WHERE c.accountLastModified = :accountLastModified"),
    @NamedQuery(name = "Customers.findByGlobalProductNotifications", query = "SELECT c FROM Customers c WHERE c.globalProductNotifications = :globalProductNotifications"),
    @NamedQuery(name = "Customers.findByCurrency", query = "SELECT c FROM Customers c WHERE c.currency = :currency"),
    @NamedQuery(name = "Customers.findByLanguage", query = "SELECT c FROM Customers c WHERE c.language = :language")})
public class Customers implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "customers_id")
    private Integer customersId;
    @Column(name = "gender")
    private String gender;
    @Size(max = 100)
    @Column(name = "firstname")
    private String firstname;
    @Size(max = 100)
    @Column(name = "lastname")
    private String lastname;
    @Size(max = 16)
    @Column(name = "dob")
    private String dob;
    @Size(max = 100)
    @Column(name = "email_address")
    private String emailAddress;
    @Column(name = "default_address_id")
    private Integer defaultAddressId;
    @Size(max = 32)
    @Column(name = "telephone")
    private String telephone;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 32)
    @Column(name = "fax")
    private String fax;
    @Size(max = 255)
    @Column(name = "password")
    private String password;
    @Column(name = "newsletter")
    private Integer newsletter;
    @Column(name = "status")
    private int status=1;
    @Column(name = "date_of_last_logon")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfLastLogon;
    @Column(name = "number_of_logons")
    private Integer numberOfLogons;
    @Column(name = "account_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date accountCreated;
    @Column(name = "account_last_modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date accountLastModified;
    @Column(name = "global_product_notifications")
    private Integer globalProductNotifications;
    @Lob
    @Size(max = 65535)
    @Column(name = "aboutme")
    private String aboutme;
    @Column(name = "currency")
    private Integer currency;
    @Column(name = "language")
    private Integer language;
    @Column(name = "socialsite")
    private String socialsite;
    @Column(name = "socialaccountId")
    private String accountId;
    
    public Customers() {
    }

    public Customers(Integer customersId) {
        this.customersId = customersId;
    }

    public Customers(Integer customersId, String gender, int status) {
        this.customersId = customersId;
        this.gender = gender;
        this.status = status;
    }

    public Integer getCustomersId() {
        return customersId;
    }

    public void setCustomersId(Integer customersId) {
        this.customersId = customersId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Integer getDefaultAddressId() {
        return defaultAddressId;
    }

    public void setDefaultAddressId(Integer defaultAddressId) {
        this.defaultAddressId = defaultAddressId;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getNewsletter() {
        return newsletter;
    }

    public void setNewsletter(Integer newsletter) {
        this.newsletter = newsletter;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getDateOfLastLogon() {
        return dateOfLastLogon;
    }

    public void setDateOfLastLogon(Date dateOfLastLogon) {
        this.dateOfLastLogon = dateOfLastLogon;
    }

    public Integer getNumberOfLogons() {
        return numberOfLogons;
    }

    public void setNumberOfLogons(Integer numberOfLogons) {
        this.numberOfLogons = numberOfLogons;
    }

    public Date getAccountCreated() {
        return accountCreated;
    }

    public void setAccountCreated(Date accountCreated) {
        this.accountCreated = accountCreated;
    }

    public Date getAccountLastModified() {
        return accountLastModified;
    }

    public void setAccountLastModified(Date accountLastModified) {
        this.accountLastModified = accountLastModified;
    }

    public Integer getGlobalProductNotifications() {
        return globalProductNotifications;
    }

    public void setGlobalProductNotifications(Integer globalProductNotifications) {
        this.globalProductNotifications = globalProductNotifications;
    }

    public String getAboutme() {
        return aboutme;
    }

    public void setAboutme(String aboutme) {
        this.aboutme = aboutme;
    }

    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }

    public Integer getLanguage() {
        return language;
    }

    public void setLanguage(Integer language) {
        this.language = language;
    }
    
    public String getSocialsite() {
        return socialsite;
    }

    public void setSocialsite(String socialsite) {
        this.socialsite = socialsite;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customersId != null ? customersId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customers)) {
            return false;
        }
        Customers other = (Customers) object;
        if ((this.customersId == null && other.customersId != null) || (this.customersId != null && !this.customersId.equals(other.customersId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cnbitsols.adrobe.entities.Customers[ customersId=" + customersId + " ]";
    }
    
}
