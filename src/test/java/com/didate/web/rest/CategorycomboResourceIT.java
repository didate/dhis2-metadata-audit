package com.didate.web.rest;

import static com.didate.domain.CategorycomboAsserts.*;
import static com.didate.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.didate.IntegrationTest;
import com.didate.domain.Categorycombo;
import com.didate.repository.CategorycomboRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
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
    private ObjectMapper om;

    @Autowired
    private CategorycomboRepository categorycomboRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategorycomboMockMvc;

    private Categorycombo categorycombo;

    private Categorycombo insertedCategorycombo;

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

    @AfterEach
    public void cleanup() {
        if (insertedCategorycombo != null) {
            categorycomboRepository.delete(insertedCategorycombo);
            insertedCategorycombo = null;
        }
    }

    @Test
    @Transactional
    void createCategorycombo() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Categorycombo
        var returnedCategorycombo = om.readValue(
            restCategorycomboMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(categorycombo)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Categorycombo.class
        );

        // Validate the Categorycombo in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCategorycomboUpdatableFieldsEquals(returnedCategorycombo, getPersistedCategorycombo(returnedCategorycombo));

        insertedCategorycombo = returnedCategorycombo;
    }

    @Test
    @Transactional
    void createCategorycomboWithExistingId() throws Exception {
        // Create the Categorycombo with an existing ID
        categorycombo.setId("existing_id");

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategorycomboMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(categorycombo)))
            .andExpect(status().isBadRequest());

        // Validate the Categorycombo in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCategorycombos() throws Exception {
        // Initialize the database
        insertedCategorycombo = categorycomboRepository.saveAndFlush(categorycombo);

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
        insertedCategorycombo = categorycomboRepository.saveAndFlush(categorycombo);

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
        insertedCategorycombo = categorycomboRepository.saveAndFlush(categorycombo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the categorycombo
        Categorycombo updatedCategorycombo = categorycomboRepository.findById(categorycombo.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCategorycombo are not directly saved in db
        em.detach(updatedCategorycombo);
        updatedCategorycombo.name(UPDATED_NAME);

        restCategorycomboMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCategorycombo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCategorycombo))
            )
            .andExpect(status().isOk());

        // Validate the Categorycombo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCategorycomboToMatchAllProperties(updatedCategorycombo);
    }

    @Test
    @Transactional
    void putNonExistingCategorycombo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        categorycombo.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategorycomboMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categorycombo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(categorycombo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Categorycombo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCategorycombo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        categorycombo.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorycomboMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(categorycombo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Categorycombo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCategorycombo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        categorycombo.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorycomboMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(categorycombo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Categorycombo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCategorycomboWithPatch() throws Exception {
        // Initialize the database
        insertedCategorycombo = categorycomboRepository.saveAndFlush(categorycombo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the categorycombo using partial update
        Categorycombo partialUpdatedCategorycombo = new Categorycombo();
        partialUpdatedCategorycombo.setId(categorycombo.getId());

        restCategorycomboMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategorycombo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCategorycombo))
            )
            .andExpect(status().isOk());

        // Validate the Categorycombo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCategorycomboUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCategorycombo, categorycombo),
            getPersistedCategorycombo(categorycombo)
        );
    }

    @Test
    @Transactional
    void fullUpdateCategorycomboWithPatch() throws Exception {
        // Initialize the database
        insertedCategorycombo = categorycomboRepository.saveAndFlush(categorycombo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the categorycombo using partial update
        Categorycombo partialUpdatedCategorycombo = new Categorycombo();
        partialUpdatedCategorycombo.setId(categorycombo.getId());

        partialUpdatedCategorycombo.name(UPDATED_NAME);

        restCategorycomboMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategorycombo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCategorycombo))
            )
            .andExpect(status().isOk());

        // Validate the Categorycombo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCategorycomboUpdatableFieldsEquals(partialUpdatedCategorycombo, getPersistedCategorycombo(partialUpdatedCategorycombo));
    }

    @Test
    @Transactional
    void patchNonExistingCategorycombo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        categorycombo.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategorycomboMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, categorycombo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(categorycombo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Categorycombo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCategorycombo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        categorycombo.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorycomboMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(categorycombo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Categorycombo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCategorycombo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        categorycombo.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorycomboMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(categorycombo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Categorycombo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCategorycombo() throws Exception {
        // Initialize the database
        insertedCategorycombo = categorycomboRepository.saveAndFlush(categorycombo);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the categorycombo
        restCategorycomboMockMvc
            .perform(delete(ENTITY_API_URL_ID, categorycombo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return categorycomboRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Categorycombo getPersistedCategorycombo(Categorycombo categorycombo) {
        return categorycomboRepository.findById(categorycombo.getId()).orElseThrow();
    }

    protected void assertPersistedCategorycomboToMatchAllProperties(Categorycombo expectedCategorycombo) {
        assertCategorycomboAllPropertiesEquals(expectedCategorycombo, getPersistedCategorycombo(expectedCategorycombo));
    }

    protected void assertPersistedCategorycomboToMatchUpdatableProperties(Categorycombo expectedCategorycombo) {
        assertCategorycomboAllUpdatablePropertiesEquals(expectedCategorycombo, getPersistedCategorycombo(expectedCategorycombo));
    }
}
