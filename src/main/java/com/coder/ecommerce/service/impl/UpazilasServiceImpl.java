package com.coder.ecommerce.service.impl;

import com.coder.ecommerce.service.UpazilasService;
import com.coder.ecommerce.domain.Upazilas;
import com.coder.ecommerce.repository.UpazilasRepository;
import com.coder.ecommerce.service.dto.UpazilasDTO;
import com.coder.ecommerce.service.mapper.UpazilasMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Upazilas}.
 */
@Service
@Transactional
public class UpazilasServiceImpl implements UpazilasService {

    private final Logger log = LoggerFactory.getLogger(UpazilasServiceImpl.class);

    private final UpazilasRepository upazilasRepository;

    private final UpazilasMapper upazilasMapper;

    public UpazilasServiceImpl(UpazilasRepository upazilasRepository, UpazilasMapper upazilasMapper) {
        this.upazilasRepository = upazilasRepository;
        this.upazilasMapper = upazilasMapper;
    }

    @Override
    public UpazilasDTO save(UpazilasDTO upazilasDTO) {
        log.debug("Request to save Upazilas : {}", upazilasDTO);
        Upazilas upazilas = upazilasMapper.toEntity(upazilasDTO);
        upazilas = upazilasRepository.save(upazilas);
        return upazilasMapper.toDto(upazilas);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UpazilasDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Upazilas");
        return upazilasRepository.findAll(pageable)
            .map(upazilasMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UpazilasDTO> findOne(Long id) {
        log.debug("Request to get Upazilas : {}", id);
        return upazilasRepository.findById(id)
            .map(upazilasMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Upazilas : {}", id);
        upazilasRepository.deleteById(id);
    }
}
