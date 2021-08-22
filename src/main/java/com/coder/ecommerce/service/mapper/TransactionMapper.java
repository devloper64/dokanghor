package com.coder.ecommerce.service.mapper;


import com.coder.ecommerce.domain.*;
import com.coder.ecommerce.service.dto.TransactionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Transaction} and its DTO {@link TransactionDTO}.
 */
@Mapper(componentModel = "spring", uses = {PaymentMapper.class,TransactionMethodMapper.class})
public interface TransactionMapper extends EntityMapper<TransactionDTO, Transaction> {

    @Mapping(source = "payment.id", target = "paymentId")
    @Mapping(source = "transactionMethod.id", target = "transactionMethodId")
    TransactionDTO toDto(Transaction transaction);

    @Mapping(source = "paymentId", target = "payment")
    @Mapping(source = "transactionMethodId", target = "transactionMethod")
    Transaction toEntity(TransactionDTO transactionDTO);

    default Transaction fromId(Long id) {
        if (id == null) {
            return null;
        }
        Transaction transaction = new Transaction();
        transaction.setId(id);
        return transaction;
    }
}
