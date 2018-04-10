package com.eyun.user.web.rest;

import com.eyun.user.UserApp;

import com.eyun.user.config.SecurityBeanOverrideConfiguration;

import com.eyun.user.domain.UserStatusHistory;
import com.eyun.user.domain.UserAnnex;
import com.eyun.user.repository.UserStatusHistoryRepository;
import com.eyun.user.service.UserStatusHistoryService;
import com.eyun.user.service.dto.UserStatusHistoryDTO;
import com.eyun.user.service.mapper.UserStatusHistoryMapper;
import com.eyun.user.web.rest.errors.ExceptionTranslator;
import com.eyun.user.service.dto.UserStatusHistoryCriteria;
import com.eyun.user.service.UserStatusHistoryQueryService;

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
 * Test class for the UserStatusHistoryResource REST controller.
 *
 * @see UserStatusHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserApp.class, SecurityBeanOverrideConfiguration.class})
public class UserStatusHistoryResourceIntTest {

    private static final Long DEFAULT_MODIFIED_BY = 1L;
    private static final Long UPDATED_MODIFIED_BY = 2L;

    private static final Instant DEFAULT_MODIFIED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_USERID = 1L;
    private static final Long UPDATED_USERID = 2L;

    private static final Integer DEFAULT_WITH_STATUS = 1;
    private static final Integer UPDATED_WITH_STATUS = 2;

    private static final Integer DEFAULT_TO_STATUS = 1;
    private static final Integer UPDATED_TO_STATUS = 2;

    @Autowired
    private UserStatusHistoryRepository userStatusHistoryRepository;

    @Autowired
    private UserStatusHistoryMapper userStatusHistoryMapper;

    @Autowired
    private UserStatusHistoryService userStatusHistoryService;

    @Autowired
    private UserStatusHistoryQueryService userStatusHistoryQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserStatusHistoryMockMvc;

    private UserStatusHistory userStatusHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserStatusHistoryResource userStatusHistoryResource = new UserStatusHistoryResource(userStatusHistoryService, userStatusHistoryQueryService);
        this.restUserStatusHistoryMockMvc = MockMvcBuilders.standaloneSetup(userStatusHistoryResource)
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
    public static UserStatusHistory createEntity(EntityManager em) {
        UserStatusHistory userStatusHistory = new UserStatusHistory()
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedTime(DEFAULT_MODIFIED_TIME)
            .userid(DEFAULT_USERID)
            .withStatus(DEFAULT_WITH_STATUS)
            .toStatus(DEFAULT_TO_STATUS);
        return userStatusHistory;
    }

