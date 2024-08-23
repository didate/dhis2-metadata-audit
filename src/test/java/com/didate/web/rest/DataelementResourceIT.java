package com.didate.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.didate.IntegrationTest;
import com.didate.domain.DHISUser;
import com.didate.domain.Dataelement;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.repository.DataelementRepository;
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
 * Integration tests for the {@link DataelementResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DataelementResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FORM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FORM_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_FORM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_FORM_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PUBLIC_ACCESS = "AAAAAAAAAA";
    private static final String UPDATED_PUBLIC_ACCESS = "BBBBBBBBBB";

    private static final String DEFAULT_DIMENSION_ITEM_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DIMENSION_ITEM_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_AGGREGATION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_AGGREGATION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DOMAIN_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DOMAIN_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ZERO_IS_SIGNIFICANT = false;
    private static final Boolean UPDATED_ZERO_IS_SIGNIFICANT = true;

    private static final String DEFAULT_OPTION_SET_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_OPTION_SET_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DIMENSION_ITEM = "AAAAAAAAAA";
    private static final String UPDATED_DIMENSION_ITEM = "BBBBBBBBBB";

    private static final TypeTrack DEFAULT_TRACK = TypeTrack.NEW;
    private static final TypeTrack UPDATED_TRACK = TypeTrack.UPDATE;

    private static final String ENTITY_API_URL = "/api/dataelements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private DataelementRepository dataelementRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDataelementMockMvc;

    private Dataelement dataelement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dataelement createEntity(EntityManager em) {
        Dataelement dataelement = new Dataelement()
            .name(DEFAULT_NAME)
            .shortName(DEFAULT_SHORT_NAME)
            .formName(DEFAULT_FORM_NAME)
            .description(DEFAULT_DESCRIPTION)
            .displayShortName(DEFAULT_DISPLAY_SHORT_NAME)
            .displayName(DEFAULT_DISPLAY_NAME)
            .displayFormName(DEFAULT_DISPLAY_FORM_NAME)
            .created(DEFAULT_CREATED)
            .lastUpdated(DEFAULT_LAST_UPDATED)
            .publicAccess(DEFAULT_PUBLIC_ACCESS)
            .dimensionItemType(DEFAULT_DIMENSION_ITEM_TYPE)
            .aggregationType(DEFAULT_AGGREGATION_TYPE)
            .valueType(DEFAULT_VALUE_TYPE)
            .domainType(DEFAULT_DOMAIN_TYPE)
            .zeroIsSignificant(DEFAULT_ZERO_IS_SIGNIFICANT)
            .optionSetValue(DEFAULT_OPTION_SET_VALUE)
            .dimensionItem(DEFAULT_DIMENSION_ITEM)
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
        dataelement.setCreatedBy(dHISUser);
        // Add required entity
        dataelement.setLastUpdatedBy(dHISUser);
        return dataelement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dataelement createUpdatedEntity(EntityManager em) {
        Dataelement dataelement = new Dataelement()
            .name(UPDATED_NAME)
            .shortName(UPDATED_SHORT_NAME)
            .formName(UPDATED_FORM_NAME)
            .description(UPDATED_DESCRIPTION)
            .displayShortName(UPDATED_DISPLAY_SHORT_NAME)
            .displayName(UPDATED_DISPLAY_NAME)
            .displayFormName(UPDATED_DISPLAY_FORM_NAME)
            .created(UPDATED_CREATED)
            .lastUpdated(UPDATED_LAST_UPDATED)
            .publicAccess(UPDATED_PUBLIC_ACCESS)
            .dimensionItemType(UPDATED_DIMENSION_ITEM_TYPE)
            .aggregationType(UPDATED_AGGREGATION_TYPE)
            .valueType(UPDATED_VALUE_TYPE)
            .domainType(UPDATED_DOMAIN_TYPE)
            .zeroIsSignificant(UPDATED_ZERO_IS_SIGNIFICANT)
            .optionSetValue(UPDATED_OPTION_SET_VALUE)
            .dimensionItem(UPDATED_DIMENSION_ITEM)
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
        dataelement.setCreatedBy(dHISUser);
        // Add required entity
        dataelement.setLastUpdatedBy(dHISUser);
        return dataelement;
    }

    @BeforeEach
    public void initTest() {
        dataelement = createEntity(em);
    }

    @Test
    @Transactional
    void createDataelement() throws Exception {
        int databaseSizeBeforeCreate = dataelementRepository.findAll().size();
        // Create the Dataelement
        restDataelementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataelement)))
            .andExpect(status().isCreated());

        // Validate the Dataelement in the database
        List<Dataelement> dataelementList = dataelementRepository.findAll();
        assertThat(dataelementList).hasSize(databaseSizeBeforeCreate + 1);
        Dataelement testDataelement = dataelementList.get(dataelementList.size() - 1);
        assertThat(testDataelement.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDataelement.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
        assertThat(testDataelement.getFormName()).isEqualTo(DEFAULT_FORM_NAME);
        assertThat(testDataelement.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDataelement.getDisplayShortName()).isEqualTo(DEFAULT_DISPLAY_SHORT_NAME);
        assertThat(testDataelement.getDisplayName()).isEqualTo(DEFAULT_DISPLAY_NAME);
        assertThat(testDataelement.getDisplayFormName()).isEqualTo(DEFAULT_DISPLAY_FORM_NAME);
        assertThat(testDataelement.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testDataelement.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
        assertThat(testDataelement.getPublicAccess()).isEqualTo(DEFAULT_PUBLIC_ACCESS);
        assertThat(testDataelement.getDimensionItemType()).isEqualTo(DEFAULT_DIMENSION_ITEM_TYPE);
        assertThat(testDataelement.getAggregationType()).isEqualTo(DEFAULT_AGGREGATION_TYPE);
        assertThat(testDataelement.getValueType()).isEqualTo(DEFAULT_VALUE_TYPE);
        assertThat(testDataelement.getDomainType()).isEqualTo(DEFAULT_DOMAIN_TYPE);
        assertThat(testDataelement.getZeroIsSignificant()).isEqualTo(DEFAULT_ZERO_IS_SIGNIFICANT);
        assertThat(testDataelement.getOptionSetValue()).isEqualTo(DEFAULT_OPTION_SET_VALUE);
        assertThat(testDataelement.getDimensionItem()).isEqualTo(DEFAULT_DIMENSION_ITEM);
        assertThat(testDataelement.getTrack()).isEqualTo(DEFAULT_TRACK);
    }

    @Test
    @Transactional
    void createDataelementWithExistingId() throws Exception {
        // Create the Dataelement with an existing ID
        dataelement.setId("existing_id");

        int databaseSizeBeforeCreate = dataelementRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataelementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataelement)))
            .andExpect(status().isBadRequest());

        // Validate the Dataelement in the database
        List<Dataelement> dataelementList = dataelementRepository.findAll();
        assertThat(dataelementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataelementRepository.findAll().size();
        // set the field null
        dataelement.setName(null);

        // Create the Dataelement, which fails.

        restDataelementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataelement)))
            .andExpect(status().isBadRequest());

        List<Dataelement> dataelementList = dataelementRepository.findAll();
        assertThat(dataelementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkShortNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataelementRepository.findAll().size();
        // set the field null
        dataelement.setShortName(null);

        // Create the Dataelement, which fails.

        restDataelementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataelement)))
            .andExpect(status().isBadRequest());

        List<Dataelement> dataelementList = dataelementRepository.findAll();
        assertThat(dataelementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataelementRepository.findAll().size();
        // set the field null
        dataelement.setCreated(null);

        // Create the Dataelement, which fails.

        restDataelementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataelement)))
            .andExpect(status().isBadRequest());

        List<Dataelement> dataelementList = dataelementRepository.findAll();
        assertThat(dataelementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastUpdatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataelementRepository.findAll().size();
        // set the field null
        dataelement.setLastUpdated(null);

        // Create the Dataelement, which fails.

        restDataelementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataelement)))
            .andExpect(status().isBadRequest());

        List<Dataelement> dataelementList = dataelementRepository.findAll();
        assertThat(dataelementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPublicAccessIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataelementRepository.findAll().size();
        // set the field null
        dataelement.setPublicAccess(null);

        // Create the Dataelement, which fails.

        restDataelementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataelement)))
            .andExpect(status().isBadRequest());

        List<Dataelement> dataelementList = dataelementRepository.findAll();
        assertThat(dataelementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDimensionItemTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataelementRepository.findAll().size();
        // set the field null
        dataelement.setDimensionItemType(null);

        // Create the Dataelement, which fails.

        restDataelementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataelement)))
            .andExpect(status().isBadRequest());

        List<Dataelement> dataelementList = dataelementRepository.findAll();
        assertThat(dataelementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAggregationTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataelementRepository.findAll().size();
        // set the field null
        dataelement.setAggregationType(null);

        // Create the Dataelement, which fails.

        restDataelementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataelement)))
            .andExpect(status().isBadRequest());

        List<Dataelement> dataelementList = dataelementRepository.findAll();
        assertThat(dataelementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataelementRepository.findAll().size();
        // set the field null
        dataelement.setValueType(null);

        // Create the Dataelement, which fails.

        restDataelementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataelement)))
            .andExpect(status().isBadRequest());

        List<Dataelement> dataelementList = dataelementRepository.findAll();
        assertThat(dataelementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDomainTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataelementRepository.findAll().size();
        // set the field null
        dataelement.setDomainType(null);

        // Create the Dataelement, which fails.

        restDataelementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataelement)))
            .andExpect(status().isBadRequest());

        List<Dataelement> dataelementList = dataelementRepository.findAll();
        assertThat(dataelementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTrackIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataelementRepository.findAll().size();
        // set the field null
        dataelement.setTrack(null);

        // Create the Dataelement, which fails.

        restDataelementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataelement)))
            .andExpect(status().isBadRequest());

        List<Dataelement> dataelementList = dataelementRepository.findAll();
        assertThat(dataelementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDataelements() throws Exception {
        // Initialize the database
        dataelement.setId(UUID.randomUUID().toString());
        dataelementRepository.saveAndFlush(dataelement);

        // Get all the dataelementList
        restDataelementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataelement.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].formName").value(hasItem(DEFAULT_FORM_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].displayShortName").value(hasItem(DEFAULT_DISPLAY_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].displayName").value(hasItem(DEFAULT_DISPLAY_NAME)))
            .andExpect(jsonPath("$.[*].displayFormName").value(hasItem(DEFAULT_DISPLAY_FORM_NAME)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].publicAccess").value(hasItem(DEFAULT_PUBLIC_ACCESS)))
            .andExpect(jsonPath("$.[*].dimensionItemType").value(hasItem(DEFAULT_DIMENSION_ITEM_TYPE)))
            .andExpect(jsonPath("$.[*].aggregationType").value(hasItem(DEFAULT_AGGREGATION_TYPE)))
            .andExpect(jsonPath("$.[*].valueType").value(hasItem(DEFAULT_VALUE_TYPE)))
            .andExpect(jsonPath("$.[*].domainType").value(hasItem(DEFAULT_DOMAIN_TYPE)))
            .andExpect(jsonPath("$.[*].zeroIsSignificant").value(hasItem(DEFAULT_ZERO_IS_SIGNIFICANT.booleanValue())))
            .andExpect(jsonPath("$.[*].optionSetValue").value(hasItem(DEFAULT_OPTION_SET_VALUE)))
            .andExpect(jsonPath("$.[*].dimensionItem").value(hasItem(DEFAULT_DIMENSION_ITEM)))
            .andExpect(jsonPath("$.[*].track").value(hasItem(DEFAULT_TRACK.toString())));
    }

    @Test
    @Transactional
    void getDataelement() throws Exception {
        // Initialize the database
        dataelement.setId(UUID.randomUUID().toString());
        dataelementRepository.saveAndFlush(dataelement);

        // Get the dataelement
        restDataelementMockMvc
            .perform(get(ENTITY_API_URL_ID, dataelement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dataelement.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME))
            .andExpect(jsonPath("$.formName").value(DEFAULT_FORM_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.displayShortName").value(DEFAULT_DISPLAY_SHORT_NAME))
            .andExpect(jsonPath("$.displayName").value(DEFAULT_DISPLAY_NAME))
            .andExpect(jsonPath("$.displayFormName").value(DEFAULT_DISPLAY_FORM_NAME))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.lastUpdated").value(DEFAULT_LAST_UPDATED.toString()))
            .andExpect(jsonPath("$.publicAccess").value(DEFAULT_PUBLIC_ACCESS))
            .andExpect(jsonPath("$.dimensionItemType").value(DEFAULT_DIMENSION_ITEM_TYPE))
            .andExpect(jsonPath("$.aggregationType").value(DEFAULT_AGGREGATION_TYPE))
            .andExpect(jsonPath("$.valueType").value(DEFAULT_VALUE_TYPE))
            .andExpect(jsonPath("$.domainType").value(DEFAULT_DOMAIN_TYPE))
            .andExpect(jsonPath("$.zeroIsSignificant").value(DEFAULT_ZERO_IS_SIGNIFICANT.booleanValue()))
            .andExpect(jsonPath("$.optionSetValue").value(DEFAULT_OPTION_SET_VALUE))
            .andExpect(jsonPath("$.dimensionItem").value(DEFAULT_DIMENSION_ITEM))
            .andExpect(jsonPath("$.track").value(DEFAULT_TRACK.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDataelement() throws Exception {
        // Get the dataelement
        restDataelementMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDataelement() throws Exception {
        // Initialize the database
        dataelement.setId(UUID.randomUUID().toString());
        dataelementRepository.saveAndFlush(dataelement);

        int databaseSizeBeforeUpdate = dataelementRepository.findAll().size();

        // Update the dataelement
        Dataelement updatedDataelement = dataelementRepository.findById(dataelement.getId()).get();
        // Disconnect from session so that the updates on updatedDataelement are not directly saved in db
        em.detach(updatedDataelement);
        updatedDataelement
            .name(UPDATED_NAME)
            .shortName(UPDATED_SHORT_NAME)
            .formName(UPDATED_FORM_NAME)
            .description(UPDATED_DESCRIPTION)
            .displayShortName(UPDATED_DISPLAY_SHORT_NAME)
            .displayName(UPDATED_DISPLAY_NAME)
            .displayFormName(UPDATED_DISPLAY_FORM_NAME)
            .created(UPDATED_CREATED)
            .lastUpdated(UPDATED_LAST_UPDATED)
            .publicAccess(UPDATED_PUBLIC_ACCESS)
            .dimensionItemType(UPDATED_DIMENSION_ITEM_TYPE)
            .aggregationType(UPDATED_AGGREGATION_TYPE)
            .valueType(UPDATED_VALUE_TYPE)
            .domainType(UPDATED_DOMAIN_TYPE)
            .zeroIsSignificant(UPDATED_ZERO_IS_SIGNIFICANT)
            .optionSetValue(UPDATED_OPTION_SET_VALUE)
            .dimensionItem(UPDATED_DIMENSION_ITEM)
            .track(UPDATED_TRACK);

        restDataelementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDataelement.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDataelement))
            )
            .andExpect(status().isOk());

        // Validate the Dataelement in the database
        List<Dataelement> dataelementList = dataelementRepository.findAll();
        assertThat(dataelementList).hasSize(databaseSizeBeforeUpdate);
        Dataelement testDataelement = dataelementList.get(dataelementList.size() - 1);
        assertThat(testDataelement.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataelement.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testDataelement.getFormName()).isEqualTo(UPDATED_FORM_NAME);
        assertThat(testDataelement.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDataelement.getDisplayShortName()).isEqualTo(UPDATED_DISPLAY_SHORT_NAME);
        assertThat(testDataelement.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testDataelement.getDisplayFormName()).isEqualTo(UPDATED_DISPLAY_FORM_NAME);
        assertThat(testDataelement.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testDataelement.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
        assertThat(testDataelement.getPublicAccess()).isEqualTo(UPDATED_PUBLIC_ACCESS);
        assertThat(testDataelement.getDimensionItemType()).isEqualTo(UPDATED_DIMENSION_ITEM_TYPE);
        assertThat(testDataelement.getAggregationType()).isEqualTo(UPDATED_AGGREGATION_TYPE);
        assertThat(testDataelement.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testDataelement.getDomainType()).isEqualTo(UPDATED_DOMAIN_TYPE);
        assertThat(testDataelement.getZeroIsSignificant()).isEqualTo(UPDATED_ZERO_IS_SIGNIFICANT);
        assertThat(testDataelement.getOptionSetValue()).isEqualTo(UPDATED_OPTION_SET_VALUE);
        assertThat(testDataelement.getDimensionItem()).isEqualTo(UPDATED_DIMENSION_ITEM);
        assertThat(testDataelement.getTrack()).isEqualTo(UPDATED_TRACK);
    }

    @Test
    @Transactional
    void putNonExistingDataelement() throws Exception {
        int databaseSizeBeforeUpdate = dataelementRepository.findAll().size();
        dataelement.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataelementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dataelement.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dataelement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dataelement in the database
        List<Dataelement> dataelementList = dataelementRepository.findAll();
        assertThat(dataelementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDataelement() throws Exception {
        int databaseSizeBeforeUpdate = dataelementRepository.findAll().size();
        dataelement.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataelementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dataelement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dataelement in the database
        List<Dataelement> dataelementList = dataelementRepository.findAll();
        assertThat(dataelementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDataelement() throws Exception {
        int databaseSizeBeforeUpdate = dataelementRepository.findAll().size();
        dataelement.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataelementMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataelement)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dataelement in the database
        List<Dataelement> dataelementList = dataelementRepository.findAll();
        assertThat(dataelementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDataelementWithPatch() throws Exception {
        // Initialize the database
        dataelement.setId(UUID.randomUUID().toString());
        dataelementRepository.saveAndFlush(dataelement);

        int databaseSizeBeforeUpdate = dataelementRepository.findAll().size();

        // Update the dataelement using partial update
        Dataelement partialUpdatedDataelement = new Dataelement();
        partialUpdatedDataelement.setId(dataelement.getId());

        partialUpdatedDataelement
            .name(UPDATED_NAME)
            .displayFormName(UPDATED_DISPLAY_FORM_NAME)
            .dimensionItemType(UPDATED_DIMENSION_ITEM_TYPE)
            .aggregationType(UPDATED_AGGREGATION_TYPE)
            .valueType(UPDATED_VALUE_TYPE)
            .domainType(UPDATED_DOMAIN_TYPE)
            .zeroIsSignificant(UPDATED_ZERO_IS_SIGNIFICANT);

        restDataelementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDataelement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDataelement))
            )
            .andExpect(status().isOk());

        // Validate the Dataelement in the database
        List<Dataelement> dataelementList = dataelementRepository.findAll();
        assertThat(dataelementList).hasSize(databaseSizeBeforeUpdate);
        Dataelement testDataelement = dataelementList.get(dataelementList.size() - 1);
        assertThat(testDataelement.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataelement.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
        assertThat(testDataelement.getFormName()).isEqualTo(DEFAULT_FORM_NAME);
        assertThat(testDataelement.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDataelement.getDisplayShortName()).isEqualTo(DEFAULT_DISPLAY_SHORT_NAME);
        assertThat(testDataelement.getDisplayName()).isEqualTo(DEFAULT_DISPLAY_NAME);
        assertThat(testDataelement.getDisplayFormName()).isEqualTo(UPDATED_DISPLAY_FORM_NAME);
        assertThat(testDataelement.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testDataelement.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
        assertThat(testDataelement.getPublicAccess()).isEqualTo(DEFAULT_PUBLIC_ACCESS);
        assertThat(testDataelement.getDimensionItemType()).isEqualTo(UPDATED_DIMENSION_ITEM_TYPE);
        assertThat(testDataelement.getAggregationType()).isEqualTo(UPDATED_AGGREGATION_TYPE);
        assertThat(testDataelement.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testDataelement.getDomainType()).isEqualTo(UPDATED_DOMAIN_TYPE);
        assertThat(testDataelement.getZeroIsSignificant()).isEqualTo(UPDATED_ZERO_IS_SIGNIFICANT);
        assertThat(testDataelement.getOptionSetValue()).isEqualTo(DEFAULT_OPTION_SET_VALUE);
        assertThat(testDataelement.getDimensionItem()).isEqualTo(DEFAULT_DIMENSION_ITEM);
        assertThat(testDataelement.getTrack()).isEqualTo(DEFAULT_TRACK);
    }

    @Test
    @Transactional
    void fullUpdateDataelementWithPatch() throws Exception {
        // Initialize the database
        dataelement.setId(UUID.randomUUID().toString());
        dataelementRepository.saveAndFlush(dataelement);

        int databaseSizeBeforeUpdate = dataelementRepository.findAll().size();

        // Update the dataelement using partial update
        Dataelement partialUpdatedDataelement = new Dataelement();
        partialUpdatedDataelement.setId(dataelement.getId());

        partialUpdatedDataelement
            .name(UPDATED_NAME)
            .shortName(UPDATED_SHORT_NAME)
            .formName(UPDATED_FORM_NAME)
            .description(UPDATED_DESCRIPTION)
            .displayShortName(UPDATED_DISPLAY_SHORT_NAME)
            .displayName(UPDATED_DISPLAY_NAME)
            .displayFormName(UPDATED_DISPLAY_FORM_NAME)
            .created(UPDATED_CREATED)
            .lastUpdated(UPDATED_LAST_UPDATED)
            .publicAccess(UPDATED_PUBLIC_ACCESS)
            .dimensionItemType(UPDATED_DIMENSION_ITEM_TYPE)
            .aggregationType(UPDATED_AGGREGATION_TYPE)
            .valueType(UPDATED_VALUE_TYPE)
            .domainType(UPDATED_DOMAIN_TYPE)
            .zeroIsSignificant(UPDATED_ZERO_IS_SIGNIFICANT)
            .optionSetValue(UPDATED_OPTION_SET_VALUE)
            .dimensionItem(UPDATED_DIMENSION_ITEM)
            .track(UPDATED_TRACK);

        restDataelementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDataelement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDataelement))
            )
            .andExpect(status().isOk());

        // Validate the Dataelement in the database
        List<Dataelement> dataelementList = dataelementRepository.findAll();
        assertThat(dataelementList).hasSize(databaseSizeBeforeUpdate);
        Dataelement testDataelement = dataelementList.get(dataelementList.size() - 1);
        assertThat(testDataelement.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataelement.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testDataelement.getFormName()).isEqualTo(UPDATED_FORM_NAME);
        assertThat(testDataelement.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDataelement.getDisplayShortName()).isEqualTo(UPDATED_DISPLAY_SHORT_NAME);
        assertThat(testDataelement.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testDataelement.getDisplayFormName()).isEqualTo(UPDATED_DISPLAY_FORM_NAME);
        assertThat(testDataelement.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testDataelement.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
        assertThat(testDataelement.getPublicAccess()).isEqualTo(UPDATED_PUBLIC_ACCESS);
        assertThat(testDataelement.getDimensionItemType()).isEqualTo(UPDATED_DIMENSION_ITEM_TYPE);
        assertThat(testDataelement.getAggregationType()).isEqualTo(UPDATED_AGGREGATION_TYPE);
        assertThat(testDataelement.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testDataelement.getDomainType()).isEqualTo(UPDATED_DOMAIN_TYPE);
        assertThat(testDataelement.getZeroIsSignificant()).isEqualTo(UPDATED_ZERO_IS_SIGNIFICANT);
        assertThat(testDataelement.getOptionSetValue()).isEqualTo(UPDATED_OPTION_SET_VALUE);
        assertThat(testDataelement.getDimensionItem()).isEqualTo(UPDATED_DIMENSION_ITEM);
        assertThat(testDataelement.getTrack()).isEqualTo(UPDATED_TRACK);
    }

    @Test
    @Transactional
    void patchNonExistingDataelement() throws Exception {
        int databaseSizeBeforeUpdate = dataelementRepository.findAll().size();
        dataelement.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataelementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dataelement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dataelement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dataelement in the database
        List<Dataelement> dataelementList = dataelementRepository.findAll();
        assertThat(dataelementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDataelement() throws Exception {
        int databaseSizeBeforeUpdate = dataelementRepository.findAll().size();
        dataelement.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataelementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dataelement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dataelement in the database
        List<Dataelement> dataelementList = dataelementRepository.findAll();
        assertThat(dataelementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDataelement() throws Exception {
        int databaseSizeBeforeUpdate = dataelementRepository.findAll().size();
        dataelement.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataelementMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dataelement))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dataelement in the database
        List<Dataelement> dataelementList = dataelementRepository.findAll();
        assertThat(dataelementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDataelement() throws Exception {
        // Initialize the database
        dataelement.setId(UUID.randomUUID().toString());
        dataelementRepository.saveAndFlush(dataelement);

        int databaseSizeBeforeDelete = dataelementRepository.findAll().size();

        // Delete the dataelement
        restDataelementMockMvc
            .perform(delete(ENTITY_API_URL_ID, dataelement.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Dataelement> dataelementList = dataelementRepository.findAll();
        assertThat(dataelementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
