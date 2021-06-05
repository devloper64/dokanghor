package com.coder.ecommerce.web.rest;

import com.coder.ecommerce.EcommerceApp;
import com.coder.ecommerce.domain.ShippingAddress;
import com.coder.ecommerce.repository.ShippingAddressRepository;
import com.coder.ecommerce.service.ShippingAddressService;
import com.coder.ecommerce.service.dto.ShippingAddressDTO;
import com.coder.ecommerce.service.mapper.ShippingAddressMapper;
import com.coder.ecommerce.service.dto.ShippingAddressCriteria;
import com.coder.ecommerce.service.ShippingAddressQueryService;

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
 * Integration tests for the {@link ShippingAddressResource} REST controller.
 */
@SpringBootTest(classes = EcommerceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ShippingAddressResourceIT {

    private static final String DEFAULT_DISTRICT = "AAAAAAAAAA";
    private static final String UPDATED_DISTRICT = "BBBBBBBBBB";

    private static final String DEFAULT_UPAZILA = "AAAAAAAAAA";
    private static final String UPDATED_UPAZILA = "BBBBBBBBBB";

    private static final String DEFAULT_POSTALCODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTALCODE = "BBBBBBBBBB";

    @Autowired
    private ShippingAddressRepository shippingAddressRepository;

    @Autowired
    private ShippingAddressMapper shippingAddressMapper;

    @Autowired
    private ShippingAddressService shippingAddressService;

    @Autowired
    private ShippingAddressQueryService shippingAddressQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restShippingAddressMockMvc;

    private ShippingAddress shippingAddress;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShippingAddress createEntity(EntityManager em) {
        ShippingAddress shippingAddress = new ShippingAddress()
            .district(DEFAULT_DISTRICT)
            .upazila(DEFAULT_UPAZILA)
            .postalcode(DEFAULT_POSTALCODE);
        return shippingAddress;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShippingAddress createUpdatedEntity(EntityManager em) {
        ShippingAddress shippingAddress = new ShippingAddress()
            .district(UPDATED_DISTRICT)
            .upazila(UPDATED_UPAZILA)
            .postalcode(UPDATED_POSTALCODE);
        return shippingAddress;
    }

    @BeforeEach
    public void initTest() {
        shippingAddress = createEntity(em);
    }

    @Test
    @Transactional
    public void createShippingAddress() throws Exception {
        int databaseSizeBeforeCreate = shippingAddressRepository.findAll().size();
        // Create the ShippingAddress
        ShippingAddressDTO shippingAddressDTO = shippingAddressMapper.toDto(shippingAddress);
        restShippingAddressMockMvc.perform(post("/api/shipping-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shippingAddressDTO)))
            .andExpect(status().isCreated());

        // Validate the ShippingAddress in the database
        List<ShippingAddress> shippingAddressList = shippingAddressRepository.findAll();
        assertThat(shippingAddressList).hasSize(databaseSizeBeforeCreate + 1);
        ShippingAddress testShippingAddress = shippingAddressList.get(shippingAddressList.size() - 1);
        assertThat(testShippingAddress.getDistrict()).isEqualTo(DEFAULT_DISTRICT);
        assertThat(testShippingAddress.getUpazila()).isEqualTo(DEFAULT_UPAZILA);
        assertThat(testShippingAddress.getPostalcode()).isEqualTo(DEFAULT_POSTALCODE);
    }

    @Test
    @Transactional
    public void createShippingAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shippingAddressRepository.findAll().size();

        // Create the ShippingAddress with an existing ID
        shippingAddress.setId(1L);
        ShippingAddressDTO shippingAddressDTO = shippingAddressMapper.toDto(shippingAddress);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShippingAddressMockMvc.perform(post("/api/shipping-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shippingAddressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShippingAddress in the database
        List<ShippingAddress> shippingAddressList = shippingAddressRepository.findAll();
        assertThat(shippingAddressList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDistrictIsRequired() throws Exception {
        int databaseSizeBeforeTest = shippingAddressRepository.findAll().size();
        // set the field null
        shippingAddress.setDistrict(null);

        // Create the ShippingAddress, which fails.
        ShippingAddressDTO shippingAddressDTO = shippingAddressMapper.toDto(shippingAddress);


        restShippingAddressMockMvc.perform(post("/api/shipping-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shippingAddressDTO)))
            .andExpect(status().isBadRequest());

        List<ShippingAddress> shippingAddressList = shippingAddressRepository.findAll();
        assertThat(shippingAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpazilaIsRequired() throws Exception {
        int databaseSizeBeforeTest = shippingAddressRepository.findAll().size();
        // set the field null
        shippingAddress.setUpazila(null);

        // Create the ShippingAddress, which fails.
        ShippingAddressDTO shippingAddressDTO = shippingAddressMapper.toDto(shippingAddress);


        restShippingAddressMockMvc.perform(post("/api/shipping-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shippingAddressDTO)))
            .andExpect(status().isBadRequest());

        List<ShippingAddress> shippingAddressList = shippingAddressRepository.findAll();
        assertThat(shippingAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostalcodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = shippingAddressRepository.findAll().size();
        // set the field null
        shippingAddress.setPostalcode(null);

        // Create the ShippingAddress, which fails.
        ShippingAddressDTO shippingAddressDTO = shippingAddressMapper.toDto(shippingAddress);


        restShippingAddressMockMvc.perform(post("/api/shipping-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shippingAddressDTO)))
            .andExpect(status().isBadRequest());

        List<ShippingAddress> shippingAddressList = shippingAddressRepository.findAll();
        assertThat(shippingAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllShippingAddresses() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList
        restShippingAddressMockMvc.perform(get("/api/shipping-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shippingAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].district").value(hasItem(DEFAULT_DISTRICT)))
            .andExpect(jsonPath("$.[*].upazila").value(hasItem(DEFAULT_UPAZILA)))
            .andExpect(jsonPath("$.[*].postalcode").value(hasItem(DEFAULT_POSTALCODE)));
    }
    
    @Test
    @Transactional
    public void getShippingAddress() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get the shippingAddress
        restShippingAddressMockMvc.perform(get("/api/shipping-addresses/{id}", shippingAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shippingAddress.getId().intValue()))
            .andExpect(jsonPath("$.district").value(DEFAULT_DISTRICT))
            .andExpect(jsonPath("$.upazila").value(DEFAULT_UPAZILA))
            .andExpect(jsonPath("$.postalcode").value(DEFAULT_POSTALCODE));
    }


    @Test
    @Transactional
    public void getShippingAddressesByIdFiltering() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        Long id = shippingAddress.getId();

        defaultShippingAddressShouldBeFound("id.equals=" + id);
        defaultShippingAddressShouldNotBeFound("id.notEquals=" + id);

        defaultShippingAddressShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultShippingAddressShouldNotBeFound("id.greaterThan=" + id);

        defaultShippingAddressShouldBeFound("id.lessThanOrEqual=" + id);
        defaultShippingAddressShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllShippingAddressesByDistrictIsEqualToSomething() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList where district equals to DEFAULT_DISTRICT
        defaultShippingAddressShouldBeFound("district.equals=" + DEFAULT_DISTRICT);

        // Get all the shippingAddressList where district equals to UPDATED_DISTRICT
        defaultShippingAddressShouldNotBeFound("district.equals=" + UPDATED_DISTRICT);
    }

    @Test
    @Transactional
    public void getAllShippingAddressesByDistrictIsNotEqualToSomething() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList where district not equals to DEFAULT_DISTRICT
        defaultShippingAddressShouldNotBeFound("district.notEquals=" + DEFAULT_DISTRICT);

        // Get all the shippingAddressList where district not equals to UPDATED_DISTRICT
        defaultShippingAddressShouldBeFound("district.notEquals=" + UPDATED_DISTRICT);
    }

    @Test
    @Transactional
    public void getAllShippingAddressesByDistrictIsInShouldWork() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList where district in DEFAULT_DISTRICT or UPDATED_DISTRICT
        defaultShippingAddressShouldBeFound("district.in=" + DEFAULT_DISTRICT + "," + UPDATED_DISTRICT);

        // Get all the shippingAddressList where district equals to UPDATED_DISTRICT
        defaultShippingAddressShouldNotBeFound("district.in=" + UPDATED_DISTRICT);
    }

    @Test
    @Transactional
    public void getAllShippingAddressesByDistrictIsNullOrNotNull() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList where district is not null
        defaultShippingAddressShouldBeFound("district.specified=true");

        // Get all the shippingAddressList where district is null
        defaultShippingAddressShouldNotBeFound("district.specified=false");
    }
                @Test
    @Transactional
    public void getAllShippingAddressesByDistrictContainsSomething() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList where district contains DEFAULT_DISTRICT
        defaultShippingAddressShouldBeFound("district.contains=" + DEFAULT_DISTRICT);

        // Get all the shippingAddressList where district contains UPDATED_DISTRICT
        defaultShippingAddressShouldNotBeFound("district.contains=" + UPDATED_DISTRICT);
    }

    @Test
    @Transactional
    public void getAllShippingAddressesByDistrictNotContainsSomething() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList where district does not contain DEFAULT_DISTRICT
        defaultShippingAddressShouldNotBeFound("district.doesNotContain=" + DEFAULT_DISTRICT);

        // Get all the shippingAddressList where district does not contain UPDATED_DISTRICT
        defaultShippingAddressShouldBeFound("district.doesNotContain=" + UPDATED_DISTRICT);
    }


    @Test
    @Transactional
    public void getAllShippingAddressesByUpazilaIsEqualToSomething() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList where upazila equals to DEFAULT_UPAZILA
        defaultShippingAddressShouldBeFound("upazila.equals=" + DEFAULT_UPAZILA);

        // Get all the shippingAddressList where upazila equals to UPDATED_UPAZILA
        defaultShippingAddressShouldNotBeFound("upazila.equals=" + UPDATED_UPAZILA);
    }

    @Test
    @Transactional
    public void getAllShippingAddressesByUpazilaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList where upazila not equals to DEFAULT_UPAZILA
        defaultShippingAddressShouldNotBeFound("upazila.notEquals=" + DEFAULT_UPAZILA);

        // Get all the shippingAddressList where upazila not equals to UPDATED_UPAZILA
        defaultShippingAddressShouldBeFound("upazila.notEquals=" + UPDATED_UPAZILA);
    }

    @Test
    @Transactional
    public void getAllShippingAddressesByUpazilaIsInShouldWork() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList where upazila in DEFAULT_UPAZILA or UPDATED_UPAZILA
        defaultShippingAddressShouldBeFound("upazila.in=" + DEFAULT_UPAZILA + "," + UPDATED_UPAZILA);

        // Get all the shippingAddressList where upazila equals to UPDATED_UPAZILA
        defaultShippingAddressShouldNotBeFound("upazila.in=" + UPDATED_UPAZILA);
    }

    @Test
    @Transactional
    public void getAllShippingAddressesByUpazilaIsNullOrNotNull() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList where upazila is not null
        defaultShippingAddressShouldBeFound("upazila.specified=true");

        // Get all the shippingAddressList where upazila is null
        defaultShippingAddressShouldNotBeFound("upazila.specified=false");
    }
                @Test
    @Transactional
    public void getAllShippingAddressesByUpazilaContainsSomething() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList where upazila contains DEFAULT_UPAZILA
        defaultShippingAddressShouldBeFound("upazila.contains=" + DEFAULT_UPAZILA);

        // Get all the shippingAddressList where upazila contains UPDATED_UPAZILA
        defaultShippingAddressShouldNotBeFound("upazila.contains=" + UPDATED_UPAZILA);
    }

    @Test
    @Transactional
    public void getAllShippingAddressesByUpazilaNotContainsSomething() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList where upazila does not contain DEFAULT_UPAZILA
        defaultShippingAddressShouldNotBeFound("upazila.doesNotContain=" + DEFAULT_UPAZILA);

        // Get all the shippingAddressList where upazila does not contain UPDATED_UPAZILA
        defaultShippingAddressShouldBeFound("upazila.doesNotContain=" + UPDATED_UPAZILA);
    }


    @Test
    @Transactional
    public void getAllShippingAddressesByPostalcodeIsEqualToSomething() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList where postalcode equals to DEFAULT_POSTALCODE
        defaultShippingAddressShouldBeFound("postalcode.equals=" + DEFAULT_POSTALCODE);

        // Get all the shippingAddressList where postalcode equals to UPDATED_POSTALCODE
        defaultShippingAddressShouldNotBeFound("postalcode.equals=" + UPDATED_POSTALCODE);
    }

    @Test
    @Transactional
    public void getAllShippingAddressesByPostalcodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList where postalcode not equals to DEFAULT_POSTALCODE
        defaultShippingAddressShouldNotBeFound("postalcode.notEquals=" + DEFAULT_POSTALCODE);

        // Get all the shippingAddressList where postalcode not equals to UPDATED_POSTALCODE
        defaultShippingAddressShouldBeFound("postalcode.notEquals=" + UPDATED_POSTALCODE);
    }

    @Test
    @Transactional
    public void getAllShippingAddressesByPostalcodeIsInShouldWork() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList where postalcode in DEFAULT_POSTALCODE or UPDATED_POSTALCODE
        defaultShippingAddressShouldBeFound("postalcode.in=" + DEFAULT_POSTALCODE + "," + UPDATED_POSTALCODE);

        // Get all the shippingAddressList where postalcode equals to UPDATED_POSTALCODE
        defaultShippingAddressShouldNotBeFound("postalcode.in=" + UPDATED_POSTALCODE);
    }

    @Test
    @Transactional
    public void getAllShippingAddressesByPostalcodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList where postalcode is not null
        defaultShippingAddressShouldBeFound("postalcode.specified=true");

        // Get all the shippingAddressList where postalcode is null
        defaultShippingAddressShouldNotBeFound("postalcode.specified=false");
    }
                @Test
    @Transactional
    public void getAllShippingAddressesByPostalcodeContainsSomething() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList where postalcode contains DEFAULT_POSTALCODE
        defaultShippingAddressShouldBeFound("postalcode.contains=" + DEFAULT_POSTALCODE);

        // Get all the shippingAddressList where postalcode contains UPDATED_POSTALCODE
        defaultShippingAddressShouldNotBeFound("postalcode.contains=" + UPDATED_POSTALCODE);
    }

    @Test
    @Transactional
    public void getAllShippingAddressesByPostalcodeNotContainsSomething() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddressList where postalcode does not contain DEFAULT_POSTALCODE
        defaultShippingAddressShouldNotBeFound("postalcode.doesNotContain=" + DEFAULT_POSTALCODE);

        // Get all the shippingAddressList where postalcode does not contain UPDATED_POSTALCODE
        defaultShippingAddressShouldBeFound("postalcode.doesNotContain=" + UPDATED_POSTALCODE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultShippingAddressShouldBeFound(String filter) throws Exception {
        restShippingAddressMockMvc.perform(get("/api/shipping-addresses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shippingAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].district").value(hasItem(DEFAULT_DISTRICT)))
            .andExpect(jsonPath("$.[*].upazila").value(hasItem(DEFAULT_UPAZILA)))
            .andExpect(jsonPath("$.[*].postalcode").value(hasItem(DEFAULT_POSTALCODE)));

        // Check, that the count call also returns 1
        restShippingAddressMockMvc.perform(get("/api/shipping-addresses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultShippingAddressShouldNotBeFound(String filter) throws Exception {
        restShippingAddressMockMvc.perform(get("/api/shipping-addresses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restShippingAddressMockMvc.perform(get("/api/shipping-addresses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingShippingAddress() throws Exception {
        // Get the shippingAddress
        restShippingAddressMockMvc.perform(get("/api/shipping-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShippingAddress() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        int databaseSizeBeforeUpdate = shippingAddressRepository.findAll().size();

        // Update the shippingAddress
        ShippingAddress updatedShippingAddress = shippingAddressRepository.findById(shippingAddress.getId()).get();
        // Disconnect from session so that the updates on updatedShippingAddress are not directly saved in db
        em.detach(updatedShippingAddress);
        updatedShippingAddress
            .district(UPDATED_DISTRICT)
            .upazila(UPDATED_UPAZILA)
            .postalcode(UPDATED_POSTALCODE);
        ShippingAddressDTO shippingAddressDTO = shippingAddressMapper.toDto(updatedShippingAddress);

        restShippingAddressMockMvc.perform(put("/api/shipping-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shippingAddressDTO)))
            .andExpect(status().isOk());

        // Validate the ShippingAddress in the database
        List<ShippingAddress> shippingAddressList = shippingAddressRepository.findAll();
        assertThat(shippingAddressList).hasSize(databaseSizeBeforeUpdate);
        ShippingAddress testShippingAddress = shippingAddressList.get(shippingAddressList.size() - 1);
        assertThat(testShippingAddress.getDistrict()).isEqualTo(UPDATED_DISTRICT);
        assertThat(testShippingAddress.getUpazila()).isEqualTo(UPDATED_UPAZILA);
        assertThat(testShippingAddress.getPostalcode()).isEqualTo(UPDATED_POSTALCODE);
    }

    @Test
    @Transactional
    public void updateNonExistingShippingAddress() throws Exception {
        int databaseSizeBeforeUpdate = shippingAddressRepository.findAll().size();

        // Create the ShippingAddress
        ShippingAddressDTO shippingAddressDTO = shippingAddressMapper.toDto(shippingAddress);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShippingAddressMockMvc.perform(put("/api/shipping-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shippingAddressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShippingAddress in the database
        List<ShippingAddress> shippingAddressList = shippingAddressRepository.findAll();
        assertThat(shippingAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteShippingAddress() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        int databaseSizeBeforeDelete = shippingAddressRepository.findAll().size();

        // Delete the shippingAddress
        restShippingAddressMockMvc.perform(delete("/api/shipping-addresses/{id}", shippingAddress.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ShippingAddress> shippingAddressList = shippingAddressRepository.findAll();
        assertThat(shippingAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
