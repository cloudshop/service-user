package com.eyun.user.web.rest;

import com.eyun.user.UserApp;

import com.eyun.user.config.SecurityBeanOverrideConfiguration;

import com.eyun.user.domain.OwnerType;
import com.eyun.user.repository.OwnerTypeRepository;
import com.eyun.user.service.OwnerTypeService;
import com.eyun.user.service.dto.OwnerTypeDTO;
import com.eyun.user.service.mapper.OwnerTypeMapper;
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
 * Test class for the OwnerTypeResource REST controller.
 *
 * @see OwnerTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserApp.class, SecurityBeanOverrideConfiguration.class})
public class OwnerTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private OwnerTypeRepository ownerTypeRepository;

    @Autowired
    private OwnerTypeMapper ownerTypeMapper;

    @Autowired
    private OwnerTypeService ownerTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOwnerTypeMockMvc;

    private OwnerType ownerType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OwnerTypeResource ownerTypeResource = new OwnerTypeResource(ownerTypeService);
        this.restOwnerTypeMockMvc = MockMvcBuilders.standaloneSetup(ownerTypeResource)
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
    public static OwnerType createEntity(EntityManager em) {
        OwnerType ownerType = new OwnerType()
            .name(DEFAULT_NAME);
        return ownerType;
    }

    @Before
    public void initTest() {
        ownerType = createEntity(em);
    }

    @Test
    @Transactional
    public void createOwnerType() throws Exception {
        int databaseSizeBeforeCreate = ownerTypeRepository.findAll().size();

        // Create the OwnerType
        OwnerTypeDTO ownerTypeDTO = ownerTypeMapper.toDto(ownerType);
        restOwnerTypeMockMvc.perform(post("/api/owner-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ownerTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the OwnerType in the database
        List<OwnerType> ownerTypeList = ownerTypeRepository.findAll();
        assertThat(ownerTypeList).hasSize(databaseSizeBeforeCreate + 1);
        OwnerType testOwnerType = ownerTypeList.get(ownerTypeList.size() - 1);
        assertThat(testOwnerType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createOwnerTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ownerTypeRepository.findAll().size();

        // Create the OwnerType with an existing ID
        ownerType.setId(1L);
        OwnerTypeDTO ownerTypeDTO = ownerTypeMapper.toDto(ownerType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOwnerTypeMockMvc.perform(post("/api/owner-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ownerTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OwnerType in the database
        List<OwnerType> ownerTypeList = ownerTypeRepository.findAll();
        assertThat(ownerTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOwnerTypes() throws Exception {
        // Initialize the database
        ownerTypeRepository.saveAndFlush(ownerType);

        // Get all the ownerTypeList
        restOwnerTypeMockMvc.perform(get("/api/owner-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ownerType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getOwnerType() throws Exception {
        // Initialize the database
        ownerTypeRepository.saveAndFlush(ownerType);

        // Get the ownerType
        restOwnerTypeMockMvc.perform(get("/api/owner-types/{id}", ownerType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ownerType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOwnerType() throws Exception {
        // Get the ownerType
        restOwnerTypeMockMvc.perform(get("/api/owner-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOwnerType() throws Exception {
        // Initialize the database
        ownerTypeRepository.saveAndFlush(ownerType);
        int databaseSizeBeforeUpdate = ownerTypeRepository.findAll().size();

        // Update the ownerType
        OwnerType updatedOwnerType = ownerTypeRepository.findOne(ownerType.getId());
        // Disconnect from session so that the updates on updatedOwnerType are not directly saved in db
        em.detach(updatedOwnerType);
        updatedOwnerType
            .name(UPDATED_NAME);
        OwnerTypeDTO ownerTypeDTO = ownerTypeMapper.toDto(updatedOwnerType);

        restOwnerTypeMockMvc.perform(put("/api/owner-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ownerTypeDTO)))
            .andExpect(status().isOk());

        // Validate the OwnerType in the database
        List<OwnerType> ownerTypeList = ownerTypeRepository.findAll();
        assertThat(ownerTypeList).hasSize(databaseSizeBeforeUpdate);
        OwnerType testOwnerType = ownerTypeList.get(ownerTypeList.size() - 1);
        assertThat(testOwnerType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingOwnerType() throws Exception {
        int databaseSizeBeforeUpdate = ownerTypeRepository.findAll().size();

        // Create the OwnerType
        OwnerTypeDTO ownerTypeDTO = ownerTypeMapper.toDto(ownerType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOwnerTypeMockMvc.perform(put("/api/owner-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ownerTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the OwnerType in the database
        List<OwnerType> ownerTypeList = ownerTypeRepository.findAll();
        assertThat(ownerTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOwnerType() throws Exception {
        // Initialize the database
        ownerTypeRepository.saveAndFlush(ownerType);
        int databaseSizeBeforeDelete = ownerTypeRepository.findAll().size();

        // Get the ownerType
        restOwnerTypeMockMvc.perform(delete("/api/owner-types/{id}", ownerType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OwnerType> ownerTypeList = ownerTypeRepository.findAll();
        assertThat(ownerTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OwnerType.class);
        OwnerType ownerType1 = new OwnerType();
        ownerType1.setId(1L);
        OwnerType ownerType2 = new OwnerType();
        ownerType2.setId(ownerType1.getId());
        assertThat(ownerType1).isEqualTo(ownerType2);
        ownerType2.setId(2L);
        assertThat(ownerType1).isNotEqualTo(ownerType2);
        ownerType1.setId(null);
        assertThat(ownerType1).isNotEqualTo(ownerType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OwnerTypeDTO.class);
        OwnerTypeDTO ownerTypeDTO1 = new OwnerTypeDTO();
        ownerTypeDTO1.setId(1L);
        OwnerTypeDTO ownerTypeDTO2 = new OwnerTypeDTO();
        assertThat(ownerTypeDTO1).isNotEqualTo(ownerTypeDTO2);
        ownerTypeDTO2.setId(ownerTypeDTO1.getId());
        assertThat(ownerTypeDTO1).isEqualTo(ownerTypeDTO2);
        ownerTypeDTO2.setId(2L);
        assertThat(ownerTypeDTO1).isNotEqualTo(ownerTypeDTO2);
        ownerTypeDTO1.setId(null);
        assertThat(ownerTypeDTO1).isNotEqualTo(ownerTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ownerTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ownerTypeMapper.fromId(null)).isNull();
    }
}
