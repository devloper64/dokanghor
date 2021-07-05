package com.coder.ecommerce.service.impl;

import com.coder.ecommerce.service.MobileIntroService;
import com.coder.ecommerce.domain.MobileIntro;
import com.coder.ecommerce.repository.MobileIntroRepository;
import com.coder.ecommerce.service.dto.MobileIntroDTO;
import com.coder.ecommerce.service.mapper.MobileIntroMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MobileIntro}.
 */
@Service
@Transactional
public class MobileIntroServiceImpl implements MobileIntroService {

    private final Logger log = LoggerFactory.getLogger(MobileIntroServiceImpl.class);

    private final MobileIntroRepository mobileIntroRepository;

    private final MobileIntroMapper mobileIntroMapper;

    public MobileIntroServiceImpl(MobileIntroRepository mobileIntroRepository, MobileIntroMapper mobileIntroMapper) {
        this.mobileIntroRepository = mobileIntroRepository;
        this.mobileIntroMapper = mobileIntroMapper;
    }

    @Override
    public MobileIntroDTO save(MobileIntroDTO mobileIntroDTO) {
        log.debug("Request to save MobileIntro : {}", mobileIntroDTO);
        MobileIntro mobileIntro = mobileIntroMapper.toEntity(mobileIntroDTO);
        mobileIntro = mobileIntroRepository.save(mobileIntro);
        return mobileIntroMapper.toDto(mobileIntro);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MobileIntroDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MobileIntros");
        return mobileIntroRepository.findAll(pageable)
            .map(mobileIntroMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<MobileIntroDTO> findOne(Long id) {
        log.debug("Request to get MobileIntro : {}", id);
        return mobileIntroRepository.findById(id)
            .map(mobileIntroMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MobileIntro : {}", id);
        mobileIntroRepository.deleteById(id);
    }
}
