package com.coder.ecommerce.service.helper;

import com.coder.ecommerce.service.customBody.AdditionalData;

import java.util.List;

public class AdditionalDataHelper {

    private String productId;
    List<AdditionalData> additionalDataList;


    public AdditionalDataHelper(String productId, List<AdditionalData> additionalDataList) {
        this.productId = productId;
        this.additionalDataList = additionalDataList;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public List<AdditionalData> getAdditionalDataList() {
        return additionalDataList;
    }

    public void setAdditionalDataList(List<AdditionalData> additionalDataList) {
        this.additionalDataList = additionalDataList;
    }
}
