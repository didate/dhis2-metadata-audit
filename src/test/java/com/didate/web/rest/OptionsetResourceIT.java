package com.didate.web.rest;

import static com.didate.domain.OptionsetAsserts.*;
import static com.didate.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.didate.IntegrationTest;
import com.didate.domain.Optionset;
import com.didate.repository.OptionsetRepository;
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
 * Integration tests for the {@link OptionsetResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OptionsetResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/optionsets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OptionsetRepository optionsetRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOptionsetMockMvc;

    private Optionset optionset;

    private Optionset insertedOptionset;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Optionset createEntity(EntityManager em) {
        Optionset optionset = new Optionset().name(DEFAULT_NAME);
        return optionset;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Optionset createUpdatedEntity(EntityManager em) {
        Optionset optionset = new Optionset().name(UPDATED_NAME);
        return optionset;
    }

    @BeforeEach
    public void initTest() {
        optionset = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedOptionset != null) {
            optionsetRepository.delete(insertedOptionset);
            insertedOptionset = null;
        }
    }

    @Test
    @Transactional
    void createOptionset() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Optionset
        var returnedOptionset = om.readValue(
            restOptionsetMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(optionset)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Optionset.class
        );

        // Validate the Optionset in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOptionsetUpdatableFieldsEquals(returnedOptionset, getPersistedOptionset(returnedOptionset));

        insertedOptionset = returnedOptionset;
    }

    @Test
    @Transactional
    void createOptionsetWithExistingId() throws Exception {
        // Create the Optionset with an existing ID
        optionset.setId("existing_id");

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOptionsetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(optionset)))
            .andExpect(status().isBadRequest());

        // Validate the Optionset in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOptionsets() throws Exception {
        // Initialize the database
        insertedOptionset = optionsetRepository.saveAndFlush(optionset);

        // Get all the optionsetList
        restOptionsetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(optionset.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getOptionset() throws Exception {
        // Initialize the database
        insertedOptionset = optionsetRepository.saveAndFlush(optionset);

        // Get the optionset
        restOptionsetMockMvc
            .perform(get(ENTITY_API_URL_ID, optionset.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(optionset.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingOptionset() throws Exception {
        // Get the optionset
        restOptionsetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOptionset() throws Exception {
        // Initialize the database
        insertedOptionset = optionsetRepository.saveAndFlush(optionset);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the optionset
        Optionset updatedOptionset = optionsetRepository.findById(optionset.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOptionset are not directly saved in db
        em.detach(updatedOptionset);
        updatedOptionset.name(UPDATED_NAME);

        restOptionsetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOptionset.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOptionset))
            )
            .andExpect(status().isOk());

        // Validate the Optionset in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOptionsetToMatchAllProperties(updatedOptionset);
    }

    @Test
    @Transactional
    void putNonExistingOptionset() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        optionset.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOptionsetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, optionset.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(optionset))
            )
            .andExpect(status().isBadRequest());

        // Validate the Optionset in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOptionset() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        optionset.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOptionsetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(optionset))
            )
            .andExpect(status().isBadRequest());

        // Validate the Optionset in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOptionset() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        optionset.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOptionsetMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(optionset)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Optionset in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOptionsetWithPatch() throws Exception {
        // Initialize the database
        insertedOptionset = optionsetRepository.saveAndFlush(optionset);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the optionset using partial update
        Optionset partialUpdatedOptionset = new Optionset();
        partialUpdatedOptionset.setId(optionset.getId());

        partialUpdatedOptionset.name(UPDATED_NAME);

        restOptionsetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOptionset.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOptionset))
            )
            .andExpect(status().isOk());

        // Validate the Optionset in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOptionsetUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOptionset, optionset),
            getPersistedOptionset(optionset)
        );
    }

    @Test
    @Transactional
    void fullUpdateOptionsetWithPatch() throws Exception {
        // Initialize the database
        insertedOptionset = optionsetRepository.saveAndFlush(optionset);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the optionset using partial update
        Optionset partialUpdatedOptionset = new Optionset();
        partialUpdatedOptionset.setId(optionset.getId());

        partialUpdatedOptionset.name(UPDATED_NAME);

        restOptionsetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOptionset.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOptionset))
            )
            .andExpect(status().isOk());

        // Validate the Optionset in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOptionsetUpdatableFieldsEquals(partialUpdatedOptionset, getPersistedOptionset(partialUpdatedOptionset));
    }

    @Test
    @Transactional
    void patchNonExistingOptionset() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        optionset.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOptionsetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, optionset.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(optionset))
            )
            .andExpect(status().isBadRequest());

        // Validate the Optionset in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOptionset() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        optionset.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOptionsetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(optionset))
            )
            .andExpect(status().isBadRequest());

        // Validate the Optionset in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOptionset() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        optionset.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOptionsetMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(optionset)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Optionset in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOptionset() throws Exception {
        // Initialize the database
        insertedOptionset = optionsetRepository.saveAndFlush(optionset);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the optionset
        restOptionsetMockMvc
            .perform(delete(ENTITY_API_URL_ID, optionset.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return optionsetRepository.count();
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

    protected Optionset getPersistedOptionset(Optionset optionset) {
        return optionsetRepository.findById(optionset.getId()).orElseThrow();
    }

    protected void assertPersistedOptionsetToMatchAllProperties(Optionset expectedOptionset) {
        assertOptionsetAllPropertiesEquals(expectedOptionset, getPersistedOptionset(expectedOptionset));
    }

    protected void assertPersistedOptionsetToMatchUpdatableProperties(Optionset expectedOptionset) {
        assertOptionsetAllUpdatablePropertiesEquals(expectedOptionset, getPersistedOptionset(expectedOptionset));
    }
}
