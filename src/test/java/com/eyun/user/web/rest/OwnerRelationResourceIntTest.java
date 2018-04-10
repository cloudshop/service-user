package com.eyun.user.web.rest;

import com.eyun.user.UserApp;

import com.eyun.user.config.SecurityBeanOverrideConfiguration;

import com.eyun.user.domain.OwnerRelation;
import com.eyun.user.domain.UserAnnex;
import com.eyun.user.domain.Mercury;
import com.eyun.user.repository.OwnerRelationRepository;
import com.eyun.user.service.OwnerRelationService;
import com.eyun.user.service.dto.OwnerRelationDTO;
import com.eyun.user.service.mapper.OwnerRelationMapper;
import com.eyun.user.web.rest.errors.ExceptionTranslator;
import com.eyun.user.service.dto.OwnerRelationCriteria;
import com.eyun.user.service.OwnerRelationQueryService;

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
 * Test class for the OwnerRelationResource REST controller.
 *
 * @see OwnerRelationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserApp.class, SecurityBeanOverrideConfiguration.class})
public class OwnerRelationResourceIntTest {

    private static final String DEFAULT_ROLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ROLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private OwnerRelationRepository ownerRelationRepository;

    @Autowired
    private OwnerRelationMapper ownerRelationMapper;

    @Autowired
    private OwnerRelationService ownerRelationService;

    @Autowired
    private OwnerRelationQueryService ownerRelationQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOwnerRelationMockMvc;

    private OwnerRelation ownerRelation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OwnerRelationResource ownerRelationResource = new OwnerRelationResource(ownerRelationService, ownerRelationQueryService);
        this.restOwnerRelationMockMvc = MockMvcBuilders.standaloneSetup(ownerRelationResource)
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
    public static OwnerRelation createEntity(EntityManager em) {
        OwnerRelation ownerRelation = new OwnerRelation()
            .roleName(DEFAULT_ROLE_NAME)
            .description(DEFAULT_DESCRIPTION)
            .createdTime(DEFAULT_CREATED_TIME)
            .updatedTime(DEFAULT_UPDATED_TIME);
        return ownerRelation;
    }

    @Before
    public void initTest() {
        ownerRelation = createEntity(em);
    }

