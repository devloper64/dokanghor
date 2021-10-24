package com.coder.ecommerce.web.rest;

import com.coder.ecommerce.EcommerceApp;
import com.coder.ecommerce.domain.Divisions;
import com.coder.ecommerce.repository.DivisionsRepository;
import com.coder.ecommerce.service.DivisionsService;
import com.coder.ecommerce.service.dto.DivisionsDTO;
import com.coder.ecommerce.service.mapper.DivisionsMapper;
import com.coder.ecommerce.service.dto.DivisionsCriteria;
import com.coder.ecommerce.service.DivisionsQueryService;

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
 * Integration tests for the {@link DivisionsResource} REST controller.
 */
@SpringBootTest(classes = EcommerceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DivisionsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BN_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    @Autowired
    private DivisionsRepository divisionsRepository;

    @Autowired
    private DivisionsMapper divisionsMapper;

    @Autowired
    private DivisionsService divisionsService;

    @Autowired
    private DivisionsQueryService divisionsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDivisionsMockMvc;

    private Divisions divisions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Divisions createEntity(EntityManager em) {
        Divisions divisions = new Divisions()
            .name(DEFAULT_NAME)
            .bn_name(DEFAULT_BN_NAME)
            .url(DEFAULT_URL);
        return divisions;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Divisions createUpdatedEntity(EntityManager em) {
        Divisions divisions = new Divisions()
            .name(UPDATED_NAME)
            .bn_name(UPDATED_BN_NAME)
            .url(UPDATED_URL);
        return divisions;
    }

    @BeforeEach
    public void initTest() {
        divisions = createEntity(em);
    }

    @Test
    @Transactional
    public void createDivisions() throws Exception {
        int databaseSizeBeforeCreate = divisionsRepository.findAll().size();
        // Create the Divisions
        DivisionsDTO divisionsDTO = divisionsMapper.toDto(divisions);
        restDivisionsMockMvc.perform(post("/api/divisions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(divisionsDTO)))
            .andExpect(status().isCreated());

        // Validate the Divisions in the database
        List<Divisions> divisionsList = divisionsRepository.findAll();
        assertThat(divisionsList).hasSize(databaseSizeBeforeCreate + 1);
        Divisions testDivisions = divisionsList.get(divisionsList.size() - 1);
        assertThat(testDivisions.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDivisions.getBn_name()).isEqualTo(DEFAULT_BN_NAME);
        assertThat(testDivisions.getUrl()).isEqualTo(DEFAULT_URL);
    }

    @Test
    @Transactional
    public void createDivisionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = divisionsRepository.findAll().size();

        // Create the Divisions with an existing ID
        divisions.setId(1L);
        DivisionsDTO divisionsDTO = divisionsMapper.toDto(divisions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDivisionsMockMvc.perform(post("/api/divisions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(divisionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Divisions in the database
        List<Divisions> divisionsList = divisionsRepository.findAll();
        assertThat(divisionsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = divisionsRepository.findAll().size();
        // set the field null
        divisions.setName(null);

        // Create the Divisions, which fails.
        DivisionsDTO divisionsDTO = divisionsMapper.toDto(divisions);


        restDivisionsMockMvc.perform(post("/api/divisions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(divisionsDTO)))
            .andExpect(status().isBadRequest());

        List<Divisions> divisionsList = divisionsRepository.findAll();
        assertThat(divisionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBn_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = divisionsRepository.findAll().size();
        // set the field null
        divisions.setBn_name(null);

        // Create the Divisions, which fails.
        DivisionsDTO divisionsDTO = divisionsMapper.toDto(divisions);


        restDivisionsMockMvc.perform(post("/api/divisions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(divisionsDTO)))
            .andExpect(status().isBadRequest());

        List<Divisions> divisionsList = divisionsRepository.findAll();
        assertThat(divisionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = divisionsRepository.findAll().size();
        // set the field null
        divisions.setUrl(null);

        // Create the Divisions, which fails.
        DivisionsDTO divisionsDTO = divisionsMapper.toDto(divisions);


        restDivisionsMockMvc.perform(post("/api/divisions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(divisionsDTO)))
            .andExpect(status().isBadRequest());

        List<Divisions> divisionsList = divisionsRepository.findAll();
        assertThat(divisionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDivisions() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        // Get all the divisionsList
        restDivisionsMockMvc.perform(get("/api/divisions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(divisions.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].bn_name").value(hasItem(DEFAULT_BN_NAME)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)));
    }
    
    @Test
    @Transactional
    public void getDivisions() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        // Get the divisions
        restDivisionsMockMvc.perform(get("/api/divisions/{id}", divisions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(divisions.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.bn_name").value(DEFAULT_BN_NAME))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL));
    }


    @Test
    @Transactional
    public void getDivisionsByIdFiltering() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        Long id = divisions.getId();

        defaultDivisionsShouldBeFound("id.equals=" + id);
        defaultDivisionsShouldNotBeFound("id.notEquals=" + id);

        defaultDivisionsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDivisionsShouldNotBeFound("id.greaterThan=" + id);

        defaultDivisionsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDivisionsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDivisionsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        // Get all the divisionsList where name equals to DEFAULT_NAME
        defaultDivisionsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the divisionsList where name equals to UPDATED_NAME
        defaultDivisionsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDivisionsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        // Get all the divisionsList where name not equals to DEFAULT_NAME
        defaultDivisionsShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the divisionsList where name not equals to UPDATED_NAME
        defaultDivisionsShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDivisionsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        // Get all the divisionsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDivisionsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the divisionsList where name equals to UPDATED_NAME
        defaultDivisionsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDivisionsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        // Get all the divisionsList where name is not null
        defaultDivisionsShouldBeFound("name.specified=true");

        // Get all the divisionsList where name is null
        defaultDivisionsShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllDivisionsByNameContainsSomething() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        // Get all the divisionsList where name contains DEFAULT_NAME
        defaultDivisionsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the divisionsList where name contains UPDATED_NAME
        defaultDivisionsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDivisionsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        // Get all the divisionsList where name does not contain DEFAULT_NAME
        defaultDivisionsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the divisionsList where name does not contain UPDATED_NAME
        defaultDivisionsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllDivisionsByBn_nameIsEqualToSomething() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        // Get all the divisionsList where bn_name equals to DEFAULT_BN_NAME
        defaultDivisionsShouldBeFound("bn_name.equals=" + DEFAULT_BN_NAME);

        // Get all the divisionsList where bn_name equals to UPDATED_BN_NAME
        defaultDivisionsShouldNotBeFound("bn_name.equals=" + UPDATED_BN_NAME);
    }

    @Test
    @Transactional
    public void getAllDivisionsByBn_nameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        // Get all the divisionsList where bn_name not equals to DEFAULT_BN_NAME
        defaultDivisionsShouldNotBeFound("bn_name.notEquals=" + DEFAULT_BN_NAME);

        // Get all the divisionsList where bn_name not equals to UPDATED_BN_NAME
        defaultDivisionsShouldBeFound("bn_name.notEquals=" + UPDATED_BN_NAME);
    }

    @Test
    @Transactional
    public void getAllDivisionsByBn_nameIsInShouldWork() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        // Get all the divisionsList where bn_name in DEFAULT_BN_NAME or UPDATED_BN_NAME
        defaultDivisionsShouldBeFound("bn_name.in=" + DEFAULT_BN_NAME + "," + UPDATED_BN_NAME);

        // Get all the divisionsList where bn_name equals to UPDATED_BN_NAME
        defaultDivisionsShouldNotBeFound("bn_name.in=" + UPDATED_BN_NAME);
    }

    @Test
    @Transactional
    public void getAllDivisionsByBn_nameIsNullOrNotNull() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        // Get all the divisionsList where bn_name is not null
        defaultDivisionsShouldBeFound("bn_name.specified=true");

        // Get all the divisionsList where bn_name is null
        defaultDivisionsShouldNotBeFound("bn_name.specified=false");
    }
                @Test
    @Transactional
    public void getAllDivisionsByBn_nameContainsSomething() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        // Get all the divisionsList where bn_name contains DEFAULT_BN_NAME
        defaultDivisionsShouldBeFound("bn_name.contains=" + DEFAULT_BN_NAME);

        // Get all the divisionsList where bn_name contains UPDATED_BN_NAME
        defaultDivisionsShouldNotBeFound("bn_name.contains=" + UPDATED_BN_NAME);
    }

    @Test
    @Transactional
    public void getAllDivisionsByBn_nameNotContainsSomething() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        // Get all the divisionsList where bn_name does not contain DEFAULT_BN_NAME
        defaultDivisionsShouldNotBeFound("bn_name.doesNotContain=" + DEFAULT_BN_NAME);

        // Get all the divisionsList where bn_name does not contain UPDATED_BN_NAME
        defaultDivisionsShouldBeFound("bn_name.doesNotContain=" + UPDATED_BN_NAME);
    }


    @Test
    @Transactional
    public void getAllDivisionsByUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        // Get all the divisionsList where url equals to DEFAULT_URL
        defaultDivisionsShouldBeFound("url.equals=" + DEFAULT_URL);

        // Get all the divisionsList where url equals to UPDATED_URL
        defaultDivisionsShouldNotBeFound("url.equals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllDivisionsByUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        // Get all the divisionsList where url not equals to DEFAULT_URL
        defaultDivisionsShouldNotBeFound("url.notEquals=" + DEFAULT_URL);

        // Get all the divisionsList where url not equals to UPDATED_URL
        defaultDivisionsShouldBeFound("url.notEquals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllDivisionsByUrlIsInShouldWork() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        // Get all the divisionsList where url in DEFAULT_URL or UPDATED_URL
        defaultDivisionsShouldBeFound("url.in=" + DEFAULT_URL + "," + UPDATED_URL);

        // Get all the divisionsList where url equals to UPDATED_URL
        defaultDivisionsShouldNotBeFound("url.in=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllDivisionsByUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        // Get all the divisionsList where url is not null
        defaultDivisionsShouldBeFound("url.specified=true");

        // Get all the divisionsList where url is null
        defaultDivisionsShouldNotBeFound("url.specified=false");
    }
                @Test
    @Transactional
    public void getAllDivisionsByUrlContainsSomething() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        // Get all the divisionsList where url contains DEFAULT_URL
        defaultDivisionsShouldBeFound("url.contains=" + DEFAULT_URL);

        // Get all the divisionsList where url contains UPDATED_URL
        defaultDivisionsShouldNotBeFound("url.contains=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllDivisionsByUrlNotContainsSomething() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        // Get all the divisionsList where url does not contain DEFAULT_URL
        defaultDivisionsShouldNotBeFound("url.doesNotContain=" + DEFAULT_URL);

        // Get all the divisionsList where url does not contain UPDATED_URL
        defaultDivisionsShouldBeFound("url.doesNotContain=" + UPDATED_URL);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDivisionsShouldBeFound(String filter) throws Exception {
        restDivisionsMockMvc.perform(get("/api/divisions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(divisions.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].bn_name").value(hasItem(DEFAULT_BN_NAME)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)));

        // Check, that the count call also returns 1
        restDivisionsMockMvc.perform(get("/api/divisions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDivisionsShouldNotBeFound(String filter) throws Exception {
        restDivisionsMockMvc.perform(get("/api/divisions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDivisionsMockMvc.perform(get("/api/divisions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDivisions() throws Exception {
        // Get the divisions
        restDivisionsMockMvc.perform(get("/api/divisions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDivisions() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        int databaseSizeBeforeUpdate = divisionsRepository.findAll().size();

        // Update the divisions
        Divisions updatedDivisions = divisionsRepository.findById(divisions.getId()).get();
        // Disconnect from session so that the updates on updatedDivisions are not directly saved in db
        em.detach(updatedDivisions);
        updatedDivisions
            .name(UPDATED_NAME)
            .bn_name(UPDATED_BN_NAME)
            .url(UPDATED_URL);
        DivisionsDTO divisionsDTO = divisionsMapper.toDto(updatedDivisions);

        restDivisionsMockMvc.perform(put("/api/divisions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(divisionsDTO)))
            .andExpect(status().isOk());

        // Validate the Divisions in the database
        List<Divisions> divisionsList = divisionsRepository.findAll();
        assertThat(divisionsList).hasSize(databaseSizeBeforeUpdate);
        Divisions testDivisions = divisionsList.get(divisionsList.size() - 1);
        assertThat(testDivisions.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDivisions.getBn_name()).isEqualTo(UPDATED_BN_NAME);
        assertThat(testDivisions.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingDivisions() throws Exception {
        int databaseSizeBeforeUpdate = divisionsRepository.findAll().size();

        // Create the Divisions
        DivisionsDTO divisionsDTO = divisionsMapper.toDto(divisions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDivisionsMockMvc.perform(put("/api/divisions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(divisionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Divisions in the database
        List<Divisions> divisionsList = divisionsRepository.findAll();
        assertThat(divisionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDivisions() throws Exception {
        // Initialize the database
        divisionsRepository.saveAndFlush(divisions);

        int databaseSizeBeforeDelete = divisionsRepository.findAll().size();

        // Delete the divisions
        restDivisionsMockMvc.perform(delete("/api/divisions/{id}", divisions.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Divisions> divisionsList = divisionsRepository.findAll();
        assertThat(divisionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
