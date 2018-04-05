package com.eyun.user.web.rest;

import com.eyun.user.UserApp;

import com.eyun.user.config.SecurityBeanOverrideConfiguration;

import com.eyun.user.domain.MercuryStatusHistory;
import com.eyun.user.repository.MercuryStatusHistoryRepository;
import com.eyun.user.service.MercuryStatusHistoryService;
import com.eyun.user.service.dto.MercuryStatusHistoryDTO;
import com.eyun.user.service.mapper.MercuryStatusHistoryMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.eyun.user.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MercuryStatusHistoryResource REST controller.
 *
 * @see MercuryStatusHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserApp.class, SecurityBeanOverrideConfiguration.class})
public class MercuryStatusHistoryResourceIntTest {

    private static final Long DEFAULT_MODIFIED_BY = 1L;
    private static final Long UPDATED_MODIFIED_BY = 2L;

    private static final Instant DEFAULT_MODIFIED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private MercuryStatusHistoryRepository mercuryStatusHistoryRepository;

    @Autowired
    private MercuryStatusHistoryMapper mercuryStatusHistoryMapper;

    @Autowired
    private MercuryStatusHistoryService mercuryStatusHistoryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMercuryStatusHistoryMockMvc;

    private MercuryStatusHistory mercuryStatusHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MercuryStatusHistoryResource mercuryStatusHistoryResource = new MercuryStatusHistoryResource(mercuryStatusHistoryService);
        this.restMercuryStatusHistoryMockMvc = MockMvcBuilders.standaloneSetup(mercuryStatusHistoryResource)
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
    public static MercuryStatusHistory createEntity(EntityManager em) {
        MercuryStatusHistory mercuryStatusHistory = new MercuryStatusHistory()
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedTime(DEFAULT_MODIFIED_TIME);
        return mercuryStatusHistory;
    }

