package com.coder.ecommerce.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.coder.ecommerce.domain.ShippingAddress} entity.
 */
public class ShippingAddressDTO implements Serializable {

    private Long id;

    @NotNull
    private String district;

    @NotNull
    private String upazila;

    @NotNull
    private String postalcode;

    private String phoneNumber;

    private Long userId;

    private String divisions;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getUpazila() {
        return upazila;
    }

    public void setUpazila(String upazila) {
        this.upazila = upazila;
    }

    public String getPostalcode() {
        return postalcode;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDivisions() {
        return divisions;
    }

    public void setDivisions(String divisions) {
        this.divisions = divisions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShippingAddressDTO)) {
            return false;
        }

        return id != null && id.equals(((ShippingAddressDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShippingAddressDTO{" +
            "id=" + getId() +
            ", district='" + getDistrict() + "'" +
            ", upazila='" + getUpazila() + "'" +
            ", postalcode='" + getPostalcode() + "'" +
            "}";
    }
}
