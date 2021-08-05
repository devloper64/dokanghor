package com.coder.ecommerce.service.impl;

import com.coder.ecommerce.domain.ProductDetails;
import com.coder.ecommerce.repository.ProductDetailsRepository;
import com.coder.ecommerce.service.ProductService;
import com.coder.ecommerce.domain.Product;
import com.coder.ecommerce.repository.ProductRepository;
import com.coder.ecommerce.service.dto.ProductDTO;
import com.coder.ecommerce.service.dto.ProductDetailsDTO;
import com.coder.ecommerce.service.mapper.ProductDetailsMapper;
import com.coder.ecommerce.service.mapper.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Product}.
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;
    private final ProductDetailsRepository productDetailsRepository;

    private final ProductMapper productMapper;
    private final ProductDetailsMapper productDetailsMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, ProductDetailsRepository productDetailsRepository, ProductDetailsMapper productDetailsMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.productDetailsRepository=productDetailsRepository;
        this.productDetailsMapper = productDetailsMapper;
    }

    @Override
    public ProductDTO save(ProductDTO productDTO) {
        log.debug("Request to save Product : {}", productDTO);
        ProductDetails productDetails=productDetailsMapper.toEntity(productDTO.getProductDetails());
        productDetails=productDetailsRepository.save(productDetails);
        ProductDetailsDTO productDetailsDTO=productDetailsMapper.toDto(productDetails);
        productDTO.setProductDetails(productDetailsDTO);
        Product product = productMapper.toEntity(productDTO);
        product = productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Products");
        return productRepository.findAll(pageable)
            .map(productMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ProductDTO> findOne(Long id) {
        log.debug("Request to get Product : {}", id);
        return productRepository.findById(id)
            .map(productMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Product : {}", id);
        Optional<Product> product=productRepository.findById(id);
        productRepository.deleteById(id);
        product.ifPresent(product1 -> {
            productDetailsRepository.delete(product1.getProductDetails());
        });
    }
}
