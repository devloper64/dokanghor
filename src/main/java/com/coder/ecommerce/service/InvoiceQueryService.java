package com.coder.ecommerce.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.coder.ecommerce.domain.Invoice;
import com.coder.ecommerce.domain.*; // for static metamodels
import com.coder.ecommerce.repository.InvoiceRepository;
import com.coder.ecommerce.service.dto.InvoiceCriteria;
import com.coder.ecommerce.service.dto.InvoiceDTO;
import com.coder.ecommerce.service.mapper.InvoiceMapper;

/**
 * Service for executing complex queries for {@link Invoice} entities in the database.
 * The main input is a {@link InvoiceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InvoiceDTO} or a {@link Page} of {@link InvoiceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InvoiceQueryService extends QueryService<Invoice> {

    private final Logger log = LoggerFactory.getLogger(InvoiceQueryService.class);

    private final InvoiceRepository invoiceRepository;

    private final InvoiceMapper invoiceMapper;

    public InvoiceQueryService(InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
    }

    /**
     * Return a {@link List} of {@link InvoiceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InvoiceDTO> findByCriteria(InvoiceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Invoice> specification = createSpecification(criteria);
        return invoiceMapper.toDto(invoiceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link InvoiceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InvoiceDTO> findByCriteria(InvoiceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Invoice> specification = createSpecification(criteria);
        return invoiceRepository.findAll(specification, page)
            .map(invoiceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InvoiceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Invoice> specification = createSpecification(criteria);
        return invoiceRepository.count(specification);
    }

    /**
     * Function to convert {@link InvoiceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Invoice> createSpecification(InvoiceCriteria criteria) {
        Specification<Invoice> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Invoice_.id));
            }
            if (criteria.getInvoice_number() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInvoice_number(), Invoice_.invoice_number));
            }
            if (criteria.getTo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTo(), Invoice_.to));
            }
            if (criteria.getItem_list() != null) {
                specification = specification.and(buildStringSpecification(criteria.getItem_list(), Invoice_.item_list));
            }
            if (criteria.getSubtotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSubtotal(), Invoice_.subtotal));
            }
            if (criteria.getDiscount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiscount(), Invoice_.discount));
            }
            if (criteria.getVat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVat(), Invoice_.vat));
            }
            if (criteria.getTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotal(), Invoice_.total));
            }
            if (criteria.getInvoice_date() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInvoice_date(), Invoice_.invoice_date));
            }
            if (criteria.getTransactionId() != null) {
                specification = specification.and(buildSpecification(criteria.getTransactionId(),
                    root -> root.join(Invoice_.transaction, JoinType.LEFT).get(Transaction_.id)));
            }
        }
        return specification;
    }
}
