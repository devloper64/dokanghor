package com.coder.ecommerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Payment.
 */
@Entity
@Table(name = "payment",schema = "public")
public class Payment extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;


    @NotNull
    @Column(name = "individual_amount", nullable = false)
    private String individualAmount;

    @NotNull
    @Column(name = "product_quantities", nullable = false)
    private String productQuantities;

    @NotNull
    @Column(name = "additional_data")
    private String additionalData;

    @NotNull
    @Column(name = "is_active", nullable = false)
    private boolean isActive;


    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "payments", allowSetters = true)
    private User user;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Product> products = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "payments", allowSetters = true)
    private ShippingAddress shippingAddress;


    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "payments", allowSetters = true)
    private OrderStatus orderStatus;


    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public Payment totalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Payment productQuantities(String productQuantities) {
        this.productQuantities = productQuantities;
        return this;
    }


    public String getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(String additionalData) {
        this.additionalData = additionalData;
    }

    public String getProductQuantities() {
        return productQuantities;
    }

    public void setProductQuantities(String productQuantities) {
        this.productQuantities = productQuantities;
    }

    public Payment individualAmount(String individualAmount) {
        this.individualAmount = individualAmount;
        return this;
    }

    public String getIndividualAmount() {
        return individualAmount;
    }

    public void setIndividualAmount(String individualAmount) {
        this.individualAmount = individualAmount;
    }

    public User getUser() {
        return user;
    }

    public Payment user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Payment products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }


    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Payment orderStatus(OrderStatus orderStatus){
        this.orderStatus=orderStatus;
        return this;
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }



    public Payment shippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
        return this;
    }

    public void setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Payment)) {
            return false;
        }
        return id != null && id.equals(((Payment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Payment{" +
            "id=" + getId() +
            ", amount=" + getTotalAmount() +
            "}";
    }
}
