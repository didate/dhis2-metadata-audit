package com.didate.web.rest;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
    private ProgramRuleRepository programRuleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProgramRuleMockMvc;

    private ProgramRule programRule;

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

    @Test
    @Transactional
    void createProgramRule() throws Exception {
        int databaseSizeBeforeCreate = programRuleRepository.findAll().size();
        // Create the ProgramRule
        restProgramRuleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(programRule)))
            .andExpect(status().isCreated());

        // Validate the ProgramRule in the database
        List<ProgramRule> programRuleList = programRuleRepository.findAll();
        assertThat(programRuleList).hasSize(databaseSizeBeforeCreate + 1);
        ProgramRule testProgramRule = programRuleList.get(programRuleList.size() - 1);
        assertThat(testProgramRule.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
        assertThat(testProgramRule.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testProgramRule.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProgramRule.getDisplayName()).isEqualTo(DEFAULT_DISPLAY_NAME);
        assertThat(testProgramRule.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testProgramRule.getCondition()).isEqualTo(DEFAULT_CONDITION);
        assertThat(testProgramRule.getTrack()).isEqualTo(DEFAULT_TRACK);
    }

    @Test
    @Transactional
    void createProgramRuleWithExistingId() throws Exception {
        // Create the ProgramRule with an existing ID
        programRule.setId("existing_id");

        int databaseSizeBeforeCreate = programRuleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProgramRuleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(programRule)))
            .andExpect(status().isBadRequest());

        // Validate the ProgramRule in the database
        List<ProgramRule> programRuleList = programRuleRepository.findAll();
        assertThat(programRuleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTrackIsRequired() throws Exception {
        int databaseSizeBeforeTest = programRuleRepository.findAll().size();
        // set the field null
        programRule.setTrack(null);

        // Create the ProgramRule, which fails.

        restProgramRuleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(programRule)))
            .andExpect(status().isBadRequest());

        List<ProgramRule> programRuleList = programRuleRepository.findAll();
        assertThat(programRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProgramRules() throws Exception {
        // Initialize the database
        programRule.setId(UUID.randomUUID().toString());
        programRuleRepository.saveAndFlush(programRule);

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
        programRule.setId(UUID.randomUUID().toString());
        programRuleRepository.saveAndFlush(programRule);

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
        programRule.setId(UUID.randomUUID().toString());
        programRuleRepository.saveAndFlush(programRule);

        int databaseSizeBeforeUpdate = programRuleRepository.findAll().size();

        // Update the programRule
        ProgramRule updatedProgramRule = programRuleRepository.findById(programRule.getId()).get();
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
                    .content(TestUtil.convertObjectToJsonBytes(updatedProgramRule))
            )
            .andExpect(status().isOk());

        // Validate the ProgramRule in the database
        List<ProgramRule> programRuleList = programRuleRepository.findAll();
        assertThat(programRuleList).hasSize(databaseSizeBeforeUpdate);
        ProgramRule testProgramRule = programRuleList.get(programRuleList.size() - 1);
        assertThat(testProgramRule.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
        assertThat(testProgramRule.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testProgramRule.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProgramRule.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testProgramRule.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testProgramRule.getCondition()).isEqualTo(UPDATED_CONDITION);
        assertThat(testProgramRule.getTrack()).isEqualTo(UPDATED_TRACK);
    }

    @Test
    @Transactional
    void putNonExistingProgramRule() throws Exception {
        int databaseSizeBeforeUpdate = programRuleRepository.findAll().size();
        programRule.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgramRuleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, programRule.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(programRule))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProgramRule in the database
        List<ProgramRule> programRuleList = programRuleRepository.findAll();
        assertThat(programRuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProgramRule() throws Exception {
        int databaseSizeBeforeUpdate = programRuleRepository.findAll().size();
        programRule.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramRuleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(programRule))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProgramRule in the database
        List<ProgramRule> programRuleList = programRuleRepository.findAll();
        assertThat(programRuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProgramRule() throws Exception {
        int databaseSizeBeforeUpdate = programRuleRepository.findAll().size();
        programRule.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramRuleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(programRule)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProgramRule in the database
        List<ProgramRule> programRuleList = programRuleRepository.findAll();
        assertThat(programRuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProgramRuleWithPatch() throws Exception {
        // Initialize the database
        programRule.setId(UUID.randomUUID().toString());
        programRuleRepository.saveAndFlush(programRule);

        int databaseSizeBeforeUpdate = programRuleRepository.findAll().size();

        // Update the programRule using partial update
        ProgramRule partialUpdatedProgramRule = new ProgramRule();
        partialUpdatedProgramRule.setId(programRule.getId());

        partialUpdatedProgramRule
            .created(UPDATED_CREATED)
            .displayName(UPDATED_DISPLAY_NAME)
            .priority(UPDATED_PRIORITY)
            .condition(UPDATED_CONDITION);

        restProgramRuleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProgramRule.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProgramRule))
            )
            .andExpect(status().isOk());

        // Validate the ProgramRule in the database
        List<ProgramRule> programRuleList = programRuleRepository.findAll();
        assertThat(programRuleList).hasSize(databaseSizeBeforeUpdate);
        ProgramRule testProgramRule = programRuleList.get(programRuleList.size() - 1);
        assertThat(testProgramRule.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
        assertThat(testProgramRule.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testProgramRule.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProgramRule.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testProgramRule.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testProgramRule.getCondition()).isEqualTo(UPDATED_CONDITION);
        assertThat(testProgramRule.getTrack()).isEqualTo(DEFAULT_TRACK);
    }

    @Test
    @Transactional
    void fullUpdateProgramRuleWithPatch() throws Exception {
        // Initialize the database
        programRule.setId(UUID.randomUUID().toString());
        programRuleRepository.saveAndFlush(programRule);

        int databaseSizeBeforeUpdate = programRuleRepository.findAll().size();

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
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProgramRule))
            )
            .andExpect(status().isOk());

        // Validate the ProgramRule in the database
        List<ProgramRule> programRuleList = programRuleRepository.findAll();
        assertThat(programRuleList).hasSize(databaseSizeBeforeUpdate);
        ProgramRule testProgramRule = programRuleList.get(programRuleList.size() - 1);
        assertThat(testProgramRule.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
        assertThat(testProgramRule.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testProgramRule.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProgramRule.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testProgramRule.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testProgramRule.getCondition()).isEqualTo(UPDATED_CONDITION);
        assertThat(testProgramRule.getTrack()).isEqualTo(UPDATED_TRACK);
    }

    @Test
    @Transactional
    void patchNonExistingProgramRule() throws Exception {
        int databaseSizeBeforeUpdate = programRuleRepository.findAll().size();
        programRule.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgramRuleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, programRule.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(programRule))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProgramRule in the database
        List<ProgramRule> programRuleList = programRuleRepository.findAll();
        assertThat(programRuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProgramRule() throws Exception {
        int databaseSizeBeforeUpdate = programRuleRepository.findAll().size();
        programRule.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramRuleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(programRule))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProgramRule in the database
        List<ProgramRule> programRuleList = programRuleRepository.findAll();
        assertThat(programRuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProgramRule() throws Exception {
        int databaseSizeBeforeUpdate = programRuleRepository.findAll().size();
        programRule.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramRuleMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(programRule))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProgramRule in the database
        List<ProgramRule> programRuleList = programRuleRepository.findAll();
        assertThat(programRuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProgramRule() throws Exception {
        // Initialize the database
        programRule.setId(UUID.randomUUID().toString());
        programRuleRepository.saveAndFlush(programRule);

        int databaseSizeBeforeDelete = programRuleRepository.findAll().size();

        // Delete the programRule
        restProgramRuleMockMvc
            .perform(delete(ENTITY_API_URL_ID, programRule.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProgramRule> programRuleList = programRuleRepository.findAll();
        assertThat(programRuleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
