package com.didate.web.rest;

import static com.didate.domain.ProgramAsserts.*;
import static com.didate.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.didate.IntegrationTest;
import com.didate.domain.DHISUser;
import com.didate.domain.Program;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.repository.ProgramRepository;
import com.didate.service.ProgramService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
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
 * Integration tests for the {@link ProgramResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProgramResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED = "AAAAAAAAAA";
    private static final String UPDATED_CREATED = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_UPDATED = "AAAAAAAAAA";
    private static final String UPDATED_LAST_UPDATED = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_VERSION = 1D;
    private static final Double UPDATED_VERSION = 2D;

    private static final String DEFAULT_ENROLLMENT_DATE_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_ENROLLMENT_DATE_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_INCIDENT_DATE_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_INCIDENT_DATE_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_PROGRAM_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PROGRAM_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DISPLAY_INCIDENT_DATE = false;
    private static final Boolean UPDATED_DISPLAY_INCIDENT_DATE = true;

    private static final Boolean DEFAULT_IGNORE_OVERDUE_EVENTS = false;
    private static final Boolean UPDATED_IGNORE_OVERDUE_EVENTS = true;

    private static final String DEFAULT_USER_ROLES = "AAAAAAAAAA";
    private static final String UPDATED_USER_ROLES = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ONLY_ENROLL_ONCE = false;
    private static final Boolean UPDATED_ONLY_ENROLL_ONCE = true;

    private static final String DEFAULT_NOTIFICATION_TEMPLATES = "AAAAAAAAAA";
    private static final String UPDATED_NOTIFICATION_TEMPLATES = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SELECT_ENROLLMENT_DATES_IN_FUTURE = false;
    private static final Boolean UPDATED_SELECT_ENROLLMENT_DATES_IN_FUTURE = true;

    private static final Boolean DEFAULT_SELECT_INCIDENT_DATES_IN_FUTURE = false;
    private static final Boolean UPDATED_SELECT_INCIDENT_DATES_IN_FUTURE = true;

    private static final String DEFAULT_TRACKED_ENTITY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TRACKED_ENTITY_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_STYLE = "AAAAAAAAAA";
    private static final String UPDATED_STYLE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SKIP_OFFLINE = false;
    private static final Boolean UPDATED_SKIP_OFFLINE = true;

    private static final Boolean DEFAULT_DISPLAY_FRONT_PAGE_LIST = false;
    private static final Boolean UPDATED_DISPLAY_FRONT_PAGE_LIST = true;

    private static final Boolean DEFAULT_USE_FIRST_STAGE_DURING_REGISTRATION = false;
    private static final Boolean UPDATED_USE_FIRST_STAGE_DURING_REGISTRATION = true;

    private static final Double DEFAULT_EXPIRY_DAYS = 1D;
    private static final Double UPDATED_EXPIRY_DAYS = 2D;

    private static final Double DEFAULT_COMPLETE_EVENTS_EXPIRY_DAYS = 1D;
    private static final Double UPDATED_COMPLETE_EVENTS_EXPIRY_DAYS = 2D;

    private static final Double DEFAULT_OPEN_DAYS_AFTER_CO_END_DATE = 1D;
    private static final Double UPDATED_OPEN_DAYS_AFTER_CO_END_DATE = 2D;

    private static final Double DEFAULT_MIN_ATTRIBUTES_REQUIRED_TO_SEARCH = 1D;
    private static final Double UPDATED_MIN_ATTRIBUTES_REQUIRED_TO_SEARCH = 2D;

    private static final Double DEFAULT_MAX_TEI_COUNT_TO_RETURN = 1D;
    private static final Double UPDATED_MAX_TEI_COUNT_TO_RETURN = 2D;

    private static final String DEFAULT_ACCESS_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_ACCESS_LEVEL = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_ENROLLMENT_DATE_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_ENROLLMENT_DATE_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_INCIDENT_DATE_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_INCIDENT_DATE_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REGISTRATION = false;
    private static final Boolean UPDATED_REGISTRATION = true;

    private static final Boolean DEFAULT_WITHOUT_REGISTRATION = false;
    private static final Boolean UPDATED_WITHOUT_REGISTRATION = true;

    private static final String DEFAULT_DISPLAY_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_FORM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_FORM_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORGANISATION_UNITS_COUNT = 1;
    private static final Integer UPDATED_ORGANISATION_UNITS_COUNT = 2;

    private static final Integer DEFAULT_PROGRAM_STAGES_COUNT = 1;
    private static final Integer UPDATED_PROGRAM_STAGES_COUNT = 2;

    private static final Integer DEFAULT_PROGRAM_INDICATORS_COUNT = 1;
    private static final Integer UPDATED_PROGRAM_INDICATORS_COUNT = 2;

    private static final Integer DEFAULT_PROGRAM_TRACKED_ENTITY_ATTRIBUTES_COUNT = 1;
    private static final Integer UPDATED_PROGRAM_TRACKED_ENTITY_ATTRIBUTES_COUNT = 2;

    private static final String DEFAULT_ORGANISATION_UNITS_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_ORGANISATION_UNITS_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_PROGRAM_STAGES_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_PROGRAM_STAGES_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_PROGRAM_INDICATORS_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_PROGRAM_INDICATORS_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_PROGRAM_TRACKED_ENTITY_ATTRIBUTES_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_PROGRAM_TRACKED_ENTITY_ATTRIBUTES_CONTENT = "BBBBBBBBBB";

    private static final TypeTrack DEFAULT_TRACK = TypeTrack.NEW;
    private static final TypeTrack UPDATED_TRACK = TypeTrack.UPDATE;

    private static final String ENTITY_API_URL = "/api/programs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProgramRepository programRepository;

    @Mock
    private ProgramRepository programRepositoryMock;

    @Mock
    private ProgramService programServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProgramMockMvc;

    private Program program;

    private Program insertedProgram;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Program createEntity(EntityManager em) {
        Program program = new Program()
            .name(DEFAULT_NAME)
            .created(DEFAULT_CREATED)
            .lastUpdated(DEFAULT_LAST_UPDATED)
            .shortName(DEFAULT_SHORT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .version(DEFAULT_VERSION)
            .enrollmentDateLabel(DEFAULT_ENROLLMENT_DATE_LABEL)
            .incidentDateLabel(DEFAULT_INCIDENT_DATE_LABEL)
            .programType(DEFAULT_PROGRAM_TYPE)
            .displayIncidentDate(DEFAULT_DISPLAY_INCIDENT_DATE)
            .ignoreOverdueEvents(DEFAULT_IGNORE_OVERDUE_EVENTS)
            .userRoles(DEFAULT_USER_ROLES)
            .onlyEnrollOnce(DEFAULT_ONLY_ENROLL_ONCE)
            .notificationTemplates(DEFAULT_NOTIFICATION_TEMPLATES)
            .selectEnrollmentDatesInFuture(DEFAULT_SELECT_ENROLLMENT_DATES_IN_FUTURE)
            .selectIncidentDatesInFuture(DEFAULT_SELECT_INCIDENT_DATES_IN_FUTURE)
            .trackedEntityType(DEFAULT_TRACKED_ENTITY_TYPE)
            .style(DEFAULT_STYLE)
            .skipOffline(DEFAULT_SKIP_OFFLINE)
            .displayFrontPageList(DEFAULT_DISPLAY_FRONT_PAGE_LIST)
            .useFirstStageDuringRegistration(DEFAULT_USE_FIRST_STAGE_DURING_REGISTRATION)
            .expiryDays(DEFAULT_EXPIRY_DAYS)
            .completeEventsExpiryDays(DEFAULT_COMPLETE_EVENTS_EXPIRY_DAYS)
            .openDaysAfterCoEndDate(DEFAULT_OPEN_DAYS_AFTER_CO_END_DATE)
            .minAttributesRequiredToSearch(DEFAULT_MIN_ATTRIBUTES_REQUIRED_TO_SEARCH)
            .maxTeiCountToReturn(DEFAULT_MAX_TEI_COUNT_TO_RETURN)
            .accessLevel(DEFAULT_ACCESS_LEVEL)
            .displayEnrollmentDateLabel(DEFAULT_DISPLAY_ENROLLMENT_DATE_LABEL)
            .displayIncidentDateLabel(DEFAULT_DISPLAY_INCIDENT_DATE_LABEL)
            .registration(DEFAULT_REGISTRATION)
            .withoutRegistration(DEFAULT_WITHOUT_REGISTRATION)
            .displayShortName(DEFAULT_DISPLAY_SHORT_NAME)
            .displayDescription(DEFAULT_DISPLAY_DESCRIPTION)
            .displayFormName(DEFAULT_DISPLAY_FORM_NAME)
            .displayName(DEFAULT_DISPLAY_NAME)
            .organisationUnitsCount(DEFAULT_ORGANISATION_UNITS_COUNT)
            .programStagesCount(DEFAULT_PROGRAM_STAGES_COUNT)
            .programIndicatorsCount(DEFAULT_PROGRAM_INDICATORS_COUNT)
            .programTrackedEntityAttributesCount(DEFAULT_PROGRAM_TRACKED_ENTITY_ATTRIBUTES_COUNT)
            .organisationUnitsContent(DEFAULT_ORGANISATION_UNITS_CONTENT)
            .programStagesContent(DEFAULT_PROGRAM_STAGES_CONTENT)
            .programIndicatorsContent(DEFAULT_PROGRAM_INDICATORS_CONTENT)
            .programTrackedEntityAttributesContent(DEFAULT_PROGRAM_TRACKED_ENTITY_ATTRIBUTES_CONTENT)
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
        program.setCreatedBy(dHISUser);
        // Add required entity
        program.setLastUpdatedBy(dHISUser);
        return program;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Program createUpdatedEntity(EntityManager em) {
        Program program = new Program()
            .name(UPDATED_NAME)
            .created(UPDATED_CREATED)
            .lastUpdated(UPDATED_LAST_UPDATED)
            .shortName(UPDATED_SHORT_NAME)
            .description(UPDATED_DESCRIPTION)
            .version(UPDATED_VERSION)
            .enrollmentDateLabel(UPDATED_ENROLLMENT_DATE_LABEL)
            .incidentDateLabel(UPDATED_INCIDENT_DATE_LABEL)
            .programType(UPDATED_PROGRAM_TYPE)
            .displayIncidentDate(UPDATED_DISPLAY_INCIDENT_DATE)
            .ignoreOverdueEvents(UPDATED_IGNORE_OVERDUE_EVENTS)
            .userRoles(UPDATED_USER_ROLES)
            .onlyEnrollOnce(UPDATED_ONLY_ENROLL_ONCE)
            .notificationTemplates(UPDATED_NOTIFICATION_TEMPLATES)
            .selectEnrollmentDatesInFuture(UPDATED_SELECT_ENROLLMENT_DATES_IN_FUTURE)
            .selectIncidentDatesInFuture(UPDATED_SELECT_INCIDENT_DATES_IN_FUTURE)
            .trackedEntityType(UPDATED_TRACKED_ENTITY_TYPE)
            .style(UPDATED_STYLE)
            .skipOffline(UPDATED_SKIP_OFFLINE)
            .displayFrontPageList(UPDATED_DISPLAY_FRONT_PAGE_LIST)
            .useFirstStageDuringRegistration(UPDATED_USE_FIRST_STAGE_DURING_REGISTRATION)
            .expiryDays(UPDATED_EXPIRY_DAYS)
            .completeEventsExpiryDays(UPDATED_COMPLETE_EVENTS_EXPIRY_DAYS)
            .openDaysAfterCoEndDate(UPDATED_OPEN_DAYS_AFTER_CO_END_DATE)
            .minAttributesRequiredToSearch(UPDATED_MIN_ATTRIBUTES_REQUIRED_TO_SEARCH)
            .maxTeiCountToReturn(UPDATED_MAX_TEI_COUNT_TO_RETURN)
            .accessLevel(UPDATED_ACCESS_LEVEL)
            .displayEnrollmentDateLabel(UPDATED_DISPLAY_ENROLLMENT_DATE_LABEL)
            .displayIncidentDateLabel(UPDATED_DISPLAY_INCIDENT_DATE_LABEL)
            .registration(UPDATED_REGISTRATION)
            .withoutRegistration(UPDATED_WITHOUT_REGISTRATION)
            .displayShortName(UPDATED_DISPLAY_SHORT_NAME)
            .displayDescription(UPDATED_DISPLAY_DESCRIPTION)
            .displayFormName(UPDATED_DISPLAY_FORM_NAME)
            .displayName(UPDATED_DISPLAY_NAME)
            .organisationUnitsCount(UPDATED_ORGANISATION_UNITS_COUNT)
            .programStagesCount(UPDATED_PROGRAM_STAGES_COUNT)
            .programIndicatorsCount(UPDATED_PROGRAM_INDICATORS_COUNT)
            .programTrackedEntityAttributesCount(UPDATED_PROGRAM_TRACKED_ENTITY_ATTRIBUTES_COUNT)
            .organisationUnitsContent(UPDATED_ORGANISATION_UNITS_CONTENT)
            .programStagesContent(UPDATED_PROGRAM_STAGES_CONTENT)
            .programIndicatorsContent(UPDATED_PROGRAM_INDICATORS_CONTENT)
            .programTrackedEntityAttributesContent(UPDATED_PROGRAM_TRACKED_ENTITY_ATTRIBUTES_CONTENT)
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
        program.setCreatedBy(dHISUser);
        // Add required entity
        program.setLastUpdatedBy(dHISUser);
        return program;
    }

    @BeforeEach
    public void initTest() {
        program = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedProgram != null) {
            programRepository.delete(insertedProgram);
            insertedProgram = null;
        }
    }

    @Test
    @Transactional
    void createProgram() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Program
        var returnedProgram = om.readValue(
            restProgramMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(program)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Program.class
        );

        // Validate the Program in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertProgramUpdatableFieldsEquals(returnedProgram, getPersistedProgram(returnedProgram));

        insertedProgram = returnedProgram;
    }

    @Test
    @Transactional
    void createProgramWithExistingId() throws Exception {
        // Create the Program with an existing ID
        program.setId("existing_id");

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProgramMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(program)))
            .andExpect(status().isBadRequest());

        // Validate the Program in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTrackIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        program.setTrack(null);

        // Create the Program, which fails.

        restProgramMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(program)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPrograms() throws Exception {
        // Initialize the database
        insertedProgram = programRepository.saveAndFlush(program);

        // Get all the programList
        restProgramMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(program.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED)))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED)))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.doubleValue())))
            .andExpect(jsonPath("$.[*].enrollmentDateLabel").value(hasItem(DEFAULT_ENROLLMENT_DATE_LABEL)))
            .andExpect(jsonPath("$.[*].incidentDateLabel").value(hasItem(DEFAULT_INCIDENT_DATE_LABEL)))
            .andExpect(jsonPath("$.[*].programType").value(hasItem(DEFAULT_PROGRAM_TYPE)))
            .andExpect(jsonPath("$.[*].displayIncidentDate").value(hasItem(DEFAULT_DISPLAY_INCIDENT_DATE.booleanValue())))
            .andExpect(jsonPath("$.[*].ignoreOverdueEvents").value(hasItem(DEFAULT_IGNORE_OVERDUE_EVENTS.booleanValue())))
            .andExpect(jsonPath("$.[*].userRoles").value(hasItem(DEFAULT_USER_ROLES)))
            .andExpect(jsonPath("$.[*].onlyEnrollOnce").value(hasItem(DEFAULT_ONLY_ENROLL_ONCE.booleanValue())))
            .andExpect(jsonPath("$.[*].notificationTemplates").value(hasItem(DEFAULT_NOTIFICATION_TEMPLATES)))
            .andExpect(
                jsonPath("$.[*].selectEnrollmentDatesInFuture").value(hasItem(DEFAULT_SELECT_ENROLLMENT_DATES_IN_FUTURE.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].selectIncidentDatesInFuture").value(hasItem(DEFAULT_SELECT_INCIDENT_DATES_IN_FUTURE.booleanValue())))
            .andExpect(jsonPath("$.[*].trackedEntityType").value(hasItem(DEFAULT_TRACKED_ENTITY_TYPE)))
            .andExpect(jsonPath("$.[*].style").value(hasItem(DEFAULT_STYLE)))
            .andExpect(jsonPath("$.[*].skipOffline").value(hasItem(DEFAULT_SKIP_OFFLINE.booleanValue())))
            .andExpect(jsonPath("$.[*].displayFrontPageList").value(hasItem(DEFAULT_DISPLAY_FRONT_PAGE_LIST.booleanValue())))
            .andExpect(
                jsonPath("$.[*].useFirstStageDuringRegistration").value(hasItem(DEFAULT_USE_FIRST_STAGE_DURING_REGISTRATION.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].expiryDays").value(hasItem(DEFAULT_EXPIRY_DAYS.doubleValue())))
            .andExpect(jsonPath("$.[*].completeEventsExpiryDays").value(hasItem(DEFAULT_COMPLETE_EVENTS_EXPIRY_DAYS.doubleValue())))
            .andExpect(jsonPath("$.[*].openDaysAfterCoEndDate").value(hasItem(DEFAULT_OPEN_DAYS_AFTER_CO_END_DATE.doubleValue())))
            .andExpect(
                jsonPath("$.[*].minAttributesRequiredToSearch").value(hasItem(DEFAULT_MIN_ATTRIBUTES_REQUIRED_TO_SEARCH.doubleValue()))
            )
            .andExpect(jsonPath("$.[*].maxTeiCountToReturn").value(hasItem(DEFAULT_MAX_TEI_COUNT_TO_RETURN.doubleValue())))
            .andExpect(jsonPath("$.[*].accessLevel").value(hasItem(DEFAULT_ACCESS_LEVEL)))
            .andExpect(jsonPath("$.[*].displayEnrollmentDateLabel").value(hasItem(DEFAULT_DISPLAY_ENROLLMENT_DATE_LABEL)))
            .andExpect(jsonPath("$.[*].displayIncidentDateLabel").value(hasItem(DEFAULT_DISPLAY_INCIDENT_DATE_LABEL)))
            .andExpect(jsonPath("$.[*].registration").value(hasItem(DEFAULT_REGISTRATION.booleanValue())))
            .andExpect(jsonPath("$.[*].withoutRegistration").value(hasItem(DEFAULT_WITHOUT_REGISTRATION.booleanValue())))
            .andExpect(jsonPath("$.[*].displayShortName").value(hasItem(DEFAULT_DISPLAY_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].displayDescription").value(hasItem(DEFAULT_DISPLAY_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].displayFormName").value(hasItem(DEFAULT_DISPLAY_FORM_NAME)))
            .andExpect(jsonPath("$.[*].displayName").value(hasItem(DEFAULT_DISPLAY_NAME)))
            .andExpect(jsonPath("$.[*].organisationUnitsCount").value(hasItem(DEFAULT_ORGANISATION_UNITS_COUNT)))
            .andExpect(jsonPath("$.[*].programStagesCount").value(hasItem(DEFAULT_PROGRAM_STAGES_COUNT)))
            .andExpect(jsonPath("$.[*].programIndicatorsCount").value(hasItem(DEFAULT_PROGRAM_INDICATORS_COUNT)))
            .andExpect(
                jsonPath("$.[*].programTrackedEntityAttributesCount").value(hasItem(DEFAULT_PROGRAM_TRACKED_ENTITY_ATTRIBUTES_COUNT))
            )
            .andExpect(jsonPath("$.[*].organisationUnitsContent").value(hasItem(DEFAULT_ORGANISATION_UNITS_CONTENT)))
            .andExpect(jsonPath("$.[*].programStagesContent").value(hasItem(DEFAULT_PROGRAM_STAGES_CONTENT)))
            .andExpect(jsonPath("$.[*].programIndicatorsContent").value(hasItem(DEFAULT_PROGRAM_INDICATORS_CONTENT)))
            .andExpect(
                jsonPath("$.[*].programTrackedEntityAttributesContent").value(hasItem(DEFAULT_PROGRAM_TRACKED_ENTITY_ATTRIBUTES_CONTENT))
            )
            .andExpect(jsonPath("$.[*].track").value(hasItem(DEFAULT_TRACK.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProgramsWithEagerRelationshipsIsEnabled() throws Exception {
        when(programServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProgramMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(programServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProgramsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(programServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProgramMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(programRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getProgram() throws Exception {
        // Initialize the database
        insertedProgram = programRepository.saveAndFlush(program);

        // Get the program
        restProgramMockMvc
            .perform(get(ENTITY_API_URL_ID, program.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(program.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED))
            .andExpect(jsonPath("$.lastUpdated").value(DEFAULT_LAST_UPDATED))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.doubleValue()))
            .andExpect(jsonPath("$.enrollmentDateLabel").value(DEFAULT_ENROLLMENT_DATE_LABEL))
            .andExpect(jsonPath("$.incidentDateLabel").value(DEFAULT_INCIDENT_DATE_LABEL))
            .andExpect(jsonPath("$.programType").value(DEFAULT_PROGRAM_TYPE))
            .andExpect(jsonPath("$.displayIncidentDate").value(DEFAULT_DISPLAY_INCIDENT_DATE.booleanValue()))
            .andExpect(jsonPath("$.ignoreOverdueEvents").value(DEFAULT_IGNORE_OVERDUE_EVENTS.booleanValue()))
            .andExpect(jsonPath("$.userRoles").value(DEFAULT_USER_ROLES))
            .andExpect(jsonPath("$.onlyEnrollOnce").value(DEFAULT_ONLY_ENROLL_ONCE.booleanValue()))
            .andExpect(jsonPath("$.notificationTemplates").value(DEFAULT_NOTIFICATION_TEMPLATES))
            .andExpect(jsonPath("$.selectEnrollmentDatesInFuture").value(DEFAULT_SELECT_ENROLLMENT_DATES_IN_FUTURE.booleanValue()))
            .andExpect(jsonPath("$.selectIncidentDatesInFuture").value(DEFAULT_SELECT_INCIDENT_DATES_IN_FUTURE.booleanValue()))
            .andExpect(jsonPath("$.trackedEntityType").value(DEFAULT_TRACKED_ENTITY_TYPE))
            .andExpect(jsonPath("$.style").value(DEFAULT_STYLE))
            .andExpect(jsonPath("$.skipOffline").value(DEFAULT_SKIP_OFFLINE.booleanValue()))
            .andExpect(jsonPath("$.displayFrontPageList").value(DEFAULT_DISPLAY_FRONT_PAGE_LIST.booleanValue()))
            .andExpect(jsonPath("$.useFirstStageDuringRegistration").value(DEFAULT_USE_FIRST_STAGE_DURING_REGISTRATION.booleanValue()))
            .andExpect(jsonPath("$.expiryDays").value(DEFAULT_EXPIRY_DAYS.doubleValue()))
            .andExpect(jsonPath("$.completeEventsExpiryDays").value(DEFAULT_COMPLETE_EVENTS_EXPIRY_DAYS.doubleValue()))
            .andExpect(jsonPath("$.openDaysAfterCoEndDate").value(DEFAULT_OPEN_DAYS_AFTER_CO_END_DATE.doubleValue()))
            .andExpect(jsonPath("$.minAttributesRequiredToSearch").value(DEFAULT_MIN_ATTRIBUTES_REQUIRED_TO_SEARCH.doubleValue()))
            .andExpect(jsonPath("$.maxTeiCountToReturn").value(DEFAULT_MAX_TEI_COUNT_TO_RETURN.doubleValue()))
            .andExpect(jsonPath("$.accessLevel").value(DEFAULT_ACCESS_LEVEL))
            .andExpect(jsonPath("$.displayEnrollmentDateLabel").value(DEFAULT_DISPLAY_ENROLLMENT_DATE_LABEL))
            .andExpect(jsonPath("$.displayIncidentDateLabel").value(DEFAULT_DISPLAY_INCIDENT_DATE_LABEL))
            .andExpect(jsonPath("$.registration").value(DEFAULT_REGISTRATION.booleanValue()))
            .andExpect(jsonPath("$.withoutRegistration").value(DEFAULT_WITHOUT_REGISTRATION.booleanValue()))
            .andExpect(jsonPath("$.displayShortName").value(DEFAULT_DISPLAY_SHORT_NAME))
            .andExpect(jsonPath("$.displayDescription").value(DEFAULT_DISPLAY_DESCRIPTION))
            .andExpect(jsonPath("$.displayFormName").value(DEFAULT_DISPLAY_FORM_NAME))
            .andExpect(jsonPath("$.displayName").value(DEFAULT_DISPLAY_NAME))
            .andExpect(jsonPath("$.organisationUnitsCount").value(DEFAULT_ORGANISATION_UNITS_COUNT))
            .andExpect(jsonPath("$.programStagesCount").value(DEFAULT_PROGRAM_STAGES_COUNT))
            .andExpect(jsonPath("$.programIndicatorsCount").value(DEFAULT_PROGRAM_INDICATORS_COUNT))
            .andExpect(jsonPath("$.programTrackedEntityAttributesCount").value(DEFAULT_PROGRAM_TRACKED_ENTITY_ATTRIBUTES_COUNT))
            .andExpect(jsonPath("$.organisationUnitsContent").value(DEFAULT_ORGANISATION_UNITS_CONTENT))
            .andExpect(jsonPath("$.programStagesContent").value(DEFAULT_PROGRAM_STAGES_CONTENT))
            .andExpect(jsonPath("$.programIndicatorsContent").value(DEFAULT_PROGRAM_INDICATORS_CONTENT))
            .andExpect(jsonPath("$.programTrackedEntityAttributesContent").value(DEFAULT_PROGRAM_TRACKED_ENTITY_ATTRIBUTES_CONTENT))
            .andExpect(jsonPath("$.track").value(DEFAULT_TRACK.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProgram() throws Exception {
        // Get the program
        restProgramMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProgram() throws Exception {
        // Initialize the database
        insertedProgram = programRepository.saveAndFlush(program);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the program
        Program updatedProgram = programRepository.findById(program.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProgram are not directly saved in db
        em.detach(updatedProgram);
        updatedProgram
            .name(UPDATED_NAME)
            .created(UPDATED_CREATED)
            .lastUpdated(UPDATED_LAST_UPDATED)
            .shortName(UPDATED_SHORT_NAME)
            .description(UPDATED_DESCRIPTION)
            .version(UPDATED_VERSION)
            .enrollmentDateLabel(UPDATED_ENROLLMENT_DATE_LABEL)
            .incidentDateLabel(UPDATED_INCIDENT_DATE_LABEL)
            .programType(UPDATED_PROGRAM_TYPE)
            .displayIncidentDate(UPDATED_DISPLAY_INCIDENT_DATE)
            .ignoreOverdueEvents(UPDATED_IGNORE_OVERDUE_EVENTS)
            .userRoles(UPDATED_USER_ROLES)
            .onlyEnrollOnce(UPDATED_ONLY_ENROLL_ONCE)
            .notificationTemplates(UPDATED_NOTIFICATION_TEMPLATES)
            .selectEnrollmentDatesInFuture(UPDATED_SELECT_ENROLLMENT_DATES_IN_FUTURE)
            .selectIncidentDatesInFuture(UPDATED_SELECT_INCIDENT_DATES_IN_FUTURE)
            .trackedEntityType(UPDATED_TRACKED_ENTITY_TYPE)
            .style(UPDATED_STYLE)
            .skipOffline(UPDATED_SKIP_OFFLINE)
            .displayFrontPageList(UPDATED_DISPLAY_FRONT_PAGE_LIST)
            .useFirstStageDuringRegistration(UPDATED_USE_FIRST_STAGE_DURING_REGISTRATION)
            .expiryDays(UPDATED_EXPIRY_DAYS)
            .completeEventsExpiryDays(UPDATED_COMPLETE_EVENTS_EXPIRY_DAYS)
            .openDaysAfterCoEndDate(UPDATED_OPEN_DAYS_AFTER_CO_END_DATE)
            .minAttributesRequiredToSearch(UPDATED_MIN_ATTRIBUTES_REQUIRED_TO_SEARCH)
            .maxTeiCountToReturn(UPDATED_MAX_TEI_COUNT_TO_RETURN)
            .accessLevel(UPDATED_ACCESS_LEVEL)
            .displayEnrollmentDateLabel(UPDATED_DISPLAY_ENROLLMENT_DATE_LABEL)
            .displayIncidentDateLabel(UPDATED_DISPLAY_INCIDENT_DATE_LABEL)
            .registration(UPDATED_REGISTRATION)
            .withoutRegistration(UPDATED_WITHOUT_REGISTRATION)
            .displayShortName(UPDATED_DISPLAY_SHORT_NAME)
            .displayDescription(UPDATED_DISPLAY_DESCRIPTION)
            .displayFormName(UPDATED_DISPLAY_FORM_NAME)
            .displayName(UPDATED_DISPLAY_NAME)
            .organisationUnitsCount(UPDATED_ORGANISATION_UNITS_COUNT)
            .programStagesCount(UPDATED_PROGRAM_STAGES_COUNT)
            .programIndicatorsCount(UPDATED_PROGRAM_INDICATORS_COUNT)
            .programTrackedEntityAttributesCount(UPDATED_PROGRAM_TRACKED_ENTITY_ATTRIBUTES_COUNT)
            .organisationUnitsContent(UPDATED_ORGANISATION_UNITS_CONTENT)
            .programStagesContent(UPDATED_PROGRAM_STAGES_CONTENT)
            .programIndicatorsContent(UPDATED_PROGRAM_INDICATORS_CONTENT)
            .programTrackedEntityAttributesContent(UPDATED_PROGRAM_TRACKED_ENTITY_ATTRIBUTES_CONTENT)
            .track(UPDATED_TRACK);

        restProgramMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProgram.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedProgram))
            )
            .andExpect(status().isOk());

        // Validate the Program in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProgramToMatchAllProperties(updatedProgram);
    }

    @Test
    @Transactional
    void putNonExistingProgram() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        program.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgramMockMvc
            .perform(put(ENTITY_API_URL_ID, program.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(program)))
            .andExpect(status().isBadRequest());

        // Validate the Program in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProgram() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        program.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(program))
            )
            .andExpect(status().isBadRequest());

        // Validate the Program in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProgram() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        program.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(program)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Program in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProgramWithPatch() throws Exception {
        // Initialize the database
        insertedProgram = programRepository.saveAndFlush(program);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the program using partial update
        Program partialUpdatedProgram = new Program();
        partialUpdatedProgram.setId(program.getId());

        partialUpdatedProgram
            .name(UPDATED_NAME)
            .lastUpdated(UPDATED_LAST_UPDATED)
            .enrollmentDateLabel(UPDATED_ENROLLMENT_DATE_LABEL)
            .displayIncidentDate(UPDATED_DISPLAY_INCIDENT_DATE)
            .ignoreOverdueEvents(UPDATED_IGNORE_OVERDUE_EVENTS)
            .userRoles(UPDATED_USER_ROLES)
            .notificationTemplates(UPDATED_NOTIFICATION_TEMPLATES)
            .selectEnrollmentDatesInFuture(UPDATED_SELECT_ENROLLMENT_DATES_IN_FUTURE)
            .selectIncidentDatesInFuture(UPDATED_SELECT_INCIDENT_DATES_IN_FUTURE)
            .trackedEntityType(UPDATED_TRACKED_ENTITY_TYPE)
            .style(UPDATED_STYLE)
            .skipOffline(UPDATED_SKIP_OFFLINE)
            .useFirstStageDuringRegistration(UPDATED_USE_FIRST_STAGE_DURING_REGISTRATION)
            .openDaysAfterCoEndDate(UPDATED_OPEN_DAYS_AFTER_CO_END_DATE)
            .maxTeiCountToReturn(UPDATED_MAX_TEI_COUNT_TO_RETURN)
            .accessLevel(UPDATED_ACCESS_LEVEL)
            .displayIncidentDateLabel(UPDATED_DISPLAY_INCIDENT_DATE_LABEL)
            .withoutRegistration(UPDATED_WITHOUT_REGISTRATION)
            .displayShortName(UPDATED_DISPLAY_SHORT_NAME)
            .displayName(UPDATED_DISPLAY_NAME)
            .organisationUnitsCount(UPDATED_ORGANISATION_UNITS_COUNT)
            .programStagesCount(UPDATED_PROGRAM_STAGES_COUNT)
            .programIndicatorsCount(UPDATED_PROGRAM_INDICATORS_COUNT)
            .programIndicatorsContent(UPDATED_PROGRAM_INDICATORS_CONTENT);

        restProgramMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProgram.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProgram))
            )
            .andExpect(status().isOk());

        // Validate the Program in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProgramUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedProgram, program), getPersistedProgram(program));
    }

    @Test
    @Transactional
    void fullUpdateProgramWithPatch() throws Exception {
        // Initialize the database
        insertedProgram = programRepository.saveAndFlush(program);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the program using partial update
        Program partialUpdatedProgram = new Program();
        partialUpdatedProgram.setId(program.getId());

        partialUpdatedProgram
            .name(UPDATED_NAME)
            .created(UPDATED_CREATED)
            .lastUpdated(UPDATED_LAST_UPDATED)
            .shortName(UPDATED_SHORT_NAME)
            .description(UPDATED_DESCRIPTION)
            .version(UPDATED_VERSION)
            .enrollmentDateLabel(UPDATED_ENROLLMENT_DATE_LABEL)
            .incidentDateLabel(UPDATED_INCIDENT_DATE_LABEL)
            .programType(UPDATED_PROGRAM_TYPE)
            .displayIncidentDate(UPDATED_DISPLAY_INCIDENT_DATE)
            .ignoreOverdueEvents(UPDATED_IGNORE_OVERDUE_EVENTS)
            .userRoles(UPDATED_USER_ROLES)
            .onlyEnrollOnce(UPDATED_ONLY_ENROLL_ONCE)
            .notificationTemplates(UPDATED_NOTIFICATION_TEMPLATES)
            .selectEnrollmentDatesInFuture(UPDATED_SELECT_ENROLLMENT_DATES_IN_FUTURE)
            .selectIncidentDatesInFuture(UPDATED_SELECT_INCIDENT_DATES_IN_FUTURE)
            .trackedEntityType(UPDATED_TRACKED_ENTITY_TYPE)
            .style(UPDATED_STYLE)
            .skipOffline(UPDATED_SKIP_OFFLINE)
            .displayFrontPageList(UPDATED_DISPLAY_FRONT_PAGE_LIST)
            .useFirstStageDuringRegistration(UPDATED_USE_FIRST_STAGE_DURING_REGISTRATION)
            .expiryDays(UPDATED_EXPIRY_DAYS)
            .completeEventsExpiryDays(UPDATED_COMPLETE_EVENTS_EXPIRY_DAYS)
            .openDaysAfterCoEndDate(UPDATED_OPEN_DAYS_AFTER_CO_END_DATE)
            .minAttributesRequiredToSearch(UPDATED_MIN_ATTRIBUTES_REQUIRED_TO_SEARCH)
            .maxTeiCountToReturn(UPDATED_MAX_TEI_COUNT_TO_RETURN)
            .accessLevel(UPDATED_ACCESS_LEVEL)
            .displayEnrollmentDateLabel(UPDATED_DISPLAY_ENROLLMENT_DATE_LABEL)
            .displayIncidentDateLabel(UPDATED_DISPLAY_INCIDENT_DATE_LABEL)
            .registration(UPDATED_REGISTRATION)
            .withoutRegistration(UPDATED_WITHOUT_REGISTRATION)
            .displayShortName(UPDATED_DISPLAY_SHORT_NAME)
            .displayDescription(UPDATED_DISPLAY_DESCRIPTION)
            .displayFormName(UPDATED_DISPLAY_FORM_NAME)
            .displayName(UPDATED_DISPLAY_NAME)
            .organisationUnitsCount(UPDATED_ORGANISATION_UNITS_COUNT)
            .programStagesCount(UPDATED_PROGRAM_STAGES_COUNT)
            .programIndicatorsCount(UPDATED_PROGRAM_INDICATORS_COUNT)
            .programTrackedEntityAttributesCount(UPDATED_PROGRAM_TRACKED_ENTITY_ATTRIBUTES_COUNT)
            .organisationUnitsContent(UPDATED_ORGANISATION_UNITS_CONTENT)
            .programStagesContent(UPDATED_PROGRAM_STAGES_CONTENT)
            .programIndicatorsContent(UPDATED_PROGRAM_INDICATORS_CONTENT)
            .programTrackedEntityAttributesContent(UPDATED_PROGRAM_TRACKED_ENTITY_ATTRIBUTES_CONTENT)
            .track(UPDATED_TRACK);

        restProgramMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProgram.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProgram))
            )
            .andExpect(status().isOk());

        // Validate the Program in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProgramUpdatableFieldsEquals(partialUpdatedProgram, getPersistedProgram(partialUpdatedProgram));
    }

    @Test
    @Transactional
    void patchNonExistingProgram() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        program.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgramMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, program.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(program))
            )
            .andExpect(status().isBadRequest());

        // Validate the Program in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProgram() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        program.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(program))
            )
            .andExpect(status().isBadRequest());

        // Validate the Program in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProgram() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        program.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(program)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Program in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProgram() throws Exception {
        // Initialize the database
        insertedProgram = programRepository.saveAndFlush(program);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the program
        restProgramMockMvc
            .perform(delete(ENTITY_API_URL_ID, program.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return programRepository.count();
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

    protected Program getPersistedProgram(Program program) {
        return programRepository.findById(program.getId()).orElseThrow();
    }

    protected void assertPersistedProgramToMatchAllProperties(Program expectedProgram) {
        assertProgramAllPropertiesEquals(expectedProgram, getPersistedProgram(expectedProgram));
    }

    protected void assertPersistedProgramToMatchUpdatableProperties(Program expectedProgram) {
        assertProgramAllUpdatablePropertiesEquals(expectedProgram, getPersistedProgram(expectedProgram));
    }
}
