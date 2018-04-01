package com.eyun.user.web.rest;

import com.eyun.user.UserApp;

import com.eyun.user.config.SecurityBeanOverrideConfiguration;

import com.eyun.user.domain.Delivery;
import com.eyun.user.repository.DeliveryRepository;
import com.eyun.user.service.DeliveryService;
import com.eyun.user.service.dto.DeliveryDTO;
import com.eyun.user.service.mapper.DeliveryMapper;
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
 * Test class for the DeliveryResource REST controller.
 *
 * @see DeliveryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserApp.class, SecurityBeanOverrideConfiguration.class})
public class DeliveryResourceIntTest {

    private static final String DEFAULT_ALIASES = "AAAAAAAAAA";
    private static final String UPDATED_ALIASES = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCE = "BBBBBBBBBB";

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private DeliveryMapper deliveryMapper;

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDeliveryMockMvc;

    private Delivery delivery;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DeliveryResource deliveryResource = new DeliveryResource(deliveryService);
        this.restDeliveryMockMvc = MockMvcBuilders.standaloneSetup(deliveryResource)
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
    public static Delivery createEntity(EntityManager em) {
        Delivery delivery = new Delivery()
            .aliases(DEFAULT_ALIASES)
            .contact(DEFAULT_CONTACT)
            .mobile(DEFAULT_MOBILE)
            .address(DEFAULT_ADDRESS)
            .postal(DEFAULT_POSTAL)
            .city(DEFAULT_CITY)
            .province(DEFAULT_PROVINCE);
        return delivery;
    }

    @Before
    public void initTest() {
        delivery = createEntity(em);
    }

    @Test
    @Transactional
    public void createDelivery() throws Exception {
        int databaseSizeBeforeCreate = deliveryRepository.findAll().size();

        // Create the Delivery
        DeliveryDTO deliveryDTO = deliveryMapper.toDto(delivery);
        restDeliveryMockMvc.perform(post("/api/deliveries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryDTO)))
            .andExpect(status().isCreated());

        // Validate the Delivery in the database
        List<Delivery> deliveryList = deliveryRepository.findAll();
        assertThat(deliveryList).hasSize(databaseSizeBeforeCreate + 1);
        Delivery testDelivery = deliveryList.get(deliveryList.size() - 1);
        assertThat(testDelivery.getAliases()).isEqualTo(DEFAULT_ALIASES);
        assertThat(testDelivery.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testDelivery.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testDelivery.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testDelivery.getPostal()).isEqualTo(DEFAULT_POSTAL);
        assertThat(testDelivery.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testDelivery.getProvince()).isEqualTo(DEFAULT_PROVINCE);
    }

    @Test
    @Transactional
    public void createDeliveryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deliveryRepository.findAll().size();

        // Create the Delivery with an existing ID
        delivery.setId(1L);
        DeliveryDTO deliveryDTO = deliveryMapper.toDto(delivery);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeliveryMockMvc.perform(post("/api/deliveries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Delivery in the database
        List<Delivery> deliveryList = deliveryRepository.findAll();
        assertThat(deliveryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDeliveries() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        // Get all the deliveryList
        restDeliveryMockMvc.perform(get("/api/deliveries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(delivery.getId().intValue())))
            .andExpect(jsonPath("$.[*].aliases").value(hasItem(DEFAULT_ALIASES.toString())))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT.toString())))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].postal").value(hasItem(DEFAULT_POSTAL.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].province").value(hasItem(DEFAULT_PROVINCE.toString())));
    }

    @Test
    @Transactional
    public void getDelivery() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        // Get the delivery
        restDeliveryMockMvc.perform(get("/api/deliveries/{id}", delivery.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(delivery.getId().intValue()))
            .andExpect(jsonPath("$.aliases").value(DEFAULT_ALIASES.toString()))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT.toString()))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.postal").value(DEFAULT_POSTAL.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.province").value(DEFAULT_PROVINCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDelivery() throws Exception {
        // Get the delivery
        restDeliveryMockMvc.perform(get("/api/deliveries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDelivery() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);
        int databaseSizeBeforeUpdate = deliveryRepository.findAll().size();

        // Update the delivery
        Delivery updatedDelivery = deliveryRepository.findOne(delivery.getId());
        // Disconnect from session so that the updates on updatedDelivery are not directly saved in db
        em.detach(updatedDelivery);
        updatedDelivery
            .aliases(UPDATED_ALIASES)
            .contact(UPDATED_CONTACT)
            .mobile(UPDATED_MOBILE)
            .address(UPDATED_ADDRESS)
            .postal(UPDATED_POSTAL)
            .city(UPDATED_CITY)
            .province(UPDATED_PROVINCE);
        DeliveryDTO deliveryDTO = deliveryMapper.toDto(updatedDelivery);

        restDeliveryMockMvc.perform(put("/api/deliveries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryDTO)))
            .andExpect(status().isOk());

        // Validate the Delivery in the database
        List<Delivery> deliveryList = deliveryRepository.findAll();
        assertThat(deliveryList).hasSize(databaseSizeBeforeUpdate);
        Delivery testDelivery = deliveryList.get(deliveryList.size() - 1);
        assertThat(testDelivery.getAliases()).isEqualTo(UPDATED_ALIASES);
        assertThat(testDelivery.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testDelivery.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testDelivery.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testDelivery.getPostal()).isEqualTo(UPDATED_POSTAL);
        assertThat(testDelivery.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testDelivery.getProvince()).isEqualTo(UPDATED_PROVINCE);
    }

    @Test
    @Transactional
    public void updateNonExistingDelivery() throws Exception {
        int databaseSizeBeforeUpdate = deliveryRepository.findAll().size();

        // Create the Delivery
        DeliveryDTO deliveryDTO = deliveryMapper.toDto(delivery);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDeliveryMockMvc.perform(put("/api/deliveries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryDTO)))
            .andExpect(status().isCreated());

        // Validate the Delivery in the database
        List<Delivery> deliveryList = deliveryRepository.findAll();
        assertThat(deliveryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDelivery() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);
        int databaseSizeBeforeDelete = deliveryRepository.findAll().size();

        // Get the delivery
        restDeliveryMockMvc.perform(delete("/api/deliveries/{id}", delivery.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Delivery> deliveryList = deliveryRepository.findAll();
        assertThat(deliveryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Delivery.class);
        Delivery delivery1 = new Delivery();
        delivery1.setId(1L);
        Delivery delivery2 = new Delivery();
        delivery2.setId(delivery1.getId());
        assertThat(delivery1).isEqualTo(delivery2);
        delivery2.setId(2L);
        assertThat(delivery1).isNotEqualTo(delivery2);
        delivery1.setId(null);
        assertThat(delivery1).isNotEqualTo(delivery2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeliveryDTO.class);
        DeliveryDTO deliveryDTO1 = new DeliveryDTO();
        deliveryDTO1.setId(1L);
        DeliveryDTO deliveryDTO2 = new DeliveryDTO();
        assertThat(deliveryDTO1).isNotEqualTo(deliveryDTO2);
        deliveryDTO2.setId(deliveryDTO1.getId());
        assertThat(deliveryDTO1).isEqualTo(deliveryDTO2);
        deliveryDTO2.setId(2L);
        assertThat(deliveryDTO1).isNotEqualTo(deliveryDTO2);
        deliveryDTO1.setId(null);
        assertThat(deliveryDTO1).isNotEqualTo(deliveryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(deliveryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(deliveryMapper.fromId(null)).isNull();
    }
}
