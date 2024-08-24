package com.didate.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.didate.IntegrationTest;
import com.didate.domain.DHISUser;
import com.didate.domain.DataSet;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.repository.DatasetRepository;
import com.didate.service.DatasetService;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link DatasetResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
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

    private static final Integer DEFAULT_DATA_SET_ELEMENTS_COUNT = 1;
    private static final Integer UPDATED_DATA_SET_ELEMENTS_COUNT = 2;

    private static final Integer DEFAULT_INDICATORS_COUNT = 1;
    private static final Integer UPDATED_INDICATORS_COUNT = 2;

    private static final Integer DEFAULT_ORGANISATION_UNITS_COUNT = 1;
    private static final Integer UPDATED_ORGANISATION_UNITS_COUNT = 2;

    private static final String DEFAULT_DATA_SET_ELEMENTS_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_DATA_SET_ELEMENTS_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_INDICATORS_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_INDICATORS_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANISATION_UNITS_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_ORGANISATION_UNITS_CONTENT = "BBBBBBBBBB";

    private static final TypeTrack DEFAULT_TRACK = TypeTrack.NEW;
    private static final TypeTrack UPDATED_TRACK = TypeTrack.UPDATE;

    private static final String ENTITY_API_URL = "/api/datasets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private DatasetRepository datasetRepository;

    @Mock
    private DatasetRepository datasetRepositoryMock;

    @Mock
    private DatasetService datasetServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDatasetMockMvc;

    private DataSet dataset;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataSet createEntity(EntityManager em) {
        DataSet dataset = new DataSet()
            .name(DEFAULT_NAME)
            .created(DEFAULT_CREATED)
            .lastUpdated(DEFAULT_LAST_UPDATED)
            .shortName(DEFAULT_SHORT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .dimensionItemType(DEFAULT_DIMENSION_ITEM_TYPE)
            .periodType(DEFAULT_PERIOD_TYPE)
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
            .displayFormName(DEFAULT_DISPLAY_FORM_NAME)
            .dataSetElementsCount(DEFAULT_DATA_SET_ELEMENTS_COUNT)
            .indicatorsCount(DEFAULT_INDICATORS_COUNT)
            .organisationUnitsCount(DEFAULT_ORGANISATION_UNITS_COUNT)
            .dataSetElementsContent(DEFAULT_DATA_SET_ELEMENTS_CONTENT)
            .indicatorsContent(DEFAULT_INDICATORS_CONTENT)
            .organisationUnitsContent(DEFAULT_ORGANISATION_UNITS_CONTENT)
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
    public static DataSet createUpdatedEntity(EntityManager em) {
        DataSet dataset = new DataSet()
            .name(UPDATED_NAME)
            .created(UPDATED_CREATED)
            .lastUpdated(UPDATED_LAST_UPDATED)
            .shortName(UPDATED_SHORT_NAME)
            .description(UPDATED_DESCRIPTION)
            .dimensionItemType(UPDATED_DIMENSION_ITEM_TYPE)
            .periodType(UPDATED_PERIOD_TYPE)
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
            .displayFormName(UPDATED_DISPLAY_FORM_NAME)
            .dataSetElementsCount(UPDATED_DATA_SET_ELEMENTS_COUNT)
            .indicatorsCount(UPDATED_INDICATORS_COUNT)
            .organisationUnitsCount(UPDATED_ORGANISATION_UNITS_COUNT)
            .dataSetElementsContent(UPDATED_DATA_SET_ELEMENTS_CONTENT)
            .indicatorsContent(UPDATED_INDICATORS_CONTENT)
            .organisationUnitsContent(UPDATED_ORGANISATION_UNITS_CONTENT)
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
        dataset.setCreatedBy(dHISUser);
        // Add required entity
        dataset.setLastUpdatedBy(dHISUser);
        return dataset;
    }

    @BeforeEach
    public void initTest() {
        dataset = createEntity(em);
    }

    @Test
    @Transactional
    void createDataset() throws Exception {
        int databaseSizeBeforeCreate = datasetRepository.findAll().size();
        // Create the DataSet
        restDatasetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataset)))
            .andExpect(status().isCreated());

        // Validate the DataSet in the database
        List<DataSet> datasetList = datasetRepository.findAll();
        assertThat(datasetList).hasSize(databaseSizeBeforeCreate + 1);
        DataSet testDataset = datasetList.get(datasetList.size() - 1);
        assertThat(testDataset.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDataset.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testDataset.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
        assertThat(testDataset.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
        assertThat(testDataset.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDataset.getDimensionItemType()).isEqualTo(DEFAULT_DIMENSION_ITEM_TYPE);
        assertThat(testDataset.getPeriodType()).isEqualTo(DEFAULT_PERIOD_TYPE);
        assertThat(testDataset.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testDataset.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testDataset.getExpiryDays()).isEqualTo(DEFAULT_EXPIRY_DAYS);
        assertThat(testDataset.getTimelyDays()).isEqualTo(DEFAULT_TIMELY_DAYS);
        assertThat(testDataset.getNotifyCompletingUser()).isEqualTo(DEFAULT_NOTIFY_COMPLETING_USER);
        assertThat(testDataset.getOpenFuturePeriods()).isEqualTo(DEFAULT_OPEN_FUTURE_PERIODS);
        assertThat(testDataset.getOpenPeriodsAfterCoEndDate()).isEqualTo(DEFAULT_OPEN_PERIODS_AFTER_CO_END_DATE);
        assertThat(testDataset.getFieldCombinationRequired()).isEqualTo(DEFAULT_FIELD_COMBINATION_REQUIRED);
        assertThat(testDataset.getValidCompleteOnly()).isEqualTo(DEFAULT_VALID_COMPLETE_ONLY);
        assertThat(testDataset.getNoValueRequiresComment()).isEqualTo(DEFAULT_NO_VALUE_REQUIRES_COMMENT);
        assertThat(testDataset.getSkipOffline()).isEqualTo(DEFAULT_SKIP_OFFLINE);
        assertThat(testDataset.getDataElementDecoration()).isEqualTo(DEFAULT_DATA_ELEMENT_DECORATION);
        assertThat(testDataset.getRenderAsTabs()).isEqualTo(DEFAULT_RENDER_AS_TABS);
        assertThat(testDataset.getRenderHorizontally()).isEqualTo(DEFAULT_RENDER_HORIZONTALLY);
        assertThat(testDataset.getCompulsoryFieldsCompleteOnly()).isEqualTo(DEFAULT_COMPULSORY_FIELDS_COMPLETE_ONLY);
        assertThat(testDataset.getFormType()).isEqualTo(DEFAULT_FORM_TYPE);
        assertThat(testDataset.getDisplayName()).isEqualTo(DEFAULT_DISPLAY_NAME);
        assertThat(testDataset.getDimensionItem()).isEqualTo(DEFAULT_DIMENSION_ITEM);
        assertThat(testDataset.getDisplayShortName()).isEqualTo(DEFAULT_DISPLAY_SHORT_NAME);
        assertThat(testDataset.getDisplayDescription()).isEqualTo(DEFAULT_DISPLAY_DESCRIPTION);
        assertThat(testDataset.getDisplayFormName()).isEqualTo(DEFAULT_DISPLAY_FORM_NAME);
        assertThat(testDataset.getDataSetElementsCount()).isEqualTo(DEFAULT_DATA_SET_ELEMENTS_COUNT);
        assertThat(testDataset.getIndicatorsCount()).isEqualTo(DEFAULT_INDICATORS_COUNT);
        assertThat(testDataset.getOrganisationUnitsCount()).isEqualTo(DEFAULT_ORGANISATION_UNITS_COUNT);
        assertThat(testDataset.getDataSetElementsContent()).isEqualTo(DEFAULT_DATA_SET_ELEMENTS_CONTENT);
        assertThat(testDataset.getIndicatorsContent()).isEqualTo(DEFAULT_INDICATORS_CONTENT);
        assertThat(testDataset.getOrganisationUnitsContent()).isEqualTo(DEFAULT_ORGANISATION_UNITS_CONTENT);
        assertThat(testDataset.getTrack()).isEqualTo(DEFAULT_TRACK);
    }

    @Test
    @Transactional
    void createDatasetWithExistingId() throws Exception {
        // Create the DataSet with an existing ID
        dataset.setId("existing_id");

        int databaseSizeBeforeCreate = datasetRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDatasetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataset)))
            .andExpect(status().isBadRequest());

        // Validate the DataSet in the database
        List<DataSet> datasetList = datasetRepository.findAll();
        assertThat(datasetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = datasetRepository.findAll().size();
        // set the field null
        dataset.setCreated(null);

        // Create the DataSet, which fails.

        restDatasetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataset)))
            .andExpect(status().isBadRequest());

        List<DataSet> datasetList = datasetRepository.findAll();
        assertThat(datasetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastUpdatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = datasetRepository.findAll().size();
        // set the field null
        dataset.setLastUpdated(null);

        // Create the DataSet, which fails.

        restDatasetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataset)))
            .andExpect(status().isBadRequest());

        List<DataSet> datasetList = datasetRepository.findAll();
        assertThat(datasetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTrackIsRequired() throws Exception {
        int databaseSizeBeforeTest = datasetRepository.findAll().size();
        // set the field null
        dataset.setTrack(null);

        // Create the DataSet, which fails.

        restDatasetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataset)))
            .andExpect(status().isBadRequest());

        List<DataSet> datasetList = datasetRepository.findAll();
        assertThat(datasetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDatasets() throws Exception {
        // Initialize the database
        dataset.setId(UUID.randomUUID().toString());
        datasetRepository.saveAndFlush(dataset);

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
            .andExpect(jsonPath("$.[*].displayFormName").value(hasItem(DEFAULT_DISPLAY_FORM_NAME)))
            .andExpect(jsonPath("$.[*].dataSetElementsCount").value(hasItem(DEFAULT_DATA_SET_ELEMENTS_COUNT)))
            .andExpect(jsonPath("$.[*].indicatorsCount").value(hasItem(DEFAULT_INDICATORS_COUNT)))
            .andExpect(jsonPath("$.[*].organisationUnitsCount").value(hasItem(DEFAULT_ORGANISATION_UNITS_COUNT)))
            .andExpect(jsonPath("$.[*].dataSetElementsContent").value(hasItem(DEFAULT_DATA_SET_ELEMENTS_CONTENT)))
            .andExpect(jsonPath("$.[*].indicatorsContent").value(hasItem(DEFAULT_INDICATORS_CONTENT)))
            .andExpect(jsonPath("$.[*].organisationUnitsContent").value(hasItem(DEFAULT_ORGANISATION_UNITS_CONTENT)))
            .andExpect(jsonPath("$.[*].track").value(hasItem(DEFAULT_TRACK.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDatasetsWithEagerRelationshipsIsEnabled() throws Exception {
        when(datasetServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDatasetMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(datasetServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDatasetsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(datasetServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDatasetMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(datasetRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDataset() throws Exception {
        // Initialize the database
        dataset.setId(UUID.randomUUID().toString());
        datasetRepository.saveAndFlush(dataset);

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
            .andExpect(jsonPath("$.displayFormName").value(DEFAULT_DISPLAY_FORM_NAME))
            .andExpect(jsonPath("$.dataSetElementsCount").value(DEFAULT_DATA_SET_ELEMENTS_COUNT))
            .andExpect(jsonPath("$.indicatorsCount").value(DEFAULT_INDICATORS_COUNT))
            .andExpect(jsonPath("$.organisationUnitsCount").value(DEFAULT_ORGANISATION_UNITS_COUNT))
            .andExpect(jsonPath("$.dataSetElementsContent").value(DEFAULT_DATA_SET_ELEMENTS_CONTENT))
            .andExpect(jsonPath("$.indicatorsContent").value(DEFAULT_INDICATORS_CONTENT))
            .andExpect(jsonPath("$.organisationUnitsContent").value(DEFAULT_ORGANISATION_UNITS_CONTENT))
            .andExpect(jsonPath("$.track").value(DEFAULT_TRACK.toString()));
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
        dataset.setId(UUID.randomUUID().toString());
        datasetRepository.saveAndFlush(dataset);

        int databaseSizeBeforeUpdate = datasetRepository.findAll().size();

        // Update the dataset
        DataSet updatedDataset = datasetRepository.findById(dataset.getId()).get();
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
            .displayFormName(UPDATED_DISPLAY_FORM_NAME)
            .dataSetElementsCount(UPDATED_DATA_SET_ELEMENTS_COUNT)
            .indicatorsCount(UPDATED_INDICATORS_COUNT)
            .organisationUnitsCount(UPDATED_ORGANISATION_UNITS_COUNT)
            .dataSetElementsContent(UPDATED_DATA_SET_ELEMENTS_CONTENT)
            .indicatorsContent(UPDATED_INDICATORS_CONTENT)
            .organisationUnitsContent(UPDATED_ORGANISATION_UNITS_CONTENT)
            .track(UPDATED_TRACK);

        restDatasetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDataset.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDataset))
            )
            .andExpect(status().isOk());

        // Validate the DataSet in the database
        List<DataSet> datasetList = datasetRepository.findAll();
        assertThat(datasetList).hasSize(databaseSizeBeforeUpdate);
        DataSet testDataset = datasetList.get(datasetList.size() - 1);
        assertThat(testDataset.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataset.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testDataset.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
        assertThat(testDataset.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testDataset.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDataset.getDimensionItemType()).isEqualTo(UPDATED_DIMENSION_ITEM_TYPE);
        assertThat(testDataset.getPeriodType()).isEqualTo(UPDATED_PERIOD_TYPE);
        assertThat(testDataset.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testDataset.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testDataset.getExpiryDays()).isEqualTo(UPDATED_EXPIRY_DAYS);
        assertThat(testDataset.getTimelyDays()).isEqualTo(UPDATED_TIMELY_DAYS);
        assertThat(testDataset.getNotifyCompletingUser()).isEqualTo(UPDATED_NOTIFY_COMPLETING_USER);
        assertThat(testDataset.getOpenFuturePeriods()).isEqualTo(UPDATED_OPEN_FUTURE_PERIODS);
        assertThat(testDataset.getOpenPeriodsAfterCoEndDate()).isEqualTo(UPDATED_OPEN_PERIODS_AFTER_CO_END_DATE);
        assertThat(testDataset.getFieldCombinationRequired()).isEqualTo(UPDATED_FIELD_COMBINATION_REQUIRED);
        assertThat(testDataset.getValidCompleteOnly()).isEqualTo(UPDATED_VALID_COMPLETE_ONLY);
        assertThat(testDataset.getNoValueRequiresComment()).isEqualTo(UPDATED_NO_VALUE_REQUIRES_COMMENT);
        assertThat(testDataset.getSkipOffline()).isEqualTo(UPDATED_SKIP_OFFLINE);
        assertThat(testDataset.getDataElementDecoration()).isEqualTo(UPDATED_DATA_ELEMENT_DECORATION);
        assertThat(testDataset.getRenderAsTabs()).isEqualTo(UPDATED_RENDER_AS_TABS);
        assertThat(testDataset.getRenderHorizontally()).isEqualTo(UPDATED_RENDER_HORIZONTALLY);
        assertThat(testDataset.getCompulsoryFieldsCompleteOnly()).isEqualTo(UPDATED_COMPULSORY_FIELDS_COMPLETE_ONLY);
        assertThat(testDataset.getFormType()).isEqualTo(UPDATED_FORM_TYPE);
        assertThat(testDataset.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testDataset.getDimensionItem()).isEqualTo(UPDATED_DIMENSION_ITEM);
        assertThat(testDataset.getDisplayShortName()).isEqualTo(UPDATED_DISPLAY_SHORT_NAME);
        assertThat(testDataset.getDisplayDescription()).isEqualTo(UPDATED_DISPLAY_DESCRIPTION);
        assertThat(testDataset.getDisplayFormName()).isEqualTo(UPDATED_DISPLAY_FORM_NAME);
        assertThat(testDataset.getDataSetElementsCount()).isEqualTo(UPDATED_DATA_SET_ELEMENTS_COUNT);
        assertThat(testDataset.getIndicatorsCount()).isEqualTo(UPDATED_INDICATORS_COUNT);
        assertThat(testDataset.getOrganisationUnitsCount()).isEqualTo(UPDATED_ORGANISATION_UNITS_COUNT);
        assertThat(testDataset.getDataSetElementsContent()).isEqualTo(UPDATED_DATA_SET_ELEMENTS_CONTENT);
        assertThat(testDataset.getIndicatorsContent()).isEqualTo(UPDATED_INDICATORS_CONTENT);
        assertThat(testDataset.getOrganisationUnitsContent()).isEqualTo(UPDATED_ORGANISATION_UNITS_CONTENT);
        assertThat(testDataset.getTrack()).isEqualTo(UPDATED_TRACK);
    }

    @Test
    @Transactional
    void putNonExistingDataset() throws Exception {
        int databaseSizeBeforeUpdate = datasetRepository.findAll().size();
        dataset.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDatasetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dataset.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dataset))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataSet in the database
        List<DataSet> datasetList = datasetRepository.findAll();
        assertThat(datasetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDataset() throws Exception {
        int databaseSizeBeforeUpdate = datasetRepository.findAll().size();
        dataset.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDatasetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dataset))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataSet in the database
        List<DataSet> datasetList = datasetRepository.findAll();
        assertThat(datasetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDataset() throws Exception {
        int databaseSizeBeforeUpdate = datasetRepository.findAll().size();
        dataset.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDatasetMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataset)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DataSet in the database
        List<DataSet> datasetList = datasetRepository.findAll();
        assertThat(datasetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDatasetWithPatch() throws Exception {
        // Initialize the database
        dataset.setId(UUID.randomUUID().toString());
        datasetRepository.saveAndFlush(dataset);

        int databaseSizeBeforeUpdate = datasetRepository.findAll().size();

        // Update the dataset using partial update
        DataSet partialUpdatedDataset = new DataSet();
        partialUpdatedDataset.setId(dataset.getId());

        partialUpdatedDataset
            .name(UPDATED_NAME)
            .created(UPDATED_CREATED)
            .lastUpdated(UPDATED_LAST_UPDATED)
            .dimensionItemType(UPDATED_DIMENSION_ITEM_TYPE)
            .periodType(UPDATED_PERIOD_TYPE)
            .expiryDays(UPDATED_EXPIRY_DAYS)
            .notifyCompletingUser(UPDATED_NOTIFY_COMPLETING_USER)
            .validCompleteOnly(UPDATED_VALID_COMPLETE_ONLY)
            .formType(UPDATED_FORM_TYPE)
            .displayFormName(UPDATED_DISPLAY_FORM_NAME)
            .dataSetElementsCount(UPDATED_DATA_SET_ELEMENTS_COUNT)
            .indicatorsCount(UPDATED_INDICATORS_COUNT)
            .organisationUnitsCount(UPDATED_ORGANISATION_UNITS_COUNT)
            .organisationUnitsContent(UPDATED_ORGANISATION_UNITS_CONTENT);

        restDatasetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDataset.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDataset))
            )
            .andExpect(status().isOk());

        // Validate the DataSet in the database
        List<DataSet> datasetList = datasetRepository.findAll();
        assertThat(datasetList).hasSize(databaseSizeBeforeUpdate);
        DataSet testDataset = datasetList.get(datasetList.size() - 1);
        assertThat(testDataset.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataset.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testDataset.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
        assertThat(testDataset.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
        assertThat(testDataset.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDataset.getDimensionItemType()).isEqualTo(UPDATED_DIMENSION_ITEM_TYPE);
        assertThat(testDataset.getPeriodType()).isEqualTo(UPDATED_PERIOD_TYPE);
        assertThat(testDataset.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testDataset.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testDataset.getExpiryDays()).isEqualTo(UPDATED_EXPIRY_DAYS);
        assertThat(testDataset.getTimelyDays()).isEqualTo(DEFAULT_TIMELY_DAYS);
        assertThat(testDataset.getNotifyCompletingUser()).isEqualTo(UPDATED_NOTIFY_COMPLETING_USER);
        assertThat(testDataset.getOpenFuturePeriods()).isEqualTo(DEFAULT_OPEN_FUTURE_PERIODS);
        assertThat(testDataset.getOpenPeriodsAfterCoEndDate()).isEqualTo(DEFAULT_OPEN_PERIODS_AFTER_CO_END_DATE);
        assertThat(testDataset.getFieldCombinationRequired()).isEqualTo(DEFAULT_FIELD_COMBINATION_REQUIRED);
        assertThat(testDataset.getValidCompleteOnly()).isEqualTo(UPDATED_VALID_COMPLETE_ONLY);
        assertThat(testDataset.getNoValueRequiresComment()).isEqualTo(DEFAULT_NO_VALUE_REQUIRES_COMMENT);
        assertThat(testDataset.getSkipOffline()).isEqualTo(DEFAULT_SKIP_OFFLINE);
        assertThat(testDataset.getDataElementDecoration()).isEqualTo(DEFAULT_DATA_ELEMENT_DECORATION);
        assertThat(testDataset.getRenderAsTabs()).isEqualTo(DEFAULT_RENDER_AS_TABS);
        assertThat(testDataset.getRenderHorizontally()).isEqualTo(DEFAULT_RENDER_HORIZONTALLY);
        assertThat(testDataset.getCompulsoryFieldsCompleteOnly()).isEqualTo(DEFAULT_COMPULSORY_FIELDS_COMPLETE_ONLY);
        assertThat(testDataset.getFormType()).isEqualTo(UPDATED_FORM_TYPE);
        assertThat(testDataset.getDisplayName()).isEqualTo(DEFAULT_DISPLAY_NAME);
        assertThat(testDataset.getDimensionItem()).isEqualTo(DEFAULT_DIMENSION_ITEM);
        assertThat(testDataset.getDisplayShortName()).isEqualTo(DEFAULT_DISPLAY_SHORT_NAME);
        assertThat(testDataset.getDisplayDescription()).isEqualTo(DEFAULT_DISPLAY_DESCRIPTION);
        assertThat(testDataset.getDisplayFormName()).isEqualTo(UPDATED_DISPLAY_FORM_NAME);
        assertThat(testDataset.getDataSetElementsCount()).isEqualTo(UPDATED_DATA_SET_ELEMENTS_COUNT);
        assertThat(testDataset.getIndicatorsCount()).isEqualTo(UPDATED_INDICATORS_COUNT);
        assertThat(testDataset.getOrganisationUnitsCount()).isEqualTo(UPDATED_ORGANISATION_UNITS_COUNT);
        assertThat(testDataset.getDataSetElementsContent()).isEqualTo(DEFAULT_DATA_SET_ELEMENTS_CONTENT);
        assertThat(testDataset.getIndicatorsContent()).isEqualTo(DEFAULT_INDICATORS_CONTENT);
        assertThat(testDataset.getOrganisationUnitsContent()).isEqualTo(UPDATED_ORGANISATION_UNITS_CONTENT);
        assertThat(testDataset.getTrack()).isEqualTo(DEFAULT_TRACK);
    }

    @Test
    @Transactional
    void fullUpdateDatasetWithPatch() throws Exception {
        // Initialize the database
        dataset.setId(UUID.randomUUID().toString());
        datasetRepository.saveAndFlush(dataset);

        int databaseSizeBeforeUpdate = datasetRepository.findAll().size();

        // Update the dataset using partial update
        DataSet partialUpdatedDataset = new DataSet();
        partialUpdatedDataset.setId(dataset.getId());

        partialUpdatedDataset
            .name(UPDATED_NAME)
            .created(UPDATED_CREATED)
            .lastUpdated(UPDATED_LAST_UPDATED)
            .shortName(UPDATED_SHORT_NAME)
            .description(UPDATED_DESCRIPTION)
            .dimensionItemType(UPDATED_DIMENSION_ITEM_TYPE)
            .periodType(UPDATED_PERIOD_TYPE)
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
            .displayFormName(UPDATED_DISPLAY_FORM_NAME)
            .dataSetElementsCount(UPDATED_DATA_SET_ELEMENTS_COUNT)
            .indicatorsCount(UPDATED_INDICATORS_COUNT)
            .organisationUnitsCount(UPDATED_ORGANISATION_UNITS_COUNT)
            .dataSetElementsContent(UPDATED_DATA_SET_ELEMENTS_CONTENT)
            .indicatorsContent(UPDATED_INDICATORS_CONTENT)
            .organisationUnitsContent(UPDATED_ORGANISATION_UNITS_CONTENT)
            .track(UPDATED_TRACK);

        restDatasetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDataset.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDataset))
            )
            .andExpect(status().isOk());

        // Validate the DataSet in the database
        List<DataSet> datasetList = datasetRepository.findAll();
        assertThat(datasetList).hasSize(databaseSizeBeforeUpdate);
        DataSet testDataset = datasetList.get(datasetList.size() - 1);
        assertThat(testDataset.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataset.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testDataset.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
        assertThat(testDataset.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testDataset.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDataset.getDimensionItemType()).isEqualTo(UPDATED_DIMENSION_ITEM_TYPE);
        assertThat(testDataset.getPeriodType()).isEqualTo(UPDATED_PERIOD_TYPE);
        assertThat(testDataset.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testDataset.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testDataset.getExpiryDays()).isEqualTo(UPDATED_EXPIRY_DAYS);
        assertThat(testDataset.getTimelyDays()).isEqualTo(UPDATED_TIMELY_DAYS);
        assertThat(testDataset.getNotifyCompletingUser()).isEqualTo(UPDATED_NOTIFY_COMPLETING_USER);
        assertThat(testDataset.getOpenFuturePeriods()).isEqualTo(UPDATED_OPEN_FUTURE_PERIODS);
        assertThat(testDataset.getOpenPeriodsAfterCoEndDate()).isEqualTo(UPDATED_OPEN_PERIODS_AFTER_CO_END_DATE);
        assertThat(testDataset.getFieldCombinationRequired()).isEqualTo(UPDATED_FIELD_COMBINATION_REQUIRED);
        assertThat(testDataset.getValidCompleteOnly()).isEqualTo(UPDATED_VALID_COMPLETE_ONLY);
        assertThat(testDataset.getNoValueRequiresComment()).isEqualTo(UPDATED_NO_VALUE_REQUIRES_COMMENT);
        assertThat(testDataset.getSkipOffline()).isEqualTo(UPDATED_SKIP_OFFLINE);
        assertThat(testDataset.getDataElementDecoration()).isEqualTo(UPDATED_DATA_ELEMENT_DECORATION);
        assertThat(testDataset.getRenderAsTabs()).isEqualTo(UPDATED_RENDER_AS_TABS);
        assertThat(testDataset.getRenderHorizontally()).isEqualTo(UPDATED_RENDER_HORIZONTALLY);
        assertThat(testDataset.getCompulsoryFieldsCompleteOnly()).isEqualTo(UPDATED_COMPULSORY_FIELDS_COMPLETE_ONLY);
        assertThat(testDataset.getFormType()).isEqualTo(UPDATED_FORM_TYPE);
        assertThat(testDataset.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testDataset.getDimensionItem()).isEqualTo(UPDATED_DIMENSION_ITEM);
        assertThat(testDataset.getDisplayShortName()).isEqualTo(UPDATED_DISPLAY_SHORT_NAME);
        assertThat(testDataset.getDisplayDescription()).isEqualTo(UPDATED_DISPLAY_DESCRIPTION);
        assertThat(testDataset.getDisplayFormName()).isEqualTo(UPDATED_DISPLAY_FORM_NAME);
        assertThat(testDataset.getDataSetElementsCount()).isEqualTo(UPDATED_DATA_SET_ELEMENTS_COUNT);
        assertThat(testDataset.getIndicatorsCount()).isEqualTo(UPDATED_INDICATORS_COUNT);
        assertThat(testDataset.getOrganisationUnitsCount()).isEqualTo(UPDATED_ORGANISATION_UNITS_COUNT);
        assertThat(testDataset.getDataSetElementsContent()).isEqualTo(UPDATED_DATA_SET_ELEMENTS_CONTENT);
        assertThat(testDataset.getIndicatorsContent()).isEqualTo(UPDATED_INDICATORS_CONTENT);
        assertThat(testDataset.getOrganisationUnitsContent()).isEqualTo(UPDATED_ORGANISATION_UNITS_CONTENT);
        assertThat(testDataset.getTrack()).isEqualTo(UPDATED_TRACK);
    }

    @Test
    @Transactional
    void patchNonExistingDataset() throws Exception {
        int databaseSizeBeforeUpdate = datasetRepository.findAll().size();
        dataset.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDatasetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dataset.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dataset))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataSet in the database
        List<DataSet> datasetList = datasetRepository.findAll();
        assertThat(datasetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDataset() throws Exception {
        int databaseSizeBeforeUpdate = datasetRepository.findAll().size();
        dataset.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDatasetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dataset))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataSet in the database
        List<DataSet> datasetList = datasetRepository.findAll();
        assertThat(datasetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDataset() throws Exception {
        int databaseSizeBeforeUpdate = datasetRepository.findAll().size();
        dataset.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDatasetMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dataset)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DataSet in the database
        List<DataSet> datasetList = datasetRepository.findAll();
        assertThat(datasetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDataset() throws Exception {
        // Initialize the database
        dataset.setId(UUID.randomUUID().toString());
        datasetRepository.saveAndFlush(dataset);

        int databaseSizeBeforeDelete = datasetRepository.findAll().size();

        // Delete the dataset
        restDatasetMockMvc
            .perform(delete(ENTITY_API_URL_ID, dataset.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DataSet> datasetList = datasetRepository.findAll();
        assertThat(datasetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
