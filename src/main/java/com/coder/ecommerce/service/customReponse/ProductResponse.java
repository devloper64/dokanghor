package com.coder.ecommerce.service.customReponse;

import com.coder.ecommerce.service.dto.ProductDetailsDTO;

import javax.validation.constraints.NotNull;
import java.time.Instant;

public class ProductResponse {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private double price;

    private String image;


    private Long subCategoryId;

    private String subCategoryName;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private Long productTypeId;

    private double discount_amount;

    private ProductDetailsResponse productDetailsResponse;

    private double quantity;

    public ProductResponse(Long id, String name, double price, String image, Long subCategoryId, String subCategoryName, String createdBy, Instant createdDate, String lastModifiedBy, Instant lastModifiedDate, Long productTypeId, double discount_amount, ProductDetailsResponse productDetailsResponse, double quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.subCategoryId = subCategoryId;
        this.subCategoryName = subCategoryName;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.productTypeId = productTypeId;
        this.discount_amount = discount_amount;
        this.productDetailsResponse = productDetailsResponse;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Long subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

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

    public Long getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Long productTypeId) {
        this.productTypeId = productTypeId;
    }

    public double getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(double discount_amount) {
        this.discount_amount = discount_amount;
    }

    public ProductDetailsResponse getProductDetailsResponse() {
        return productDetailsResponse;
    }

    public void setProductDetailsResponse(ProductDetailsResponse productDetailsResponse) {
        this.productDetailsResponse = productDetailsResponse;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ProductResponse{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", price=" + price +
            ", image='" + image + '\'' +
            ", subCategoryId=" + subCategoryId +
            ", subCategoryName='" + subCategoryName + '\'' +
            ", createdBy='" + createdBy + '\'' +
            ", createdDate=" + createdDate +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            ", lastModifiedDate=" + lastModifiedDate +
            ", productTypeId=" + productTypeId +
            ", discount_amount=" + discount_amount +
            ", productDetailsResponse=" + productDetailsResponse +
            ", quantity=" + quantity +
            '}';
    }
}
