package com.eyun.user.web.rest;

import com.eyun.user.UserApp;

import com.eyun.user.config.SecurityBeanOverrideConfiguration;

import com.eyun.user.domain.UserAnnex;
import com.eyun.user.repository.UserAnnexRepository;
import com.eyun.user.service.UserAnnexService;
import com.eyun.user.service.dto.UserAnnexDTO;
import com.eyun.user.service.mapper.UserAnnexMapper;
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
 * Test class for the UserAnnexResource REST controller.
 *
 * @see UserAnnexResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserApp.class, SecurityBeanOverrideConfiguration.class})
public class UserAnnexResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_NICKNAME = "AAAAAAAAAA";
    private static final String UPDATED_NICKNAME = "BBBBBBBBBB";

    private static final String DEFAULT_AVATAR = "AAAAAAAAAA";
    private static final String UPDATED_AVATAR = "BBBBBBBBBB";

    @Autowired
    private UserAnnexRepository userAnnexRepository;

    @Autowired
    private UserAnnexMapper userAnnexMapper;

    @Autowired
    private UserAnnexService userAnnexService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserAnnexMockMvc;

    private UserAnnex userAnnex;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserAnnexResource userAnnexResource = new UserAnnexResource(userAnnexService);
        this.restUserAnnexMockMvc = MockMvcBuilders.standaloneSetup(userAnnexResource)
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
    public static UserAnnex createEntity(EntityManager em) {
        UserAnnex userAnnex = new UserAnnex()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .nickname(DEFAULT_NICKNAME)
            .avatar(DEFAULT_AVATAR);
        return userAnnex;
    }

    @Before
    public void initTest() {
        userAnnex = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserAnnex() throws Exception {
        int databaseSizeBeforeCreate = userAnnexRepository.findAll().size();

        // Create the UserAnnex
        UserAnnexDTO userAnnexDTO = userAnnexMapper.toDto(userAnnex);
        restUserAnnexMockMvc.perform(post("/api/user-annexes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAnnexDTO)))
            .andExpect(status().isCreated());

        // Validate the UserAnnex in the database
        List<UserAnnex> userAnnexList = userAnnexRepository.findAll();
        assertThat(userAnnexList).hasSize(databaseSizeBeforeCreate + 1);
        UserAnnex testUserAnnex = userAnnexList.get(userAnnexList.size() - 1);
        assertThat(testUserAnnex.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testUserAnnex.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testUserAnnex.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testUserAnnex.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testUserAnnex.getNickname()).isEqualTo(DEFAULT_NICKNAME);
        assertThat(testUserAnnex.getAvatar()).isEqualTo(DEFAULT_AVATAR);
    }

    @Test
    @Transactional
    public void createUserAnnexWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userAnnexRepository.findAll().size();

        // Create the UserAnnex with an existing ID
        userAnnex.setId(1L);
        UserAnnexDTO userAnnexDTO = userAnnexMapper.toDto(userAnnex);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserAnnexMockMvc.perform(post("/api/user-annexes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAnnexDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserAnnex in the database
        List<UserAnnex> userAnnexList = userAnnexRepository.findAll();
        assertThat(userAnnexList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserAnnexes() throws Exception {
        // Initialize the database
        userAnnexRepository.saveAndFlush(userAnnex);

        // Get all the userAnnexList
        restUserAnnexMockMvc.perform(get("/api/user-annexes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAnnex.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].nickname").value(hasItem(DEFAULT_NICKNAME.toString())))
            .andExpect(jsonPath("$.[*].avatar").value(hasItem(DEFAULT_AVATAR.toString())));
    }

    @Test
    @Transactional
    public void getUserAnnex() throws Exception {
        // Initialize the database
        userAnnexRepository.saveAndFlush(userAnnex);

        // Get the userAnnex
        restUserAnnexMockMvc.perform(get("/api/user-annexes/{id}", userAnnex.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userAnnex.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.nickname").value(DEFAULT_NICKNAME.toString()))
            .andExpect(jsonPath("$.avatar").value(DEFAULT_AVATAR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserAnnex() throws Exception {
        // Get the userAnnex
        restUserAnnexMockMvc.perform(get("/api/user-annexes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserAnnex() throws Exception {
        // Initialize the database
        userAnnexRepository.saveAndFlush(userAnnex);
        int databaseSizeBeforeUpdate = userAnnexRepository.findAll().size();

        // Update the userAnnex
        UserAnnex updatedUserAnnex = userAnnexRepository.findOne(userAnnex.getId());
        // Disconnect from session so that the updates on updatedUserAnnex are not directly saved in db
        em.detach(updatedUserAnnex);
        updatedUserAnnex
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .nickname(UPDATED_NICKNAME)
            .avatar(UPDATED_AVATAR);
        UserAnnexDTO userAnnexDTO = userAnnexMapper.toDto(updatedUserAnnex);

        restUserAnnexMockMvc.perform(put("/api/user-annexes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAnnexDTO)))
            .andExpect(status().isOk());

        // Validate the UserAnnex in the database
        List<UserAnnex> userAnnexList = userAnnexRepository.findAll();
        assertThat(userAnnexList).hasSize(databaseSizeBeforeUpdate);
        UserAnnex testUserAnnex = userAnnexList.get(userAnnexList.size() - 1);
        assertThat(testUserAnnex.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testUserAnnex.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testUserAnnex.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testUserAnnex.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testUserAnnex.getNickname()).isEqualTo(UPDATED_NICKNAME);
        assertThat(testUserAnnex.getAvatar()).isEqualTo(UPDATED_AVATAR);
    }

    @Test
    @Transactional
    public void updateNonExistingUserAnnex() throws Exception {
        int databaseSizeBeforeUpdate = userAnnexRepository.findAll().size();

        // Create the UserAnnex
        UserAnnexDTO userAnnexDTO = userAnnexMapper.toDto(userAnnex);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserAnnexMockMvc.perform(put("/api/user-annexes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAnnexDTO)))
            .andExpect(status().isCreated());

        // Validate the UserAnnex in the database
        List<UserAnnex> userAnnexList = userAnnexRepository.findAll();
        assertThat(userAnnexList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserAnnex() throws Exception {
        // Initialize the database
        userAnnexRepository.saveAndFlush(userAnnex);
        int databaseSizeBeforeDelete = userAnnexRepository.findAll().size();

        // Get the userAnnex
        restUserAnnexMockMvc.perform(delete("/api/user-annexes/{id}", userAnnex.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserAnnex> userAnnexList = userAnnexRepository.findAll();
        assertThat(userAnnexList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAnnex.class);
        UserAnnex userAnnex1 = new UserAnnex();
        userAnnex1.setId(1L);
        UserAnnex userAnnex2 = new UserAnnex();
        userAnnex2.setId(userAnnex1.getId());
        assertThat(userAnnex1).isEqualTo(userAnnex2);
        userAnnex2.setId(2L);
        assertThat(userAnnex1).isNotEqualTo(userAnnex2);
        userAnnex1.setId(null);
        assertThat(userAnnex1).isNotEqualTo(userAnnex2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAnnexDTO.class);
        UserAnnexDTO userAnnexDTO1 = new UserAnnexDTO();
        userAnnexDTO1.setId(1L);
        UserAnnexDTO userAnnexDTO2 = new UserAnnexDTO();
        assertThat(userAnnexDTO1).isNotEqualTo(userAnnexDTO2);
        userAnnexDTO2.setId(userAnnexDTO1.getId());
        assertThat(userAnnexDTO1).isEqualTo(userAnnexDTO2);
        userAnnexDTO2.setId(2L);
        assertThat(userAnnexDTO1).isNotEqualTo(userAnnexDTO2);
        userAnnexDTO1.setId(null);
        assertThat(userAnnexDTO1).isNotEqualTo(userAnnexDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userAnnexMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userAnnexMapper.fromId(null)).isNull();
    }
}
