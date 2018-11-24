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
@Table(name = "feedback")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Feedback.findAll", query = "SELECT f FROM Feedback f"),
    @NamedQuery(name = "Feedback.findByFeedbackId", query = "SELECT f FROM Feedback f WHERE f.feedbackId = :feedbackId"),
    @NamedQuery(name = "Feedback.findByFeedbackCreated", query = "SELECT f FROM Feedback f WHERE f.feedbackCreated = :feedbackCreated"),
    @NamedQuery(name = "Feedback.findByFirstname", query = "SELECT f FROM Feedback f WHERE f.firstname = :firstname"),
    @NamedQuery(name = "Feedback.findByLastname", query = "SELECT f FROM Feedback f WHERE f.lastname = :lastname"),
    @NamedQuery(name = "Feedback.findByStreetAddress", query = "SELECT f FROM Feedback f WHERE f.streetAddress = :streetAddress"),
    @NamedQuery(name = "Feedback.findByCity", query = "SELECT f FROM Feedback f WHERE f.city = :city"),
    @NamedQuery(name = "Feedback.findByPostcode", query = "SELECT f FROM Feedback f WHERE f.postcode = :postcode"),
    @NamedQuery(name = "Feedback.findByCountry", query = "SELECT f FROM Feedback f WHERE f.country = :country"),
    @NamedQuery(name = "Feedback.findByTitle", query = "SELECT f FROM Feedback f WHERE f.title = :title")})
public class Feedback implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "feedback_id")
    private Integer feedbackId;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Lob
    @Size(max = 65535)
    @Column(name = "email")
    private String email;
    @Lob
    @Size(max = 65535)
    @Column(name = "feedback")
    private String feedback;
    @Column(name = "feedback_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date feedbackCreated;
    @Size(max = 45)
    @Column(name = "firstname")
    private String firstname;
    @Size(max = 45)
    @Column(name = "lastname")
    private String lastname;
    @Size(max = 45)
    @Column(name = "street_address")
    private String streetAddress;
    @Size(max = 45)
    @Column(name = "city")
    private String city;
    @Size(max = 45)
    @Column(name = "postcode")
    private String postcode;
    @Size(max = 155)
    @Column(name = "country")
    private String country;
    @Size(max = 45)
    @Column(name = "title")
    private String title;

    public Feedback() {
    }

    public Feedback(Integer feedbackId) {
        this.feedbackId = feedbackId;
    }

    public Integer getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Integer feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Date getFeedbackCreated() {
        return feedbackCreated;
    }

    public void setFeedbackCreated(Date feedbackCreated) {
        this.feedbackCreated = feedbackCreated;
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

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (feedbackId != null ? feedbackId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Feedback)) {
            return false;
        }
        Feedback other = (Feedback) object;
        if ((this.feedbackId == null && other.feedbackId != null) || (this.feedbackId != null && !this.feedbackId.equals(other.feedbackId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cnbitsols.adrobe.entities.Feedback[ feedbackId=" + feedbackId + " ]";
    }
    
}
