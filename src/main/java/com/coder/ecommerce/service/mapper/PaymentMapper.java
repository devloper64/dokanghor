package com.coder.ecommerce.service.mapper;


import com.coder.ecommerce.domain.*;
import com.coder.ecommerce.service.dto.PaymentDTO;

import org.mapstruct.*;


/**
 * Mapper for the entity {@link Payment} and its DTO {@link PaymentDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ProductMapper.class, ShippingAddressMapper.class, OrderStatusMapper.class})
public interface PaymentMapper extends EntityMapper<PaymentDTO, Payment> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "shippingAddress.id", target = "shippingAddressId")
    @Mapping(source = "orderStatus.id", target = "orderStatusId")
    PaymentDTO toDto(Payment payment);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "shippingAddressId", target = "shippingAddress")
    @Mapping(source = "orderStatusId", target = "orderStatus")
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
