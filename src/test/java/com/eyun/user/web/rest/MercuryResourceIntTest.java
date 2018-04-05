package com.eyun.user.web.rest;

import com.eyun.user.UserApp;

import com.eyun.user.config.SecurityBeanOverrideConfiguration;

import com.eyun.user.domain.Mercury;
import com.eyun.user.repository.MercuryRepository;
import com.eyun.user.service.MercuryService;
import com.eyun.user.service.dto.MercuryDTO;
import com.eyun.user.service.mapper.MercuryMapper;
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

    private static final Float DEFAULT_LANGITUDE = 1F;
    private static final Float UPDATED_LANGITUDE = 2F;

    private static final Float DEFAULT_LANTITUDE = 1F;
    private static final Float UPDATED_LANTITUDE = 2F;

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

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    @Autowired
    private MercuryRepository mercuryRepository;

    @Autowired
    private MercuryMapper mercuryMapper;

    @Autowired
    private MercuryService mercuryService;

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
        final MercuryResource mercuryResource = new MercuryResource(mercuryService);
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
            .desc(DEFAULT_DESC);
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
        assertThat(testMercury.getDesc()).isEqualTo(DEFAULT_DESC);
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
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC.toString())));
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
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC.toString()));
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
            .desc(UPDATED_DESC);
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
        assertThat(testMercury.getDesc()).isEqualTo(UPDATED_DESC);
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
