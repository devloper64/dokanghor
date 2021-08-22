package com.coder.ecommerce.service.mapper;


import com.coder.ecommerce.domain.*;
import com.coder.ecommerce.service.dto.TransactionMethodDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TransactionMethod} and its DTO {@link TransactionMethodDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TransactionMethodMapper extends EntityMapper<TransactionMethodDTO, TransactionMethod> {



    default TransactionMethod fromId(Long id) {
        if (id == null) {
            return null;
        }
        TransactionMethod transactionMethod = new TransactionMethod();
        transactionMethod.setId(id);
        return transactionMethod;
    }
}
