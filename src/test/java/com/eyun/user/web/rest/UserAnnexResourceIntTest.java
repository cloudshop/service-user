package com.eyun.user.web.rest;

import com.eyun.user.UserApp;

import com.eyun.user.config.SecurityBeanOverrideConfiguration;

import com.eyun.user.domain.UserAnnex;
import com.eyun.user.domain.OwnerRelation;
import com.eyun.user.domain.UserStatusHistory;
import com.eyun.user.repository.UserAnnexRepository;
import com.eyun.user.service.UserAnnexService;
import com.eyun.user.service.dto.UserAnnexDTO;
import com.eyun.user.service.mapper.UserAnnexMapper;
import com.eyun.user.web.rest.errors.ExceptionTranslator;
import com.eyun.user.service.dto.UserAnnexCriteria;
import com.eyun.user.service.UserAnnexQueryService;

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
 * Test class for the UserAnnexResource REST controller.
 *
 * @see UserAnnexResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserApp.class, SecurityBeanOverrideConfiguration.class})
public class UserAnnexResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_NICKNAME = "AAAAAAAAAA";
    private static final String UPDATED_NICKNAME = "BBBBBBBBBB";

    private static final String DEFAULT_AVATAR = "AAAAAAAAAA";
    private static final String UPDATED_AVATAR = "BBBBBBBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_TYPE = 1;
    private static final Integer UPDATED_TYPE = 2;

    private static final Long DEFAULT_INVITER_ID = 1L;
    private static final Long UPDATED_INVITER_ID = 2L;

    @Autowired
    private UserAnnexRepository userAnnexRepository;

    @Autowired
    private UserAnnexMapper userAnnexMapper;

    @Autowired
    private UserAnnexService userAnnexService;

    @Autowired
    private UserAnnexQueryService userAnnexQueryService;

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
        final UserAnnexResource userAnnexResource = new UserAnnexResource(userAnnexService, userAnnexQueryService);
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
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .nickname(DEFAULT_NICKNAME)
            .avatar(DEFAULT_AVATAR)
            .status(DEFAULT_STATUS)
            .createdTime(DEFAULT_CREATED_TIME)
            .updatedTime(DEFAULT_UPDATED_TIME)
            .type(DEFAULT_TYPE)
            .inviterId(DEFAULT_INVITER_ID);
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
        assertThat(testUserAnnex.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUserAnnex.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testUserAnnex.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testUserAnnex.getNickname()).isEqualTo(DEFAULT_NICKNAME);
        assertThat(testUserAnnex.getAvatar()).isEqualTo(DEFAULT_AVATAR);
        assertThat(testUserAnnex.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUserAnnex.getCreatedTime()).isEqualTo(DEFAULT_CREATED_TIME);
        assertThat(testUserAnnex.getUpdatedTime()).isEqualTo(DEFAULT_UPDATED_TIME);
        assertThat(testUserAnnex.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testUserAnnex.getInviterId()).isEqualTo(DEFAULT_INVITER_ID);
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
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].nickname").value(hasItem(DEFAULT_NICKNAME.toString())))
            .andExpect(jsonPath("$.[*].avatar").value(hasItem(DEFAULT_AVATAR.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].inviterId").value(hasItem(DEFAULT_INVITER_ID.intValue())));
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
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.nickname").value(DEFAULT_NICKNAME.toString()))
            .andExpect(jsonPath("$.avatar").value(DEFAULT_AVATAR.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.inviterId").value(DEFAULT_INVITER_ID.intValue()));
    }

    @Test
    @Transactional
    public void getAllUserAnnexesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        userAnnexRepository.saveAndFlush(userAnnex);

        // Get all the userAnnexList where name equals to DEFAULT_NAME
        defaultUserAnnexShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the userAnnexList where name equals to UPDATED_NAME
        defaultUserAnnexShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUserAnnexesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        userAnnexRepository.saveAndFlush(userAnnex);

        // Get all the userAnnexList where name in DEFAULT_NAME or UPDATED_NAME
        defaultUserAnnexShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the userAnnexList where name equals to UPDATED_NAME
        defaultUserAnnexShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUserAnnexesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        userAnnexRepository.saveAndFlush(userAnnex);

        // Get all the userAnnexList where name is not null
        defaultUserAnnexShouldBeFound("name.specified=true");

        // Get all the userAnnexList where name is null
        defaultUserAnnexShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserAnnexesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        userAnnexRepository.saveAndFlush(userAnnex);

        // Get all the userAnnexList where email equals to DEFAULT_EMAIL
        defaultUserAnnexShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the userAnnexList where email equals to UPDATED_EMAIL
        defaultUserAnnexShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllUserAnnexesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        userAnnexRepository.saveAndFlush(userAnnex);

        // Get all the userAnnexList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultUserAnnexShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the userAnnexList where email equals to UPDATED_EMAIL
        defaultUserAnnexShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllUserAnnexesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        userAnnexRepository.saveAndFlush(userAnnex);

        // Get all the userAnnexList where email is not null
        defaultUserAnnexShouldBeFound("email.specified=true");

        // Get all the userAnnexList where email is null
        defaultUserAnnexShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserAnnexesByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        userAnnexRepository.saveAndFlush(userAnnex);

        // Get all the userAnnexList where phone equals to DEFAULT_PHONE
        defaultUserAnnexShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the userAnnexList where phone equals to UPDATED_PHONE
        defaultUserAnnexShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllUserAnnexesByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        userAnnexRepository.saveAndFlush(userAnnex);

        // Get all the userAnnexList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultUserAnnexShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the userAnnexList where phone equals to UPDATED_PHONE
        defaultUserAnnexShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllUserAnnexesByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        userAnnexRepository.saveAndFlush(userAnnex);

        // Get all the userAnnexList where phone is not null
        defaultUserAnnexShouldBeFound("phone.specified=true");

        // Get all the userAnnexList where phone is null
        defaultUserAnnexShouldNotBeFound("phone.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserAnnexesByNicknameIsEqualToSomething() throws Exception {
        // Initialize the database
        userAnnexRepository.saveAndFlush(userAnnex);

        // Get all the userAnnexList where nickname equals to DEFAULT_NICKNAME
        defaultUserAnnexShouldBeFound("nickname.equals=" + DEFAULT_NICKNAME);

        // Get all the userAnnexList where nickname equals to UPDATED_NICKNAME
        defaultUserAnnexShouldNotBeFound("nickname.equals=" + UPDATED_NICKNAME);
    }

    @Test
    @Transactional
    public void getAllUserAnnexesByNicknameIsInShouldWork() throws Exception {
        // Initialize the database
        userAnnexRepository.saveAndFlush(userAnnex);

        // Get all the userAnnexList where nickname in DEFAULT_NICKNAME or UPDATED_NICKNAME
        defaultUserAnnexShouldBeFound("nickname.in=" + DEFAULT_NICKNAME + "," + UPDATED_NICKNAME);

        // Get all the userAnnexList where nickname equals to UPDATED_NICKNAME
        defaultUserAnnexShouldNotBeFound("nickname.in=" + UPDATED_NICKNAME);
    }

    @Test
    @Transactional
    public void getAllUserAnnexesByNicknameIsNullOrNotNull() throws Exception {
        // Initialize the database
        userAnnexRepository.saveAndFlush(userAnnex);

        // Get all the userAnnexList where nickname is not null
        defaultUserAnnexShouldBeFound("nickname.specified=true");

        // Get all the userAnnexList where nickname is null
        defaultUserAnnexShouldNotBeFound("nickname.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserAnnexesByAvatarIsEqualToSomething() throws Exception {
        // Initialize the database
        userAnnexRepository.saveAndFlush(userAnnex);

        // Get all the userAnnexList where avatar equals to DEFAULT_AVATAR
        defaultUserAnnexShouldBeFound("avatar.equals=" + DEFAULT_AVATAR);

        // Get all the userAnnexList where avatar equals to UPDATED_AVATAR
        defaultUserAnnexShouldNotBeFound("avatar.equals=" + UPDATED_AVATAR);
    }

    @Test
    @Transactional
    public void getAllUserAnnexesByAvatarIsInShouldWork() throws Exception {
        // Initialize the database
        userAnnexRepository.saveAndFlush(userAnnex);

        // Get all the userAnnexList where avatar in DEFAULT_AVATAR or UPDATED_AVATAR
        defaultUserAnnexShouldBeFound("avatar.in=" + DEFAULT_AVATAR + "," + UPDATED_AVATAR);

        // Get all the userAnnexList where avatar equals to UPDATED_AVATAR
        defaultUserAnnexShouldNotBeFound("avatar.in=" + UPDATED_AVATAR);
    }

    @Test
    @Transactional
    public void getAllUserAnnexesByAvatarIsNullOrNotNull() throws Exception {
        // Initialize the database
        userAnnexRepository.saveAndFlush(userAnnex);

        // Get all the userAnnexList where avatar is not null
        defaultUserAnnexShouldBeFound("avatar.specified=true");

        // Get all the userAnnexList where avatar is null
        defaultUserAnnexShouldNotBeFound("avatar.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserAnnexesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        userAnnexRepository.saveAndFlush(userAnnex);

        // Get all the userAnnexList where status equals to DEFAULT_STATUS
        defaultUserAnnexShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the userAnnexList where status equals to UPDATED_STATUS
        defaultUserAnnexShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllUserAnnexesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        userAnnexRepository.saveAndFlush(userAnnex);

        // Get all the userAnnexList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultUserAnnexShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the userAnnexList where status equals to UPDATED_STATUS
        defaultUserAnnexShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllUserAnnexesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        userAnnexRepository.saveAndFlush(userAnnex);

        // Get all the userAnnexList where status is not null
        defaultUserAnnexShouldBeFound("status.specified=true");

        // Get all the userAnnexList where status is null
        defaultUserAnnexShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserAnnexesByStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userAnnexRepository.saveAndFlush(userAnnex);

        // Get all the userAnnexList where status greater than or equals to DEFAULT_STATUS
        defaultUserAnnexShouldBeFound("status.greaterOrEqualThan=" + DEFAULT_STATUS);

        // Get all the userAnnexList where status greater than or equals to UPDATED_STATUS
        defaultUserAnnexShouldNotBeFound("status.greaterOrEqualThan=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllUserAnnexesByStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        userAnnexRepository.saveAndFlush(userAnnex);

        // Get all the userAnnexList where status less than or equals to DEFAULT_STATUS
        defaultUserAnnexShouldNotBeFound("status.lessThan=" + DEFAULT_STATUS);

        // Get all the userAnnexList where status less than or equals to UPDATED_STATUS
        defaultUserAnnexShouldBeFound("status.lessThan=" + UPDATED_STATUS);
    }


    @Test
    @Transactional
    public void getAllUserAnnexesByCreatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        userAnnexRepository.saveAndFlush(userAnnex);

        // Get all the userAnnexList where createdTime equals to DEFAULT_CREATED_TIME
        defaultUserAnnexShouldBeFound("createdTime.equals=" + DEFAULT_CREATED_TIME);

        // Get all the userAnnexList where createdTime equals to UPDATED_CREATED_TIME
        defaultUserAnnexShouldNotBeFound("createdTime.equals=" + UPDATED_CREATED_TIME);
    }

    @Test
    @Transactional
    public void getAllUserAnnexesByCreatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        userAnnexRepository.saveAndFlush(userAnnex);

        // Get all the userAnnexList where createdTime in DEFAULT_CREATED_TIME or UPDATED_CREATED_TIME
        defaultUserAnnexShouldBeFound("createdTime.in=" + DEFAULT_CREATED_TIME + "," + UPDATED_CREATED_TIME);

        // Get all the userAnnexList where createdTime equals to UPDATED_CREATED_TIME
        defaultUserAnnexShouldNotBeFound("createdTime.in=" + UPDATED_CREATED_TIME);
    }

    @Test
    @Transactional
    public void getAllUserAnnexesByCreatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        userAnnexRepository.saveAndFlush(userAnnex);

        // Get all the userAnnexList where createdTime is not null
        defaultUserAnnexShouldBeFound("createdTime.specified=true");

        // Get all the userAnnexList where createdTime is null
        defaultUserAnnexShouldNotBeFound("createdTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserAnnexesByUpdatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        userAnnexRepository.saveAndFlush(userAnnex);

        // Get all the userAnnexList where updatedTime equals to DEFAULT_UPDATED_TIME
        defaultUserAnnexShouldBeFound("updatedTime.equals=" + DEFAULT_UPDATED_TIME);

        // Get all the userAnnexList where updatedTime equals to UPDATED_UPDATED_TIME
        defaultUserAnnexShouldNotBeFound("updatedTime.equals=" + UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void getAllUserAnnexesByUpdatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        userAnnexRepository.saveAndFlush(userAnnex);

        // Get all the userAnnexList where updatedTime in DEFAULT_UPDATED_TIME or UPDATED_UPDATED_TIME
        defaultUserAnnexShouldBeFound("updatedTime.in=" + DEFAULT_UPDATED_TIME + "," + UPDATED_UPDATED_TIME);

        // Get all the userAnnexList where updatedTime equals to UPDATED_UPDATED_TIME
        defaultUserAnnexShouldNotBeFound("updatedTime.in=" + UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void getAllUserAnnexesByUpdatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        userAnnexRepository.saveAndFlush(userAnnex);

        // Get all the userAnnexList where updatedTime is not null
        defaultUserAnnexShouldBeFound("updatedTime.specified=true");

        // Get all the userAnnexList where updatedTime is null
        defaultUserAnnexShouldNotBeFound("updatedTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserAnnexesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        userAnnexRepository.saveAndFlush(userAnnex);

        // Get all the userAnnexList where type equals to DEFAULT_TYPE
        defaultUserAnnexShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the userAnnexList where type equals to UPDATED_TYPE
        defaultUserAnnexShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllUserAnnexesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        userAnnexRepository.saveAndFlush(userAnnex);

        // Get all the userAnnexList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultUserAnnexShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the userAnnexList where type equals to UPDATED_TYPE
        defaultUserAnnexShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllUserAnnexesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        userAnnexRepository.saveAndFlush(userAnnex);

        // Get all the userAnnexList where type is not null
        defaultUserAnnexShouldBeFound("type.specified=true");

        // Get all the userAnnexList where type is null
        defaultUserAnnexShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserAnnexesByTypeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userAnnexRepository.saveAndFlush(userAnnex);

        // Get all the userAnnexList where type greater than or equals to DEFAULT_TYPE
        defaultUserAnnexShouldBeFound("type.greaterOrEqualThan=" + DEFAULT_TYPE);

        // Get all the userAnnexList where type greater than or equals to UPDATED_TYPE
        defaultUserAnnexShouldNotBeFound("type.greaterOrEqualThan=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllUserAnnexesByTypeIsLessThanSomething() throws Exception {
        // Initialize the database
        userAnnexRepository.saveAndFlush(userAnnex);

        // Get all the userAnnexList where type less than or equals to DEFAULT_TYPE
        defaultUserAnnexShouldNotBeFound("type.lessThan=" + DEFAULT_TYPE);

        // Get all the userAnnexList where type less than or equals to UPDATED_TYPE
        defaultUserAnnexShouldBeFound("type.lessThan=" + UPDATED_TYPE);
    }


    @Test
    @Transactional
    public void getAllUserAnnexesByInviterIdIsEqualToSomething() throws Exception {
        // Initialize the database
        userAnnexRepository.saveAndFlush(userAnnex);

        // Get all the userAnnexList where inviterId equals to DEFAULT_INVITER_ID
        defaultUserAnnexShouldBeFound("inviterId.equals=" + DEFAULT_INVITER_ID);

        // Get all the userAnnexList where inviterId equals to UPDATED_INVITER_ID
        defaultUserAnnexShouldNotBeFound("inviterId.equals=" + UPDATED_INVITER_ID);
    }

    @Test
    @Transactional
    public void getAllUserAnnexesByInviterIdIsInShouldWork() throws Exception {
        // Initialize the database
        userAnnexRepository.saveAndFlush(userAnnex);

        // Get all the userAnnexList where inviterId in DEFAULT_INVITER_ID or UPDATED_INVITER_ID
        defaultUserAnnexShouldBeFound("inviterId.in=" + DEFAULT_INVITER_ID + "," + UPDATED_INVITER_ID);

        // Get all the userAnnexList where inviterId equals to UPDATED_INVITER_ID
        defaultUserAnnexShouldNotBeFound("inviterId.in=" + UPDATED_INVITER_ID);
    }

    @Test
    @Transactional
    public void getAllUserAnnexesByInviterIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        userAnnexRepository.saveAndFlush(userAnnex);

        // Get all the userAnnexList where inviterId is not null
        defaultUserAnnexShouldBeFound("inviterId.specified=true");

        // Get all the userAnnexList where inviterId is null
        defaultUserAnnexShouldNotBeFound("inviterId.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserAnnexesByInviterIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userAnnexRepository.saveAndFlush(userAnnex);

        // Get all the userAnnexList where inviterId greater than or equals to DEFAULT_INVITER_ID
        defaultUserAnnexShouldBeFound("inviterId.greaterOrEqualThan=" + DEFAULT_INVITER_ID);

        // Get all the userAnnexList where inviterId greater than or equals to UPDATED_INVITER_ID
        defaultUserAnnexShouldNotBeFound("inviterId.greaterOrEqualThan=" + UPDATED_INVITER_ID);
    }

    @Test
    @Transactional
    public void getAllUserAnnexesByInviterIdIsLessThanSomething() throws Exception {
        // Initialize the database
        userAnnexRepository.saveAndFlush(userAnnex);

        // Get all the userAnnexList where inviterId less than or equals to DEFAULT_INVITER_ID
        defaultUserAnnexShouldNotBeFound("inviterId.lessThan=" + DEFAULT_INVITER_ID);

        // Get all the userAnnexList where inviterId less than or equals to UPDATED_INVITER_ID
        defaultUserAnnexShouldBeFound("inviterId.lessThan=" + UPDATED_INVITER_ID);
    }


    @Test
    @Transactional
    public void getAllUserAnnexesByOwnerRelationIsEqualToSomething() throws Exception {
        // Initialize the database
        OwnerRelation ownerRelation = OwnerRelationResourceIntTest.createEntity(em);
        em.persist(ownerRelation);
        em.flush();
        userAnnex.setOwnerRelation(ownerRelation);
        ownerRelation.setUserAnnex(userAnnex);
        userAnnexRepository.saveAndFlush(userAnnex);
        Long ownerRelationId = ownerRelation.getId();

        // Get all the userAnnexList where ownerRelation equals to ownerRelationId
        defaultUserAnnexShouldBeFound("ownerRelationId.equals=" + ownerRelationId);

        // Get all the userAnnexList where ownerRelation equals to ownerRelationId + 1
        defaultUserAnnexShouldNotBeFound("ownerRelationId.equals=" + (ownerRelationId + 1));
    }


    @Test
    @Transactional
    public void getAllUserAnnexesByUserStatusHistoryIsEqualToSomething() throws Exception {
        // Initialize the database
        UserStatusHistory userStatusHistory = UserStatusHistoryResourceIntTest.createEntity(em);
        em.persist(userStatusHistory);
        em.flush();
        userAnnex.addUserStatusHistory(userStatusHistory);
        userAnnexRepository.saveAndFlush(userAnnex);
        Long userStatusHistoryId = userStatusHistory.getId();

        // Get all the userAnnexList where userStatusHistory equals to userStatusHistoryId
        defaultUserAnnexShouldBeFound("userStatusHistoryId.equals=" + userStatusHistoryId);

        // Get all the userAnnexList where userStatusHistory equals to userStatusHistoryId + 1
        defaultUserAnnexShouldNotBeFound("userStatusHistoryId.equals=" + (userStatusHistoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultUserAnnexShouldBeFound(String filter) throws Exception {
        restUserAnnexMockMvc.perform(get("/api/user-annexes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAnnex.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].nickname").value(hasItem(DEFAULT_NICKNAME.toString())))
            .andExpect(jsonPath("$.[*].avatar").value(hasItem(DEFAULT_AVATAR.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].inviterId").value(hasItem(DEFAULT_INVITER_ID.intValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultUserAnnexShouldNotBeFound(String filter) throws Exception {
        restUserAnnexMockMvc.perform(get("/api/user-annexes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
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
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .nickname(UPDATED_NICKNAME)
            .avatar(UPDATED_AVATAR)
            .status(UPDATED_STATUS)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedTime(UPDATED_UPDATED_TIME)
            .type(UPDATED_TYPE)
            .inviterId(UPDATED_INVITER_ID);
        UserAnnexDTO userAnnexDTO = userAnnexMapper.toDto(updatedUserAnnex);

        restUserAnnexMockMvc.perform(put("/api/user-annexes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAnnexDTO)))
            .andExpect(status().isOk());

        // Validate the UserAnnex in the database
        List<UserAnnex> userAnnexList = userAnnexRepository.findAll();
        assertThat(userAnnexList).hasSize(databaseSizeBeforeUpdate);
        UserAnnex testUserAnnex = userAnnexList.get(userAnnexList.size() - 1);
        assertThat(testUserAnnex.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUserAnnex.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testUserAnnex.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testUserAnnex.getNickname()).isEqualTo(UPDATED_NICKNAME);
        assertThat(testUserAnnex.getAvatar()).isEqualTo(UPDATED_AVATAR);
        assertThat(testUserAnnex.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUserAnnex.getCreatedTime()).isEqualTo(UPDATED_CREATED_TIME);
        assertThat(testUserAnnex.getUpdatedTime()).isEqualTo(UPDATED_UPDATED_TIME);
        assertThat(testUserAnnex.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testUserAnnex.getInviterId()).isEqualTo(UPDATED_INVITER_ID);
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
