package com.didate.web.rest;

import static com.didate.domain.TrackedEntityAttributeAsserts.*;
import static com.didate.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.didate.IntegrationTest;
import com.didate.domain.DHISUser;
import com.didate.domain.TrackedEntityAttribute;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.repository.TrackedEntityAttributeRepository;
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
    private ObjectMapper om;

    @Autowired
    private TrackedEntityAttributeRepository trackedEntityAttributeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrackedEntityAttributeMockMvc;

    private TrackedEntityAttribute trackedEntityAttribute;

    private TrackedEntityAttribute insertedTrackedEntityAttribute;

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

    @AfterEach
    public void cleanup() {
        if (insertedTrackedEntityAttribute != null) {
            trackedEntityAttributeRepository.delete(insertedTrackedEntityAttribute);
            insertedTrackedEntityAttribute = null;
        }
    }

    @Test
    @Transactional
    void createTrackedEntityAttribute() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TrackedEntityAttribute
        var returnedTrackedEntityAttribute = om.readValue(
            restTrackedEntityAttributeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(trackedEntityAttribute)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TrackedEntityAttribute.class
        );

        // Validate the TrackedEntityAttribute in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTrackedEntityAttributeUpdatableFieldsEquals(
            returnedTrackedEntityAttribute,
            getPersistedTrackedEntityAttribute(returnedTrackedEntityAttribute)
        );

        insertedTrackedEntityAttribute = returnedTrackedEntityAttribute;
    }

    @Test
    @Transactional
    void createTrackedEntityAttributeWithExistingId() throws Exception {
        // Create the TrackedEntityAttribute with an existing ID
        trackedEntityAttribute.setId("existing_id");

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrackedEntityAttributeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(trackedEntityAttribute)))
            .andExpect(status().isBadRequest());

        // Validate the TrackedEntityAttribute in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTrackIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        trackedEntityAttribute.setTrack(null);

        // Create the TrackedEntityAttribute, which fails.

        restTrackedEntityAttributeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(trackedEntityAttribute)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTrackedEntityAttributes() throws Exception {
        // Initialize the database
        insertedTrackedEntityAttribute = trackedEntityAttributeRepository.saveAndFlush(trackedEntityAttribute);

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
        insertedTrackedEntityAttribute = trackedEntityAttributeRepository.saveAndFlush(trackedEntityAttribute);

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
        insertedTrackedEntityAttribute = trackedEntityAttributeRepository.saveAndFlush(trackedEntityAttribute);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the trackedEntityAttribute
        TrackedEntityAttribute updatedTrackedEntityAttribute = trackedEntityAttributeRepository
            .findById(trackedEntityAttribute.getId())
            .orElseThrow();
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
                    .content(om.writeValueAsBytes(updatedTrackedEntityAttribute))
            )
            .andExpect(status().isOk());

        // Validate the TrackedEntityAttribute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTrackedEntityAttributeToMatchAllProperties(updatedTrackedEntityAttribute);
    }

    @Test
    @Transactional
    void putNonExistingTrackedEntityAttribute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trackedEntityAttribute.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrackedEntityAttributeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trackedEntityAttribute.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(trackedEntityAttribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrackedEntityAttribute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTrackedEntityAttribute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trackedEntityAttribute.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrackedEntityAttributeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(trackedEntityAttribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrackedEntityAttribute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTrackedEntityAttribute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trackedEntityAttribute.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrackedEntityAttributeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(trackedEntityAttribute)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TrackedEntityAttribute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTrackedEntityAttributeWithPatch() throws Exception {
        // Initialize the database
        insertedTrackedEntityAttribute = trackedEntityAttributeRepository.saveAndFlush(trackedEntityAttribute);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the trackedEntityAttribute using partial update
        TrackedEntityAttribute partialUpdatedTrackedEntityAttribute = new TrackedEntityAttribute();
        partialUpdatedTrackedEntityAttribute.setId(trackedEntityAttribute.getId());

        partialUpdatedTrackedEntityAttribute
            .created(UPDATED_CREATED)
            .shortName(UPDATED_SHORT_NAME)
            .valueType(UPDATED_VALUE_TYPE)
            .confidential(UPDATED_CONFIDENTIAL)
            .displayFormName(UPDATED_DISPLAY_FORM_NAME)
            .uniquee(UPDATED_UNIQUEE)
            .dimensionItemType(UPDATED_DIMENSION_ITEM_TYPE)
            .displayInListNoProgram(UPDATED_DISPLAY_IN_LIST_NO_PROGRAM)
            .skipSynchronization(UPDATED_SKIP_SYNCHRONIZATION)
            .periodOffset(UPDATED_PERIOD_OFFSET)
            .dimensionItem(UPDATED_DIMENSION_ITEM)
            .optionSetValue(UPDATED_OPTION_SET_VALUE);

        restTrackedEntityAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrackedEntityAttribute.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTrackedEntityAttribute))
            )
            .andExpect(status().isOk());

        // Validate the TrackedEntityAttribute in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTrackedEntityAttributeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTrackedEntityAttribute, trackedEntityAttribute),
            getPersistedTrackedEntityAttribute(trackedEntityAttribute)
        );
    }

    @Test
    @Transactional
    void fullUpdateTrackedEntityAttributeWithPatch() throws Exception {
        // Initialize the database
        insertedTrackedEntityAttribute = trackedEntityAttributeRepository.saveAndFlush(trackedEntityAttribute);

        long databaseSizeBeforeUpdate = getRepositoryCount();

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
                    .content(om.writeValueAsBytes(partialUpdatedTrackedEntityAttribute))
            )
            .andExpect(status().isOk());

        // Validate the TrackedEntityAttribute in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTrackedEntityAttributeUpdatableFieldsEquals(
            partialUpdatedTrackedEntityAttribute,
            getPersistedTrackedEntityAttribute(partialUpdatedTrackedEntityAttribute)
        );
    }

    @Test
    @Transactional
    void patchNonExistingTrackedEntityAttribute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trackedEntityAttribute.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrackedEntityAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, trackedEntityAttribute.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(trackedEntityAttribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrackedEntityAttribute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTrackedEntityAttribute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trackedEntityAttribute.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrackedEntityAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(trackedEntityAttribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrackedEntityAttribute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTrackedEntityAttribute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trackedEntityAttribute.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrackedEntityAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(trackedEntityAttribute))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TrackedEntityAttribute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTrackedEntityAttribute() throws Exception {
        // Initialize the database
        insertedTrackedEntityAttribute = trackedEntityAttributeRepository.saveAndFlush(trackedEntityAttribute);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the trackedEntityAttribute
        restTrackedEntityAttributeMockMvc
            .perform(delete(ENTITY_API_URL_ID, trackedEntityAttribute.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return trackedEntityAttributeRepository.count();
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

    protected TrackedEntityAttribute getPersistedTrackedEntityAttribute(TrackedEntityAttribute trackedEntityAttribute) {
        return trackedEntityAttributeRepository.findById(trackedEntityAttribute.getId()).orElseThrow();
    }

    protected void assertPersistedTrackedEntityAttributeToMatchAllProperties(TrackedEntityAttribute expectedTrackedEntityAttribute) {
        assertTrackedEntityAttributeAllPropertiesEquals(
            expectedTrackedEntityAttribute,
            getPersistedTrackedEntityAttribute(expectedTrackedEntityAttribute)
        );
    }

    protected void assertPersistedTrackedEntityAttributeToMatchUpdatableProperties(TrackedEntityAttribute expectedTrackedEntityAttribute) {
        assertTrackedEntityAttributeAllUpdatablePropertiesEquals(
            expectedTrackedEntityAttribute,
            getPersistedTrackedEntityAttribute(expectedTrackedEntityAttribute)
        );
    }
}
