package com.eyun.user.web.rest;

import com.eyun.user.UserApp;

import com.eyun.user.config.SecurityBeanOverrideConfiguration;

import com.eyun.user.domain.UserStatusHistory;
import com.eyun.user.repository.UserStatusHistoryRepository;
import com.eyun.user.service.UserStatusHistoryService;
import com.eyun.user.service.dto.UserStatusHistoryDTO;
import com.eyun.user.service.mapper.UserStatusHistoryMapper;
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

    @Autowired
    private UserStatusHistoryRepository userStatusHistoryRepository;

    @Autowired
    private UserStatusHistoryMapper userStatusHistoryMapper;

    @Autowired
    private UserStatusHistoryService userStatusHistoryService;

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
        final UserStatusHistoryResource userStatusHistoryResource = new UserStatusHistoryResource(userStatusHistoryService);
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
            .modifiedTime(DEFAULT_MODIFIED_TIME);
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
            .andExpect(jsonPath("$.[*].modifiedTime").value(hasItem(DEFAULT_MODIFIED_TIME.toString())));
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
            .andExpect(jsonPath("$.modifiedTime").value(DEFAULT_MODIFIED_TIME.toString()));
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
            .modifiedTime(UPDATED_MODIFIED_TIME);
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
