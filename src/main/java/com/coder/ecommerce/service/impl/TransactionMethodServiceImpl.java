package com.coder.ecommerce.service.impl;

import com.coder.ecommerce.service.TransactionMethodService;
import com.coder.ecommerce.domain.TransactionMethod;
import com.coder.ecommerce.repository.TransactionMethodRepository;
import com.coder.ecommerce.service.dto.TransactionMethodDTO;
import com.coder.ecommerce.service.mapper.TransactionMethodMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TransactionMethod}.
 */
@Service
@Transactional
public class TransactionMethodServiceImpl implements TransactionMethodService {

    private final Logger log = LoggerFactory.getLogger(TransactionMethodServiceImpl.class);

    private final TransactionMethodRepository transactionMethodRepository;

    private final TransactionMethodMapper transactionMethodMapper;

    public TransactionMethodServiceImpl(TransactionMethodRepository transactionMethodRepository, TransactionMethodMapper transactionMethodMapper) {
        this.transactionMethodRepository = transactionMethodRepository;
        this.transactionMethodMapper = transactionMethodMapper;
    }

    @Override
    public TransactionMethodDTO save(TransactionMethodDTO transactionMethodDTO) {
        log.debug("Request to save TransactionMethod : {}", transactionMethodDTO);
        TransactionMethod transactionMethod = transactionMethodMapper.toEntity(transactionMethodDTO);
        transactionMethod = transactionMethodRepository.save(transactionMethod);
        return transactionMethodMapper.toDto(transactionMethod);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionMethodDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TransactionMethods");
        return transactionMethodRepository.findAll(pageable)
            .map(transactionMethodMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TransactionMethodDTO> findOne(Long id) {
        log.debug("Request to get TransactionMethod : {}", id);
        return transactionMethodRepository.findById(id)
            .map(transactionMethodMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TransactionMethod : {}", id);
        transactionMethodRepository.deleteById(id);
    }
}
