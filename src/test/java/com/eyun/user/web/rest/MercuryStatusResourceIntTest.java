package com.eyun.user.web.rest;

import com.eyun.user.UserApp;

import com.eyun.user.config.SecurityBeanOverrideConfiguration;

import com.eyun.user.domain.MercuryStatus;
import com.eyun.user.repository.MercuryStatusRepository;
import com.eyun.user.service.MercuryStatusService;
import com.eyun.user.service.dto.MercuryStatusDTO;
import com.eyun.user.service.mapper.MercuryStatusMapper;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.eyun.user.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MercuryStatusResource REST controller.
 *
 * @see MercuryStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserApp.class, SecurityBeanOverrideConfiguration.class})
public class MercuryStatusResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    @Autowired
    private MercuryStatusRepository mercuryStatusRepository;

    @Autowired
    private MercuryStatusMapper mercuryStatusMapper;

    @Autowired
    private MercuryStatusService mercuryStatusService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMercuryStatusMockMvc;

    private MercuryStatus mercuryStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MercuryStatusResource mercuryStatusResource = new MercuryStatusResource(mercuryStatusService);
        this.restMercuryStatusMockMvc = MockMvcBuilders.standaloneSetup(mercuryStatusResource)
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
    public static MercuryStatus createEntity(EntityManager em) {
        MercuryStatus mercuryStatus = new MercuryStatus()
            .name(DEFAULT_NAME)
            .desc(DEFAULT_DESC);
        return mercuryStatus;
    }

    @Before
    public void initTest() {
        mercuryStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createMercuryStatus() throws Exception {
        int databaseSizeBeforeCreate = mercuryStatusRepository.findAll().size();

        // Create the MercuryStatus
        MercuryStatusDTO mercuryStatusDTO = mercuryStatusMapper.toDto(mercuryStatus);
        restMercuryStatusMockMvc.perform(post("/api/mercury-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mercuryStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the MercuryStatus in the database
        List<MercuryStatus> mercuryStatusList = mercuryStatusRepository.findAll();
        assertThat(mercuryStatusList).hasSize(databaseSizeBeforeCreate + 1);
        MercuryStatus testMercuryStatus = mercuryStatusList.get(mercuryStatusList.size() - 1);
        assertThat(testMercuryStatus.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMercuryStatus.getDesc()).isEqualTo(DEFAULT_DESC);
    }

    @Test
    @Transactional
    public void createMercuryStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mercuryStatusRepository.findAll().size();

        // Create the MercuryStatus with an existing ID
        mercuryStatus.setId(1L);
        MercuryStatusDTO mercuryStatusDTO = mercuryStatusMapper.toDto(mercuryStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMercuryStatusMockMvc.perform(post("/api/mercury-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mercuryStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MercuryStatus in the database
        List<MercuryStatus> mercuryStatusList = mercuryStatusRepository.findAll();
        assertThat(mercuryStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMercuryStatuses() throws Exception {
        // Initialize the database
        mercuryStatusRepository.saveAndFlush(mercuryStatus);

        // Get all the mercuryStatusList
        restMercuryStatusMockMvc.perform(get("/api/mercury-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mercuryStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC.toString())));
    }

    @Test
    @Transactional
    public void getMercuryStatus() throws Exception {
        // Initialize the database
        mercuryStatusRepository.saveAndFlush(mercuryStatus);

        // Get the mercuryStatus
        restMercuryStatusMockMvc.perform(get("/api/mercury-statuses/{id}", mercuryStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mercuryStatus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMercuryStatus() throws Exception {
        // Get the mercuryStatus
        restMercuryStatusMockMvc.perform(get("/api/mercury-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMercuryStatus() throws Exception {
        // Initialize the database
        mercuryStatusRepository.saveAndFlush(mercuryStatus);
        int databaseSizeBeforeUpdate = mercuryStatusRepository.findAll().size();

        // Update the mercuryStatus
        MercuryStatus updatedMercuryStatus = mercuryStatusRepository.findOne(mercuryStatus.getId());
        // Disconnect from session so that the updates on updatedMercuryStatus are not directly saved in db
        em.detach(updatedMercuryStatus);
        updatedMercuryStatus
            .name(UPDATED_NAME)
            .desc(UPDATED_DESC);
        MercuryStatusDTO mercuryStatusDTO = mercuryStatusMapper.toDto(updatedMercuryStatus);

        restMercuryStatusMockMvc.perform(put("/api/mercury-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mercuryStatusDTO)))
            .andExpect(status().isOk());

        // Validate the MercuryStatus in the database
        List<MercuryStatus> mercuryStatusList = mercuryStatusRepository.findAll();
        assertThat(mercuryStatusList).hasSize(databaseSizeBeforeUpdate);
        MercuryStatus testMercuryStatus = mercuryStatusList.get(mercuryStatusList.size() - 1);
        assertThat(testMercuryStatus.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMercuryStatus.getDesc()).isEqualTo(UPDATED_DESC);
    }

    @Test
    @Transactional
    public void updateNonExistingMercuryStatus() throws Exception {
        int databaseSizeBeforeUpdate = mercuryStatusRepository.findAll().size();

        // Create the MercuryStatus
        MercuryStatusDTO mercuryStatusDTO = mercuryStatusMapper.toDto(mercuryStatus);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMercuryStatusMockMvc.perform(put("/api/mercury-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mercuryStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the MercuryStatus in the database
        List<MercuryStatus> mercuryStatusList = mercuryStatusRepository.findAll();
        assertThat(mercuryStatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMercuryStatus() throws Exception {
        // Initialize the database
        mercuryStatusRepository.saveAndFlush(mercuryStatus);
        int databaseSizeBeforeDelete = mercuryStatusRepository.findAll().size();

        // Get the mercuryStatus
        restMercuryStatusMockMvc.perform(delete("/api/mercury-statuses/{id}", mercuryStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MercuryStatus> mercuryStatusList = mercuryStatusRepository.findAll();
        assertThat(mercuryStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MercuryStatus.class);
        MercuryStatus mercuryStatus1 = new MercuryStatus();
        mercuryStatus1.setId(1L);
        MercuryStatus mercuryStatus2 = new MercuryStatus();
        mercuryStatus2.setId(mercuryStatus1.getId());
        assertThat(mercuryStatus1).isEqualTo(mercuryStatus2);
        mercuryStatus2.setId(2L);
        assertThat(mercuryStatus1).isNotEqualTo(mercuryStatus2);
        mercuryStatus1.setId(null);
        assertThat(mercuryStatus1).isNotEqualTo(mercuryStatus2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MercuryStatusDTO.class);
        MercuryStatusDTO mercuryStatusDTO1 = new MercuryStatusDTO();
        mercuryStatusDTO1.setId(1L);
        MercuryStatusDTO mercuryStatusDTO2 = new MercuryStatusDTO();
        assertThat(mercuryStatusDTO1).isNotEqualTo(mercuryStatusDTO2);
        mercuryStatusDTO2.setId(mercuryStatusDTO1.getId());
        assertThat(mercuryStatusDTO1).isEqualTo(mercuryStatusDTO2);
        mercuryStatusDTO2.setId(2L);
        assertThat(mercuryStatusDTO1).isNotEqualTo(mercuryStatusDTO2);
        mercuryStatusDTO1.setId(null);
        assertThat(mercuryStatusDTO1).isNotEqualTo(mercuryStatusDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mercuryStatusMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mercuryStatusMapper.fromId(null)).isNull();
    }
}
