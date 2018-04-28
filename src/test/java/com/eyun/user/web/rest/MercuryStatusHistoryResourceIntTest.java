package com.eyun.user.web.rest;

import com.eyun.user.UserApp;

import com.eyun.user.config.SecurityBeanOverrideConfiguration;

import com.eyun.user.domain.MercuryStatusHistory;
import com.eyun.user.domain.Mercury;
import com.eyun.user.repository.MercuryStatusHistoryRepository;
import com.eyun.user.service.MercuryStatusHistoryService;
import com.eyun.user.service.dto.MercuryStatusHistoryDTO;
import com.eyun.user.service.mapper.MercuryStatusHistoryMapper;
import com.eyun.user.web.rest.errors.ExceptionTranslator;
import com.eyun.user.service.dto.MercuryStatusHistoryCriteria;
import com.eyun.user.service.MercuryStatusHistoryQueryService;

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

    private static final Integer DEFAULT_WITH_STATUS = 1;
    private static final Integer UPDATED_WITH_STATUS = 2;

    private static final Integer DEFAULT_TO_STATUS = 1;
    private static final Integer UPDATED_TO_STATUS = 2;

    @Autowired
    private MercuryStatusHistoryRepository mercuryStatusHistoryRepository;

    @Autowired
    private MercuryStatusHistoryMapper mercuryStatusHistoryMapper;

    @Autowired
    private MercuryStatusHistoryService mercuryStatusHistoryService;

    @Autowired
    private MercuryStatusHistoryQueryService mercuryStatusHistoryQueryService;

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
        final MercuryStatusHistoryResource mercuryStatusHistoryResource = new MercuryStatusHistoryResource(mercuryStatusHistoryService, mercuryStatusHistoryQueryService);
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
            .modifiedTime(DEFAULT_MODIFIED_TIME)
            .withStatus(DEFAULT_WITH_STATUS)
            .toStatus(DEFAULT_TO_STATUS);
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
        assertThat(testMercuryStatusHistory.getWithStatus()).isEqualTo(DEFAULT_WITH_STATUS);
        assertThat(testMercuryStatusHistory.getToStatus()).isEqualTo(DEFAULT_TO_STATUS);
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
            .andExpect(jsonPath("$.[*].modifiedTime").value(hasItem(DEFAULT_MODIFIED_TIME.toString())))
            .andExpect(jsonPath("$.[*].withStatus").value(hasItem(DEFAULT_WITH_STATUS)))
            .andExpect(jsonPath("$.[*].toStatus").value(hasItem(DEFAULT_TO_STATUS)));
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
            .andExpect(jsonPath("$.modifiedTime").value(DEFAULT_MODIFIED_TIME.toString()))
            .andExpect(jsonPath("$.withStatus").value(DEFAULT_WITH_STATUS))
            .andExpect(jsonPath("$.toStatus").value(DEFAULT_TO_STATUS));
    }

    @Test
    @Transactional
    public void getAllMercuryStatusHistoriesByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        mercuryStatusHistoryRepository.saveAndFlush(mercuryStatusHistory);

        // Get all the mercuryStatusHistoryList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultMercuryStatusHistoryShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the mercuryStatusHistoryList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultMercuryStatusHistoryShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllMercuryStatusHistoriesByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        mercuryStatusHistoryRepository.saveAndFlush(mercuryStatusHistory);

        // Get all the mercuryStatusHistoryList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultMercuryStatusHistoryShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the mercuryStatusHistoryList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultMercuryStatusHistoryShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllMercuryStatusHistoriesByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        mercuryStatusHistoryRepository.saveAndFlush(mercuryStatusHistory);

        // Get all the mercuryStatusHistoryList where modifiedBy is not null
        defaultMercuryStatusHistoryShouldBeFound("modifiedBy.specified=true");

        // Get all the mercuryStatusHistoryList where modifiedBy is null
        defaultMercuryStatusHistoryShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllMercuryStatusHistoriesByModifiedByIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mercuryStatusHistoryRepository.saveAndFlush(mercuryStatusHistory);

        // Get all the mercuryStatusHistoryList where modifiedBy greater than or equals to DEFAULT_MODIFIED_BY
        defaultMercuryStatusHistoryShouldBeFound("modifiedBy.greaterOrEqualThan=" + DEFAULT_MODIFIED_BY);

        // Get all the mercuryStatusHistoryList where modifiedBy greater than or equals to UPDATED_MODIFIED_BY
        defaultMercuryStatusHistoryShouldNotBeFound("modifiedBy.greaterOrEqualThan=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllMercuryStatusHistoriesByModifiedByIsLessThanSomething() throws Exception {
        // Initialize the database
        mercuryStatusHistoryRepository.saveAndFlush(mercuryStatusHistory);

        // Get all the mercuryStatusHistoryList where modifiedBy less than or equals to DEFAULT_MODIFIED_BY
        defaultMercuryStatusHistoryShouldNotBeFound("modifiedBy.lessThan=" + DEFAULT_MODIFIED_BY);

        // Get all the mercuryStatusHistoryList where modifiedBy less than or equals to UPDATED_MODIFIED_BY
        defaultMercuryStatusHistoryShouldBeFound("modifiedBy.lessThan=" + UPDATED_MODIFIED_BY);
    }


    @Test
    @Transactional
    public void getAllMercuryStatusHistoriesByModifiedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        mercuryStatusHistoryRepository.saveAndFlush(mercuryStatusHistory);

        // Get all the mercuryStatusHistoryList where modifiedTime equals to DEFAULT_MODIFIED_TIME
        defaultMercuryStatusHistoryShouldBeFound("modifiedTime.equals=" + DEFAULT_MODIFIED_TIME);

        // Get all the mercuryStatusHistoryList where modifiedTime equals to UPDATED_MODIFIED_TIME
        defaultMercuryStatusHistoryShouldNotBeFound("modifiedTime.equals=" + UPDATED_MODIFIED_TIME);
    }

    @Test
    @Transactional
    public void getAllMercuryStatusHistoriesByModifiedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        mercuryStatusHistoryRepository.saveAndFlush(mercuryStatusHistory);

        // Get all the mercuryStatusHistoryList where modifiedTime in DEFAULT_MODIFIED_TIME or UPDATED_MODIFIED_TIME
        defaultMercuryStatusHistoryShouldBeFound("modifiedTime.in=" + DEFAULT_MODIFIED_TIME + "," + UPDATED_MODIFIED_TIME);

        // Get all the mercuryStatusHistoryList where modifiedTime equals to UPDATED_MODIFIED_TIME
        defaultMercuryStatusHistoryShouldNotBeFound("modifiedTime.in=" + UPDATED_MODIFIED_TIME);
    }

    @Test
    @Transactional
    public void getAllMercuryStatusHistoriesByModifiedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        mercuryStatusHistoryRepository.saveAndFlush(mercuryStatusHistory);

        // Get all the mercuryStatusHistoryList where modifiedTime is not null
        defaultMercuryStatusHistoryShouldBeFound("modifiedTime.specified=true");

        // Get all the mercuryStatusHistoryList where modifiedTime is null
        defaultMercuryStatusHistoryShouldNotBeFound("modifiedTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllMercuryStatusHistoriesByWithStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mercuryStatusHistoryRepository.saveAndFlush(mercuryStatusHistory);

        // Get all the mercuryStatusHistoryList where withStatus equals to DEFAULT_WITH_STATUS
        defaultMercuryStatusHistoryShouldBeFound("withStatus.equals=" + DEFAULT_WITH_STATUS);

        // Get all the mercuryStatusHistoryList where withStatus equals to UPDATED_WITH_STATUS
        defaultMercuryStatusHistoryShouldNotBeFound("withStatus.equals=" + UPDATED_WITH_STATUS);
    }

    @Test
    @Transactional
    public void getAllMercuryStatusHistoriesByWithStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mercuryStatusHistoryRepository.saveAndFlush(mercuryStatusHistory);

        // Get all the mercuryStatusHistoryList where withStatus in DEFAULT_WITH_STATUS or UPDATED_WITH_STATUS
        defaultMercuryStatusHistoryShouldBeFound("withStatus.in=" + DEFAULT_WITH_STATUS + "," + UPDATED_WITH_STATUS);

        // Get all the mercuryStatusHistoryList where withStatus equals to UPDATED_WITH_STATUS
        defaultMercuryStatusHistoryShouldNotBeFound("withStatus.in=" + UPDATED_WITH_STATUS);
    }

    @Test
    @Transactional
    public void getAllMercuryStatusHistoriesByWithStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mercuryStatusHistoryRepository.saveAndFlush(mercuryStatusHistory);

        // Get all the mercuryStatusHistoryList where withStatus is not null
        defaultMercuryStatusHistoryShouldBeFound("withStatus.specified=true");

        // Get all the mercuryStatusHistoryList where withStatus is null
        defaultMercuryStatusHistoryShouldNotBeFound("withStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllMercuryStatusHistoriesByWithStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mercuryStatusHistoryRepository.saveAndFlush(mercuryStatusHistory);

        // Get all the mercuryStatusHistoryList where withStatus greater than or equals to DEFAULT_WITH_STATUS
        defaultMercuryStatusHistoryShouldBeFound("withStatus.greaterOrEqualThan=" + DEFAULT_WITH_STATUS);

        // Get all the mercuryStatusHistoryList where withStatus greater than or equals to UPDATED_WITH_STATUS
        defaultMercuryStatusHistoryShouldNotBeFound("withStatus.greaterOrEqualThan=" + UPDATED_WITH_STATUS);
    }

    @Test
    @Transactional
    public void getAllMercuryStatusHistoriesByWithStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        mercuryStatusHistoryRepository.saveAndFlush(mercuryStatusHistory);

        // Get all the mercuryStatusHistoryList where withStatus less than or equals to DEFAULT_WITH_STATUS
        defaultMercuryStatusHistoryShouldNotBeFound("withStatus.lessThan=" + DEFAULT_WITH_STATUS);

        // Get all the mercuryStatusHistoryList where withStatus less than or equals to UPDATED_WITH_STATUS
        defaultMercuryStatusHistoryShouldBeFound("withStatus.lessThan=" + UPDATED_WITH_STATUS);
    }


    @Test
    @Transactional
    public void getAllMercuryStatusHistoriesByToStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mercuryStatusHistoryRepository.saveAndFlush(mercuryStatusHistory);

        // Get all the mercuryStatusHistoryList where toStatus equals to DEFAULT_TO_STATUS
        defaultMercuryStatusHistoryShouldBeFound("toStatus.equals=" + DEFAULT_TO_STATUS);

        // Get all the mercuryStatusHistoryList where toStatus equals to UPDATED_TO_STATUS
        defaultMercuryStatusHistoryShouldNotBeFound("toStatus.equals=" + UPDATED_TO_STATUS);
    }

    @Test
    @Transactional
    public void getAllMercuryStatusHistoriesByToStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mercuryStatusHistoryRepository.saveAndFlush(mercuryStatusHistory);

        // Get all the mercuryStatusHistoryList where toStatus in DEFAULT_TO_STATUS or UPDATED_TO_STATUS
        defaultMercuryStatusHistoryShouldBeFound("toStatus.in=" + DEFAULT_TO_STATUS + "," + UPDATED_TO_STATUS);

        // Get all the mercuryStatusHistoryList where toStatus equals to UPDATED_TO_STATUS
        defaultMercuryStatusHistoryShouldNotBeFound("toStatus.in=" + UPDATED_TO_STATUS);
    }

    @Test
    @Transactional
    public void getAllMercuryStatusHistoriesByToStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mercuryStatusHistoryRepository.saveAndFlush(mercuryStatusHistory);

        // Get all the mercuryStatusHistoryList where toStatus is not null
        defaultMercuryStatusHistoryShouldBeFound("toStatus.specified=true");

        // Get all the mercuryStatusHistoryList where toStatus is null
        defaultMercuryStatusHistoryShouldNotBeFound("toStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllMercuryStatusHistoriesByToStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mercuryStatusHistoryRepository.saveAndFlush(mercuryStatusHistory);

        // Get all the mercuryStatusHistoryList where toStatus greater than or equals to DEFAULT_TO_STATUS
        defaultMercuryStatusHistoryShouldBeFound("toStatus.greaterOrEqualThan=" + DEFAULT_TO_STATUS);

        // Get all the mercuryStatusHistoryList where toStatus greater than or equals to UPDATED_TO_STATUS
        defaultMercuryStatusHistoryShouldNotBeFound("toStatus.greaterOrEqualThan=" + UPDATED_TO_STATUS);
    }

    @Test
    @Transactional
    public void getAllMercuryStatusHistoriesByToStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        mercuryStatusHistoryRepository.saveAndFlush(mercuryStatusHistory);

        // Get all the mercuryStatusHistoryList where toStatus less than or equals to DEFAULT_TO_STATUS
        defaultMercuryStatusHistoryShouldNotBeFound("toStatus.lessThan=" + DEFAULT_TO_STATUS);

        // Get all the mercuryStatusHistoryList where toStatus less than or equals to UPDATED_TO_STATUS
        defaultMercuryStatusHistoryShouldBeFound("toStatus.lessThan=" + UPDATED_TO_STATUS);
    }


    @Test
    @Transactional
    public void getAllMercuryStatusHistoriesByMercuryIsEqualToSomething() throws Exception {
        // Initialize the database
        Mercury mercury = MercuryResourceIntTest.createEntity(em);
        em.persist(mercury);
        em.flush();
        mercuryStatusHistory.setMercury(mercury);
        mercuryStatusHistoryRepository.saveAndFlush(mercuryStatusHistory);
        Long mercuryId = mercury.getId();

        // Get all the mercuryStatusHistoryList where mercury equals to mercuryId
        defaultMercuryStatusHistoryShouldBeFound("mercuryId.equals=" + mercuryId);

        // Get all the mercuryStatusHistoryList where mercury equals to mercuryId + 1
        defaultMercuryStatusHistoryShouldNotBeFound("mercuryId.equals=" + (mercuryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultMercuryStatusHistoryShouldBeFound(String filter) throws Exception {
        restMercuryStatusHistoryMockMvc.perform(get("/api/mercury-status-histories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mercuryStatusHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.intValue())))
            .andExpect(jsonPath("$.[*].modifiedTime").value(hasItem(DEFAULT_MODIFIED_TIME.toString())))
            .andExpect(jsonPath("$.[*].withStatus").value(hasItem(DEFAULT_WITH_STATUS)))
            .andExpect(jsonPath("$.[*].toStatus").value(hasItem(DEFAULT_TO_STATUS)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultMercuryStatusHistoryShouldNotBeFound(String filter) throws Exception {
        restMercuryStatusHistoryMockMvc.perform(get("/api/mercury-status-histories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
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
            .modifiedTime(UPDATED_MODIFIED_TIME)
            .withStatus(UPDATED_WITH_STATUS)
            .toStatus(UPDATED_TO_STATUS);
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
        assertThat(testMercuryStatusHistory.getWithStatus()).isEqualTo(UPDATED_WITH_STATUS);
        assertThat(testMercuryStatusHistory.getToStatus()).isEqualTo(UPDATED_TO_STATUS);
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
