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

import com.coder.ecommerce.domain.TransactionMethod;
import com.coder.ecommerce.domain.*; // for static metamodels
import com.coder.ecommerce.repository.TransactionMethodRepository;
import com.coder.ecommerce.service.dto.TransactionMethodCriteria;
import com.coder.ecommerce.service.dto.TransactionMethodDTO;
import com.coder.ecommerce.service.mapper.TransactionMethodMapper;

/**
 * Service for executing complex queries for {@link TransactionMethod} entities in the database.
 * The main input is a {@link TransactionMethodCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TransactionMethodDTO} or a {@link Page} of {@link TransactionMethodDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TransactionMethodQueryService extends QueryService<TransactionMethod> {

    private final Logger log = LoggerFactory.getLogger(TransactionMethodQueryService.class);

    private final TransactionMethodRepository transactionMethodRepository;

    private final TransactionMethodMapper transactionMethodMapper;

    public TransactionMethodQueryService(TransactionMethodRepository transactionMethodRepository, TransactionMethodMapper transactionMethodMapper) {
        this.transactionMethodRepository = transactionMethodRepository;
        this.transactionMethodMapper = transactionMethodMapper;
    }

    /**
     * Return a {@link List} of {@link TransactionMethodDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TransactionMethodDTO> findByCriteria(TransactionMethodCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TransactionMethod> specification = createSpecification(criteria);
        return transactionMethodMapper.toDto(transactionMethodRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TransactionMethodDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TransactionMethodDTO> findByCriteria(TransactionMethodCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TransactionMethod> specification = createSpecification(criteria);
        return transactionMethodRepository.findAll(specification, page)
            .map(transactionMethodMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TransactionMethodCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TransactionMethod> specification = createSpecification(criteria);
        return transactionMethodRepository.count(specification);
    }

    /**
     * Function to convert {@link TransactionMethodCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TransactionMethod> createSpecification(TransactionMethodCriteria criteria) {
        Specification<TransactionMethod> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TransactionMethod_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), TransactionMethod_.name));
            }
        }
        return specification;
    }
}
