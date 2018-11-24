/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cnbitsols.adrobe.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rajeshk
 */
@Entity
@Table(name = "ad_prints")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdPrint.findByRef", query = "FROM AdPrint ad WHERE ad.adReferer = :adRef")
})
public class AdPrint implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ad_print_id")
    private Integer adPrintID;
    
    @Column(name = "ad_referrer")
    @Size(max = 45)
    private String adReferer;
    
    @Column(name = "ad_referrer_count")
    private Integer adReferrerCount;

    public Integer getAdPrintID() {
        return adPrintID;
    }

    public void setAdPrintID(Integer adPrintID) {
        this.adPrintID = adPrintID;
    }

    public String getAdReferer() {
        return adReferer;
    }

    public void setAdReferer(String adReferer) {
        this.adReferer = adReferer;
    }

    public Integer getAdReferrerCount() {
        return adReferrerCount;
    }

    public void setAdReferrerCount(Integer adReferrerCount) {
        this.adReferrerCount = adReferrerCount;
    }
    
}
