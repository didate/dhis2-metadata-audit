package com.didate.web.rest;

import static com.didate.domain.ProgramRuleVariableAsserts.*;
import static com.didate.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.didate.IntegrationTest;
import com.didate.domain.DHISUser;
import com.didate.domain.Program;
import com.didate.domain.ProgramRuleVariable;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.repository.ProgramRuleVariableRepository;
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
 * Integration tests for the {@link ProgramRuleVariableResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProgramRuleVariableResourceIT {

    private static final Instant DEFAULT_LAST_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PROGRAM_RULE_VARIABLE_SOURCE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PROGRAM_RULE_VARIABLE_SOURCE_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_USE_CODE_FOR_OPTION_SET = false;
    private static final Boolean UPDATED_USE_CODE_FOR_OPTION_SET = true;

    private static final TypeTrack DEFAULT_TRACK = TypeTrack.NEW;
    private static final TypeTrack UPDATED_TRACK = TypeTrack.UPDATE;

    private static final String ENTITY_API_URL = "/api/program-rule-variables";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProgramRuleVariableRepository programRuleVariableRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProgramRuleVariableMockMvc;

    private ProgramRuleVariable programRuleVariable;

    private ProgramRuleVariable insertedProgramRuleVariable;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProgramRuleVariable createEntity(EntityManager em) {
        ProgramRuleVariable programRuleVariable = new ProgramRuleVariable()
            .lastUpdated(DEFAULT_LAST_UPDATED)
            .created(DEFAULT_CREATED)
            .name(DEFAULT_NAME)
            .displayName(DEFAULT_DISPLAY_NAME)
            .programRuleVariableSourceType(DEFAULT_PROGRAM_RULE_VARIABLE_SOURCE_TYPE)
            .useCodeForOptionSet(DEFAULT_USE_CODE_FOR_OPTION_SET)
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
        programRuleVariable.setCreatedBy(dHISUser);
        // Add required entity
        programRuleVariable.setLastUpdatedBy(dHISUser);
        // Add required entity
        Program program;
        if (TestUtil.findAll(em, Program.class).isEmpty()) {
            program = ProgramResourceIT.createEntity(em);
            em.persist(program);
            em.flush();
        } else {
            program = TestUtil.findAll(em, Program.class).get(0);
        }
        programRuleVariable.setProgram(program);
        return programRuleVariable;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProgramRuleVariable createUpdatedEntity(EntityManager em) {
        ProgramRuleVariable programRuleVariable = new ProgramRuleVariable()
            .lastUpdated(UPDATED_LAST_UPDATED)
            .created(UPDATED_CREATED)
            .name(UPDATED_NAME)
            .displayName(UPDATED_DISPLAY_NAME)
            .programRuleVariableSourceType(UPDATED_PROGRAM_RULE_VARIABLE_SOURCE_TYPE)
            .useCodeForOptionSet(UPDATED_USE_CODE_FOR_OPTION_SET)
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
        programRuleVariable.setCreatedBy(dHISUser);
        // Add required entity
        programRuleVariable.setLastUpdatedBy(dHISUser);
        // Add required entity
        Program program;
        if (TestUtil.findAll(em, Program.class).isEmpty()) {
            program = ProgramResourceIT.createUpdatedEntity(em);
            em.persist(program);
            em.flush();
        } else {
            program = TestUtil.findAll(em, Program.class).get(0);
        }
        programRuleVariable.setProgram(program);
        return programRuleVariable;
    }

    @BeforeEach
    public void initTest() {
        programRuleVariable = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedProgramRuleVariable != null) {
            programRuleVariableRepository.delete(insertedProgramRuleVariable);
            insertedProgramRuleVariable = null;
        }
    }

    @Test
    @Transactional
    void createProgramRuleVariable() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ProgramRuleVariable
        var returnedProgramRuleVariable = om.readValue(
            restProgramRuleVariableMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(programRuleVariable)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ProgramRuleVariable.class
        );

        // Validate the ProgramRuleVariable in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertProgramRuleVariableUpdatableFieldsEquals(
            returnedProgramRuleVariable,
            getPersistedProgramRuleVariable(returnedProgramRuleVariable)
        );

        insertedProgramRuleVariable = returnedProgramRuleVariable;
    }

    @Test
    @Transactional
    void createProgramRuleVariableWithExistingId() throws Exception {
        // Create the ProgramRuleVariable with an existing ID
        programRuleVariable.setId("existing_id");

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProgramRuleVariableMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(programRuleVariable)))
            .andExpect(status().isBadRequest());

        // Validate the ProgramRuleVariable in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLastUpdatedIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        programRuleVariable.setLastUpdated(null);

        // Create the ProgramRuleVariable, which fails.

        restProgramRuleVariableMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(programRuleVariable)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        programRuleVariable.setCreated(null);

        // Create the ProgramRuleVariable, which fails.

        restProgramRuleVariableMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(programRuleVariable)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        programRuleVariable.setName(null);

        // Create the ProgramRuleVariable, which fails.

        restProgramRuleVariableMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(programRuleVariable)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTrackIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        programRuleVariable.setTrack(null);

        // Create the ProgramRuleVariable, which fails.

        restProgramRuleVariableMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(programRuleVariable)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProgramRuleVariables() throws Exception {
        // Initialize the database
        insertedProgramRuleVariable = programRuleVariableRepository.saveAndFlush(programRuleVariable);

        // Get all the programRuleVariableList
        restProgramRuleVariableMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(programRuleVariable.getId())))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].displayName").value(hasItem(DEFAULT_DISPLAY_NAME)))
            .andExpect(jsonPath("$.[*].programRuleVariableSourceType").value(hasItem(DEFAULT_PROGRAM_RULE_VARIABLE_SOURCE_TYPE)))
            .andExpect(jsonPath("$.[*].useCodeForOptionSet").value(hasItem(DEFAULT_USE_CODE_FOR_OPTION_SET.booleanValue())))
            .andExpect(jsonPath("$.[*].track").value(hasItem(DEFAULT_TRACK.toString())));
    }

    @Test
    @Transactional
    void getProgramRuleVariable() throws Exception {
        // Initialize the database
        insertedProgramRuleVariable = programRuleVariableRepository.saveAndFlush(programRuleVariable);

        // Get the programRuleVariable
        restProgramRuleVariableMockMvc
            .perform(get(ENTITY_API_URL_ID, programRuleVariable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(programRuleVariable.getId()))
            .andExpect(jsonPath("$.lastUpdated").value(DEFAULT_LAST_UPDATED.toString()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.displayName").value(DEFAULT_DISPLAY_NAME))
            .andExpect(jsonPath("$.programRuleVariableSourceType").value(DEFAULT_PROGRAM_RULE_VARIABLE_SOURCE_TYPE))
            .andExpect(jsonPath("$.useCodeForOptionSet").value(DEFAULT_USE_CODE_FOR_OPTION_SET.booleanValue()))
            .andExpect(jsonPath("$.track").value(DEFAULT_TRACK.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProgramRuleVariable() throws Exception {
        // Get the programRuleVariable
        restProgramRuleVariableMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProgramRuleVariable() throws Exception {
        // Initialize the database
        insertedProgramRuleVariable = programRuleVariableRepository.saveAndFlush(programRuleVariable);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the programRuleVariable
        ProgramRuleVariable updatedProgramRuleVariable = programRuleVariableRepository.findById(programRuleVariable.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProgramRuleVariable are not directly saved in db
        em.detach(updatedProgramRuleVariable);
        updatedProgramRuleVariable
            .lastUpdated(UPDATED_LAST_UPDATED)
            .created(UPDATED_CREATED)
            .name(UPDATED_NAME)
            .displayName(UPDATED_DISPLAY_NAME)
            .programRuleVariableSourceType(UPDATED_PROGRAM_RULE_VARIABLE_SOURCE_TYPE)
            .useCodeForOptionSet(UPDATED_USE_CODE_FOR_OPTION_SET)
            .track(UPDATED_TRACK);

        restProgramRuleVariableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProgramRuleVariable.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedProgramRuleVariable))
            )
            .andExpect(status().isOk());

        // Validate the ProgramRuleVariable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProgramRuleVariableToMatchAllProperties(updatedProgramRuleVariable);
    }

    @Test
    @Transactional
    void putNonExistingProgramRuleVariable() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        programRuleVariable.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgramRuleVariableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, programRuleVariable.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(programRuleVariable))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProgramRuleVariable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProgramRuleVariable() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        programRuleVariable.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramRuleVariableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(programRuleVariable))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProgramRuleVariable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProgramRuleVariable() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        programRuleVariable.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramRuleVariableMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(programRuleVariable)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProgramRuleVariable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProgramRuleVariableWithPatch() throws Exception {
        // Initialize the database
        insertedProgramRuleVariable = programRuleVariableRepository.saveAndFlush(programRuleVariable);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the programRuleVariable using partial update
        ProgramRuleVariable partialUpdatedProgramRuleVariable = new ProgramRuleVariable();
        partialUpdatedProgramRuleVariable.setId(programRuleVariable.getId());

        partialUpdatedProgramRuleVariable
            .lastUpdated(UPDATED_LAST_UPDATED)
            .created(UPDATED_CREATED)
            .name(UPDATED_NAME)
            .programRuleVariableSourceType(UPDATED_PROGRAM_RULE_VARIABLE_SOURCE_TYPE)
            .useCodeForOptionSet(UPDATED_USE_CODE_FOR_OPTION_SET);

        restProgramRuleVariableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProgramRuleVariable.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProgramRuleVariable))
            )
            .andExpect(status().isOk());

        // Validate the ProgramRuleVariable in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProgramRuleVariableUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedProgramRuleVariable, programRuleVariable),
            getPersistedProgramRuleVariable(programRuleVariable)
        );
    }

    @Test
    @Transactional
    void fullUpdateProgramRuleVariableWithPatch() throws Exception {
        // Initialize the database
        insertedProgramRuleVariable = programRuleVariableRepository.saveAndFlush(programRuleVariable);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the programRuleVariable using partial update
        ProgramRuleVariable partialUpdatedProgramRuleVariable = new ProgramRuleVariable();
        partialUpdatedProgramRuleVariable.setId(programRuleVariable.getId());

        partialUpdatedProgramRuleVariable
            .lastUpdated(UPDATED_LAST_UPDATED)
            .created(UPDATED_CREATED)
            .name(UPDATED_NAME)
            .displayName(UPDATED_DISPLAY_NAME)
            .programRuleVariableSourceType(UPDATED_PROGRAM_RULE_VARIABLE_SOURCE_TYPE)
            .useCodeForOptionSet(UPDATED_USE_CODE_FOR_OPTION_SET)
            .track(UPDATED_TRACK);

        restProgramRuleVariableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProgramRuleVariable.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProgramRuleVariable))
            )
            .andExpect(status().isOk());

        // Validate the ProgramRuleVariable in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProgramRuleVariableUpdatableFieldsEquals(
            partialUpdatedProgramRuleVariable,
            getPersistedProgramRuleVariable(partialUpdatedProgramRuleVariable)
        );
    }

    @Test
    @Transactional
    void patchNonExistingProgramRuleVariable() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        programRuleVariable.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgramRuleVariableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, programRuleVariable.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(programRuleVariable))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProgramRuleVariable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProgramRuleVariable() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        programRuleVariable.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramRuleVariableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(programRuleVariable))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProgramRuleVariable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProgramRuleVariable() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        programRuleVariable.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramRuleVariableMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(programRuleVariable)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProgramRuleVariable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProgramRuleVariable() throws Exception {
        // Initialize the database
        insertedProgramRuleVariable = programRuleVariableRepository.saveAndFlush(programRuleVariable);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the programRuleVariable
        restProgramRuleVariableMockMvc
            .perform(delete(ENTITY_API_URL_ID, programRuleVariable.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return programRuleVariableRepository.count();
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

    protected ProgramRuleVariable getPersistedProgramRuleVariable(ProgramRuleVariable programRuleVariable) {
        return programRuleVariableRepository.findById(programRuleVariable.getId()).orElseThrow();
    }

    protected void assertPersistedProgramRuleVariableToMatchAllProperties(ProgramRuleVariable expectedProgramRuleVariable) {
        assertProgramRuleVariableAllPropertiesEquals(
            expectedProgramRuleVariable,
            getPersistedProgramRuleVariable(expectedProgramRuleVariable)
        );
    }

    protected void assertPersistedProgramRuleVariableToMatchUpdatableProperties(ProgramRuleVariable expectedProgramRuleVariable) {
        assertProgramRuleVariableAllUpdatablePropertiesEquals(
            expectedProgramRuleVariable,
            getPersistedProgramRuleVariable(expectedProgramRuleVariable)
        );
    }
}
