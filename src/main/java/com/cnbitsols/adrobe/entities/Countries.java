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
@Table(name = "countries")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Countries.findAll", query = "SELECT c FROM Countries c"),
    @NamedQuery(name = "Countries.findByCountriesId", query = "SELECT c FROM Countries c WHERE c.countriesId = :countriesId"),
    @NamedQuery(name = "Countries.findByCountriesName", query = "SELECT c FROM Countries c WHERE c.countriesName = :countriesName"),
    @NamedQuery(name = "Countries.findByCountriesIsoCode2", query = "SELECT c FROM Countries c WHERE c.countriesIsoCode2 = :countriesIsoCode2"),
    @NamedQuery(name = "Countries.findByCountriesIsoCode3", query = "SELECT c FROM Countries c WHERE c.countriesIsoCode3 = :countriesIsoCode3"),
    @NamedQuery(name = "Countries.findByAddressFormatId", query = "SELECT c FROM Countries c WHERE c.addressFormatId = :addressFormatId")})
public class Countries implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "countries_id")
    private Integer countriesId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "countries_name")
    private String countriesName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "countries_iso_code_2")
    private String countriesIsoCode2;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "countries_iso_code_3")
    private String countriesIsoCode3;
    @Basic(optional = false)
    @NotNull
    @Column(name = "address_format_id")
    private int addressFormatId;

    public Countries() {
    }

    public Countries(Integer countriesId) {
        this.countriesId = countriesId;
    }

    public Countries(Integer countriesId, String countriesName, String countriesIsoCode2, String countriesIsoCode3, int addressFormatId) {
        this.countriesId = countriesId;
        this.countriesName = countriesName;
        this.countriesIsoCode2 = countriesIsoCode2;
        this.countriesIsoCode3 = countriesIsoCode3;
        this.addressFormatId = addressFormatId;
    }

    public Integer getCountriesId() {
        return countriesId;
    }

    public void setCountriesId(Integer countriesId) {
        this.countriesId = countriesId;
    }

    public String getCountriesName() {
        return countriesName;
    }

    public void setCountriesName(String countriesName) {
        this.countriesName = countriesName;
    }

    public String getCountriesIsoCode2() {
        return countriesIsoCode2;
    }

    public void setCountriesIsoCode2(String countriesIsoCode2) {
        this.countriesIsoCode2 = countriesIsoCode2;
    }

    public String getCountriesIsoCode3() {
        return countriesIsoCode3;
    }

    public void setCountriesIsoCode3(String countriesIsoCode3) {
        this.countriesIsoCode3 = countriesIsoCode3;
    }

    public int getAddressFormatId() {
        return addressFormatId;
    }

    public void setAddressFormatId(int addressFormatId) {
        this.addressFormatId = addressFormatId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (countriesId != null ? countriesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Countries)) {
            return false;
        }
        Countries other = (Countries) object;
        if ((this.countriesId == null && other.countriesId != null) || (this.countriesId != null && !this.countriesId.equals(other.countriesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cnbitsols.adrobe.entities.Countries[ countriesId=" + countriesId + " ]";
    }
    
}
