package com.coder.ecommerce.service.dto;

import com.coder.ecommerce.domain.OrderStatus;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

/**
 * A DTO for the {@link com.coder.ecommerce.domain.Payment} entity.
 */
public class PaymentDTO implements Serializable {

    private Long id;

    @NotNull
    private Double totalAmount;


    private Long userId;

    private String userLogin;
    private Set<ProductDTO> products;
    private Long shippingAddressId;
    private String createdBy;
    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private String productQuantities;
    private String individualAmount;
    private boolean isActive;

    private Long orderStatusId;

    private String additionalData;



    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getProductQuantities() {
        return productQuantities;
    }

    public void setProductQuantities(String productQuantities) {
        this.productQuantities = productQuantities;
    }

    public String getIndividualAmount() {
        return individualAmount;
    }

    public void setIndividualAmount(String individualAmount) {
        this.individualAmount = individualAmount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Set<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductDTO> products) {
        this.products = products;
    }

    public Long getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(Long shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Long getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(Long orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public String getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(String additionalData) {
        this.additionalData = additionalData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentDTO)) {
            return false;
        }

        return id != null && id.equals(((PaymentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentDTO{" +
            "id=" + getId() +
            ", amount=" + getTotalAmount() +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            ", shippingAddressId=" + getShippingAddressId() +
            ", orderStatusId=" + getOrderStatusId() +
            "}";
    }
}
