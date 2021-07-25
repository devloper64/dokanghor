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
 * Criteria class for the {@link com.coder.ecommerce.domain.ProductDetails} entity. This class is used
 * in {@link com.coder.ecommerce.web.rest.ProductDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /product-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductDetailsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter brand;

    private StringFilter color;

    private StringFilter gender;

    private StringFilter style;

    private StringFilter size_mesaurments;

    private StringFilter size_details;


    public ProductDetailsCriteria() {
    }

    public ProductDetailsCriteria(ProductDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.brand = other.brand == null ? null : other.brand.copy();
        this.color = other.color == null ? null : other.color.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.style = other.style == null ? null : other.style.copy();
        this.size_mesaurments = other.size_mesaurments == null ? null : other.size_mesaurments.copy();
        this.size_details = other.size_details == null ? null : other.size_details.copy();
    }

    @Override
    public ProductDetailsCriteria copy() {
        return new ProductDetailsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getBrand() {
        return brand;
    }

    public void setBrand(StringFilter brand) {
        this.brand = brand;
    }

    public StringFilter getColor() {
        return color;
    }

    public void setColor(StringFilter color) {
        this.color = color;
    }

    public StringFilter getGender() {
        return gender;
    }

    public void setGender(StringFilter gender) {
        this.gender = gender;
    }

    public StringFilter getStyle() {
        return style;
    }

    public void setStyle(StringFilter style) {
        this.style = style;
    }

    public StringFilter getSize_mesaurments() {
        return size_mesaurments;
    }

    public void setSize_mesaurments(StringFilter size_mesaurments) {
        this.size_mesaurments = size_mesaurments;
    }

    public StringFilter getSize_details() {
        return size_details;
    }

    public void setSize_details(StringFilter size_details) {
        this.size_details = size_details;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductDetailsCriteria that = (ProductDetailsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(brand, that.brand) &&
            Objects.equals(color, that.color) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(style, that.style) &&
            Objects.equals(size_mesaurments, that.size_mesaurments) &&
            Objects.equals(size_details, that.size_details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        brand,
        color,
        gender,
        style,
        size_mesaurments,
        size_details
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductDetailsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (brand != null ? "brand=" + brand + ", " : "") +
                (color != null ? "color=" + color + ", " : "") +
                (gender != null ? "gender=" + gender + ", " : "") +
                (style != null ? "style=" + style + ", " : "") +
                (size_mesaurments != null ? "size_mesaurments=" + size_mesaurments + ", " : "") +
                (size_details != null ? "size_details=" + size_details + ", " : "") +
            "}";
    }

}
