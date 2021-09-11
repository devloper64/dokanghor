package com.coder.ecommerce.service.mapper;


import com.coder.ecommerce.domain.*;
import com.coder.ecommerce.service.dto.InvoiceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Invoice} and its DTO {@link InvoiceDTO}.
 */
@Mapper(componentModel = "spring", uses = {TransactionMapper.class})
public interface InvoiceMapper extends EntityMapper<InvoiceDTO, Invoice> {

    @Mapping(source = "transaction.id", target = "transactionId")
    InvoiceDTO toDto(Invoice invoice);

    @Mapping(source = "transactionId", target = "transaction")
    Invoice toEntity(InvoiceDTO invoiceDTO);

    default Invoice fromId(Long id) {
        if (id == null) {
            return null;
        }
        Invoice invoice = new Invoice();
        invoice.setId(id);
        return invoice;
    }
}
