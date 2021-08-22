package com.coder.ecommerce.scheduler;

import com.coder.ecommerce.domain.Payment;
import com.coder.ecommerce.domain.Product;
import com.coder.ecommerce.repository.PaymentRepository;
import com.coder.ecommerce.repository.ProductRepository;
import com.coder.ecommerce.service.PaymentService;
import com.coder.ecommerce.service.ProductService;
import com.coder.ecommerce.service.dto.PaymentDTO;
import com.coder.ecommerce.service.dto.ProductDTO;
import com.coder.ecommerce.service.helper.IndividualQuantity;
import com.coder.ecommerce.service.mapper.PaymentMapper;
import com.coder.ecommerce.service.mapper.ProductMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduledTasks {

    private final PaymentRepository paymentRepository;
    private final PaymentService paymentService;
    private final PaymentMapper paymentMapper;
    private final ProductMapper productMapper;
    private final ProductService productService;
    private final ProductRepository productRepository;

    public ScheduledTasks(PaymentRepository paymentRepository, PaymentService paymentService, PaymentMapper paymentMapper, ProductMapper productMapper, ProductService productService, ProductRepository productRepository) {
        this.paymentRepository = paymentRepository;
        this.paymentService = paymentService;
        this.paymentMapper = paymentMapper;
        this.productMapper = productMapper;
        this.productService = productService;
        this.productRepository = productRepository;
    }


    @Scheduled(initialDelay = 7200000, fixedRate = 7200000)
    public void performTaskForPaymentStatus() {

        List<Payment> payments = paymentRepository.findAll();
        String timeStamp = Instant.now().toString();


        for (Payment payment : payments) {
            System.out.println(">>>>>>>" + payment.getCreatedDate().toString());
            System.out.println("<<<<<<<" + timeStamp);
            String output = HelperUtil.findDifference(payment.getCreatedDate().toString(), timeStamp);
            String[] array = output.split(",");
            System.out.println("------>" + array[0]);
            System.out.println("------>" + array[1]);
            System.out.println("------>" + array[2]);
            if (Integer.parseInt(array[0]) > 1) {
                if (payment.isActive()) {
                    updatePayment(payment);
                }
            } else if (Integer.parseInt(array[1]) > 1) {
                if (payment.isActive()) {
                    updatePayment(payment);
                }
            } else if (Integer.parseInt(array[2]) > 4) {
                if (payment.isActive()) {
                    updatePayment(payment);
                }
            }
        }

    }

    private void updatePayment(Payment payment) {
        PaymentDTO paymentDTO = paymentMapper.toDto(payment);
        paymentDTO.setActive(false);
        paymentService.cleanSave(paymentDTO);
        System.out.println("Operation product update done");
        for (Product product : payment.getProducts()) {
            updateProduct(product, payment);
        }
    }

    private void updateProduct(Product product, Payment payment) {
        Gson g = new Gson();
        List<IndividualQuantity> individualQuantityList = new ArrayList<>();
        Type listQuantity = new TypeToken<ArrayList<IndividualQuantity>>() {
        }.getType();
        individualQuantityList = g.fromJson(payment.getProductQuantities(), listQuantity);
        ProductDTO productDTO = productMapper.toDto(product);
        for (IndividualQuantity individualQuantity : individualQuantityList) {
            if (productDTO.getId() == Long.parseLong(individualQuantity.getProductId())) {
                double quantity = productDTO.getQuantity() + Double.parseDouble(individualQuantity.getQuantity());
                productDTO.setQuantity(quantity);
                productService.save(productDTO);
            }

        }


    }
}
