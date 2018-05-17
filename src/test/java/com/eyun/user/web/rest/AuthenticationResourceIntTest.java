package com.eyun.user.web.rest;

import com.eyun.user.UserApp;

import com.eyun.user.config.SecurityBeanOverrideConfiguration;

import com.eyun.user.domain.Authentication;
import com.eyun.user.repository.AuthenticationRepository;
import com.eyun.user.service.AuthenticationService;
import com.eyun.user.service.dto.AuthenticationDTO;
import com.eyun.user.service.mapper.AuthenticationMapper;
import com.eyun.user.web.rest.errors.ExceptionTranslator;
import com.eyun.user.service.dto.AuthenticationCriteria;
import com.eyun.user.service.AuthenticationQueryService;

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
 * Test class for the AuthenticationResource REST controller.
 *
 * @see AuthenticationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserApp.class, SecurityBeanOverrideConfiguration.class})
public class AuthenticationResourceIntTest {

    private static final String DEFAULT_REAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_REAL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IDNUBER = "AAAAAAAAAA";
    private static final String UPDATED_IDNUBER = "BBBBBBBBBB";

    private static final String DEFAULT_FRONT_IMG = "AAAAAAAAAA";
    private static final String UPDATED_FRONT_IMG = "BBBBBBBBBB";

    private static final String DEFAULT_REVERSE_IMG = "AAAAAAAAAA";
    private static final String UPDATED_REVERSE_IMG = "BBBBBBBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final String DEFAULT_STATUS_STRING = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_STRING = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private AuthenticationMapper authenticationMapper;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AuthenticationQueryService authenticationQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAuthenticationMockMvc;

    private Authentication authentication;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AuthenticationResource authenticationResource = new AuthenticationResource(authenticationService, authenticationQueryService);
        this.restAuthenticationMockMvc = MockMvcBuilders.standaloneSetup(authenticationResource)
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
    public static Authentication createEntity(EntityManager em) {
        Authentication authentication = new Authentication()
            .realName(DEFAULT_REAL_NAME)
            .idnuber(DEFAULT_IDNUBER)
            .frontImg(DEFAULT_FRONT_IMG)
            .reverseImg(DEFAULT_REVERSE_IMG)
            .status(DEFAULT_STATUS)
            .statusString(DEFAULT_STATUS_STRING)
            .createdTime(DEFAULT_CREATED_TIME)
            .updatedTime(DEFAULT_UPDATED_TIME);
        return authentication;
    }

