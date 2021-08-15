package com.coder.ecommerce.service.mapper;


import com.coder.ecommerce.domain.*;
import com.coder.ecommerce.service.dto.OrderStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrderStatus} and its DTO {@link OrderStatusDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrderStatusMapper extends EntityMapper<OrderStatusDTO, OrderStatus> {



    default OrderStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setId(id);
        return orderStatus;
    }
}
