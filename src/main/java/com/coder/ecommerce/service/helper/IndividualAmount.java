package com.coder.ecommerce.service.helper;

public class IndividualAmount {

    private String productId;
    private String amount;


    public IndividualAmount(String productId, String amount) {
        this.productId = productId;
        this.amount = amount;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
