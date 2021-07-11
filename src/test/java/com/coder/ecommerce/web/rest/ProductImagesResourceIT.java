package com.coder.ecommerce.web.rest;

import com.coder.ecommerce.EcommerceApp;
import com.coder.ecommerce.domain.ProductImages;
import com.coder.ecommerce.repository.ProductImagesRepository;
import com.coder.ecommerce.service.ProductImagesService;
import com.coder.ecommerce.service.dto.ProductImagesDTO;
import com.coder.ecommerce.service.mapper.ProductImagesMapper;

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
 * Integration tests for the {@link ProductImagesResource} REST controller.
 */
@SpringBootTest(classes = EcommerceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductImagesResourceIT {

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    @Autowired
    private ProductImagesRepository productImagesRepository;

    @Autowired
    private ProductImagesMapper productImagesMapper;

    @Autowired
    private ProductImagesService productImagesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductImagesMockMvc;

    private ProductImages productImages;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductImages createEntity(EntityManager em) {
        ProductImages productImages = new ProductImages()
            .image(DEFAULT_IMAGE);
        return productImages;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductImages createUpdatedEntity(EntityManager em) {
        ProductImages productImages = new ProductImages()
            .image(UPDATED_IMAGE);
        return productImages;
    }

    @BeforeEach
    public void initTest() {
        productImages = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductImages() throws Exception {
        int databaseSizeBeforeCreate = productImagesRepository.findAll().size();
        // Create the ProductImages
        ProductImagesDTO productImagesDTO = productImagesMapper.toDto(productImages);
        restProductImagesMockMvc.perform(post("/api/product-images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productImagesDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductImages in the database
        List<ProductImages> productImagesList = productImagesRepository.findAll();
        assertThat(productImagesList).hasSize(databaseSizeBeforeCreate + 1);
        ProductImages testProductImages = productImagesList.get(productImagesList.size() - 1);
        assertThat(testProductImages.getImage()).isEqualTo(DEFAULT_IMAGE);
    }

    @Test
    @Transactional
    public void createProductImagesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productImagesRepository.findAll().size();

        // Create the ProductImages with an existing ID
        productImages.setId(1L);
        ProductImagesDTO productImagesDTO = productImagesMapper.toDto(productImages);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductImagesMockMvc.perform(post("/api/product-images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productImagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductImages in the database
        List<ProductImages> productImagesList = productImagesRepository.findAll();
        assertThat(productImagesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkImageIsRequired() throws Exception {
        int databaseSizeBeforeTest = productImagesRepository.findAll().size();
        // set the field null
        productImages.setImage(null);

        // Create the ProductImages, which fails.
        ProductImagesDTO productImagesDTO = productImagesMapper.toDto(productImages);


        restProductImagesMockMvc.perform(post("/api/product-images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productImagesDTO)))
            .andExpect(status().isBadRequest());

        List<ProductImages> productImagesList = productImagesRepository.findAll();
        assertThat(productImagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductImages() throws Exception {
        // Initialize the database
        productImagesRepository.saveAndFlush(productImages);

        // Get all the productImagesList
        restProductImagesMockMvc.perform(get("/api/product-images?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productImages.getId().intValue())))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE)));
    }
    
    @Test
    @Transactional
    public void getProductImages() throws Exception {
        // Initialize the database
        productImagesRepository.saveAndFlush(productImages);

        // Get the productImages
        restProductImagesMockMvc.perform(get("/api/product-images/{id}", productImages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productImages.getId().intValue()))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE));
    }
    @Test
    @Transactional
    public void getNonExistingProductImages() throws Exception {
        // Get the productImages
        restProductImagesMockMvc.perform(get("/api/product-images/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductImages() throws Exception {
        // Initialize the database
        productImagesRepository.saveAndFlush(productImages);

        int databaseSizeBeforeUpdate = productImagesRepository.findAll().size();

        // Update the productImages
        ProductImages updatedProductImages = productImagesRepository.findById(productImages.getId()).get();
        // Disconnect from session so that the updates on updatedProductImages are not directly saved in db
        em.detach(updatedProductImages);
        updatedProductImages
            .image(UPDATED_IMAGE);
        ProductImagesDTO productImagesDTO = productImagesMapper.toDto(updatedProductImages);

        restProductImagesMockMvc.perform(put("/api/product-images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productImagesDTO)))
            .andExpect(status().isOk());

        // Validate the ProductImages in the database
        List<ProductImages> productImagesList = productImagesRepository.findAll();
        assertThat(productImagesList).hasSize(databaseSizeBeforeUpdate);
        ProductImages testProductImages = productImagesList.get(productImagesList.size() - 1);
        assertThat(testProductImages.getImage()).isEqualTo(UPDATED_IMAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingProductImages() throws Exception {
        int databaseSizeBeforeUpdate = productImagesRepository.findAll().size();

        // Create the ProductImages
        ProductImagesDTO productImagesDTO = productImagesMapper.toDto(productImages);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductImagesMockMvc.perform(put("/api/product-images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productImagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductImages in the database
        List<ProductImages> productImagesList = productImagesRepository.findAll();
        assertThat(productImagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductImages() throws Exception {
        // Initialize the database
        productImagesRepository.saveAndFlush(productImages);

        int databaseSizeBeforeDelete = productImagesRepository.findAll().size();

        // Delete the productImages
        restProductImagesMockMvc.perform(delete("/api/product-images/{id}", productImages.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductImages> productImagesList = productImagesRepository.findAll();
        assertThat(productImagesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
