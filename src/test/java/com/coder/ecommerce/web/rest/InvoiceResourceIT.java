package com.coder.ecommerce.web.rest;

import com.coder.ecommerce.EcommerceApp;
import com.coder.ecommerce.domain.Invoice;
import com.coder.ecommerce.domain.Transaction;
import com.coder.ecommerce.repository.InvoiceRepository;
import com.coder.ecommerce.service.InvoiceService;
import com.coder.ecommerce.service.dto.InvoiceDTO;
import com.coder.ecommerce.service.mapper.InvoiceMapper;
import com.coder.ecommerce.service.dto.InvoiceCriteria;
import com.coder.ecommerce.service.InvoiceQueryService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.coder.ecommerce.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link InvoiceResource} REST controller.
 */
@SpringBootTest(classes = EcommerceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InvoiceResourceIT {

    private static final String DEFAULT_INVOICE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_TO = "AAAAAAAAAA";
    private static final String UPDATED_TO = "BBBBBBBBBB";

    private static final String DEFAULT_ITEM_LIST = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_LIST = "BBBBBBBBBB";

    private static final Double DEFAULT_SUBTOTAL = 1D;
    private static final Double UPDATED_SUBTOTAL = 2D;
    private static final Double SMALLER_SUBTOTAL = 1D - 1D;

    private static final Double DEFAULT_DISCOUNT = 1D;
    private static final Double UPDATED_DISCOUNT = 2D;
    private static final Double SMALLER_DISCOUNT = 1D - 1D;

    private static final Double DEFAULT_VAT = 1D;
    private static final Double UPDATED_VAT = 2D;
    private static final Double SMALLER_VAT = 1D - 1D;

    private static final Double DEFAULT_TOTAL = 1D;
    private static final Double UPDATED_TOTAL = 2D;
    private static final Double SMALLER_TOTAL = 1D - 1D;

    private static final ZonedDateTime DEFAULT_INVOICE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_INVOICE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_INVOICE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private InvoiceQueryService invoiceQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInvoiceMockMvc;

    private Invoice invoice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Invoice createEntity(EntityManager em) {
        Invoice invoice = new Invoice()
            .invoice_number(DEFAULT_INVOICE_NUMBER)
            .to(DEFAULT_TO)
            .item_list(DEFAULT_ITEM_LIST)
            .subtotal(DEFAULT_SUBTOTAL)
            .discount(DEFAULT_DISCOUNT)
            .vat(DEFAULT_VAT)
            .total(DEFAULT_TOTAL)
            .invoice_date(DEFAULT_INVOICE_DATE);
        // Add required entity
        Transaction transaction;
        if (TestUtil.findAll(em, Transaction.class).isEmpty()) {
            transaction = TransactionResourceIT.createEntity(em);
            em.persist(transaction);
            em.flush();
        } else {
            transaction = TestUtil.findAll(em, Transaction.class).get(0);
        }
        invoice.setTransaction(transaction);
        return invoice;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Invoice createUpdatedEntity(EntityManager em) {
        Invoice invoice = new Invoice()
            .invoice_number(UPDATED_INVOICE_NUMBER)
            .to(UPDATED_TO)
            .item_list(UPDATED_ITEM_LIST)
            .subtotal(UPDATED_SUBTOTAL)
            .discount(UPDATED_DISCOUNT)
            .vat(UPDATED_VAT)
            .total(UPDATED_TOTAL)
            .invoice_date(UPDATED_INVOICE_DATE);
        // Add required entity
        Transaction transaction;
        if (TestUtil.findAll(em, Transaction.class).isEmpty()) {
            transaction = TransactionResourceIT.createUpdatedEntity(em);
            em.persist(transaction);
            em.flush();
        } else {
            transaction = TestUtil.findAll(em, Transaction.class).get(0);
        }
        invoice.setTransaction(transaction);
        return invoice;
    }

    @BeforeEach
    public void initTest() {
        invoice = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvoice() throws Exception {
        int databaseSizeBeforeCreate = invoiceRepository.findAll().size();
        // Create the Invoice
        InvoiceDTO invoiceDTO = invoiceMapper.toDto(invoice);
        restInvoiceMockMvc.perform(post("/api/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoiceDTO)))
            .andExpect(status().isCreated());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeCreate + 1);
        Invoice testInvoice = invoiceList.get(invoiceList.size() - 1);
        assertThat(testInvoice.getInvoice_number()).isEqualTo(DEFAULT_INVOICE_NUMBER);
        assertThat(testInvoice.getTo()).isEqualTo(DEFAULT_TO);
        assertThat(testInvoice.getItem_list()).isEqualTo(DEFAULT_ITEM_LIST);
        assertThat(testInvoice.getSubtotal()).isEqualTo(DEFAULT_SUBTOTAL);
        assertThat(testInvoice.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testInvoice.getVat()).isEqualTo(DEFAULT_VAT);
        assertThat(testInvoice.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testInvoice.getInvoice_date()).isEqualTo(DEFAULT_INVOICE_DATE);
    }

    @Test
    @Transactional
    public void createInvoiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invoiceRepository.findAll().size();

        // Create the Invoice with an existing ID
        invoice.setId(1L);
        InvoiceDTO invoiceDTO = invoiceMapper.toDto(invoice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvoiceMockMvc.perform(post("/api/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkInvoice_numberIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoiceRepository.findAll().size();
        // set the field null
        invoice.setInvoice_number(null);

        // Create the Invoice, which fails.
        InvoiceDTO invoiceDTO = invoiceMapper.toDto(invoice);


        restInvoiceMockMvc.perform(post("/api/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoiceDTO)))
            .andExpect(status().isBadRequest());

        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkToIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoiceRepository.findAll().size();
        // set the field null
        invoice.setTo(null);

        // Create the Invoice, which fails.
        InvoiceDTO invoiceDTO = invoiceMapper.toDto(invoice);


        restInvoiceMockMvc.perform(post("/api/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoiceDTO)))
            .andExpect(status().isBadRequest());

        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkItem_listIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoiceRepository.findAll().size();
        // set the field null
        invoice.setItem_list(null);

        // Create the Invoice, which fails.
        InvoiceDTO invoiceDTO = invoiceMapper.toDto(invoice);


        restInvoiceMockMvc.perform(post("/api/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoiceDTO)))
            .andExpect(status().isBadRequest());

        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSubtotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoiceRepository.findAll().size();
        // set the field null
        invoice.setSubtotal(null);

        // Create the Invoice, which fails.
        InvoiceDTO invoiceDTO = invoiceMapper.toDto(invoice);


        restInvoiceMockMvc.perform(post("/api/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoiceDTO)))
            .andExpect(status().isBadRequest());

        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDiscountIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoiceRepository.findAll().size();
        // set the field null
        invoice.setDiscount(null);

        // Create the Invoice, which fails.
        InvoiceDTO invoiceDTO = invoiceMapper.toDto(invoice);


        restInvoiceMockMvc.perform(post("/api/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoiceDTO)))
            .andExpect(status().isBadRequest());

        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoiceRepository.findAll().size();
        // set the field null
        invoice.setTotal(null);

        // Create the Invoice, which fails.
        InvoiceDTO invoiceDTO = invoiceMapper.toDto(invoice);


        restInvoiceMockMvc.perform(post("/api/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoiceDTO)))
            .andExpect(status().isBadRequest());

        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInvoice_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoiceRepository.findAll().size();
        // set the field null
        invoice.setInvoice_date(null);

        // Create the Invoice, which fails.
        InvoiceDTO invoiceDTO = invoiceMapper.toDto(invoice);


        restInvoiceMockMvc.perform(post("/api/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoiceDTO)))
            .andExpect(status().isBadRequest());

        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInvoices() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList
        restInvoiceMockMvc.perform(get("/api/invoices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoice_number").value(hasItem(DEFAULT_INVOICE_NUMBER)))
            .andExpect(jsonPath("$.[*].to").value(hasItem(DEFAULT_TO)))
            .andExpect(jsonPath("$.[*].item_list").value(hasItem(DEFAULT_ITEM_LIST)))
            .andExpect(jsonPath("$.[*].subtotal").value(hasItem(DEFAULT_SUBTOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].vat").value(hasItem(DEFAULT_VAT.doubleValue())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].invoice_date").value(hasItem(sameInstant(DEFAULT_INVOICE_DATE))));
    }
    
    @Test
    @Transactional
    public void getInvoice() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get the invoice
        restInvoiceMockMvc.perform(get("/api/invoices/{id}", invoice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(invoice.getId().intValue()))
            .andExpect(jsonPath("$.invoice_number").value(DEFAULT_INVOICE_NUMBER))
            .andExpect(jsonPath("$.to").value(DEFAULT_TO))
            .andExpect(jsonPath("$.item_list").value(DEFAULT_ITEM_LIST))
            .andExpect(jsonPath("$.subtotal").value(DEFAULT_SUBTOTAL.doubleValue()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.vat").value(DEFAULT_VAT.doubleValue()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.invoice_date").value(sameInstant(DEFAULT_INVOICE_DATE)));
    }


    @Test
    @Transactional
    public void getInvoicesByIdFiltering() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        Long id = invoice.getId();

        defaultInvoiceShouldBeFound("id.equals=" + id);
        defaultInvoiceShouldNotBeFound("id.notEquals=" + id);

        defaultInvoiceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInvoiceShouldNotBeFound("id.greaterThan=" + id);

        defaultInvoiceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInvoiceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllInvoicesByInvoice_numberIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoice_number equals to DEFAULT_INVOICE_NUMBER
        defaultInvoiceShouldBeFound("invoice_number.equals=" + DEFAULT_INVOICE_NUMBER);

        // Get all the invoiceList where invoice_number equals to UPDATED_INVOICE_NUMBER
        defaultInvoiceShouldNotBeFound("invoice_number.equals=" + UPDATED_INVOICE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoice_numberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoice_number not equals to DEFAULT_INVOICE_NUMBER
        defaultInvoiceShouldNotBeFound("invoice_number.notEquals=" + DEFAULT_INVOICE_NUMBER);

        // Get all the invoiceList where invoice_number not equals to UPDATED_INVOICE_NUMBER
        defaultInvoiceShouldBeFound("invoice_number.notEquals=" + UPDATED_INVOICE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoice_numberIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoice_number in DEFAULT_INVOICE_NUMBER or UPDATED_INVOICE_NUMBER
        defaultInvoiceShouldBeFound("invoice_number.in=" + DEFAULT_INVOICE_NUMBER + "," + UPDATED_INVOICE_NUMBER);

        // Get all the invoiceList where invoice_number equals to UPDATED_INVOICE_NUMBER
        defaultInvoiceShouldNotBeFound("invoice_number.in=" + UPDATED_INVOICE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoice_numberIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoice_number is not null
        defaultInvoiceShouldBeFound("invoice_number.specified=true");

        // Get all the invoiceList where invoice_number is null
        defaultInvoiceShouldNotBeFound("invoice_number.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoicesByInvoice_numberContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoice_number contains DEFAULT_INVOICE_NUMBER
        defaultInvoiceShouldBeFound("invoice_number.contains=" + DEFAULT_INVOICE_NUMBER);

        // Get all the invoiceList where invoice_number contains UPDATED_INVOICE_NUMBER
        defaultInvoiceShouldNotBeFound("invoice_number.contains=" + UPDATED_INVOICE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoice_numberNotContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoice_number does not contain DEFAULT_INVOICE_NUMBER
        defaultInvoiceShouldNotBeFound("invoice_number.doesNotContain=" + DEFAULT_INVOICE_NUMBER);

        // Get all the invoiceList where invoice_number does not contain UPDATED_INVOICE_NUMBER
        defaultInvoiceShouldBeFound("invoice_number.doesNotContain=" + UPDATED_INVOICE_NUMBER);
    }


    @Test
    @Transactional
    public void getAllInvoicesByToIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where to equals to DEFAULT_TO
        defaultInvoiceShouldBeFound("to.equals=" + DEFAULT_TO);

        // Get all the invoiceList where to equals to UPDATED_TO
        defaultInvoiceShouldNotBeFound("to.equals=" + UPDATED_TO);
    }

    @Test
    @Transactional
    public void getAllInvoicesByToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where to not equals to DEFAULT_TO
        defaultInvoiceShouldNotBeFound("to.notEquals=" + DEFAULT_TO);

        // Get all the invoiceList where to not equals to UPDATED_TO
        defaultInvoiceShouldBeFound("to.notEquals=" + UPDATED_TO);
    }

    @Test
    @Transactional
    public void getAllInvoicesByToIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where to in DEFAULT_TO or UPDATED_TO
        defaultInvoiceShouldBeFound("to.in=" + DEFAULT_TO + "," + UPDATED_TO);

        // Get all the invoiceList where to equals to UPDATED_TO
        defaultInvoiceShouldNotBeFound("to.in=" + UPDATED_TO);
    }

    @Test
    @Transactional
    public void getAllInvoicesByToIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where to is not null
        defaultInvoiceShouldBeFound("to.specified=true");

        // Get all the invoiceList where to is null
        defaultInvoiceShouldNotBeFound("to.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoicesByToContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where to contains DEFAULT_TO
        defaultInvoiceShouldBeFound("to.contains=" + DEFAULT_TO);

        // Get all the invoiceList where to contains UPDATED_TO
        defaultInvoiceShouldNotBeFound("to.contains=" + UPDATED_TO);
    }

    @Test
    @Transactional
    public void getAllInvoicesByToNotContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where to does not contain DEFAULT_TO
        defaultInvoiceShouldNotBeFound("to.doesNotContain=" + DEFAULT_TO);

        // Get all the invoiceList where to does not contain UPDATED_TO
        defaultInvoiceShouldBeFound("to.doesNotContain=" + UPDATED_TO);
    }


    @Test
    @Transactional
    public void getAllInvoicesByItem_listIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where item_list equals to DEFAULT_ITEM_LIST
        defaultInvoiceShouldBeFound("item_list.equals=" + DEFAULT_ITEM_LIST);

        // Get all the invoiceList where item_list equals to UPDATED_ITEM_LIST
        defaultInvoiceShouldNotBeFound("item_list.equals=" + UPDATED_ITEM_LIST);
    }

    @Test
    @Transactional
    public void getAllInvoicesByItem_listIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where item_list not equals to DEFAULT_ITEM_LIST
        defaultInvoiceShouldNotBeFound("item_list.notEquals=" + DEFAULT_ITEM_LIST);

        // Get all the invoiceList where item_list not equals to UPDATED_ITEM_LIST
        defaultInvoiceShouldBeFound("item_list.notEquals=" + UPDATED_ITEM_LIST);
    }

    @Test
    @Transactional
    public void getAllInvoicesByItem_listIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where item_list in DEFAULT_ITEM_LIST or UPDATED_ITEM_LIST
        defaultInvoiceShouldBeFound("item_list.in=" + DEFAULT_ITEM_LIST + "," + UPDATED_ITEM_LIST);

        // Get all the invoiceList where item_list equals to UPDATED_ITEM_LIST
        defaultInvoiceShouldNotBeFound("item_list.in=" + UPDATED_ITEM_LIST);
    }

    @Test
    @Transactional
    public void getAllInvoicesByItem_listIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where item_list is not null
        defaultInvoiceShouldBeFound("item_list.specified=true");

        // Get all the invoiceList where item_list is null
        defaultInvoiceShouldNotBeFound("item_list.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoicesByItem_listContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where item_list contains DEFAULT_ITEM_LIST
        defaultInvoiceShouldBeFound("item_list.contains=" + DEFAULT_ITEM_LIST);

        // Get all the invoiceList where item_list contains UPDATED_ITEM_LIST
        defaultInvoiceShouldNotBeFound("item_list.contains=" + UPDATED_ITEM_LIST);
    }

    @Test
    @Transactional
    public void getAllInvoicesByItem_listNotContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where item_list does not contain DEFAULT_ITEM_LIST
        defaultInvoiceShouldNotBeFound("item_list.doesNotContain=" + DEFAULT_ITEM_LIST);

        // Get all the invoiceList where item_list does not contain UPDATED_ITEM_LIST
        defaultInvoiceShouldBeFound("item_list.doesNotContain=" + UPDATED_ITEM_LIST);
    }


    @Test
    @Transactional
    public void getAllInvoicesBySubtotalIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where subtotal equals to DEFAULT_SUBTOTAL
        defaultInvoiceShouldBeFound("subtotal.equals=" + DEFAULT_SUBTOTAL);

        // Get all the invoiceList where subtotal equals to UPDATED_SUBTOTAL
        defaultInvoiceShouldNotBeFound("subtotal.equals=" + UPDATED_SUBTOTAL);
    }

    @Test
    @Transactional
    public void getAllInvoicesBySubtotalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where subtotal not equals to DEFAULT_SUBTOTAL
        defaultInvoiceShouldNotBeFound("subtotal.notEquals=" + DEFAULT_SUBTOTAL);

        // Get all the invoiceList where subtotal not equals to UPDATED_SUBTOTAL
        defaultInvoiceShouldBeFound("subtotal.notEquals=" + UPDATED_SUBTOTAL);
    }

    @Test
    @Transactional
    public void getAllInvoicesBySubtotalIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where subtotal in DEFAULT_SUBTOTAL or UPDATED_SUBTOTAL
        defaultInvoiceShouldBeFound("subtotal.in=" + DEFAULT_SUBTOTAL + "," + UPDATED_SUBTOTAL);

        // Get all the invoiceList where subtotal equals to UPDATED_SUBTOTAL
        defaultInvoiceShouldNotBeFound("subtotal.in=" + UPDATED_SUBTOTAL);
    }

    @Test
    @Transactional
    public void getAllInvoicesBySubtotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where subtotal is not null
        defaultInvoiceShouldBeFound("subtotal.specified=true");

        // Get all the invoiceList where subtotal is null
        defaultInvoiceShouldNotBeFound("subtotal.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesBySubtotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where subtotal is greater than or equal to DEFAULT_SUBTOTAL
        defaultInvoiceShouldBeFound("subtotal.greaterThanOrEqual=" + DEFAULT_SUBTOTAL);

        // Get all the invoiceList where subtotal is greater than or equal to UPDATED_SUBTOTAL
        defaultInvoiceShouldNotBeFound("subtotal.greaterThanOrEqual=" + UPDATED_SUBTOTAL);
    }

    @Test
    @Transactional
    public void getAllInvoicesBySubtotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where subtotal is less than or equal to DEFAULT_SUBTOTAL
        defaultInvoiceShouldBeFound("subtotal.lessThanOrEqual=" + DEFAULT_SUBTOTAL);

        // Get all the invoiceList where subtotal is less than or equal to SMALLER_SUBTOTAL
        defaultInvoiceShouldNotBeFound("subtotal.lessThanOrEqual=" + SMALLER_SUBTOTAL);
    }

    @Test
    @Transactional
    public void getAllInvoicesBySubtotalIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where subtotal is less than DEFAULT_SUBTOTAL
        defaultInvoiceShouldNotBeFound("subtotal.lessThan=" + DEFAULT_SUBTOTAL);

        // Get all the invoiceList where subtotal is less than UPDATED_SUBTOTAL
        defaultInvoiceShouldBeFound("subtotal.lessThan=" + UPDATED_SUBTOTAL);
    }

    @Test
    @Transactional
    public void getAllInvoicesBySubtotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where subtotal is greater than DEFAULT_SUBTOTAL
        defaultInvoiceShouldNotBeFound("subtotal.greaterThan=" + DEFAULT_SUBTOTAL);

        // Get all the invoiceList where subtotal is greater than SMALLER_SUBTOTAL
        defaultInvoiceShouldBeFound("subtotal.greaterThan=" + SMALLER_SUBTOTAL);
    }


    @Test
    @Transactional
    public void getAllInvoicesByDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where discount equals to DEFAULT_DISCOUNT
        defaultInvoiceShouldBeFound("discount.equals=" + DEFAULT_DISCOUNT);

        // Get all the invoiceList where discount equals to UPDATED_DISCOUNT
        defaultInvoiceShouldNotBeFound("discount.equals=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDiscountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where discount not equals to DEFAULT_DISCOUNT
        defaultInvoiceShouldNotBeFound("discount.notEquals=" + DEFAULT_DISCOUNT);

        // Get all the invoiceList where discount not equals to UPDATED_DISCOUNT
        defaultInvoiceShouldBeFound("discount.notEquals=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where discount in DEFAULT_DISCOUNT or UPDATED_DISCOUNT
        defaultInvoiceShouldBeFound("discount.in=" + DEFAULT_DISCOUNT + "," + UPDATED_DISCOUNT);

        // Get all the invoiceList where discount equals to UPDATED_DISCOUNT
        defaultInvoiceShouldNotBeFound("discount.in=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where discount is not null
        defaultInvoiceShouldBeFound("discount.specified=true");

        // Get all the invoiceList where discount is null
        defaultInvoiceShouldNotBeFound("discount.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesByDiscountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where discount is greater than or equal to DEFAULT_DISCOUNT
        defaultInvoiceShouldBeFound("discount.greaterThanOrEqual=" + DEFAULT_DISCOUNT);

        // Get all the invoiceList where discount is greater than or equal to UPDATED_DISCOUNT
        defaultInvoiceShouldNotBeFound("discount.greaterThanOrEqual=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDiscountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where discount is less than or equal to DEFAULT_DISCOUNT
        defaultInvoiceShouldBeFound("discount.lessThanOrEqual=" + DEFAULT_DISCOUNT);

        // Get all the invoiceList where discount is less than or equal to SMALLER_DISCOUNT
        defaultInvoiceShouldNotBeFound("discount.lessThanOrEqual=" + SMALLER_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDiscountIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where discount is less than DEFAULT_DISCOUNT
        defaultInvoiceShouldNotBeFound("discount.lessThan=" + DEFAULT_DISCOUNT);

        // Get all the invoiceList where discount is less than UPDATED_DISCOUNT
        defaultInvoiceShouldBeFound("discount.lessThan=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDiscountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where discount is greater than DEFAULT_DISCOUNT
        defaultInvoiceShouldNotBeFound("discount.greaterThan=" + DEFAULT_DISCOUNT);

        // Get all the invoiceList where discount is greater than SMALLER_DISCOUNT
        defaultInvoiceShouldBeFound("discount.greaterThan=" + SMALLER_DISCOUNT);
    }


    @Test
    @Transactional
    public void getAllInvoicesByVatIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where vat equals to DEFAULT_VAT
        defaultInvoiceShouldBeFound("vat.equals=" + DEFAULT_VAT);

        // Get all the invoiceList where vat equals to UPDATED_VAT
        defaultInvoiceShouldNotBeFound("vat.equals=" + UPDATED_VAT);
    }

    @Test
    @Transactional
    public void getAllInvoicesByVatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where vat not equals to DEFAULT_VAT
        defaultInvoiceShouldNotBeFound("vat.notEquals=" + DEFAULT_VAT);

        // Get all the invoiceList where vat not equals to UPDATED_VAT
        defaultInvoiceShouldBeFound("vat.notEquals=" + UPDATED_VAT);
    }

    @Test
    @Transactional
    public void getAllInvoicesByVatIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where vat in DEFAULT_VAT or UPDATED_VAT
        defaultInvoiceShouldBeFound("vat.in=" + DEFAULT_VAT + "," + UPDATED_VAT);

        // Get all the invoiceList where vat equals to UPDATED_VAT
        defaultInvoiceShouldNotBeFound("vat.in=" + UPDATED_VAT);
    }

    @Test
    @Transactional
    public void getAllInvoicesByVatIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where vat is not null
        defaultInvoiceShouldBeFound("vat.specified=true");

        // Get all the invoiceList where vat is null
        defaultInvoiceShouldNotBeFound("vat.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesByVatIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where vat is greater than or equal to DEFAULT_VAT
        defaultInvoiceShouldBeFound("vat.greaterThanOrEqual=" + DEFAULT_VAT);

        // Get all the invoiceList where vat is greater than or equal to UPDATED_VAT
        defaultInvoiceShouldNotBeFound("vat.greaterThanOrEqual=" + UPDATED_VAT);
    }

    @Test
    @Transactional
    public void getAllInvoicesByVatIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where vat is less than or equal to DEFAULT_VAT
        defaultInvoiceShouldBeFound("vat.lessThanOrEqual=" + DEFAULT_VAT);

        // Get all the invoiceList where vat is less than or equal to SMALLER_VAT
        defaultInvoiceShouldNotBeFound("vat.lessThanOrEqual=" + SMALLER_VAT);
    }

    @Test
    @Transactional
    public void getAllInvoicesByVatIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where vat is less than DEFAULT_VAT
        defaultInvoiceShouldNotBeFound("vat.lessThan=" + DEFAULT_VAT);

        // Get all the invoiceList where vat is less than UPDATED_VAT
        defaultInvoiceShouldBeFound("vat.lessThan=" + UPDATED_VAT);
    }

    @Test
    @Transactional
    public void getAllInvoicesByVatIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where vat is greater than DEFAULT_VAT
        defaultInvoiceShouldNotBeFound("vat.greaterThan=" + DEFAULT_VAT);

        // Get all the invoiceList where vat is greater than SMALLER_VAT
        defaultInvoiceShouldBeFound("vat.greaterThan=" + SMALLER_VAT);
    }


    @Test
    @Transactional
    public void getAllInvoicesByTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where total equals to DEFAULT_TOTAL
        defaultInvoiceShouldBeFound("total.equals=" + DEFAULT_TOTAL);

        // Get all the invoiceList where total equals to UPDATED_TOTAL
        defaultInvoiceShouldNotBeFound("total.equals=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTotalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where total not equals to DEFAULT_TOTAL
        defaultInvoiceShouldNotBeFound("total.notEquals=" + DEFAULT_TOTAL);

        // Get all the invoiceList where total not equals to UPDATED_TOTAL
        defaultInvoiceShouldBeFound("total.notEquals=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTotalIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where total in DEFAULT_TOTAL or UPDATED_TOTAL
        defaultInvoiceShouldBeFound("total.in=" + DEFAULT_TOTAL + "," + UPDATED_TOTAL);

        // Get all the invoiceList where total equals to UPDATED_TOTAL
        defaultInvoiceShouldNotBeFound("total.in=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where total is not null
        defaultInvoiceShouldBeFound("total.specified=true");

        // Get all the invoiceList where total is null
        defaultInvoiceShouldNotBeFound("total.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesByTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where total is greater than or equal to DEFAULT_TOTAL
        defaultInvoiceShouldBeFound("total.greaterThanOrEqual=" + DEFAULT_TOTAL);

        // Get all the invoiceList where total is greater than or equal to UPDATED_TOTAL
        defaultInvoiceShouldNotBeFound("total.greaterThanOrEqual=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where total is less than or equal to DEFAULT_TOTAL
        defaultInvoiceShouldBeFound("total.lessThanOrEqual=" + DEFAULT_TOTAL);

        // Get all the invoiceList where total is less than or equal to SMALLER_TOTAL
        defaultInvoiceShouldNotBeFound("total.lessThanOrEqual=" + SMALLER_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where total is less than DEFAULT_TOTAL
        defaultInvoiceShouldNotBeFound("total.lessThan=" + DEFAULT_TOTAL);

        // Get all the invoiceList where total is less than UPDATED_TOTAL
        defaultInvoiceShouldBeFound("total.lessThan=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInvoicesByTotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where total is greater than DEFAULT_TOTAL
        defaultInvoiceShouldNotBeFound("total.greaterThan=" + DEFAULT_TOTAL);

        // Get all the invoiceList where total is greater than SMALLER_TOTAL
        defaultInvoiceShouldBeFound("total.greaterThan=" + SMALLER_TOTAL);
    }


    @Test
    @Transactional
    public void getAllInvoicesByInvoice_dateIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoice_date equals to DEFAULT_INVOICE_DATE
        defaultInvoiceShouldBeFound("invoice_date.equals=" + DEFAULT_INVOICE_DATE);

        // Get all the invoiceList where invoice_date equals to UPDATED_INVOICE_DATE
        defaultInvoiceShouldNotBeFound("invoice_date.equals=" + UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoice_dateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoice_date not equals to DEFAULT_INVOICE_DATE
        defaultInvoiceShouldNotBeFound("invoice_date.notEquals=" + DEFAULT_INVOICE_DATE);

        // Get all the invoiceList where invoice_date not equals to UPDATED_INVOICE_DATE
        defaultInvoiceShouldBeFound("invoice_date.notEquals=" + UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoice_dateIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoice_date in DEFAULT_INVOICE_DATE or UPDATED_INVOICE_DATE
        defaultInvoiceShouldBeFound("invoice_date.in=" + DEFAULT_INVOICE_DATE + "," + UPDATED_INVOICE_DATE);

        // Get all the invoiceList where invoice_date equals to UPDATED_INVOICE_DATE
        defaultInvoiceShouldNotBeFound("invoice_date.in=" + UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoice_dateIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoice_date is not null
        defaultInvoiceShouldBeFound("invoice_date.specified=true");

        // Get all the invoiceList where invoice_date is null
        defaultInvoiceShouldNotBeFound("invoice_date.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoice_dateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoice_date is greater than or equal to DEFAULT_INVOICE_DATE
        defaultInvoiceShouldBeFound("invoice_date.greaterThanOrEqual=" + DEFAULT_INVOICE_DATE);

        // Get all the invoiceList where invoice_date is greater than or equal to UPDATED_INVOICE_DATE
        defaultInvoiceShouldNotBeFound("invoice_date.greaterThanOrEqual=" + UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoice_dateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoice_date is less than or equal to DEFAULT_INVOICE_DATE
        defaultInvoiceShouldBeFound("invoice_date.lessThanOrEqual=" + DEFAULT_INVOICE_DATE);

        // Get all the invoiceList where invoice_date is less than or equal to SMALLER_INVOICE_DATE
        defaultInvoiceShouldNotBeFound("invoice_date.lessThanOrEqual=" + SMALLER_INVOICE_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoice_dateIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoice_date is less than DEFAULT_INVOICE_DATE
        defaultInvoiceShouldNotBeFound("invoice_date.lessThan=" + DEFAULT_INVOICE_DATE);

        // Get all the invoiceList where invoice_date is less than UPDATED_INVOICE_DATE
        defaultInvoiceShouldBeFound("invoice_date.lessThan=" + UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoice_dateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoice_date is greater than DEFAULT_INVOICE_DATE
        defaultInvoiceShouldNotBeFound("invoice_date.greaterThan=" + DEFAULT_INVOICE_DATE);

        // Get all the invoiceList where invoice_date is greater than SMALLER_INVOICE_DATE
        defaultInvoiceShouldBeFound("invoice_date.greaterThan=" + SMALLER_INVOICE_DATE);
    }


    @Test
    @Transactional
    public void getAllInvoicesByTransactionIsEqualToSomething() throws Exception {
        // Get already existing entity
        Transaction transaction = invoice.getTransaction();
        invoiceRepository.saveAndFlush(invoice);
        Long transactionId = transaction.getId();

        // Get all the invoiceList where transaction equals to transactionId
        defaultInvoiceShouldBeFound("transactionId.equals=" + transactionId);

        // Get all the invoiceList where transaction equals to transactionId + 1
        defaultInvoiceShouldNotBeFound("transactionId.equals=" + (transactionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInvoiceShouldBeFound(String filter) throws Exception {
        restInvoiceMockMvc.perform(get("/api/invoices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoice_number").value(hasItem(DEFAULT_INVOICE_NUMBER)))
            .andExpect(jsonPath("$.[*].to").value(hasItem(DEFAULT_TO)))
            .andExpect(jsonPath("$.[*].item_list").value(hasItem(DEFAULT_ITEM_LIST)))
            .andExpect(jsonPath("$.[*].subtotal").value(hasItem(DEFAULT_SUBTOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].vat").value(hasItem(DEFAULT_VAT.doubleValue())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].invoice_date").value(hasItem(sameInstant(DEFAULT_INVOICE_DATE))));

        // Check, that the count call also returns 1
        restInvoiceMockMvc.perform(get("/api/invoices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInvoiceShouldNotBeFound(String filter) throws Exception {
        restInvoiceMockMvc.perform(get("/api/invoices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInvoiceMockMvc.perform(get("/api/invoices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingInvoice() throws Exception {
        // Get the invoice
        restInvoiceMockMvc.perform(get("/api/invoices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvoice() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        int databaseSizeBeforeUpdate = invoiceRepository.findAll().size();

        // Update the invoice
        Invoice updatedInvoice = invoiceRepository.findById(invoice.getId()).get();
        // Disconnect from session so that the updates on updatedInvoice are not directly saved in db
        em.detach(updatedInvoice);
        updatedInvoice
            .invoice_number(UPDATED_INVOICE_NUMBER)
            .to(UPDATED_TO)
            .item_list(UPDATED_ITEM_LIST)
            .subtotal(UPDATED_SUBTOTAL)
            .discount(UPDATED_DISCOUNT)
            .vat(UPDATED_VAT)
            .total(UPDATED_TOTAL)
            .invoice_date(UPDATED_INVOICE_DATE);
        InvoiceDTO invoiceDTO = invoiceMapper.toDto(updatedInvoice);

        restInvoiceMockMvc.perform(put("/api/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoiceDTO)))
            .andExpect(status().isOk());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeUpdate);
        Invoice testInvoice = invoiceList.get(invoiceList.size() - 1);
        assertThat(testInvoice.getInvoice_number()).isEqualTo(UPDATED_INVOICE_NUMBER);
        assertThat(testInvoice.getTo()).isEqualTo(UPDATED_TO);
        assertThat(testInvoice.getItem_list()).isEqualTo(UPDATED_ITEM_LIST);
        assertThat(testInvoice.getSubtotal()).isEqualTo(UPDATED_SUBTOTAL);
        assertThat(testInvoice.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testInvoice.getVat()).isEqualTo(UPDATED_VAT);
        assertThat(testInvoice.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testInvoice.getInvoice_date()).isEqualTo(UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingInvoice() throws Exception {
        int databaseSizeBeforeUpdate = invoiceRepository.findAll().size();

        // Create the Invoice
        InvoiceDTO invoiceDTO = invoiceMapper.toDto(invoice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoiceMockMvc.perform(put("/api/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInvoice() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        int databaseSizeBeforeDelete = invoiceRepository.findAll().size();

        // Delete the invoice
        restInvoiceMockMvc.perform(delete("/api/invoices/{id}", invoice.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