    @Before
    public void initTest() {
        authentication = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuthentication() throws Exception {
        int databaseSizeBeforeCreate = authenticationRepository.findAll().size();

        // Create the Authentication
        AuthenticationDTO authenticationDTO = authenticationMapper.toDto(authentication);
        restAuthenticationMockMvc.perform(post("/api/authentications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(authenticationDTO)))
            .andExpect(status().isCreated());

        // Validate the Authentication in the database
        List<Authentication> authenticationList = authenticationRepository.findAll();
        assertThat(authenticationList).hasSize(databaseSizeBeforeCreate + 1);
        Authentication testAuthentication = authenticationList.get(authenticationList.size() - 1);
        assertThat(testAuthentication.getRealName()).isEqualTo(DEFAULT_REAL_NAME);
        assertThat(testAuthentication.getIdnuber()).isEqualTo(DEFAULT_IDNUBER);
        assertThat(testAuthentication.getFrontImg()).isEqualTo(DEFAULT_FRONT_IMG);
        assertThat(testAuthentication.getReverseImg()).isEqualTo(DEFAULT_REVERSE_IMG);
        assertThat(testAuthentication.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAuthentication.getStatusString()).isEqualTo(DEFAULT_STATUS_STRING);
        assertThat(testAuthentication.getCreatedTime()).isEqualTo(DEFAULT_CREATED_TIME);
        assertThat(testAuthentication.getUpdatedTime()).isEqualTo(DEFAULT_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void createAuthenticationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = authenticationRepository.findAll().size();

        // Create the Authentication with an existing ID
        authentication.setId(1L);
        AuthenticationDTO authenticationDTO = authenticationMapper.toDto(authentication);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuthenticationMockMvc.perform(post("/api/authentications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(authenticationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Authentication in the database
        List<Authentication> authenticationList = authenticationRepository.findAll();
        assertThat(authenticationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAuthentications() throws Exception {
        // Initialize the database
        authenticationRepository.saveAndFlush(authentication);

        // Get all the authenticationList
        restAuthenticationMockMvc.perform(get("/api/authentications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(authentication.getId().intValue())))
            .andExpect(jsonPath("$.[*].realName").value(hasItem(DEFAULT_REAL_NAME.toString())))
            .andExpect(jsonPath("$.[*].idnuber").value(hasItem(DEFAULT_IDNUBER.toString())))
            .andExpect(jsonPath("$.[*].frontImg").value(hasItem(DEFAULT_FRONT_IMG.toString())))
            .andExpect(jsonPath("$.[*].reverseImg").value(hasItem(DEFAULT_REVERSE_IMG.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].statusString").value(hasItem(DEFAULT_STATUS_STRING.toString())))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    public void getAuthentication() throws Exception {
        // Initialize the database
        authenticationRepository.saveAndFlush(authentication);

        // Get the authentication
        restAuthenticationMockMvc.perform(get("/api/authentications/{id}", authentication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(authentication.getId().intValue()))
            .andExpect(jsonPath("$.realName").value(DEFAULT_REAL_NAME.toString()))
            .andExpect(jsonPath("$.idnuber").value(DEFAULT_IDNUBER.toString()))
            .andExpect(jsonPath("$.frontImg").value(DEFAULT_FRONT_IMG.toString()))
            .andExpect(jsonPath("$.reverseImg").value(DEFAULT_REVERSE_IMG.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.statusString").value(DEFAULT_STATUS_STRING.toString()))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    public void getAllAuthenticationsByRealNameIsEqualToSomething() throws Exception {
        // Initialize the database
        authenticationRepository.saveAndFlush(authentication);

        // Get all the authenticationList where realName equals to DEFAULT_REAL_NAME
        defaultAuthenticationShouldBeFound("realName.equals=" + DEFAULT_REAL_NAME);

        // Get all the authenticationList where realName equals to UPDATED_REAL_NAME
        defaultAuthenticationShouldNotBeFound("realName.equals=" + UPDATED_REAL_NAME);
    }

    @Test
    @Transactional
    public void getAllAuthenticationsByRealNameIsInShouldWork() throws Exception {
        // Initialize the database
        authenticationRepository.saveAndFlush(authentication);

        // Get all the authenticationList where realName in DEFAULT_REAL_NAME or UPDATED_REAL_NAME
        defaultAuthenticationShouldBeFound("realName.in=" + DEFAULT_REAL_NAME + "," + UPDATED_REAL_NAME);

        // Get all the authenticationList where realName equals to UPDATED_REAL_NAME
        defaultAuthenticationShouldNotBeFound("realName.in=" + UPDATED_REAL_NAME);
    }

    @Test
    @Transactional
    public void getAllAuthenticationsByRealNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        authenticationRepository.saveAndFlush(authentication);

        // Get all the authenticationList where realName is not null
        defaultAuthenticationShouldBeFound("realName.specified=true");

        // Get all the authenticationList where realName is null
        defaultAuthenticationShouldNotBeFound("realName.specified=false");
    }

    @Test
    @Transactional
    public void getAllAuthenticationsByIdnuberIsEqualToSomething() throws Exception {
        // Initialize the database
        authenticationRepository.saveAndFlush(authentication);

        // Get all the authenticationList where idnuber equals to DEFAULT_IDNUBER
        defaultAuthenticationShouldBeFound("idnuber.equals=" + DEFAULT_IDNUBER);

        // Get all the authenticationList where idnuber equals to UPDATED_IDNUBER
        defaultAuthenticationShouldNotBeFound("idnuber.equals=" + UPDATED_IDNUBER);
    }

    @Test
    @Transactional
    public void getAllAuthenticationsByIdnuberIsInShouldWork() throws Exception {
        // Initialize the database
        authenticationRepository.saveAndFlush(authentication);

        // Get all the authenticationList where idnuber in DEFAULT_IDNUBER or UPDATED_IDNUBER
        defaultAuthenticationShouldBeFound("idnuber.in=" + DEFAULT_IDNUBER + "," + UPDATED_IDNUBER);

        // Get all the authenticationList where idnuber equals to UPDATED_IDNUBER
        defaultAuthenticationShouldNotBeFound("idnuber.in=" + UPDATED_IDNUBER);
    }

    @Test
    @Transactional
    public void getAllAuthenticationsByIdnuberIsNullOrNotNull() throws Exception {
        // Initialize the database
        authenticationRepository.saveAndFlush(authentication);

        // Get all the authenticationList where idnuber is not null
        defaultAuthenticationShouldBeFound("idnuber.specified=true");

        // Get all the authenticationList where idnuber is null
        defaultAuthenticationShouldNotBeFound("idnuber.specified=false");
    }

    @Test
    @Transactional
    public void getAllAuthenticationsByFrontImgIsEqualToSomething() throws Exception {
        // Initialize the database
        authenticationRepository.saveAndFlush(authentication);

        // Get all the authenticationList where frontImg equals to DEFAULT_FRONT_IMG
        defaultAuthenticationShouldBeFound("frontImg.equals=" + DEFAULT_FRONT_IMG);

        // Get all the authenticationList where frontImg equals to UPDATED_FRONT_IMG
        defaultAuthenticationShouldNotBeFound("frontImg.equals=" + UPDATED_FRONT_IMG);
    }

    @Test
    @Transactional
    public void getAllAuthenticationsByFrontImgIsInShouldWork() throws Exception {
        // Initialize the database
        authenticationRepository.saveAndFlush(authentication);

        // Get all the authenticationList where frontImg in DEFAULT_FRONT_IMG or UPDATED_FRONT_IMG
        defaultAuthenticationShouldBeFound("frontImg.in=" + DEFAULT_FRONT_IMG + "," + UPDATED_FRONT_IMG);

        // Get all the authenticationList where frontImg equals to UPDATED_FRONT_IMG
        defaultAuthenticationShouldNotBeFound("frontImg.in=" + UPDATED_FRONT_IMG);
    }

    @Test
    @Transactional
    public void getAllAuthenticationsByFrontImgIsNullOrNotNull() throws Exception {
        // Initialize the database
        authenticationRepository.saveAndFlush(authentication);

        // Get all the authenticationList where frontImg is not null
        defaultAuthenticationShouldBeFound("frontImg.specified=true");

        // Get all the authenticationList where frontImg is null
        defaultAuthenticationShouldNotBeFound("frontImg.specified=false");
    }

    @Test
    @Transactional
    public void getAllAuthenticationsByReverseImgIsEqualToSomething() throws Exception {
        // Initialize the database
        authenticationRepository.saveAndFlush(authentication);

        // Get all the authenticationList where reverseImg equals to DEFAULT_REVERSE_IMG
        defaultAuthenticationShouldBeFound("reverseImg.equals=" + DEFAULT_REVERSE_IMG);

        // Get all the authenticationList where reverseImg equals to UPDATED_REVERSE_IMG
        defaultAuthenticationShouldNotBeFound("reverseImg.equals=" + UPDATED_REVERSE_IMG);
    }

    @Test
    @Transactional
    public void getAllAuthenticationsByReverseImgIsInShouldWork() throws Exception {
        // Initialize the database
        authenticationRepository.saveAndFlush(authentication);

        // Get all the authenticationList where reverseImg in DEFAULT_REVERSE_IMG or UPDATED_REVERSE_IMG
        defaultAuthenticationShouldBeFound("reverseImg.in=" + DEFAULT_REVERSE_IMG + "," + UPDATED_REVERSE_IMG);

        // Get all the authenticationList where reverseImg equals to UPDATED_REVERSE_IMG
        defaultAuthenticationShouldNotBeFound("reverseImg.in=" + UPDATED_REVERSE_IMG);
    }

    @Test
    @Transactional
    public void getAllAuthenticationsByReverseImgIsNullOrNotNull() throws Exception {
        // Initialize the database
        authenticationRepository.saveAndFlush(authentication);

        // Get all the authenticationList where reverseImg is not null
        defaultAuthenticationShouldBeFound("reverseImg.specified=true");

        // Get all the authenticationList where reverseImg is null
        defaultAuthenticationShouldNotBeFound("reverseImg.specified=false");
    }

    @Test
    @Transactional
    public void getAllAuthenticationsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        authenticationRepository.saveAndFlush(authentication);

        // Get all the authenticationList where status equals to DEFAULT_STATUS
        defaultAuthenticationShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the authenticationList where status equals to UPDATED_STATUS
        defaultAuthenticationShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllAuthenticationsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        authenticationRepository.saveAndFlush(authentication);

        // Get all the authenticationList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultAuthenticationShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the authenticationList where status equals to UPDATED_STATUS
        defaultAuthenticationShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllAuthenticationsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        authenticationRepository.saveAndFlush(authentication);

        // Get all the authenticationList where status is not null
        defaultAuthenticationShouldBeFound("status.specified=true");

        // Get all the authenticationList where status is null
        defaultAuthenticationShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllAuthenticationsByStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        authenticationRepository.saveAndFlush(authentication);

        // Get all the authenticationList where status greater than or equals to DEFAULT_STATUS
        defaultAuthenticationShouldBeFound("status.greaterOrEqualThan=" + DEFAULT_STATUS);

        // Get all the authenticationList where status greater than or equals to UPDATED_STATUS
        defaultAuthenticationShouldNotBeFound("status.greaterOrEqualThan=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllAuthenticationsByStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        authenticationRepository.saveAndFlush(authentication);

        // Get all the authenticationList where status less than or equals to DEFAULT_STATUS
        defaultAuthenticationShouldNotBeFound("status.lessThan=" + DEFAULT_STATUS);

        // Get all the authenticationList where status less than or equals to UPDATED_STATUS
        defaultAuthenticationShouldBeFound("status.lessThan=" + UPDATED_STATUS);
    }


    @Test
    @Transactional
    public void getAllAuthenticationsByStatusStringIsEqualToSomething() throws Exception {
        // Initialize the database
        authenticationRepository.saveAndFlush(authentication);

        // Get all the authenticationList where statusString equals to DEFAULT_STATUS_STRING
        defaultAuthenticationShouldBeFound("statusString.equals=" + DEFAULT_STATUS_STRING);

        // Get all the authenticationList where statusString equals to UPDATED_STATUS_STRING
        defaultAuthenticationShouldNotBeFound("statusString.equals=" + UPDATED_STATUS_STRING);
    }

    @Test
    @Transactional
    public void getAllAuthenticationsByStatusStringIsInShouldWork() throws Exception {
        // Initialize the database
        authenticationRepository.saveAndFlush(authentication);

        // Get all the authenticationList where statusString in DEFAULT_STATUS_STRING or UPDATED_STATUS_STRING
        defaultAuthenticationShouldBeFound("statusString.in=" + DEFAULT_STATUS_STRING + "," + UPDATED_STATUS_STRING);

        // Get all the authenticationList where statusString equals to UPDATED_STATUS_STRING
        defaultAuthenticationShouldNotBeFound("statusString.in=" + UPDATED_STATUS_STRING);
    }

    @Test
    @Transactional
    public void getAllAuthenticationsByStatusStringIsNullOrNotNull() throws Exception {
        // Initialize the database
        authenticationRepository.saveAndFlush(authentication);

        // Get all the authenticationList where statusString is not null
        defaultAuthenticationShouldBeFound("statusString.specified=true");

        // Get all the authenticationList where statusString is null
        defaultAuthenticationShouldNotBeFound("statusString.specified=false");
    }

    @Test
    @Transactional
    public void getAllAuthenticationsByCreatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        authenticationRepository.saveAndFlush(authentication);

        // Get all the authenticationList where createdTime equals to DEFAULT_CREATED_TIME
        defaultAuthenticationShouldBeFound("createdTime.equals=" + DEFAULT_CREATED_TIME);

        // Get all the authenticationList where createdTime equals to UPDATED_CREATED_TIME
        defaultAuthenticationShouldNotBeFound("createdTime.equals=" + UPDATED_CREATED_TIME);
    }

    @Test
    @Transactional
    public void getAllAuthenticationsByCreatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        authenticationRepository.saveAndFlush(authentication);

        // Get all the authenticationList where createdTime in DEFAULT_CREATED_TIME or UPDATED_CREATED_TIME
        defaultAuthenticationShouldBeFound("createdTime.in=" + DEFAULT_CREATED_TIME + "," + UPDATED_CREATED_TIME);

        // Get all the authenticationList where createdTime equals to UPDATED_CREATED_TIME
        defaultAuthenticationShouldNotBeFound("createdTime.in=" + UPDATED_CREATED_TIME);
    }

    @Test
    @Transactional
    public void getAllAuthenticationsByCreatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        authenticationRepository.saveAndFlush(authentication);

        // Get all the authenticationList where createdTime is not null
        defaultAuthenticationShouldBeFound("createdTime.specified=true");

        // Get all the authenticationList where createdTime is null
        defaultAuthenticationShouldNotBeFound("createdTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllAuthenticationsByUpdatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        authenticationRepository.saveAndFlush(authentication);

        // Get all the authenticationList where updatedTime equals to DEFAULT_UPDATED_TIME
        defaultAuthenticationShouldBeFound("updatedTime.equals=" + DEFAULT_UPDATED_TIME);

        // Get all the authenticationList where updatedTime equals to UPDATED_UPDATED_TIME
        defaultAuthenticationShouldNotBeFound("updatedTime.equals=" + UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void getAllAuthenticationsByUpdatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        authenticationRepository.saveAndFlush(authentication);

        // Get all the authenticationList where updatedTime in DEFAULT_UPDATED_TIME or UPDATED_UPDATED_TIME
        defaultAuthenticationShouldBeFound("updatedTime.in=" + DEFAULT_UPDATED_TIME + "," + UPDATED_UPDATED_TIME);

        // Get all the authenticationList where updatedTime equals to UPDATED_UPDATED_TIME
        defaultAuthenticationShouldNotBeFound("updatedTime.in=" + UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void getAllAuthenticationsByUpdatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        authenticationRepository.saveAndFlush(authentication);

        // Get all the authenticationList where updatedTime is not null
        defaultAuthenticationShouldBeFound("updatedTime.specified=true");

        // Get all the authenticationList where updatedTime is null
        defaultAuthenticationShouldNotBeFound("updatedTime.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultAuthenticationShouldBeFound(String filter) throws Exception {
        restAuthenticationMockMvc.perform(get("/api/authentications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(authentication.getId().intValue())))
            .andExpect(jsonPath("$.[*].realName").value(hasItem(DEFAULT_REAL_NAME.toString())))
            .andExpect(jsonPath("$.[*].idnuber").value(hasItem(DEFAULT_IDNUBER.toString())))
            .andExpect(jsonPath("$.[*].frontImg").value(hasItem(DEFAULT_FRONT_IMG.toString())))
            .andExpect(jsonPath("$.[*].reverseImg").value(hasItem(DEFAULT_REVERSE_IMG.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].statusString").value(hasItem(DEFAULT_STATUS_STRING.toString())))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultAuthenticationShouldNotBeFound(String filter) throws Exception {
        restAuthenticationMockMvc.perform(get("/api/authentications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingAuthentication() throws Exception {
        // Get the authentication
        restAuthenticationMockMvc.perform(get("/api/authentications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuthentication() throws Exception {
        // Initialize the database
        authenticationRepository.saveAndFlush(authentication);
        int databaseSizeBeforeUpdate = authenticationRepository.findAll().size();

        // Update the authentication
        Authentication updatedAuthentication = authenticationRepository.findOne(authentication.getId());
        // Disconnect from session so that the updates on updatedAuthentication are not directly saved in db
        em.detach(updatedAuthentication);
        updatedAuthentication
            .realName(UPDATED_REAL_NAME)
            .idnuber(UPDATED_IDNUBER)
            .frontImg(UPDATED_FRONT_IMG)
            .reverseImg(UPDATED_REVERSE_IMG)
            .status(UPDATED_STATUS)
            .statusString(UPDATED_STATUS_STRING)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedTime(UPDATED_UPDATED_TIME);
        AuthenticationDTO authenticationDTO = authenticationMapper.toDto(updatedAuthentication);

        restAuthenticationMockMvc.perform(put("/api/authentications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(authenticationDTO)))
            .andExpect(status().isOk());

        // Validate the Authentication in the database
        List<Authentication> authenticationList = authenticationRepository.findAll();
        assertThat(authenticationList).hasSize(databaseSizeBeforeUpdate);
        Authentication testAuthentication = authenticationList.get(authenticationList.size() - 1);
        assertThat(testAuthentication.getRealName()).isEqualTo(UPDATED_REAL_NAME);
        assertThat(testAuthentication.getIdnuber()).isEqualTo(UPDATED_IDNUBER);
        assertThat(testAuthentication.getFrontImg()).isEqualTo(UPDATED_FRONT_IMG);
        assertThat(testAuthentication.getReverseImg()).isEqualTo(UPDATED_REVERSE_IMG);
        assertThat(testAuthentication.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAuthentication.getStatusString()).isEqualTo(UPDATED_STATUS_STRING);
        assertThat(testAuthentication.getCreatedTime()).isEqualTo(UPDATED_CREATED_TIME);
        assertThat(testAuthentication.getUpdatedTime()).isEqualTo(UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingAuthentication() throws Exception {
        int databaseSizeBeforeUpdate = authenticationRepository.findAll().size();

        // Create the Authentication
        AuthenticationDTO authenticationDTO = authenticationMapper.toDto(authentication);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAuthenticationMockMvc.perform(put("/api/authentications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(authenticationDTO)))
            .andExpect(status().isCreated());

        // Validate the Authentication in the database
        List<Authentication> authenticationList = authenticationRepository.findAll();
        assertThat(authenticationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAuthentication() throws Exception {
        // Initialize the database
        authenticationRepository.saveAndFlush(authentication);
        int databaseSizeBeforeDelete = authenticationRepository.findAll().size();

        // Get the authentication
        restAuthenticationMockMvc.perform(delete("/api/authentications/{id}", authentication.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Authentication> authenticationList = authenticationRepository.findAll();
        assertThat(authenticationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Authentication.class);
        Authentication authentication1 = new Authentication();
        authentication1.setId(1L);
        Authentication authentication2 = new Authentication();
        authentication2.setId(authentication1.getId());
        assertThat(authentication1).isEqualTo(authentication2);
        authentication2.setId(2L);
        assertThat(authentication1).isNotEqualTo(authentication2);
        authentication1.setId(null);
        assertThat(authentication1).isNotEqualTo(authentication2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuthenticationDTO.class);
        AuthenticationDTO authenticationDTO1 = new AuthenticationDTO();
        authenticationDTO1.setId(1L);
        AuthenticationDTO authenticationDTO2 = new AuthenticationDTO();
        assertThat(authenticationDTO1).isNotEqualTo(authenticationDTO2);
        authenticationDTO2.setId(authenticationDTO1.getId());
        assertThat(authenticationDTO1).isEqualTo(authenticationDTO2);
        authenticationDTO2.setId(2L);
        assertThat(authenticationDTO1).isNotEqualTo(authenticationDTO2);
        authenticationDTO1.setId(null);
        assertThat(authenticationDTO1).isNotEqualTo(authenticationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(authenticationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(authenticationMapper.fromId(null)).isNull();
    }
}
