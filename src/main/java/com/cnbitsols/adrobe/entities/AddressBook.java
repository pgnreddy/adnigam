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
@Table(name = "address_book")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AddressBook.findAll", query = "SELECT a FROM AddressBook a"),
    @NamedQuery(name = "AddressBook.findByAddressBookId", query = "SELECT a FROM AddressBook a WHERE a.addressBookId = :addressBookId and a.customersId=:customersId"),
    @NamedQuery(name = "AddressBook.findByCustomersId", query = "SELECT a FROM AddressBook a WHERE a.customersId = :customersId")
    })
public class AddressBook implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "address_book_id")
    private Integer addressBookId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "customers_id")
    private int customersId;
    
    @Column(name = "entry_gender")
    private Character entryGender;
    @Size(max = 100)
    @Column(name = "entry_company")
    private String entryCompany;
    @Size(max = 100)
    @Column(name = "entry_firstname")
    private String entryFirstname;
    @Size(max = 100)
    @Column(name = "entry_lastname")
    private String entryLastname;
    @Size(max = 255)
    @Column(name = "entry_street_address")
    private String entryStreetAddress;
    @Size(max = 100)
    @Column(name = "entry_suburb")
    private String entrySuburb;
    @Size(max = 10)
    @Column(name = "entry_postcode")
    private String entryPostcode;
    @Size(max = 100)
    @Column(name = "entry_city")
    private String entryCity;
    @Size(max = 100)
    @Column(name = "entry_state")
    private String entryState;
    
    @Column(name = "entry_country_id")
    private int entryCountryId;
   
    @Column(name = "entry_zone_id")
    private int entryZoneId;

    public AddressBook() {
    }

    public AddressBook(Integer addressBookId) {
        this.addressBookId = addressBookId;
    }

    public AddressBook(Integer addressBookId, int customersId, Character entryGender, int entryCountryId, int entryZoneId) {
        this.addressBookId = addressBookId;
        this.customersId = customersId;
        this.entryGender = entryGender;
        this.entryCountryId = entryCountryId;
        this.entryZoneId = entryZoneId;
    }

    public Integer getAddressBookId() {
        return addressBookId;
    }

    public void setAddressBookId(Integer addressBookId) {
        this.addressBookId = addressBookId;
    }

    public int getCustomersId() {
        return customersId;
    }

    public void setCustomersId(int customersId) {
        this.customersId = customersId;
    }

    public Character getEntryGender() {
        return entryGender;
    }

    public void setEntryGender(Character entryGender) {
        this.entryGender = entryGender;
    }

    public String getEntryCompany() {
        return entryCompany;
    }

    public void setEntryCompany(String entryCompany) {
        this.entryCompany = entryCompany;
    }

    public String getEntryFirstname() {
        return entryFirstname;
    }

    public void setEntryFirstname(String entryFirstname) {
        this.entryFirstname = entryFirstname;
    }

    public String getEntryLastname() {
        return entryLastname;
    }

    public void setEntryLastname(String entryLastname) {
        this.entryLastname = entryLastname;
    }

    public String getEntryStreetAddress() {
        return entryStreetAddress;
    }

    public void setEntryStreetAddress(String entryStreetAddress) {
        this.entryStreetAddress = entryStreetAddress;
    }

    public String getEntrySuburb() {
        return entrySuburb;
    }

    public void setEntrySuburb(String entrySuburb) {
        this.entrySuburb = entrySuburb;
    }

    public String getEntryPostcode() {
        return entryPostcode;
    }

    public void setEntryPostcode(String entryPostcode) {
        this.entryPostcode = entryPostcode;
    }

    public String getEntryCity() {
        return entryCity;
    }

    public void setEntryCity(String entryCity) {
        this.entryCity = entryCity;
    }

    public String getEntryState() {
        return entryState;
    }

    public void setEntryState(String entryState) {
        this.entryState = entryState;
    }

    public int getEntryCountryId() {
        return entryCountryId;
    }

    public void setEntryCountryId(int entryCountryId) {
        this.entryCountryId = entryCountryId;
    }

    public int getEntryZoneId() {
        return entryZoneId;
    }

    public void setEntryZoneId(int entryZoneId) {
        this.entryZoneId = entryZoneId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (addressBookId != null ? addressBookId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AddressBook)) {
            return false;
        }
        AddressBook other = (AddressBook) object;
        if ((this.addressBookId == null && other.addressBookId != null) || (this.addressBookId != null && !this.addressBookId.equals(other.addressBookId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cnbitsols.adrobe.entities.AddressBook[ addressBookId=" + addressBookId + " ]";
    }
    
}
