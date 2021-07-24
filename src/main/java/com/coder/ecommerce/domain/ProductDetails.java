package com.coder.ecommerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ProductDetails.
 */
@Entity
@Table(name = "product_details")
public class ProductDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "brand")
    private String brand;

    @Column(name = "color")
    private String color;

    @Column(name = "gender")
    private String gender;

    @Column(name = "style")
    private String style;

    @Column(name = "size_mesaurments")
    private String size_mesaurments;

    @Column(name = "size_details")
    private String size_details;

    @OneToOne(mappedBy = "productDetails")
    @JsonIgnore
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public ProductDetails brand(String brand) {
        this.brand = brand;
        return this;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public ProductDetails color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getGender() {
        return gender;
    }

    public ProductDetails gender(String gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStyle() {
        return style;
    }

    public ProductDetails style(String style) {
        this.style = style;
        return this;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getSize_mesaurments() {
        return size_mesaurments;
    }

    public ProductDetails size_mesaurments(String size_mesaurments) {
        this.size_mesaurments = size_mesaurments;
        return this;
    }

    public void setSize_mesaurments(String size_mesaurments) {
        this.size_mesaurments = size_mesaurments;
    }

    public String getSize_details() {
        return size_details;
    }

    public ProductDetails size_details(String size_details) {
        this.size_details = size_details;
        return this;
    }

    public void setSize_details(String size_details) {
        this.size_details = size_details;
    }

    public Product getProduct() {
        return product;
    }

    public ProductDetails product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductDetails)) {
            return false;
        }
        return id != null && id.equals(((ProductDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductDetails{" +
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
