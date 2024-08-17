package com.didate.web.rest;

import static com.didate.domain.DatasetAsserts.*;
import static com.didate.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.didate.IntegrationTest;
import com.didate.domain.DHISUser;
import com.didate.domain.Dataset;
import com.didate.repository.DatasetRepository;
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
 * Integration tests for the {@link DatasetResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DatasetResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DIMENSION_ITEM_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DIMENSION_ITEM_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_PERIOD_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PERIOD_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY_COMBO = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_COMBO = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE = "BBBBBBBBBB";

    private static final Double DEFAULT_VERSION = 1D;
    private static final Double UPDATED_VERSION = 2D;

    private static final Double DEFAULT_EXPIRY_DAYS = 1D;
    private static final Double UPDATED_EXPIRY_DAYS = 2D;

    private static final Double DEFAULT_TIMELY_DAYS = 1D;
    private static final Double UPDATED_TIMELY_DAYS = 2D;

    private static final String DEFAULT_NOTIFY_COMPLETING_USER = "AAAAAAAAAA";
    private static final String UPDATED_NOTIFY_COMPLETING_USER = "BBBBBBBBBB";

    private static final Double DEFAULT_OPEN_FUTURE_PERIODS = 1D;
    private static final Double UPDATED_OPEN_FUTURE_PERIODS = 2D;

    private static final Double DEFAULT_OPEN_PERIODS_AFTER_CO_END_DATE = 1D;
    private static final Double UPDATED_OPEN_PERIODS_AFTER_CO_END_DATE = 2D;

    private static final Boolean DEFAULT_FIELD_COMBINATION_REQUIRED = false;
    private static final Boolean UPDATED_FIELD_COMBINATION_REQUIRED = true;

    private static final Boolean DEFAULT_VALID_COMPLETE_ONLY = false;
    private static final Boolean UPDATED_VALID_COMPLETE_ONLY = true;

    private static final Boolean DEFAULT_NO_VALUE_REQUIRES_COMMENT = false;
    private static final Boolean UPDATED_NO_VALUE_REQUIRES_COMMENT = true;

    private static final Boolean DEFAULT_SKIP_OFFLINE = false;
    private static final Boolean UPDATED_SKIP_OFFLINE = true;

    private static final Boolean DEFAULT_DATA_ELEMENT_DECORATION = false;
    private static final Boolean UPDATED_DATA_ELEMENT_DECORATION = true;

    private static final Boolean DEFAULT_RENDER_AS_TABS = false;
    private static final Boolean UPDATED_RENDER_AS_TABS = true;

    private static final Boolean DEFAULT_RENDER_HORIZONTALLY = false;
    private static final Boolean UPDATED_RENDER_HORIZONTALLY = true;

    private static final Boolean DEFAULT_COMPULSORY_FIELDS_COMPLETE_ONLY = false;
    private static final Boolean UPDATED_COMPULSORY_FIELDS_COMPLETE_ONLY = true;

    private static final String DEFAULT_FORM_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_FORM_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DIMENSION_ITEM = "AAAAAAAAAA";
    private static final String UPDATED_DIMENSION_ITEM = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_FORM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_FORM_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/datasets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DatasetRepository datasetRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDatasetMockMvc;

    private Dataset dataset;

    private Dataset insertedDataset;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dataset createEntity(EntityManager em) {
        Dataset dataset = new Dataset()
            .name(DEFAULT_NAME)
            .created(DEFAULT_CREATED)
            .lastUpdated(DEFAULT_LAST_UPDATED)
            .shortName(DEFAULT_SHORT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .dimensionItemType(DEFAULT_DIMENSION_ITEM_TYPE)
            .periodType(DEFAULT_PERIOD_TYPE)
            .categoryCombo(DEFAULT_CATEGORY_COMBO)
            .mobile(DEFAULT_MOBILE)
            .version(DEFAULT_VERSION)
            .expiryDays(DEFAULT_EXPIRY_DAYS)
            .timelyDays(DEFAULT_TIMELY_DAYS)
            .notifyCompletingUser(DEFAULT_NOTIFY_COMPLETING_USER)
            .openFuturePeriods(DEFAULT_OPEN_FUTURE_PERIODS)
            .openPeriodsAfterCoEndDate(DEFAULT_OPEN_PERIODS_AFTER_CO_END_DATE)
            .fieldCombinationRequired(DEFAULT_FIELD_COMBINATION_REQUIRED)
            .validCompleteOnly(DEFAULT_VALID_COMPLETE_ONLY)
            .noValueRequiresComment(DEFAULT_NO_VALUE_REQUIRES_COMMENT)
            .skipOffline(DEFAULT_SKIP_OFFLINE)
            .dataElementDecoration(DEFAULT_DATA_ELEMENT_DECORATION)
            .renderAsTabs(DEFAULT_RENDER_AS_TABS)
            .renderHorizontally(DEFAULT_RENDER_HORIZONTALLY)
            .compulsoryFieldsCompleteOnly(DEFAULT_COMPULSORY_FIELDS_COMPLETE_ONLY)
            .formType(DEFAULT_FORM_TYPE)
            .displayName(DEFAULT_DISPLAY_NAME)
            .dimensionItem(DEFAULT_DIMENSION_ITEM)
            .displayShortName(DEFAULT_DISPLAY_SHORT_NAME)
            .displayDescription(DEFAULT_DISPLAY_DESCRIPTION)
            .displayFormName(DEFAULT_DISPLAY_FORM_NAME);
        // Add required entity
        DHISUser dHISUser;
        if (TestUtil.findAll(em, DHISUser.class).isEmpty()) {
            dHISUser = DHISUserResourceIT.createEntity(em);
            em.persist(dHISUser);
            em.flush();
        } else {
            dHISUser = TestUtil.findAll(em, DHISUser.class).get(0);
        }
        dataset.setCreatedBy(dHISUser);
        // Add required entity
        dataset.setLastUpdatedBy(dHISUser);
        return dataset;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dataset createUpdatedEntity(EntityManager em) {
        Dataset dataset = new Dataset()
            .name(UPDATED_NAME)
            .created(UPDATED_CREATED)
            .lastUpdated(UPDATED_LAST_UPDATED)
            .shortName(UPDATED_SHORT_NAME)
            .description(UPDATED_DESCRIPTION)
            .dimensionItemType(UPDATED_DIMENSION_ITEM_TYPE)
            .periodType(UPDATED_PERIOD_TYPE)
            .categoryCombo(UPDATED_CATEGORY_COMBO)
            .mobile(UPDATED_MOBILE)
            .version(UPDATED_VERSION)
            .expiryDays(UPDATED_EXPIRY_DAYS)
            .timelyDays(UPDATED_TIMELY_DAYS)
            .notifyCompletingUser(UPDATED_NOTIFY_COMPLETING_USER)
            .openFuturePeriods(UPDATED_OPEN_FUTURE_PERIODS)
            .openPeriodsAfterCoEndDate(UPDATED_OPEN_PERIODS_AFTER_CO_END_DATE)
            .fieldCombinationRequired(UPDATED_FIELD_COMBINATION_REQUIRED)
            .validCompleteOnly(UPDATED_VALID_COMPLETE_ONLY)
            .noValueRequiresComment(UPDATED_NO_VALUE_REQUIRES_COMMENT)
            .skipOffline(UPDATED_SKIP_OFFLINE)
            .dataElementDecoration(UPDATED_DATA_ELEMENT_DECORATION)
            .renderAsTabs(UPDATED_RENDER_AS_TABS)
            .renderHorizontally(UPDATED_RENDER_HORIZONTALLY)
            .compulsoryFieldsCompleteOnly(UPDATED_COMPULSORY_FIELDS_COMPLETE_ONLY)
            .formType(UPDATED_FORM_TYPE)
            .displayName(UPDATED_DISPLAY_NAME)
            .dimensionItem(UPDATED_DIMENSION_ITEM)
            .displayShortName(UPDATED_DISPLAY_SHORT_NAME)
            .displayDescription(UPDATED_DISPLAY_DESCRIPTION)
            .displayFormName(UPDATED_DISPLAY_FORM_NAME);
        // Add required entity
        DHISUser dHISUser;
        if (TestUtil.findAll(em, DHISUser.class).isEmpty()) {
            dHISUser = DHISUserResourceIT.createUpdatedEntity(em);
            em.persist(dHISUser);
            em.flush();
        } else {
            dHISUser = TestUtil.findAll(em, DHISUser.class).get(0);
        }
        dataset.setCreatedBy(dHISUser);
        // Add required entity
        dataset.setLastUpdatedBy(dHISUser);
        return dataset;
    }

    @BeforeEach
    public void initTest() {
        dataset = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedDataset != null) {
            datasetRepository.delete(insertedDataset);
            insertedDataset = null;
        }
    }

    @Test
    @Transactional
    void createDataset() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Dataset
        var returnedDataset = om.readValue(
            restDatasetMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dataset)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Dataset.class
        );

        // Validate the Dataset in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDatasetUpdatableFieldsEquals(returnedDataset, getPersistedDataset(returnedDataset));

        insertedDataset = returnedDataset;
    }

    @Test
    @Transactional
    void createDatasetWithExistingId() throws Exception {
        // Create the Dataset with an existing ID
        dataset.setId("existing_id");

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDatasetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dataset)))
            .andExpect(status().isBadRequest());

        // Validate the Dataset in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        dataset.setCreated(null);

        // Create the Dataset, which fails.

        restDatasetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dataset)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastUpdatedIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        dataset.setLastUpdated(null);

        // Create the Dataset, which fails.

        restDatasetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dataset)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDatasets() throws Exception {
        // Initialize the database
        insertedDataset = datasetRepository.saveAndFlush(dataset);

        // Get all the datasetList
        restDatasetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataset.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].dimensionItemType").value(hasItem(DEFAULT_DIMENSION_ITEM_TYPE)))
            .andExpect(jsonPath("$.[*].periodType").value(hasItem(DEFAULT_PERIOD_TYPE)))
            .andExpect(jsonPath("$.[*].categoryCombo").value(hasItem(DEFAULT_CATEGORY_COMBO)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.doubleValue())))
            .andExpect(jsonPath("$.[*].expiryDays").value(hasItem(DEFAULT_EXPIRY_DAYS.doubleValue())))
            .andExpect(jsonPath("$.[*].timelyDays").value(hasItem(DEFAULT_TIMELY_DAYS.doubleValue())))
            .andExpect(jsonPath("$.[*].notifyCompletingUser").value(hasItem(DEFAULT_NOTIFY_COMPLETING_USER)))
            .andExpect(jsonPath("$.[*].openFuturePeriods").value(hasItem(DEFAULT_OPEN_FUTURE_PERIODS.doubleValue())))
            .andExpect(jsonPath("$.[*].openPeriodsAfterCoEndDate").value(hasItem(DEFAULT_OPEN_PERIODS_AFTER_CO_END_DATE.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldCombinationRequired").value(hasItem(DEFAULT_FIELD_COMBINATION_REQUIRED.booleanValue())))
            .andExpect(jsonPath("$.[*].validCompleteOnly").value(hasItem(DEFAULT_VALID_COMPLETE_ONLY.booleanValue())))
            .andExpect(jsonPath("$.[*].noValueRequiresComment").value(hasItem(DEFAULT_NO_VALUE_REQUIRES_COMMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].skipOffline").value(hasItem(DEFAULT_SKIP_OFFLINE.booleanValue())))
            .andExpect(jsonPath("$.[*].dataElementDecoration").value(hasItem(DEFAULT_DATA_ELEMENT_DECORATION.booleanValue())))
            .andExpect(jsonPath("$.[*].renderAsTabs").value(hasItem(DEFAULT_RENDER_AS_TABS.booleanValue())))
            .andExpect(jsonPath("$.[*].renderHorizontally").value(hasItem(DEFAULT_RENDER_HORIZONTALLY.booleanValue())))
            .andExpect(
                jsonPath("$.[*].compulsoryFieldsCompleteOnly").value(hasItem(DEFAULT_COMPULSORY_FIELDS_COMPLETE_ONLY.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].formType").value(hasItem(DEFAULT_FORM_TYPE)))
            .andExpect(jsonPath("$.[*].displayName").value(hasItem(DEFAULT_DISPLAY_NAME)))
            .andExpect(jsonPath("$.[*].dimensionItem").value(hasItem(DEFAULT_DIMENSION_ITEM)))
            .andExpect(jsonPath("$.[*].displayShortName").value(hasItem(DEFAULT_DISPLAY_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].displayDescription").value(hasItem(DEFAULT_DISPLAY_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].displayFormName").value(hasItem(DEFAULT_DISPLAY_FORM_NAME)));
    }

    @Test
    @Transactional
    void getDataset() throws Exception {
        // Initialize the database
        insertedDataset = datasetRepository.saveAndFlush(dataset);

        // Get the dataset
        restDatasetMockMvc
            .perform(get(ENTITY_API_URL_ID, dataset.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dataset.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.lastUpdated").value(DEFAULT_LAST_UPDATED.toString()))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.dimensionItemType").value(DEFAULT_DIMENSION_ITEM_TYPE))
            .andExpect(jsonPath("$.periodType").value(DEFAULT_PERIOD_TYPE))
            .andExpect(jsonPath("$.categoryCombo").value(DEFAULT_CATEGORY_COMBO))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.doubleValue()))
            .andExpect(jsonPath("$.expiryDays").value(DEFAULT_EXPIRY_DAYS.doubleValue()))
            .andExpect(jsonPath("$.timelyDays").value(DEFAULT_TIMELY_DAYS.doubleValue()))
            .andExpect(jsonPath("$.notifyCompletingUser").value(DEFAULT_NOTIFY_COMPLETING_USER))
            .andExpect(jsonPath("$.openFuturePeriods").value(DEFAULT_OPEN_FUTURE_PERIODS.doubleValue()))
            .andExpect(jsonPath("$.openPeriodsAfterCoEndDate").value(DEFAULT_OPEN_PERIODS_AFTER_CO_END_DATE.doubleValue()))
            .andExpect(jsonPath("$.fieldCombinationRequired").value(DEFAULT_FIELD_COMBINATION_REQUIRED.booleanValue()))
            .andExpect(jsonPath("$.validCompleteOnly").value(DEFAULT_VALID_COMPLETE_ONLY.booleanValue()))
            .andExpect(jsonPath("$.noValueRequiresComment").value(DEFAULT_NO_VALUE_REQUIRES_COMMENT.booleanValue()))
            .andExpect(jsonPath("$.skipOffline").value(DEFAULT_SKIP_OFFLINE.booleanValue()))
            .andExpect(jsonPath("$.dataElementDecoration").value(DEFAULT_DATA_ELEMENT_DECORATION.booleanValue()))
            .andExpect(jsonPath("$.renderAsTabs").value(DEFAULT_RENDER_AS_TABS.booleanValue()))
            .andExpect(jsonPath("$.renderHorizontally").value(DEFAULT_RENDER_HORIZONTALLY.booleanValue()))
            .andExpect(jsonPath("$.compulsoryFieldsCompleteOnly").value(DEFAULT_COMPULSORY_FIELDS_COMPLETE_ONLY.booleanValue()))
            .andExpect(jsonPath("$.formType").value(DEFAULT_FORM_TYPE))
            .andExpect(jsonPath("$.displayName").value(DEFAULT_DISPLAY_NAME))
            .andExpect(jsonPath("$.dimensionItem").value(DEFAULT_DIMENSION_ITEM))
            .andExpect(jsonPath("$.displayShortName").value(DEFAULT_DISPLAY_SHORT_NAME))
            .andExpect(jsonPath("$.displayDescription").value(DEFAULT_DISPLAY_DESCRIPTION))
            .andExpect(jsonPath("$.displayFormName").value(DEFAULT_DISPLAY_FORM_NAME));
    }

    @Test
    @Transactional
    void getNonExistingDataset() throws Exception {
        // Get the dataset
        restDatasetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDataset() throws Exception {
        // Initialize the database
        insertedDataset = datasetRepository.saveAndFlush(dataset);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the dataset
        Dataset updatedDataset = datasetRepository.findById(dataset.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDataset are not directly saved in db
        em.detach(updatedDataset);
        updatedDataset
            .name(UPDATED_NAME)
            .created(UPDATED_CREATED)
            .lastUpdated(UPDATED_LAST_UPDATED)
            .shortName(UPDATED_SHORT_NAME)
            .description(UPDATED_DESCRIPTION)
            .dimensionItemType(UPDATED_DIMENSION_ITEM_TYPE)
            .periodType(UPDATED_PERIOD_TYPE)
            .categoryCombo(UPDATED_CATEGORY_COMBO)
            .mobile(UPDATED_MOBILE)
            .version(UPDATED_VERSION)
            .expiryDays(UPDATED_EXPIRY_DAYS)
            .timelyDays(UPDATED_TIMELY_DAYS)
            .notifyCompletingUser(UPDATED_NOTIFY_COMPLETING_USER)
            .openFuturePeriods(UPDATED_OPEN_FUTURE_PERIODS)
            .openPeriodsAfterCoEndDate(UPDATED_OPEN_PERIODS_AFTER_CO_END_DATE)
            .fieldCombinationRequired(UPDATED_FIELD_COMBINATION_REQUIRED)
            .validCompleteOnly(UPDATED_VALID_COMPLETE_ONLY)
            .noValueRequiresComment(UPDATED_NO_VALUE_REQUIRES_COMMENT)
            .skipOffline(UPDATED_SKIP_OFFLINE)
            .dataElementDecoration(UPDATED_DATA_ELEMENT_DECORATION)
            .renderAsTabs(UPDATED_RENDER_AS_TABS)
            .renderHorizontally(UPDATED_RENDER_HORIZONTALLY)
            .compulsoryFieldsCompleteOnly(UPDATED_COMPULSORY_FIELDS_COMPLETE_ONLY)
            .formType(UPDATED_FORM_TYPE)
            .displayName(UPDATED_DISPLAY_NAME)
            .dimensionItem(UPDATED_DIMENSION_ITEM)
            .displayShortName(UPDATED_DISPLAY_SHORT_NAME)
            .displayDescription(UPDATED_DISPLAY_DESCRIPTION)
            .displayFormName(UPDATED_DISPLAY_FORM_NAME);

        restDatasetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDataset.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDataset))
            )
            .andExpect(status().isOk());

        // Validate the Dataset in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDatasetToMatchAllProperties(updatedDataset);
    }

    @Test
    @Transactional
    void putNonExistingDataset() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dataset.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDatasetMockMvc
            .perform(put(ENTITY_API_URL_ID, dataset.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dataset)))
            .andExpect(status().isBadRequest());

        // Validate the Dataset in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDataset() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dataset.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDatasetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(dataset))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dataset in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDataset() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dataset.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDatasetMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dataset)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dataset in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDatasetWithPatch() throws Exception {
        // Initialize the database
        insertedDataset = datasetRepository.saveAndFlush(dataset);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the dataset using partial update
        Dataset partialUpdatedDataset = new Dataset();
        partialUpdatedDataset.setId(dataset.getId());

        partialUpdatedDataset
            .lastUpdated(UPDATED_LAST_UPDATED)
            .description(UPDATED_DESCRIPTION)
            .periodType(UPDATED_PERIOD_TYPE)
            .categoryCombo(UPDATED_CATEGORY_COMBO)
            .mobile(UPDATED_MOBILE)
            .timelyDays(UPDATED_TIMELY_DAYS)
            .notifyCompletingUser(UPDATED_NOTIFY_COMPLETING_USER)
            .openFuturePeriods(UPDATED_OPEN_FUTURE_PERIODS)
            .openPeriodsAfterCoEndDate(UPDATED_OPEN_PERIODS_AFTER_CO_END_DATE)
            .validCompleteOnly(UPDATED_VALID_COMPLETE_ONLY)
            .renderAsTabs(UPDATED_RENDER_AS_TABS)
            .displayDescription(UPDATED_DISPLAY_DESCRIPTION);

        restDatasetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDataset.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDataset))
            )
            .andExpect(status().isOk());

        // Validate the Dataset in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDatasetUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedDataset, dataset), getPersistedDataset(dataset));
    }

    @Test
    @Transactional
    void fullUpdateDatasetWithPatch() throws Exception {
        // Initialize the database
        insertedDataset = datasetRepository.saveAndFlush(dataset);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the dataset using partial update
        Dataset partialUpdatedDataset = new Dataset();
        partialUpdatedDataset.setId(dataset.getId());

        partialUpdatedDataset
            .name(UPDATED_NAME)
            .created(UPDATED_CREATED)
            .lastUpdated(UPDATED_LAST_UPDATED)
            .shortName(UPDATED_SHORT_NAME)
            .description(UPDATED_DESCRIPTION)
            .dimensionItemType(UPDATED_DIMENSION_ITEM_TYPE)
            .periodType(UPDATED_PERIOD_TYPE)
            .categoryCombo(UPDATED_CATEGORY_COMBO)
            .mobile(UPDATED_MOBILE)
            .version(UPDATED_VERSION)
            .expiryDays(UPDATED_EXPIRY_DAYS)
            .timelyDays(UPDATED_TIMELY_DAYS)
            .notifyCompletingUser(UPDATED_NOTIFY_COMPLETING_USER)
            .openFuturePeriods(UPDATED_OPEN_FUTURE_PERIODS)
            .openPeriodsAfterCoEndDate(UPDATED_OPEN_PERIODS_AFTER_CO_END_DATE)
            .fieldCombinationRequired(UPDATED_FIELD_COMBINATION_REQUIRED)
            .validCompleteOnly(UPDATED_VALID_COMPLETE_ONLY)
            .noValueRequiresComment(UPDATED_NO_VALUE_REQUIRES_COMMENT)
            .skipOffline(UPDATED_SKIP_OFFLINE)
            .dataElementDecoration(UPDATED_DATA_ELEMENT_DECORATION)
            .renderAsTabs(UPDATED_RENDER_AS_TABS)
            .renderHorizontally(UPDATED_RENDER_HORIZONTALLY)
            .compulsoryFieldsCompleteOnly(UPDATED_COMPULSORY_FIELDS_COMPLETE_ONLY)
            .formType(UPDATED_FORM_TYPE)
            .displayName(UPDATED_DISPLAY_NAME)
            .dimensionItem(UPDATED_DIMENSION_ITEM)
            .displayShortName(UPDATED_DISPLAY_SHORT_NAME)
            .displayDescription(UPDATED_DISPLAY_DESCRIPTION)
            .displayFormName(UPDATED_DISPLAY_FORM_NAME);

        restDatasetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDataset.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDataset))
            )
            .andExpect(status().isOk());

        // Validate the Dataset in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDatasetUpdatableFieldsEquals(partialUpdatedDataset, getPersistedDataset(partialUpdatedDataset));
    }

    @Test
    @Transactional
    void patchNonExistingDataset() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dataset.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDatasetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dataset.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(dataset))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dataset in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDataset() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dataset.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDatasetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(dataset))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dataset in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDataset() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dataset.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDatasetMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(dataset)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dataset in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDataset() throws Exception {
        // Initialize the database
        insertedDataset = datasetRepository.saveAndFlush(dataset);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the dataset
        restDatasetMockMvc
            .perform(delete(ENTITY_API_URL_ID, dataset.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return datasetRepository.count();
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

    protected Dataset getPersistedDataset(Dataset dataset) {
        return datasetRepository.findById(dataset.getId()).orElseThrow();
    }

    protected void assertPersistedDatasetToMatchAllProperties(Dataset expectedDataset) {
        assertDatasetAllPropertiesEquals(expectedDataset, getPersistedDataset(expectedDataset));
    }

    protected void assertPersistedDatasetToMatchUpdatableProperties(Dataset expectedDataset) {
        assertDatasetAllUpdatablePropertiesEquals(expectedDataset, getPersistedDataset(expectedDataset));
    }
}
