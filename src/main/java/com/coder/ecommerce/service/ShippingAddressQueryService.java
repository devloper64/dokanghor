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

import com.coder.ecommerce.domain.ShippingAddress;
import com.coder.ecommerce.domain.*; // for static metamodels
import com.coder.ecommerce.repository.ShippingAddressRepository;
import com.coder.ecommerce.service.dto.ShippingAddressCriteria;
import com.coder.ecommerce.service.dto.ShippingAddressDTO;
import com.coder.ecommerce.service.mapper.ShippingAddressMapper;

/**
 * Service for executing complex queries for {@link ShippingAddress} entities in the database.
 * The main input is a {@link ShippingAddressCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ShippingAddressDTO} or a {@link Page} of {@link ShippingAddressDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ShippingAddressQueryService extends QueryService<ShippingAddress> {

    private final Logger log = LoggerFactory.getLogger(ShippingAddressQueryService.class);

    private final ShippingAddressRepository shippingAddressRepository;

    private final ShippingAddressMapper shippingAddressMapper;

    public ShippingAddressQueryService(ShippingAddressRepository shippingAddressRepository, ShippingAddressMapper shippingAddressMapper) {
        this.shippingAddressRepository = shippingAddressRepository;
        this.shippingAddressMapper = shippingAddressMapper;
    }

    /**
     * Return a {@link List} of {@link ShippingAddressDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ShippingAddressDTO> findByCriteria(ShippingAddressCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ShippingAddress> specification = createSpecification(criteria);
        return shippingAddressMapper.toDto(shippingAddressRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ShippingAddressDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ShippingAddressDTO> findByCriteria(ShippingAddressCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ShippingAddress> specification = createSpecification(criteria);
        return shippingAddressRepository.findAll(specification, page)
            .map(shippingAddressMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ShippingAddressCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ShippingAddress> specification = createSpecification(criteria);
        return shippingAddressRepository.count(specification);
    }

    /**
     * Function to convert {@link ShippingAddressCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ShippingAddress> createSpecification(ShippingAddressCriteria criteria) {
        Specification<ShippingAddress> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ShippingAddress_.id));
            }
            if (criteria.getDistrict() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDistrict(), ShippingAddress_.district));
            }
            if (criteria.getUpazila() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpazila(), ShippingAddress_.upazila));
            }
            if (criteria.getPostalcode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPostalcode(), ShippingAddress_.postalcode));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(ShippingAddress_.user, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
