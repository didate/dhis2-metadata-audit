package com.didate.web.rest;

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
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.data.domain.PageRequest;
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
            .onlyEnrollOnce(DEFAULT_ONLY_ENROLL_ONCE)
            .selectEnrollmentDatesInFuture(DEFAULT_SELECT_ENROLLMENT_DATES_IN_FUTURE)
            .selectIncidentDatesInFuture(DEFAULT_SELECT_INCIDENT_DATES_IN_FUTURE)
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
            .onlyEnrollOnce(UPDATED_ONLY_ENROLL_ONCE)
            .selectEnrollmentDatesInFuture(UPDATED_SELECT_ENROLLMENT_DATES_IN_FUTURE)
            .selectIncidentDatesInFuture(UPDATED_SELECT_INCIDENT_DATES_IN_FUTURE)
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

    @Test
    @Transactional
    void createProgram() throws Exception {
        int databaseSizeBeforeCreate = programRepository.findAll().size();
        // Create the Program
        restProgramMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(program)))
            .andExpect(status().isCreated());

        // Validate the Program in the database
        List<Program> programList = programRepository.findAll();
        assertThat(programList).hasSize(databaseSizeBeforeCreate + 1);
        Program testProgram = programList.get(programList.size() - 1);
        assertThat(testProgram.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProgram.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testProgram.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
        assertThat(testProgram.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
        assertThat(testProgram.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProgram.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testProgram.getEnrollmentDateLabel()).isEqualTo(DEFAULT_ENROLLMENT_DATE_LABEL);
        assertThat(testProgram.getIncidentDateLabel()).isEqualTo(DEFAULT_INCIDENT_DATE_LABEL);
        assertThat(testProgram.getProgramType()).isEqualTo(DEFAULT_PROGRAM_TYPE);
        assertThat(testProgram.getDisplayIncidentDate()).isEqualTo(DEFAULT_DISPLAY_INCIDENT_DATE);
        assertThat(testProgram.getIgnoreOverdueEvents()).isEqualTo(DEFAULT_IGNORE_OVERDUE_EVENTS);

        assertThat(testProgram.getOnlyEnrollOnce()).isEqualTo(DEFAULT_ONLY_ENROLL_ONCE);

        assertThat(testProgram.getSelectEnrollmentDatesInFuture()).isEqualTo(DEFAULT_SELECT_ENROLLMENT_DATES_IN_FUTURE);
        assertThat(testProgram.getSelectIncidentDatesInFuture()).isEqualTo(DEFAULT_SELECT_INCIDENT_DATES_IN_FUTURE);

        assertThat(testProgram.getSkipOffline()).isEqualTo(DEFAULT_SKIP_OFFLINE);
        assertThat(testProgram.getDisplayFrontPageList()).isEqualTo(DEFAULT_DISPLAY_FRONT_PAGE_LIST);
        assertThat(testProgram.getUseFirstStageDuringRegistration()).isEqualTo(DEFAULT_USE_FIRST_STAGE_DURING_REGISTRATION);
        assertThat(testProgram.getExpiryDays()).isEqualTo(DEFAULT_EXPIRY_DAYS);
        assertThat(testProgram.getCompleteEventsExpiryDays()).isEqualTo(DEFAULT_COMPLETE_EVENTS_EXPIRY_DAYS);
        assertThat(testProgram.getOpenDaysAfterCoEndDate()).isEqualTo(DEFAULT_OPEN_DAYS_AFTER_CO_END_DATE);
        assertThat(testProgram.getMinAttributesRequiredToSearch()).isEqualTo(DEFAULT_MIN_ATTRIBUTES_REQUIRED_TO_SEARCH);
        assertThat(testProgram.getMaxTeiCountToReturn()).isEqualTo(DEFAULT_MAX_TEI_COUNT_TO_RETURN);
        assertThat(testProgram.getAccessLevel()).isEqualTo(DEFAULT_ACCESS_LEVEL);
        assertThat(testProgram.getDisplayEnrollmentDateLabel()).isEqualTo(DEFAULT_DISPLAY_ENROLLMENT_DATE_LABEL);
        assertThat(testProgram.getDisplayIncidentDateLabel()).isEqualTo(DEFAULT_DISPLAY_INCIDENT_DATE_LABEL);
        assertThat(testProgram.getRegistration()).isEqualTo(DEFAULT_REGISTRATION);
        assertThat(testProgram.getWithoutRegistration()).isEqualTo(DEFAULT_WITHOUT_REGISTRATION);
        assertThat(testProgram.getDisplayShortName()).isEqualTo(DEFAULT_DISPLAY_SHORT_NAME);
        assertThat(testProgram.getDisplayDescription()).isEqualTo(DEFAULT_DISPLAY_DESCRIPTION);
        assertThat(testProgram.getDisplayFormName()).isEqualTo(DEFAULT_DISPLAY_FORM_NAME);
        assertThat(testProgram.getDisplayName()).isEqualTo(DEFAULT_DISPLAY_NAME);
        assertThat(testProgram.getOrganisationUnitsCount()).isEqualTo(DEFAULT_ORGANISATION_UNITS_COUNT);
        assertThat(testProgram.getProgramStagesCount()).isEqualTo(DEFAULT_PROGRAM_STAGES_COUNT);
        assertThat(testProgram.getProgramIndicatorsCount()).isEqualTo(DEFAULT_PROGRAM_INDICATORS_COUNT);
        assertThat(testProgram.getProgramTrackedEntityAttributesCount()).isEqualTo(DEFAULT_PROGRAM_TRACKED_ENTITY_ATTRIBUTES_COUNT);
        assertThat(testProgram.getOrganisationUnitsContent()).isEqualTo(DEFAULT_ORGANISATION_UNITS_CONTENT);
        assertThat(testProgram.getProgramStagesContent()).isEqualTo(DEFAULT_PROGRAM_STAGES_CONTENT);
        assertThat(testProgram.getProgramIndicatorsContent()).isEqualTo(DEFAULT_PROGRAM_INDICATORS_CONTENT);
        assertThat(testProgram.getProgramTrackedEntityAttributesContent()).isEqualTo(DEFAULT_PROGRAM_TRACKED_ENTITY_ATTRIBUTES_CONTENT);
        assertThat(testProgram.getTrack()).isEqualTo(DEFAULT_TRACK);
    }

    @Test
    @Transactional
    void createProgramWithExistingId() throws Exception {
        // Create the Program with an existing ID
        program.setId("existing_id");

        int databaseSizeBeforeCreate = programRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProgramMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(program)))
            .andExpect(status().isBadRequest());

        // Validate the Program in the database
        List<Program> programList = programRepository.findAll();
        assertThat(programList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTrackIsRequired() throws Exception {
        int databaseSizeBeforeTest = programRepository.findAll().size();
        // set the field null
        program.setTrack(null);

        // Create the Program, which fails.

        restProgramMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(program)))
            .andExpect(status().isBadRequest());

        List<Program> programList = programRepository.findAll();
        assertThat(programList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPrograms() throws Exception {
        // Initialize the database
        program.setId(UUID.randomUUID().toString());
        programRepository.saveAndFlush(program);

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
        program.setId(UUID.randomUUID().toString());
        programRepository.saveAndFlush(program);

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
        program.setId(UUID.randomUUID().toString());
        programRepository.saveAndFlush(program);

        int databaseSizeBeforeUpdate = programRepository.findAll().size();

        // Update the program
        Program updatedProgram = programRepository.findById(program.getId()).get();
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
            .onlyEnrollOnce(UPDATED_ONLY_ENROLL_ONCE)
            .selectEnrollmentDatesInFuture(UPDATED_SELECT_ENROLLMENT_DATES_IN_FUTURE)
            .selectIncidentDatesInFuture(UPDATED_SELECT_INCIDENT_DATES_IN_FUTURE)
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
                    .content(TestUtil.convertObjectToJsonBytes(updatedProgram))
            )
            .andExpect(status().isOk());

        // Validate the Program in the database
        List<Program> programList = programRepository.findAll();
        assertThat(programList).hasSize(databaseSizeBeforeUpdate);
        Program testProgram = programList.get(programList.size() - 1);
        assertThat(testProgram.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProgram.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testProgram.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
        assertThat(testProgram.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testProgram.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProgram.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testProgram.getEnrollmentDateLabel()).isEqualTo(UPDATED_ENROLLMENT_DATE_LABEL);
        assertThat(testProgram.getIncidentDateLabel()).isEqualTo(UPDATED_INCIDENT_DATE_LABEL);
        assertThat(testProgram.getProgramType()).isEqualTo(UPDATED_PROGRAM_TYPE);
        assertThat(testProgram.getDisplayIncidentDate()).isEqualTo(UPDATED_DISPLAY_INCIDENT_DATE);
        assertThat(testProgram.getIgnoreOverdueEvents()).isEqualTo(UPDATED_IGNORE_OVERDUE_EVENTS);

        assertThat(testProgram.getOnlyEnrollOnce()).isEqualTo(UPDATED_ONLY_ENROLL_ONCE);

        assertThat(testProgram.getSelectEnrollmentDatesInFuture()).isEqualTo(UPDATED_SELECT_ENROLLMENT_DATES_IN_FUTURE);
        assertThat(testProgram.getSelectIncidentDatesInFuture()).isEqualTo(UPDATED_SELECT_INCIDENT_DATES_IN_FUTURE);

        assertThat(testProgram.getSkipOffline()).isEqualTo(UPDATED_SKIP_OFFLINE);
        assertThat(testProgram.getDisplayFrontPageList()).isEqualTo(UPDATED_DISPLAY_FRONT_PAGE_LIST);
        assertThat(testProgram.getUseFirstStageDuringRegistration()).isEqualTo(UPDATED_USE_FIRST_STAGE_DURING_REGISTRATION);
        assertThat(testProgram.getExpiryDays()).isEqualTo(UPDATED_EXPIRY_DAYS);
        assertThat(testProgram.getCompleteEventsExpiryDays()).isEqualTo(UPDATED_COMPLETE_EVENTS_EXPIRY_DAYS);
        assertThat(testProgram.getOpenDaysAfterCoEndDate()).isEqualTo(UPDATED_OPEN_DAYS_AFTER_CO_END_DATE);
        assertThat(testProgram.getMinAttributesRequiredToSearch()).isEqualTo(UPDATED_MIN_ATTRIBUTES_REQUIRED_TO_SEARCH);
        assertThat(testProgram.getMaxTeiCountToReturn()).isEqualTo(UPDATED_MAX_TEI_COUNT_TO_RETURN);
        assertThat(testProgram.getAccessLevel()).isEqualTo(UPDATED_ACCESS_LEVEL);
        assertThat(testProgram.getDisplayEnrollmentDateLabel()).isEqualTo(UPDATED_DISPLAY_ENROLLMENT_DATE_LABEL);
        assertThat(testProgram.getDisplayIncidentDateLabel()).isEqualTo(UPDATED_DISPLAY_INCIDENT_DATE_LABEL);
        assertThat(testProgram.getRegistration()).isEqualTo(UPDATED_REGISTRATION);
        assertThat(testProgram.getWithoutRegistration()).isEqualTo(UPDATED_WITHOUT_REGISTRATION);
        assertThat(testProgram.getDisplayShortName()).isEqualTo(UPDATED_DISPLAY_SHORT_NAME);
        assertThat(testProgram.getDisplayDescription()).isEqualTo(UPDATED_DISPLAY_DESCRIPTION);
        assertThat(testProgram.getDisplayFormName()).isEqualTo(UPDATED_DISPLAY_FORM_NAME);
        assertThat(testProgram.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testProgram.getOrganisationUnitsCount()).isEqualTo(UPDATED_ORGANISATION_UNITS_COUNT);
        assertThat(testProgram.getProgramStagesCount()).isEqualTo(UPDATED_PROGRAM_STAGES_COUNT);
        assertThat(testProgram.getProgramIndicatorsCount()).isEqualTo(UPDATED_PROGRAM_INDICATORS_COUNT);
        assertThat(testProgram.getProgramTrackedEntityAttributesCount()).isEqualTo(UPDATED_PROGRAM_TRACKED_ENTITY_ATTRIBUTES_COUNT);
        assertThat(testProgram.getOrganisationUnitsContent()).isEqualTo(UPDATED_ORGANISATION_UNITS_CONTENT);
        assertThat(testProgram.getProgramStagesContent()).isEqualTo(UPDATED_PROGRAM_STAGES_CONTENT);
        assertThat(testProgram.getProgramIndicatorsContent()).isEqualTo(UPDATED_PROGRAM_INDICATORS_CONTENT);
        assertThat(testProgram.getProgramTrackedEntityAttributesContent()).isEqualTo(UPDATED_PROGRAM_TRACKED_ENTITY_ATTRIBUTES_CONTENT);
        assertThat(testProgram.getTrack()).isEqualTo(UPDATED_TRACK);
    }

    @Test
    @Transactional
    void putNonExistingProgram() throws Exception {
        int databaseSizeBeforeUpdate = programRepository.findAll().size();
        program.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgramMockMvc
            .perform(
                put(ENTITY_API_URL_ID, program.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(program))
            )
            .andExpect(status().isBadRequest());

        // Validate the Program in the database
        List<Program> programList = programRepository.findAll();
        assertThat(programList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProgram() throws Exception {
        int databaseSizeBeforeUpdate = programRepository.findAll().size();
        program.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(program))
            )
            .andExpect(status().isBadRequest());

        // Validate the Program in the database
        List<Program> programList = programRepository.findAll();
        assertThat(programList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProgram() throws Exception {
        int databaseSizeBeforeUpdate = programRepository.findAll().size();
        program.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(program)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Program in the database
        List<Program> programList = programRepository.findAll();
        assertThat(programList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProgramWithPatch() throws Exception {
        // Initialize the database
        program.setId(UUID.randomUUID().toString());
        programRepository.saveAndFlush(program);

        int databaseSizeBeforeUpdate = programRepository.findAll().size();

        // Update the program using partial update
        Program partialUpdatedProgram = new Program();
        partialUpdatedProgram.setId(program.getId());

        partialUpdatedProgram
            .lastUpdated(UPDATED_LAST_UPDATED)
            .enrollmentDateLabel(UPDATED_ENROLLMENT_DATE_LABEL)
            .displayIncidentDate(UPDATED_DISPLAY_INCIDENT_DATE)
            .ignoreOverdueEvents(UPDATED_IGNORE_OVERDUE_EVENTS)
            .selectIncidentDatesInFuture(UPDATED_SELECT_INCIDENT_DATES_IN_FUTURE)
            .completeEventsExpiryDays(UPDATED_COMPLETE_EVENTS_EXPIRY_DAYS)
            .openDaysAfterCoEndDate(UPDATED_OPEN_DAYS_AFTER_CO_END_DATE)
            .displayIncidentDateLabel(UPDATED_DISPLAY_INCIDENT_DATE_LABEL)
            .displayFormName(UPDATED_DISPLAY_FORM_NAME)
            .displayName(UPDATED_DISPLAY_NAME)
            .organisationUnitsCount(UPDATED_ORGANISATION_UNITS_COUNT)
            .programStagesCount(UPDATED_PROGRAM_STAGES_COUNT)
            .organisationUnitsContent(UPDATED_ORGANISATION_UNITS_CONTENT)
            .programTrackedEntityAttributesContent(UPDATED_PROGRAM_TRACKED_ENTITY_ATTRIBUTES_CONTENT);

        restProgramMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProgram.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProgram))
            )
            .andExpect(status().isOk());

        // Validate the Program in the database
        List<Program> programList = programRepository.findAll();
        assertThat(programList).hasSize(databaseSizeBeforeUpdate);
        Program testProgram = programList.get(programList.size() - 1);
        assertThat(testProgram.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProgram.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testProgram.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
        assertThat(testProgram.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
        assertThat(testProgram.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProgram.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testProgram.getEnrollmentDateLabel()).isEqualTo(UPDATED_ENROLLMENT_DATE_LABEL);
        assertThat(testProgram.getIncidentDateLabel()).isEqualTo(DEFAULT_INCIDENT_DATE_LABEL);
        assertThat(testProgram.getProgramType()).isEqualTo(DEFAULT_PROGRAM_TYPE);
        assertThat(testProgram.getDisplayIncidentDate()).isEqualTo(UPDATED_DISPLAY_INCIDENT_DATE);
        assertThat(testProgram.getIgnoreOverdueEvents()).isEqualTo(UPDATED_IGNORE_OVERDUE_EVENTS);

        assertThat(testProgram.getOnlyEnrollOnce()).isEqualTo(DEFAULT_ONLY_ENROLL_ONCE);

        assertThat(testProgram.getSelectEnrollmentDatesInFuture()).isEqualTo(DEFAULT_SELECT_ENROLLMENT_DATES_IN_FUTURE);
        assertThat(testProgram.getSelectIncidentDatesInFuture()).isEqualTo(UPDATED_SELECT_INCIDENT_DATES_IN_FUTURE);

        assertThat(testProgram.getSkipOffline()).isEqualTo(DEFAULT_SKIP_OFFLINE);
        assertThat(testProgram.getDisplayFrontPageList()).isEqualTo(DEFAULT_DISPLAY_FRONT_PAGE_LIST);
        assertThat(testProgram.getUseFirstStageDuringRegistration()).isEqualTo(DEFAULT_USE_FIRST_STAGE_DURING_REGISTRATION);
        assertThat(testProgram.getExpiryDays()).isEqualTo(DEFAULT_EXPIRY_DAYS);
        assertThat(testProgram.getCompleteEventsExpiryDays()).isEqualTo(UPDATED_COMPLETE_EVENTS_EXPIRY_DAYS);
        assertThat(testProgram.getOpenDaysAfterCoEndDate()).isEqualTo(UPDATED_OPEN_DAYS_AFTER_CO_END_DATE);
        assertThat(testProgram.getMinAttributesRequiredToSearch()).isEqualTo(DEFAULT_MIN_ATTRIBUTES_REQUIRED_TO_SEARCH);
        assertThat(testProgram.getMaxTeiCountToReturn()).isEqualTo(DEFAULT_MAX_TEI_COUNT_TO_RETURN);
        assertThat(testProgram.getAccessLevel()).isEqualTo(DEFAULT_ACCESS_LEVEL);
        assertThat(testProgram.getDisplayEnrollmentDateLabel()).isEqualTo(DEFAULT_DISPLAY_ENROLLMENT_DATE_LABEL);
        assertThat(testProgram.getDisplayIncidentDateLabel()).isEqualTo(UPDATED_DISPLAY_INCIDENT_DATE_LABEL);
        assertThat(testProgram.getRegistration()).isEqualTo(DEFAULT_REGISTRATION);
        assertThat(testProgram.getWithoutRegistration()).isEqualTo(DEFAULT_WITHOUT_REGISTRATION);
        assertThat(testProgram.getDisplayShortName()).isEqualTo(DEFAULT_DISPLAY_SHORT_NAME);
        assertThat(testProgram.getDisplayDescription()).isEqualTo(DEFAULT_DISPLAY_DESCRIPTION);
        assertThat(testProgram.getDisplayFormName()).isEqualTo(UPDATED_DISPLAY_FORM_NAME);
        assertThat(testProgram.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testProgram.getOrganisationUnitsCount()).isEqualTo(UPDATED_ORGANISATION_UNITS_COUNT);
        assertThat(testProgram.getProgramStagesCount()).isEqualTo(UPDATED_PROGRAM_STAGES_COUNT);
        assertThat(testProgram.getProgramIndicatorsCount()).isEqualTo(DEFAULT_PROGRAM_INDICATORS_COUNT);
        assertThat(testProgram.getProgramTrackedEntityAttributesCount()).isEqualTo(DEFAULT_PROGRAM_TRACKED_ENTITY_ATTRIBUTES_COUNT);
        assertThat(testProgram.getOrganisationUnitsContent()).isEqualTo(UPDATED_ORGANISATION_UNITS_CONTENT);
        assertThat(testProgram.getProgramStagesContent()).isEqualTo(DEFAULT_PROGRAM_STAGES_CONTENT);
        assertThat(testProgram.getProgramIndicatorsContent()).isEqualTo(DEFAULT_PROGRAM_INDICATORS_CONTENT);
        assertThat(testProgram.getProgramTrackedEntityAttributesContent()).isEqualTo(UPDATED_PROGRAM_TRACKED_ENTITY_ATTRIBUTES_CONTENT);
        assertThat(testProgram.getTrack()).isEqualTo(DEFAULT_TRACK);
    }

    @Test
    @Transactional
    void fullUpdateProgramWithPatch() throws Exception {
        // Initialize the database
        program.setId(UUID.randomUUID().toString());
        programRepository.saveAndFlush(program);

        int databaseSizeBeforeUpdate = programRepository.findAll().size();

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
            .onlyEnrollOnce(UPDATED_ONLY_ENROLL_ONCE)
            .selectEnrollmentDatesInFuture(UPDATED_SELECT_ENROLLMENT_DATES_IN_FUTURE)
            .selectIncidentDatesInFuture(UPDATED_SELECT_INCIDENT_DATES_IN_FUTURE)
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
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProgram))
            )
            .andExpect(status().isOk());

        // Validate the Program in the database
        List<Program> programList = programRepository.findAll();
        assertThat(programList).hasSize(databaseSizeBeforeUpdate);
        Program testProgram = programList.get(programList.size() - 1);
        assertThat(testProgram.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProgram.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testProgram.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
        assertThat(testProgram.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testProgram.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProgram.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testProgram.getEnrollmentDateLabel()).isEqualTo(UPDATED_ENROLLMENT_DATE_LABEL);
        assertThat(testProgram.getIncidentDateLabel()).isEqualTo(UPDATED_INCIDENT_DATE_LABEL);
        assertThat(testProgram.getProgramType()).isEqualTo(UPDATED_PROGRAM_TYPE);
        assertThat(testProgram.getDisplayIncidentDate()).isEqualTo(UPDATED_DISPLAY_INCIDENT_DATE);
        assertThat(testProgram.getIgnoreOverdueEvents()).isEqualTo(UPDATED_IGNORE_OVERDUE_EVENTS);

        assertThat(testProgram.getOnlyEnrollOnce()).isEqualTo(UPDATED_ONLY_ENROLL_ONCE);
        assertThat(testProgram.getSelectEnrollmentDatesInFuture()).isEqualTo(UPDATED_SELECT_ENROLLMENT_DATES_IN_FUTURE);
        assertThat(testProgram.getSelectIncidentDatesInFuture()).isEqualTo(UPDATED_SELECT_INCIDENT_DATES_IN_FUTURE);
        assertThat(testProgram.getSkipOffline()).isEqualTo(UPDATED_SKIP_OFFLINE);
        assertThat(testProgram.getDisplayFrontPageList()).isEqualTo(UPDATED_DISPLAY_FRONT_PAGE_LIST);
        assertThat(testProgram.getUseFirstStageDuringRegistration()).isEqualTo(UPDATED_USE_FIRST_STAGE_DURING_REGISTRATION);
        assertThat(testProgram.getExpiryDays()).isEqualTo(UPDATED_EXPIRY_DAYS);
        assertThat(testProgram.getCompleteEventsExpiryDays()).isEqualTo(UPDATED_COMPLETE_EVENTS_EXPIRY_DAYS);
        assertThat(testProgram.getOpenDaysAfterCoEndDate()).isEqualTo(UPDATED_OPEN_DAYS_AFTER_CO_END_DATE);
        assertThat(testProgram.getMinAttributesRequiredToSearch()).isEqualTo(UPDATED_MIN_ATTRIBUTES_REQUIRED_TO_SEARCH);
        assertThat(testProgram.getMaxTeiCountToReturn()).isEqualTo(UPDATED_MAX_TEI_COUNT_TO_RETURN);
        assertThat(testProgram.getAccessLevel()).isEqualTo(UPDATED_ACCESS_LEVEL);
        assertThat(testProgram.getDisplayEnrollmentDateLabel()).isEqualTo(UPDATED_DISPLAY_ENROLLMENT_DATE_LABEL);
        assertThat(testProgram.getDisplayIncidentDateLabel()).isEqualTo(UPDATED_DISPLAY_INCIDENT_DATE_LABEL);
        assertThat(testProgram.getRegistration()).isEqualTo(UPDATED_REGISTRATION);
        assertThat(testProgram.getWithoutRegistration()).isEqualTo(UPDATED_WITHOUT_REGISTRATION);
        assertThat(testProgram.getDisplayShortName()).isEqualTo(UPDATED_DISPLAY_SHORT_NAME);
        assertThat(testProgram.getDisplayDescription()).isEqualTo(UPDATED_DISPLAY_DESCRIPTION);
        assertThat(testProgram.getDisplayFormName()).isEqualTo(UPDATED_DISPLAY_FORM_NAME);
        assertThat(testProgram.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testProgram.getOrganisationUnitsCount()).isEqualTo(UPDATED_ORGANISATION_UNITS_COUNT);
        assertThat(testProgram.getProgramStagesCount()).isEqualTo(UPDATED_PROGRAM_STAGES_COUNT);
        assertThat(testProgram.getProgramIndicatorsCount()).isEqualTo(UPDATED_PROGRAM_INDICATORS_COUNT);
        assertThat(testProgram.getProgramTrackedEntityAttributesCount()).isEqualTo(UPDATED_PROGRAM_TRACKED_ENTITY_ATTRIBUTES_COUNT);
        assertThat(testProgram.getOrganisationUnitsContent()).isEqualTo(UPDATED_ORGANISATION_UNITS_CONTENT);
        assertThat(testProgram.getProgramStagesContent()).isEqualTo(UPDATED_PROGRAM_STAGES_CONTENT);
        assertThat(testProgram.getProgramIndicatorsContent()).isEqualTo(UPDATED_PROGRAM_INDICATORS_CONTENT);
        assertThat(testProgram.getProgramTrackedEntityAttributesContent()).isEqualTo(UPDATED_PROGRAM_TRACKED_ENTITY_ATTRIBUTES_CONTENT);
        assertThat(testProgram.getTrack()).isEqualTo(UPDATED_TRACK);
    }

    @Test
    @Transactional
    void patchNonExistingProgram() throws Exception {
        int databaseSizeBeforeUpdate = programRepository.findAll().size();
        program.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgramMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, program.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(program))
            )
            .andExpect(status().isBadRequest());

        // Validate the Program in the database
        List<Program> programList = programRepository.findAll();
        assertThat(programList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProgram() throws Exception {
        int databaseSizeBeforeUpdate = programRepository.findAll().size();
        program.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(program))
            )
            .andExpect(status().isBadRequest());

        // Validate the Program in the database
        List<Program> programList = programRepository.findAll();
        assertThat(programList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProgram() throws Exception {
        int databaseSizeBeforeUpdate = programRepository.findAll().size();
        program.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(program)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Program in the database
        List<Program> programList = programRepository.findAll();
        assertThat(programList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProgram() throws Exception {
        // Initialize the database
        program.setId(UUID.randomUUID().toString());
        programRepository.saveAndFlush(program);

        int databaseSizeBeforeDelete = programRepository.findAll().size();

        // Delete the program
        restProgramMockMvc
            .perform(delete(ENTITY_API_URL_ID, program.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Program> programList = programRepository.findAll();
        assertThat(programList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
