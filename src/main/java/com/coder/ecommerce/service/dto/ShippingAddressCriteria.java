package com.coder.ecommerce.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.coder.ecommerce.domain.ShippingAddress} entity. This class is used
 * in {@link com.coder.ecommerce.web.rest.ShippingAddressResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /shipping-addresses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ShippingAddressCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter district;

    private StringFilter upazila;

    private StringFilter postalcode;

    private LongFilter userId;


    public ShippingAddressCriteria() {
    }

    public ShippingAddressCriteria(ShippingAddressCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.district = other.district == null ? null : other.district.copy();
        this.upazila = other.upazila == null ? null : other.upazila.copy();
        this.postalcode = other.postalcode == null ? null : other.postalcode.copy();
        this.userId = other.userId == null ? null : other.userId.copy();

    }

    @Override
    public ShippingAddressCriteria copy() {
        return new ShippingAddressCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDistrict() {
        return district;
    }

    public void setDistrict(StringFilter district) {
        this.district = district;
    }

    public StringFilter getUpazila() {
        return upazila;
    }

    public void setUpazila(StringFilter upazila) {
        this.upazila = upazila;
    }

    public StringFilter getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(StringFilter postalcode) {
        this.postalcode = postalcode;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ShippingAddressCriteria that = (ShippingAddressCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(district, that.district) &&
            Objects.equals(upazila, that.upazila) &&
            Objects.equals(postalcode, that.postalcode) &&
            Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        district,
        upazila,
        postalcode,
            userId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShippingAddressCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (district != null ? "district=" + district + ", " : "") +
                (upazila != null ? "upazila=" + upazila + ", " : "") +
                (postalcode != null ? "postalcode=" + postalcode + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
