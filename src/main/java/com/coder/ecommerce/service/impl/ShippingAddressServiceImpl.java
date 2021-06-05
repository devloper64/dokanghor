package com.coder.ecommerce.service.impl;

import com.coder.ecommerce.service.ShippingAddressService;
import com.coder.ecommerce.domain.ShippingAddress;
import com.coder.ecommerce.repository.ShippingAddressRepository;
import com.coder.ecommerce.service.dto.ShippingAddressDTO;
import com.coder.ecommerce.service.mapper.ShippingAddressMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ShippingAddress}.
 */
@Service
@Transactional
public class ShippingAddressServiceImpl implements ShippingAddressService {

    private final Logger log = LoggerFactory.getLogger(ShippingAddressServiceImpl.class);

    private final ShippingAddressRepository shippingAddressRepository;

    private final ShippingAddressMapper shippingAddressMapper;

    public ShippingAddressServiceImpl(ShippingAddressRepository shippingAddressRepository, ShippingAddressMapper shippingAddressMapper) {
        this.shippingAddressRepository = shippingAddressRepository;
        this.shippingAddressMapper = shippingAddressMapper;
    }

    @Override
    public ShippingAddressDTO save(ShippingAddressDTO shippingAddressDTO) {
        log.debug("Request to save ShippingAddress : {}", shippingAddressDTO);
        ShippingAddress shippingAddress = shippingAddressMapper.toEntity(shippingAddressDTO);
        shippingAddress = shippingAddressRepository.save(shippingAddress);
        return shippingAddressMapper.toDto(shippingAddress);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ShippingAddressDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ShippingAddresses");
        return shippingAddressRepository.findAll(pageable)
            .map(shippingAddressMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ShippingAddressDTO> findOne(Long id) {
        log.debug("Request to get ShippingAddress : {}", id);
        return shippingAddressRepository.findById(id)
            .map(shippingAddressMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShippingAddress : {}", id);
        shippingAddressRepository.deleteById(id);
    }
}
