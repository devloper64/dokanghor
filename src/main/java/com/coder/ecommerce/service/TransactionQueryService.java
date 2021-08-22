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

import com.coder.ecommerce.domain.Transaction;
import com.coder.ecommerce.domain.*; // for static metamodels
import com.coder.ecommerce.repository.TransactionRepository;
import com.coder.ecommerce.service.dto.TransactionCriteria;
import com.coder.ecommerce.service.dto.TransactionDTO;
import com.coder.ecommerce.service.mapper.TransactionMapper;

/**
 * Service for executing complex queries for {@link Transaction} entities in the database.
 * The main input is a {@link TransactionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TransactionDTO} or a {@link Page} of {@link TransactionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TransactionQueryService extends QueryService<Transaction> {

    private final Logger log = LoggerFactory.getLogger(TransactionQueryService.class);

    private final TransactionRepository transactionRepository;

    private final TransactionMapper transactionMapper;

    public TransactionQueryService(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    /**
     * Return a {@link List} of {@link TransactionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TransactionDTO> findByCriteria(TransactionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Transaction> specification = createSpecification(criteria);
        return transactionMapper.toDto(transactionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TransactionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TransactionDTO> findByCriteria(TransactionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Transaction> specification = createSpecification(criteria);
        return transactionRepository.findAll(specification, page)
            .map(transactionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TransactionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Transaction> specification = createSpecification(criteria);
        return transactionRepository.count(specification);
    }

    /**
     * Function to convert {@link TransactionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Transaction> createSpecification(TransactionCriteria criteria) {
        Specification<Transaction> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Transaction_.id));
            }
            if (criteria.getTransactionid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTransactionid(), Transaction_.transactionid));
            }

            if (criteria.getIs_transaction_completed() != null) {
                specification = specification.and(buildSpecification(criteria.getIs_transaction_completed(), Transaction_.is_transaction_completed));
            }

            if (criteria.getPaymentId() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentId(),
                    root -> root.join(Transaction_.payment, JoinType.LEFT).get(Payment_.id)));
            }
            if (criteria.getTransactionMethodId() != null) {
                specification = specification.and(buildSpecification(criteria.getTransactionMethodId(),
                    root -> root.join(Transaction_.transactionMethod, JoinType.LEFT).get(TransactionMethod_.id)));
            }
        }
        return specification;
    }
}
