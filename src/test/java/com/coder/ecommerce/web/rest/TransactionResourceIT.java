package com.coder.ecommerce.web.rest;

import com.coder.ecommerce.EcommerceApp;
import com.coder.ecommerce.domain.Transaction;
import com.coder.ecommerce.domain.Payment;
import com.coder.ecommerce.repository.TransactionRepository;
import com.coder.ecommerce.service.TransactionService;
import com.coder.ecommerce.service.dto.TransactionDTO;
import com.coder.ecommerce.service.mapper.TransactionMapper;
import com.coder.ecommerce.service.dto.TransactionCriteria;
import com.coder.ecommerce.service.TransactionQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TransactionResource} REST controller.
 */
@SpringBootTest(classes = EcommerceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TransactionResourceIT {

    private static final String DEFAULT_TRANSACTIONID = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTIONID = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSACTION_METHOD = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_METHOD = "BBBBBBBBBB";

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionQueryService transactionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransactionMockMvc;

    private Transaction transaction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transaction createEntity(EntityManager em) {
        Transaction transaction = new Transaction()
            .transactionid(DEFAULT_TRANSACTIONID);// Add required entity
        Payment payment;
        if (TestUtil.findAll(em, Payment.class).isEmpty()) {
            payment = PaymentResourceIT.createEntity(em);
            em.persist(payment);
            em.flush();
        } else {
            payment = TestUtil.findAll(em, Payment.class).get(0);
        }
        transaction.setPayment(payment);
        return transaction;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transaction createUpdatedEntity(EntityManager em) {
        Transaction transaction = new Transaction()
            .transactionid(UPDATED_TRANSACTIONID);
        // Add required entity
        Payment payment;
        if (TestUtil.findAll(em, Payment.class).isEmpty()) {
            payment = PaymentResourceIT.createUpdatedEntity(em);
            em.persist(payment);
            em.flush();
        } else {
            payment = TestUtil.findAll(em, Payment.class).get(0);
        }
        transaction.setPayment(payment);
        return transaction;
    }

    @BeforeEach
    public void initTest() {
        transaction = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransaction() throws Exception {
        int databaseSizeBeforeCreate = transactionRepository.findAll().size();
        // Create the Transaction
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);
        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDTO)))
            .andExpect(status().isCreated());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeCreate + 1);
        Transaction testTransaction = transactionList.get(transactionList.size() - 1);
        assertThat(testTransaction.getTransactionid()).isEqualTo(DEFAULT_TRANSACTIONID);
    }

    @Test
    @Transactional
    public void createTransactionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionRepository.findAll().size();

        // Create the Transaction with an existing ID
        transaction.setId(1L);
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTransactionidIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionRepository.findAll().size();
        // set the field null
        transaction.setTransactionid(null);

        // Create the Transaction, which fails.
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);


        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDTO)))
            .andExpect(status().isBadRequest());

        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransaction_methodIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionRepository.findAll().size();
        // set the field null

        // Create the Transaction, which fails.
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);


        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDTO)))
            .andExpect(status().isBadRequest());

        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransactions() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList
        restTransactionMockMvc.perform(get("/api/transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionid").value(hasItem(DEFAULT_TRANSACTIONID)))
            .andExpect(jsonPath("$.[*].transaction_method").value(hasItem(DEFAULT_TRANSACTION_METHOD)));
    }

    @Test
    @Transactional
    public void getTransaction() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get the transaction
        restTransactionMockMvc.perform(get("/api/transactions/{id}", transaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transaction.getId().intValue()))
            .andExpect(jsonPath("$.transactionid").value(DEFAULT_TRANSACTIONID))
            .andExpect(jsonPath("$.transaction_method").value(DEFAULT_TRANSACTION_METHOD));
    }


    @Test
    @Transactional
    public void getTransactionsByIdFiltering() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        Long id = transaction.getId();

        defaultTransactionShouldBeFound("id.equals=" + id);
        defaultTransactionShouldNotBeFound("id.notEquals=" + id);

        defaultTransactionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTransactionShouldNotBeFound("id.greaterThan=" + id);

        defaultTransactionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTransactionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTransactionsByTransactionidIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where transactionid equals to DEFAULT_TRANSACTIONID
        defaultTransactionShouldBeFound("transactionid.equals=" + DEFAULT_TRANSACTIONID);

        // Get all the transactionList where transactionid equals to UPDATED_TRANSACTIONID
        defaultTransactionShouldNotBeFound("transactionid.equals=" + UPDATED_TRANSACTIONID);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTransactionidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where transactionid not equals to DEFAULT_TRANSACTIONID
        defaultTransactionShouldNotBeFound("transactionid.notEquals=" + DEFAULT_TRANSACTIONID);

        // Get all the transactionList where transactionid not equals to UPDATED_TRANSACTIONID
        defaultTransactionShouldBeFound("transactionid.notEquals=" + UPDATED_TRANSACTIONID);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTransactionidIsInShouldWork() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where transactionid in DEFAULT_TRANSACTIONID or UPDATED_TRANSACTIONID
        defaultTransactionShouldBeFound("transactionid.in=" + DEFAULT_TRANSACTIONID + "," + UPDATED_TRANSACTIONID);

        // Get all the transactionList where transactionid equals to UPDATED_TRANSACTIONID
        defaultTransactionShouldNotBeFound("transactionid.in=" + UPDATED_TRANSACTIONID);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTransactionidIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where transactionid is not null
        defaultTransactionShouldBeFound("transactionid.specified=true");

        // Get all the transactionList where transactionid is null
        defaultTransactionShouldNotBeFound("transactionid.specified=false");
    }
                @Test
    @Transactional
    public void getAllTransactionsByTransactionidContainsSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where transactionid contains DEFAULT_TRANSACTIONID
        defaultTransactionShouldBeFound("transactionid.contains=" + DEFAULT_TRANSACTIONID);

        // Get all the transactionList where transactionid contains UPDATED_TRANSACTIONID
        defaultTransactionShouldNotBeFound("transactionid.contains=" + UPDATED_TRANSACTIONID);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTransactionidNotContainsSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where transactionid does not contain DEFAULT_TRANSACTIONID
        defaultTransactionShouldNotBeFound("transactionid.doesNotContain=" + DEFAULT_TRANSACTIONID);

        // Get all the transactionList where transactionid does not contain UPDATED_TRANSACTIONID
        defaultTransactionShouldBeFound("transactionid.doesNotContain=" + UPDATED_TRANSACTIONID);
    }


    @Test
    @Transactional
    public void getAllTransactionsByTransaction_methodIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where transaction_method equals to DEFAULT_TRANSACTION_METHOD
        defaultTransactionShouldBeFound("transaction_method.equals=" + DEFAULT_TRANSACTION_METHOD);

        // Get all the transactionList where transaction_method equals to UPDATED_TRANSACTION_METHOD
        defaultTransactionShouldNotBeFound("transaction_method.equals=" + UPDATED_TRANSACTION_METHOD);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTransaction_methodIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where transaction_method not equals to DEFAULT_TRANSACTION_METHOD
        defaultTransactionShouldNotBeFound("transaction_method.notEquals=" + DEFAULT_TRANSACTION_METHOD);

        // Get all the transactionList where transaction_method not equals to UPDATED_TRANSACTION_METHOD
        defaultTransactionShouldBeFound("transaction_method.notEquals=" + UPDATED_TRANSACTION_METHOD);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTransaction_methodIsInShouldWork() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where transaction_method in DEFAULT_TRANSACTION_METHOD or UPDATED_TRANSACTION_METHOD
        defaultTransactionShouldBeFound("transaction_method.in=" + DEFAULT_TRANSACTION_METHOD + "," + UPDATED_TRANSACTION_METHOD);

        // Get all the transactionList where transaction_method equals to UPDATED_TRANSACTION_METHOD
        defaultTransactionShouldNotBeFound("transaction_method.in=" + UPDATED_TRANSACTION_METHOD);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTransaction_methodIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where transaction_method is not null
        defaultTransactionShouldBeFound("transaction_method.specified=true");

        // Get all the transactionList where transaction_method is null
        defaultTransactionShouldNotBeFound("transaction_method.specified=false");
    }
                @Test
    @Transactional
    public void getAllTransactionsByTransaction_methodContainsSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where transaction_method contains DEFAULT_TRANSACTION_METHOD
        defaultTransactionShouldBeFound("transaction_method.contains=" + DEFAULT_TRANSACTION_METHOD);

        // Get all the transactionList where transaction_method contains UPDATED_TRANSACTION_METHOD
        defaultTransactionShouldNotBeFound("transaction_method.contains=" + UPDATED_TRANSACTION_METHOD);
    }

    @Test
    @Transactional
    public void getAllTransactionsByTransaction_methodNotContainsSomething() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList where transaction_method does not contain DEFAULT_TRANSACTION_METHOD
        defaultTransactionShouldNotBeFound("transaction_method.doesNotContain=" + DEFAULT_TRANSACTION_METHOD);

        // Get all the transactionList where transaction_method does not contain UPDATED_TRANSACTION_METHOD
        defaultTransactionShouldBeFound("transaction_method.doesNotContain=" + UPDATED_TRANSACTION_METHOD);
    }


    @Test
    @Transactional
    public void getAllTransactionsByPaymentIsEqualToSomething() throws Exception {
        // Get already existing entity
        Payment payment = transaction.getPayment();
        transactionRepository.saveAndFlush(transaction);
        Long paymentId = payment.getId();

        // Get all the transactionList where payment equals to paymentId
        defaultTransactionShouldBeFound("paymentId.equals=" + paymentId);

        // Get all the transactionList where payment equals to paymentId + 1
        defaultTransactionShouldNotBeFound("paymentId.equals=" + (paymentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTransactionShouldBeFound(String filter) throws Exception {
        restTransactionMockMvc.perform(get("/api/transactions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionid").value(hasItem(DEFAULT_TRANSACTIONID)))
            .andExpect(jsonPath("$.[*].transaction_method").value(hasItem(DEFAULT_TRANSACTION_METHOD)));

        // Check, that the count call also returns 1
        restTransactionMockMvc.perform(get("/api/transactions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTransactionShouldNotBeFound(String filter) throws Exception {
        restTransactionMockMvc.perform(get("/api/transactions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTransactionMockMvc.perform(get("/api/transactions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTransaction() throws Exception {
        // Get the transaction
        restTransactionMockMvc.perform(get("/api/transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransaction() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();

        // Update the transaction
        Transaction updatedTransaction = transactionRepository.findById(transaction.getId()).get();
        // Disconnect from session so that the updates on updatedTransaction are not directly saved in db
        em.detach(updatedTransaction);
        updatedTransaction
            .transactionid(UPDATED_TRANSACTIONID);
        TransactionDTO transactionDTO = transactionMapper.toDto(updatedTransaction);

        restTransactionMockMvc.perform(put("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDTO)))
            .andExpect(status().isOk());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate);
        Transaction testTransaction = transactionList.get(transactionList.size() - 1);
        assertThat(testTransaction.getTransactionid()).isEqualTo(UPDATED_TRANSACTIONID);
    }

    @Test
    @Transactional
    public void updateNonExistingTransaction() throws Exception {
        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();

        // Create the Transaction
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionMockMvc.perform(put("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransaction() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        int databaseSizeBeforeDelete = transactionRepository.findAll().size();

        // Delete the transaction
        restTransactionMockMvc.perform(delete("/api/transactions/{id}", transaction.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
