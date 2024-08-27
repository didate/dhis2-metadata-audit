package com.didate.web.rest;

import static org.hamcrest.Matchers.hasItem;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.didate.IntegrationTest;
import com.didate.domain.DHISUser;
import com.didate.domain.Program;
import com.didate.domain.ProgramStage;
import com.didate.repository.ProgramStageRepository;
import com.didate.service.ProgramStageService;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProgramStageResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProgramStageResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_MIN_DAYS_FROM_START = 1;
    private static final Integer UPDATED_MIN_DAYS_FROM_START = 2;

    private static final String DEFAULT_EXECUTION_DATE_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_EXECUTION_DATE_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AUTO_GENERATE_EVENT = false;
    private static final Boolean UPDATED_AUTO_GENERATE_EVENT = true;

    private static final String DEFAULT_VALIDATION_STRATEGY = "AAAAAAAAAA";
    private static final String UPDATED_VALIDATION_STRATEGY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DISPLAY_GENERATE_EVENT_BOX = false;
    private static final Boolean UPDATED_DISPLAY_GENERATE_EVENT_BOX = true;

    private static final String DEFAULT_FEATURE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_FEATURE_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_BLOCK_ENTRY_FORM = false;
    private static final Boolean UPDATED_BLOCK_ENTRY_FORM = true;

    private static final Boolean DEFAULT_PRE_GENERATE_UID = false;
    private static final Boolean UPDATED_PRE_GENERATE_UID = true;

    private static final Boolean DEFAULT_REMIND_COMPLETED = false;
    private static final Boolean UPDATED_REMIND_COMPLETED = true;

    private static final Boolean DEFAULT_GENERATED_BY_ENROLLMENT_DATE = false;
    private static final Boolean UPDATED_GENERATED_BY_ENROLLMENT_DATE = true;

    private static final Boolean DEFAULT_ALLOW_GENERATE_NEXT_VISIT = false;
    private static final Boolean UPDATED_ALLOW_GENERATE_NEXT_VISIT = true;

    private static final Boolean DEFAULT_OPEN_AFTER_ENROLLMENT = false;
    private static final Boolean UPDATED_OPEN_AFTER_ENROLLMENT = true;

    private static final Integer DEFAULT_SORT_ORDER = 1;
    private static final Integer UPDATED_SORT_ORDER = 2;

    private static final Boolean DEFAULT_HIDE_DUE_DATE = false;
    private static final Boolean UPDATED_HIDE_DUE_DATE = true;

    private static final Boolean DEFAULT_ENABLE_USER_ASSIGNMENT = false;
    private static final Boolean UPDATED_ENABLE_USER_ASSIGNMENT = true;

    private static final Boolean DEFAULT_REFERRAL = false;
    private static final Boolean UPDATED_REFERRAL = true;

    private static final String DEFAULT_DISPLAY_EXECUTION_DATE_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_EXECUTION_DATE_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_FORM_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_FORM_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_FORM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_FORM_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REPEATABLE = false;
    private static final Boolean UPDATED_REPEATABLE = true;

    private static final Integer DEFAULT_PROGRAM_STAGE_DATA_ELEMENTS_COUNT = 1;
    private static final Integer UPDATED_PROGRAM_STAGE_DATA_ELEMENTS_COUNT = 2;

    private static final Integer DEFAULT_PROGRAM_STAGE_DATA_ELEMENTS_CONTENT = 1;
    private static final Integer UPDATED_PROGRAM_STAGE_DATA_ELEMENTS_CONTENT = 2;

    private static final String ENTITY_API_URL = "/api/program-stages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ProgramStageRepository programStageRepository;

    @Mock
    private ProgramStageRepository programStageRepositoryMock;

    @Mock
    private ProgramStageService programStageServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProgramStageMockMvc;

    private ProgramStage programStage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProgramStage createEntity(EntityManager em) {
        ProgramStage programStage = new ProgramStage()
            .name(DEFAULT_NAME)
            .created(DEFAULT_CREATED)
            .lastUpdated(DEFAULT_LAST_UPDATED)
            .minDaysFromStart(DEFAULT_MIN_DAYS_FROM_START)
            .executionDateLabel(DEFAULT_EXECUTION_DATE_LABEL)
            .autoGenerateEvent(DEFAULT_AUTO_GENERATE_EVENT)
            .validationStrategy(DEFAULT_VALIDATION_STRATEGY)
            .displayGenerateEventBox(DEFAULT_DISPLAY_GENERATE_EVENT_BOX)
            .featureType(DEFAULT_FEATURE_TYPE)
            .blockEntryForm(DEFAULT_BLOCK_ENTRY_FORM)
            .preGenerateUID(DEFAULT_PRE_GENERATE_UID)
            .remindCompleted(DEFAULT_REMIND_COMPLETED)
            .generatedByEnrollmentDate(DEFAULT_GENERATED_BY_ENROLLMENT_DATE)
            .allowGenerateNextVisit(DEFAULT_ALLOW_GENERATE_NEXT_VISIT)
            .openAfterEnrollment(DEFAULT_OPEN_AFTER_ENROLLMENT)
            .sortOrder(DEFAULT_SORT_ORDER)
            .hideDueDate(DEFAULT_HIDE_DUE_DATE)
            .enableUserAssignment(DEFAULT_ENABLE_USER_ASSIGNMENT)
            .referral(DEFAULT_REFERRAL)
            .displayExecutionDateLabel(DEFAULT_DISPLAY_EXECUTION_DATE_LABEL)
            .formType(DEFAULT_FORM_TYPE)
            .displayFormName(DEFAULT_DISPLAY_FORM_NAME)
            .displayName(DEFAULT_DISPLAY_NAME)
            .repeatable(DEFAULT_REPEATABLE)
            .programStageDataElementsCount(DEFAULT_PROGRAM_STAGE_DATA_ELEMENTS_COUNT);
        // Add required entity
        DHISUser dHISUser;
        if (TestUtil.findAll(em, DHISUser.class).isEmpty()) {
            dHISUser = DHISUserResourceIT.createEntity(em);
            em.persist(dHISUser);
            em.flush();
        } else {
            dHISUser = TestUtil.findAll(em, DHISUser.class).get(0);
        }
        programStage.setCreatedBy(dHISUser);
        // Add required entity
        programStage.setLastUpdatedBy(dHISUser);
        // Add required entity
        Program program;
        if (TestUtil.findAll(em, Program.class).isEmpty()) {
            program = ProgramResourceIT.createEntity(em);
            em.persist(program);
            em.flush();
        } else {
            program = TestUtil.findAll(em, Program.class).get(0);
        }
        programStage.setProgram(program);
        return programStage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProgramStage createUpdatedEntity(EntityManager em) {
        ProgramStage programStage = new ProgramStage()
            .name(UPDATED_NAME)
            .created(UPDATED_CREATED)
            .lastUpdated(UPDATED_LAST_UPDATED)
            .minDaysFromStart(UPDATED_MIN_DAYS_FROM_START)
            .executionDateLabel(UPDATED_EXECUTION_DATE_LABEL)
            .autoGenerateEvent(UPDATED_AUTO_GENERATE_EVENT)
            .validationStrategy(UPDATED_VALIDATION_STRATEGY)
            .displayGenerateEventBox(UPDATED_DISPLAY_GENERATE_EVENT_BOX)
            .featureType(UPDATED_FEATURE_TYPE)
            .blockEntryForm(UPDATED_BLOCK_ENTRY_FORM)
            .preGenerateUID(UPDATED_PRE_GENERATE_UID)
            .remindCompleted(UPDATED_REMIND_COMPLETED)
            .generatedByEnrollmentDate(UPDATED_GENERATED_BY_ENROLLMENT_DATE)
            .allowGenerateNextVisit(UPDATED_ALLOW_GENERATE_NEXT_VISIT)
            .openAfterEnrollment(UPDATED_OPEN_AFTER_ENROLLMENT)
            .sortOrder(UPDATED_SORT_ORDER)
            .hideDueDate(UPDATED_HIDE_DUE_DATE)
            .enableUserAssignment(UPDATED_ENABLE_USER_ASSIGNMENT)
            .referral(UPDATED_REFERRAL)
            .displayExecutionDateLabel(UPDATED_DISPLAY_EXECUTION_DATE_LABEL)
            .formType(UPDATED_FORM_TYPE)
            .displayFormName(UPDATED_DISPLAY_FORM_NAME)
            .displayName(UPDATED_DISPLAY_NAME)
            .repeatable(UPDATED_REPEATABLE)
            .programStageDataElementsCount(UPDATED_PROGRAM_STAGE_DATA_ELEMENTS_COUNT);
        // Add required entity
        DHISUser dHISUser;
        if (TestUtil.findAll(em, DHISUser.class).isEmpty()) {
            dHISUser = DHISUserResourceIT.createUpdatedEntity(em);
            em.persist(dHISUser);
            em.flush();
        } else {
            dHISUser = TestUtil.findAll(em, DHISUser.class).get(0);
        }
        programStage.setCreatedBy(dHISUser);
        // Add required entity
        programStage.setLastUpdatedBy(dHISUser);
        // Add required entity
        Program program;
        if (TestUtil.findAll(em, Program.class).isEmpty()) {
            program = ProgramResourceIT.createUpdatedEntity(em);
            em.persist(program);
            em.flush();
        } else {
            program = TestUtil.findAll(em, Program.class).get(0);
        }
        programStage.setProgram(program);
        return programStage;
    }

    @BeforeEach
    public void initTest() {
        programStage = createEntity(em);
    }

    @Test
    @Transactional
    void getAllProgramStages() throws Exception {
        // Initialize the database
        programStage.setId(UUID.randomUUID().toString());
        programStageRepository.saveAndFlush(programStage);

        // Get all the programStageList
        restProgramStageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(programStage.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].minDaysFromStart").value(hasItem(DEFAULT_MIN_DAYS_FROM_START)))
            .andExpect(jsonPath("$.[*].executionDateLabel").value(hasItem(DEFAULT_EXECUTION_DATE_LABEL)))
            .andExpect(jsonPath("$.[*].autoGenerateEvent").value(hasItem(DEFAULT_AUTO_GENERATE_EVENT.booleanValue())))
            .andExpect(jsonPath("$.[*].validationStrategy").value(hasItem(DEFAULT_VALIDATION_STRATEGY)))
            .andExpect(jsonPath("$.[*].displayGenerateEventBox").value(hasItem(DEFAULT_DISPLAY_GENERATE_EVENT_BOX.booleanValue())))
            .andExpect(jsonPath("$.[*].featureType").value(hasItem(DEFAULT_FEATURE_TYPE)))
            .andExpect(jsonPath("$.[*].blockEntryForm").value(hasItem(DEFAULT_BLOCK_ENTRY_FORM.booleanValue())))
            .andExpect(jsonPath("$.[*].preGenerateUID").value(hasItem(DEFAULT_PRE_GENERATE_UID.booleanValue())))
            .andExpect(jsonPath("$.[*].remindCompleted").value(hasItem(DEFAULT_REMIND_COMPLETED.booleanValue())))
            .andExpect(jsonPath("$.[*].generatedByEnrollmentDate").value(hasItem(DEFAULT_GENERATED_BY_ENROLLMENT_DATE.booleanValue())))
            .andExpect(jsonPath("$.[*].allowGenerateNextVisit").value(hasItem(DEFAULT_ALLOW_GENERATE_NEXT_VISIT.booleanValue())))
            .andExpect(jsonPath("$.[*].openAfterEnrollment").value(hasItem(DEFAULT_OPEN_AFTER_ENROLLMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].hideDueDate").value(hasItem(DEFAULT_HIDE_DUE_DATE.booleanValue())))
            .andExpect(jsonPath("$.[*].enableUserAssignment").value(hasItem(DEFAULT_ENABLE_USER_ASSIGNMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].referral").value(hasItem(DEFAULT_REFERRAL.booleanValue())))
            .andExpect(jsonPath("$.[*].displayExecutionDateLabel").value(hasItem(DEFAULT_DISPLAY_EXECUTION_DATE_LABEL)))
            .andExpect(jsonPath("$.[*].formType").value(hasItem(DEFAULT_FORM_TYPE)))
            .andExpect(jsonPath("$.[*].displayFormName").value(hasItem(DEFAULT_DISPLAY_FORM_NAME)))
            .andExpect(jsonPath("$.[*].displayName").value(hasItem(DEFAULT_DISPLAY_NAME)))
            .andExpect(jsonPath("$.[*].repeatable").value(hasItem(DEFAULT_REPEATABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].programStageDataElementsCount").value(hasItem(DEFAULT_PROGRAM_STAGE_DATA_ELEMENTS_COUNT)))
            .andExpect(jsonPath("$.[*].programStageDataElementsContent").value(hasItem(DEFAULT_PROGRAM_STAGE_DATA_ELEMENTS_CONTENT)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProgramStagesWithEagerRelationshipsIsEnabled() throws Exception {
        when(programStageServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProgramStageMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(programStageServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProgramStagesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(programStageServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProgramStageMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(programStageRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getProgramStage() throws Exception {
        // Initialize the database
        programStage.setId(UUID.randomUUID().toString());
        programStageRepository.saveAndFlush(programStage);

        // Get the programStage
        restProgramStageMockMvc
            .perform(get(ENTITY_API_URL_ID, programStage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(programStage.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.lastUpdated").value(DEFAULT_LAST_UPDATED.toString()))
            .andExpect(jsonPath("$.minDaysFromStart").value(DEFAULT_MIN_DAYS_FROM_START))
            .andExpect(jsonPath("$.executionDateLabel").value(DEFAULT_EXECUTION_DATE_LABEL))
            .andExpect(jsonPath("$.autoGenerateEvent").value(DEFAULT_AUTO_GENERATE_EVENT.booleanValue()))
            .andExpect(jsonPath("$.validationStrategy").value(DEFAULT_VALIDATION_STRATEGY))
            .andExpect(jsonPath("$.displayGenerateEventBox").value(DEFAULT_DISPLAY_GENERATE_EVENT_BOX.booleanValue()))
            .andExpect(jsonPath("$.featureType").value(DEFAULT_FEATURE_TYPE))
            .andExpect(jsonPath("$.blockEntryForm").value(DEFAULT_BLOCK_ENTRY_FORM.booleanValue()))
            .andExpect(jsonPath("$.preGenerateUID").value(DEFAULT_PRE_GENERATE_UID.booleanValue()))
            .andExpect(jsonPath("$.remindCompleted").value(DEFAULT_REMIND_COMPLETED.booleanValue()))
            .andExpect(jsonPath("$.generatedByEnrollmentDate").value(DEFAULT_GENERATED_BY_ENROLLMENT_DATE.booleanValue()))
            .andExpect(jsonPath("$.allowGenerateNextVisit").value(DEFAULT_ALLOW_GENERATE_NEXT_VISIT.booleanValue()))
            .andExpect(jsonPath("$.openAfterEnrollment").value(DEFAULT_OPEN_AFTER_ENROLLMENT.booleanValue()))
            .andExpect(jsonPath("$.sortOrder").value(DEFAULT_SORT_ORDER))
            .andExpect(jsonPath("$.hideDueDate").value(DEFAULT_HIDE_DUE_DATE.booleanValue()))
            .andExpect(jsonPath("$.enableUserAssignment").value(DEFAULT_ENABLE_USER_ASSIGNMENT.booleanValue()))
            .andExpect(jsonPath("$.referral").value(DEFAULT_REFERRAL.booleanValue()))
            .andExpect(jsonPath("$.displayExecutionDateLabel").value(DEFAULT_DISPLAY_EXECUTION_DATE_LABEL))
            .andExpect(jsonPath("$.formType").value(DEFAULT_FORM_TYPE))
            .andExpect(jsonPath("$.displayFormName").value(DEFAULT_DISPLAY_FORM_NAME))
            .andExpect(jsonPath("$.displayName").value(DEFAULT_DISPLAY_NAME))
            .andExpect(jsonPath("$.repeatable").value(DEFAULT_REPEATABLE.booleanValue()))
            .andExpect(jsonPath("$.programStageDataElementsCount").value(DEFAULT_PROGRAM_STAGE_DATA_ELEMENTS_COUNT))
            .andExpect(jsonPath("$.programStageDataElementsContent").value(DEFAULT_PROGRAM_STAGE_DATA_ELEMENTS_CONTENT));
    }

    @Test
    @Transactional
    void getNonExistingProgramStage() throws Exception {
        // Get the programStage
        restProgramStageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }
}
