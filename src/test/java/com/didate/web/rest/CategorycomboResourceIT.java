package com.didate.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.didate.IntegrationTest;
import com.didate.domain.Categorycombo;
import com.didate.repository.CategorycomboRepository;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CategorycomboResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CategorycomboResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/categorycombos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private CategorycomboRepository categorycomboRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategorycomboMockMvc;

    private Categorycombo categorycombo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Categorycombo createEntity(EntityManager em) {
        Categorycombo categorycombo = new Categorycombo().name(DEFAULT_NAME);
        return categorycombo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Categorycombo createUpdatedEntity(EntityManager em) {
        Categorycombo categorycombo = new Categorycombo().name(UPDATED_NAME);
        return categorycombo;
    }

    @BeforeEach
    public void initTest() {
        categorycombo = createEntity(em);
    }

    @Test
    @Transactional
    void createCategorycombo() throws Exception {
        int databaseSizeBeforeCreate = categorycomboRepository.findAll().size();
        // Create the Categorycombo
        restCategorycomboMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categorycombo)))
            .andExpect(status().isCreated());

        // Validate the Categorycombo in the database
        List<Categorycombo> categorycomboList = categorycomboRepository.findAll();
        assertThat(categorycomboList).hasSize(databaseSizeBeforeCreate + 1);
        Categorycombo testCategorycombo = categorycomboList.get(categorycomboList.size() - 1);
        assertThat(testCategorycombo.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createCategorycomboWithExistingId() throws Exception {
        // Create the Categorycombo with an existing ID
        categorycombo.setId("existing_id");

        int databaseSizeBeforeCreate = categorycomboRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategorycomboMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categorycombo)))
            .andExpect(status().isBadRequest());

        // Validate the Categorycombo in the database
        List<Categorycombo> categorycomboList = categorycomboRepository.findAll();
        assertThat(categorycomboList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCategorycombos() throws Exception {
        // Initialize the database
        categorycombo.setId(UUID.randomUUID().toString());
        categorycomboRepository.saveAndFlush(categorycombo);

        // Get all the categorycomboList
        restCategorycomboMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categorycombo.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getCategorycombo() throws Exception {
        // Initialize the database
        categorycombo.setId(UUID.randomUUID().toString());
        categorycomboRepository.saveAndFlush(categorycombo);

        // Get the categorycombo
        restCategorycomboMockMvc
            .perform(get(ENTITY_API_URL_ID, categorycombo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categorycombo.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingCategorycombo() throws Exception {
        // Get the categorycombo
        restCategorycomboMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCategorycombo() throws Exception {
        // Initialize the database
        categorycombo.setId(UUID.randomUUID().toString());
        categorycomboRepository.saveAndFlush(categorycombo);

        int databaseSizeBeforeUpdate = categorycomboRepository.findAll().size();

        // Update the categorycombo
        Categorycombo updatedCategorycombo = categorycomboRepository.findById(categorycombo.getId()).get();
        // Disconnect from session so that the updates on updatedCategorycombo are not directly saved in db
        em.detach(updatedCategorycombo);
        updatedCategorycombo.name(UPDATED_NAME);

        restCategorycomboMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCategorycombo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCategorycombo))
            )
            .andExpect(status().isOk());

        // Validate the Categorycombo in the database
        List<Categorycombo> categorycomboList = categorycomboRepository.findAll();
        assertThat(categorycomboList).hasSize(databaseSizeBeforeUpdate);
        Categorycombo testCategorycombo = categorycomboList.get(categorycomboList.size() - 1);
        assertThat(testCategorycombo.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingCategorycombo() throws Exception {
        int databaseSizeBeforeUpdate = categorycomboRepository.findAll().size();
        categorycombo.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategorycomboMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categorycombo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categorycombo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Categorycombo in the database
        List<Categorycombo> categorycomboList = categorycomboRepository.findAll();
        assertThat(categorycomboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCategorycombo() throws Exception {
        int databaseSizeBeforeUpdate = categorycomboRepository.findAll().size();
        categorycombo.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorycomboMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categorycombo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Categorycombo in the database
        List<Categorycombo> categorycomboList = categorycomboRepository.findAll();
        assertThat(categorycomboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCategorycombo() throws Exception {
        int databaseSizeBeforeUpdate = categorycomboRepository.findAll().size();
        categorycombo.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorycomboMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categorycombo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Categorycombo in the database
        List<Categorycombo> categorycomboList = categorycomboRepository.findAll();
        assertThat(categorycomboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCategorycomboWithPatch() throws Exception {
        // Initialize the database
        categorycombo.setId(UUID.randomUUID().toString());
        categorycomboRepository.saveAndFlush(categorycombo);

        int databaseSizeBeforeUpdate = categorycomboRepository.findAll().size();

        // Update the categorycombo using partial update
        Categorycombo partialUpdatedCategorycombo = new Categorycombo();
        partialUpdatedCategorycombo.setId(categorycombo.getId());

        partialUpdatedCategorycombo.name(UPDATED_NAME);

        restCategorycomboMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategorycombo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategorycombo))
            )
            .andExpect(status().isOk());

        // Validate the Categorycombo in the database
        List<Categorycombo> categorycomboList = categorycomboRepository.findAll();
        assertThat(categorycomboList).hasSize(databaseSizeBeforeUpdate);
        Categorycombo testCategorycombo = categorycomboList.get(categorycomboList.size() - 1);
        assertThat(testCategorycombo.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateCategorycomboWithPatch() throws Exception {
        // Initialize the database
        categorycombo.setId(UUID.randomUUID().toString());
        categorycomboRepository.saveAndFlush(categorycombo);

        int databaseSizeBeforeUpdate = categorycomboRepository.findAll().size();

        // Update the categorycombo using partial update
        Categorycombo partialUpdatedCategorycombo = new Categorycombo();
        partialUpdatedCategorycombo.setId(categorycombo.getId());

        partialUpdatedCategorycombo.name(UPDATED_NAME);

        restCategorycomboMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategorycombo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategorycombo))
            )
            .andExpect(status().isOk());

        // Validate the Categorycombo in the database
        List<Categorycombo> categorycomboList = categorycomboRepository.findAll();
        assertThat(categorycomboList).hasSize(databaseSizeBeforeUpdate);
        Categorycombo testCategorycombo = categorycomboList.get(categorycomboList.size() - 1);
        assertThat(testCategorycombo.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingCategorycombo() throws Exception {
        int databaseSizeBeforeUpdate = categorycomboRepository.findAll().size();
        categorycombo.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategorycomboMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, categorycombo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categorycombo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Categorycombo in the database
        List<Categorycombo> categorycomboList = categorycomboRepository.findAll();
        assertThat(categorycomboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCategorycombo() throws Exception {
        int databaseSizeBeforeUpdate = categorycomboRepository.findAll().size();
        categorycombo.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorycomboMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categorycombo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Categorycombo in the database
        List<Categorycombo> categorycomboList = categorycomboRepository.findAll();
        assertThat(categorycomboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCategorycombo() throws Exception {
        int databaseSizeBeforeUpdate = categorycomboRepository.findAll().size();
        categorycombo.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorycomboMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(categorycombo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Categorycombo in the database
        List<Categorycombo> categorycomboList = categorycomboRepository.findAll();
        assertThat(categorycomboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCategorycombo() throws Exception {
        // Initialize the database
        categorycombo.setId(UUID.randomUUID().toString());
        categorycomboRepository.saveAndFlush(categorycombo);

        int databaseSizeBeforeDelete = categorycomboRepository.findAll().size();

        // Delete the categorycombo
        restCategorycomboMockMvc
            .perform(delete(ENTITY_API_URL_ID, categorycombo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Categorycombo> categorycomboList = categorycomboRepository.findAll();
        assertThat(categorycomboList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
