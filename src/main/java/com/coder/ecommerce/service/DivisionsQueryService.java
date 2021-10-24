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

import com.coder.ecommerce.domain.Divisions;
import com.coder.ecommerce.domain.*; // for static metamodels
import com.coder.ecommerce.repository.DivisionsRepository;
import com.coder.ecommerce.service.dto.DivisionsCriteria;
import com.coder.ecommerce.service.dto.DivisionsDTO;
import com.coder.ecommerce.service.mapper.DivisionsMapper;

/**
 * Service for executing complex queries for {@link Divisions} entities in the database.
 * The main input is a {@link DivisionsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DivisionsDTO} or a {@link Page} of {@link DivisionsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DivisionsQueryService extends QueryService<Divisions> {

    private final Logger log = LoggerFactory.getLogger(DivisionsQueryService.class);

    private final DivisionsRepository divisionsRepository;

    private final DivisionsMapper divisionsMapper;

    public DivisionsQueryService(DivisionsRepository divisionsRepository, DivisionsMapper divisionsMapper) {
        this.divisionsRepository = divisionsRepository;
        this.divisionsMapper = divisionsMapper;
    }

    /**
     * Return a {@link List} of {@link DivisionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DivisionsDTO> findByCriteria(DivisionsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Divisions> specification = createSpecification(criteria);
        return divisionsMapper.toDto(divisionsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DivisionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DivisionsDTO> findByCriteria(DivisionsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Divisions> specification = createSpecification(criteria);
        return divisionsRepository.findAll(specification, page)
            .map(divisionsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DivisionsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Divisions> specification = createSpecification(criteria);
        return divisionsRepository.count(specification);
    }

    /**
     * Function to convert {@link DivisionsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Divisions> createSpecification(DivisionsCriteria criteria) {
        Specification<Divisions> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Divisions_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Divisions_.name));
            }
            if (criteria.getBn_name() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBn_name(), Divisions_.bn_name));
            }
            if (criteria.getUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrl(), Divisions_.url));
            }
        }
        return specification;
    }
}
