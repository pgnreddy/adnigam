/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cnbitsols.adrobe.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author sunil.t
 */
@Entity
@Table(name = "configuration")
public class Configuration implements java.io.Serializable{
    private Long id;
    private String name;
    private String value;
    public Configuration() {
    }
    
    public Configuration(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Column(name = "name", length = 1024)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "value", length = 2048)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    
}
