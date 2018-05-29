package com.eyun.user.web.rest;

import com.eyun.user.UserApp;

import com.eyun.user.config.SecurityBeanOverrideConfiguration;

import com.eyun.user.domain.DeliveryAddress;
import com.eyun.user.domain.UserAnnex;
import com.eyun.user.repository.DeliveryAddressRepository;
import com.eyun.user.service.DeliveryAddressService;
import com.eyun.user.service.dto.DeliveryAddressDTO;
import com.eyun.user.service.mapper.DeliveryAddressMapper;
import com.eyun.user.web.rest.errors.ExceptionTranslator;
import com.eyun.user.service.dto.DeliveryAddressCriteria;
import com.eyun.user.service.DeliveryAddressQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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

    private static final Boolean DEFAULT_DEFAULT_ADDRESS = false;
    private static final Boolean UPDATED_DEFAULT_ADDRESS = true;

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private DeliveryAddressRepository deliveryAddressRepository;

    @Autowired
    private DeliveryAddressMapper deliveryAddressMapper;

    @Autowired
    private DeliveryAddressService deliveryAddressService;

    @Autowired
    private DeliveryAddressQueryService deliveryAddressQueryService;

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
        final DeliveryAddressResource deliveryAddressResource = new DeliveryAddressResource(deliveryAddressService, deliveryAddressQueryService);
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
            .postalCode(DEFAULT_POSTAL_CODE)
            .defaultAddress(DEFAULT_DEFAULT_ADDRESS)
            .createdTime(DEFAULT_CREATED_TIME)
            .updatedTime(DEFAULT_UPDATED_TIME);
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
        assertThat(testDeliveryAddress.isDefaultAddress()).isEqualTo(DEFAULT_DEFAULT_ADDRESS);
        assertThat(testDeliveryAddress.getCreatedTime()).isEqualTo(DEFAULT_CREATED_TIME);
        assertThat(testDeliveryAddress.getUpdatedTime()).isEqualTo(DEFAULT_UPDATED_TIME);
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
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].defaultAddress").value(hasItem(DEFAULT_DEFAULT_ADDRESS.booleanValue())))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
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
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE.toString()))
            .andExpect(jsonPath("$.defaultAddress").value(DEFAULT_DEFAULT_ADDRESS.booleanValue()))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    public void getAllDeliveryAddressesByAliasesIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryAddressRepository.saveAndFlush(deliveryAddress);

        // Get all the deliveryAddressList where aliases equals to DEFAULT_ALIASES
        defaultDeliveryAddressShouldBeFound("aliases.equals=" + DEFAULT_ALIASES);

        // Get all the deliveryAddressList where aliases equals to UPDATED_ALIASES
        defaultDeliveryAddressShouldNotBeFound("aliases.equals=" + UPDATED_ALIASES);
    }

    @Test
    @Transactional
    public void getAllDeliveryAddressesByAliasesIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryAddressRepository.saveAndFlush(deliveryAddress);

        // Get all the deliveryAddressList where aliases in DEFAULT_ALIASES or UPDATED_ALIASES
        defaultDeliveryAddressShouldBeFound("aliases.in=" + DEFAULT_ALIASES + "," + UPDATED_ALIASES);

        // Get all the deliveryAddressList where aliases equals to UPDATED_ALIASES
        defaultDeliveryAddressShouldNotBeFound("aliases.in=" + UPDATED_ALIASES);
    }

    @Test
    @Transactional
    public void getAllDeliveryAddressesByAliasesIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryAddressRepository.saveAndFlush(deliveryAddress);

        // Get all the deliveryAddressList where aliases is not null
        defaultDeliveryAddressShouldBeFound("aliases.specified=true");

        // Get all the deliveryAddressList where aliases is null
        defaultDeliveryAddressShouldNotBeFound("aliases.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeliveryAddressesByContactIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryAddressRepository.saveAndFlush(deliveryAddress);

        // Get all the deliveryAddressList where contact equals to DEFAULT_CONTACT
        defaultDeliveryAddressShouldBeFound("contact.equals=" + DEFAULT_CONTACT);

        // Get all the deliveryAddressList where contact equals to UPDATED_CONTACT
        defaultDeliveryAddressShouldNotBeFound("contact.equals=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    public void getAllDeliveryAddressesByContactIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryAddressRepository.saveAndFlush(deliveryAddress);

        // Get all the deliveryAddressList where contact in DEFAULT_CONTACT or UPDATED_CONTACT
        defaultDeliveryAddressShouldBeFound("contact.in=" + DEFAULT_CONTACT + "," + UPDATED_CONTACT);

        // Get all the deliveryAddressList where contact equals to UPDATED_CONTACT
        defaultDeliveryAddressShouldNotBeFound("contact.in=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    public void getAllDeliveryAddressesByContactIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryAddressRepository.saveAndFlush(deliveryAddress);

        // Get all the deliveryAddressList where contact is not null
        defaultDeliveryAddressShouldBeFound("contact.specified=true");

        // Get all the deliveryAddressList where contact is null
        defaultDeliveryAddressShouldNotBeFound("contact.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeliveryAddressesByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryAddressRepository.saveAndFlush(deliveryAddress);

        // Get all the deliveryAddressList where phone equals to DEFAULT_PHONE
        defaultDeliveryAddressShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the deliveryAddressList where phone equals to UPDATED_PHONE
        defaultDeliveryAddressShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllDeliveryAddressesByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryAddressRepository.saveAndFlush(deliveryAddress);

        // Get all the deliveryAddressList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultDeliveryAddressShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the deliveryAddressList where phone equals to UPDATED_PHONE
        defaultDeliveryAddressShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllDeliveryAddressesByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryAddressRepository.saveAndFlush(deliveryAddress);

        // Get all the deliveryAddressList where phone is not null
        defaultDeliveryAddressShouldBeFound("phone.specified=true");

        // Get all the deliveryAddressList where phone is null
        defaultDeliveryAddressShouldNotBeFound("phone.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeliveryAddressesByStateProvinceIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryAddressRepository.saveAndFlush(deliveryAddress);

        // Get all the deliveryAddressList where stateProvince equals to DEFAULT_STATE_PROVINCE
        defaultDeliveryAddressShouldBeFound("stateProvince.equals=" + DEFAULT_STATE_PROVINCE);

        // Get all the deliveryAddressList where stateProvince equals to UPDATED_STATE_PROVINCE
        defaultDeliveryAddressShouldNotBeFound("stateProvince.equals=" + UPDATED_STATE_PROVINCE);
    }

    @Test
    @Transactional
    public void getAllDeliveryAddressesByStateProvinceIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryAddressRepository.saveAndFlush(deliveryAddress);

        // Get all the deliveryAddressList where stateProvince in DEFAULT_STATE_PROVINCE or UPDATED_STATE_PROVINCE
        defaultDeliveryAddressShouldBeFound("stateProvince.in=" + DEFAULT_STATE_PROVINCE + "," + UPDATED_STATE_PROVINCE);

        // Get all the deliveryAddressList where stateProvince equals to UPDATED_STATE_PROVINCE
        defaultDeliveryAddressShouldNotBeFound("stateProvince.in=" + UPDATED_STATE_PROVINCE);
    }

    @Test
    @Transactional
    public void getAllDeliveryAddressesByStateProvinceIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryAddressRepository.saveAndFlush(deliveryAddress);

        // Get all the deliveryAddressList where stateProvince is not null
        defaultDeliveryAddressShouldBeFound("stateProvince.specified=true");

        // Get all the deliveryAddressList where stateProvince is null
        defaultDeliveryAddressShouldNotBeFound("stateProvince.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeliveryAddressesByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryAddressRepository.saveAndFlush(deliveryAddress);

        // Get all the deliveryAddressList where city equals to DEFAULT_CITY
        defaultDeliveryAddressShouldBeFound("city.equals=" + DEFAULT_CITY);

        // Get all the deliveryAddressList where city equals to UPDATED_CITY
        defaultDeliveryAddressShouldNotBeFound("city.equals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllDeliveryAddressesByCityIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryAddressRepository.saveAndFlush(deliveryAddress);

        // Get all the deliveryAddressList where city in DEFAULT_CITY or UPDATED_CITY
        defaultDeliveryAddressShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

        // Get all the deliveryAddressList where city equals to UPDATED_CITY
        defaultDeliveryAddressShouldNotBeFound("city.in=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllDeliveryAddressesByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryAddressRepository.saveAndFlush(deliveryAddress);

        // Get all the deliveryAddressList where city is not null
        defaultDeliveryAddressShouldBeFound("city.specified=true");

        // Get all the deliveryAddressList where city is null
        defaultDeliveryAddressShouldNotBeFound("city.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeliveryAddressesByStreetAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryAddressRepository.saveAndFlush(deliveryAddress);

        // Get all the deliveryAddressList where streetAddress equals to DEFAULT_STREET_ADDRESS
        defaultDeliveryAddressShouldBeFound("streetAddress.equals=" + DEFAULT_STREET_ADDRESS);

        // Get all the deliveryAddressList where streetAddress equals to UPDATED_STREET_ADDRESS
        defaultDeliveryAddressShouldNotBeFound("streetAddress.equals=" + UPDATED_STREET_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllDeliveryAddressesByStreetAddressIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryAddressRepository.saveAndFlush(deliveryAddress);

        // Get all the deliveryAddressList where streetAddress in DEFAULT_STREET_ADDRESS or UPDATED_STREET_ADDRESS
        defaultDeliveryAddressShouldBeFound("streetAddress.in=" + DEFAULT_STREET_ADDRESS + "," + UPDATED_STREET_ADDRESS);

        // Get all the deliveryAddressList where streetAddress equals to UPDATED_STREET_ADDRESS
        defaultDeliveryAddressShouldNotBeFound("streetAddress.in=" + UPDATED_STREET_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllDeliveryAddressesByStreetAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryAddressRepository.saveAndFlush(deliveryAddress);

        // Get all the deliveryAddressList where streetAddress is not null
        defaultDeliveryAddressShouldBeFound("streetAddress.specified=true");

        // Get all the deliveryAddressList where streetAddress is null
        defaultDeliveryAddressShouldNotBeFound("streetAddress.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeliveryAddressesByPostalCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryAddressRepository.saveAndFlush(deliveryAddress);

        // Get all the deliveryAddressList where postalCode equals to DEFAULT_POSTAL_CODE
        defaultDeliveryAddressShouldBeFound("postalCode.equals=" + DEFAULT_POSTAL_CODE);

        // Get all the deliveryAddressList where postalCode equals to UPDATED_POSTAL_CODE
        defaultDeliveryAddressShouldNotBeFound("postalCode.equals=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void getAllDeliveryAddressesByPostalCodeIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryAddressRepository.saveAndFlush(deliveryAddress);

        // Get all the deliveryAddressList where postalCode in DEFAULT_POSTAL_CODE or UPDATED_POSTAL_CODE
        defaultDeliveryAddressShouldBeFound("postalCode.in=" + DEFAULT_POSTAL_CODE + "," + UPDATED_POSTAL_CODE);

        // Get all the deliveryAddressList where postalCode equals to UPDATED_POSTAL_CODE
        defaultDeliveryAddressShouldNotBeFound("postalCode.in=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void getAllDeliveryAddressesByPostalCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryAddressRepository.saveAndFlush(deliveryAddress);

        // Get all the deliveryAddressList where postalCode is not null
        defaultDeliveryAddressShouldBeFound("postalCode.specified=true");

        // Get all the deliveryAddressList where postalCode is null
        defaultDeliveryAddressShouldNotBeFound("postalCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeliveryAddressesByDefaultAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryAddressRepository.saveAndFlush(deliveryAddress);

        // Get all the deliveryAddressList where defaultAddress equals to DEFAULT_DEFAULT_ADDRESS
        defaultDeliveryAddressShouldBeFound("defaultAddress.equals=" + DEFAULT_DEFAULT_ADDRESS);

        // Get all the deliveryAddressList where defaultAddress equals to UPDATED_DEFAULT_ADDRESS
        defaultDeliveryAddressShouldNotBeFound("defaultAddress.equals=" + UPDATED_DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllDeliveryAddressesByDefaultAddressIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryAddressRepository.saveAndFlush(deliveryAddress);

        // Get all the deliveryAddressList where defaultAddress in DEFAULT_DEFAULT_ADDRESS or UPDATED_DEFAULT_ADDRESS
        defaultDeliveryAddressShouldBeFound("defaultAddress.in=" + DEFAULT_DEFAULT_ADDRESS + "," + UPDATED_DEFAULT_ADDRESS);

        // Get all the deliveryAddressList where defaultAddress equals to UPDATED_DEFAULT_ADDRESS
        defaultDeliveryAddressShouldNotBeFound("defaultAddress.in=" + UPDATED_DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllDeliveryAddressesByDefaultAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryAddressRepository.saveAndFlush(deliveryAddress);

        // Get all the deliveryAddressList where defaultAddress is not null
        defaultDeliveryAddressShouldBeFound("defaultAddress.specified=true");

        // Get all the deliveryAddressList where defaultAddress is null
        defaultDeliveryAddressShouldNotBeFound("defaultAddress.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeliveryAddressesByCreatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryAddressRepository.saveAndFlush(deliveryAddress);

        // Get all the deliveryAddressList where createdTime equals to DEFAULT_CREATED_TIME
        defaultDeliveryAddressShouldBeFound("createdTime.equals=" + DEFAULT_CREATED_TIME);

        // Get all the deliveryAddressList where createdTime equals to UPDATED_CREATED_TIME
        defaultDeliveryAddressShouldNotBeFound("createdTime.equals=" + UPDATED_CREATED_TIME);
    }

    @Test
    @Transactional
    public void getAllDeliveryAddressesByCreatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryAddressRepository.saveAndFlush(deliveryAddress);

        // Get all the deliveryAddressList where createdTime in DEFAULT_CREATED_TIME or UPDATED_CREATED_TIME
        defaultDeliveryAddressShouldBeFound("createdTime.in=" + DEFAULT_CREATED_TIME + "," + UPDATED_CREATED_TIME);

        // Get all the deliveryAddressList where createdTime equals to UPDATED_CREATED_TIME
        defaultDeliveryAddressShouldNotBeFound("createdTime.in=" + UPDATED_CREATED_TIME);
    }

    @Test
    @Transactional
    public void getAllDeliveryAddressesByCreatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryAddressRepository.saveAndFlush(deliveryAddress);

        // Get all the deliveryAddressList where createdTime is not null
        defaultDeliveryAddressShouldBeFound("createdTime.specified=true");

        // Get all the deliveryAddressList where createdTime is null
        defaultDeliveryAddressShouldNotBeFound("createdTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeliveryAddressesByUpdatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryAddressRepository.saveAndFlush(deliveryAddress);

        // Get all the deliveryAddressList where updatedTime equals to DEFAULT_UPDATED_TIME
        defaultDeliveryAddressShouldBeFound("updatedTime.equals=" + DEFAULT_UPDATED_TIME);

        // Get all the deliveryAddressList where updatedTime equals to UPDATED_UPDATED_TIME
        defaultDeliveryAddressShouldNotBeFound("updatedTime.equals=" + UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void getAllDeliveryAddressesByUpdatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryAddressRepository.saveAndFlush(deliveryAddress);

        // Get all the deliveryAddressList where updatedTime in DEFAULT_UPDATED_TIME or UPDATED_UPDATED_TIME
        defaultDeliveryAddressShouldBeFound("updatedTime.in=" + DEFAULT_UPDATED_TIME + "," + UPDATED_UPDATED_TIME);

        // Get all the deliveryAddressList where updatedTime equals to UPDATED_UPDATED_TIME
        defaultDeliveryAddressShouldNotBeFound("updatedTime.in=" + UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void getAllDeliveryAddressesByUpdatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryAddressRepository.saveAndFlush(deliveryAddress);

        // Get all the deliveryAddressList where updatedTime is not null
        defaultDeliveryAddressShouldBeFound("updatedTime.specified=true");

        // Get all the deliveryAddressList where updatedTime is null
        defaultDeliveryAddressShouldNotBeFound("updatedTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeliveryAddressesByUserAnnexIsEqualToSomething() throws Exception {
        // Initialize the database
        UserAnnex userAnnex = UserAnnexResourceIntTest.createEntity(em);
        em.persist(userAnnex);
        em.flush();
        deliveryAddress.setUserAnnex(userAnnex);
        deliveryAddressRepository.saveAndFlush(deliveryAddress);
        Long userAnnexId = userAnnex.getId();

        // Get all the deliveryAddressList where userAnnex equals to userAnnexId
        defaultDeliveryAddressShouldBeFound("userAnnexId.equals=" + userAnnexId);

        // Get all the deliveryAddressList where userAnnex equals to userAnnexId + 1
        defaultDeliveryAddressShouldNotBeFound("userAnnexId.equals=" + (userAnnexId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultDeliveryAddressShouldBeFound(String filter) throws Exception {
        restDeliveryAddressMockMvc.perform(get("/api/delivery-addresses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deliveryAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].aliases").value(hasItem(DEFAULT_ALIASES.toString())))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].stateProvince").value(hasItem(DEFAULT_STATE_PROVINCE.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].streetAddress").value(hasItem(DEFAULT_STREET_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].defaultAddress").value(hasItem(DEFAULT_DEFAULT_ADDRESS.booleanValue())))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultDeliveryAddressShouldNotBeFound(String filter) throws Exception {
        restDeliveryAddressMockMvc.perform(get("/api/delivery-addresses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
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
            .postalCode(UPDATED_POSTAL_CODE)
            .defaultAddress(UPDATED_DEFAULT_ADDRESS)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedTime(UPDATED_UPDATED_TIME);
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
        assertThat(testDeliveryAddress.isDefaultAddress()).isEqualTo(UPDATED_DEFAULT_ADDRESS);
        assertThat(testDeliveryAddress.getCreatedTime()).isEqualTo(UPDATED_CREATED_TIME);
        assertThat(testDeliveryAddress.getUpdatedTime()).isEqualTo(UPDATED_UPDATED_TIME);
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
