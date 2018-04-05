package com.eyun.user.web.rest;

import com.eyun.user.UserApp;

import com.eyun.user.config.SecurityBeanOverrideConfiguration;

import com.eyun.user.domain.OwnerRelation;
import com.eyun.user.repository.OwnerRelationRepository;
import com.eyun.user.service.OwnerRelationService;
import com.eyun.user.service.dto.OwnerRelationDTO;
import com.eyun.user.service.mapper.OwnerRelationMapper;
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
 * Test class for the OwnerRelationResource REST controller.
 *
 * @see OwnerRelationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserApp.class, SecurityBeanOverrideConfiguration.class})
public class OwnerRelationResourceIntTest {

    @Autowired
    private OwnerRelationRepository ownerRelationRepository;

    @Autowired
    private OwnerRelationMapper ownerRelationMapper;

    @Autowired
    private OwnerRelationService ownerRelationService;

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
        final OwnerRelationResource ownerRelationResource = new OwnerRelationResource(ownerRelationService);
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
        OwnerRelation ownerRelation = new OwnerRelation();
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
            .andExpect(jsonPath("$.[*].id").value(hasItem(ownerRelation.getId().intValue())));
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
            .andExpect(jsonPath("$.id").value(ownerRelation.getId().intValue()));
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
        OwnerRelationDTO ownerRelationDTO = ownerRelationMapper.toDto(updatedOwnerRelation);

        restOwnerRelationMockMvc.perform(put("/api/owner-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ownerRelationDTO)))
            .andExpect(status().isOk());

        // Validate the OwnerRelation in the database
        List<OwnerRelation> ownerRelationList = ownerRelationRepository.findAll();
        assertThat(ownerRelationList).hasSize(databaseSizeBeforeUpdate);
        OwnerRelation testOwnerRelation = ownerRelationList.get(ownerRelationList.size() - 1);
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
