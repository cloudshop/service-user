package com.eyun.user.web.rest;

import com.eyun.user.UserApp;

import com.eyun.user.config.SecurityBeanOverrideConfiguration;

import com.eyun.user.domain.Mercury;
import com.eyun.user.domain.MercuryStatusHistory;
import com.eyun.user.domain.OwnerRelation;
import com.eyun.user.repository.MercuryRepository;
import com.eyun.user.service.MercuryService;
import com.eyun.user.service.dto.MercuryDTO;
import com.eyun.user.service.mapper.MercuryMapper;
import com.eyun.user.web.rest.errors.ExceptionTranslator;
import com.eyun.user.service.dto.MercuryCriteria;
import com.eyun.user.service.MercuryQueryService;

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
 * Test class for the MercuryResource REST controller.
 *
 * @see MercuryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserApp.class, SecurityBeanOverrideConfiguration.class})
public class MercuryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IMG_LICENSE = "AAAAAAAAAA";
    private static final String UPDATED_IMG_LICENSE = "BBBBBBBBBB";

    private static final String DEFAULT_IMG_IDCARD_FRONT = "AAAAAAAAAA";
    private static final String UPDATED_IMG_IDCARD_FRONT = "BBBBBBBBBB";

    private static final String DEFAULT_IMG_IDCARD_BACK = "AAAAAAAAAA";
    private static final String UPDATED_IMG_IDCARD_BACK = "BBBBBBBBBB";

    private static final String DEFAULT_IMG_IDCARD_HOLD = "AAAAAAAAAA";
    private static final String UPDATED_IMG_IDCARD_HOLD = "BBBBBBBBBB";

    private static final Double DEFAULT_LANGITUDE = 1D;
    private static final Double UPDATED_LANGITUDE = 2D;

    private static final Double DEFAULT_LANTITUDE = 1D;
    private static final Double UPDATED_LANTITUDE = 2D;

    private static final String DEFAULT_PROVICE = "AAAAAAAAAA";
    private static final String UPDATED_PROVICE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_IMG_FACADE = "AAAAAAAAAA";
    private static final String UPDATED_IMG_FACADE = "BBBBBBBBBB";

    private static final String DEFAULT_IMG_INTRODUCES = "AAAAAAAAAA";
    private static final String UPDATED_IMG_INTRODUCES = "BBBBBBBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private MercuryRepository mercuryRepository;

    @Autowired
    private MercuryMapper mercuryMapper;

    @Autowired
    private MercuryService mercuryService;

    @Autowired
    private MercuryQueryService mercuryQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMercuryMockMvc;

    private Mercury mercury;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MercuryResource mercuryResource = new MercuryResource(mercuryService, mercuryQueryService);
        this.restMercuryMockMvc = MockMvcBuilders.standaloneSetup(mercuryResource)
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
    public static Mercury createEntity(EntityManager em) {
        Mercury mercury = new Mercury()
            .name(DEFAULT_NAME)
            .imgLicense(DEFAULT_IMG_LICENSE)
            .imgIdcardFront(DEFAULT_IMG_IDCARD_FRONT)
            .imgIdcardBack(DEFAULT_IMG_IDCARD_BACK)
            .imgIdcardHold(DEFAULT_IMG_IDCARD_HOLD)
            .langitude(DEFAULT_LANGITUDE)
            .lantitude(DEFAULT_LANTITUDE)
            .provice(DEFAULT_PROVICE)
            .city(DEFAULT_CITY)
            .street(DEFAULT_STREET)
            .imgFacade(DEFAULT_IMG_FACADE)
            .imgIntroduces(DEFAULT_IMG_INTRODUCES)
            .status(DEFAULT_STATUS)
            .createdTime(DEFAULT_CREATED_TIME)
            .updatedTime(DEFAULT_UPDATED_TIME);
        return mercury;
    }

    @Before
    public void initTest() {
        mercury = createEntity(em);
    }

    @Test
    @Transactional
    public void createMercury() throws Exception {
        int databaseSizeBeforeCreate = mercuryRepository.findAll().size();

        // Create the Mercury
        MercuryDTO mercuryDTO = mercuryMapper.toDto(mercury);
        restMercuryMockMvc.perform(post("/api/mercuries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mercuryDTO)))
            .andExpect(status().isCreated());

        // Validate the Mercury in the database
        List<Mercury> mercuryList = mercuryRepository.findAll();
        assertThat(mercuryList).hasSize(databaseSizeBeforeCreate + 1);
        Mercury testMercury = mercuryList.get(mercuryList.size() - 1);
        assertThat(testMercury.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMercury.getImgLicense()).isEqualTo(DEFAULT_IMG_LICENSE);
        assertThat(testMercury.getImgIdcardFront()).isEqualTo(DEFAULT_IMG_IDCARD_FRONT);
        assertThat(testMercury.getImgIdcardBack()).isEqualTo(DEFAULT_IMG_IDCARD_BACK);
        assertThat(testMercury.getImgIdcardHold()).isEqualTo(DEFAULT_IMG_IDCARD_HOLD);
        assertThat(testMercury.getLangitude()).isEqualTo(DEFAULT_LANGITUDE);
        assertThat(testMercury.getLantitude()).isEqualTo(DEFAULT_LANTITUDE);
        assertThat(testMercury.getProvice()).isEqualTo(DEFAULT_PROVICE);
        assertThat(testMercury.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testMercury.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testMercury.getImgFacade()).isEqualTo(DEFAULT_IMG_FACADE);
        assertThat(testMercury.getImgIntroduces()).isEqualTo(DEFAULT_IMG_INTRODUCES);
        assertThat(testMercury.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMercury.getCreatedTime()).isEqualTo(DEFAULT_CREATED_TIME);
        assertThat(testMercury.getUpdatedTime()).isEqualTo(DEFAULT_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void createMercuryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mercuryRepository.findAll().size();

        // Create the Mercury with an existing ID
        mercury.setId(1L);
        MercuryDTO mercuryDTO = mercuryMapper.toDto(mercury);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMercuryMockMvc.perform(post("/api/mercuries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mercuryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Mercury in the database
        List<Mercury> mercuryList = mercuryRepository.findAll();
        assertThat(mercuryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMercuries() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList
        restMercuryMockMvc.perform(get("/api/mercuries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mercury.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].imgLicense").value(hasItem(DEFAULT_IMG_LICENSE.toString())))
            .andExpect(jsonPath("$.[*].imgIdcardFront").value(hasItem(DEFAULT_IMG_IDCARD_FRONT.toString())))
            .andExpect(jsonPath("$.[*].imgIdcardBack").value(hasItem(DEFAULT_IMG_IDCARD_BACK.toString())))
            .andExpect(jsonPath("$.[*].imgIdcardHold").value(hasItem(DEFAULT_IMG_IDCARD_HOLD.toString())))
            .andExpect(jsonPath("$.[*].langitude").value(hasItem(DEFAULT_LANGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].lantitude").value(hasItem(DEFAULT_LANTITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].provice").value(hasItem(DEFAULT_PROVICE.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
            .andExpect(jsonPath("$.[*].imgFacade").value(hasItem(DEFAULT_IMG_FACADE.toString())))
            .andExpect(jsonPath("$.[*].imgIntroduces").value(hasItem(DEFAULT_IMG_INTRODUCES.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    public void getMercury() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get the mercury
        restMercuryMockMvc.perform(get("/api/mercuries/{id}", mercury.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mercury.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.imgLicense").value(DEFAULT_IMG_LICENSE.toString()))
            .andExpect(jsonPath("$.imgIdcardFront").value(DEFAULT_IMG_IDCARD_FRONT.toString()))
            .andExpect(jsonPath("$.imgIdcardBack").value(DEFAULT_IMG_IDCARD_BACK.toString()))
            .andExpect(jsonPath("$.imgIdcardHold").value(DEFAULT_IMG_IDCARD_HOLD.toString()))
            .andExpect(jsonPath("$.langitude").value(DEFAULT_LANGITUDE.doubleValue()))
            .andExpect(jsonPath("$.lantitude").value(DEFAULT_LANTITUDE.doubleValue()))
            .andExpect(jsonPath("$.provice").value(DEFAULT_PROVICE.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()))
            .andExpect(jsonPath("$.imgFacade").value(DEFAULT_IMG_FACADE.toString()))
            .andExpect(jsonPath("$.imgIntroduces").value(DEFAULT_IMG_INTRODUCES.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    public void getAllMercuriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where name equals to DEFAULT_NAME
        defaultMercuryShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the mercuryList where name equals to UPDATED_NAME
        defaultMercuryShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMercuriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where name in DEFAULT_NAME or UPDATED_NAME
        defaultMercuryShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the mercuryList where name equals to UPDATED_NAME
        defaultMercuryShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMercuriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where name is not null
        defaultMercuryShouldBeFound("name.specified=true");

        // Get all the mercuryList where name is null
        defaultMercuryShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllMercuriesByImgLicenseIsEqualToSomething() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where imgLicense equals to DEFAULT_IMG_LICENSE
        defaultMercuryShouldBeFound("imgLicense.equals=" + DEFAULT_IMG_LICENSE);

        // Get all the mercuryList where imgLicense equals to UPDATED_IMG_LICENSE
        defaultMercuryShouldNotBeFound("imgLicense.equals=" + UPDATED_IMG_LICENSE);
    }

    @Test
    @Transactional
    public void getAllMercuriesByImgLicenseIsInShouldWork() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where imgLicense in DEFAULT_IMG_LICENSE or UPDATED_IMG_LICENSE
        defaultMercuryShouldBeFound("imgLicense.in=" + DEFAULT_IMG_LICENSE + "," + UPDATED_IMG_LICENSE);

        // Get all the mercuryList where imgLicense equals to UPDATED_IMG_LICENSE
        defaultMercuryShouldNotBeFound("imgLicense.in=" + UPDATED_IMG_LICENSE);
    }

    @Test
    @Transactional
    public void getAllMercuriesByImgLicenseIsNullOrNotNull() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where imgLicense is not null
        defaultMercuryShouldBeFound("imgLicense.specified=true");

        // Get all the mercuryList where imgLicense is null
        defaultMercuryShouldNotBeFound("imgLicense.specified=false");
    }

    @Test
    @Transactional
    public void getAllMercuriesByImgIdcardFrontIsEqualToSomething() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where imgIdcardFront equals to DEFAULT_IMG_IDCARD_FRONT
        defaultMercuryShouldBeFound("imgIdcardFront.equals=" + DEFAULT_IMG_IDCARD_FRONT);

        // Get all the mercuryList where imgIdcardFront equals to UPDATED_IMG_IDCARD_FRONT
        defaultMercuryShouldNotBeFound("imgIdcardFront.equals=" + UPDATED_IMG_IDCARD_FRONT);
    }

    @Test
    @Transactional
    public void getAllMercuriesByImgIdcardFrontIsInShouldWork() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where imgIdcardFront in DEFAULT_IMG_IDCARD_FRONT or UPDATED_IMG_IDCARD_FRONT
        defaultMercuryShouldBeFound("imgIdcardFront.in=" + DEFAULT_IMG_IDCARD_FRONT + "," + UPDATED_IMG_IDCARD_FRONT);

        // Get all the mercuryList where imgIdcardFront equals to UPDATED_IMG_IDCARD_FRONT
        defaultMercuryShouldNotBeFound("imgIdcardFront.in=" + UPDATED_IMG_IDCARD_FRONT);
    }

    @Test
    @Transactional
    public void getAllMercuriesByImgIdcardFrontIsNullOrNotNull() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where imgIdcardFront is not null
        defaultMercuryShouldBeFound("imgIdcardFront.specified=true");

        // Get all the mercuryList where imgIdcardFront is null
        defaultMercuryShouldNotBeFound("imgIdcardFront.specified=false");
    }

    @Test
    @Transactional
    public void getAllMercuriesByImgIdcardBackIsEqualToSomething() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where imgIdcardBack equals to DEFAULT_IMG_IDCARD_BACK
        defaultMercuryShouldBeFound("imgIdcardBack.equals=" + DEFAULT_IMG_IDCARD_BACK);

        // Get all the mercuryList where imgIdcardBack equals to UPDATED_IMG_IDCARD_BACK
        defaultMercuryShouldNotBeFound("imgIdcardBack.equals=" + UPDATED_IMG_IDCARD_BACK);
    }

    @Test
    @Transactional
    public void getAllMercuriesByImgIdcardBackIsInShouldWork() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where imgIdcardBack in DEFAULT_IMG_IDCARD_BACK or UPDATED_IMG_IDCARD_BACK
        defaultMercuryShouldBeFound("imgIdcardBack.in=" + DEFAULT_IMG_IDCARD_BACK + "," + UPDATED_IMG_IDCARD_BACK);

        // Get all the mercuryList where imgIdcardBack equals to UPDATED_IMG_IDCARD_BACK
        defaultMercuryShouldNotBeFound("imgIdcardBack.in=" + UPDATED_IMG_IDCARD_BACK);
    }

    @Test
    @Transactional
    public void getAllMercuriesByImgIdcardBackIsNullOrNotNull() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where imgIdcardBack is not null
        defaultMercuryShouldBeFound("imgIdcardBack.specified=true");

        // Get all the mercuryList where imgIdcardBack is null
        defaultMercuryShouldNotBeFound("imgIdcardBack.specified=false");
    }

    @Test
    @Transactional
    public void getAllMercuriesByImgIdcardHoldIsEqualToSomething() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where imgIdcardHold equals to DEFAULT_IMG_IDCARD_HOLD
        defaultMercuryShouldBeFound("imgIdcardHold.equals=" + DEFAULT_IMG_IDCARD_HOLD);

        // Get all the mercuryList where imgIdcardHold equals to UPDATED_IMG_IDCARD_HOLD
        defaultMercuryShouldNotBeFound("imgIdcardHold.equals=" + UPDATED_IMG_IDCARD_HOLD);
    }

    @Test
    @Transactional
    public void getAllMercuriesByImgIdcardHoldIsInShouldWork() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where imgIdcardHold in DEFAULT_IMG_IDCARD_HOLD or UPDATED_IMG_IDCARD_HOLD
        defaultMercuryShouldBeFound("imgIdcardHold.in=" + DEFAULT_IMG_IDCARD_HOLD + "," + UPDATED_IMG_IDCARD_HOLD);

        // Get all the mercuryList where imgIdcardHold equals to UPDATED_IMG_IDCARD_HOLD
        defaultMercuryShouldNotBeFound("imgIdcardHold.in=" + UPDATED_IMG_IDCARD_HOLD);
    }

    @Test
    @Transactional
    public void getAllMercuriesByImgIdcardHoldIsNullOrNotNull() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where imgIdcardHold is not null
        defaultMercuryShouldBeFound("imgIdcardHold.specified=true");

        // Get all the mercuryList where imgIdcardHold is null
        defaultMercuryShouldNotBeFound("imgIdcardHold.specified=false");
    }

    @Test
    @Transactional
    public void getAllMercuriesByLangitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where langitude equals to DEFAULT_LANGITUDE
        defaultMercuryShouldBeFound("langitude.equals=" + DEFAULT_LANGITUDE);

        // Get all the mercuryList where langitude equals to UPDATED_LANGITUDE
        defaultMercuryShouldNotBeFound("langitude.equals=" + UPDATED_LANGITUDE);
    }

    @Test
    @Transactional
    public void getAllMercuriesByLangitudeIsInShouldWork() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where langitude in DEFAULT_LANGITUDE or UPDATED_LANGITUDE
        defaultMercuryShouldBeFound("langitude.in=" + DEFAULT_LANGITUDE + "," + UPDATED_LANGITUDE);

        // Get all the mercuryList where langitude equals to UPDATED_LANGITUDE
        defaultMercuryShouldNotBeFound("langitude.in=" + UPDATED_LANGITUDE);
    }

    @Test
    @Transactional
    public void getAllMercuriesByLangitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where langitude is not null
        defaultMercuryShouldBeFound("langitude.specified=true");

        // Get all the mercuryList where langitude is null
        defaultMercuryShouldNotBeFound("langitude.specified=false");
    }

    @Test
    @Transactional
    public void getAllMercuriesByLantitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where lantitude equals to DEFAULT_LANTITUDE
        defaultMercuryShouldBeFound("lantitude.equals=" + DEFAULT_LANTITUDE);

        // Get all the mercuryList where lantitude equals to UPDATED_LANTITUDE
        defaultMercuryShouldNotBeFound("lantitude.equals=" + UPDATED_LANTITUDE);
    }

    @Test
    @Transactional
    public void getAllMercuriesByLantitudeIsInShouldWork() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where lantitude in DEFAULT_LANTITUDE or UPDATED_LANTITUDE
        defaultMercuryShouldBeFound("lantitude.in=" + DEFAULT_LANTITUDE + "," + UPDATED_LANTITUDE);

        // Get all the mercuryList where lantitude equals to UPDATED_LANTITUDE
        defaultMercuryShouldNotBeFound("lantitude.in=" + UPDATED_LANTITUDE);
    }

    @Test
    @Transactional
    public void getAllMercuriesByLantitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where lantitude is not null
        defaultMercuryShouldBeFound("lantitude.specified=true");

        // Get all the mercuryList where lantitude is null
        defaultMercuryShouldNotBeFound("lantitude.specified=false");
    }

    @Test
    @Transactional
    public void getAllMercuriesByProviceIsEqualToSomething() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where provice equals to DEFAULT_PROVICE
        defaultMercuryShouldBeFound("provice.equals=" + DEFAULT_PROVICE);

        // Get all the mercuryList where provice equals to UPDATED_PROVICE
        defaultMercuryShouldNotBeFound("provice.equals=" + UPDATED_PROVICE);
    }

    @Test
    @Transactional
    public void getAllMercuriesByProviceIsInShouldWork() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where provice in DEFAULT_PROVICE or UPDATED_PROVICE
        defaultMercuryShouldBeFound("provice.in=" + DEFAULT_PROVICE + "," + UPDATED_PROVICE);

        // Get all the mercuryList where provice equals to UPDATED_PROVICE
        defaultMercuryShouldNotBeFound("provice.in=" + UPDATED_PROVICE);
    }

    @Test
    @Transactional
    public void getAllMercuriesByProviceIsNullOrNotNull() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where provice is not null
        defaultMercuryShouldBeFound("provice.specified=true");

        // Get all the mercuryList where provice is null
        defaultMercuryShouldNotBeFound("provice.specified=false");
    }

    @Test
    @Transactional
    public void getAllMercuriesByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where city equals to DEFAULT_CITY
        defaultMercuryShouldBeFound("city.equals=" + DEFAULT_CITY);

        // Get all the mercuryList where city equals to UPDATED_CITY
        defaultMercuryShouldNotBeFound("city.equals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllMercuriesByCityIsInShouldWork() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where city in DEFAULT_CITY or UPDATED_CITY
        defaultMercuryShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

        // Get all the mercuryList where city equals to UPDATED_CITY
        defaultMercuryShouldNotBeFound("city.in=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllMercuriesByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where city is not null
        defaultMercuryShouldBeFound("city.specified=true");

        // Get all the mercuryList where city is null
        defaultMercuryShouldNotBeFound("city.specified=false");
    }

    @Test
    @Transactional
    public void getAllMercuriesByStreetIsEqualToSomething() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where street equals to DEFAULT_STREET
        defaultMercuryShouldBeFound("street.equals=" + DEFAULT_STREET);

        // Get all the mercuryList where street equals to UPDATED_STREET
        defaultMercuryShouldNotBeFound("street.equals=" + UPDATED_STREET);
    }

    @Test
    @Transactional
    public void getAllMercuriesByStreetIsInShouldWork() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where street in DEFAULT_STREET or UPDATED_STREET
        defaultMercuryShouldBeFound("street.in=" + DEFAULT_STREET + "," + UPDATED_STREET);

        // Get all the mercuryList where street equals to UPDATED_STREET
        defaultMercuryShouldNotBeFound("street.in=" + UPDATED_STREET);
    }

    @Test
    @Transactional
    public void getAllMercuriesByStreetIsNullOrNotNull() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where street is not null
        defaultMercuryShouldBeFound("street.specified=true");

        // Get all the mercuryList where street is null
        defaultMercuryShouldNotBeFound("street.specified=false");
    }

    @Test
    @Transactional
    public void getAllMercuriesByImgFacadeIsEqualToSomething() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where imgFacade equals to DEFAULT_IMG_FACADE
        defaultMercuryShouldBeFound("imgFacade.equals=" + DEFAULT_IMG_FACADE);

        // Get all the mercuryList where imgFacade equals to UPDATED_IMG_FACADE
        defaultMercuryShouldNotBeFound("imgFacade.equals=" + UPDATED_IMG_FACADE);
    }

    @Test
    @Transactional
    public void getAllMercuriesByImgFacadeIsInShouldWork() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where imgFacade in DEFAULT_IMG_FACADE or UPDATED_IMG_FACADE
        defaultMercuryShouldBeFound("imgFacade.in=" + DEFAULT_IMG_FACADE + "," + UPDATED_IMG_FACADE);

        // Get all the mercuryList where imgFacade equals to UPDATED_IMG_FACADE
        defaultMercuryShouldNotBeFound("imgFacade.in=" + UPDATED_IMG_FACADE);
    }

    @Test
    @Transactional
    public void getAllMercuriesByImgFacadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where imgFacade is not null
        defaultMercuryShouldBeFound("imgFacade.specified=true");

        // Get all the mercuryList where imgFacade is null
        defaultMercuryShouldNotBeFound("imgFacade.specified=false");
    }

    @Test
    @Transactional
    public void getAllMercuriesByImgIntroducesIsEqualToSomething() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where imgIntroduces equals to DEFAULT_IMG_INTRODUCES
        defaultMercuryShouldBeFound("imgIntroduces.equals=" + DEFAULT_IMG_INTRODUCES);

        // Get all the mercuryList where imgIntroduces equals to UPDATED_IMG_INTRODUCES
        defaultMercuryShouldNotBeFound("imgIntroduces.equals=" + UPDATED_IMG_INTRODUCES);
    }

    @Test
    @Transactional
    public void getAllMercuriesByImgIntroducesIsInShouldWork() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where imgIntroduces in DEFAULT_IMG_INTRODUCES or UPDATED_IMG_INTRODUCES
        defaultMercuryShouldBeFound("imgIntroduces.in=" + DEFAULT_IMG_INTRODUCES + "," + UPDATED_IMG_INTRODUCES);

        // Get all the mercuryList where imgIntroduces equals to UPDATED_IMG_INTRODUCES
        defaultMercuryShouldNotBeFound("imgIntroduces.in=" + UPDATED_IMG_INTRODUCES);
    }

    @Test
    @Transactional
    public void getAllMercuriesByImgIntroducesIsNullOrNotNull() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where imgIntroduces is not null
        defaultMercuryShouldBeFound("imgIntroduces.specified=true");

        // Get all the mercuryList where imgIntroduces is null
        defaultMercuryShouldNotBeFound("imgIntroduces.specified=false");
    }

    @Test
    @Transactional
    public void getAllMercuriesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where status equals to DEFAULT_STATUS
        defaultMercuryShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the mercuryList where status equals to UPDATED_STATUS
        defaultMercuryShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMercuriesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultMercuryShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the mercuryList where status equals to UPDATED_STATUS
        defaultMercuryShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMercuriesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where status is not null
        defaultMercuryShouldBeFound("status.specified=true");

        // Get all the mercuryList where status is null
        defaultMercuryShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllMercuriesByStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where status greater than or equals to DEFAULT_STATUS
        defaultMercuryShouldBeFound("status.greaterOrEqualThan=" + DEFAULT_STATUS);

        // Get all the mercuryList where status greater than or equals to UPDATED_STATUS
        defaultMercuryShouldNotBeFound("status.greaterOrEqualThan=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMercuriesByStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where status less than or equals to DEFAULT_STATUS
        defaultMercuryShouldNotBeFound("status.lessThan=" + DEFAULT_STATUS);

        // Get all the mercuryList where status less than or equals to UPDATED_STATUS
        defaultMercuryShouldBeFound("status.lessThan=" + UPDATED_STATUS);
    }


    @Test
    @Transactional
    public void getAllMercuriesByCreatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where createdTime equals to DEFAULT_CREATED_TIME
        defaultMercuryShouldBeFound("createdTime.equals=" + DEFAULT_CREATED_TIME);

        // Get all the mercuryList where createdTime equals to UPDATED_CREATED_TIME
        defaultMercuryShouldNotBeFound("createdTime.equals=" + UPDATED_CREATED_TIME);
    }

    @Test
    @Transactional
    public void getAllMercuriesByCreatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where createdTime in DEFAULT_CREATED_TIME or UPDATED_CREATED_TIME
        defaultMercuryShouldBeFound("createdTime.in=" + DEFAULT_CREATED_TIME + "," + UPDATED_CREATED_TIME);

        // Get all the mercuryList where createdTime equals to UPDATED_CREATED_TIME
        defaultMercuryShouldNotBeFound("createdTime.in=" + UPDATED_CREATED_TIME);
    }

    @Test
    @Transactional
    public void getAllMercuriesByCreatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where createdTime is not null
        defaultMercuryShouldBeFound("createdTime.specified=true");

        // Get all the mercuryList where createdTime is null
        defaultMercuryShouldNotBeFound("createdTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllMercuriesByUpdatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where updatedTime equals to DEFAULT_UPDATED_TIME
        defaultMercuryShouldBeFound("updatedTime.equals=" + DEFAULT_UPDATED_TIME);

        // Get all the mercuryList where updatedTime equals to UPDATED_UPDATED_TIME
        defaultMercuryShouldNotBeFound("updatedTime.equals=" + UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void getAllMercuriesByUpdatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where updatedTime in DEFAULT_UPDATED_TIME or UPDATED_UPDATED_TIME
        defaultMercuryShouldBeFound("updatedTime.in=" + DEFAULT_UPDATED_TIME + "," + UPDATED_UPDATED_TIME);

        // Get all the mercuryList where updatedTime equals to UPDATED_UPDATED_TIME
        defaultMercuryShouldNotBeFound("updatedTime.in=" + UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void getAllMercuriesByUpdatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);

        // Get all the mercuryList where updatedTime is not null
        defaultMercuryShouldBeFound("updatedTime.specified=true");

        // Get all the mercuryList where updatedTime is null
        defaultMercuryShouldNotBeFound("updatedTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllMercuriesByMercuryStatusHistoryIsEqualToSomething() throws Exception {
        // Initialize the database
        MercuryStatusHistory mercuryStatusHistory = MercuryStatusHistoryResourceIntTest.createEntity(em);
        em.persist(mercuryStatusHistory);
        em.flush();
        mercury.addMercuryStatusHistory(mercuryStatusHistory);
        mercuryRepository.saveAndFlush(mercury);
        Long mercuryStatusHistoryId = mercuryStatusHistory.getId();

        // Get all the mercuryList where mercuryStatusHistory equals to mercuryStatusHistoryId
        defaultMercuryShouldBeFound("mercuryStatusHistoryId.equals=" + mercuryStatusHistoryId);

        // Get all the mercuryList where mercuryStatusHistory equals to mercuryStatusHistoryId + 1
        defaultMercuryShouldNotBeFound("mercuryStatusHistoryId.equals=" + (mercuryStatusHistoryId + 1));
    }


    @Test
    @Transactional
    public void getAllMercuriesByOwnerRelationIsEqualToSomething() throws Exception {
        // Initialize the database
        OwnerRelation ownerRelation = OwnerRelationResourceIntTest.createEntity(em);
        em.persist(ownerRelation);
        em.flush();
        mercury.setOwnerRelation(ownerRelation);
        ownerRelation.setMercury(mercury);
        mercuryRepository.saveAndFlush(mercury);
        Long ownerRelationId = ownerRelation.getId();

        // Get all the mercuryList where ownerRelation equals to ownerRelationId
        defaultMercuryShouldBeFound("ownerRelationId.equals=" + ownerRelationId);

        // Get all the mercuryList where ownerRelation equals to ownerRelationId + 1
        defaultMercuryShouldNotBeFound("ownerRelationId.equals=" + (ownerRelationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultMercuryShouldBeFound(String filter) throws Exception {
        restMercuryMockMvc.perform(get("/api/mercuries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mercury.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].imgLicense").value(hasItem(DEFAULT_IMG_LICENSE.toString())))
            .andExpect(jsonPath("$.[*].imgIdcardFront").value(hasItem(DEFAULT_IMG_IDCARD_FRONT.toString())))
            .andExpect(jsonPath("$.[*].imgIdcardBack").value(hasItem(DEFAULT_IMG_IDCARD_BACK.toString())))
            .andExpect(jsonPath("$.[*].imgIdcardHold").value(hasItem(DEFAULT_IMG_IDCARD_HOLD.toString())))
            .andExpect(jsonPath("$.[*].langitude").value(hasItem(DEFAULT_LANGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].lantitude").value(hasItem(DEFAULT_LANTITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].provice").value(hasItem(DEFAULT_PROVICE.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
            .andExpect(jsonPath("$.[*].imgFacade").value(hasItem(DEFAULT_IMG_FACADE.toString())))
            .andExpect(jsonPath("$.[*].imgIntroduces").value(hasItem(DEFAULT_IMG_INTRODUCES.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultMercuryShouldNotBeFound(String filter) throws Exception {
        restMercuryMockMvc.perform(get("/api/mercuries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingMercury() throws Exception {
        // Get the mercury
        restMercuryMockMvc.perform(get("/api/mercuries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMercury() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);
        int databaseSizeBeforeUpdate = mercuryRepository.findAll().size();

        // Update the mercury
        Mercury updatedMercury = mercuryRepository.findOne(mercury.getId());
        // Disconnect from session so that the updates on updatedMercury are not directly saved in db
        em.detach(updatedMercury);
        updatedMercury
            .name(UPDATED_NAME)
            .imgLicense(UPDATED_IMG_LICENSE)
            .imgIdcardFront(UPDATED_IMG_IDCARD_FRONT)
            .imgIdcardBack(UPDATED_IMG_IDCARD_BACK)
            .imgIdcardHold(UPDATED_IMG_IDCARD_HOLD)
            .langitude(UPDATED_LANGITUDE)
            .lantitude(UPDATED_LANTITUDE)
            .provice(UPDATED_PROVICE)
            .city(UPDATED_CITY)
            .street(UPDATED_STREET)
            .imgFacade(UPDATED_IMG_FACADE)
            .imgIntroduces(UPDATED_IMG_INTRODUCES)
            .status(UPDATED_STATUS)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedTime(UPDATED_UPDATED_TIME);
        MercuryDTO mercuryDTO = mercuryMapper.toDto(updatedMercury);

        restMercuryMockMvc.perform(put("/api/mercuries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mercuryDTO)))
            .andExpect(status().isOk());

        // Validate the Mercury in the database
        List<Mercury> mercuryList = mercuryRepository.findAll();
        assertThat(mercuryList).hasSize(databaseSizeBeforeUpdate);
        Mercury testMercury = mercuryList.get(mercuryList.size() - 1);
        assertThat(testMercury.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMercury.getImgLicense()).isEqualTo(UPDATED_IMG_LICENSE);
        assertThat(testMercury.getImgIdcardFront()).isEqualTo(UPDATED_IMG_IDCARD_FRONT);
        assertThat(testMercury.getImgIdcardBack()).isEqualTo(UPDATED_IMG_IDCARD_BACK);
        assertThat(testMercury.getImgIdcardHold()).isEqualTo(UPDATED_IMG_IDCARD_HOLD);
        assertThat(testMercury.getLangitude()).isEqualTo(UPDATED_LANGITUDE);
        assertThat(testMercury.getLantitude()).isEqualTo(UPDATED_LANTITUDE);
        assertThat(testMercury.getProvice()).isEqualTo(UPDATED_PROVICE);
        assertThat(testMercury.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testMercury.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testMercury.getImgFacade()).isEqualTo(UPDATED_IMG_FACADE);
        assertThat(testMercury.getImgIntroduces()).isEqualTo(UPDATED_IMG_INTRODUCES);
        assertThat(testMercury.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMercury.getCreatedTime()).isEqualTo(UPDATED_CREATED_TIME);
        assertThat(testMercury.getUpdatedTime()).isEqualTo(UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingMercury() throws Exception {
        int databaseSizeBeforeUpdate = mercuryRepository.findAll().size();

        // Create the Mercury
        MercuryDTO mercuryDTO = mercuryMapper.toDto(mercury);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMercuryMockMvc.perform(put("/api/mercuries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mercuryDTO)))
            .andExpect(status().isCreated());

        // Validate the Mercury in the database
        List<Mercury> mercuryList = mercuryRepository.findAll();
        assertThat(mercuryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMercury() throws Exception {
        // Initialize the database
        mercuryRepository.saveAndFlush(mercury);
        int databaseSizeBeforeDelete = mercuryRepository.findAll().size();

        // Get the mercury
        restMercuryMockMvc.perform(delete("/api/mercuries/{id}", mercury.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Mercury> mercuryList = mercuryRepository.findAll();
        assertThat(mercuryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mercury.class);
        Mercury mercury1 = new Mercury();
        mercury1.setId(1L);
        Mercury mercury2 = new Mercury();
        mercury2.setId(mercury1.getId());
        assertThat(mercury1).isEqualTo(mercury2);
        mercury2.setId(2L);
        assertThat(mercury1).isNotEqualTo(mercury2);
        mercury1.setId(null);
        assertThat(mercury1).isNotEqualTo(mercury2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MercuryDTO.class);
        MercuryDTO mercuryDTO1 = new MercuryDTO();
        mercuryDTO1.setId(1L);
        MercuryDTO mercuryDTO2 = new MercuryDTO();
        assertThat(mercuryDTO1).isNotEqualTo(mercuryDTO2);
        mercuryDTO2.setId(mercuryDTO1.getId());
        assertThat(mercuryDTO1).isEqualTo(mercuryDTO2);
        mercuryDTO2.setId(2L);
        assertThat(mercuryDTO1).isNotEqualTo(mercuryDTO2);
        mercuryDTO1.setId(null);
        assertThat(mercuryDTO1).isNotEqualTo(mercuryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mercuryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mercuryMapper.fromId(null)).isNull();
    }
}
