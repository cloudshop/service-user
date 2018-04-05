package com.eyun.user.web.rest;

import com.eyun.user.UserApp;

import com.eyun.user.config.SecurityBeanOverrideConfiguration;

import com.eyun.user.domain.DeliveryAddress;
import com.eyun.user.repository.DeliveryAddressRepository;
import com.eyun.user.service.DeliveryAddressService;
import com.eyun.user.service.dto.DeliveryAddressDTO;
import com.eyun.user.service.mapper.DeliveryAddressMapper;
import com.eyun.user.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.eyun.user.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DeliveryAddressResource REST controller.
 *
 * @see DeliveryAddressResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserApp.class, SecurityBeanOverrideConfiguration.class})
public class DeliveryAddressResourceIntTest {

    private static final String DEFAULT_ALIASES = "AAAAAAAAAA";
    private static final String UPDATED_ALIASES = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_STATE_PROVINCE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STREET_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_STREET_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    @Autowired
    private DeliveryAddressRepository deliveryAddressRepository;

    @Autowired
    private DeliveryAddressMapper deliveryAddressMapper;

    @Autowired
    private DeliveryAddressService deliveryAddressService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDeliveryAddressMockMvc;

    private DeliveryAddress deliveryAddress;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DeliveryAddressResource deliveryAddressResource = new DeliveryAddressResource(deliveryAddressService);
        this.restDeliveryAddressMockMvc = MockMvcBuilders.standaloneSetup(deliveryAddressResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeliveryAddress createEntity(EntityManager em) {
        DeliveryAddress deliveryAddress = new DeliveryAddress()
            .aliases(DEFAULT_ALIASES)
            .contact(DEFAULT_CONTACT)
            .phone(DEFAULT_PHONE)
            .stateProvince(DEFAULT_STATE_PROVINCE)
            .city(DEFAULT_CITY)
            .streetAddress(DEFAULT_STREET_ADDRESS)
            .postalCode(DEFAULT_POSTAL_CODE);
        return deliveryAddress;
    }

    @Before
    public void initTest() {
        deliveryAddress = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeliveryAddress() throws Exception {
        int databaseSizeBeforeCreate = deliveryAddressRepository.findAll().size();

        // Create the DeliveryAddress
        DeliveryAddressDTO deliveryAddressDTO = deliveryAddressMapper.toDto(deliveryAddress);
        restDeliveryAddressMockMvc.perform(post("/api/delivery-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryAddressDTO)))
            .andExpect(status().isCreated());

        // Validate the DeliveryAddress in the database
        List<DeliveryAddress> deliveryAddressList = deliveryAddressRepository.findAll();
        assertThat(deliveryAddressList).hasSize(databaseSizeBeforeCreate + 1);
        DeliveryAddress testDeliveryAddress = deliveryAddressList.get(deliveryAddressList.size() - 1);
        assertThat(testDeliveryAddress.getAliases()).isEqualTo(DEFAULT_ALIASES);
        assertThat(testDeliveryAddress.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testDeliveryAddress.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testDeliveryAddress.getStateProvince()).isEqualTo(DEFAULT_STATE_PROVINCE);
        assertThat(testDeliveryAddress.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testDeliveryAddress.getStreetAddress()).isEqualTo(DEFAULT_STREET_ADDRESS);
        assertThat(testDeliveryAddress.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void createDeliveryAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deliveryAddressRepository.findAll().size();

        // Create the DeliveryAddress with an existing ID
        deliveryAddress.setId(1L);
        DeliveryAddressDTO deliveryAddressDTO = deliveryAddressMapper.toDto(deliveryAddress);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeliveryAddressMockMvc.perform(post("/api/delivery-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryAddressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DeliveryAddress in the database
        List<DeliveryAddress> deliveryAddressList = deliveryAddressRepository.findAll();
        assertThat(deliveryAddressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDeliveryAddresses() throws Exception {
        // Initialize the database
        deliveryAddressRepository.saveAndFlush(deliveryAddress);

        // Get all the deliveryAddressList
        restDeliveryAddressMockMvc.perform(get("/api/delivery-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deliveryAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].aliases").value(hasItem(DEFAULT_ALIASES.toString())))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].stateProvince").value(hasItem(DEFAULT_STATE_PROVINCE.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].streetAddress").value(hasItem(DEFAULT_STREET_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())));
    }

    @Test
    @Transactional
    public void getDeliveryAddress() throws Exception {
        // Initialize the database
        deliveryAddressRepository.saveAndFlush(deliveryAddress);

        // Get the deliveryAddress
        restDeliveryAddressMockMvc.perform(get("/api/delivery-addresses/{id}", deliveryAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(deliveryAddress.getId().intValue()))
            .andExpect(jsonPath("$.aliases").value(DEFAULT_ALIASES.toString()))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.stateProvince").value(DEFAULT_STATE_PROVINCE.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.streetAddress").value(DEFAULT_STREET_ADDRESS.toString()))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDeliveryAddress() throws Exception {
        // Get the deliveryAddress
        restDeliveryAddressMockMvc.perform(get("/api/delivery-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeliveryAddress() throws Exception {
        // Initialize the database
        deliveryAddressRepository.saveAndFlush(deliveryAddress);
        int databaseSizeBeforeUpdate = deliveryAddressRepository.findAll().size();

        // Update the deliveryAddress
        DeliveryAddress updatedDeliveryAddress = deliveryAddressRepository.findOne(deliveryAddress.getId());
        // Disconnect from session so that the updates on updatedDeliveryAddress are not directly saved in db
        em.detach(updatedDeliveryAddress);
        updatedDeliveryAddress
            .aliases(UPDATED_ALIASES)
            .contact(UPDATED_CONTACT)
            .phone(UPDATED_PHONE)
            .stateProvince(UPDATED_STATE_PROVINCE)
            .city(UPDATED_CITY)
            .streetAddress(UPDATED_STREET_ADDRESS)
            .postalCode(UPDATED_POSTAL_CODE);
        DeliveryAddressDTO deliveryAddressDTO = deliveryAddressMapper.toDto(updatedDeliveryAddress);

        restDeliveryAddressMockMvc.perform(put("/api/delivery-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryAddressDTO)))
            .andExpect(status().isOk());

        // Validate the DeliveryAddress in the database
        List<DeliveryAddress> deliveryAddressList = deliveryAddressRepository.findAll();
        assertThat(deliveryAddressList).hasSize(databaseSizeBeforeUpdate);
        DeliveryAddress testDeliveryAddress = deliveryAddressList.get(deliveryAddressList.size() - 1);
        assertThat(testDeliveryAddress.getAliases()).isEqualTo(UPDATED_ALIASES);
        assertThat(testDeliveryAddress.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testDeliveryAddress.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testDeliveryAddress.getStateProvince()).isEqualTo(UPDATED_STATE_PROVINCE);
        assertThat(testDeliveryAddress.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testDeliveryAddress.getStreetAddress()).isEqualTo(UPDATED_STREET_ADDRESS);
        assertThat(testDeliveryAddress.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingDeliveryAddress() throws Exception {
        int databaseSizeBeforeUpdate = deliveryAddressRepository.findAll().size();

        // Create the DeliveryAddress
        DeliveryAddressDTO deliveryAddressDTO = deliveryAddressMapper.toDto(deliveryAddress);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDeliveryAddressMockMvc.perform(put("/api/delivery-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryAddressDTO)))
            .andExpect(status().isCreated());

        // Validate the DeliveryAddress in the database
        List<DeliveryAddress> deliveryAddressList = deliveryAddressRepository.findAll();
        assertThat(deliveryAddressList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDeliveryAddress() throws Exception {
        // Initialize the database
        deliveryAddressRepository.saveAndFlush(deliveryAddress);
        int databaseSizeBeforeDelete = deliveryAddressRepository.findAll().size();

        // Get the deliveryAddress
        restDeliveryAddressMockMvc.perform(delete("/api/delivery-addresses/{id}", deliveryAddress.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DeliveryAddress> deliveryAddressList = deliveryAddressRepository.findAll();
        assertThat(deliveryAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeliveryAddress.class);
        DeliveryAddress deliveryAddress1 = new DeliveryAddress();
        deliveryAddress1.setId(1L);
        DeliveryAddress deliveryAddress2 = new DeliveryAddress();
        deliveryAddress2.setId(deliveryAddress1.getId());
        assertThat(deliveryAddress1).isEqualTo(deliveryAddress2);
        deliveryAddress2.setId(2L);
        assertThat(deliveryAddress1).isNotEqualTo(deliveryAddress2);
        deliveryAddress1.setId(null);
        assertThat(deliveryAddress1).isNotEqualTo(deliveryAddress2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeliveryAddressDTO.class);
        DeliveryAddressDTO deliveryAddressDTO1 = new DeliveryAddressDTO();
        deliveryAddressDTO1.setId(1L);
        DeliveryAddressDTO deliveryAddressDTO2 = new DeliveryAddressDTO();
        assertThat(deliveryAddressDTO1).isNotEqualTo(deliveryAddressDTO2);
        deliveryAddressDTO2.setId(deliveryAddressDTO1.getId());
        assertThat(deliveryAddressDTO1).isEqualTo(deliveryAddressDTO2);
        deliveryAddressDTO2.setId(2L);
        assertThat(deliveryAddressDTO1).isNotEqualTo(deliveryAddressDTO2);
        deliveryAddressDTO1.setId(null);
        assertThat(deliveryAddressDTO1).isNotEqualTo(deliveryAddressDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(deliveryAddressMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(deliveryAddressMapper.fromId(null)).isNull();
    }
}
