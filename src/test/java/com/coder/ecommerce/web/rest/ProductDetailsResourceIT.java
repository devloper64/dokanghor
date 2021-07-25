package com.coder.ecommerce.web.rest;

import com.coder.ecommerce.EcommerceApp;
import com.coder.ecommerce.domain.ProductDetails;
import com.coder.ecommerce.domain.Product;
import com.coder.ecommerce.repository.ProductDetailsRepository;
import com.coder.ecommerce.service.ProductDetailsService;
import com.coder.ecommerce.service.dto.ProductDetailsDTO;
import com.coder.ecommerce.service.mapper.ProductDetailsMapper;
import com.coder.ecommerce.service.dto.ProductDetailsCriteria;
import com.coder.ecommerce.service.ProductDetailsQueryService;

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
 * Integration tests for the {@link ProductDetailsResource} REST controller.
 */
@SpringBootTest(classes = EcommerceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductDetailsResourceIT {

    private static final String DEFAULT_BRAND = "AAAAAAAAAA";
    private static final String UPDATED_BRAND = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final String DEFAULT_STYLE = "AAAAAAAAAA";
    private static final String UPDATED_STYLE = "BBBBBBBBBB";

    private static final String DEFAULT_SIZE_MESAURMENTS = "AAAAAAAAAA";
    private static final String UPDATED_SIZE_MESAURMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_SIZE_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_SIZE_DETAILS = "BBBBBBBBBB";

    @Autowired
    private ProductDetailsRepository productDetailsRepository;

    @Autowired
    private ProductDetailsMapper productDetailsMapper;

    @Autowired
    private ProductDetailsService productDetailsService;

    @Autowired
    private ProductDetailsQueryService productDetailsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductDetailsMockMvc;

    private ProductDetails productDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductDetails createEntity(EntityManager em) {
        ProductDetails productDetails = new ProductDetails()
            .brand(DEFAULT_BRAND)
            .color(DEFAULT_COLOR)
            .gender(DEFAULT_GENDER)
            .style(DEFAULT_STYLE)
            .size_mesaurments(DEFAULT_SIZE_MESAURMENTS)
            .size_details(DEFAULT_SIZE_DETAILS);
        return productDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductDetails createUpdatedEntity(EntityManager em) {
        ProductDetails productDetails = new ProductDetails()
            .brand(UPDATED_BRAND)
            .color(UPDATED_COLOR)
            .gender(UPDATED_GENDER)
            .style(UPDATED_STYLE)
            .size_mesaurments(UPDATED_SIZE_MESAURMENTS)
            .size_details(UPDATED_SIZE_DETAILS);
        return productDetails;
    }

    @BeforeEach
    public void initTest() {
        productDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductDetails() throws Exception {
        int databaseSizeBeforeCreate = productDetailsRepository.findAll().size();
        // Create the ProductDetails
        ProductDetailsDTO productDetailsDTO = productDetailsMapper.toDto(productDetails);
        restProductDetailsMockMvc.perform(post("/api/product-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductDetails in the database
        List<ProductDetails> productDetailsList = productDetailsRepository.findAll();
        assertThat(productDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        ProductDetails testProductDetails = productDetailsList.get(productDetailsList.size() - 1);
        assertThat(testProductDetails.getBrand()).isEqualTo(DEFAULT_BRAND);
        assertThat(testProductDetails.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testProductDetails.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testProductDetails.getStyle()).isEqualTo(DEFAULT_STYLE);
        assertThat(testProductDetails.getSize_mesaurments()).isEqualTo(DEFAULT_SIZE_MESAURMENTS);
        assertThat(testProductDetails.getSize_details()).isEqualTo(DEFAULT_SIZE_DETAILS);
    }

    @Test
    @Transactional
    public void createProductDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productDetailsRepository.findAll().size();

        // Create the ProductDetails with an existing ID
        productDetails.setId(1L);
        ProductDetailsDTO productDetailsDTO = productDetailsMapper.toDto(productDetails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductDetailsMockMvc.perform(post("/api/product-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductDetails in the database
        List<ProductDetails> productDetailsList = productDetailsRepository.findAll();
        assertThat(productDetailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductDetails() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList
        restProductDetailsMockMvc.perform(get("/api/product-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].brand").value(hasItem(DEFAULT_BRAND)))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].style").value(hasItem(DEFAULT_STYLE)))
            .andExpect(jsonPath("$.[*].size_mesaurments").value(hasItem(DEFAULT_SIZE_MESAURMENTS)))
            .andExpect(jsonPath("$.[*].size_details").value(hasItem(DEFAULT_SIZE_DETAILS)));
    }

    @Test
    @Transactional
    public void getProductDetails() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get the productDetails
        restProductDetailsMockMvc.perform(get("/api/product-details/{id}", productDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productDetails.getId().intValue()))
            .andExpect(jsonPath("$.brand").value(DEFAULT_BRAND))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.style").value(DEFAULT_STYLE))
            .andExpect(jsonPath("$.size_mesaurments").value(DEFAULT_SIZE_MESAURMENTS))
            .andExpect(jsonPath("$.size_details").value(DEFAULT_SIZE_DETAILS));
    }


    @Test
    @Transactional
    public void getProductDetailsByIdFiltering() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        Long id = productDetails.getId();

        defaultProductDetailsShouldBeFound("id.equals=" + id);
        defaultProductDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultProductDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultProductDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductDetailsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProductDetailsByBrandIsEqualToSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where brand equals to DEFAULT_BRAND
        defaultProductDetailsShouldBeFound("brand.equals=" + DEFAULT_BRAND);

        // Get all the productDetailsList where brand equals to UPDATED_BRAND
        defaultProductDetailsShouldNotBeFound("brand.equals=" + UPDATED_BRAND);
    }

    @Test
    @Transactional
    public void getAllProductDetailsByBrandIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where brand not equals to DEFAULT_BRAND
        defaultProductDetailsShouldNotBeFound("brand.notEquals=" + DEFAULT_BRAND);

        // Get all the productDetailsList where brand not equals to UPDATED_BRAND
        defaultProductDetailsShouldBeFound("brand.notEquals=" + UPDATED_BRAND);
    }

    @Test
    @Transactional
    public void getAllProductDetailsByBrandIsInShouldWork() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where brand in DEFAULT_BRAND or UPDATED_BRAND
        defaultProductDetailsShouldBeFound("brand.in=" + DEFAULT_BRAND + "," + UPDATED_BRAND);

        // Get all the productDetailsList where brand equals to UPDATED_BRAND
        defaultProductDetailsShouldNotBeFound("brand.in=" + UPDATED_BRAND);
    }

    @Test
    @Transactional
    public void getAllProductDetailsByBrandIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where brand is not null
        defaultProductDetailsShouldBeFound("brand.specified=true");

        // Get all the productDetailsList where brand is null
        defaultProductDetailsShouldNotBeFound("brand.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDetailsByBrandContainsSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where brand contains DEFAULT_BRAND
        defaultProductDetailsShouldBeFound("brand.contains=" + DEFAULT_BRAND);

        // Get all the productDetailsList where brand contains UPDATED_BRAND
        defaultProductDetailsShouldNotBeFound("brand.contains=" + UPDATED_BRAND);
    }

    @Test
    @Transactional
    public void getAllProductDetailsByBrandNotContainsSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where brand does not contain DEFAULT_BRAND
        defaultProductDetailsShouldNotBeFound("brand.doesNotContain=" + DEFAULT_BRAND);

        // Get all the productDetailsList where brand does not contain UPDATED_BRAND
        defaultProductDetailsShouldBeFound("brand.doesNotContain=" + UPDATED_BRAND);
    }


    @Test
    @Transactional
    public void getAllProductDetailsByColorIsEqualToSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where color equals to DEFAULT_COLOR
        defaultProductDetailsShouldBeFound("color.equals=" + DEFAULT_COLOR);

        // Get all the productDetailsList where color equals to UPDATED_COLOR
        defaultProductDetailsShouldNotBeFound("color.equals=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    public void getAllProductDetailsByColorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where color not equals to DEFAULT_COLOR
        defaultProductDetailsShouldNotBeFound("color.notEquals=" + DEFAULT_COLOR);

        // Get all the productDetailsList where color not equals to UPDATED_COLOR
        defaultProductDetailsShouldBeFound("color.notEquals=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    public void getAllProductDetailsByColorIsInShouldWork() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where color in DEFAULT_COLOR or UPDATED_COLOR
        defaultProductDetailsShouldBeFound("color.in=" + DEFAULT_COLOR + "," + UPDATED_COLOR);

        // Get all the productDetailsList where color equals to UPDATED_COLOR
        defaultProductDetailsShouldNotBeFound("color.in=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    public void getAllProductDetailsByColorIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where color is not null
        defaultProductDetailsShouldBeFound("color.specified=true");

        // Get all the productDetailsList where color is null
        defaultProductDetailsShouldNotBeFound("color.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDetailsByColorContainsSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where color contains DEFAULT_COLOR
        defaultProductDetailsShouldBeFound("color.contains=" + DEFAULT_COLOR);

        // Get all the productDetailsList where color contains UPDATED_COLOR
        defaultProductDetailsShouldNotBeFound("color.contains=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    public void getAllProductDetailsByColorNotContainsSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where color does not contain DEFAULT_COLOR
        defaultProductDetailsShouldNotBeFound("color.doesNotContain=" + DEFAULT_COLOR);

        // Get all the productDetailsList where color does not contain UPDATED_COLOR
        defaultProductDetailsShouldBeFound("color.doesNotContain=" + UPDATED_COLOR);
    }


    @Test
    @Transactional
    public void getAllProductDetailsByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where gender equals to DEFAULT_GENDER
        defaultProductDetailsShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the productDetailsList where gender equals to UPDATED_GENDER
        defaultProductDetailsShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllProductDetailsByGenderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where gender not equals to DEFAULT_GENDER
        defaultProductDetailsShouldNotBeFound("gender.notEquals=" + DEFAULT_GENDER);

        // Get all the productDetailsList where gender not equals to UPDATED_GENDER
        defaultProductDetailsShouldBeFound("gender.notEquals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllProductDetailsByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultProductDetailsShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the productDetailsList where gender equals to UPDATED_GENDER
        defaultProductDetailsShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllProductDetailsByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where gender is not null
        defaultProductDetailsShouldBeFound("gender.specified=true");

        // Get all the productDetailsList where gender is null
        defaultProductDetailsShouldNotBeFound("gender.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDetailsByGenderContainsSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where gender contains DEFAULT_GENDER
        defaultProductDetailsShouldBeFound("gender.contains=" + DEFAULT_GENDER);

        // Get all the productDetailsList where gender contains UPDATED_GENDER
        defaultProductDetailsShouldNotBeFound("gender.contains=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllProductDetailsByGenderNotContainsSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where gender does not contain DEFAULT_GENDER
        defaultProductDetailsShouldNotBeFound("gender.doesNotContain=" + DEFAULT_GENDER);

        // Get all the productDetailsList where gender does not contain UPDATED_GENDER
        defaultProductDetailsShouldBeFound("gender.doesNotContain=" + UPDATED_GENDER);
    }


    @Test
    @Transactional
    public void getAllProductDetailsByStyleIsEqualToSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where style equals to DEFAULT_STYLE
        defaultProductDetailsShouldBeFound("style.equals=" + DEFAULT_STYLE);

        // Get all the productDetailsList where style equals to UPDATED_STYLE
        defaultProductDetailsShouldNotBeFound("style.equals=" + UPDATED_STYLE);
    }

    @Test
    @Transactional
    public void getAllProductDetailsByStyleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where style not equals to DEFAULT_STYLE
        defaultProductDetailsShouldNotBeFound("style.notEquals=" + DEFAULT_STYLE);

        // Get all the productDetailsList where style not equals to UPDATED_STYLE
        defaultProductDetailsShouldBeFound("style.notEquals=" + UPDATED_STYLE);
    }

    @Test
    @Transactional
    public void getAllProductDetailsByStyleIsInShouldWork() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where style in DEFAULT_STYLE or UPDATED_STYLE
        defaultProductDetailsShouldBeFound("style.in=" + DEFAULT_STYLE + "," + UPDATED_STYLE);

        // Get all the productDetailsList where style equals to UPDATED_STYLE
        defaultProductDetailsShouldNotBeFound("style.in=" + UPDATED_STYLE);
    }

    @Test
    @Transactional
    public void getAllProductDetailsByStyleIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where style is not null
        defaultProductDetailsShouldBeFound("style.specified=true");

        // Get all the productDetailsList where style is null
        defaultProductDetailsShouldNotBeFound("style.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDetailsByStyleContainsSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where style contains DEFAULT_STYLE
        defaultProductDetailsShouldBeFound("style.contains=" + DEFAULT_STYLE);

        // Get all the productDetailsList where style contains UPDATED_STYLE
        defaultProductDetailsShouldNotBeFound("style.contains=" + UPDATED_STYLE);
    }

    @Test
    @Transactional
    public void getAllProductDetailsByStyleNotContainsSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where style does not contain DEFAULT_STYLE
        defaultProductDetailsShouldNotBeFound("style.doesNotContain=" + DEFAULT_STYLE);

        // Get all the productDetailsList where style does not contain UPDATED_STYLE
        defaultProductDetailsShouldBeFound("style.doesNotContain=" + UPDATED_STYLE);
    }


    @Test
    @Transactional
    public void getAllProductDetailsBySize_mesaurmentsIsEqualToSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where size_mesaurments equals to DEFAULT_SIZE_MESAURMENTS
        defaultProductDetailsShouldBeFound("size_mesaurments.equals=" + DEFAULT_SIZE_MESAURMENTS);

        // Get all the productDetailsList where size_mesaurments equals to UPDATED_SIZE_MESAURMENTS
        defaultProductDetailsShouldNotBeFound("size_mesaurments.equals=" + UPDATED_SIZE_MESAURMENTS);
    }

    @Test
    @Transactional
    public void getAllProductDetailsBySize_mesaurmentsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where size_mesaurments not equals to DEFAULT_SIZE_MESAURMENTS
        defaultProductDetailsShouldNotBeFound("size_mesaurments.notEquals=" + DEFAULT_SIZE_MESAURMENTS);

        // Get all the productDetailsList where size_mesaurments not equals to UPDATED_SIZE_MESAURMENTS
        defaultProductDetailsShouldBeFound("size_mesaurments.notEquals=" + UPDATED_SIZE_MESAURMENTS);
    }

    @Test
    @Transactional
    public void getAllProductDetailsBySize_mesaurmentsIsInShouldWork() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where size_mesaurments in DEFAULT_SIZE_MESAURMENTS or UPDATED_SIZE_MESAURMENTS
        defaultProductDetailsShouldBeFound("size_mesaurments.in=" + DEFAULT_SIZE_MESAURMENTS + "," + UPDATED_SIZE_MESAURMENTS);

        // Get all the productDetailsList where size_mesaurments equals to UPDATED_SIZE_MESAURMENTS
        defaultProductDetailsShouldNotBeFound("size_mesaurments.in=" + UPDATED_SIZE_MESAURMENTS);
    }

    @Test
    @Transactional
    public void getAllProductDetailsBySize_mesaurmentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where size_mesaurments is not null
        defaultProductDetailsShouldBeFound("size_mesaurments.specified=true");

        // Get all the productDetailsList where size_mesaurments is null
        defaultProductDetailsShouldNotBeFound("size_mesaurments.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDetailsBySize_mesaurmentsContainsSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where size_mesaurments contains DEFAULT_SIZE_MESAURMENTS
        defaultProductDetailsShouldBeFound("size_mesaurments.contains=" + DEFAULT_SIZE_MESAURMENTS);

        // Get all the productDetailsList where size_mesaurments contains UPDATED_SIZE_MESAURMENTS
        defaultProductDetailsShouldNotBeFound("size_mesaurments.contains=" + UPDATED_SIZE_MESAURMENTS);
    }

    @Test
    @Transactional
    public void getAllProductDetailsBySize_mesaurmentsNotContainsSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where size_mesaurments does not contain DEFAULT_SIZE_MESAURMENTS
        defaultProductDetailsShouldNotBeFound("size_mesaurments.doesNotContain=" + DEFAULT_SIZE_MESAURMENTS);

        // Get all the productDetailsList where size_mesaurments does not contain UPDATED_SIZE_MESAURMENTS
        defaultProductDetailsShouldBeFound("size_mesaurments.doesNotContain=" + UPDATED_SIZE_MESAURMENTS);
    }


    @Test
    @Transactional
    public void getAllProductDetailsBySize_detailsIsEqualToSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where size_details equals to DEFAULT_SIZE_DETAILS
        defaultProductDetailsShouldBeFound("size_details.equals=" + DEFAULT_SIZE_DETAILS);

        // Get all the productDetailsList where size_details equals to UPDATED_SIZE_DETAILS
        defaultProductDetailsShouldNotBeFound("size_details.equals=" + UPDATED_SIZE_DETAILS);
    }

    @Test
    @Transactional
    public void getAllProductDetailsBySize_detailsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where size_details not equals to DEFAULT_SIZE_DETAILS
        defaultProductDetailsShouldNotBeFound("size_details.notEquals=" + DEFAULT_SIZE_DETAILS);

        // Get all the productDetailsList where size_details not equals to UPDATED_SIZE_DETAILS
        defaultProductDetailsShouldBeFound("size_details.notEquals=" + UPDATED_SIZE_DETAILS);
    }

    @Test
    @Transactional
    public void getAllProductDetailsBySize_detailsIsInShouldWork() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where size_details in DEFAULT_SIZE_DETAILS or UPDATED_SIZE_DETAILS
        defaultProductDetailsShouldBeFound("size_details.in=" + DEFAULT_SIZE_DETAILS + "," + UPDATED_SIZE_DETAILS);

        // Get all the productDetailsList where size_details equals to UPDATED_SIZE_DETAILS
        defaultProductDetailsShouldNotBeFound("size_details.in=" + UPDATED_SIZE_DETAILS);
    }

    @Test
    @Transactional
    public void getAllProductDetailsBySize_detailsIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where size_details is not null
        defaultProductDetailsShouldBeFound("size_details.specified=true");

        // Get all the productDetailsList where size_details is null
        defaultProductDetailsShouldNotBeFound("size_details.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductDetailsBySize_detailsContainsSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where size_details contains DEFAULT_SIZE_DETAILS
        defaultProductDetailsShouldBeFound("size_details.contains=" + DEFAULT_SIZE_DETAILS);

        // Get all the productDetailsList where size_details contains UPDATED_SIZE_DETAILS
        defaultProductDetailsShouldNotBeFound("size_details.contains=" + UPDATED_SIZE_DETAILS);
    }

    @Test
    @Transactional
    public void getAllProductDetailsBySize_detailsNotContainsSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where size_details does not contain DEFAULT_SIZE_DETAILS
        defaultProductDetailsShouldNotBeFound("size_details.doesNotContain=" + DEFAULT_SIZE_DETAILS);

        // Get all the productDetailsList where size_details does not contain UPDATED_SIZE_DETAILS
        defaultProductDetailsShouldBeFound("size_details.doesNotContain=" + UPDATED_SIZE_DETAILS);
    }


    @Test
    @Transactional
    public void getAllProductDetailsByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);
        Product product = ProductResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        product.setProductDetails(productDetails);
        productDetailsRepository.saveAndFlush(productDetails);
        Long productId = product.getId();

        // Get all the productDetailsList where product equals to productId
        defaultProductDetailsShouldBeFound("productId.equals=" + productId);

        // Get all the productDetailsList where product equals to productId + 1
        defaultProductDetailsShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductDetailsShouldBeFound(String filter) throws Exception {
        restProductDetailsMockMvc.perform(get("/api/product-details?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].brand").value(hasItem(DEFAULT_BRAND)))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].style").value(hasItem(DEFAULT_STYLE)))
            .andExpect(jsonPath("$.[*].size_mesaurments").value(hasItem(DEFAULT_SIZE_MESAURMENTS)))
            .andExpect(jsonPath("$.[*].size_details").value(hasItem(DEFAULT_SIZE_DETAILS)));

        // Check, that the count call also returns 1
        restProductDetailsMockMvc.perform(get("/api/product-details/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductDetailsShouldNotBeFound(String filter) throws Exception {
        restProductDetailsMockMvc.perform(get("/api/product-details?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductDetailsMockMvc.perform(get("/api/product-details/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProductDetails() throws Exception {
        // Get the productDetails
        restProductDetailsMockMvc.perform(get("/api/product-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductDetails() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        int databaseSizeBeforeUpdate = productDetailsRepository.findAll().size();

        // Update the productDetails
        ProductDetails updatedProductDetails = productDetailsRepository.findById(productDetails.getId()).get();
        // Disconnect from session so that the updates on updatedProductDetails are not directly saved in db
        em.detach(updatedProductDetails);
        updatedProductDetails
            .brand(UPDATED_BRAND)
            .color(UPDATED_COLOR)
            .gender(UPDATED_GENDER)
            .style(UPDATED_STYLE)
            .size_mesaurments(UPDATED_SIZE_MESAURMENTS)
            .size_details(UPDATED_SIZE_DETAILS);
        ProductDetailsDTO productDetailsDTO = productDetailsMapper.toDto(updatedProductDetails);

        restProductDetailsMockMvc.perform(put("/api/product-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDetailsDTO)))
            .andExpect(status().isOk());

        // Validate the ProductDetails in the database
        List<ProductDetails> productDetailsList = productDetailsRepository.findAll();
        assertThat(productDetailsList).hasSize(databaseSizeBeforeUpdate);
        ProductDetails testProductDetails = productDetailsList.get(productDetailsList.size() - 1);
        assertThat(testProductDetails.getBrand()).isEqualTo(UPDATED_BRAND);
        assertThat(testProductDetails.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testProductDetails.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testProductDetails.getStyle()).isEqualTo(UPDATED_STYLE);
        assertThat(testProductDetails.getSize_mesaurments()).isEqualTo(UPDATED_SIZE_MESAURMENTS);
        assertThat(testProductDetails.getSize_details()).isEqualTo(UPDATED_SIZE_DETAILS);
    }

    @Test
    @Transactional
    public void updateNonExistingProductDetails() throws Exception {
        int databaseSizeBeforeUpdate = productDetailsRepository.findAll().size();

        // Create the ProductDetails
        ProductDetailsDTO productDetailsDTO = productDetailsMapper.toDto(productDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductDetailsMockMvc.perform(put("/api/product-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductDetails in the database
        List<ProductDetails> productDetailsList = productDetailsRepository.findAll();
        assertThat(productDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductDetails() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        int databaseSizeBeforeDelete = productDetailsRepository.findAll().size();

        // Delete the productDetails
        restProductDetailsMockMvc.perform(delete("/api/product-details/{id}", productDetails.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductDetails> productDetailsList = productDetailsRepository.findAll();
        assertThat(productDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
