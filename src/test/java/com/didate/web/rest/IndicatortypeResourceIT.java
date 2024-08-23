package com.didate.web.rest;

import static com.didate.domain.IndicatortypeAsserts.*;
import static com.didate.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.didate.IntegrationTest;
import com.didate.domain.Indicatortype;
import com.didate.repository.IndicatortypeRepository;
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
    private ObjectMapper om;

    @Autowired
    private IndicatortypeRepository indicatortypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIndicatortypeMockMvc;

    private Indicatortype indicatortype;

    private Indicatortype insertedIndicatortype;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Indicatortype createEntity(EntityManager em) {
        Indicatortype indicatortype = new Indicatortype().name(DEFAULT_NAME);
        return indicatortype;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Indicatortype createUpdatedEntity(EntityManager em) {
        Indicatortype indicatortype = new Indicatortype().name(UPDATED_NAME);
        return indicatortype;
    }

    @BeforeEach
    public void initTest() {
        indicatortype = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedIndicatortype != null) {
            indicatortypeRepository.delete(insertedIndicatortype);
            insertedIndicatortype = null;
        }
    }

    @Test
    @Transactional
    void createIndicatortype() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Indicatortype
        var returnedIndicatortype = om.readValue(
            restIndicatortypeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(indicatortype)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Indicatortype.class
        );

        // Validate the Indicatortype in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertIndicatortypeUpdatableFieldsEquals(returnedIndicatortype, getPersistedIndicatortype(returnedIndicatortype));

        insertedIndicatortype = returnedIndicatortype;
    }

    @Test
    @Transactional
    void createIndicatortypeWithExistingId() throws Exception {
        // Create the Indicatortype with an existing ID
        indicatortype.setId("existing_id");

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndicatortypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(indicatortype)))
            .andExpect(status().isBadRequest());

        // Validate the Indicatortype in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIndicatortypes() throws Exception {
        // Initialize the database
        insertedIndicatortype = indicatortypeRepository.saveAndFlush(indicatortype);

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
        insertedIndicatortype = indicatortypeRepository.saveAndFlush(indicatortype);

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
        insertedIndicatortype = indicatortypeRepository.saveAndFlush(indicatortype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the indicatortype
        Indicatortype updatedIndicatortype = indicatortypeRepository.findById(indicatortype.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedIndicatortype are not directly saved in db
        em.detach(updatedIndicatortype);
        updatedIndicatortype.name(UPDATED_NAME);

        restIndicatortypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIndicatortype.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedIndicatortype))
            )
            .andExpect(status().isOk());

        // Validate the Indicatortype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedIndicatortypeToMatchAllProperties(updatedIndicatortype);
    }

    @Test
    @Transactional
    void putNonExistingIndicatortype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        indicatortype.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndicatortypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, indicatortype.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(indicatortype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Indicatortype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIndicatortype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        indicatortype.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndicatortypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(indicatortype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Indicatortype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIndicatortype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        indicatortype.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndicatortypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(indicatortype)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Indicatortype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIndicatortypeWithPatch() throws Exception {
        // Initialize the database
        insertedIndicatortype = indicatortypeRepository.saveAndFlush(indicatortype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the indicatortype using partial update
        Indicatortype partialUpdatedIndicatortype = new Indicatortype();
        partialUpdatedIndicatortype.setId(indicatortype.getId());

        partialUpdatedIndicatortype.name(UPDATED_NAME);

        restIndicatortypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndicatortype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIndicatortype))
            )
            .andExpect(status().isOk());

        // Validate the Indicatortype in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIndicatortypeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedIndicatortype, indicatortype),
            getPersistedIndicatortype(indicatortype)
        );
    }

    @Test
    @Transactional
    void fullUpdateIndicatortypeWithPatch() throws Exception {
        // Initialize the database
        insertedIndicatortype = indicatortypeRepository.saveAndFlush(indicatortype);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the indicatortype using partial update
        Indicatortype partialUpdatedIndicatortype = new Indicatortype();
        partialUpdatedIndicatortype.setId(indicatortype.getId());

        partialUpdatedIndicatortype.name(UPDATED_NAME);

        restIndicatortypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndicatortype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIndicatortype))
            )
            .andExpect(status().isOk());

        // Validate the Indicatortype in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIndicatortypeUpdatableFieldsEquals(partialUpdatedIndicatortype, getPersistedIndicatortype(partialUpdatedIndicatortype));
    }

    @Test
    @Transactional
    void patchNonExistingIndicatortype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        indicatortype.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndicatortypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, indicatortype.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(indicatortype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Indicatortype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIndicatortype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        indicatortype.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndicatortypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(indicatortype))
            )
            .andExpect(status().isBadRequest());

        // Validate the Indicatortype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIndicatortype() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        indicatortype.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndicatortypeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(indicatortype)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Indicatortype in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIndicatortype() throws Exception {
        // Initialize the database
        insertedIndicatortype = indicatortypeRepository.saveAndFlush(indicatortype);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the indicatortype
        restIndicatortypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, indicatortype.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return indicatortypeRepository.count();
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

    protected Indicatortype getPersistedIndicatortype(Indicatortype indicatortype) {
        return indicatortypeRepository.findById(indicatortype.getId()).orElseThrow();
    }

    protected void assertPersistedIndicatortypeToMatchAllProperties(Indicatortype expectedIndicatortype) {
        assertIndicatortypeAllPropertiesEquals(expectedIndicatortype, getPersistedIndicatortype(expectedIndicatortype));
    }

    protected void assertPersistedIndicatortypeToMatchUpdatableProperties(Indicatortype expectedIndicatortype) {
        assertIndicatortypeAllUpdatablePropertiesEquals(expectedIndicatortype, getPersistedIndicatortype(expectedIndicatortype));
    }
}
