package com.didate.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.didate.IntegrationTest;
import com.didate.domain.OptionGroup;
import com.didate.repository.OptionGroupRepository;
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
 * Integration tests for the {@link OptionGroupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OptionGroupResourceIT {

    private static final String ENTITY_API_URL = "/api/option-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private OptionGroupRepository optionGroupRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOptionGroupMockMvc;

    private OptionGroup optionGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OptionGroup createEntity(EntityManager em) {
        OptionGroup optionGroup = new OptionGroup();
        return optionGroup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OptionGroup createUpdatedEntity(EntityManager em) {
        OptionGroup optionGroup = new OptionGroup();
        return optionGroup;
    }

    @BeforeEach
    public void initTest() {
        optionGroup = createEntity(em);
    }

    @Test
    @Transactional
    void createOptionGroup() throws Exception {
        int databaseSizeBeforeCreate = optionGroupRepository.findAll().size();
        // Create the OptionGroup
        restOptionGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(optionGroup)))
            .andExpect(status().isCreated());

        // Validate the OptionGroup in the database
        List<OptionGroup> optionGroupList = optionGroupRepository.findAll();
        assertThat(optionGroupList).hasSize(databaseSizeBeforeCreate + 1);
        OptionGroup testOptionGroup = optionGroupList.get(optionGroupList.size() - 1);
    }

    @Test
    @Transactional
    void createOptionGroupWithExistingId() throws Exception {
        // Create the OptionGroup with an existing ID
        optionGroup.setId("existing_id");

        int databaseSizeBeforeCreate = optionGroupRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOptionGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(optionGroup)))
            .andExpect(status().isBadRequest());

        // Validate the OptionGroup in the database
        List<OptionGroup> optionGroupList = optionGroupRepository.findAll();
        assertThat(optionGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOptionGroups() throws Exception {
        // Initialize the database
        optionGroup.setId(UUID.randomUUID().toString());
        optionGroupRepository.saveAndFlush(optionGroup);

        // Get all the optionGroupList
        restOptionGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(optionGroup.getId())));
    }

    @Test
    @Transactional
    void getOptionGroup() throws Exception {
        // Initialize the database
        optionGroup.setId(UUID.randomUUID().toString());
        optionGroupRepository.saveAndFlush(optionGroup);

        // Get the optionGroup
        restOptionGroupMockMvc
            .perform(get(ENTITY_API_URL_ID, optionGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(optionGroup.getId()));
    }

    @Test
    @Transactional
    void getNonExistingOptionGroup() throws Exception {
        // Get the optionGroup
        restOptionGroupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOptionGroup() throws Exception {
        // Initialize the database
        optionGroup.setId(UUID.randomUUID().toString());
        optionGroupRepository.saveAndFlush(optionGroup);

        int databaseSizeBeforeUpdate = optionGroupRepository.findAll().size();

        // Update the optionGroup
        OptionGroup updatedOptionGroup = optionGroupRepository.findById(optionGroup.getId()).get();
        // Disconnect from session so that the updates on updatedOptionGroup are not directly saved in db
        em.detach(updatedOptionGroup);

        restOptionGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOptionGroup.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOptionGroup))
            )
            .andExpect(status().isOk());

        // Validate the OptionGroup in the database
        List<OptionGroup> optionGroupList = optionGroupRepository.findAll();
        assertThat(optionGroupList).hasSize(databaseSizeBeforeUpdate);
        OptionGroup testOptionGroup = optionGroupList.get(optionGroupList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingOptionGroup() throws Exception {
        int databaseSizeBeforeUpdate = optionGroupRepository.findAll().size();
        optionGroup.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOptionGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, optionGroup.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(optionGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the OptionGroup in the database
        List<OptionGroup> optionGroupList = optionGroupRepository.findAll();
        assertThat(optionGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOptionGroup() throws Exception {
        int databaseSizeBeforeUpdate = optionGroupRepository.findAll().size();
        optionGroup.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOptionGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(optionGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the OptionGroup in the database
        List<OptionGroup> optionGroupList = optionGroupRepository.findAll();
        assertThat(optionGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOptionGroup() throws Exception {
        int databaseSizeBeforeUpdate = optionGroupRepository.findAll().size();
        optionGroup.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOptionGroupMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(optionGroup)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OptionGroup in the database
        List<OptionGroup> optionGroupList = optionGroupRepository.findAll();
        assertThat(optionGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOptionGroupWithPatch() throws Exception {
        // Initialize the database
        optionGroup.setId(UUID.randomUUID().toString());
        optionGroupRepository.saveAndFlush(optionGroup);

        int databaseSizeBeforeUpdate = optionGroupRepository.findAll().size();

        // Update the optionGroup using partial update
        OptionGroup partialUpdatedOptionGroup = new OptionGroup();
        partialUpdatedOptionGroup.setId(optionGroup.getId());

        restOptionGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOptionGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOptionGroup))
            )
            .andExpect(status().isOk());

        // Validate the OptionGroup in the database
        List<OptionGroup> optionGroupList = optionGroupRepository.findAll();
        assertThat(optionGroupList).hasSize(databaseSizeBeforeUpdate);
        OptionGroup testOptionGroup = optionGroupList.get(optionGroupList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateOptionGroupWithPatch() throws Exception {
        // Initialize the database
        optionGroup.setId(UUID.randomUUID().toString());
        optionGroupRepository.saveAndFlush(optionGroup);

        int databaseSizeBeforeUpdate = optionGroupRepository.findAll().size();

        // Update the optionGroup using partial update
        OptionGroup partialUpdatedOptionGroup = new OptionGroup();
        partialUpdatedOptionGroup.setId(optionGroup.getId());

        restOptionGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOptionGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOptionGroup))
            )
            .andExpect(status().isOk());

        // Validate the OptionGroup in the database
        List<OptionGroup> optionGroupList = optionGroupRepository.findAll();
        assertThat(optionGroupList).hasSize(databaseSizeBeforeUpdate);
        OptionGroup testOptionGroup = optionGroupList.get(optionGroupList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingOptionGroup() throws Exception {
        int databaseSizeBeforeUpdate = optionGroupRepository.findAll().size();
        optionGroup.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOptionGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, optionGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(optionGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the OptionGroup in the database
        List<OptionGroup> optionGroupList = optionGroupRepository.findAll();
        assertThat(optionGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOptionGroup() throws Exception {
        int databaseSizeBeforeUpdate = optionGroupRepository.findAll().size();
        optionGroup.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOptionGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(optionGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the OptionGroup in the database
        List<OptionGroup> optionGroupList = optionGroupRepository.findAll();
        assertThat(optionGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOptionGroup() throws Exception {
        int databaseSizeBeforeUpdate = optionGroupRepository.findAll().size();
        optionGroup.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOptionGroupMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(optionGroup))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OptionGroup in the database
        List<OptionGroup> optionGroupList = optionGroupRepository.findAll();
        assertThat(optionGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOptionGroup() throws Exception {
        // Initialize the database
        optionGroup.setId(UUID.randomUUID().toString());
        optionGroupRepository.saveAndFlush(optionGroup);

        int databaseSizeBeforeDelete = optionGroupRepository.findAll().size();

        // Delete the optionGroup
        restOptionGroupMockMvc
            .perform(delete(ENTITY_API_URL_ID, optionGroup.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OptionGroup> optionGroupList = optionGroupRepository.findAll();
        assertThat(optionGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