    @Before
    public void initTest() {
        userStatusHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserStatusHistory() throws Exception {
        int databaseSizeBeforeCreate = userStatusHistoryRepository.findAll().size();

        // Create the UserStatusHistory
        UserStatusHistoryDTO userStatusHistoryDTO = userStatusHistoryMapper.toDto(userStatusHistory);
        restUserStatusHistoryMockMvc.perform(post("/api/user-status-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userStatusHistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the UserStatusHistory in the database
        List<UserStatusHistory> userStatusHistoryList = userStatusHistoryRepository.findAll();
        assertThat(userStatusHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        UserStatusHistory testUserStatusHistory = userStatusHistoryList.get(userStatusHistoryList.size() - 1);
        assertThat(testUserStatusHistory.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testUserStatusHistory.getModifiedTime()).isEqualTo(DEFAULT_MODIFIED_TIME);
        assertThat(testUserStatusHistory.getUserid()).isEqualTo(DEFAULT_USERID);
        assertThat(testUserStatusHistory.getWithStatus()).isEqualTo(DEFAULT_WITH_STATUS);
        assertThat(testUserStatusHistory.getToStatus()).isEqualTo(DEFAULT_TO_STATUS);
    }

    @Test
    @Transactional
    public void createUserStatusHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userStatusHistoryRepository.findAll().size();

        // Create the UserStatusHistory with an existing ID
        userStatusHistory.setId(1L);
        UserStatusHistoryDTO userStatusHistoryDTO = userStatusHistoryMapper.toDto(userStatusHistory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserStatusHistoryMockMvc.perform(post("/api/user-status-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userStatusHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserStatusHistory in the database
        List<UserStatusHistory> userStatusHistoryList = userStatusHistoryRepository.findAll();
        assertThat(userStatusHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserStatusHistories() throws Exception {
        // Initialize the database
        userStatusHistoryRepository.saveAndFlush(userStatusHistory);

        // Get all the userStatusHistoryList
        restUserStatusHistoryMockMvc.perform(get("/api/user-status-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userStatusHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.intValue())))
            .andExpect(jsonPath("$.[*].modifiedTime").value(hasItem(DEFAULT_MODIFIED_TIME.toString())))
            .andExpect(jsonPath("$.[*].userid").value(hasItem(DEFAULT_USERID.intValue())))
            .andExpect(jsonPath("$.[*].withStatus").value(hasItem(DEFAULT_WITH_STATUS)))
            .andExpect(jsonPath("$.[*].toStatus").value(hasItem(DEFAULT_TO_STATUS)));
    }

    @Test
    @Transactional
    public void getUserStatusHistory() throws Exception {
        // Initialize the database
        userStatusHistoryRepository.saveAndFlush(userStatusHistory);

        // Get the userStatusHistory
        restUserStatusHistoryMockMvc.perform(get("/api/user-status-histories/{id}", userStatusHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userStatusHistory.getId().intValue()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.intValue()))
            .andExpect(jsonPath("$.modifiedTime").value(DEFAULT_MODIFIED_TIME.toString()))
            .andExpect(jsonPath("$.userid").value(DEFAULT_USERID.intValue()))
            .andExpect(jsonPath("$.withStatus").value(DEFAULT_WITH_STATUS))
            .andExpect(jsonPath("$.toStatus").value(DEFAULT_TO_STATUS));
    }

    @Test
    @Transactional
    public void getAllUserStatusHistoriesByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        userStatusHistoryRepository.saveAndFlush(userStatusHistory);

        // Get all the userStatusHistoryList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultUserStatusHistoryShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the userStatusHistoryList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultUserStatusHistoryShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllUserStatusHistoriesByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        userStatusHistoryRepository.saveAndFlush(userStatusHistory);

        // Get all the userStatusHistoryList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultUserStatusHistoryShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the userStatusHistoryList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultUserStatusHistoryShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllUserStatusHistoriesByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        userStatusHistoryRepository.saveAndFlush(userStatusHistory);

        // Get all the userStatusHistoryList where modifiedBy is not null
        defaultUserStatusHistoryShouldBeFound("modifiedBy.specified=true");

        // Get all the userStatusHistoryList where modifiedBy is null
        defaultUserStatusHistoryShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserStatusHistoriesByModifiedByIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userStatusHistoryRepository.saveAndFlush(userStatusHistory);

        // Get all the userStatusHistoryList where modifiedBy greater than or equals to DEFAULT_MODIFIED_BY
        defaultUserStatusHistoryShouldBeFound("modifiedBy.greaterOrEqualThan=" + DEFAULT_MODIFIED_BY);

        // Get all the userStatusHistoryList where modifiedBy greater than or equals to UPDATED_MODIFIED_BY
        defaultUserStatusHistoryShouldNotBeFound("modifiedBy.greaterOrEqualThan=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllUserStatusHistoriesByModifiedByIsLessThanSomething() throws Exception {
        // Initialize the database
        userStatusHistoryRepository.saveAndFlush(userStatusHistory);

        // Get all the userStatusHistoryList where modifiedBy less than or equals to DEFAULT_MODIFIED_BY
        defaultUserStatusHistoryShouldNotBeFound("modifiedBy.lessThan=" + DEFAULT_MODIFIED_BY);

        // Get all the userStatusHistoryList where modifiedBy less than or equals to UPDATED_MODIFIED_BY
        defaultUserStatusHistoryShouldBeFound("modifiedBy.lessThan=" + UPDATED_MODIFIED_BY);
    }


    @Test
    @Transactional
    public void getAllUserStatusHistoriesByModifiedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        userStatusHistoryRepository.saveAndFlush(userStatusHistory);

        // Get all the userStatusHistoryList where modifiedTime equals to DEFAULT_MODIFIED_TIME
        defaultUserStatusHistoryShouldBeFound("modifiedTime.equals=" + DEFAULT_MODIFIED_TIME);

        // Get all the userStatusHistoryList where modifiedTime equals to UPDATED_MODIFIED_TIME
        defaultUserStatusHistoryShouldNotBeFound("modifiedTime.equals=" + UPDATED_MODIFIED_TIME);
    }

    @Test
    @Transactional
    public void getAllUserStatusHistoriesByModifiedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        userStatusHistoryRepository.saveAndFlush(userStatusHistory);

        // Get all the userStatusHistoryList where modifiedTime in DEFAULT_MODIFIED_TIME or UPDATED_MODIFIED_TIME
        defaultUserStatusHistoryShouldBeFound("modifiedTime.in=" + DEFAULT_MODIFIED_TIME + "," + UPDATED_MODIFIED_TIME);

        // Get all the userStatusHistoryList where modifiedTime equals to UPDATED_MODIFIED_TIME
        defaultUserStatusHistoryShouldNotBeFound("modifiedTime.in=" + UPDATED_MODIFIED_TIME);
    }

    @Test
    @Transactional
    public void getAllUserStatusHistoriesByModifiedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        userStatusHistoryRepository.saveAndFlush(userStatusHistory);

        // Get all the userStatusHistoryList where modifiedTime is not null
        defaultUserStatusHistoryShouldBeFound("modifiedTime.specified=true");

        // Get all the userStatusHistoryList where modifiedTime is null
        defaultUserStatusHistoryShouldNotBeFound("modifiedTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserStatusHistoriesByUseridIsEqualToSomething() throws Exception {
        // Initialize the database
        userStatusHistoryRepository.saveAndFlush(userStatusHistory);

        // Get all the userStatusHistoryList where userid equals to DEFAULT_USERID
        defaultUserStatusHistoryShouldBeFound("userid.equals=" + DEFAULT_USERID);

        // Get all the userStatusHistoryList where userid equals to UPDATED_USERID
        defaultUserStatusHistoryShouldNotBeFound("userid.equals=" + UPDATED_USERID);
    }

    @Test
    @Transactional
    public void getAllUserStatusHistoriesByUseridIsInShouldWork() throws Exception {
        // Initialize the database
        userStatusHistoryRepository.saveAndFlush(userStatusHistory);

        // Get all the userStatusHistoryList where userid in DEFAULT_USERID or UPDATED_USERID
        defaultUserStatusHistoryShouldBeFound("userid.in=" + DEFAULT_USERID + "," + UPDATED_USERID);

        // Get all the userStatusHistoryList where userid equals to UPDATED_USERID
        defaultUserStatusHistoryShouldNotBeFound("userid.in=" + UPDATED_USERID);
    }

    @Test
    @Transactional
    public void getAllUserStatusHistoriesByUseridIsNullOrNotNull() throws Exception {
        // Initialize the database
        userStatusHistoryRepository.saveAndFlush(userStatusHistory);

        // Get all the userStatusHistoryList where userid is not null
        defaultUserStatusHistoryShouldBeFound("userid.specified=true");

        // Get all the userStatusHistoryList where userid is null
        defaultUserStatusHistoryShouldNotBeFound("userid.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserStatusHistoriesByUseridIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userStatusHistoryRepository.saveAndFlush(userStatusHistory);

        // Get all the userStatusHistoryList where userid greater than or equals to DEFAULT_USERID
        defaultUserStatusHistoryShouldBeFound("userid.greaterOrEqualThan=" + DEFAULT_USERID);

        // Get all the userStatusHistoryList where userid greater than or equals to UPDATED_USERID
        defaultUserStatusHistoryShouldNotBeFound("userid.greaterOrEqualThan=" + UPDATED_USERID);
    }

    @Test
    @Transactional
    public void getAllUserStatusHistoriesByUseridIsLessThanSomething() throws Exception {
        // Initialize the database
        userStatusHistoryRepository.saveAndFlush(userStatusHistory);

        // Get all the userStatusHistoryList where userid less than or equals to DEFAULT_USERID
        defaultUserStatusHistoryShouldNotBeFound("userid.lessThan=" + DEFAULT_USERID);

        // Get all the userStatusHistoryList where userid less than or equals to UPDATED_USERID
        defaultUserStatusHistoryShouldBeFound("userid.lessThan=" + UPDATED_USERID);
    }


    @Test
    @Transactional
    public void getAllUserStatusHistoriesByWithStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        userStatusHistoryRepository.saveAndFlush(userStatusHistory);

        // Get all the userStatusHistoryList where withStatus equals to DEFAULT_WITH_STATUS
        defaultUserStatusHistoryShouldBeFound("withStatus.equals=" + DEFAULT_WITH_STATUS);

        // Get all the userStatusHistoryList where withStatus equals to UPDATED_WITH_STATUS
        defaultUserStatusHistoryShouldNotBeFound("withStatus.equals=" + UPDATED_WITH_STATUS);
    }

    @Test
    @Transactional
    public void getAllUserStatusHistoriesByWithStatusIsInShouldWork() throws Exception {
        // Initialize the database
        userStatusHistoryRepository.saveAndFlush(userStatusHistory);

        // Get all the userStatusHistoryList where withStatus in DEFAULT_WITH_STATUS or UPDATED_WITH_STATUS
        defaultUserStatusHistoryShouldBeFound("withStatus.in=" + DEFAULT_WITH_STATUS + "," + UPDATED_WITH_STATUS);

        // Get all the userStatusHistoryList where withStatus equals to UPDATED_WITH_STATUS
        defaultUserStatusHistoryShouldNotBeFound("withStatus.in=" + UPDATED_WITH_STATUS);
    }

    @Test
    @Transactional
    public void getAllUserStatusHistoriesByWithStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        userStatusHistoryRepository.saveAndFlush(userStatusHistory);

        // Get all the userStatusHistoryList where withStatus is not null
        defaultUserStatusHistoryShouldBeFound("withStatus.specified=true");

        // Get all the userStatusHistoryList where withStatus is null
        defaultUserStatusHistoryShouldNotBeFound("withStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserStatusHistoriesByWithStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userStatusHistoryRepository.saveAndFlush(userStatusHistory);

        // Get all the userStatusHistoryList where withStatus greater than or equals to DEFAULT_WITH_STATUS
        defaultUserStatusHistoryShouldBeFound("withStatus.greaterOrEqualThan=" + DEFAULT_WITH_STATUS);

        // Get all the userStatusHistoryList where withStatus greater than or equals to UPDATED_WITH_STATUS
        defaultUserStatusHistoryShouldNotBeFound("withStatus.greaterOrEqualThan=" + UPDATED_WITH_STATUS);
    }

    @Test
    @Transactional
    public void getAllUserStatusHistoriesByWithStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        userStatusHistoryRepository.saveAndFlush(userStatusHistory);

        // Get all the userStatusHistoryList where withStatus less than or equals to DEFAULT_WITH_STATUS
        defaultUserStatusHistoryShouldNotBeFound("withStatus.lessThan=" + DEFAULT_WITH_STATUS);

        // Get all the userStatusHistoryList where withStatus less than or equals to UPDATED_WITH_STATUS
        defaultUserStatusHistoryShouldBeFound("withStatus.lessThan=" + UPDATED_WITH_STATUS);
    }


    @Test
    @Transactional
    public void getAllUserStatusHistoriesByToStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        userStatusHistoryRepository.saveAndFlush(userStatusHistory);

        // Get all the userStatusHistoryList where toStatus equals to DEFAULT_TO_STATUS
        defaultUserStatusHistoryShouldBeFound("toStatus.equals=" + DEFAULT_TO_STATUS);

        // Get all the userStatusHistoryList where toStatus equals to UPDATED_TO_STATUS
        defaultUserStatusHistoryShouldNotBeFound("toStatus.equals=" + UPDATED_TO_STATUS);
    }

    @Test
    @Transactional
    public void getAllUserStatusHistoriesByToStatusIsInShouldWork() throws Exception {
        // Initialize the database
        userStatusHistoryRepository.saveAndFlush(userStatusHistory);

        // Get all the userStatusHistoryList where toStatus in DEFAULT_TO_STATUS or UPDATED_TO_STATUS
        defaultUserStatusHistoryShouldBeFound("toStatus.in=" + DEFAULT_TO_STATUS + "," + UPDATED_TO_STATUS);

        // Get all the userStatusHistoryList where toStatus equals to UPDATED_TO_STATUS
        defaultUserStatusHistoryShouldNotBeFound("toStatus.in=" + UPDATED_TO_STATUS);
    }

    @Test
    @Transactional
    public void getAllUserStatusHistoriesByToStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        userStatusHistoryRepository.saveAndFlush(userStatusHistory);

        // Get all the userStatusHistoryList where toStatus is not null
        defaultUserStatusHistoryShouldBeFound("toStatus.specified=true");

        // Get all the userStatusHistoryList where toStatus is null
        defaultUserStatusHistoryShouldNotBeFound("toStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserStatusHistoriesByToStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userStatusHistoryRepository.saveAndFlush(userStatusHistory);

        // Get all the userStatusHistoryList where toStatus greater than or equals to DEFAULT_TO_STATUS
        defaultUserStatusHistoryShouldBeFound("toStatus.greaterOrEqualThan=" + DEFAULT_TO_STATUS);

        // Get all the userStatusHistoryList where toStatus greater than or equals to UPDATED_TO_STATUS
        defaultUserStatusHistoryShouldNotBeFound("toStatus.greaterOrEqualThan=" + UPDATED_TO_STATUS);
    }

    @Test
    @Transactional
    public void getAllUserStatusHistoriesByToStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        userStatusHistoryRepository.saveAndFlush(userStatusHistory);

        // Get all the userStatusHistoryList where toStatus less than or equals to DEFAULT_TO_STATUS
        defaultUserStatusHistoryShouldNotBeFound("toStatus.lessThan=" + DEFAULT_TO_STATUS);

        // Get all the userStatusHistoryList where toStatus less than or equals to UPDATED_TO_STATUS
        defaultUserStatusHistoryShouldBeFound("toStatus.lessThan=" + UPDATED_TO_STATUS);
    }


    @Test
    @Transactional
    public void getAllUserStatusHistoriesByUserAnnexIsEqualToSomething() throws Exception {
        // Initialize the database
        UserAnnex userAnnex = UserAnnexResourceIntTest.createEntity(em);
        em.persist(userAnnex);
        em.flush();
        userStatusHistory.setUserAnnex(userAnnex);
        userStatusHistoryRepository.saveAndFlush(userStatusHistory);
        Long userAnnexId = userAnnex.getId();

        // Get all the userStatusHistoryList where userAnnex equals to userAnnexId
        defaultUserStatusHistoryShouldBeFound("userAnnexId.equals=" + userAnnexId);

        // Get all the userStatusHistoryList where userAnnex equals to userAnnexId + 1
        defaultUserStatusHistoryShouldNotBeFound("userAnnexId.equals=" + (userAnnexId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultUserStatusHistoryShouldBeFound(String filter) throws Exception {
        restUserStatusHistoryMockMvc.perform(get("/api/user-status-histories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userStatusHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.intValue())))
            .andExpect(jsonPath("$.[*].modifiedTime").value(hasItem(DEFAULT_MODIFIED_TIME.toString())))
            .andExpect(jsonPath("$.[*].userid").value(hasItem(DEFAULT_USERID.intValue())))
            .andExpect(jsonPath("$.[*].withStatus").value(hasItem(DEFAULT_WITH_STATUS)))
            .andExpect(jsonPath("$.[*].toStatus").value(hasItem(DEFAULT_TO_STATUS)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultUserStatusHistoryShouldNotBeFound(String filter) throws Exception {
        restUserStatusHistoryMockMvc.perform(get("/api/user-status-histories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingUserStatusHistory() throws Exception {
        // Get the userStatusHistory
        restUserStatusHistoryMockMvc.perform(get("/api/user-status-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserStatusHistory() throws Exception {
        // Initialize the database
        userStatusHistoryRepository.saveAndFlush(userStatusHistory);
        int databaseSizeBeforeUpdate = userStatusHistoryRepository.findAll().size();

        // Update the userStatusHistory
        UserStatusHistory updatedUserStatusHistory = userStatusHistoryRepository.findOne(userStatusHistory.getId());
        // Disconnect from session so that the updates on updatedUserStatusHistory are not directly saved in db
        em.detach(updatedUserStatusHistory);
        updatedUserStatusHistory
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedTime(UPDATED_MODIFIED_TIME)
            .userid(UPDATED_USERID)
            .withStatus(UPDATED_WITH_STATUS)
            .toStatus(UPDATED_TO_STATUS);
        UserStatusHistoryDTO userStatusHistoryDTO = userStatusHistoryMapper.toDto(updatedUserStatusHistory);

        restUserStatusHistoryMockMvc.perform(put("/api/user-status-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userStatusHistoryDTO)))
            .andExpect(status().isOk());

        // Validate the UserStatusHistory in the database
        List<UserStatusHistory> userStatusHistoryList = userStatusHistoryRepository.findAll();
        assertThat(userStatusHistoryList).hasSize(databaseSizeBeforeUpdate);
        UserStatusHistory testUserStatusHistory = userStatusHistoryList.get(userStatusHistoryList.size() - 1);
        assertThat(testUserStatusHistory.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testUserStatusHistory.getModifiedTime()).isEqualTo(UPDATED_MODIFIED_TIME);
        assertThat(testUserStatusHistory.getUserid()).isEqualTo(UPDATED_USERID);
        assertThat(testUserStatusHistory.getWithStatus()).isEqualTo(UPDATED_WITH_STATUS);
        assertThat(testUserStatusHistory.getToStatus()).isEqualTo(UPDATED_TO_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingUserStatusHistory() throws Exception {
        int databaseSizeBeforeUpdate = userStatusHistoryRepository.findAll().size();

        // Create the UserStatusHistory
        UserStatusHistoryDTO userStatusHistoryDTO = userStatusHistoryMapper.toDto(userStatusHistory);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserStatusHistoryMockMvc.perform(put("/api/user-status-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userStatusHistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the UserStatusHistory in the database
        List<UserStatusHistory> userStatusHistoryList = userStatusHistoryRepository.findAll();
        assertThat(userStatusHistoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserStatusHistory() throws Exception {
        // Initialize the database
        userStatusHistoryRepository.saveAndFlush(userStatusHistory);
        int databaseSizeBeforeDelete = userStatusHistoryRepository.findAll().size();

        // Get the userStatusHistory
        restUserStatusHistoryMockMvc.perform(delete("/api/user-status-histories/{id}", userStatusHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserStatusHistory> userStatusHistoryList = userStatusHistoryRepository.findAll();
        assertThat(userStatusHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserStatusHistory.class);
        UserStatusHistory userStatusHistory1 = new UserStatusHistory();
        userStatusHistory1.setId(1L);
        UserStatusHistory userStatusHistory2 = new UserStatusHistory();
        userStatusHistory2.setId(userStatusHistory1.getId());
        assertThat(userStatusHistory1).isEqualTo(userStatusHistory2);
        userStatusHistory2.setId(2L);
        assertThat(userStatusHistory1).isNotEqualTo(userStatusHistory2);
        userStatusHistory1.setId(null);
        assertThat(userStatusHistory1).isNotEqualTo(userStatusHistory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserStatusHistoryDTO.class);
        UserStatusHistoryDTO userStatusHistoryDTO1 = new UserStatusHistoryDTO();
        userStatusHistoryDTO1.setId(1L);
        UserStatusHistoryDTO userStatusHistoryDTO2 = new UserStatusHistoryDTO();
        assertThat(userStatusHistoryDTO1).isNotEqualTo(userStatusHistoryDTO2);
        userStatusHistoryDTO2.setId(userStatusHistoryDTO1.getId());
        assertThat(userStatusHistoryDTO1).isEqualTo(userStatusHistoryDTO2);
        userStatusHistoryDTO2.setId(2L);
        assertThat(userStatusHistoryDTO1).isNotEqualTo(userStatusHistoryDTO2);
        userStatusHistoryDTO1.setId(null);
        assertThat(userStatusHistoryDTO1).isNotEqualTo(userStatusHistoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userStatusHistoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userStatusHistoryMapper.fromId(null)).isNull();
    }
}