    @Test
    @Transactional
    public void createOwnerRelation() throws Exception {
        int databaseSizeBeforeCreate = ownerRelationRepository.findAll().size();

        // Create the OwnerRelation
        OwnerRelationDTO ownerRelationDTO = ownerRelationMapper.toDto(ownerRelation);
        restOwnerRelationMockMvc.perform(post("/api/owner-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ownerRelationDTO)))
            .andExpect(status().isCreated());

        // Validate the OwnerRelation in the database
        List<OwnerRelation> ownerRelationList = ownerRelationRepository.findAll();
        assertThat(ownerRelationList).hasSize(databaseSizeBeforeCreate + 1);
        OwnerRelation testOwnerRelation = ownerRelationList.get(ownerRelationList.size() - 1);
        assertThat(testOwnerRelation.getRoleName()).isEqualTo(DEFAULT_ROLE_NAME);
        assertThat(testOwnerRelation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOwnerRelation.getCreatedTime()).isEqualTo(DEFAULT_CREATED_TIME);
        assertThat(testOwnerRelation.getUpdatedTime()).isEqualTo(DEFAULT_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void createOwnerRelationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ownerRelationRepository.findAll().size();

        // Create the OwnerRelation with an existing ID
        ownerRelation.setId(1L);
        OwnerRelationDTO ownerRelationDTO = ownerRelationMapper.toDto(ownerRelation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOwnerRelationMockMvc.perform(post("/api/owner-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ownerRelationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OwnerRelation in the database
        List<OwnerRelation> ownerRelationList = ownerRelationRepository.findAll();
        assertThat(ownerRelationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOwnerRelations() throws Exception {
        // Initialize the database
        ownerRelationRepository.saveAndFlush(ownerRelation);

        // Get all the ownerRelationList
        restOwnerRelationMockMvc.perform(get("/api/owner-relations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ownerRelation.getId().intValue())))
            .andExpect(jsonPath("$.[*].roleName").value(hasItem(DEFAULT_ROLE_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    public void getOwnerRelation() throws Exception {
        // Initialize the database
        ownerRelationRepository.saveAndFlush(ownerRelation);

        // Get the ownerRelation
        restOwnerRelationMockMvc.perform(get("/api/owner-relations/{id}", ownerRelation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ownerRelation.getId().intValue()))
            .andExpect(jsonPath("$.roleName").value(DEFAULT_ROLE_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    public void getAllOwnerRelationsByRoleNameIsEqualToSomething() throws Exception {
        // Initialize the database
        ownerRelationRepository.saveAndFlush(ownerRelation);

        // Get all the ownerRelationList where roleName equals to DEFAULT_ROLE_NAME
        defaultOwnerRelationShouldBeFound("roleName.equals=" + DEFAULT_ROLE_NAME);

        // Get all the ownerRelationList where roleName equals to UPDATED_ROLE_NAME
        defaultOwnerRelationShouldNotBeFound("roleName.equals=" + UPDATED_ROLE_NAME);
    }

    @Test
    @Transactional
    public void getAllOwnerRelationsByRoleNameIsInShouldWork() throws Exception {
        // Initialize the database
        ownerRelationRepository.saveAndFlush(ownerRelation);

        // Get all the ownerRelationList where roleName in DEFAULT_ROLE_NAME or UPDATED_ROLE_NAME
        defaultOwnerRelationShouldBeFound("roleName.in=" + DEFAULT_ROLE_NAME + "," + UPDATED_ROLE_NAME);

        // Get all the ownerRelationList where roleName equals to UPDATED_ROLE_NAME
        defaultOwnerRelationShouldNotBeFound("roleName.in=" + UPDATED_ROLE_NAME);
    }

    @Test
    @Transactional
    public void getAllOwnerRelationsByRoleNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        ownerRelationRepository.saveAndFlush(ownerRelation);

        // Get all the ownerRelationList where roleName is not null
        defaultOwnerRelationShouldBeFound("roleName.specified=true");

        // Get all the ownerRelationList where roleName is null
        defaultOwnerRelationShouldNotBeFound("roleName.specified=false");
    }

    @Test
    @Transactional
    public void getAllOwnerRelationsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        ownerRelationRepository.saveAndFlush(ownerRelation);

        // Get all the ownerRelationList where description equals to DEFAULT_DESCRIPTION
        defaultOwnerRelationShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the ownerRelationList where description equals to UPDATED_DESCRIPTION
        defaultOwnerRelationShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllOwnerRelationsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        ownerRelationRepository.saveAndFlush(ownerRelation);

        // Get all the ownerRelationList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultOwnerRelationShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the ownerRelationList where description equals to UPDATED_DESCRIPTION
        defaultOwnerRelationShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllOwnerRelationsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        ownerRelationRepository.saveAndFlush(ownerRelation);

        // Get all the ownerRelationList where description is not null
        defaultOwnerRelationShouldBeFound("description.specified=true");

        // Get all the ownerRelationList where description is null
        defaultOwnerRelationShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllOwnerRelationsByCreatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        ownerRelationRepository.saveAndFlush(ownerRelation);

        // Get all the ownerRelationList where createdTime equals to DEFAULT_CREATED_TIME
        defaultOwnerRelationShouldBeFound("createdTime.equals=" + DEFAULT_CREATED_TIME);

        // Get all the ownerRelationList where createdTime equals to UPDATED_CREATED_TIME
        defaultOwnerRelationShouldNotBeFound("createdTime.equals=" + UPDATED_CREATED_TIME);
    }

    @Test
    @Transactional
    public void getAllOwnerRelationsByCreatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        ownerRelationRepository.saveAndFlush(ownerRelation);

        // Get all the ownerRelationList where createdTime in DEFAULT_CREATED_TIME or UPDATED_CREATED_TIME
        defaultOwnerRelationShouldBeFound("createdTime.in=" + DEFAULT_CREATED_TIME + "," + UPDATED_CREATED_TIME);

        // Get all the ownerRelationList where createdTime equals to UPDATED_CREATED_TIME
        defaultOwnerRelationShouldNotBeFound("createdTime.in=" + UPDATED_CREATED_TIME);
    }

    @Test
    @Transactional
    public void getAllOwnerRelationsByCreatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        ownerRelationRepository.saveAndFlush(ownerRelation);

        // Get all the ownerRelationList where createdTime is not null
        defaultOwnerRelationShouldBeFound("createdTime.specified=true");

        // Get all the ownerRelationList where createdTime is null
        defaultOwnerRelationShouldNotBeFound("createdTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllOwnerRelationsByUpdatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        ownerRelationRepository.saveAndFlush(ownerRelation);

        // Get all the ownerRelationList where updatedTime equals to DEFAULT_UPDATED_TIME
        defaultOwnerRelationShouldBeFound("updatedTime.equals=" + DEFAULT_UPDATED_TIME);

        // Get all the ownerRelationList where updatedTime equals to UPDATED_UPDATED_TIME
        defaultOwnerRelationShouldNotBeFound("updatedTime.equals=" + UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void getAllOwnerRelationsByUpdatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        ownerRelationRepository.saveAndFlush(ownerRelation);

        // Get all the ownerRelationList where updatedTime in DEFAULT_UPDATED_TIME or UPDATED_UPDATED_TIME
        defaultOwnerRelationShouldBeFound("updatedTime.in=" + DEFAULT_UPDATED_TIME + "," + UPDATED_UPDATED_TIME);

        // Get all the ownerRelationList where updatedTime equals to UPDATED_UPDATED_TIME
        defaultOwnerRelationShouldNotBeFound("updatedTime.in=" + UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void getAllOwnerRelationsByUpdatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        ownerRelationRepository.saveAndFlush(ownerRelation);

        // Get all the ownerRelationList where updatedTime is not null
        defaultOwnerRelationShouldBeFound("updatedTime.specified=true");

        // Get all the ownerRelationList where updatedTime is null
        defaultOwnerRelationShouldNotBeFound("updatedTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllOwnerRelationsByUserAnnexIsEqualToSomething() throws Exception {
        // Initialize the database
        UserAnnex userAnnex = UserAnnexResourceIntTest.createEntity(em);
        em.persist(userAnnex);
        em.flush();
        ownerRelation.setUserAnnex(userAnnex);
        ownerRelationRepository.saveAndFlush(ownerRelation);
        Long userAnnexId = userAnnex.getId();

        // Get all the ownerRelationList where userAnnex equals to userAnnexId
        defaultOwnerRelationShouldBeFound("userAnnexId.equals=" + userAnnexId);

        // Get all the ownerRelationList where userAnnex equals to userAnnexId + 1
        defaultOwnerRelationShouldNotBeFound("userAnnexId.equals=" + (userAnnexId + 1));
    }


    @Test
    @Transactional
    public void getAllOwnerRelationsByMercuryIsEqualToSomething() throws Exception {
        // Initialize the database
        Mercury mercury = MercuryResourceIntTest.createEntity(em);
        em.persist(mercury);
        em.flush();
        ownerRelation.setMercury(mercury);
        ownerRelationRepository.saveAndFlush(ownerRelation);
        Long mercuryId = mercury.getId();

        // Get all the ownerRelationList where mercury equals to mercuryId
        defaultOwnerRelationShouldBeFound("mercuryId.equals=" + mercuryId);

        // Get all the ownerRelationList where mercury equals to mercuryId + 1
        defaultOwnerRelationShouldNotBeFound("mercuryId.equals=" + (mercuryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultOwnerRelationShouldBeFound(String filter) throws Exception {
        restOwnerRelationMockMvc.perform(get("/api/owner-relations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ownerRelation.getId().intValue())))
            .andExpect(jsonPath("$.[*].roleName").value(hasItem(DEFAULT_ROLE_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultOwnerRelationShouldNotBeFound(String filter) throws Exception {
        restOwnerRelationMockMvc.perform(get("/api/owner-relations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingOwnerRelation() throws Exception {
        // Get the ownerRelation
        restOwnerRelationMockMvc.perform(get("/api/owner-relations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOwnerRelation() throws Exception {
        // Initialize the database
        ownerRelationRepository.saveAndFlush(ownerRelation);
        int databaseSizeBeforeUpdate = ownerRelationRepository.findAll().size();

        // Update the ownerRelation
        OwnerRelation updatedOwnerRelation = ownerRelationRepository.findOne(ownerRelation.getId());
        // Disconnect from session so that the updates on updatedOwnerRelation are not directly saved in db
        em.detach(updatedOwnerRelation);
        updatedOwnerRelation
            .roleName(UPDATED_ROLE_NAME)
            .description(UPDATED_DESCRIPTION)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedTime(UPDATED_UPDATED_TIME);
        OwnerRelationDTO ownerRelationDTO = ownerRelationMapper.toDto(updatedOwnerRelation);

        restOwnerRelationMockMvc.perform(put("/api/owner-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ownerRelationDTO)))
            .andExpect(status().isOk());

        // Validate the OwnerRelation in the database
        List<OwnerRelation> ownerRelationList = ownerRelationRepository.findAll();
        assertThat(ownerRelationList).hasSize(databaseSizeBeforeUpdate);
        OwnerRelation testOwnerRelation = ownerRelationList.get(ownerRelationList.size() - 1);
        assertThat(testOwnerRelation.getRoleName()).isEqualTo(UPDATED_ROLE_NAME);
        assertThat(testOwnerRelation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOwnerRelation.getCreatedTime()).isEqualTo(UPDATED_CREATED_TIME);
        assertThat(testOwnerRelation.getUpdatedTime()).isEqualTo(UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingOwnerRelation() throws Exception {
        int databaseSizeBeforeUpdate = ownerRelationRepository.findAll().size();

        // Create the OwnerRelation
        OwnerRelationDTO ownerRelationDTO = ownerRelationMapper.toDto(ownerRelation);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOwnerRelationMockMvc.perform(put("/api/owner-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ownerRelationDTO)))
            .andExpect(status().isCreated());

        // Validate the OwnerRelation in the database
        List<OwnerRelation> ownerRelationList = ownerRelationRepository.findAll();
        assertThat(ownerRelationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOwnerRelation() throws Exception {
        // Initialize the database
        ownerRelationRepository.saveAndFlush(ownerRelation);
        int databaseSizeBeforeDelete = ownerRelationRepository.findAll().size();

        // Get the ownerRelation
        restOwnerRelationMockMvc.perform(delete("/api/owner-relations/{id}", ownerRelation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OwnerRelation> ownerRelationList = ownerRelationRepository.findAll();
        assertThat(ownerRelationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OwnerRelation.class);
        OwnerRelation ownerRelation1 = new OwnerRelation();
        ownerRelation1.setId(1L);
        OwnerRelation ownerRelation2 = new OwnerRelation();
        ownerRelation2.setId(ownerRelation1.getId());
        assertThat(ownerRelation1).isEqualTo(ownerRelation2);
        ownerRelation2.setId(2L);
        assertThat(ownerRelation1).isNotEqualTo(ownerRelation2);
        ownerRelation1.setId(null);
        assertThat(ownerRelation1).isNotEqualTo(ownerRelation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OwnerRelationDTO.class);
        OwnerRelationDTO ownerRelationDTO1 = new OwnerRelationDTO();
        ownerRelationDTO1.setId(1L);
        OwnerRelationDTO ownerRelationDTO2 = new OwnerRelationDTO();
        assertThat(ownerRelationDTO1).isNotEqualTo(ownerRelationDTO2);
        ownerRelationDTO2.setId(ownerRelationDTO1.getId());
        assertThat(ownerRelationDTO1).isEqualTo(ownerRelationDTO2);
        ownerRelationDTO2.setId(2L);
        assertThat(ownerRelationDTO1).isNotEqualTo(ownerRelationDTO2);
        ownerRelationDTO1.setId(null);
        assertThat(ownerRelationDTO1).isNotEqualTo(ownerRelationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ownerRelationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ownerRelationMapper.fromId(null)).isNull();
    }
}
