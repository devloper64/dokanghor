package com.coder.ecommerce.web.rest;

import com.coder.ecommerce.EcommerceApp;
import com.coder.ecommerce.domain.TransactionMethod;
import com.coder.ecommerce.repository.TransactionMethodRepository;
import com.coder.ecommerce.service.TransactionMethodService;
import com.coder.ecommerce.service.dto.TransactionMethodDTO;
import com.coder.ecommerce.service.mapper.TransactionMethodMapper;
import com.coder.ecommerce.service.dto.TransactionMethodCriteria;
import com.coder.ecommerce.service.TransactionMethodQueryService;

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
 * Integration tests for the {@link TransactionMethodResource} REST controller.
 */
@SpringBootTest(classes = EcommerceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TransactionMethodResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private TransactionMethodRepository transactionMethodRepository;

    @Autowired
    private TransactionMethodMapper transactionMethodMapper;

    @Autowired
    private TransactionMethodService transactionMethodService;

    @Autowired
    private TransactionMethodQueryService transactionMethodQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransactionMethodMockMvc;

    private TransactionMethod transactionMethod;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionMethod createEntity(EntityManager em) {
        TransactionMethod transactionMethod = new TransactionMethod()
            .name(DEFAULT_NAME);
        return transactionMethod;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionMethod createUpdatedEntity(EntityManager em) {
        TransactionMethod transactionMethod = new TransactionMethod()
            .name(UPDATED_NAME);
        return transactionMethod;
    }

    @BeforeEach
    public void initTest() {
        transactionMethod = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransactionMethod() throws Exception {
        int databaseSizeBeforeCreate = transactionMethodRepository.findAll().size();
        // Create the TransactionMethod
        TransactionMethodDTO transactionMethodDTO = transactionMethodMapper.toDto(transactionMethod);
        restTransactionMethodMockMvc.perform(post("/api/transaction-methods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionMethodDTO)))
            .andExpect(status().isCreated());

        // Validate the TransactionMethod in the database
        List<TransactionMethod> transactionMethodList = transactionMethodRepository.findAll();
        assertThat(transactionMethodList).hasSize(databaseSizeBeforeCreate + 1);
        TransactionMethod testTransactionMethod = transactionMethodList.get(transactionMethodList.size() - 1);
        assertThat(testTransactionMethod.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createTransactionMethodWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionMethodRepository.findAll().size();

        // Create the TransactionMethod with an existing ID
        transactionMethod.setId(1L);
        TransactionMethodDTO transactionMethodDTO = transactionMethodMapper.toDto(transactionMethod);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionMethodMockMvc.perform(post("/api/transaction-methods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionMethodDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionMethod in the database
        List<TransactionMethod> transactionMethodList = transactionMethodRepository.findAll();
        assertThat(transactionMethodList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionMethodRepository.findAll().size();
        // set the field null
        transactionMethod.setName(null);

        // Create the TransactionMethod, which fails.
        TransactionMethodDTO transactionMethodDTO = transactionMethodMapper.toDto(transactionMethod);


        restTransactionMethodMockMvc.perform(post("/api/transaction-methods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionMethodDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionMethod> transactionMethodList = transactionMethodRepository.findAll();
        assertThat(transactionMethodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransactionMethods() throws Exception {
        // Initialize the database
        transactionMethodRepository.saveAndFlush(transactionMethod);

        // Get all the transactionMethodList
        restTransactionMethodMockMvc.perform(get("/api/transaction-methods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionMethod.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getTransactionMethod() throws Exception {
        // Initialize the database
        transactionMethodRepository.saveAndFlush(transactionMethod);

        // Get the transactionMethod
        restTransactionMethodMockMvc.perform(get("/api/transaction-methods/{id}", transactionMethod.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transactionMethod.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getTransactionMethodsByIdFiltering() throws Exception {
        // Initialize the database
        transactionMethodRepository.saveAndFlush(transactionMethod);

        Long id = transactionMethod.getId();

        defaultTransactionMethodShouldBeFound("id.equals=" + id);
        defaultTransactionMethodShouldNotBeFound("id.notEquals=" + id);

        defaultTransactionMethodShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTransactionMethodShouldNotBeFound("id.greaterThan=" + id);

        defaultTransactionMethodShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTransactionMethodShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTransactionMethodsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionMethodRepository.saveAndFlush(transactionMethod);

        // Get all the transactionMethodList where name equals to DEFAULT_NAME
        defaultTransactionMethodShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the transactionMethodList where name equals to UPDATED_NAME
        defaultTransactionMethodShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTransactionMethodsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionMethodRepository.saveAndFlush(transactionMethod);

        // Get all the transactionMethodList where name not equals to DEFAULT_NAME
        defaultTransactionMethodShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the transactionMethodList where name not equals to UPDATED_NAME
        defaultTransactionMethodShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTransactionMethodsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        transactionMethodRepository.saveAndFlush(transactionMethod);

        // Get all the transactionMethodList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTransactionMethodShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the transactionMethodList where name equals to UPDATED_NAME
        defaultTransactionMethodShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTransactionMethodsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionMethodRepository.saveAndFlush(transactionMethod);

        // Get all the transactionMethodList where name is not null
        defaultTransactionMethodShouldBeFound("name.specified=true");

        // Get all the transactionMethodList where name is null
        defaultTransactionMethodShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllTransactionMethodsByNameContainsSomething() throws Exception {
        // Initialize the database
        transactionMethodRepository.saveAndFlush(transactionMethod);

        // Get all the transactionMethodList where name contains DEFAULT_NAME
        defaultTransactionMethodShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the transactionMethodList where name contains UPDATED_NAME
        defaultTransactionMethodShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTransactionMethodsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        transactionMethodRepository.saveAndFlush(transactionMethod);

        // Get all the transactionMethodList where name does not contain DEFAULT_NAME
        defaultTransactionMethodShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the transactionMethodList where name does not contain UPDATED_NAME
        defaultTransactionMethodShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTransactionMethodShouldBeFound(String filter) throws Exception {
        restTransactionMethodMockMvc.perform(get("/api/transaction-methods?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionMethod.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restTransactionMethodMockMvc.perform(get("/api/transaction-methods/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTransactionMethodShouldNotBeFound(String filter) throws Exception {
        restTransactionMethodMockMvc.perform(get("/api/transaction-methods?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTransactionMethodMockMvc.perform(get("/api/transaction-methods/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTransactionMethod() throws Exception {
        // Get the transactionMethod
        restTransactionMethodMockMvc.perform(get("/api/transaction-methods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransactionMethod() throws Exception {
        // Initialize the database
        transactionMethodRepository.saveAndFlush(transactionMethod);

        int databaseSizeBeforeUpdate = transactionMethodRepository.findAll().size();

        // Update the transactionMethod
        TransactionMethod updatedTransactionMethod = transactionMethodRepository.findById(transactionMethod.getId()).get();
        // Disconnect from session so that the updates on updatedTransactionMethod are not directly saved in db
        em.detach(updatedTransactionMethod);
        updatedTransactionMethod
            .name(UPDATED_NAME);
        TransactionMethodDTO transactionMethodDTO = transactionMethodMapper.toDto(updatedTransactionMethod);

        restTransactionMethodMockMvc.perform(put("/api/transaction-methods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionMethodDTO)))
            .andExpect(status().isOk());

        // Validate the TransactionMethod in the database
        List<TransactionMethod> transactionMethodList = transactionMethodRepository.findAll();
        assertThat(transactionMethodList).hasSize(databaseSizeBeforeUpdate);
        TransactionMethod testTransactionMethod = transactionMethodList.get(transactionMethodList.size() - 1);
        assertThat(testTransactionMethod.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTransactionMethod() throws Exception {
        int databaseSizeBeforeUpdate = transactionMethodRepository.findAll().size();

        // Create the TransactionMethod
        TransactionMethodDTO transactionMethodDTO = transactionMethodMapper.toDto(transactionMethod);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionMethodMockMvc.perform(put("/api/transaction-methods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionMethodDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionMethod in the database
        List<TransactionMethod> transactionMethodList = transactionMethodRepository.findAll();
        assertThat(transactionMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransactionMethod() throws Exception {
        // Initialize the database
        transactionMethodRepository.saveAndFlush(transactionMethod);

        int databaseSizeBeforeDelete = transactionMethodRepository.findAll().size();

        // Delete the transactionMethod
        restTransactionMethodMockMvc.perform(delete("/api/transaction-methods/{id}", transactionMethod.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TransactionMethod> transactionMethodList = transactionMethodRepository.findAll();
        assertThat(transactionMethodList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
