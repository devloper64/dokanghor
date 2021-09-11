package com.coder.ecommerce.service.customBody;

import com.coder.ecommerce.service.dto.ProductDTO;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;
import java.util.Set;

public class PaymentBody {


    private Double totalAmount;
    private Long shippingAddressId;
    private List<ProductsList> productsLists;
    private Long orderStatusId;

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }


    public Long getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(Long shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public List<ProductsList> getProductsLists() {
        return productsLists;
    }

    public void setProductsLists(List<ProductsList> productsLists) {
        this.productsLists = productsLists;
    }

    public Long getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(Long orderStatusId) {
        this.orderStatusId = orderStatusId;
    }
}
