package com.coder.ecommerce.service.impl;

import com.coder.ecommerce.service.DistrictsService;
import com.coder.ecommerce.domain.Districts;
import com.coder.ecommerce.repository.DistrictsRepository;
import com.coder.ecommerce.service.dto.DistrictsDTO;
import com.coder.ecommerce.service.mapper.DistrictsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Districts}.
 */
@Service
@Transactional
public class DistrictsServiceImpl implements DistrictsService {

    private final Logger log = LoggerFactory.getLogger(DistrictsServiceImpl.class);

    private final DistrictsRepository districtsRepository;

    private final DistrictsMapper districtsMapper;

    public DistrictsServiceImpl(DistrictsRepository districtsRepository, DistrictsMapper districtsMapper) {
        this.districtsRepository = districtsRepository;
        this.districtsMapper = districtsMapper;
    }

    @Override
    public DistrictsDTO save(DistrictsDTO districtsDTO) {
        log.debug("Request to save Districts : {}", districtsDTO);
        Districts districts = districtsMapper.toEntity(districtsDTO);
        districts = districtsRepository.save(districts);
        return districtsMapper.toDto(districts);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DistrictsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Districts");
        return districtsRepository.findAll(pageable)
            .map(districtsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DistrictsDTO> findOne(Long id) {
        log.debug("Request to get Districts : {}", id);
        return districtsRepository.findById(id)
            .map(districtsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Districts : {}", id);
        districtsRepository.deleteById(id);
    }
}
