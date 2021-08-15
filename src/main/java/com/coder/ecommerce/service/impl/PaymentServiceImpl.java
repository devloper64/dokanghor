package com.coder.ecommerce.service.impl;

import com.coder.ecommerce.domain.Product;
import com.coder.ecommerce.repository.ProductRepository;
import com.coder.ecommerce.service.PaymentService;
import com.coder.ecommerce.domain.Payment;
import com.coder.ecommerce.repository.PaymentRepository;
import com.coder.ecommerce.service.ProductService;
import com.coder.ecommerce.service.dto.PaymentDTO;
import com.coder.ecommerce.service.dto.ProductDTO;
import com.coder.ecommerce.service.helper.IndividualQuantity;
import com.coder.ecommerce.service.mapper.PaymentMapper;
import com.coder.ecommerce.service.mapper.ProductMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.*;

/**
 * Service Implementation for managing {@link Payment}.
 */
@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private final PaymentRepository paymentRepository;

    private final PaymentMapper paymentMapper;
    private final ProductMapper productMapper;
    private final ProductService productService;
    private final ProductRepository productRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentMapper paymentMapper, ProductMapper productMapper, ProductService productService, ProductRepository productRepository) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.productMapper = productMapper;
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @Override
    public PaymentDTO save(PaymentDTO paymentDTO) {
        log.debug("Request to save Payment : {}", paymentDTO);
        Payment payment = paymentMapper.toEntity(paymentDTO);
        Set<ProductDTO>productDTOs=paymentDTO.getProducts();
        Set<ProductDTO>productDTOStore=new HashSet<>();
        for (ProductDTO productDTO:productDTOs) {
            Optional<Product> productOptional=productRepository.findById(productDTO.getId());
            if (productOptional.isPresent()){
                ProductDTO dto=productMapper.toDto(productOptional.get());
                productDTOStore.add(dto);
            }
        }
        Gson g = new Gson();
        List<IndividualQuantity> individualQuantityList=new ArrayList<>();
        Type listQuantity = new TypeToken<ArrayList<IndividualQuantity>>() {}.getType();
        individualQuantityList= g.fromJson(payment.getProductQuantities(), listQuantity);
        for (ProductDTO productDTO :productDTOStore) {
            for (IndividualQuantity individualQuantity:individualQuantityList) {
                if (productDTO.getId()==Long.parseLong(individualQuantity.getProductId())){

                    double quantity=productDTO.getQuantity()-Double.parseDouble(individualQuantity.getQuantity());
                    productDTO.setQuantity(quantity);
                    productService.save(productDTO);
                }

            }

        }

        payment = paymentRepository.save(payment);
        return paymentMapper.toDto(payment);
    }

    @Override
    public PaymentDTO cleanSave(PaymentDTO paymentDTO) {
        log.debug("Request to save Payment : {}", paymentDTO);
        Payment payment = paymentMapper.toEntity(paymentDTO);
        payment = paymentRepository.save(payment);
        return paymentMapper.toDto(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Payments");
        return paymentRepository.findAll(pageable)
            .map(paymentMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentDTO> findOne(Long id) {
        log.debug("Request to get Payment : {}", id);
        return paymentRepository.findById(id)
            .map(paymentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Payment : {}", id);
        paymentRepository.deleteById(id);
    }
}
