package com.eyun.user.web.rest;

import com.eyun.user.UserApp;

import com.eyun.user.config.SecurityBeanOverrideConfiguration;

import com.eyun.user.domain.UserType;
import com.eyun.user.repository.UserTypeRepository;
import com.eyun.user.service.UserTypeService;
import com.eyun.user.service.dto.UserTypeDTO;
import com.eyun.user.service.mapper.UserTypeMapper;
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
 * Test class for the UserTypeResource REST controller.
 *
 * @see UserTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserApp.class, SecurityBeanOverrideConfiguration.class})
public class UserTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    @Autowired
    private UserTypeRepository userTypeRepository;

    @Autowired
    private UserTypeMapper userTypeMapper;

    @Autowired
    private UserTypeService userTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserTypeMockMvc;

    private UserType userType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserTypeResource userTypeResource = new UserTypeResource(userTypeService);
        this.restUserTypeMockMvc = MockMvcBuilders.standaloneSetup(userTypeResource)
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
    public static UserType createEntity(EntityManager em) {
        UserType userType = new UserType()
            .name(DEFAULT_NAME)
            .desc(DEFAULT_DESC);
        return userType;
    }

    @Before
    public void initTest() {
        userType = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserType() throws Exception {
        int databaseSizeBeforeCreate = userTypeRepository.findAll().size();

        // Create the UserType
        UserTypeDTO userTypeDTO = userTypeMapper.toDto(userType);
        restUserTypeMockMvc.perform(post("/api/user-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the UserType in the database
        List<UserType> userTypeList = userTypeRepository.findAll();
        assertThat(userTypeList).hasSize(databaseSizeBeforeCreate + 1);
        UserType testUserType = userTypeList.get(userTypeList.size() - 1);
        assertThat(testUserType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUserType.getDesc()).isEqualTo(DEFAULT_DESC);
    }

    @Test
    @Transactional
    public void createUserTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userTypeRepository.findAll().size();

        // Create the UserType with an existing ID
        userType.setId(1L);
        UserTypeDTO userTypeDTO = userTypeMapper.toDto(userType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserTypeMockMvc.perform(post("/api/user-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserType in the database
        List<UserType> userTypeList = userTypeRepository.findAll();
        assertThat(userTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserTypes() throws Exception {
        // Initialize the database
        userTypeRepository.saveAndFlush(userType);

        // Get all the userTypeList
        restUserTypeMockMvc.perform(get("/api/user-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC.toString())));
    }

    @Test
    @Transactional
    public void getUserType() throws Exception {
        // Initialize the database
        userTypeRepository.saveAndFlush(userType);

        // Get the userType
        restUserTypeMockMvc.perform(get("/api/user-types/{id}", userType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserType() throws Exception {
        // Get the userType
        restUserTypeMockMvc.perform(get("/api/user-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserType() throws Exception {
        // Initialize the database
        userTypeRepository.saveAndFlush(userType);
        int databaseSizeBeforeUpdate = userTypeRepository.findAll().size();

        // Update the userType
        UserType updatedUserType = userTypeRepository.findOne(userType.getId());
        // Disconnect from session so that the updates on updatedUserType are not directly saved in db
        em.detach(updatedUserType);
        updatedUserType
            .name(UPDATED_NAME)
            .desc(UPDATED_DESC);
        UserTypeDTO userTypeDTO = userTypeMapper.toDto(updatedUserType);

        restUserTypeMockMvc.perform(put("/api/user-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userTypeDTO)))
            .andExpect(status().isOk());

        // Validate the UserType in the database
        List<UserType> userTypeList = userTypeRepository.findAll();
        assertThat(userTypeList).hasSize(databaseSizeBeforeUpdate);
        UserType testUserType = userTypeList.get(userTypeList.size() - 1);
        assertThat(testUserType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUserType.getDesc()).isEqualTo(UPDATED_DESC);
    }

    @Test
    @Transactional
    public void updateNonExistingUserType() throws Exception {
        int databaseSizeBeforeUpdate = userTypeRepository.findAll().size();

        // Create the UserType
        UserTypeDTO userTypeDTO = userTypeMapper.toDto(userType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserTypeMockMvc.perform(put("/api/user-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the UserType in the database
        List<UserType> userTypeList = userTypeRepository.findAll();
        assertThat(userTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserType() throws Exception {
        // Initialize the database
        userTypeRepository.saveAndFlush(userType);
        int databaseSizeBeforeDelete = userTypeRepository.findAll().size();

        // Get the userType
        restUserTypeMockMvc.perform(delete("/api/user-types/{id}", userType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserType> userTypeList = userTypeRepository.findAll();
        assertThat(userTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserType.class);
        UserType userType1 = new UserType();
        userType1.setId(1L);
        UserType userType2 = new UserType();
        userType2.setId(userType1.getId());
        assertThat(userType1).isEqualTo(userType2);
        userType2.setId(2L);
        assertThat(userType1).isNotEqualTo(userType2);
        userType1.setId(null);
        assertThat(userType1).isNotEqualTo(userType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserTypeDTO.class);
        UserTypeDTO userTypeDTO1 = new UserTypeDTO();
        userTypeDTO1.setId(1L);
        UserTypeDTO userTypeDTO2 = new UserTypeDTO();
        assertThat(userTypeDTO1).isNotEqualTo(userTypeDTO2);
        userTypeDTO2.setId(userTypeDTO1.getId());
        assertThat(userTypeDTO1).isEqualTo(userTypeDTO2);
        userTypeDTO2.setId(2L);
        assertThat(userTypeDTO1).isNotEqualTo(userTypeDTO2);
        userTypeDTO1.setId(null);
        assertThat(userTypeDTO1).isNotEqualTo(userTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userTypeMapper.fromId(null)).isNull();
    }
}
