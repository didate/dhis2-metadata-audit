package com.didate.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.didate.IntegrationTest;
import com.didate.domain.DHISUser;
import com.didate.domain.TrackedEntityAttribute;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.repository.TrackedEntityAttributeRepository;
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
 * Integration tests for the {@link TrackedEntityAttributeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TrackedEntityAttributeResourceIT {

    private static final Instant DEFAULT_LAST_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_GENERATED = false;
    private static final Boolean UPDATED_GENERATED = true;

    private static final String DEFAULT_VALUE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CONFIDENTIAL = false;
    private static final Boolean UPDATED_CONFIDENTIAL = true;

    private static final String DEFAULT_DISPLAY_FORM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_FORM_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_UNIQUEE = false;
    private static final Boolean UPDATED_UNIQUEE = true;

    private static final String DEFAULT_DIMENSION_ITEM_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DIMENSION_ITEM_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_AGGREGATION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_AGGREGATION_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DISPLAY_IN_LIST_NO_PROGRAM = false;
    private static final Boolean UPDATED_DISPLAY_IN_LIST_NO_PROGRAM = true;

    private static final String DEFAULT_DISPLAY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PATTERNE = "AAAAAAAAAA";
    private static final String UPDATED_PATTERNE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SKIP_SYNCHRONIZATION = false;
    private static final Boolean UPDATED_SKIP_SYNCHRONIZATION = true;

    private static final String DEFAULT_DISPLAY_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_SHORT_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_PERIOD_OFFSET = 1;
    private static final Integer UPDATED_PERIOD_OFFSET = 2;

    private static final Boolean DEFAULT_DISPLAY_ON_VISIT_SCHEDULE = false;
    private static final Boolean UPDATED_DISPLAY_ON_VISIT_SCHEDULE = true;

    private static final String DEFAULT_FORM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FORM_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ORGUNIT_SCOPE = false;
    private static final Boolean UPDATED_ORGUNIT_SCOPE = true;

    private static final String DEFAULT_DIMENSION_ITEM = "AAAAAAAAAA";
    private static final String UPDATED_DIMENSION_ITEM = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INHERIT = false;
    private static final Boolean UPDATED_INHERIT = true;

    private static final Boolean DEFAULT_OPTION_SET_VALUE = false;
    private static final Boolean UPDATED_OPTION_SET_VALUE = true;

    private static final TypeTrack DEFAULT_TRACK = TypeTrack.NEW;
    private static final TypeTrack UPDATED_TRACK = TypeTrack.UPDATE;

    private static final String ENTITY_API_URL = "/api/tracked-entity-attributes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private TrackedEntityAttributeRepository trackedEntityAttributeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrackedEntityAttributeMockMvc;

    private TrackedEntityAttribute trackedEntityAttribute;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrackedEntityAttribute createEntity(EntityManager em) {
        TrackedEntityAttribute trackedEntityAttribute = new TrackedEntityAttribute()
            .lastUpdated(DEFAULT_LAST_UPDATED)
            .created(DEFAULT_CREATED)
            .name(DEFAULT_NAME)
            .shortName(DEFAULT_SHORT_NAME)
            .generated(DEFAULT_GENERATED)
            .valueType(DEFAULT_VALUE_TYPE)
            .confidential(DEFAULT_CONFIDENTIAL)
            .displayFormName(DEFAULT_DISPLAY_FORM_NAME)
            .uniquee(DEFAULT_UNIQUEE)
            .dimensionItemType(DEFAULT_DIMENSION_ITEM_TYPE)
            .aggregationType(DEFAULT_AGGREGATION_TYPE)
            .displayInListNoProgram(DEFAULT_DISPLAY_IN_LIST_NO_PROGRAM)
            .displayName(DEFAULT_DISPLAY_NAME)
            .patterne(DEFAULT_PATTERNE)
            .skipSynchronization(DEFAULT_SKIP_SYNCHRONIZATION)
            .displayShortName(DEFAULT_DISPLAY_SHORT_NAME)
            .periodOffset(DEFAULT_PERIOD_OFFSET)
            .displayOnVisitSchedule(DEFAULT_DISPLAY_ON_VISIT_SCHEDULE)
            .formName(DEFAULT_FORM_NAME)
            .orgunitScope(DEFAULT_ORGUNIT_SCOPE)
            .dimensionItem(DEFAULT_DIMENSION_ITEM)
            .inherit(DEFAULT_INHERIT)
            .optionSetValue(DEFAULT_OPTION_SET_VALUE)
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
        trackedEntityAttribute.setCreatedBy(dHISUser);
        // Add required entity
        trackedEntityAttribute.setLastUpdatedBy(dHISUser);
        return trackedEntityAttribute;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrackedEntityAttribute createUpdatedEntity(EntityManager em) {
        TrackedEntityAttribute trackedEntityAttribute = new TrackedEntityAttribute()
            .lastUpdated(UPDATED_LAST_UPDATED)
            .created(UPDATED_CREATED)
            .name(UPDATED_NAME)
            .shortName(UPDATED_SHORT_NAME)
            .generated(UPDATED_GENERATED)
            .valueType(UPDATED_VALUE_TYPE)
            .confidential(UPDATED_CONFIDENTIAL)
            .displayFormName(UPDATED_DISPLAY_FORM_NAME)
            .uniquee(UPDATED_UNIQUEE)
            .dimensionItemType(UPDATED_DIMENSION_ITEM_TYPE)
            .aggregationType(UPDATED_AGGREGATION_TYPE)
            .displayInListNoProgram(UPDATED_DISPLAY_IN_LIST_NO_PROGRAM)
            .displayName(UPDATED_DISPLAY_NAME)
            .patterne(UPDATED_PATTERNE)
            .skipSynchronization(UPDATED_SKIP_SYNCHRONIZATION)
            .displayShortName(UPDATED_DISPLAY_SHORT_NAME)
            .periodOffset(UPDATED_PERIOD_OFFSET)
            .displayOnVisitSchedule(UPDATED_DISPLAY_ON_VISIT_SCHEDULE)
            .formName(UPDATED_FORM_NAME)
            .orgunitScope(UPDATED_ORGUNIT_SCOPE)
            .dimensionItem(UPDATED_DIMENSION_ITEM)
            .inherit(UPDATED_INHERIT)
            .optionSetValue(UPDATED_OPTION_SET_VALUE)
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
        trackedEntityAttribute.setCreatedBy(dHISUser);
        // Add required entity
        trackedEntityAttribute.setLastUpdatedBy(dHISUser);
        return trackedEntityAttribute;
    }

    @BeforeEach
    public void initTest() {
        trackedEntityAttribute = createEntity(em);
    }

    @Test
    @Transactional
    void createTrackedEntityAttribute() throws Exception {
        int databaseSizeBeforeCreate = trackedEntityAttributeRepository.findAll().size();
        // Create the TrackedEntityAttribute
        restTrackedEntityAttributeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trackedEntityAttribute))
            )
            .andExpect(status().isCreated());

        // Validate the TrackedEntityAttribute in the database
        List<TrackedEntityAttribute> trackedEntityAttributeList = trackedEntityAttributeRepository.findAll();
        assertThat(trackedEntityAttributeList).hasSize(databaseSizeBeforeCreate + 1);
        TrackedEntityAttribute testTrackedEntityAttribute = trackedEntityAttributeList.get(trackedEntityAttributeList.size() - 1);
        assertThat(testTrackedEntityAttribute.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
        assertThat(testTrackedEntityAttribute.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testTrackedEntityAttribute.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTrackedEntityAttribute.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
        assertThat(testTrackedEntityAttribute.getGenerated()).isEqualTo(DEFAULT_GENERATED);
        assertThat(testTrackedEntityAttribute.getValueType()).isEqualTo(DEFAULT_VALUE_TYPE);
        assertThat(testTrackedEntityAttribute.getConfidential()).isEqualTo(DEFAULT_CONFIDENTIAL);
        assertThat(testTrackedEntityAttribute.getDisplayFormName()).isEqualTo(DEFAULT_DISPLAY_FORM_NAME);
        assertThat(testTrackedEntityAttribute.getUniquee()).isEqualTo(DEFAULT_UNIQUEE);
        assertThat(testTrackedEntityAttribute.getDimensionItemType()).isEqualTo(DEFAULT_DIMENSION_ITEM_TYPE);
        assertThat(testTrackedEntityAttribute.getAggregationType()).isEqualTo(DEFAULT_AGGREGATION_TYPE);
        assertThat(testTrackedEntityAttribute.getDisplayInListNoProgram()).isEqualTo(DEFAULT_DISPLAY_IN_LIST_NO_PROGRAM);
        assertThat(testTrackedEntityAttribute.getDisplayName()).isEqualTo(DEFAULT_DISPLAY_NAME);
        assertThat(testTrackedEntityAttribute.getPatterne()).isEqualTo(DEFAULT_PATTERNE);
        assertThat(testTrackedEntityAttribute.getSkipSynchronization()).isEqualTo(DEFAULT_SKIP_SYNCHRONIZATION);
        assertThat(testTrackedEntityAttribute.getDisplayShortName()).isEqualTo(DEFAULT_DISPLAY_SHORT_NAME);
        assertThat(testTrackedEntityAttribute.getPeriodOffset()).isEqualTo(DEFAULT_PERIOD_OFFSET);
        assertThat(testTrackedEntityAttribute.getDisplayOnVisitSchedule()).isEqualTo(DEFAULT_DISPLAY_ON_VISIT_SCHEDULE);
        assertThat(testTrackedEntityAttribute.getFormName()).isEqualTo(DEFAULT_FORM_NAME);
        assertThat(testTrackedEntityAttribute.getOrgunitScope()).isEqualTo(DEFAULT_ORGUNIT_SCOPE);
        assertThat(testTrackedEntityAttribute.getDimensionItem()).isEqualTo(DEFAULT_DIMENSION_ITEM);
        assertThat(testTrackedEntityAttribute.getInherit()).isEqualTo(DEFAULT_INHERIT);
        assertThat(testTrackedEntityAttribute.getOptionSetValue()).isEqualTo(DEFAULT_OPTION_SET_VALUE);
        assertThat(testTrackedEntityAttribute.getTrack()).isEqualTo(DEFAULT_TRACK);
    }

    @Test
    @Transactional
    void createTrackedEntityAttributeWithExistingId() throws Exception {
        // Create the TrackedEntityAttribute with an existing ID
        trackedEntityAttribute.setId("existing_id");

        int databaseSizeBeforeCreate = trackedEntityAttributeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrackedEntityAttributeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trackedEntityAttribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrackedEntityAttribute in the database
        List<TrackedEntityAttribute> trackedEntityAttributeList = trackedEntityAttributeRepository.findAll();
        assertThat(trackedEntityAttributeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTrackIsRequired() throws Exception {
        int databaseSizeBeforeTest = trackedEntityAttributeRepository.findAll().size();
        // set the field null
        trackedEntityAttribute.setTrack(null);

        // Create the TrackedEntityAttribute, which fails.

        restTrackedEntityAttributeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trackedEntityAttribute))
            )
            .andExpect(status().isBadRequest());

        List<TrackedEntityAttribute> trackedEntityAttributeList = trackedEntityAttributeRepository.findAll();
        assertThat(trackedEntityAttributeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTrackedEntityAttributes() throws Exception {
        // Initialize the database
        trackedEntityAttribute.setId(UUID.randomUUID().toString());
        trackedEntityAttributeRepository.saveAndFlush(trackedEntityAttribute);

        // Get all the trackedEntityAttributeList
        restTrackedEntityAttributeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trackedEntityAttribute.getId())))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].generated").value(hasItem(DEFAULT_GENERATED.booleanValue())))
            .andExpect(jsonPath("$.[*].valueType").value(hasItem(DEFAULT_VALUE_TYPE)))
            .andExpect(jsonPath("$.[*].confidential").value(hasItem(DEFAULT_CONFIDENTIAL.booleanValue())))
            .andExpect(jsonPath("$.[*].displayFormName").value(hasItem(DEFAULT_DISPLAY_FORM_NAME)))
            .andExpect(jsonPath("$.[*].uniquee").value(hasItem(DEFAULT_UNIQUEE.booleanValue())))
            .andExpect(jsonPath("$.[*].dimensionItemType").value(hasItem(DEFAULT_DIMENSION_ITEM_TYPE)))
            .andExpect(jsonPath("$.[*].aggregationType").value(hasItem(DEFAULT_AGGREGATION_TYPE)))
            .andExpect(jsonPath("$.[*].displayInListNoProgram").value(hasItem(DEFAULT_DISPLAY_IN_LIST_NO_PROGRAM.booleanValue())))
            .andExpect(jsonPath("$.[*].displayName").value(hasItem(DEFAULT_DISPLAY_NAME)))
            .andExpect(jsonPath("$.[*].patterne").value(hasItem(DEFAULT_PATTERNE)))
            .andExpect(jsonPath("$.[*].skipSynchronization").value(hasItem(DEFAULT_SKIP_SYNCHRONIZATION.booleanValue())))
            .andExpect(jsonPath("$.[*].displayShortName").value(hasItem(DEFAULT_DISPLAY_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].periodOffset").value(hasItem(DEFAULT_PERIOD_OFFSET)))
            .andExpect(jsonPath("$.[*].displayOnVisitSchedule").value(hasItem(DEFAULT_DISPLAY_ON_VISIT_SCHEDULE.booleanValue())))
            .andExpect(jsonPath("$.[*].formName").value(hasItem(DEFAULT_FORM_NAME)))
            .andExpect(jsonPath("$.[*].orgunitScope").value(hasItem(DEFAULT_ORGUNIT_SCOPE.booleanValue())))
            .andExpect(jsonPath("$.[*].dimensionItem").value(hasItem(DEFAULT_DIMENSION_ITEM)))
            .andExpect(jsonPath("$.[*].inherit").value(hasItem(DEFAULT_INHERIT.booleanValue())))
            .andExpect(jsonPath("$.[*].optionSetValue").value(hasItem(DEFAULT_OPTION_SET_VALUE.booleanValue())))
            .andExpect(jsonPath("$.[*].track").value(hasItem(DEFAULT_TRACK.toString())));
    }

    @Test
    @Transactional
    void getTrackedEntityAttribute() throws Exception {
        // Initialize the database
        trackedEntityAttribute.setId(UUID.randomUUID().toString());
        trackedEntityAttributeRepository.saveAndFlush(trackedEntityAttribute);

        // Get the trackedEntityAttribute
        restTrackedEntityAttributeMockMvc
            .perform(get(ENTITY_API_URL_ID, trackedEntityAttribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trackedEntityAttribute.getId()))
            .andExpect(jsonPath("$.lastUpdated").value(DEFAULT_LAST_UPDATED.toString()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME))
            .andExpect(jsonPath("$.generated").value(DEFAULT_GENERATED.booleanValue()))
            .andExpect(jsonPath("$.valueType").value(DEFAULT_VALUE_TYPE))
            .andExpect(jsonPath("$.confidential").value(DEFAULT_CONFIDENTIAL.booleanValue()))
            .andExpect(jsonPath("$.displayFormName").value(DEFAULT_DISPLAY_FORM_NAME))
            .andExpect(jsonPath("$.uniquee").value(DEFAULT_UNIQUEE.booleanValue()))
            .andExpect(jsonPath("$.dimensionItemType").value(DEFAULT_DIMENSION_ITEM_TYPE))
            .andExpect(jsonPath("$.aggregationType").value(DEFAULT_AGGREGATION_TYPE))
            .andExpect(jsonPath("$.displayInListNoProgram").value(DEFAULT_DISPLAY_IN_LIST_NO_PROGRAM.booleanValue()))
            .andExpect(jsonPath("$.displayName").value(DEFAULT_DISPLAY_NAME))
            .andExpect(jsonPath("$.patterne").value(DEFAULT_PATTERNE))
            .andExpect(jsonPath("$.skipSynchronization").value(DEFAULT_SKIP_SYNCHRONIZATION.booleanValue()))
            .andExpect(jsonPath("$.displayShortName").value(DEFAULT_DISPLAY_SHORT_NAME))
            .andExpect(jsonPath("$.periodOffset").value(DEFAULT_PERIOD_OFFSET))
            .andExpect(jsonPath("$.displayOnVisitSchedule").value(DEFAULT_DISPLAY_ON_VISIT_SCHEDULE.booleanValue()))
            .andExpect(jsonPath("$.formName").value(DEFAULT_FORM_NAME))
            .andExpect(jsonPath("$.orgunitScope").value(DEFAULT_ORGUNIT_SCOPE.booleanValue()))
            .andExpect(jsonPath("$.dimensionItem").value(DEFAULT_DIMENSION_ITEM))
            .andExpect(jsonPath("$.inherit").value(DEFAULT_INHERIT.booleanValue()))
            .andExpect(jsonPath("$.optionSetValue").value(DEFAULT_OPTION_SET_VALUE.booleanValue()))
            .andExpect(jsonPath("$.track").value(DEFAULT_TRACK.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTrackedEntityAttribute() throws Exception {
        // Get the trackedEntityAttribute
        restTrackedEntityAttributeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTrackedEntityAttribute() throws Exception {
        // Initialize the database
        trackedEntityAttribute.setId(UUID.randomUUID().toString());
        trackedEntityAttributeRepository.saveAndFlush(trackedEntityAttribute);

        int databaseSizeBeforeUpdate = trackedEntityAttributeRepository.findAll().size();

        // Update the trackedEntityAttribute
        TrackedEntityAttribute updatedTrackedEntityAttribute = trackedEntityAttributeRepository
            .findById(trackedEntityAttribute.getId())
            .get();
        // Disconnect from session so that the updates on updatedTrackedEntityAttribute are not directly saved in db
        em.detach(updatedTrackedEntityAttribute);
        updatedTrackedEntityAttribute
            .lastUpdated(UPDATED_LAST_UPDATED)
            .created(UPDATED_CREATED)
            .name(UPDATED_NAME)
            .shortName(UPDATED_SHORT_NAME)
            .generated(UPDATED_GENERATED)
            .valueType(UPDATED_VALUE_TYPE)
            .confidential(UPDATED_CONFIDENTIAL)
            .displayFormName(UPDATED_DISPLAY_FORM_NAME)
            .uniquee(UPDATED_UNIQUEE)
            .dimensionItemType(UPDATED_DIMENSION_ITEM_TYPE)
            .aggregationType(UPDATED_AGGREGATION_TYPE)
            .displayInListNoProgram(UPDATED_DISPLAY_IN_LIST_NO_PROGRAM)
            .displayName(UPDATED_DISPLAY_NAME)
            .patterne(UPDATED_PATTERNE)
            .skipSynchronization(UPDATED_SKIP_SYNCHRONIZATION)
            .displayShortName(UPDATED_DISPLAY_SHORT_NAME)
            .periodOffset(UPDATED_PERIOD_OFFSET)
            .displayOnVisitSchedule(UPDATED_DISPLAY_ON_VISIT_SCHEDULE)
            .formName(UPDATED_FORM_NAME)
            .orgunitScope(UPDATED_ORGUNIT_SCOPE)
            .dimensionItem(UPDATED_DIMENSION_ITEM)
            .inherit(UPDATED_INHERIT)
            .optionSetValue(UPDATED_OPTION_SET_VALUE)
            .track(UPDATED_TRACK);

        restTrackedEntityAttributeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTrackedEntityAttribute.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTrackedEntityAttribute))
            )
            .andExpect(status().isOk());

        // Validate the TrackedEntityAttribute in the database
        List<TrackedEntityAttribute> trackedEntityAttributeList = trackedEntityAttributeRepository.findAll();
        assertThat(trackedEntityAttributeList).hasSize(databaseSizeBeforeUpdate);
        TrackedEntityAttribute testTrackedEntityAttribute = trackedEntityAttributeList.get(trackedEntityAttributeList.size() - 1);
        assertThat(testTrackedEntityAttribute.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
        assertThat(testTrackedEntityAttribute.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testTrackedEntityAttribute.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTrackedEntityAttribute.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testTrackedEntityAttribute.getGenerated()).isEqualTo(UPDATED_GENERATED);
        assertThat(testTrackedEntityAttribute.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testTrackedEntityAttribute.getConfidential()).isEqualTo(UPDATED_CONFIDENTIAL);
        assertThat(testTrackedEntityAttribute.getDisplayFormName()).isEqualTo(UPDATED_DISPLAY_FORM_NAME);
        assertThat(testTrackedEntityAttribute.getUniquee()).isEqualTo(UPDATED_UNIQUEE);
        assertThat(testTrackedEntityAttribute.getDimensionItemType()).isEqualTo(UPDATED_DIMENSION_ITEM_TYPE);
        assertThat(testTrackedEntityAttribute.getAggregationType()).isEqualTo(UPDATED_AGGREGATION_TYPE);
        assertThat(testTrackedEntityAttribute.getDisplayInListNoProgram()).isEqualTo(UPDATED_DISPLAY_IN_LIST_NO_PROGRAM);
        assertThat(testTrackedEntityAttribute.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testTrackedEntityAttribute.getPatterne()).isEqualTo(UPDATED_PATTERNE);
        assertThat(testTrackedEntityAttribute.getSkipSynchronization()).isEqualTo(UPDATED_SKIP_SYNCHRONIZATION);
        assertThat(testTrackedEntityAttribute.getDisplayShortName()).isEqualTo(UPDATED_DISPLAY_SHORT_NAME);
        assertThat(testTrackedEntityAttribute.getPeriodOffset()).isEqualTo(UPDATED_PERIOD_OFFSET);
        assertThat(testTrackedEntityAttribute.getDisplayOnVisitSchedule()).isEqualTo(UPDATED_DISPLAY_ON_VISIT_SCHEDULE);
        assertThat(testTrackedEntityAttribute.getFormName()).isEqualTo(UPDATED_FORM_NAME);
        assertThat(testTrackedEntityAttribute.getOrgunitScope()).isEqualTo(UPDATED_ORGUNIT_SCOPE);
        assertThat(testTrackedEntityAttribute.getDimensionItem()).isEqualTo(UPDATED_DIMENSION_ITEM);
        assertThat(testTrackedEntityAttribute.getInherit()).isEqualTo(UPDATED_INHERIT);
        assertThat(testTrackedEntityAttribute.getOptionSetValue()).isEqualTo(UPDATED_OPTION_SET_VALUE);
        assertThat(testTrackedEntityAttribute.getTrack()).isEqualTo(UPDATED_TRACK);
    }

    @Test
    @Transactional
    void putNonExistingTrackedEntityAttribute() throws Exception {
        int databaseSizeBeforeUpdate = trackedEntityAttributeRepository.findAll().size();
        trackedEntityAttribute.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrackedEntityAttributeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trackedEntityAttribute.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trackedEntityAttribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrackedEntityAttribute in the database
        List<TrackedEntityAttribute> trackedEntityAttributeList = trackedEntityAttributeRepository.findAll();
        assertThat(trackedEntityAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTrackedEntityAttribute() throws Exception {
        int databaseSizeBeforeUpdate = trackedEntityAttributeRepository.findAll().size();
        trackedEntityAttribute.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrackedEntityAttributeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trackedEntityAttribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrackedEntityAttribute in the database
        List<TrackedEntityAttribute> trackedEntityAttributeList = trackedEntityAttributeRepository.findAll();
        assertThat(trackedEntityAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTrackedEntityAttribute() throws Exception {
        int databaseSizeBeforeUpdate = trackedEntityAttributeRepository.findAll().size();
        trackedEntityAttribute.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrackedEntityAttributeMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trackedEntityAttribute))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TrackedEntityAttribute in the database
        List<TrackedEntityAttribute> trackedEntityAttributeList = trackedEntityAttributeRepository.findAll();
        assertThat(trackedEntityAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTrackedEntityAttributeWithPatch() throws Exception {
        // Initialize the database
        trackedEntityAttribute.setId(UUID.randomUUID().toString());
        trackedEntityAttributeRepository.saveAndFlush(trackedEntityAttribute);

        int databaseSizeBeforeUpdate = trackedEntityAttributeRepository.findAll().size();

        // Update the trackedEntityAttribute using partial update
        TrackedEntityAttribute partialUpdatedTrackedEntityAttribute = new TrackedEntityAttribute();
        partialUpdatedTrackedEntityAttribute.setId(trackedEntityAttribute.getId());

        partialUpdatedTrackedEntityAttribute
            .name(UPDATED_NAME)
            .shortName(UPDATED_SHORT_NAME)
            .generated(UPDATED_GENERATED)
            .valueType(UPDATED_VALUE_TYPE)
            .confidential(UPDATED_CONFIDENTIAL)
            .uniquee(UPDATED_UNIQUEE)
            .periodOffset(UPDATED_PERIOD_OFFSET)
            .displayOnVisitSchedule(UPDATED_DISPLAY_ON_VISIT_SCHEDULE)
            .formName(UPDATED_FORM_NAME)
            .orgunitScope(UPDATED_ORGUNIT_SCOPE);

        restTrackedEntityAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrackedEntityAttribute.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrackedEntityAttribute))
            )
            .andExpect(status().isOk());

        // Validate the TrackedEntityAttribute in the database
        List<TrackedEntityAttribute> trackedEntityAttributeList = trackedEntityAttributeRepository.findAll();
        assertThat(trackedEntityAttributeList).hasSize(databaseSizeBeforeUpdate);
        TrackedEntityAttribute testTrackedEntityAttribute = trackedEntityAttributeList.get(trackedEntityAttributeList.size() - 1);
        assertThat(testTrackedEntityAttribute.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
        assertThat(testTrackedEntityAttribute.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testTrackedEntityAttribute.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTrackedEntityAttribute.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testTrackedEntityAttribute.getGenerated()).isEqualTo(UPDATED_GENERATED);
        assertThat(testTrackedEntityAttribute.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testTrackedEntityAttribute.getConfidential()).isEqualTo(UPDATED_CONFIDENTIAL);
        assertThat(testTrackedEntityAttribute.getDisplayFormName()).isEqualTo(DEFAULT_DISPLAY_FORM_NAME);
        assertThat(testTrackedEntityAttribute.getUniquee()).isEqualTo(UPDATED_UNIQUEE);
        assertThat(testTrackedEntityAttribute.getDimensionItemType()).isEqualTo(DEFAULT_DIMENSION_ITEM_TYPE);
        assertThat(testTrackedEntityAttribute.getAggregationType()).isEqualTo(DEFAULT_AGGREGATION_TYPE);
        assertThat(testTrackedEntityAttribute.getDisplayInListNoProgram()).isEqualTo(DEFAULT_DISPLAY_IN_LIST_NO_PROGRAM);
        assertThat(testTrackedEntityAttribute.getDisplayName()).isEqualTo(DEFAULT_DISPLAY_NAME);
        assertThat(testTrackedEntityAttribute.getPatterne()).isEqualTo(DEFAULT_PATTERNE);
        assertThat(testTrackedEntityAttribute.getSkipSynchronization()).isEqualTo(DEFAULT_SKIP_SYNCHRONIZATION);
        assertThat(testTrackedEntityAttribute.getDisplayShortName()).isEqualTo(DEFAULT_DISPLAY_SHORT_NAME);
        assertThat(testTrackedEntityAttribute.getPeriodOffset()).isEqualTo(UPDATED_PERIOD_OFFSET);
        assertThat(testTrackedEntityAttribute.getDisplayOnVisitSchedule()).isEqualTo(UPDATED_DISPLAY_ON_VISIT_SCHEDULE);
        assertThat(testTrackedEntityAttribute.getFormName()).isEqualTo(UPDATED_FORM_NAME);
        assertThat(testTrackedEntityAttribute.getOrgunitScope()).isEqualTo(UPDATED_ORGUNIT_SCOPE);
        assertThat(testTrackedEntityAttribute.getDimensionItem()).isEqualTo(DEFAULT_DIMENSION_ITEM);
        assertThat(testTrackedEntityAttribute.getInherit()).isEqualTo(DEFAULT_INHERIT);
        assertThat(testTrackedEntityAttribute.getOptionSetValue()).isEqualTo(DEFAULT_OPTION_SET_VALUE);
        assertThat(testTrackedEntityAttribute.getTrack()).isEqualTo(DEFAULT_TRACK);
    }

    @Test
    @Transactional
    void fullUpdateTrackedEntityAttributeWithPatch() throws Exception {
        // Initialize the database
        trackedEntityAttribute.setId(UUID.randomUUID().toString());
        trackedEntityAttributeRepository.saveAndFlush(trackedEntityAttribute);

        int databaseSizeBeforeUpdate = trackedEntityAttributeRepository.findAll().size();

        // Update the trackedEntityAttribute using partial update
        TrackedEntityAttribute partialUpdatedTrackedEntityAttribute = new TrackedEntityAttribute();
        partialUpdatedTrackedEntityAttribute.setId(trackedEntityAttribute.getId());

        partialUpdatedTrackedEntityAttribute
            .lastUpdated(UPDATED_LAST_UPDATED)
            .created(UPDATED_CREATED)
            .name(UPDATED_NAME)
            .shortName(UPDATED_SHORT_NAME)
            .generated(UPDATED_GENERATED)
            .valueType(UPDATED_VALUE_TYPE)
            .confidential(UPDATED_CONFIDENTIAL)
            .displayFormName(UPDATED_DISPLAY_FORM_NAME)
            .uniquee(UPDATED_UNIQUEE)
            .dimensionItemType(UPDATED_DIMENSION_ITEM_TYPE)
            .aggregationType(UPDATED_AGGREGATION_TYPE)
            .displayInListNoProgram(UPDATED_DISPLAY_IN_LIST_NO_PROGRAM)
            .displayName(UPDATED_DISPLAY_NAME)
            .patterne(UPDATED_PATTERNE)
            .skipSynchronization(UPDATED_SKIP_SYNCHRONIZATION)
            .displayShortName(UPDATED_DISPLAY_SHORT_NAME)
            .periodOffset(UPDATED_PERIOD_OFFSET)
            .displayOnVisitSchedule(UPDATED_DISPLAY_ON_VISIT_SCHEDULE)
            .formName(UPDATED_FORM_NAME)
            .orgunitScope(UPDATED_ORGUNIT_SCOPE)
            .dimensionItem(UPDATED_DIMENSION_ITEM)
            .inherit(UPDATED_INHERIT)
            .optionSetValue(UPDATED_OPTION_SET_VALUE)
            .track(UPDATED_TRACK);

        restTrackedEntityAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrackedEntityAttribute.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrackedEntityAttribute))
            )
            .andExpect(status().isOk());

        // Validate the TrackedEntityAttribute in the database
        List<TrackedEntityAttribute> trackedEntityAttributeList = trackedEntityAttributeRepository.findAll();
        assertThat(trackedEntityAttributeList).hasSize(databaseSizeBeforeUpdate);
        TrackedEntityAttribute testTrackedEntityAttribute = trackedEntityAttributeList.get(trackedEntityAttributeList.size() - 1);
        assertThat(testTrackedEntityAttribute.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
        assertThat(testTrackedEntityAttribute.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testTrackedEntityAttribute.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTrackedEntityAttribute.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testTrackedEntityAttribute.getGenerated()).isEqualTo(UPDATED_GENERATED);
        assertThat(testTrackedEntityAttribute.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testTrackedEntityAttribute.getConfidential()).isEqualTo(UPDATED_CONFIDENTIAL);
        assertThat(testTrackedEntityAttribute.getDisplayFormName()).isEqualTo(UPDATED_DISPLAY_FORM_NAME);
        assertThat(testTrackedEntityAttribute.getUniquee()).isEqualTo(UPDATED_UNIQUEE);
        assertThat(testTrackedEntityAttribute.getDimensionItemType()).isEqualTo(UPDATED_DIMENSION_ITEM_TYPE);
        assertThat(testTrackedEntityAttribute.getAggregationType()).isEqualTo(UPDATED_AGGREGATION_TYPE);
        assertThat(testTrackedEntityAttribute.getDisplayInListNoProgram()).isEqualTo(UPDATED_DISPLAY_IN_LIST_NO_PROGRAM);
        assertThat(testTrackedEntityAttribute.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testTrackedEntityAttribute.getPatterne()).isEqualTo(UPDATED_PATTERNE);
        assertThat(testTrackedEntityAttribute.getSkipSynchronization()).isEqualTo(UPDATED_SKIP_SYNCHRONIZATION);
        assertThat(testTrackedEntityAttribute.getDisplayShortName()).isEqualTo(UPDATED_DISPLAY_SHORT_NAME);
        assertThat(testTrackedEntityAttribute.getPeriodOffset()).isEqualTo(UPDATED_PERIOD_OFFSET);
        assertThat(testTrackedEntityAttribute.getDisplayOnVisitSchedule()).isEqualTo(UPDATED_DISPLAY_ON_VISIT_SCHEDULE);
        assertThat(testTrackedEntityAttribute.getFormName()).isEqualTo(UPDATED_FORM_NAME);
        assertThat(testTrackedEntityAttribute.getOrgunitScope()).isEqualTo(UPDATED_ORGUNIT_SCOPE);
        assertThat(testTrackedEntityAttribute.getDimensionItem()).isEqualTo(UPDATED_DIMENSION_ITEM);
        assertThat(testTrackedEntityAttribute.getInherit()).isEqualTo(UPDATED_INHERIT);
        assertThat(testTrackedEntityAttribute.getOptionSetValue()).isEqualTo(UPDATED_OPTION_SET_VALUE);
        assertThat(testTrackedEntityAttribute.getTrack()).isEqualTo(UPDATED_TRACK);
    }

    @Test
    @Transactional
    void patchNonExistingTrackedEntityAttribute() throws Exception {
        int databaseSizeBeforeUpdate = trackedEntityAttributeRepository.findAll().size();
        trackedEntityAttribute.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrackedEntityAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, trackedEntityAttribute.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trackedEntityAttribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrackedEntityAttribute in the database
        List<TrackedEntityAttribute> trackedEntityAttributeList = trackedEntityAttributeRepository.findAll();
        assertThat(trackedEntityAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTrackedEntityAttribute() throws Exception {
        int databaseSizeBeforeUpdate = trackedEntityAttributeRepository.findAll().size();
        trackedEntityAttribute.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrackedEntityAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trackedEntityAttribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrackedEntityAttribute in the database
        List<TrackedEntityAttribute> trackedEntityAttributeList = trackedEntityAttributeRepository.findAll();
        assertThat(trackedEntityAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTrackedEntityAttribute() throws Exception {
        int databaseSizeBeforeUpdate = trackedEntityAttributeRepository.findAll().size();
        trackedEntityAttribute.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrackedEntityAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trackedEntityAttribute))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TrackedEntityAttribute in the database
        List<TrackedEntityAttribute> trackedEntityAttributeList = trackedEntityAttributeRepository.findAll();
        assertThat(trackedEntityAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTrackedEntityAttribute() throws Exception {
        // Initialize the database
        trackedEntityAttribute.setId(UUID.randomUUID().toString());
        trackedEntityAttributeRepository.saveAndFlush(trackedEntityAttribute);

        int databaseSizeBeforeDelete = trackedEntityAttributeRepository.findAll().size();

        // Delete the trackedEntityAttribute
        restTrackedEntityAttributeMockMvc
            .perform(delete(ENTITY_API_URL_ID, trackedEntityAttribute.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TrackedEntityAttribute> trackedEntityAttributeList = trackedEntityAttributeRepository.findAll();
        assertThat(trackedEntityAttributeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
