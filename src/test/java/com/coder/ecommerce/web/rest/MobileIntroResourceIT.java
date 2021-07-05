package com.coder.ecommerce.web.rest;

import com.coder.ecommerce.EcommerceApp;
import com.coder.ecommerce.domain.MobileIntro;
import com.coder.ecommerce.repository.MobileIntroRepository;
import com.coder.ecommerce.service.MobileIntroService;
import com.coder.ecommerce.service.dto.MobileIntroDTO;
import com.coder.ecommerce.service.mapper.MobileIntroMapper;

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
 * Integration tests for the {@link MobileIntroResource} REST controller.
 */
@SpringBootTest(classes = EcommerceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MobileIntroResourceIT {

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    @Autowired
    private MobileIntroRepository mobileIntroRepository;

    @Autowired
    private MobileIntroMapper mobileIntroMapper;

    @Autowired
    private MobileIntroService mobileIntroService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMobileIntroMockMvc;

    private MobileIntro mobileIntro;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MobileIntro createEntity(EntityManager em) {
        MobileIntro mobileIntro = new MobileIntro()
            .text(DEFAULT_TEXT)
            .image(DEFAULT_IMAGE);
        return mobileIntro;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MobileIntro createUpdatedEntity(EntityManager em) {
        MobileIntro mobileIntro = new MobileIntro()
            .text(UPDATED_TEXT)
            .image(UPDATED_IMAGE);
        return mobileIntro;
    }

    @BeforeEach
    public void initTest() {
        mobileIntro = createEntity(em);
    }

    @Test
    @Transactional
    public void createMobileIntro() throws Exception {
        int databaseSizeBeforeCreate = mobileIntroRepository.findAll().size();
        // Create the MobileIntro
        MobileIntroDTO mobileIntroDTO = mobileIntroMapper.toDto(mobileIntro);
        restMobileIntroMockMvc.perform(post("/api/mobile-intros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mobileIntroDTO)))
            .andExpect(status().isCreated());

        // Validate the MobileIntro in the database
        List<MobileIntro> mobileIntroList = mobileIntroRepository.findAll();
        assertThat(mobileIntroList).hasSize(databaseSizeBeforeCreate + 1);
        MobileIntro testMobileIntro = mobileIntroList.get(mobileIntroList.size() - 1);
        assertThat(testMobileIntro.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testMobileIntro.getImage()).isEqualTo(DEFAULT_IMAGE);
    }

    @Test
    @Transactional
    public void createMobileIntroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mobileIntroRepository.findAll().size();

        // Create the MobileIntro with an existing ID
        mobileIntro.setId(1L);
        MobileIntroDTO mobileIntroDTO = mobileIntroMapper.toDto(mobileIntro);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMobileIntroMockMvc.perform(post("/api/mobile-intros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mobileIntroDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MobileIntro in the database
        List<MobileIntro> mobileIntroList = mobileIntroRepository.findAll();
        assertThat(mobileIntroList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = mobileIntroRepository.findAll().size();
        // set the field null
        mobileIntro.setText(null);

        // Create the MobileIntro, which fails.
        MobileIntroDTO mobileIntroDTO = mobileIntroMapper.toDto(mobileIntro);


        restMobileIntroMockMvc.perform(post("/api/mobile-intros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mobileIntroDTO)))
            .andExpect(status().isBadRequest());

        List<MobileIntro> mobileIntroList = mobileIntroRepository.findAll();
        assertThat(mobileIntroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImageIsRequired() throws Exception {
        int databaseSizeBeforeTest = mobileIntroRepository.findAll().size();
        // set the field null
        mobileIntro.setImage(null);

        // Create the MobileIntro, which fails.
        MobileIntroDTO mobileIntroDTO = mobileIntroMapper.toDto(mobileIntro);


        restMobileIntroMockMvc.perform(post("/api/mobile-intros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mobileIntroDTO)))
            .andExpect(status().isBadRequest());

        List<MobileIntro> mobileIntroList = mobileIntroRepository.findAll();
        assertThat(mobileIntroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMobileIntros() throws Exception {
        // Initialize the database
        mobileIntroRepository.saveAndFlush(mobileIntro);

        // Get all the mobileIntroList
        restMobileIntroMockMvc.perform(get("/api/mobile-intros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mobileIntro.getId().intValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE)));
    }
    
    @Test
    @Transactional
    public void getMobileIntro() throws Exception {
        // Initialize the database
        mobileIntroRepository.saveAndFlush(mobileIntro);

        // Get the mobileIntro
        restMobileIntroMockMvc.perform(get("/api/mobile-intros/{id}", mobileIntro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mobileIntro.getId().intValue()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE));
    }
    @Test
    @Transactional
    public void getNonExistingMobileIntro() throws Exception {
        // Get the mobileIntro
        restMobileIntroMockMvc.perform(get("/api/mobile-intros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMobileIntro() throws Exception {
        // Initialize the database
        mobileIntroRepository.saveAndFlush(mobileIntro);

        int databaseSizeBeforeUpdate = mobileIntroRepository.findAll().size();

        // Update the mobileIntro
        MobileIntro updatedMobileIntro = mobileIntroRepository.findById(mobileIntro.getId()).get();
        // Disconnect from session so that the updates on updatedMobileIntro are not directly saved in db
        em.detach(updatedMobileIntro);
        updatedMobileIntro
            .text(UPDATED_TEXT)
            .image(UPDATED_IMAGE);
        MobileIntroDTO mobileIntroDTO = mobileIntroMapper.toDto(updatedMobileIntro);

        restMobileIntroMockMvc.perform(put("/api/mobile-intros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mobileIntroDTO)))
            .andExpect(status().isOk());

        // Validate the MobileIntro in the database
        List<MobileIntro> mobileIntroList = mobileIntroRepository.findAll();
        assertThat(mobileIntroList).hasSize(databaseSizeBeforeUpdate);
        MobileIntro testMobileIntro = mobileIntroList.get(mobileIntroList.size() - 1);
        assertThat(testMobileIntro.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testMobileIntro.getImage()).isEqualTo(UPDATED_IMAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingMobileIntro() throws Exception {
        int databaseSizeBeforeUpdate = mobileIntroRepository.findAll().size();

        // Create the MobileIntro
        MobileIntroDTO mobileIntroDTO = mobileIntroMapper.toDto(mobileIntro);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMobileIntroMockMvc.perform(put("/api/mobile-intros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mobileIntroDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MobileIntro in the database
        List<MobileIntro> mobileIntroList = mobileIntroRepository.findAll();
        assertThat(mobileIntroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMobileIntro() throws Exception {
        // Initialize the database
        mobileIntroRepository.saveAndFlush(mobileIntro);

        int databaseSizeBeforeDelete = mobileIntroRepository.findAll().size();

        // Delete the mobileIntro
        restMobileIntroMockMvc.perform(delete("/api/mobile-intros/{id}", mobileIntro.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MobileIntro> mobileIntroList = mobileIntroRepository.findAll();
        assertThat(mobileIntroList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
