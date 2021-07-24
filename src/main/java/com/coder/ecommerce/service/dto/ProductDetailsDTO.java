package com.coder.ecommerce.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.coder.ecommerce.domain.ProductDetails} entity.
 */
public class ProductDetailsDTO implements Serializable {
    
    private Long id;

    private String brand;

    private String color;

    private String gender;

    private String style;

    private String size_mesaurments;

    private String size_details;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getSize_mesaurments() {
        return size_mesaurments;
    }

    public void setSize_mesaurments(String size_mesaurments) {
        this.size_mesaurments = size_mesaurments;
    }

    public String getSize_details() {
        return size_details;
    }

    public void setSize_details(String size_details) {
        this.size_details = size_details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductDetailsDTO)) {
            return false;
        }

        return id != null && id.equals(((ProductDetailsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductDetailsDTO{" +
            "id=" + getId() +
            ", brand='" + getBrand() + "'" +
            ", color='" + getColor() + "'" +
            ", gender='" + getGender() + "'" +
            ", style='" + getStyle() + "'" +
            ", size_mesaurments='" + getSize_mesaurments() + "'" +
            ", size_details='" + getSize_details() + "'" +
            "}";
    }
}
