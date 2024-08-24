package com.didate.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.didate.IntegrationTest;
import com.didate.domain.IndicatorType;
import com.didate.repository.IndicatortypeRepository;
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
 * Integration tests for the {@link IndicatortypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IndicatortypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/indicatortypes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private IndicatortypeRepository indicatortypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIndicatortypeMockMvc;

    private IndicatorType indicatortype;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndicatorType createEntity(EntityManager em) {
        IndicatorType indicatortype = new IndicatorType().name(DEFAULT_NAME);
        return indicatortype;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndicatorType createUpdatedEntity(EntityManager em) {
        IndicatorType indicatortype = new IndicatorType().name(UPDATED_NAME);
        return indicatortype;
    }

    @BeforeEach
    public void initTest() {
        indicatortype = createEntity(em);
    }

    @Test
    @Transactional
    void createIndicatortype() throws Exception {
        int databaseSizeBeforeCreate = indicatortypeRepository.findAll().size();
        // Create the Indicatortype
        restIndicatortypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indicatortype)))
            .andExpect(status().isCreated());

        // Validate the Indicatortype in the database
        List<IndicatorType> indicatortypeList = indicatortypeRepository.findAll();
        assertThat(indicatortypeList).hasSize(databaseSizeBeforeCreate + 1);
        IndicatorType testIndicatortype = indicatortypeList.get(indicatortypeList.size() - 1);
        assertThat(testIndicatortype.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createIndicatortypeWithExistingId() throws Exception {
        // Create the Indicatortype with an existing ID
        indicatortype.setId("existing_id");

        int databaseSizeBeforeCreate = indicatortypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndicatortypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indicatortype)))
            .andExpect(status().isBadRequest());

        // Validate the Indicatortype in the database
        List<IndicatorType> indicatortypeList = indicatortypeRepository.findAll();
        assertThat(indicatortypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIndicatortypes() throws Exception {
        // Initialize the database
        indicatortype.setId(UUID.randomUUID().toString());
        indicatortypeRepository.saveAndFlush(indicatortype);

        // Get all the indicatortypeList
        restIndicatortypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indicatortype.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getIndicatortype() throws Exception {
        // Initialize the database
        indicatortype.setId(UUID.randomUUID().toString());
        indicatortypeRepository.saveAndFlush(indicatortype);

        // Get the indicatortype
        restIndicatortypeMockMvc
            .perform(get(ENTITY_API_URL_ID, indicatortype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(indicatortype.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingIndicatortype() throws Exception {
        // Get the indicatortype
        restIndicatortypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIndicatortype() throws Exception {
        // Initialize the database
        indicatortype.setId(UUID.randomUUID().toString());
        indicatortypeRepository.saveAndFlush(indicatortype);

        int databaseSizeBeforeUpdate = indicatortypeRepository.findAll().size();

        // Update the indicatortype
        IndicatorType updatedIndicatortype = indicatortypeRepository.findById(indicatortype.getId()).get();
        // Disconnect from session so that the updates on updatedIndicatortype are not directly saved in db
        em.detach(updatedIndicatortype);
        updatedIndicatortype.name(UPDATED_NAME);

        restIndicatortypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIndicatortype.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedIndicatortype))
            )
            .andExpect(status().isOk());

        // Validate the Indicatortype in the database
        List<IndicatorType> indicatortypeList = indicatortypeRepository.findAll();
        assertThat(indicatortypeList).hasSize(databaseSizeBeforeUpdate);
        IndicatorType testIndicatortype = indicatortypeList.get(indicatortypeList.size() - 1);
        assertThat(testIndicatortype.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingIndicatortype() throws Exception {
        int databaseSizeBeforeUpdate = indicatortypeRepository.findAll().size();
        indicatortype.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndicatortypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, indicatortype.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indicatortype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Indicatortype in the database
        List<IndicatorType> indicatortypeList = indicatortypeRepository.findAll();
        assertThat(indicatortypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIndicatortype() throws Exception {
        int databaseSizeBeforeUpdate = indicatortypeRepository.findAll().size();
        indicatortype.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndicatortypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indicatortype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Indicatortype in the database
        List<IndicatorType> indicatortypeList = indicatortypeRepository.findAll();
        assertThat(indicatortypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIndicatortype() throws Exception {
        int databaseSizeBeforeUpdate = indicatortypeRepository.findAll().size();
        indicatortype.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndicatortypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indicatortype)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Indicatortype in the database
        List<IndicatorType> indicatortypeList = indicatortypeRepository.findAll();
        assertThat(indicatortypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIndicatortypeWithPatch() throws Exception {
        // Initialize the database
        indicatortype.setId(UUID.randomUUID().toString());
        indicatortypeRepository.saveAndFlush(indicatortype);

        int databaseSizeBeforeUpdate = indicatortypeRepository.findAll().size();

        // Update the indicatortype using partial update
        IndicatorType partialUpdatedIndicatortype = new IndicatorType();
        partialUpdatedIndicatortype.setId(indicatortype.getId());

        restIndicatortypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndicatortype.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndicatortype))
            )
            .andExpect(status().isOk());

        // Validate the Indicatortype in the database
        List<IndicatorType> indicatortypeList = indicatortypeRepository.findAll();
        assertThat(indicatortypeList).hasSize(databaseSizeBeforeUpdate);
        IndicatorType testIndicatortype = indicatortypeList.get(indicatortypeList.size() - 1);
        assertThat(testIndicatortype.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateIndicatortypeWithPatch() throws Exception {
        // Initialize the database
        indicatortype.setId(UUID.randomUUID().toString());
        indicatortypeRepository.saveAndFlush(indicatortype);

        int databaseSizeBeforeUpdate = indicatortypeRepository.findAll().size();

        // Update the indicatortype using partial update
        IndicatorType partialUpdatedIndicatortype = new IndicatorType();
        partialUpdatedIndicatortype.setId(indicatortype.getId());

        partialUpdatedIndicatortype.name(UPDATED_NAME);

        restIndicatortypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndicatortype.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndicatortype))
            )
            .andExpect(status().isOk());

        // Validate the Indicatortype in the database
        List<IndicatorType> indicatortypeList = indicatortypeRepository.findAll();
        assertThat(indicatortypeList).hasSize(databaseSizeBeforeUpdate);
        IndicatorType testIndicatortype = indicatortypeList.get(indicatortypeList.size() - 1);
        assertThat(testIndicatortype.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingIndicatortype() throws Exception {
        int databaseSizeBeforeUpdate = indicatortypeRepository.findAll().size();
        indicatortype.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndicatortypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, indicatortype.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indicatortype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Indicatortype in the database
        List<IndicatorType> indicatortypeList = indicatortypeRepository.findAll();
        assertThat(indicatortypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIndicatortype() throws Exception {
        int databaseSizeBeforeUpdate = indicatortypeRepository.findAll().size();
        indicatortype.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndicatortypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indicatortype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Indicatortype in the database
        List<IndicatorType> indicatortypeList = indicatortypeRepository.findAll();
        assertThat(indicatortypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIndicatortype() throws Exception {
        int databaseSizeBeforeUpdate = indicatortypeRepository.findAll().size();
        indicatortype.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndicatortypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(indicatortype))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Indicatortype in the database
        List<IndicatorType> indicatortypeList = indicatortypeRepository.findAll();
        assertThat(indicatortypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIndicatortype() throws Exception {
        // Initialize the database
        indicatortype.setId(UUID.randomUUID().toString());
        indicatortypeRepository.saveAndFlush(indicatortype);

        int databaseSizeBeforeDelete = indicatortypeRepository.findAll().size();

        // Delete the indicatortype
        restIndicatortypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, indicatortype.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IndicatorType> indicatortypeList = indicatortypeRepository.findAll();
        assertThat(indicatortypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
