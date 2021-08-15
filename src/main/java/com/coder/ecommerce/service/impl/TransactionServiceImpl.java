package com.coder.ecommerce.service.impl;

import com.coder.ecommerce.domain.Payment;
import com.coder.ecommerce.repository.PaymentRepository;
import com.coder.ecommerce.service.PaymentService;
import com.coder.ecommerce.service.TransactionService;
import com.coder.ecommerce.domain.Transaction;
import com.coder.ecommerce.repository.TransactionRepository;
import com.coder.ecommerce.service.dto.PaymentDTO;
import com.coder.ecommerce.service.dto.TransactionDTO;
import com.coder.ecommerce.service.mapper.PaymentMapper;
import com.coder.ecommerce.service.mapper.TransactionMapper;
import com.coder.ecommerce.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Transaction}.
 */
@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private final TransactionRepository transactionRepository;

    private final TransactionMapper transactionMapper;

    private final PaymentRepository paymentRepository;

    private final PaymentService paymentService;
    private final PaymentMapper paymentMapper;


    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionMapper transactionMapper, PaymentRepository paymentRepository, PaymentService paymentService, PaymentMapper paymentMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.paymentRepository = paymentRepository;
        this.paymentService = paymentService;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public TransactionDTO save(TransactionDTO transactionDTO) {
        log.debug("Request to save Transaction : {}", transactionDTO);
        Transaction transaction = transactionMapper.toEntity(transactionDTO);
        Payment paymentForId=transaction.getPayment();
        Optional<Payment> paymentOptional=paymentRepository.findById(paymentForId.getId());
        if (paymentOptional.isPresent()){
            Payment payment=paymentOptional.get();
            if (payment.isActive()){
                PaymentDTO paymentDTO = paymentMapper.toDto(payment);
                paymentDTO.setActive(false);
                paymentService.cleanSave(paymentDTO);
                transaction = transactionRepository.save(transaction);
            }else {
                throw new BadRequestAlertException("Payment time expired", "transaction", "payment expired");
            }
        }

        return transactionMapper.toDto(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Transactions");
        return transactionRepository.findAll(pageable)
            .map(transactionMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TransactionDTO> findOne(Long id) {
        log.debug("Request to get Transaction : {}", id);
        return transactionRepository.findById(id)
            .map(transactionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Transaction : {}", id);
        transactionRepository.deleteById(id);
    }
}
