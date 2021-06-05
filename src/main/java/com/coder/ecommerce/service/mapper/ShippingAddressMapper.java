package com.coder.ecommerce.service.mapper;


import com.coder.ecommerce.domain.*;
import com.coder.ecommerce.service.dto.ShippingAddressDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ShippingAddress} and its DTO {@link ShippingAddressDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ShippingAddressMapper extends EntityMapper<ShippingAddressDTO, ShippingAddress> {



    default ShippingAddress fromId(Long id) {
        if (id == null) {
            return null;
        }
        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setId(id);
        return shippingAddress;
    }
}
