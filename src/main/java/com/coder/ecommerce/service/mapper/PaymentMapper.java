package com.coder.ecommerce.service.mapper;


import com.coder.ecommerce.domain.*;
import com.coder.ecommerce.service.dto.PaymentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Payment} and its DTO {@link PaymentDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ProductMapper.class, ShippingAddressMapper.class})
public interface PaymentMapper extends EntityMapper<PaymentDTO, Payment> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "shippingAddress.id", target = "shippingAddressId")
    PaymentDTO toDto(Payment payment);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "productId", target = "product")
    @Mapping(source = "shippingAddressId", target = "shippingAddress")
    Payment toEntity(PaymentDTO paymentDTO);

    default Payment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Payment payment = new Payment();
        payment.setId(id);
        return payment;
    }
}
