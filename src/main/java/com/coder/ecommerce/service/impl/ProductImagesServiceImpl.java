package com.coder.ecommerce.service.impl;

import com.coder.ecommerce.service.ProductImagesService;
import com.coder.ecommerce.domain.ProductImages;
import com.coder.ecommerce.repository.ProductImagesRepository;
import com.coder.ecommerce.service.dto.ProductImagesDTO;
import com.coder.ecommerce.service.mapper.ProductImagesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProductImages}.
 */
@Service
@Transactional
public class ProductImagesServiceImpl implements ProductImagesService {

    private final Logger log = LoggerFactory.getLogger(ProductImagesServiceImpl.class);

    private final ProductImagesRepository productImagesRepository;

    private final ProductImagesMapper productImagesMapper;

    public ProductImagesServiceImpl(ProductImagesRepository productImagesRepository, ProductImagesMapper productImagesMapper) {
        this.productImagesRepository = productImagesRepository;
        this.productImagesMapper = productImagesMapper;
    }

    @Override
    public ProductImagesDTO save(ProductImagesDTO productImagesDTO) {
        log.debug("Request to save ProductImages : {}", productImagesDTO);
        ProductImages productImages = productImagesMapper.toEntity(productImagesDTO);
        productImages = productImagesRepository.save(productImages);
        return productImagesMapper.toDto(productImages);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductImagesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductImages");
        return productImagesRepository.findAll(pageable)
            .map(productImagesMapper::toDto);
    }

    @Override
    public List<ProductImagesDTO> findByProductId(Long id) {
        List<ProductImagesDTO> imagesDTOS=productImagesRepository.findByProductId(id).stream().map(productImagesMapper::toDto).collect(Collectors.toList());
        return imagesDTOS;
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ProductImagesDTO> findOne(Long id) {
        log.debug("Request to get ProductImages : {}", id);
        return productImagesRepository.findById(id)
            .map(productImagesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductImages : {}", id);
        productImagesRepository.deleteById(id);
    }
}
