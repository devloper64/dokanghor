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

import com.coder.ecommerce.domain.Upazilas;
import com.coder.ecommerce.domain.*; // for static metamodels
import com.coder.ecommerce.repository.UpazilasRepository;
import com.coder.ecommerce.service.dto.UpazilasCriteria;
import com.coder.ecommerce.service.dto.UpazilasDTO;
import com.coder.ecommerce.service.mapper.UpazilasMapper;

/**
 * Service for executing complex queries for {@link Upazilas} entities in the database.
 * The main input is a {@link UpazilasCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UpazilasDTO} or a {@link Page} of {@link UpazilasDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UpazilasQueryService extends QueryService<Upazilas> {

    private final Logger log = LoggerFactory.getLogger(UpazilasQueryService.class);

    private final UpazilasRepository upazilasRepository;

    private final UpazilasMapper upazilasMapper;

    public UpazilasQueryService(UpazilasRepository upazilasRepository, UpazilasMapper upazilasMapper) {
        this.upazilasRepository = upazilasRepository;
        this.upazilasMapper = upazilasMapper;
    }

    /**
     * Return a {@link List} of {@link UpazilasDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UpazilasDTO> findByCriteria(UpazilasCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Upazilas> specification = createSpecification(criteria);
        return upazilasMapper.toDto(upazilasRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UpazilasDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UpazilasDTO> findByCriteria(UpazilasCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Upazilas> specification = createSpecification(criteria);
        return upazilasRepository.findAll(specification, page)
            .map(upazilasMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UpazilasCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Upazilas> specification = createSpecification(criteria);
        return upazilasRepository.count(specification);
    }

    /**
     * Function to convert {@link UpazilasCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Upazilas> createSpecification(UpazilasCriteria criteria) {
        Specification<Upazilas> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Upazilas_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Upazilas_.name));
            }
            if (criteria.getBn_name() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBn_name(), Upazilas_.bn_name));
            }
            if (criteria.getUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrl(), Upazilas_.url));
            }
            if (criteria.getDistrictsId() != null) {
                specification = specification.and(buildSpecification(criteria.getDistrictsId(),
                    root -> root.join(Upazilas_.districts, JoinType.LEFT).get(Districts_.id)));
            }
        }
        return specification;
    }
}
