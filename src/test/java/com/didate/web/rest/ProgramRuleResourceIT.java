package com.didate.web.rest;

import static com.didate.domain.ProgramRuleAsserts.*;
import static com.didate.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.didate.IntegrationTest;
import com.didate.domain.DHISUser;
import com.didate.domain.Program;
import com.didate.domain.ProgramRule;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.repository.ProgramRuleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link ProgramRuleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProgramRuleResourceIT {

    private static final Instant DEFAULT_LAST_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRIORITY = 1;
    private static final Integer UPDATED_PRIORITY = 2;

    private static final String DEFAULT_CONDITION = "AAAAAAAAAA";
    private static final String UPDATED_CONDITION = "BBBBBBBBBB";

    private static final TypeTrack DEFAULT_TRACK = TypeTrack.NEW;
    private static final TypeTrack UPDATED_TRACK = TypeTrack.UPDATE;

    private static final String ENTITY_API_URL = "/api/program-rules";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProgramRuleRepository programRuleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProgramRuleMockMvc;

    private ProgramRule programRule;

    private ProgramRule insertedProgramRule;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProgramRule createEntity(EntityManager em) {
        ProgramRule programRule = new ProgramRule()
            .lastUpdated(DEFAULT_LAST_UPDATED)
            .created(DEFAULT_CREATED)
            .name(DEFAULT_NAME)
            .displayName(DEFAULT_DISPLAY_NAME)
            .priority(DEFAULT_PRIORITY)
            .condition(DEFAULT_CONDITION)
            .track(DEFAULT_TRACK);
        // Add required entity
        DHISUser dHISUser;
        if (TestUtil.findAll(em, DHISUser.class).isEmpty()) {
            dHISUser = DHISUserResourceIT.createEntity(em);
            em.persist(dHISUser);
            em.flush();
        } else {
            dHISUser = TestUtil.findAll(em, DHISUser.class).get(0);
        }
        programRule.setCreatedBy(dHISUser);
        // Add required entity
        programRule.setLastUpdatedBy(dHISUser);
        // Add required entity
        Program program;
        if (TestUtil.findAll(em, Program.class).isEmpty()) {
            program = ProgramResourceIT.createEntity(em);
            em.persist(program);
            em.flush();
        } else {
            program = TestUtil.findAll(em, Program.class).get(0);
        }
        programRule.setProgram(program);
        return programRule;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProgramRule createUpdatedEntity(EntityManager em) {
        ProgramRule programRule = new ProgramRule()
            .lastUpdated(UPDATED_LAST_UPDATED)
            .created(UPDATED_CREATED)
            .name(UPDATED_NAME)
            .displayName(UPDATED_DISPLAY_NAME)
            .priority(UPDATED_PRIORITY)
            .condition(UPDATED_CONDITION)
            .track(UPDATED_TRACK);
        // Add required entity
        DHISUser dHISUser;
        if (TestUtil.findAll(em, DHISUser.class).isEmpty()) {
            dHISUser = DHISUserResourceIT.createUpdatedEntity(em);
            em.persist(dHISUser);
            em.flush();
        } else {
            dHISUser = TestUtil.findAll(em, DHISUser.class).get(0);
        }
        programRule.setCreatedBy(dHISUser);
        // Add required entity
        programRule.setLastUpdatedBy(dHISUser);
        // Add required entity
        Program program;
        if (TestUtil.findAll(em, Program.class).isEmpty()) {
            program = ProgramResourceIT.createUpdatedEntity(em);
            em.persist(program);
            em.flush();
        } else {
            program = TestUtil.findAll(em, Program.class).get(0);
        }
        programRule.setProgram(program);
        return programRule;
    }

    @BeforeEach
    public void initTest() {
        programRule = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedProgramRule != null) {
            programRuleRepository.delete(insertedProgramRule);
            insertedProgramRule = null;
        }
    }

    @Test
    @Transactional
    void createProgramRule() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ProgramRule
        var returnedProgramRule = om.readValue(
            restProgramRuleMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(programRule)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ProgramRule.class
        );

        // Validate the ProgramRule in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertProgramRuleUpdatableFieldsEquals(returnedProgramRule, getPersistedProgramRule(returnedProgramRule));

        insertedProgramRule = returnedProgramRule;
    }

    @Test
    @Transactional
    void createProgramRuleWithExistingId() throws Exception {
        // Create the ProgramRule with an existing ID
        programRule.setId("existing_id");

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProgramRuleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(programRule)))
            .andExpect(status().isBadRequest());

        // Validate the ProgramRule in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTrackIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        programRule.setTrack(null);

        // Create the ProgramRule, which fails.

        restProgramRuleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(programRule)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProgramRules() throws Exception {
        // Initialize the database
        insertedProgramRule = programRuleRepository.saveAndFlush(programRule);

        // Get all the programRuleList
        restProgramRuleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(programRule.getId())))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].displayName").value(hasItem(DEFAULT_DISPLAY_NAME)))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].condition").value(hasItem(DEFAULT_CONDITION)))
            .andExpect(jsonPath("$.[*].track").value(hasItem(DEFAULT_TRACK.toString())));
    }

    @Test
    @Transactional
    void getProgramRule() throws Exception {
        // Initialize the database
        insertedProgramRule = programRuleRepository.saveAndFlush(programRule);

        // Get the programRule
        restProgramRuleMockMvc
            .perform(get(ENTITY_API_URL_ID, programRule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(programRule.getId()))
            .andExpect(jsonPath("$.lastUpdated").value(DEFAULT_LAST_UPDATED.toString()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.displayName").value(DEFAULT_DISPLAY_NAME))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY))
            .andExpect(jsonPath("$.condition").value(DEFAULT_CONDITION))
            .andExpect(jsonPath("$.track").value(DEFAULT_TRACK.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProgramRule() throws Exception {
        // Get the programRule
        restProgramRuleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProgramRule() throws Exception {
        // Initialize the database
        insertedProgramRule = programRuleRepository.saveAndFlush(programRule);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the programRule
        ProgramRule updatedProgramRule = programRuleRepository.findById(programRule.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProgramRule are not directly saved in db
        em.detach(updatedProgramRule);
        updatedProgramRule
            .lastUpdated(UPDATED_LAST_UPDATED)
            .created(UPDATED_CREATED)
            .name(UPDATED_NAME)
            .displayName(UPDATED_DISPLAY_NAME)
            .priority(UPDATED_PRIORITY)
            .condition(UPDATED_CONDITION)
            .track(UPDATED_TRACK);

        restProgramRuleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProgramRule.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedProgramRule))
            )
            .andExpect(status().isOk());

        // Validate the ProgramRule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProgramRuleToMatchAllProperties(updatedProgramRule);
    }

    @Test
    @Transactional
    void putNonExistingProgramRule() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        programRule.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgramRuleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, programRule.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(programRule))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProgramRule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProgramRule() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        programRule.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramRuleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(programRule))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProgramRule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProgramRule() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        programRule.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramRuleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(programRule)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProgramRule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProgramRuleWithPatch() throws Exception {
        // Initialize the database
        insertedProgramRule = programRuleRepository.saveAndFlush(programRule);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the programRule using partial update
        ProgramRule partialUpdatedProgramRule = new ProgramRule();
        partialUpdatedProgramRule.setId(programRule.getId());

        partialUpdatedProgramRule.displayName(UPDATED_DISPLAY_NAME).priority(UPDATED_PRIORITY).condition(UPDATED_CONDITION);

        restProgramRuleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProgramRule.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProgramRule))
            )
            .andExpect(status().isOk());

        // Validate the ProgramRule in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProgramRuleUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedProgramRule, programRule),
            getPersistedProgramRule(programRule)
        );
    }

    @Test
    @Transactional
    void fullUpdateProgramRuleWithPatch() throws Exception {
        // Initialize the database
        insertedProgramRule = programRuleRepository.saveAndFlush(programRule);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the programRule using partial update
        ProgramRule partialUpdatedProgramRule = new ProgramRule();
        partialUpdatedProgramRule.setId(programRule.getId());

        partialUpdatedProgramRule
            .lastUpdated(UPDATED_LAST_UPDATED)
            .created(UPDATED_CREATED)
            .name(UPDATED_NAME)
            .displayName(UPDATED_DISPLAY_NAME)
            .priority(UPDATED_PRIORITY)
            .condition(UPDATED_CONDITION)
            .track(UPDATED_TRACK);

        restProgramRuleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProgramRule.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProgramRule))
            )
            .andExpect(status().isOk());

        // Validate the ProgramRule in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProgramRuleUpdatableFieldsEquals(partialUpdatedProgramRule, getPersistedProgramRule(partialUpdatedProgramRule));
    }

    @Test
    @Transactional
    void patchNonExistingProgramRule() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        programRule.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgramRuleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, programRule.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(programRule))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProgramRule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProgramRule() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        programRule.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramRuleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(programRule))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProgramRule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProgramRule() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        programRule.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramRuleMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(programRule)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProgramRule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProgramRule() throws Exception {
        // Initialize the database
        insertedProgramRule = programRuleRepository.saveAndFlush(programRule);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the programRule
        restProgramRuleMockMvc
            .perform(delete(ENTITY_API_URL_ID, programRule.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return programRuleRepository.count();
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

    protected ProgramRule getPersistedProgramRule(ProgramRule programRule) {
        return programRuleRepository.findById(programRule.getId()).orElseThrow();
    }

    protected void assertPersistedProgramRuleToMatchAllProperties(ProgramRule expectedProgramRule) {
        assertProgramRuleAllPropertiesEquals(expectedProgramRule, getPersistedProgramRule(expectedProgramRule));
    }

    protected void assertPersistedProgramRuleToMatchUpdatableProperties(ProgramRule expectedProgramRule) {
        assertProgramRuleAllUpdatablePropertiesEquals(expectedProgramRule, getPersistedProgramRule(expectedProgramRule));
    }
}
