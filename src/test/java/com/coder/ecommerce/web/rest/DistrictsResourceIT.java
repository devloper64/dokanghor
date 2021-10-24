package com.coder.ecommerce.web.rest;

import com.coder.ecommerce.EcommerceApp;
import com.coder.ecommerce.domain.Districts;
import com.coder.ecommerce.domain.Divisions;
import com.coder.ecommerce.repository.DistrictsRepository;
import com.coder.ecommerce.service.DistrictsService;
import com.coder.ecommerce.service.dto.DistrictsDTO;
import com.coder.ecommerce.service.mapper.DistrictsMapper;
import com.coder.ecommerce.service.dto.DistrictsCriteria;
import com.coder.ecommerce.service.DistrictsQueryService;

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
 * Integration tests for the {@link DistrictsResource} REST controller.
 */
@SpringBootTest(classes = EcommerceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DistrictsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BN_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAT = "AAAAAAAAAA";
    private static final String UPDATED_LAT = "BBBBBBBBBB";

    private static final String DEFAULT_LON = "AAAAAAAAAA";
    private static final String UPDATED_LON = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    @Autowired
    private DistrictsRepository districtsRepository;

    @Autowired
    private DistrictsMapper districtsMapper;

    @Autowired
    private DistrictsService districtsService;

    @Autowired
    private DistrictsQueryService districtsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDistrictsMockMvc;

    private Districts districts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Districts createEntity(EntityManager em) {
        Districts districts = new Districts()
            .name(DEFAULT_NAME)
            .bn_name(DEFAULT_BN_NAME)
            .lat(DEFAULT_LAT)
            .lon(DEFAULT_LON)
            .url(DEFAULT_URL);
        // Add required entity
        Divisions divisions;
        if (TestUtil.findAll(em, Divisions.class).isEmpty()) {
            divisions = DivisionsResourceIT.createEntity(em);
            em.persist(divisions);
            em.flush();
        } else {
            divisions = TestUtil.findAll(em, Divisions.class).get(0);
        }
        districts.setDivisions(divisions);
        return districts;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Districts createUpdatedEntity(EntityManager em) {
        Districts districts = new Districts()
            .name(UPDATED_NAME)
            .bn_name(UPDATED_BN_NAME)
            .lat(UPDATED_LAT)
            .lon(UPDATED_LON)
            .url(UPDATED_URL);
        // Add required entity
        Divisions divisions;
        if (TestUtil.findAll(em, Divisions.class).isEmpty()) {
            divisions = DivisionsResourceIT.createUpdatedEntity(em);
            em.persist(divisions);
            em.flush();
        } else {
            divisions = TestUtil.findAll(em, Divisions.class).get(0);
        }
        districts.setDivisions(divisions);
        return districts;
    }

    @BeforeEach
    public void initTest() {
        districts = createEntity(em);
    }

    @Test
    @Transactional
    public void createDistricts() throws Exception {
        int databaseSizeBeforeCreate = districtsRepository.findAll().size();
        // Create the Districts
        DistrictsDTO districtsDTO = districtsMapper.toDto(districts);
        restDistrictsMockMvc.perform(post("/api/districts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(districtsDTO)))
            .andExpect(status().isCreated());

        // Validate the Districts in the database
        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeCreate + 1);
        Districts testDistricts = districtsList.get(districtsList.size() - 1);
        assertThat(testDistricts.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDistricts.getBn_name()).isEqualTo(DEFAULT_BN_NAME);
        assertThat(testDistricts.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testDistricts.getLon()).isEqualTo(DEFAULT_LON);
        assertThat(testDistricts.getUrl()).isEqualTo(DEFAULT_URL);
    }

    @Test
    @Transactional
    public void createDistrictsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = districtsRepository.findAll().size();

        // Create the Districts with an existing ID
        districts.setId(1L);
        DistrictsDTO districtsDTO = districtsMapper.toDto(districts);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDistrictsMockMvc.perform(post("/api/districts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(districtsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Districts in the database
        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = districtsRepository.findAll().size();
        // set the field null
        districts.setName(null);

        // Create the Districts, which fails.
        DistrictsDTO districtsDTO = districtsMapper.toDto(districts);


        restDistrictsMockMvc.perform(post("/api/districts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(districtsDTO)))
            .andExpect(status().isBadRequest());

        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBn_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = districtsRepository.findAll().size();
        // set the field null
        districts.setBn_name(null);

        // Create the Districts, which fails.
        DistrictsDTO districtsDTO = districtsMapper.toDto(districts);


        restDistrictsMockMvc.perform(post("/api/districts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(districtsDTO)))
            .andExpect(status().isBadRequest());

        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLatIsRequired() throws Exception {
        int databaseSizeBeforeTest = districtsRepository.findAll().size();
        // set the field null
        districts.setLat(null);

        // Create the Districts, which fails.
        DistrictsDTO districtsDTO = districtsMapper.toDto(districts);


        restDistrictsMockMvc.perform(post("/api/districts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(districtsDTO)))
            .andExpect(status().isBadRequest());

        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLonIsRequired() throws Exception {
        int databaseSizeBeforeTest = districtsRepository.findAll().size();
        // set the field null
        districts.setLon(null);

        // Create the Districts, which fails.
        DistrictsDTO districtsDTO = districtsMapper.toDto(districts);


        restDistrictsMockMvc.perform(post("/api/districts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(districtsDTO)))
            .andExpect(status().isBadRequest());

        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = districtsRepository.findAll().size();
        // set the field null
        districts.setUrl(null);

        // Create the Districts, which fails.
        DistrictsDTO districtsDTO = districtsMapper.toDto(districts);


        restDistrictsMockMvc.perform(post("/api/districts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(districtsDTO)))
            .andExpect(status().isBadRequest());

        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDistricts() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList
        restDistrictsMockMvc.perform(get("/api/districts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(districts.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].bn_name").value(hasItem(DEFAULT_BN_NAME)))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT)))
            .andExpect(jsonPath("$.[*].lon").value(hasItem(DEFAULT_LON)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)));
    }
    
    @Test
    @Transactional
    public void getDistricts() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get the districts
        restDistrictsMockMvc.perform(get("/api/districts/{id}", districts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(districts.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.bn_name").value(DEFAULT_BN_NAME))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT))
            .andExpect(jsonPath("$.lon").value(DEFAULT_LON))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL));
    }


    @Test
    @Transactional
    public void getDistrictsByIdFiltering() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        Long id = districts.getId();

        defaultDistrictsShouldBeFound("id.equals=" + id);
        defaultDistrictsShouldNotBeFound("id.notEquals=" + id);

        defaultDistrictsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDistrictsShouldNotBeFound("id.greaterThan=" + id);

        defaultDistrictsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDistrictsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDistrictsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where name equals to DEFAULT_NAME
        defaultDistrictsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the districtsList where name equals to UPDATED_NAME
        defaultDistrictsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDistrictsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where name not equals to DEFAULT_NAME
        defaultDistrictsShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the districtsList where name not equals to UPDATED_NAME
        defaultDistrictsShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDistrictsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDistrictsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the districtsList where name equals to UPDATED_NAME
        defaultDistrictsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDistrictsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where name is not null
        defaultDistrictsShouldBeFound("name.specified=true");

        // Get all the districtsList where name is null
        defaultDistrictsShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllDistrictsByNameContainsSomething() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where name contains DEFAULT_NAME
        defaultDistrictsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the districtsList where name contains UPDATED_NAME
        defaultDistrictsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDistrictsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where name does not contain DEFAULT_NAME
        defaultDistrictsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the districtsList where name does not contain UPDATED_NAME
        defaultDistrictsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllDistrictsByBn_nameIsEqualToSomething() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where bn_name equals to DEFAULT_BN_NAME
        defaultDistrictsShouldBeFound("bn_name.equals=" + DEFAULT_BN_NAME);

        // Get all the districtsList where bn_name equals to UPDATED_BN_NAME
        defaultDistrictsShouldNotBeFound("bn_name.equals=" + UPDATED_BN_NAME);
    }

    @Test
    @Transactional
    public void getAllDistrictsByBn_nameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where bn_name not equals to DEFAULT_BN_NAME
        defaultDistrictsShouldNotBeFound("bn_name.notEquals=" + DEFAULT_BN_NAME);

        // Get all the districtsList where bn_name not equals to UPDATED_BN_NAME
        defaultDistrictsShouldBeFound("bn_name.notEquals=" + UPDATED_BN_NAME);
    }

    @Test
    @Transactional
    public void getAllDistrictsByBn_nameIsInShouldWork() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where bn_name in DEFAULT_BN_NAME or UPDATED_BN_NAME
        defaultDistrictsShouldBeFound("bn_name.in=" + DEFAULT_BN_NAME + "," + UPDATED_BN_NAME);

        // Get all the districtsList where bn_name equals to UPDATED_BN_NAME
        defaultDistrictsShouldNotBeFound("bn_name.in=" + UPDATED_BN_NAME);
    }

    @Test
    @Transactional
    public void getAllDistrictsByBn_nameIsNullOrNotNull() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where bn_name is not null
        defaultDistrictsShouldBeFound("bn_name.specified=true");

        // Get all the districtsList where bn_name is null
        defaultDistrictsShouldNotBeFound("bn_name.specified=false");
    }
                @Test
    @Transactional
    public void getAllDistrictsByBn_nameContainsSomething() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where bn_name contains DEFAULT_BN_NAME
        defaultDistrictsShouldBeFound("bn_name.contains=" + DEFAULT_BN_NAME);

        // Get all the districtsList where bn_name contains UPDATED_BN_NAME
        defaultDistrictsShouldNotBeFound("bn_name.contains=" + UPDATED_BN_NAME);
    }

    @Test
    @Transactional
    public void getAllDistrictsByBn_nameNotContainsSomething() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where bn_name does not contain DEFAULT_BN_NAME
        defaultDistrictsShouldNotBeFound("bn_name.doesNotContain=" + DEFAULT_BN_NAME);

        // Get all the districtsList where bn_name does not contain UPDATED_BN_NAME
        defaultDistrictsShouldBeFound("bn_name.doesNotContain=" + UPDATED_BN_NAME);
    }


    @Test
    @Transactional
    public void getAllDistrictsByLatIsEqualToSomething() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where lat equals to DEFAULT_LAT
        defaultDistrictsShouldBeFound("lat.equals=" + DEFAULT_LAT);

        // Get all the districtsList where lat equals to UPDATED_LAT
        defaultDistrictsShouldNotBeFound("lat.equals=" + UPDATED_LAT);
    }

    @Test
    @Transactional
    public void getAllDistrictsByLatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where lat not equals to DEFAULT_LAT
        defaultDistrictsShouldNotBeFound("lat.notEquals=" + DEFAULT_LAT);

        // Get all the districtsList where lat not equals to UPDATED_LAT
        defaultDistrictsShouldBeFound("lat.notEquals=" + UPDATED_LAT);
    }

    @Test
    @Transactional
    public void getAllDistrictsByLatIsInShouldWork() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where lat in DEFAULT_LAT or UPDATED_LAT
        defaultDistrictsShouldBeFound("lat.in=" + DEFAULT_LAT + "," + UPDATED_LAT);

        // Get all the districtsList where lat equals to UPDATED_LAT
        defaultDistrictsShouldNotBeFound("lat.in=" + UPDATED_LAT);
    }

    @Test
    @Transactional
    public void getAllDistrictsByLatIsNullOrNotNull() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where lat is not null
        defaultDistrictsShouldBeFound("lat.specified=true");

        // Get all the districtsList where lat is null
        defaultDistrictsShouldNotBeFound("lat.specified=false");
    }
                @Test
    @Transactional
    public void getAllDistrictsByLatContainsSomething() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where lat contains DEFAULT_LAT
        defaultDistrictsShouldBeFound("lat.contains=" + DEFAULT_LAT);

        // Get all the districtsList where lat contains UPDATED_LAT
        defaultDistrictsShouldNotBeFound("lat.contains=" + UPDATED_LAT);
    }

    @Test
    @Transactional
    public void getAllDistrictsByLatNotContainsSomething() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where lat does not contain DEFAULT_LAT
        defaultDistrictsShouldNotBeFound("lat.doesNotContain=" + DEFAULT_LAT);

        // Get all the districtsList where lat does not contain UPDATED_LAT
        defaultDistrictsShouldBeFound("lat.doesNotContain=" + UPDATED_LAT);
    }


    @Test
    @Transactional
    public void getAllDistrictsByLonIsEqualToSomething() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where lon equals to DEFAULT_LON
        defaultDistrictsShouldBeFound("lon.equals=" + DEFAULT_LON);

        // Get all the districtsList where lon equals to UPDATED_LON
        defaultDistrictsShouldNotBeFound("lon.equals=" + UPDATED_LON);
    }

    @Test
    @Transactional
    public void getAllDistrictsByLonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where lon not equals to DEFAULT_LON
        defaultDistrictsShouldNotBeFound("lon.notEquals=" + DEFAULT_LON);

        // Get all the districtsList where lon not equals to UPDATED_LON
        defaultDistrictsShouldBeFound("lon.notEquals=" + UPDATED_LON);
    }

    @Test
    @Transactional
    public void getAllDistrictsByLonIsInShouldWork() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where lon in DEFAULT_LON or UPDATED_LON
        defaultDistrictsShouldBeFound("lon.in=" + DEFAULT_LON + "," + UPDATED_LON);

        // Get all the districtsList where lon equals to UPDATED_LON
        defaultDistrictsShouldNotBeFound("lon.in=" + UPDATED_LON);
    }

    @Test
    @Transactional
    public void getAllDistrictsByLonIsNullOrNotNull() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where lon is not null
        defaultDistrictsShouldBeFound("lon.specified=true");

        // Get all the districtsList where lon is null
        defaultDistrictsShouldNotBeFound("lon.specified=false");
    }
                @Test
    @Transactional
    public void getAllDistrictsByLonContainsSomething() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where lon contains DEFAULT_LON
        defaultDistrictsShouldBeFound("lon.contains=" + DEFAULT_LON);

        // Get all the districtsList where lon contains UPDATED_LON
        defaultDistrictsShouldNotBeFound("lon.contains=" + UPDATED_LON);
    }

    @Test
    @Transactional
    public void getAllDistrictsByLonNotContainsSomething() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where lon does not contain DEFAULT_LON
        defaultDistrictsShouldNotBeFound("lon.doesNotContain=" + DEFAULT_LON);

        // Get all the districtsList where lon does not contain UPDATED_LON
        defaultDistrictsShouldBeFound("lon.doesNotContain=" + UPDATED_LON);
    }


    @Test
    @Transactional
    public void getAllDistrictsByUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where url equals to DEFAULT_URL
        defaultDistrictsShouldBeFound("url.equals=" + DEFAULT_URL);

        // Get all the districtsList where url equals to UPDATED_URL
        defaultDistrictsShouldNotBeFound("url.equals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllDistrictsByUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where url not equals to DEFAULT_URL
        defaultDistrictsShouldNotBeFound("url.notEquals=" + DEFAULT_URL);

        // Get all the districtsList where url not equals to UPDATED_URL
        defaultDistrictsShouldBeFound("url.notEquals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllDistrictsByUrlIsInShouldWork() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where url in DEFAULT_URL or UPDATED_URL
        defaultDistrictsShouldBeFound("url.in=" + DEFAULT_URL + "," + UPDATED_URL);

        // Get all the districtsList where url equals to UPDATED_URL
        defaultDistrictsShouldNotBeFound("url.in=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllDistrictsByUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where url is not null
        defaultDistrictsShouldBeFound("url.specified=true");

        // Get all the districtsList where url is null
        defaultDistrictsShouldNotBeFound("url.specified=false");
    }
                @Test
    @Transactional
    public void getAllDistrictsByUrlContainsSomething() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where url contains DEFAULT_URL
        defaultDistrictsShouldBeFound("url.contains=" + DEFAULT_URL);

        // Get all the districtsList where url contains UPDATED_URL
        defaultDistrictsShouldNotBeFound("url.contains=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllDistrictsByUrlNotContainsSomething() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        // Get all the districtsList where url does not contain DEFAULT_URL
        defaultDistrictsShouldNotBeFound("url.doesNotContain=" + DEFAULT_URL);

        // Get all the districtsList where url does not contain UPDATED_URL
        defaultDistrictsShouldBeFound("url.doesNotContain=" + UPDATED_URL);
    }


    @Test
    @Transactional
    public void getAllDistrictsByDivisionsIsEqualToSomething() throws Exception {
        // Get already existing entity
        Divisions divisions = districts.getDivisions();
        districtsRepository.saveAndFlush(districts);
        Long divisionsId = divisions.getId();

        // Get all the districtsList where divisions equals to divisionsId
        defaultDistrictsShouldBeFound("divisionsId.equals=" + divisionsId);

        // Get all the districtsList where divisions equals to divisionsId + 1
        defaultDistrictsShouldNotBeFound("divisionsId.equals=" + (divisionsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDistrictsShouldBeFound(String filter) throws Exception {
        restDistrictsMockMvc.perform(get("/api/districts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(districts.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].bn_name").value(hasItem(DEFAULT_BN_NAME)))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT)))
            .andExpect(jsonPath("$.[*].lon").value(hasItem(DEFAULT_LON)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)));

        // Check, that the count call also returns 1
        restDistrictsMockMvc.perform(get("/api/districts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDistrictsShouldNotBeFound(String filter) throws Exception {
        restDistrictsMockMvc.perform(get("/api/districts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDistrictsMockMvc.perform(get("/api/districts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDistricts() throws Exception {
        // Get the districts
        restDistrictsMockMvc.perform(get("/api/districts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDistricts() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        int databaseSizeBeforeUpdate = districtsRepository.findAll().size();

        // Update the districts
        Districts updatedDistricts = districtsRepository.findById(districts.getId()).get();
        // Disconnect from session so that the updates on updatedDistricts are not directly saved in db
        em.detach(updatedDistricts);
        updatedDistricts
            .name(UPDATED_NAME)
            .bn_name(UPDATED_BN_NAME)
            .lat(UPDATED_LAT)
            .lon(UPDATED_LON)
            .url(UPDATED_URL);
        DistrictsDTO districtsDTO = districtsMapper.toDto(updatedDistricts);

        restDistrictsMockMvc.perform(put("/api/districts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(districtsDTO)))
            .andExpect(status().isOk());

        // Validate the Districts in the database
        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeUpdate);
        Districts testDistricts = districtsList.get(districtsList.size() - 1);
        assertThat(testDistricts.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDistricts.getBn_name()).isEqualTo(UPDATED_BN_NAME);
        assertThat(testDistricts.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testDistricts.getLon()).isEqualTo(UPDATED_LON);
        assertThat(testDistricts.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingDistricts() throws Exception {
        int databaseSizeBeforeUpdate = districtsRepository.findAll().size();

        // Create the Districts
        DistrictsDTO districtsDTO = districtsMapper.toDto(districts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDistrictsMockMvc.perform(put("/api/districts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(districtsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Districts in the database
        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDistricts() throws Exception {
        // Initialize the database
        districtsRepository.saveAndFlush(districts);

        int databaseSizeBeforeDelete = districtsRepository.findAll().size();

        // Delete the districts
        restDistrictsMockMvc.perform(delete("/api/districts/{id}", districts.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
