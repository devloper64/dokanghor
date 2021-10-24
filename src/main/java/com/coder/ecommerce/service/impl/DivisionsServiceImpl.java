package com.coder.ecommerce.service.impl;

import com.coder.ecommerce.service.DivisionsService;
import com.coder.ecommerce.domain.Divisions;
import com.coder.ecommerce.repository.DivisionsRepository;
import com.coder.ecommerce.service.dto.DivisionsDTO;
import com.coder.ecommerce.service.mapper.DivisionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Divisions}.
 */
@Service
@Transactional
public class DivisionsServiceImpl implements DivisionsService {

    private final Logger log = LoggerFactory.getLogger(DivisionsServiceImpl.class);

    private final DivisionsRepository divisionsRepository;

    private final DivisionsMapper divisionsMapper;

    public DivisionsServiceImpl(DivisionsRepository divisionsRepository, DivisionsMapper divisionsMapper) {
        this.divisionsRepository = divisionsRepository;
        this.divisionsMapper = divisionsMapper;
    }

    @Override
    public DivisionsDTO save(DivisionsDTO divisionsDTO) {
        log.debug("Request to save Divisions : {}", divisionsDTO);
        Divisions divisions = divisionsMapper.toEntity(divisionsDTO);
        divisions = divisionsRepository.save(divisions);
        return divisionsMapper.toDto(divisions);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DivisionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Divisions");
        return divisionsRepository.findAll(pageable)
            .map(divisionsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DivisionsDTO> findOne(Long id) {
        log.debug("Request to get Divisions : {}", id);
        return divisionsRepository.findById(id)
            .map(divisionsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Divisions : {}", id);
        divisionsRepository.deleteById(id);
    }
}
