package com.coder.ecommerce.service.impl;

import com.coder.ecommerce.domain.*;
import com.coder.ecommerce.repository.*;
import com.coder.ecommerce.service.InvoiceService;
import com.coder.ecommerce.service.PaymentService;
import com.coder.ecommerce.service.TransactionService;
import com.coder.ecommerce.service.dto.InvoiceDTO;
import com.coder.ecommerce.service.dto.PaymentDTO;
import com.coder.ecommerce.service.dto.TransactionDTO;
import com.coder.ecommerce.service.helper.IndividualAmount;
import com.coder.ecommerce.service.helper.IndividualQuantity;
import com.coder.ecommerce.service.helper.InvoiceItem;
import com.coder.ecommerce.service.helper.InvoiceTo;
import com.coder.ecommerce.service.mapper.InvoiceMapper;
import com.coder.ecommerce.service.mapper.PaymentMapper;
import com.coder.ecommerce.service.mapper.TransactionMapper;
import com.coder.ecommerce.web.rest.errors.BadRequestAlertException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
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

    private final InvoiceService invoiceService;
    private final InvoiceMapper invoiceMapper;
    private final ShippingAddressRepository shippingAddressRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionMapper transactionMapper, PaymentRepository paymentRepository, PaymentService paymentService, PaymentMapper paymentMapper, InvoiceService invoiceService, InvoiceMapper invoiceMapper, ShippingAddressRepository shippingAddressRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.paymentRepository = paymentRepository;
        this.paymentService = paymentService;
        this.paymentMapper = paymentMapper;
        this.invoiceService = invoiceService;
        this.invoiceMapper = invoiceMapper;
        this.shippingAddressRepository = shippingAddressRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
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
                throw new BadRequestAlertException("Payment time expired Or Already payment", "transaction", "payment expired");
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
    public InvoiceDTO transactionCompleted(Long id) {
        Optional<TransactionDTO>transactionDTOOptional=transactionRepository.findById(id).map(transactionMapper::toDto);
        if (transactionDTOOptional.isPresent()){
            TransactionDTO transactionDTO=transactionDTOOptional.get();
            transactionDTO.setIs_transaction_completed(true);
            Transaction transaction=transactionMapper.toEntity(transactionDTO);
            Transaction transactionData=transactionRepository.save(transaction);

            Optional<Payment> paymentOptional=paymentRepository.findById(transactionData.getPayment().getId());
            Payment payment=new Payment();
            if (paymentOptional.isPresent()){
                payment=paymentOptional.get();
            }
            ShippingAddress shippingAddress=shippingAddressRepository.getOne(payment.getShippingAddress().getId());

            User user=userRepository.getOne(payment.getUser().getId());


            InvoiceTo invoiceTo=new InvoiceTo(
                user.getFirstName()+" "+user.getLastName(),
                shippingAddress.getDistrict()+","+shippingAddress.getUpazila()+","+shippingAddress.getPostalcode(),
                        user.getEmail(),
                shippingAddress.getPhoneNumber()
            );


            Double discount = 0.0;
            Double total;

            String to = new Gson().toJson(invoiceTo);

            List<InvoiceItem> invoiceItems=new ArrayList<>();

            Gson g = new Gson();
            List<IndividualQuantity> individualQuantityList=new ArrayList<>();
            Type listQuantity = new TypeToken<ArrayList<IndividualQuantity>>() {}.getType();
            individualQuantityList= g.fromJson(payment.getProductQuantities(), listQuantity);

            List<IndividualAmount> individualAmountList=new ArrayList<>();
            Type listIndividualAmount = new TypeToken<ArrayList<IndividualAmount>>() {}.getType();
            individualAmountList= g.fromJson(payment.getIndividualAmount(), listIndividualAmount);

            for (Product product:payment.getProducts()) {
                for (IndividualQuantity individualQuantity:individualQuantityList) {
                    for (IndividualAmount individualAmount:individualAmountList) {
                        if (product.getId()==Long.parseLong(individualQuantity.getProductId())){
                            if (product.getId()==Long.parseLong(individualAmount.getProductId())){
                                discount+=product.getDiscount_amount();
                                invoiceItems.add(new InvoiceItem(
                                    product.getName(),
                                    "No",
                                    Integer.parseInt(individualQuantity.getQuantity()),
                                    Double.parseDouble(individualAmount.getAmount()),
                                    Integer.parseInt(individualQuantity.getQuantity())*Double.parseDouble(individualAmount.getAmount())

                                ));
                            }
                        }
                    }

                }

            }
            String invoiceItemsString = new Gson().toJson(invoiceItems);
            ZonedDateTime zone= ZonedDateTime.now();

            InvoiceDTO invoiceDTO=new InvoiceDTO();
            invoiceDTO.setInvoice_number(transactionData.getTransactionid());
            invoiceDTO.setTo(to);
            invoiceDTO.setItem_list(invoiceItemsString);
            invoiceDTO.setSubtotal(payment.getTotalAmount()+discount);
            invoiceDTO.setDiscount(discount);
            invoiceDTO.setVat(0.0);
            invoiceDTO.setInvoice_date(zone);
            invoiceDTO.setTotal(payment.getTotalAmount());
            invoiceDTO.setTransactionId(transactionData.getId());
            InvoiceDTO invoiceDTOFinal=invoiceService.save(invoiceDTO);

            return  invoiceDTOFinal;
        }else {
            throw new BadRequestAlertException("Not Found", "transaction", "payment expired");
        }
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Transaction : {}", id);
        transactionRepository.deleteById(id);
    }
}
