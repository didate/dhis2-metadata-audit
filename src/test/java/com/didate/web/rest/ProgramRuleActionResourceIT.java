package com.didate.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.didate.IntegrationTest;
import com.didate.domain.DHISUser;
import com.didate.domain.ProgramRule;
import com.didate.domain.ProgramRuleAction;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.repository.ProgramRuleActionRepository;
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
 * Integration tests for the {@link ProgramRuleActionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProgramRuleActionResourceIT {

    private static final Instant DEFAULT_LAST_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED = "AAAAAAAAAA";
    private static final String UPDATED_CREATED = "BBBBBBBBBB";

    private static final String DEFAULT_PROGRAM_RULE_ACTION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PROGRAM_RULE_ACTION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_EVALUATION_TIME = "AAAAAAAAAA";
    private static final String UPDATED_EVALUATION_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_DATA = "AAAAAAAAAA";
    private static final String UPDATED_DATA = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPLATE_UID = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE_UID = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_CONTENT = "BBBBBBBBBB";

    private static final TypeTrack DEFAULT_TRACK = TypeTrack.NEW;
    private static final TypeTrack UPDATED_TRACK = TypeTrack.UPDATE;

    private static final String ENTITY_API_URL = "/api/program-rule-actions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ProgramRuleActionRepository programRuleActionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProgramRuleActionMockMvc;

    private ProgramRuleAction programRuleAction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProgramRuleAction createEntity(EntityManager em) {
        ProgramRuleAction programRuleAction = new ProgramRuleAction()
            .lastUpdated(DEFAULT_LAST_UPDATED)
            .created(DEFAULT_CREATED)
            .programRuleActionType(DEFAULT_PROGRAM_RULE_ACTION_TYPE)
            .evaluationTime(DEFAULT_EVALUATION_TIME)
            .data(DEFAULT_DATA)
            .templateUid(DEFAULT_TEMPLATE_UID)
            .content(DEFAULT_CONTENT)
            .displayContent(DEFAULT_DISPLAY_CONTENT)
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
        programRuleAction.setCreatedBy(dHISUser);
        // Add required entity
        programRuleAction.setLastUpdatedBy(dHISUser);
        // Add required entity
        ProgramRule programRule;
        if (TestUtil.findAll(em, ProgramRule.class).isEmpty()) {
            programRule = ProgramRuleResourceIT.createEntity(em);
            em.persist(programRule);
            em.flush();
        } else {
            programRule = TestUtil.findAll(em, ProgramRule.class).get(0);
        }
        programRuleAction.setProgramRule(programRule);
        return programRuleAction;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProgramRuleAction createUpdatedEntity(EntityManager em) {
        ProgramRuleAction programRuleAction = new ProgramRuleAction()
            .lastUpdated(UPDATED_LAST_UPDATED)
            .created(UPDATED_CREATED)
            .programRuleActionType(UPDATED_PROGRAM_RULE_ACTION_TYPE)
            .evaluationTime(UPDATED_EVALUATION_TIME)
            .data(UPDATED_DATA)
            .templateUid(UPDATED_TEMPLATE_UID)
            .content(UPDATED_CONTENT)
            .displayContent(UPDATED_DISPLAY_CONTENT)
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
        programRuleAction.setCreatedBy(dHISUser);
        // Add required entity
        programRuleAction.setLastUpdatedBy(dHISUser);
        // Add required entity
        ProgramRule programRule;
        if (TestUtil.findAll(em, ProgramRule.class).isEmpty()) {
            programRule = ProgramRuleResourceIT.createUpdatedEntity(em);
            em.persist(programRule);
            em.flush();
        } else {
            programRule = TestUtil.findAll(em, ProgramRule.class).get(0);
        }
        programRuleAction.setProgramRule(programRule);
        return programRuleAction;
    }

    @BeforeEach
    public void initTest() {
        programRuleAction = createEntity(em);
    }

    @Test
    @Transactional
    void createProgramRuleAction() throws Exception {
        int databaseSizeBeforeCreate = programRuleActionRepository.findAll().size();
        // Create the ProgramRuleAction
        restProgramRuleActionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(programRuleAction))
            )
            .andExpect(status().isCreated());

        // Validate the ProgramRuleAction in the database
        List<ProgramRuleAction> programRuleActionList = programRuleActionRepository.findAll();
        assertThat(programRuleActionList).hasSize(databaseSizeBeforeCreate + 1);
        ProgramRuleAction testProgramRuleAction = programRuleActionList.get(programRuleActionList.size() - 1);
        assertThat(testProgramRuleAction.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
        assertThat(testProgramRuleAction.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testProgramRuleAction.getProgramRuleActionType()).isEqualTo(DEFAULT_PROGRAM_RULE_ACTION_TYPE);
        assertThat(testProgramRuleAction.getEvaluationTime()).isEqualTo(DEFAULT_EVALUATION_TIME);
        assertThat(testProgramRuleAction.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testProgramRuleAction.getTemplateUid()).isEqualTo(DEFAULT_TEMPLATE_UID);
        assertThat(testProgramRuleAction.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testProgramRuleAction.getDisplayContent()).isEqualTo(DEFAULT_DISPLAY_CONTENT);
        assertThat(testProgramRuleAction.getTrack()).isEqualTo(DEFAULT_TRACK);
    }

    @Test
    @Transactional
    void createProgramRuleActionWithExistingId() throws Exception {
        // Create the ProgramRuleAction with an existing ID
        programRuleAction.setId("existing_id");

        int databaseSizeBeforeCreate = programRuleActionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProgramRuleActionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(programRuleAction))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProgramRuleAction in the database
        List<ProgramRuleAction> programRuleActionList = programRuleActionRepository.findAll();
        assertThat(programRuleActionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTrackIsRequired() throws Exception {
        int databaseSizeBeforeTest = programRuleActionRepository.findAll().size();
        // set the field null
        programRuleAction.setTrack(null);

        // Create the ProgramRuleAction, which fails.

        restProgramRuleActionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(programRuleAction))
            )
            .andExpect(status().isBadRequest());

        List<ProgramRuleAction> programRuleActionList = programRuleActionRepository.findAll();
        assertThat(programRuleActionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProgramRuleActions() throws Exception {
        // Initialize the database
        programRuleAction.setId(UUID.randomUUID().toString());
        programRuleActionRepository.saveAndFlush(programRuleAction);

        // Get all the programRuleActionList
        restProgramRuleActionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(programRuleAction.getId())))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED)))
            .andExpect(jsonPath("$.[*].programRuleActionType").value(hasItem(DEFAULT_PROGRAM_RULE_ACTION_TYPE)))
            .andExpect(jsonPath("$.[*].evaluationTime").value(hasItem(DEFAULT_EVALUATION_TIME)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA)))
            .andExpect(jsonPath("$.[*].templateUid").value(hasItem(DEFAULT_TEMPLATE_UID)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].displayContent").value(hasItem(DEFAULT_DISPLAY_CONTENT)))
            .andExpect(jsonPath("$.[*].track").value(hasItem(DEFAULT_TRACK.toString())));
    }

    @Test
    @Transactional
    void getProgramRuleAction() throws Exception {
        // Initialize the database
        programRuleAction.setId(UUID.randomUUID().toString());
        programRuleActionRepository.saveAndFlush(programRuleAction);

        // Get the programRuleAction
        restProgramRuleActionMockMvc
            .perform(get(ENTITY_API_URL_ID, programRuleAction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(programRuleAction.getId()))
            .andExpect(jsonPath("$.lastUpdated").value(DEFAULT_LAST_UPDATED.toString()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED))
            .andExpect(jsonPath("$.programRuleActionType").value(DEFAULT_PROGRAM_RULE_ACTION_TYPE))
            .andExpect(jsonPath("$.evaluationTime").value(DEFAULT_EVALUATION_TIME))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA))
            .andExpect(jsonPath("$.templateUid").value(DEFAULT_TEMPLATE_UID))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.displayContent").value(DEFAULT_DISPLAY_CONTENT))
            .andExpect(jsonPath("$.track").value(DEFAULT_TRACK.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProgramRuleAction() throws Exception {
        // Get the programRuleAction
        restProgramRuleActionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProgramRuleAction() throws Exception {
        // Initialize the database
        programRuleAction.setId(UUID.randomUUID().toString());
        programRuleActionRepository.saveAndFlush(programRuleAction);

        int databaseSizeBeforeUpdate = programRuleActionRepository.findAll().size();

        // Update the programRuleAction
        ProgramRuleAction updatedProgramRuleAction = programRuleActionRepository.findById(programRuleAction.getId()).get();
        // Disconnect from session so that the updates on updatedProgramRuleAction are not directly saved in db
        em.detach(updatedProgramRuleAction);
        updatedProgramRuleAction
            .lastUpdated(UPDATED_LAST_UPDATED)
            .created(UPDATED_CREATED)
            .programRuleActionType(UPDATED_PROGRAM_RULE_ACTION_TYPE)
            .evaluationTime(UPDATED_EVALUATION_TIME)
            .data(UPDATED_DATA)
            .templateUid(UPDATED_TEMPLATE_UID)
            .content(UPDATED_CONTENT)
            .displayContent(UPDATED_DISPLAY_CONTENT)
            .track(UPDATED_TRACK);

        restProgramRuleActionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProgramRuleAction.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProgramRuleAction))
            )
            .andExpect(status().isOk());

        // Validate the ProgramRuleAction in the database
        List<ProgramRuleAction> programRuleActionList = programRuleActionRepository.findAll();
        assertThat(programRuleActionList).hasSize(databaseSizeBeforeUpdate);
        ProgramRuleAction testProgramRuleAction = programRuleActionList.get(programRuleActionList.size() - 1);
        assertThat(testProgramRuleAction.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
        assertThat(testProgramRuleAction.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testProgramRuleAction.getProgramRuleActionType()).isEqualTo(UPDATED_PROGRAM_RULE_ACTION_TYPE);
        assertThat(testProgramRuleAction.getEvaluationTime()).isEqualTo(UPDATED_EVALUATION_TIME);
        assertThat(testProgramRuleAction.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testProgramRuleAction.getTemplateUid()).isEqualTo(UPDATED_TEMPLATE_UID);
        assertThat(testProgramRuleAction.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testProgramRuleAction.getDisplayContent()).isEqualTo(UPDATED_DISPLAY_CONTENT);
        assertThat(testProgramRuleAction.getTrack()).isEqualTo(UPDATED_TRACK);
    }

    @Test
    @Transactional
    void putNonExistingProgramRuleAction() throws Exception {
        int databaseSizeBeforeUpdate = programRuleActionRepository.findAll().size();
        programRuleAction.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgramRuleActionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, programRuleAction.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(programRuleAction))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProgramRuleAction in the database
        List<ProgramRuleAction> programRuleActionList = programRuleActionRepository.findAll();
        assertThat(programRuleActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProgramRuleAction() throws Exception {
        int databaseSizeBeforeUpdate = programRuleActionRepository.findAll().size();
        programRuleAction.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramRuleActionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(programRuleAction))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProgramRuleAction in the database
        List<ProgramRuleAction> programRuleActionList = programRuleActionRepository.findAll();
        assertThat(programRuleActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProgramRuleAction() throws Exception {
        int databaseSizeBeforeUpdate = programRuleActionRepository.findAll().size();
        programRuleAction.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramRuleActionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(programRuleAction))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProgramRuleAction in the database
        List<ProgramRuleAction> programRuleActionList = programRuleActionRepository.findAll();
        assertThat(programRuleActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProgramRuleActionWithPatch() throws Exception {
        // Initialize the database
        programRuleAction.setId(UUID.randomUUID().toString());
        programRuleActionRepository.saveAndFlush(programRuleAction);

        int databaseSizeBeforeUpdate = programRuleActionRepository.findAll().size();

        // Update the programRuleAction using partial update
        ProgramRuleAction partialUpdatedProgramRuleAction = new ProgramRuleAction();
        partialUpdatedProgramRuleAction.setId(programRuleAction.getId());

        partialUpdatedProgramRuleAction
            .programRuleActionType(UPDATED_PROGRAM_RULE_ACTION_TYPE)
            .data(UPDATED_DATA)
            .templateUid(UPDATED_TEMPLATE_UID)
            .content(UPDATED_CONTENT)
            .displayContent(UPDATED_DISPLAY_CONTENT)
            .track(UPDATED_TRACK);

        restProgramRuleActionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProgramRuleAction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProgramRuleAction))
            )
            .andExpect(status().isOk());

        // Validate the ProgramRuleAction in the database
        List<ProgramRuleAction> programRuleActionList = programRuleActionRepository.findAll();
        assertThat(programRuleActionList).hasSize(databaseSizeBeforeUpdate);
        ProgramRuleAction testProgramRuleAction = programRuleActionList.get(programRuleActionList.size() - 1);
        assertThat(testProgramRuleAction.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
        assertThat(testProgramRuleAction.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testProgramRuleAction.getProgramRuleActionType()).isEqualTo(UPDATED_PROGRAM_RULE_ACTION_TYPE);
        assertThat(testProgramRuleAction.getEvaluationTime()).isEqualTo(DEFAULT_EVALUATION_TIME);
        assertThat(testProgramRuleAction.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testProgramRuleAction.getTemplateUid()).isEqualTo(UPDATED_TEMPLATE_UID);
        assertThat(testProgramRuleAction.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testProgramRuleAction.getDisplayContent()).isEqualTo(UPDATED_DISPLAY_CONTENT);
        assertThat(testProgramRuleAction.getTrack()).isEqualTo(UPDATED_TRACK);
    }

    @Test
    @Transactional
    void fullUpdateProgramRuleActionWithPatch() throws Exception {
        // Initialize the database
        programRuleAction.setId(UUID.randomUUID().toString());
        programRuleActionRepository.saveAndFlush(programRuleAction);

        int databaseSizeBeforeUpdate = programRuleActionRepository.findAll().size();

        // Update the programRuleAction using partial update
        ProgramRuleAction partialUpdatedProgramRuleAction = new ProgramRuleAction();
        partialUpdatedProgramRuleAction.setId(programRuleAction.getId());

        partialUpdatedProgramRuleAction
            .lastUpdated(UPDATED_LAST_UPDATED)
            .created(UPDATED_CREATED)
            .programRuleActionType(UPDATED_PROGRAM_RULE_ACTION_TYPE)
            .evaluationTime(UPDATED_EVALUATION_TIME)
            .data(UPDATED_DATA)
            .templateUid(UPDATED_TEMPLATE_UID)
            .content(UPDATED_CONTENT)
            .displayContent(UPDATED_DISPLAY_CONTENT)
            .track(UPDATED_TRACK);

        restProgramRuleActionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProgramRuleAction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProgramRuleAction))
            )
            .andExpect(status().isOk());

        // Validate the ProgramRuleAction in the database
        List<ProgramRuleAction> programRuleActionList = programRuleActionRepository.findAll();
        assertThat(programRuleActionList).hasSize(databaseSizeBeforeUpdate);
        ProgramRuleAction testProgramRuleAction = programRuleActionList.get(programRuleActionList.size() - 1);
        assertThat(testProgramRuleAction.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
        assertThat(testProgramRuleAction.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testProgramRuleAction.getProgramRuleActionType()).isEqualTo(UPDATED_PROGRAM_RULE_ACTION_TYPE);
        assertThat(testProgramRuleAction.getEvaluationTime()).isEqualTo(UPDATED_EVALUATION_TIME);
        assertThat(testProgramRuleAction.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testProgramRuleAction.getTemplateUid()).isEqualTo(UPDATED_TEMPLATE_UID);
        assertThat(testProgramRuleAction.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testProgramRuleAction.getDisplayContent()).isEqualTo(UPDATED_DISPLAY_CONTENT);
        assertThat(testProgramRuleAction.getTrack()).isEqualTo(UPDATED_TRACK);
    }

    @Test
    @Transactional
    void patchNonExistingProgramRuleAction() throws Exception {
        int databaseSizeBeforeUpdate = programRuleActionRepository.findAll().size();
        programRuleAction.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgramRuleActionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, programRuleAction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(programRuleAction))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProgramRuleAction in the database
        List<ProgramRuleAction> programRuleActionList = programRuleActionRepository.findAll();
        assertThat(programRuleActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProgramRuleAction() throws Exception {
        int databaseSizeBeforeUpdate = programRuleActionRepository.findAll().size();
        programRuleAction.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramRuleActionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(programRuleAction))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProgramRuleAction in the database
        List<ProgramRuleAction> programRuleActionList = programRuleActionRepository.findAll();
        assertThat(programRuleActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProgramRuleAction() throws Exception {
        int databaseSizeBeforeUpdate = programRuleActionRepository.findAll().size();
        programRuleAction.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramRuleActionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(programRuleAction))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProgramRuleAction in the database
        List<ProgramRuleAction> programRuleActionList = programRuleActionRepository.findAll();
        assertThat(programRuleActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProgramRuleAction() throws Exception {
        // Initialize the database
        programRuleAction.setId(UUID.randomUUID().toString());
        programRuleActionRepository.saveAndFlush(programRuleAction);

        int databaseSizeBeforeDelete = programRuleActionRepository.findAll().size();

        // Delete the programRuleAction
        restProgramRuleActionMockMvc
            .perform(delete(ENTITY_API_URL_ID, programRuleAction.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProgramRuleAction> programRuleActionList = programRuleActionRepository.findAll();
        assertThat(programRuleActionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
