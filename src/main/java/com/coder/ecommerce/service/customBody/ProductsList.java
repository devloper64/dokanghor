package com.coder.ecommerce.service.customBody;

import java.util.List;

public class ProductsList {
    private Long productId;
    private int quantity;
    private double amount;
    private List<AdditionalData> additionalData;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public List<AdditionalData> getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(List<AdditionalData> additionalData) {
        this.additionalData = additionalData;
    }
}


