package com.didate.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.didate.IntegrationTest;
import com.didate.domain.Optionset;
import com.didate.repository.OptionsetRepository;
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
    private OptionsetRepository optionsetRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOptionsetMockMvc;

    private Optionset optionset;

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

    @Test
    @Transactional
    void createOptionset() throws Exception {
        int databaseSizeBeforeCreate = optionsetRepository.findAll().size();
        // Create the Optionset
        restOptionsetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(optionset)))
            .andExpect(status().isCreated());

        // Validate the Optionset in the database
        List<Optionset> optionsetList = optionsetRepository.findAll();
        assertThat(optionsetList).hasSize(databaseSizeBeforeCreate + 1);
        Optionset testOptionset = optionsetList.get(optionsetList.size() - 1);
        assertThat(testOptionset.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createOptionsetWithExistingId() throws Exception {
        // Create the Optionset with an existing ID
        optionset.setId("existing_id");

        int databaseSizeBeforeCreate = optionsetRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOptionsetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(optionset)))
            .andExpect(status().isBadRequest());

        // Validate the Optionset in the database
        List<Optionset> optionsetList = optionsetRepository.findAll();
        assertThat(optionsetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOptionsets() throws Exception {
        // Initialize the database
        optionset.setId(UUID.randomUUID().toString());
        optionsetRepository.saveAndFlush(optionset);

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
        optionset.setId(UUID.randomUUID().toString());
        optionsetRepository.saveAndFlush(optionset);

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
        optionset.setId(UUID.randomUUID().toString());
        optionsetRepository.saveAndFlush(optionset);

        int databaseSizeBeforeUpdate = optionsetRepository.findAll().size();

        // Update the optionset
        Optionset updatedOptionset = optionsetRepository.findById(optionset.getId()).get();
        // Disconnect from session so that the updates on updatedOptionset are not directly saved in db
        em.detach(updatedOptionset);
        updatedOptionset.name(UPDATED_NAME);

        restOptionsetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOptionset.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOptionset))
            )
            .andExpect(status().isOk());

        // Validate the Optionset in the database
        List<Optionset> optionsetList = optionsetRepository.findAll();
        assertThat(optionsetList).hasSize(databaseSizeBeforeUpdate);
        Optionset testOptionset = optionsetList.get(optionsetList.size() - 1);
        assertThat(testOptionset.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingOptionset() throws Exception {
        int databaseSizeBeforeUpdate = optionsetRepository.findAll().size();
        optionset.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOptionsetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, optionset.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(optionset))
            )
            .andExpect(status().isBadRequest());

        // Validate the Optionset in the database
        List<Optionset> optionsetList = optionsetRepository.findAll();
        assertThat(optionsetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOptionset() throws Exception {
        int databaseSizeBeforeUpdate = optionsetRepository.findAll().size();
        optionset.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOptionsetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(optionset))
            )
            .andExpect(status().isBadRequest());

        // Validate the Optionset in the database
        List<Optionset> optionsetList = optionsetRepository.findAll();
        assertThat(optionsetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOptionset() throws Exception {
        int databaseSizeBeforeUpdate = optionsetRepository.findAll().size();
        optionset.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOptionsetMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(optionset)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Optionset in the database
        List<Optionset> optionsetList = optionsetRepository.findAll();
        assertThat(optionsetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOptionsetWithPatch() throws Exception {
        // Initialize the database
        optionset.setId(UUID.randomUUID().toString());
        optionsetRepository.saveAndFlush(optionset);

        int databaseSizeBeforeUpdate = optionsetRepository.findAll().size();

        // Update the optionset using partial update
        Optionset partialUpdatedOptionset = new Optionset();
        partialUpdatedOptionset.setId(optionset.getId());

        partialUpdatedOptionset.name(UPDATED_NAME);

        restOptionsetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOptionset.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOptionset))
            )
            .andExpect(status().isOk());

        // Validate the Optionset in the database
        List<Optionset> optionsetList = optionsetRepository.findAll();
        assertThat(optionsetList).hasSize(databaseSizeBeforeUpdate);
        Optionset testOptionset = optionsetList.get(optionsetList.size() - 1);
        assertThat(testOptionset.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateOptionsetWithPatch() throws Exception {
        // Initialize the database
        optionset.setId(UUID.randomUUID().toString());
        optionsetRepository.saveAndFlush(optionset);

        int databaseSizeBeforeUpdate = optionsetRepository.findAll().size();

        // Update the optionset using partial update
        Optionset partialUpdatedOptionset = new Optionset();
        partialUpdatedOptionset.setId(optionset.getId());

        partialUpdatedOptionset.name(UPDATED_NAME);

        restOptionsetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOptionset.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOptionset))
            )
            .andExpect(status().isOk());

        // Validate the Optionset in the database
        List<Optionset> optionsetList = optionsetRepository.findAll();
        assertThat(optionsetList).hasSize(databaseSizeBeforeUpdate);
        Optionset testOptionset = optionsetList.get(optionsetList.size() - 1);
        assertThat(testOptionset.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingOptionset() throws Exception {
        int databaseSizeBeforeUpdate = optionsetRepository.findAll().size();
        optionset.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOptionsetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, optionset.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(optionset))
            )
            .andExpect(status().isBadRequest());

        // Validate the Optionset in the database
        List<Optionset> optionsetList = optionsetRepository.findAll();
        assertThat(optionsetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOptionset() throws Exception {
        int databaseSizeBeforeUpdate = optionsetRepository.findAll().size();
        optionset.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOptionsetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(optionset))
            )
            .andExpect(status().isBadRequest());

        // Validate the Optionset in the database
        List<Optionset> optionsetList = optionsetRepository.findAll();
        assertThat(optionsetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOptionset() throws Exception {
        int databaseSizeBeforeUpdate = optionsetRepository.findAll().size();
        optionset.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOptionsetMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(optionset))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Optionset in the database
        List<Optionset> optionsetList = optionsetRepository.findAll();
        assertThat(optionsetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOptionset() throws Exception {
        // Initialize the database
        optionset.setId(UUID.randomUUID().toString());
        optionsetRepository.saveAndFlush(optionset);

        int databaseSizeBeforeDelete = optionsetRepository.findAll().size();

        // Delete the optionset
        restOptionsetMockMvc
            .perform(delete(ENTITY_API_URL_ID, optionset.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Optionset> optionsetList = optionsetRepository.findAll();
        assertThat(optionsetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
