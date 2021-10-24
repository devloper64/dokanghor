package com.coder.ecommerce.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ShippingAddress.
 */
@Entity
@Table(name = "shipping_address")
public class ShippingAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "district", nullable = false)
    private String district;

    @NotNull
    @Column(name = "upazila", nullable = false)
    private String upazila;

    @NotNull
    @Column(name = "postalcode", nullable = false)
    private String postalcode;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "divisions")
    private String divisions;

    @ManyToOne
    @JsonIgnoreProperties(value = "shipping_address", allowSetters = true)
    private User user;


    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDistrict() {
        return district;
    }

    public ShippingAddress district(String district) {
        this.district = district;
        return this;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getUpazila() {
        return upazila;
    }

    public ShippingAddress upazila(String upazila) {
        this.upazila = upazila;
        return this;
    }

    public void setUpazila(String upazila) {
        this.upazila = upazila;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public ShippingAddress postalcode(String postalcode) {
        this.postalcode = postalcode;
        return this;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDivisions() {
        return divisions;
    }

    public void setDivisions(String divisions) {
        this.divisions = divisions;
    }

    public User getUser() {
        return user;
    }

    public ShippingAddress user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }


    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShippingAddress)) {
            return false;
        }
        return id != null && id.equals(((ShippingAddress) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShippingAddress{" +
            "id=" + getId() +
            ", district='" + getDistrict() + "'" +
            ", upazila='" + getUpazila() + "'" +
            ", postalcode='" + getPostalcode() + "'" +
            "}";
    }
}
