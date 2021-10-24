package com.coder.ecommerce.web.rest;

import com.coder.ecommerce.EcommerceApp;
import com.coder.ecommerce.domain.Upazilas;
import com.coder.ecommerce.domain.Districts;
import com.coder.ecommerce.repository.UpazilasRepository;
import com.coder.ecommerce.service.UpazilasService;
import com.coder.ecommerce.service.dto.UpazilasDTO;
import com.coder.ecommerce.service.mapper.UpazilasMapper;
import com.coder.ecommerce.service.dto.UpazilasCriteria;
import com.coder.ecommerce.service.UpazilasQueryService;

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
 * Integration tests for the {@link UpazilasResource} REST controller.
 */
@SpringBootTest(classes = EcommerceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UpazilasResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BN_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    @Autowired
    private UpazilasRepository upazilasRepository;

    @Autowired
    private UpazilasMapper upazilasMapper;

    @Autowired
    private UpazilasService upazilasService;

    @Autowired
    private UpazilasQueryService upazilasQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUpazilasMockMvc;

    private Upazilas upazilas;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Upazilas createEntity(EntityManager em) {
        Upazilas upazilas = new Upazilas()
            .name(DEFAULT_NAME)
            .bn_name(DEFAULT_BN_NAME)
            .url(DEFAULT_URL);
        // Add required entity
        Districts districts;
        if (TestUtil.findAll(em, Districts.class).isEmpty()) {
            districts = DistrictsResourceIT.createEntity(em);
            em.persist(districts);
            em.flush();
        } else {
            districts = TestUtil.findAll(em, Districts.class).get(0);
        }
        upazilas.setDistricts(districts);
        return upazilas;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Upazilas createUpdatedEntity(EntityManager em) {
        Upazilas upazilas = new Upazilas()
            .name(UPDATED_NAME)
            .bn_name(UPDATED_BN_NAME)
            .url(UPDATED_URL);
        // Add required entity
        Districts districts;
        if (TestUtil.findAll(em, Districts.class).isEmpty()) {
            districts = DistrictsResourceIT.createUpdatedEntity(em);
            em.persist(districts);
            em.flush();
        } else {
            districts = TestUtil.findAll(em, Districts.class).get(0);
        }
        upazilas.setDistricts(districts);
        return upazilas;
    }

    @BeforeEach
    public void initTest() {
        upazilas = createEntity(em);
    }

    @Test
    @Transactional
    public void createUpazilas() throws Exception {
        int databaseSizeBeforeCreate = upazilasRepository.findAll().size();
        // Create the Upazilas
        UpazilasDTO upazilasDTO = upazilasMapper.toDto(upazilas);
        restUpazilasMockMvc.perform(post("/api/upazilas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(upazilasDTO)))
            .andExpect(status().isCreated());

        // Validate the Upazilas in the database
        List<Upazilas> upazilasList = upazilasRepository.findAll();
        assertThat(upazilasList).hasSize(databaseSizeBeforeCreate + 1);
        Upazilas testUpazilas = upazilasList.get(upazilasList.size() - 1);
        assertThat(testUpazilas.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUpazilas.getBn_name()).isEqualTo(DEFAULT_BN_NAME);
        assertThat(testUpazilas.getUrl()).isEqualTo(DEFAULT_URL);
    }

    @Test
    @Transactional
    public void createUpazilasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = upazilasRepository.findAll().size();

        // Create the Upazilas with an existing ID
        upazilas.setId(1L);
        UpazilasDTO upazilasDTO = upazilasMapper.toDto(upazilas);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUpazilasMockMvc.perform(post("/api/upazilas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(upazilasDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Upazilas in the database
        List<Upazilas> upazilasList = upazilasRepository.findAll();
        assertThat(upazilasList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = upazilasRepository.findAll().size();
        // set the field null
        upazilas.setName(null);

        // Create the Upazilas, which fails.
        UpazilasDTO upazilasDTO = upazilasMapper.toDto(upazilas);


        restUpazilasMockMvc.perform(post("/api/upazilas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(upazilasDTO)))
            .andExpect(status().isBadRequest());

        List<Upazilas> upazilasList = upazilasRepository.findAll();
        assertThat(upazilasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBn_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = upazilasRepository.findAll().size();
        // set the field null
        upazilas.setBn_name(null);

        // Create the Upazilas, which fails.
        UpazilasDTO upazilasDTO = upazilasMapper.toDto(upazilas);


        restUpazilasMockMvc.perform(post("/api/upazilas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(upazilasDTO)))
            .andExpect(status().isBadRequest());

        List<Upazilas> upazilasList = upazilasRepository.findAll();
        assertThat(upazilasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = upazilasRepository.findAll().size();
        // set the field null
        upazilas.setUrl(null);

        // Create the Upazilas, which fails.
        UpazilasDTO upazilasDTO = upazilasMapper.toDto(upazilas);


        restUpazilasMockMvc.perform(post("/api/upazilas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(upazilasDTO)))
            .andExpect(status().isBadRequest());

        List<Upazilas> upazilasList = upazilasRepository.findAll();
        assertThat(upazilasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUpazilas() throws Exception {
        // Initialize the database
        upazilasRepository.saveAndFlush(upazilas);

        // Get all the upazilasList
        restUpazilasMockMvc.perform(get("/api/upazilas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(upazilas.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].bn_name").value(hasItem(DEFAULT_BN_NAME)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)));
    }
    
    @Test
    @Transactional
    public void getUpazilas() throws Exception {
        // Initialize the database
        upazilasRepository.saveAndFlush(upazilas);

        // Get the upazilas
        restUpazilasMockMvc.perform(get("/api/upazilas/{id}", upazilas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(upazilas.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.bn_name").value(DEFAULT_BN_NAME))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL));
    }


    @Test
    @Transactional
    public void getUpazilasByIdFiltering() throws Exception {
        // Initialize the database
        upazilasRepository.saveAndFlush(upazilas);

        Long id = upazilas.getId();

        defaultUpazilasShouldBeFound("id.equals=" + id);
        defaultUpazilasShouldNotBeFound("id.notEquals=" + id);

        defaultUpazilasShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUpazilasShouldNotBeFound("id.greaterThan=" + id);

        defaultUpazilasShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUpazilasShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllUpazilasByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        upazilasRepository.saveAndFlush(upazilas);

        // Get all the upazilasList where name equals to DEFAULT_NAME
        defaultUpazilasShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the upazilasList where name equals to UPDATED_NAME
        defaultUpazilasShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUpazilasByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        upazilasRepository.saveAndFlush(upazilas);

        // Get all the upazilasList where name not equals to DEFAULT_NAME
        defaultUpazilasShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the upazilasList where name not equals to UPDATED_NAME
        defaultUpazilasShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUpazilasByNameIsInShouldWork() throws Exception {
        // Initialize the database
        upazilasRepository.saveAndFlush(upazilas);

        // Get all the upazilasList where name in DEFAULT_NAME or UPDATED_NAME
        defaultUpazilasShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the upazilasList where name equals to UPDATED_NAME
        defaultUpazilasShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUpazilasByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        upazilasRepository.saveAndFlush(upazilas);

        // Get all the upazilasList where name is not null
        defaultUpazilasShouldBeFound("name.specified=true");

        // Get all the upazilasList where name is null
        defaultUpazilasShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllUpazilasByNameContainsSomething() throws Exception {
        // Initialize the database
        upazilasRepository.saveAndFlush(upazilas);

        // Get all the upazilasList where name contains DEFAULT_NAME
        defaultUpazilasShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the upazilasList where name contains UPDATED_NAME
        defaultUpazilasShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUpazilasByNameNotContainsSomething() throws Exception {
        // Initialize the database
        upazilasRepository.saveAndFlush(upazilas);

        // Get all the upazilasList where name does not contain DEFAULT_NAME
        defaultUpazilasShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the upazilasList where name does not contain UPDATED_NAME
        defaultUpazilasShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllUpazilasByBn_nameIsEqualToSomething() throws Exception {
        // Initialize the database
        upazilasRepository.saveAndFlush(upazilas);

        // Get all the upazilasList where bn_name equals to DEFAULT_BN_NAME
        defaultUpazilasShouldBeFound("bn_name.equals=" + DEFAULT_BN_NAME);

        // Get all the upazilasList where bn_name equals to UPDATED_BN_NAME
        defaultUpazilasShouldNotBeFound("bn_name.equals=" + UPDATED_BN_NAME);
    }

    @Test
    @Transactional
    public void getAllUpazilasByBn_nameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        upazilasRepository.saveAndFlush(upazilas);

        // Get all the upazilasList where bn_name not equals to DEFAULT_BN_NAME
        defaultUpazilasShouldNotBeFound("bn_name.notEquals=" + DEFAULT_BN_NAME);

        // Get all the upazilasList where bn_name not equals to UPDATED_BN_NAME
        defaultUpazilasShouldBeFound("bn_name.notEquals=" + UPDATED_BN_NAME);
    }

    @Test
    @Transactional
    public void getAllUpazilasByBn_nameIsInShouldWork() throws Exception {
        // Initialize the database
        upazilasRepository.saveAndFlush(upazilas);

        // Get all the upazilasList where bn_name in DEFAULT_BN_NAME or UPDATED_BN_NAME
        defaultUpazilasShouldBeFound("bn_name.in=" + DEFAULT_BN_NAME + "," + UPDATED_BN_NAME);

        // Get all the upazilasList where bn_name equals to UPDATED_BN_NAME
        defaultUpazilasShouldNotBeFound("bn_name.in=" + UPDATED_BN_NAME);
    }

    @Test
    @Transactional
    public void getAllUpazilasByBn_nameIsNullOrNotNull() throws Exception {
        // Initialize the database
        upazilasRepository.saveAndFlush(upazilas);

        // Get all the upazilasList where bn_name is not null
        defaultUpazilasShouldBeFound("bn_name.specified=true");

        // Get all the upazilasList where bn_name is null
        defaultUpazilasShouldNotBeFound("bn_name.specified=false");
    }
                @Test
    @Transactional
    public void getAllUpazilasByBn_nameContainsSomething() throws Exception {
        // Initialize the database
        upazilasRepository.saveAndFlush(upazilas);

        // Get all the upazilasList where bn_name contains DEFAULT_BN_NAME
        defaultUpazilasShouldBeFound("bn_name.contains=" + DEFAULT_BN_NAME);

        // Get all the upazilasList where bn_name contains UPDATED_BN_NAME
        defaultUpazilasShouldNotBeFound("bn_name.contains=" + UPDATED_BN_NAME);
    }

    @Test
    @Transactional
    public void getAllUpazilasByBn_nameNotContainsSomething() throws Exception {
        // Initialize the database
        upazilasRepository.saveAndFlush(upazilas);

        // Get all the upazilasList where bn_name does not contain DEFAULT_BN_NAME
        defaultUpazilasShouldNotBeFound("bn_name.doesNotContain=" + DEFAULT_BN_NAME);

        // Get all the upazilasList where bn_name does not contain UPDATED_BN_NAME
        defaultUpazilasShouldBeFound("bn_name.doesNotContain=" + UPDATED_BN_NAME);
    }


    @Test
    @Transactional
    public void getAllUpazilasByUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        upazilasRepository.saveAndFlush(upazilas);

        // Get all the upazilasList where url equals to DEFAULT_URL
        defaultUpazilasShouldBeFound("url.equals=" + DEFAULT_URL);

        // Get all the upazilasList where url equals to UPDATED_URL
        defaultUpazilasShouldNotBeFound("url.equals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllUpazilasByUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        upazilasRepository.saveAndFlush(upazilas);

        // Get all the upazilasList where url not equals to DEFAULT_URL
        defaultUpazilasShouldNotBeFound("url.notEquals=" + DEFAULT_URL);

        // Get all the upazilasList where url not equals to UPDATED_URL
        defaultUpazilasShouldBeFound("url.notEquals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllUpazilasByUrlIsInShouldWork() throws Exception {
        // Initialize the database
        upazilasRepository.saveAndFlush(upazilas);

        // Get all the upazilasList where url in DEFAULT_URL or UPDATED_URL
        defaultUpazilasShouldBeFound("url.in=" + DEFAULT_URL + "," + UPDATED_URL);

        // Get all the upazilasList where url equals to UPDATED_URL
        defaultUpazilasShouldNotBeFound("url.in=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllUpazilasByUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        upazilasRepository.saveAndFlush(upazilas);

        // Get all the upazilasList where url is not null
        defaultUpazilasShouldBeFound("url.specified=true");

        // Get all the upazilasList where url is null
        defaultUpazilasShouldNotBeFound("url.specified=false");
    }
                @Test
    @Transactional
    public void getAllUpazilasByUrlContainsSomething() throws Exception {
        // Initialize the database
        upazilasRepository.saveAndFlush(upazilas);

        // Get all the upazilasList where url contains DEFAULT_URL
        defaultUpazilasShouldBeFound("url.contains=" + DEFAULT_URL);

        // Get all the upazilasList where url contains UPDATED_URL
        defaultUpazilasShouldNotBeFound("url.contains=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllUpazilasByUrlNotContainsSomething() throws Exception {
        // Initialize the database
        upazilasRepository.saveAndFlush(upazilas);

        // Get all the upazilasList where url does not contain DEFAULT_URL
        defaultUpazilasShouldNotBeFound("url.doesNotContain=" + DEFAULT_URL);

        // Get all the upazilasList where url does not contain UPDATED_URL
        defaultUpazilasShouldBeFound("url.doesNotContain=" + UPDATED_URL);
    }


    @Test
    @Transactional
    public void getAllUpazilasByDistrictsIsEqualToSomething() throws Exception {
        // Get already existing entity
        Districts districts = upazilas.getDistricts();
        upazilasRepository.saveAndFlush(upazilas);
        Long districtsId = districts.getId();

        // Get all the upazilasList where districts equals to districtsId
        defaultUpazilasShouldBeFound("districtsId.equals=" + districtsId);

        // Get all the upazilasList where districts equals to districtsId + 1
        defaultUpazilasShouldNotBeFound("districtsId.equals=" + (districtsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUpazilasShouldBeFound(String filter) throws Exception {
        restUpazilasMockMvc.perform(get("/api/upazilas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(upazilas.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].bn_name").value(hasItem(DEFAULT_BN_NAME)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)));

        // Check, that the count call also returns 1
        restUpazilasMockMvc.perform(get("/api/upazilas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUpazilasShouldNotBeFound(String filter) throws Exception {
        restUpazilasMockMvc.perform(get("/api/upazilas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUpazilasMockMvc.perform(get("/api/upazilas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingUpazilas() throws Exception {
        // Get the upazilas
        restUpazilasMockMvc.perform(get("/api/upazilas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUpazilas() throws Exception {
        // Initialize the database
        upazilasRepository.saveAndFlush(upazilas);

        int databaseSizeBeforeUpdate = upazilasRepository.findAll().size();

        // Update the upazilas
        Upazilas updatedUpazilas = upazilasRepository.findById(upazilas.getId()).get();
        // Disconnect from session so that the updates on updatedUpazilas are not directly saved in db
        em.detach(updatedUpazilas);
        updatedUpazilas
            .name(UPDATED_NAME)
            .bn_name(UPDATED_BN_NAME)
            .url(UPDATED_URL);
        UpazilasDTO upazilasDTO = upazilasMapper.toDto(updatedUpazilas);

        restUpazilasMockMvc.perform(put("/api/upazilas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(upazilasDTO)))
            .andExpect(status().isOk());

        // Validate the Upazilas in the database
        List<Upazilas> upazilasList = upazilasRepository.findAll();
        assertThat(upazilasList).hasSize(databaseSizeBeforeUpdate);
        Upazilas testUpazilas = upazilasList.get(upazilasList.size() - 1);
        assertThat(testUpazilas.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUpazilas.getBn_name()).isEqualTo(UPDATED_BN_NAME);
        assertThat(testUpazilas.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingUpazilas() throws Exception {
        int databaseSizeBeforeUpdate = upazilasRepository.findAll().size();

        // Create the Upazilas
        UpazilasDTO upazilasDTO = upazilasMapper.toDto(upazilas);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUpazilasMockMvc.perform(put("/api/upazilas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(upazilasDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Upazilas in the database
        List<Upazilas> upazilasList = upazilasRepository.findAll();
        assertThat(upazilasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUpazilas() throws Exception {
        // Initialize the database
        upazilasRepository.saveAndFlush(upazilas);

        int databaseSizeBeforeDelete = upazilasRepository.findAll().size();

        // Delete the upazilas
        restUpazilasMockMvc.perform(delete("/api/upazilas/{id}", upazilas.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Upazilas> upazilasList = upazilasRepository.findAll();
        assertThat(upazilasList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
