package com.coder.ecommerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Product.
 */
@Entity
@Table(name = "product",schema = "public")
public class Product extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "price", nullable = false)
    private double price;


    @Column(name = "image", nullable = false)
    private String image;


    @Column(name = "discount_amount")
    private double discount_amount;

    @Column(name = "quantity")
    private double quantity;

    @OneToOne
    @JoinColumn(name = "product_details_id")
    private ProductDetails productDetails;



    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "products", allowSetters = true)
    private SubCategory subCategory;

    @ManyToOne
    @JsonIgnoreProperties(value = "products", allowSetters = true)
    private ProductType productType;

    @ManyToOne
    @JsonIgnoreProperties(value = "products", allowSetters = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(double discount_amount) {
        this.discount_amount = discount_amount;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public ProductDetails getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(ProductDetails productDetails) {
        this.productDetails = productDetails;
    }

    public User getUser() {
        return user;
    }

    public Product user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product productDetails(ProductDetails productDetails){
        this.productDetails=productDetails;
        return this;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public Product price(double price) {
        this.price = price;
        return this;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public Product subCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
        return this;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here


    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", price='" + getPrice() + "'" +
            "}";
    }
}
