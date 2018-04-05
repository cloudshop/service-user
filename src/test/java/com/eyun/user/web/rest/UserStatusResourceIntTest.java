package com.eyun.user.web.rest;

import com.eyun.user.UserApp;

import com.eyun.user.config.SecurityBeanOverrideConfiguration;

import com.eyun.user.domain.UserStatus;
import com.eyun.user.repository.UserStatusRepository;
import com.eyun.user.service.UserStatusService;
import com.eyun.user.service.dto.UserStatusDTO;
import com.eyun.user.service.mapper.UserStatusMapper;
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
 * Test class for the UserStatusResource REST controller.
 *
 * @see UserStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserApp.class, SecurityBeanOverrideConfiguration.class})
public class UserStatusResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private UserStatusRepository userStatusRepository;

    @Autowired
    private UserStatusMapper userStatusMapper;

    @Autowired
    private UserStatusService userStatusService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserStatusMockMvc;

    private UserStatus userStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserStatusResource userStatusResource = new UserStatusResource(userStatusService);
        this.restUserStatusMockMvc = MockMvcBuilders.standaloneSetup(userStatusResource)
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
    public static UserStatus createEntity(EntityManager em) {
        UserStatus userStatus = new UserStatus()
            .name(DEFAULT_NAME);
        return userStatus;
    }

    @Before
    public void initTest() {
        userStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserStatus() throws Exception {
        int databaseSizeBeforeCreate = userStatusRepository.findAll().size();

        // Create the UserStatus
        UserStatusDTO userStatusDTO = userStatusMapper.toDto(userStatus);
        restUserStatusMockMvc.perform(post("/api/user-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the UserStatus in the database
        List<UserStatus> userStatusList = userStatusRepository.findAll();
        assertThat(userStatusList).hasSize(databaseSizeBeforeCreate + 1);
        UserStatus testUserStatus = userStatusList.get(userStatusList.size() - 1);
        assertThat(testUserStatus.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createUserStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userStatusRepository.findAll().size();

        // Create the UserStatus with an existing ID
        userStatus.setId(1L);
        UserStatusDTO userStatusDTO = userStatusMapper.toDto(userStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserStatusMockMvc.perform(post("/api/user-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserStatus in the database
        List<UserStatus> userStatusList = userStatusRepository.findAll();
        assertThat(userStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserStatuses() throws Exception {
        // Initialize the database
        userStatusRepository.saveAndFlush(userStatus);

        // Get all the userStatusList
        restUserStatusMockMvc.perform(get("/api/user-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getUserStatus() throws Exception {
        // Initialize the database
        userStatusRepository.saveAndFlush(userStatus);

        // Get the userStatus
        restUserStatusMockMvc.perform(get("/api/user-statuses/{id}", userStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userStatus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserStatus() throws Exception {
        // Get the userStatus
        restUserStatusMockMvc.perform(get("/api/user-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserStatus() throws Exception {
        // Initialize the database
        userStatusRepository.saveAndFlush(userStatus);
        int databaseSizeBeforeUpdate = userStatusRepository.findAll().size();

        // Update the userStatus
        UserStatus updatedUserStatus = userStatusRepository.findOne(userStatus.getId());
        // Disconnect from session so that the updates on updatedUserStatus are not directly saved in db
        em.detach(updatedUserStatus);
        updatedUserStatus
            .name(UPDATED_NAME);
        UserStatusDTO userStatusDTO = userStatusMapper.toDto(updatedUserStatus);

        restUserStatusMockMvc.perform(put("/api/user-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userStatusDTO)))
            .andExpect(status().isOk());

        // Validate the UserStatus in the database
        List<UserStatus> userStatusList = userStatusRepository.findAll();
        assertThat(userStatusList).hasSize(databaseSizeBeforeUpdate);
        UserStatus testUserStatus = userStatusList.get(userStatusList.size() - 1);
        assertThat(testUserStatus.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingUserStatus() throws Exception {
        int databaseSizeBeforeUpdate = userStatusRepository.findAll().size();

        // Create the UserStatus
        UserStatusDTO userStatusDTO = userStatusMapper.toDto(userStatus);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserStatusMockMvc.perform(put("/api/user-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the UserStatus in the database
        List<UserStatus> userStatusList = userStatusRepository.findAll();
        assertThat(userStatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserStatus() throws Exception {
        // Initialize the database
        userStatusRepository.saveAndFlush(userStatus);
        int databaseSizeBeforeDelete = userStatusRepository.findAll().size();

        // Get the userStatus
        restUserStatusMockMvc.perform(delete("/api/user-statuses/{id}", userStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserStatus> userStatusList = userStatusRepository.findAll();
        assertThat(userStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserStatus.class);
        UserStatus userStatus1 = new UserStatus();
        userStatus1.setId(1L);
        UserStatus userStatus2 = new UserStatus();
        userStatus2.setId(userStatus1.getId());
        assertThat(userStatus1).isEqualTo(userStatus2);
        userStatus2.setId(2L);
        assertThat(userStatus1).isNotEqualTo(userStatus2);
        userStatus1.setId(null);
        assertThat(userStatus1).isNotEqualTo(userStatus2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserStatusDTO.class);
        UserStatusDTO userStatusDTO1 = new UserStatusDTO();
        userStatusDTO1.setId(1L);
        UserStatusDTO userStatusDTO2 = new UserStatusDTO();
        assertThat(userStatusDTO1).isNotEqualTo(userStatusDTO2);
        userStatusDTO2.setId(userStatusDTO1.getId());
        assertThat(userStatusDTO1).isEqualTo(userStatusDTO2);
        userStatusDTO2.setId(2L);
        assertThat(userStatusDTO1).isNotEqualTo(userStatusDTO2);
        userStatusDTO1.setId(null);
        assertThat(userStatusDTO1).isNotEqualTo(userStatusDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userStatusMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userStatusMapper.fromId(null)).isNull();
    }
}