    @Before
    public void initTest() {
        mercuryStatusHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createMercuryStatusHistory() throws Exception {
        int databaseSizeBeforeCreate = mercuryStatusHistoryRepository.findAll().size();

        // Create the MercuryStatusHistory
        MercuryStatusHistoryDTO mercuryStatusHistoryDTO = mercuryStatusHistoryMapper.toDto(mercuryStatusHistory);
        restMercuryStatusHistoryMockMvc.perform(post("/api/mercury-status-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mercuryStatusHistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the MercuryStatusHistory in the database
        List<MercuryStatusHistory> mercuryStatusHistoryList = mercuryStatusHistoryRepository.findAll();
        assertThat(mercuryStatusHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        MercuryStatusHistory testMercuryStatusHistory = mercuryStatusHistoryList.get(mercuryStatusHistoryList.size() - 1);
        assertThat(testMercuryStatusHistory.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testMercuryStatusHistory.getModifiedTime()).isEqualTo(DEFAULT_MODIFIED_TIME);
    }

    @Test
    @Transactional
    public void createMercuryStatusHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mercuryStatusHistoryRepository.findAll().size();

        // Create the MercuryStatusHistory with an existing ID
        mercuryStatusHistory.setId(1L);
        MercuryStatusHistoryDTO mercuryStatusHistoryDTO = mercuryStatusHistoryMapper.toDto(mercuryStatusHistory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMercuryStatusHistoryMockMvc.perform(post("/api/mercury-status-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mercuryStatusHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MercuryStatusHistory in the database
        List<MercuryStatusHistory> mercuryStatusHistoryList = mercuryStatusHistoryRepository.findAll();
        assertThat(mercuryStatusHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMercuryStatusHistories() throws Exception {
        // Initialize the database
        mercuryStatusHistoryRepository.saveAndFlush(mercuryStatusHistory);

        // Get all the mercuryStatusHistoryList
        restMercuryStatusHistoryMockMvc.perform(get("/api/mercury-status-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mercuryStatusHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.intValue())))
            .andExpect(jsonPath("$.[*].modifiedTime").value(hasItem(DEFAULT_MODIFIED_TIME.toString())));
    }

    @Test
    @Transactional
    public void getMercuryStatusHistory() throws Exception {
        // Initialize the database
        mercuryStatusHistoryRepository.saveAndFlush(mercuryStatusHistory);

        // Get the mercuryStatusHistory
        restMercuryStatusHistoryMockMvc.perform(get("/api/mercury-status-histories/{id}", mercuryStatusHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mercuryStatusHistory.getId().intValue()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.intValue()))
            .andExpect(jsonPath("$.modifiedTime").value(DEFAULT_MODIFIED_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMercuryStatusHistory() throws Exception {
        // Get the mercuryStatusHistory
        restMercuryStatusHistoryMockMvc.perform(get("/api/mercury-status-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMercuryStatusHistory() throws Exception {
        // Initialize the database
        mercuryStatusHistoryRepository.saveAndFlush(mercuryStatusHistory);
        int databaseSizeBeforeUpdate = mercuryStatusHistoryRepository.findAll().size();

        // Update the mercuryStatusHistory
        MercuryStatusHistory updatedMercuryStatusHistory = mercuryStatusHistoryRepository.findOne(mercuryStatusHistory.getId());
        // Disconnect from session so that the updates on updatedMercuryStatusHistory are not directly saved in db
        em.detach(updatedMercuryStatusHistory);
        updatedMercuryStatusHistory
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedTime(UPDATED_MODIFIED_TIME);
        MercuryStatusHistoryDTO mercuryStatusHistoryDTO = mercuryStatusHistoryMapper.toDto(updatedMercuryStatusHistory);

        restMercuryStatusHistoryMockMvc.perform(put("/api/mercury-status-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mercuryStatusHistoryDTO)))
            .andExpect(status().isOk());

        // Validate the MercuryStatusHistory in the database
        List<MercuryStatusHistory> mercuryStatusHistoryList = mercuryStatusHistoryRepository.findAll();
        assertThat(mercuryStatusHistoryList).hasSize(databaseSizeBeforeUpdate);
        MercuryStatusHistory testMercuryStatusHistory = mercuryStatusHistoryList.get(mercuryStatusHistoryList.size() - 1);
        assertThat(testMercuryStatusHistory.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testMercuryStatusHistory.getModifiedTime()).isEqualTo(UPDATED_MODIFIED_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingMercuryStatusHistory() throws Exception {
        int databaseSizeBeforeUpdate = mercuryStatusHistoryRepository.findAll().size();

        // Create the MercuryStatusHistory
        MercuryStatusHistoryDTO mercuryStatusHistoryDTO = mercuryStatusHistoryMapper.toDto(mercuryStatusHistory);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMercuryStatusHistoryMockMvc.perform(put("/api/mercury-status-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mercuryStatusHistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the MercuryStatusHistory in the database
        List<MercuryStatusHistory> mercuryStatusHistoryList = mercuryStatusHistoryRepository.findAll();
        assertThat(mercuryStatusHistoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMercuryStatusHistory() throws Exception {
        // Initialize the database
        mercuryStatusHistoryRepository.saveAndFlush(mercuryStatusHistory);
        int databaseSizeBeforeDelete = mercuryStatusHistoryRepository.findAll().size();

        // Get the mercuryStatusHistory
        restMercuryStatusHistoryMockMvc.perform(delete("/api/mercury-status-histories/{id}", mercuryStatusHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MercuryStatusHistory> mercuryStatusHistoryList = mercuryStatusHistoryRepository.findAll();
        assertThat(mercuryStatusHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MercuryStatusHistory.class);
        MercuryStatusHistory mercuryStatusHistory1 = new MercuryStatusHistory();
        mercuryStatusHistory1.setId(1L);
        MercuryStatusHistory mercuryStatusHistory2 = new MercuryStatusHistory();
        mercuryStatusHistory2.setId(mercuryStatusHistory1.getId());
        assertThat(mercuryStatusHistory1).isEqualTo(mercuryStatusHistory2);
        mercuryStatusHistory2.setId(2L);
        assertThat(mercuryStatusHistory1).isNotEqualTo(mercuryStatusHistory2);
        mercuryStatusHistory1.setId(null);
        assertThat(mercuryStatusHistory1).isNotEqualTo(mercuryStatusHistory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MercuryStatusHistoryDTO.class);
        MercuryStatusHistoryDTO mercuryStatusHistoryDTO1 = new MercuryStatusHistoryDTO();
        mercuryStatusHistoryDTO1.setId(1L);
        MercuryStatusHistoryDTO mercuryStatusHistoryDTO2 = new MercuryStatusHistoryDTO();
        assertThat(mercuryStatusHistoryDTO1).isNotEqualTo(mercuryStatusHistoryDTO2);
        mercuryStatusHistoryDTO2.setId(mercuryStatusHistoryDTO1.getId());
        assertThat(mercuryStatusHistoryDTO1).isEqualTo(mercuryStatusHistoryDTO2);
        mercuryStatusHistoryDTO2.setId(2L);
        assertThat(mercuryStatusHistoryDTO1).isNotEqualTo(mercuryStatusHistoryDTO2);
        mercuryStatusHistoryDTO1.setId(null);
        assertThat(mercuryStatusHistoryDTO1).isNotEqualTo(mercuryStatusHistoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mercuryStatusHistoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mercuryStatusHistoryMapper.fromId(null)).isNull();
    }
}
