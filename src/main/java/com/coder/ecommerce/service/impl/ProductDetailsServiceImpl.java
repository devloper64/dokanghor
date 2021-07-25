package com.coder.ecommerce.service.impl;

import com.coder.ecommerce.service.ProductDetailsService;
import com.coder.ecommerce.domain.ProductDetails;
import com.coder.ecommerce.repository.ProductDetailsRepository;
import com.coder.ecommerce.service.dto.ProductDetailsDTO;
import com.coder.ecommerce.service.mapper.ProductDetailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link ProductDetails}.
 */
@Service
@Transactional
public class ProductDetailsServiceImpl implements ProductDetailsService {

    private final Logger log = LoggerFactory.getLogger(ProductDetailsServiceImpl.class);

    private final ProductDetailsRepository productDetailsRepository;

    private final ProductDetailsMapper productDetailsMapper;

    public ProductDetailsServiceImpl(ProductDetailsRepository productDetailsRepository, ProductDetailsMapper productDetailsMapper) {
        this.productDetailsRepository = productDetailsRepository;
        this.productDetailsMapper = productDetailsMapper;
    }

    @Override
    public ProductDetailsDTO save(ProductDetailsDTO productDetailsDTO) {
        log.debug("Request to save ProductDetails : {}", productDetailsDTO);
        ProductDetails productDetails = productDetailsMapper.toEntity(productDetailsDTO);
        productDetails = productDetailsRepository.save(productDetails);
        return productDetailsMapper.toDto(productDetails);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductDetails");
        return productDetailsRepository.findAll(pageable)
            .map(productDetailsMapper::toDto);
    }



    /**
     *  Get all the productDetails where Product is {@code null}.
     *  @return the list of entities.
     */
//    @Transactional(readOnly = true)
//    public List<ProductDetailsDTO> findAllWhereProductIsNull() {
//        log.debug("Request to get all productDetails where Product is null");
//        return StreamSupport
//            .stream(productDetailsRepository.findAll().spliterator(), false)
//            .filter(productDetails -> productDetails.getProduct() == null)
//            .map(productDetailsMapper::toDto)
//            .collect(Collectors.toCollection(LinkedList::new));
//    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductDetailsDTO> findOne(Long id) {
        log.debug("Request to get ProductDetails : {}", id);
        return productDetailsRepository.findById(id)
            .map(productDetailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductDetails : {}", id);
        productDetailsRepository.deleteById(id);
    }
}
