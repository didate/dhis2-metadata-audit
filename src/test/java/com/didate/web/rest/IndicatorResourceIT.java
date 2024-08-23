package com.didate.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.didate.IntegrationTest;
import com.didate.domain.DHISUser;
import com.didate.domain.Indicator;
import com.didate.domain.Indicatortype;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.repository.IndicatorRepository;
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
 * Integration tests for the {@link IndicatorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IndicatorResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_NAME = "BBBBBBBBBB";

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

    private static final Boolean DEFAULT_ANNUALIZED = false;
    private static final Boolean UPDATED_ANNUALIZED = true;

    private static final String DEFAULT_NUMERATOR = "AAAAAAAAAA";
    private static final String UPDATED_NUMERATOR = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERATOR_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_NUMERATOR_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DENOMINATOR = "AAAAAAAAAA";
    private static final String UPDATED_DENOMINATOR = "BBBBBBBBBB";

    private static final String DEFAULT_DENOMINATOR_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DENOMINATOR_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_NUMERATOR_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_NUMERATOR_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_DENOMINATOR_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_DENOMINATOR_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DIMENSION_ITEM = "AAAAAAAAAA";
    private static final String UPDATED_DIMENSION_ITEM = "BBBBBBBBBB";

    private static final TypeTrack DEFAULT_TRACK = TypeTrack.NEW;
    private static final TypeTrack UPDATED_TRACK = TypeTrack.UPDATE;

    private static final String ENTITY_API_URL = "/api/indicators";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private IndicatorRepository indicatorRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIndicatorMockMvc;

    private Indicator indicator;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Indicator createEntity(EntityManager em) {
        Indicator indicator = new Indicator()
            .name(DEFAULT_NAME)
            .shortName(DEFAULT_SHORT_NAME)
            .displayShortName(DEFAULT_DISPLAY_SHORT_NAME)
            .displayName(DEFAULT_DISPLAY_NAME)
            .displayFormName(DEFAULT_DISPLAY_FORM_NAME)
            .created(DEFAULT_CREATED)
            .lastUpdated(DEFAULT_LAST_UPDATED)
            .publicAccess(DEFAULT_PUBLIC_ACCESS)
            .dimensionItemType(DEFAULT_DIMENSION_ITEM_TYPE)
            .annualized(DEFAULT_ANNUALIZED)
            .numerator(DEFAULT_NUMERATOR)
            .numeratorDescription(DEFAULT_NUMERATOR_DESCRIPTION)
            .denominator(DEFAULT_DENOMINATOR)
            .denominatorDescription(DEFAULT_DENOMINATOR_DESCRIPTION)
            .displayNumeratorDescription(DEFAULT_DISPLAY_NUMERATOR_DESCRIPTION)
            .displayDenominatorDescription(DEFAULT_DISPLAY_DENOMINATOR_DESCRIPTION)
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
        indicator.setCreatedBy(dHISUser);
        // Add required entity
        indicator.setLastUpdatedBy(dHISUser);
        // Add required entity
        Indicatortype indicatortype;
        if (TestUtil.findAll(em, Indicatortype.class).isEmpty()) {
            indicatortype = IndicatortypeResourceIT.createEntity(em);
            em.persist(indicatortype);
            em.flush();
        } else {
            indicatortype = TestUtil.findAll(em, Indicatortype.class).get(0);
        }
        indicator.setIndicatorType(indicatortype);
        return indicator;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Indicator createUpdatedEntity(EntityManager em) {
        Indicator indicator = new Indicator()
            .name(UPDATED_NAME)
            .shortName(UPDATED_SHORT_NAME)
            .displayShortName(UPDATED_DISPLAY_SHORT_NAME)
            .displayName(UPDATED_DISPLAY_NAME)
            .displayFormName(UPDATED_DISPLAY_FORM_NAME)
            .created(UPDATED_CREATED)
            .lastUpdated(UPDATED_LAST_UPDATED)
            .publicAccess(UPDATED_PUBLIC_ACCESS)
            .dimensionItemType(UPDATED_DIMENSION_ITEM_TYPE)
            .annualized(UPDATED_ANNUALIZED)
            .numerator(UPDATED_NUMERATOR)
            .numeratorDescription(UPDATED_NUMERATOR_DESCRIPTION)
            .denominator(UPDATED_DENOMINATOR)
            .denominatorDescription(UPDATED_DENOMINATOR_DESCRIPTION)
            .displayNumeratorDescription(UPDATED_DISPLAY_NUMERATOR_DESCRIPTION)
            .displayDenominatorDescription(UPDATED_DISPLAY_DENOMINATOR_DESCRIPTION)
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
        indicator.setCreatedBy(dHISUser);
        // Add required entity
        indicator.setLastUpdatedBy(dHISUser);
        // Add required entity
        Indicatortype indicatortype;
        if (TestUtil.findAll(em, Indicatortype.class).isEmpty()) {
            indicatortype = IndicatortypeResourceIT.createUpdatedEntity(em);
            em.persist(indicatortype);
            em.flush();
        } else {
            indicatortype = TestUtil.findAll(em, Indicatortype.class).get(0);
        }
        indicator.setIndicatorType(indicatortype);
        return indicator;
    }

    @BeforeEach
    public void initTest() {
        indicator = createEntity(em);
    }

    @Test
    @Transactional
    void createIndicator() throws Exception {
        int databaseSizeBeforeCreate = indicatorRepository.findAll().size();
        // Create the Indicator
        restIndicatorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indicator)))
            .andExpect(status().isCreated());

        // Validate the Indicator in the database
        List<Indicator> indicatorList = indicatorRepository.findAll();
        assertThat(indicatorList).hasSize(databaseSizeBeforeCreate + 1);
        Indicator testIndicator = indicatorList.get(indicatorList.size() - 1);
        assertThat(testIndicator.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testIndicator.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
        assertThat(testIndicator.getDisplayShortName()).isEqualTo(DEFAULT_DISPLAY_SHORT_NAME);
        assertThat(testIndicator.getDisplayName()).isEqualTo(DEFAULT_DISPLAY_NAME);
        assertThat(testIndicator.getDisplayFormName()).isEqualTo(DEFAULT_DISPLAY_FORM_NAME);
        assertThat(testIndicator.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testIndicator.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
        assertThat(testIndicator.getPublicAccess()).isEqualTo(DEFAULT_PUBLIC_ACCESS);
        assertThat(testIndicator.getDimensionItemType()).isEqualTo(DEFAULT_DIMENSION_ITEM_TYPE);
        assertThat(testIndicator.getAnnualized()).isEqualTo(DEFAULT_ANNUALIZED);
        assertThat(testIndicator.getNumerator()).isEqualTo(DEFAULT_NUMERATOR);
        assertThat(testIndicator.getNumeratorDescription()).isEqualTo(DEFAULT_NUMERATOR_DESCRIPTION);
        assertThat(testIndicator.getDenominator()).isEqualTo(DEFAULT_DENOMINATOR);
        assertThat(testIndicator.getDenominatorDescription()).isEqualTo(DEFAULT_DENOMINATOR_DESCRIPTION);
        assertThat(testIndicator.getDisplayNumeratorDescription()).isEqualTo(DEFAULT_DISPLAY_NUMERATOR_DESCRIPTION);
        assertThat(testIndicator.getDisplayDenominatorDescription()).isEqualTo(DEFAULT_DISPLAY_DENOMINATOR_DESCRIPTION);
        assertThat(testIndicator.getDimensionItem()).isEqualTo(DEFAULT_DIMENSION_ITEM);
        assertThat(testIndicator.getTrack()).isEqualTo(DEFAULT_TRACK);
    }

    @Test
    @Transactional
    void createIndicatorWithExistingId() throws Exception {
        // Create the Indicator with an existing ID
        indicator.setId("existing_id");

        int databaseSizeBeforeCreate = indicatorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndicatorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indicator)))
            .andExpect(status().isBadRequest());

        // Validate the Indicator in the database
        List<Indicator> indicatorList = indicatorRepository.findAll();
        assertThat(indicatorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = indicatorRepository.findAll().size();
        // set the field null
        indicator.setName(null);

        // Create the Indicator, which fails.

        restIndicatorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indicator)))
            .andExpect(status().isBadRequest());

        List<Indicator> indicatorList = indicatorRepository.findAll();
        assertThat(indicatorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkShortNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = indicatorRepository.findAll().size();
        // set the field null
        indicator.setShortName(null);

        // Create the Indicator, which fails.

        restIndicatorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indicator)))
            .andExpect(status().isBadRequest());

        List<Indicator> indicatorList = indicatorRepository.findAll();
        assertThat(indicatorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = indicatorRepository.findAll().size();
        // set the field null
        indicator.setCreated(null);

        // Create the Indicator, which fails.

        restIndicatorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indicator)))
            .andExpect(status().isBadRequest());

        List<Indicator> indicatorList = indicatorRepository.findAll();
        assertThat(indicatorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastUpdatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = indicatorRepository.findAll().size();
        // set the field null
        indicator.setLastUpdated(null);

        // Create the Indicator, which fails.

        restIndicatorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indicator)))
            .andExpect(status().isBadRequest());

        List<Indicator> indicatorList = indicatorRepository.findAll();
        assertThat(indicatorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPublicAccessIsRequired() throws Exception {
        int databaseSizeBeforeTest = indicatorRepository.findAll().size();
        // set the field null
        indicator.setPublicAccess(null);

        // Create the Indicator, which fails.

        restIndicatorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indicator)))
            .andExpect(status().isBadRequest());

        List<Indicator> indicatorList = indicatorRepository.findAll();
        assertThat(indicatorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDimensionItemTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = indicatorRepository.findAll().size();
        // set the field null
        indicator.setDimensionItemType(null);

        // Create the Indicator, which fails.

        restIndicatorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indicator)))
            .andExpect(status().isBadRequest());

        List<Indicator> indicatorList = indicatorRepository.findAll();
        assertThat(indicatorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAnnualizedIsRequired() throws Exception {
        int databaseSizeBeforeTest = indicatorRepository.findAll().size();
        // set the field null
        indicator.setAnnualized(null);

        // Create the Indicator, which fails.

        restIndicatorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indicator)))
            .andExpect(status().isBadRequest());

        List<Indicator> indicatorList = indicatorRepository.findAll();
        assertThat(indicatorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTrackIsRequired() throws Exception {
        int databaseSizeBeforeTest = indicatorRepository.findAll().size();
        // set the field null
        indicator.setTrack(null);

        // Create the Indicator, which fails.

        restIndicatorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indicator)))
            .andExpect(status().isBadRequest());

        List<Indicator> indicatorList = indicatorRepository.findAll();
        assertThat(indicatorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllIndicators() throws Exception {
        // Initialize the database
        indicator.setId(UUID.randomUUID().toString());
        indicatorRepository.saveAndFlush(indicator);

        // Get all the indicatorList
        restIndicatorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indicator.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].displayShortName").value(hasItem(DEFAULT_DISPLAY_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].displayName").value(hasItem(DEFAULT_DISPLAY_NAME)))
            .andExpect(jsonPath("$.[*].displayFormName").value(hasItem(DEFAULT_DISPLAY_FORM_NAME)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].publicAccess").value(hasItem(DEFAULT_PUBLIC_ACCESS)))
            .andExpect(jsonPath("$.[*].dimensionItemType").value(hasItem(DEFAULT_DIMENSION_ITEM_TYPE)))
            .andExpect(jsonPath("$.[*].annualized").value(hasItem(DEFAULT_ANNUALIZED.booleanValue())))
            .andExpect(jsonPath("$.[*].numerator").value(hasItem(DEFAULT_NUMERATOR)))
            .andExpect(jsonPath("$.[*].numeratorDescription").value(hasItem(DEFAULT_NUMERATOR_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].denominator").value(hasItem(DEFAULT_DENOMINATOR)))
            .andExpect(jsonPath("$.[*].denominatorDescription").value(hasItem(DEFAULT_DENOMINATOR_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].displayNumeratorDescription").value(hasItem(DEFAULT_DISPLAY_NUMERATOR_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].displayDenominatorDescription").value(hasItem(DEFAULT_DISPLAY_DENOMINATOR_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].dimensionItem").value(hasItem(DEFAULT_DIMENSION_ITEM)))
            .andExpect(jsonPath("$.[*].track").value(hasItem(DEFAULT_TRACK.toString())));
    }

    @Test
    @Transactional
    void getIndicator() throws Exception {
        // Initialize the database
        indicator.setId(UUID.randomUUID().toString());
        indicatorRepository.saveAndFlush(indicator);

        // Get the indicator
        restIndicatorMockMvc
            .perform(get(ENTITY_API_URL_ID, indicator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(indicator.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME))
            .andExpect(jsonPath("$.displayShortName").value(DEFAULT_DISPLAY_SHORT_NAME))
            .andExpect(jsonPath("$.displayName").value(DEFAULT_DISPLAY_NAME))
            .andExpect(jsonPath("$.displayFormName").value(DEFAULT_DISPLAY_FORM_NAME))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.lastUpdated").value(DEFAULT_LAST_UPDATED.toString()))
            .andExpect(jsonPath("$.publicAccess").value(DEFAULT_PUBLIC_ACCESS))
            .andExpect(jsonPath("$.dimensionItemType").value(DEFAULT_DIMENSION_ITEM_TYPE))
            .andExpect(jsonPath("$.annualized").value(DEFAULT_ANNUALIZED.booleanValue()))
            .andExpect(jsonPath("$.numerator").value(DEFAULT_NUMERATOR))
            .andExpect(jsonPath("$.numeratorDescription").value(DEFAULT_NUMERATOR_DESCRIPTION))
            .andExpect(jsonPath("$.denominator").value(DEFAULT_DENOMINATOR))
            .andExpect(jsonPath("$.denominatorDescription").value(DEFAULT_DENOMINATOR_DESCRIPTION))
            .andExpect(jsonPath("$.displayNumeratorDescription").value(DEFAULT_DISPLAY_NUMERATOR_DESCRIPTION))
            .andExpect(jsonPath("$.displayDenominatorDescription").value(DEFAULT_DISPLAY_DENOMINATOR_DESCRIPTION))
            .andExpect(jsonPath("$.dimensionItem").value(DEFAULT_DIMENSION_ITEM))
            .andExpect(jsonPath("$.track").value(DEFAULT_TRACK.toString()));
    }

    @Test
    @Transactional
    void getNonExistingIndicator() throws Exception {
        // Get the indicator
        restIndicatorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIndicator() throws Exception {
        // Initialize the database
        indicator.setId(UUID.randomUUID().toString());
        indicatorRepository.saveAndFlush(indicator);

        int databaseSizeBeforeUpdate = indicatorRepository.findAll().size();

        // Update the indicator
        Indicator updatedIndicator = indicatorRepository.findById(indicator.getId()).get();
        // Disconnect from session so that the updates on updatedIndicator are not directly saved in db
        em.detach(updatedIndicator);
        updatedIndicator
            .name(UPDATED_NAME)
            .shortName(UPDATED_SHORT_NAME)
            .displayShortName(UPDATED_DISPLAY_SHORT_NAME)
            .displayName(UPDATED_DISPLAY_NAME)
            .displayFormName(UPDATED_DISPLAY_FORM_NAME)
            .created(UPDATED_CREATED)
            .lastUpdated(UPDATED_LAST_UPDATED)
            .publicAccess(UPDATED_PUBLIC_ACCESS)
            .dimensionItemType(UPDATED_DIMENSION_ITEM_TYPE)
            .annualized(UPDATED_ANNUALIZED)
            .numerator(UPDATED_NUMERATOR)
            .numeratorDescription(UPDATED_NUMERATOR_DESCRIPTION)
            .denominator(UPDATED_DENOMINATOR)
            .denominatorDescription(UPDATED_DENOMINATOR_DESCRIPTION)
            .displayNumeratorDescription(UPDATED_DISPLAY_NUMERATOR_DESCRIPTION)
            .displayDenominatorDescription(UPDATED_DISPLAY_DENOMINATOR_DESCRIPTION)
            .dimensionItem(UPDATED_DIMENSION_ITEM)
            .track(UPDATED_TRACK);

        restIndicatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIndicator.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedIndicator))
            )
            .andExpect(status().isOk());

        // Validate the Indicator in the database
        List<Indicator> indicatorList = indicatorRepository.findAll();
        assertThat(indicatorList).hasSize(databaseSizeBeforeUpdate);
        Indicator testIndicator = indicatorList.get(indicatorList.size() - 1);
        assertThat(testIndicator.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIndicator.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testIndicator.getDisplayShortName()).isEqualTo(UPDATED_DISPLAY_SHORT_NAME);
        assertThat(testIndicator.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testIndicator.getDisplayFormName()).isEqualTo(UPDATED_DISPLAY_FORM_NAME);
        assertThat(testIndicator.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testIndicator.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
        assertThat(testIndicator.getPublicAccess()).isEqualTo(UPDATED_PUBLIC_ACCESS);
        assertThat(testIndicator.getDimensionItemType()).isEqualTo(UPDATED_DIMENSION_ITEM_TYPE);
        assertThat(testIndicator.getAnnualized()).isEqualTo(UPDATED_ANNUALIZED);
        assertThat(testIndicator.getNumerator()).isEqualTo(UPDATED_NUMERATOR);
        assertThat(testIndicator.getNumeratorDescription()).isEqualTo(UPDATED_NUMERATOR_DESCRIPTION);
        assertThat(testIndicator.getDenominator()).isEqualTo(UPDATED_DENOMINATOR);
        assertThat(testIndicator.getDenominatorDescription()).isEqualTo(UPDATED_DENOMINATOR_DESCRIPTION);
        assertThat(testIndicator.getDisplayNumeratorDescription()).isEqualTo(UPDATED_DISPLAY_NUMERATOR_DESCRIPTION);
        assertThat(testIndicator.getDisplayDenominatorDescription()).isEqualTo(UPDATED_DISPLAY_DENOMINATOR_DESCRIPTION);
        assertThat(testIndicator.getDimensionItem()).isEqualTo(UPDATED_DIMENSION_ITEM);
        assertThat(testIndicator.getTrack()).isEqualTo(UPDATED_TRACK);
    }

    @Test
    @Transactional
    void putNonExistingIndicator() throws Exception {
        int databaseSizeBeforeUpdate = indicatorRepository.findAll().size();
        indicator.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndicatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, indicator.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indicator))
            )
            .andExpect(status().isBadRequest());

        // Validate the Indicator in the database
        List<Indicator> indicatorList = indicatorRepository.findAll();
        assertThat(indicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIndicator() throws Exception {
        int databaseSizeBeforeUpdate = indicatorRepository.findAll().size();
        indicator.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndicatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indicator))
            )
            .andExpect(status().isBadRequest());

        // Validate the Indicator in the database
        List<Indicator> indicatorList = indicatorRepository.findAll();
        assertThat(indicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIndicator() throws Exception {
        int databaseSizeBeforeUpdate = indicatorRepository.findAll().size();
        indicator.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndicatorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indicator)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Indicator in the database
        List<Indicator> indicatorList = indicatorRepository.findAll();
        assertThat(indicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIndicatorWithPatch() throws Exception {
        // Initialize the database
        indicator.setId(UUID.randomUUID().toString());
        indicatorRepository.saveAndFlush(indicator);

        int databaseSizeBeforeUpdate = indicatorRepository.findAll().size();

        // Update the indicator using partial update
        Indicator partialUpdatedIndicator = new Indicator();
        partialUpdatedIndicator.setId(indicator.getId());

        partialUpdatedIndicator
            .name(UPDATED_NAME)
            .publicAccess(UPDATED_PUBLIC_ACCESS)
            .dimensionItemType(UPDATED_DIMENSION_ITEM_TYPE)
            .numeratorDescription(UPDATED_NUMERATOR_DESCRIPTION)
            .denominator(UPDATED_DENOMINATOR)
            .displayNumeratorDescription(UPDATED_DISPLAY_NUMERATOR_DESCRIPTION)
            .track(UPDATED_TRACK);

        restIndicatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndicator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndicator))
            )
            .andExpect(status().isOk());

        // Validate the Indicator in the database
        List<Indicator> indicatorList = indicatorRepository.findAll();
        assertThat(indicatorList).hasSize(databaseSizeBeforeUpdate);
        Indicator testIndicator = indicatorList.get(indicatorList.size() - 1);
        assertThat(testIndicator.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIndicator.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
        assertThat(testIndicator.getDisplayShortName()).isEqualTo(DEFAULT_DISPLAY_SHORT_NAME);
        assertThat(testIndicator.getDisplayName()).isEqualTo(DEFAULT_DISPLAY_NAME);
        assertThat(testIndicator.getDisplayFormName()).isEqualTo(DEFAULT_DISPLAY_FORM_NAME);
        assertThat(testIndicator.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testIndicator.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
        assertThat(testIndicator.getPublicAccess()).isEqualTo(UPDATED_PUBLIC_ACCESS);
        assertThat(testIndicator.getDimensionItemType()).isEqualTo(UPDATED_DIMENSION_ITEM_TYPE);
        assertThat(testIndicator.getAnnualized()).isEqualTo(DEFAULT_ANNUALIZED);
        assertThat(testIndicator.getNumerator()).isEqualTo(DEFAULT_NUMERATOR);
        assertThat(testIndicator.getNumeratorDescription()).isEqualTo(UPDATED_NUMERATOR_DESCRIPTION);
        assertThat(testIndicator.getDenominator()).isEqualTo(UPDATED_DENOMINATOR);
        assertThat(testIndicator.getDenominatorDescription()).isEqualTo(DEFAULT_DENOMINATOR_DESCRIPTION);
        assertThat(testIndicator.getDisplayNumeratorDescription()).isEqualTo(UPDATED_DISPLAY_NUMERATOR_DESCRIPTION);
        assertThat(testIndicator.getDisplayDenominatorDescription()).isEqualTo(DEFAULT_DISPLAY_DENOMINATOR_DESCRIPTION);
        assertThat(testIndicator.getDimensionItem()).isEqualTo(DEFAULT_DIMENSION_ITEM);
        assertThat(testIndicator.getTrack()).isEqualTo(UPDATED_TRACK);
    }

    @Test
    @Transactional
    void fullUpdateIndicatorWithPatch() throws Exception {
        // Initialize the database
        indicator.setId(UUID.randomUUID().toString());
        indicatorRepository.saveAndFlush(indicator);

        int databaseSizeBeforeUpdate = indicatorRepository.findAll().size();

        // Update the indicator using partial update
        Indicator partialUpdatedIndicator = new Indicator();
        partialUpdatedIndicator.setId(indicator.getId());

        partialUpdatedIndicator
            .name(UPDATED_NAME)
            .shortName(UPDATED_SHORT_NAME)
            .displayShortName(UPDATED_DISPLAY_SHORT_NAME)
            .displayName(UPDATED_DISPLAY_NAME)
            .displayFormName(UPDATED_DISPLAY_FORM_NAME)
            .created(UPDATED_CREATED)
            .lastUpdated(UPDATED_LAST_UPDATED)
            .publicAccess(UPDATED_PUBLIC_ACCESS)
            .dimensionItemType(UPDATED_DIMENSION_ITEM_TYPE)
            .annualized(UPDATED_ANNUALIZED)
            .numerator(UPDATED_NUMERATOR)
            .numeratorDescription(UPDATED_NUMERATOR_DESCRIPTION)
            .denominator(UPDATED_DENOMINATOR)
            .denominatorDescription(UPDATED_DENOMINATOR_DESCRIPTION)
            .displayNumeratorDescription(UPDATED_DISPLAY_NUMERATOR_DESCRIPTION)
            .displayDenominatorDescription(UPDATED_DISPLAY_DENOMINATOR_DESCRIPTION)
            .dimensionItem(UPDATED_DIMENSION_ITEM)
            .track(UPDATED_TRACK);

        restIndicatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndicator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndicator))
            )
            .andExpect(status().isOk());

        // Validate the Indicator in the database
        List<Indicator> indicatorList = indicatorRepository.findAll();
        assertThat(indicatorList).hasSize(databaseSizeBeforeUpdate);
        Indicator testIndicator = indicatorList.get(indicatorList.size() - 1);
        assertThat(testIndicator.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIndicator.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testIndicator.getDisplayShortName()).isEqualTo(UPDATED_DISPLAY_SHORT_NAME);
        assertThat(testIndicator.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testIndicator.getDisplayFormName()).isEqualTo(UPDATED_DISPLAY_FORM_NAME);
        assertThat(testIndicator.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testIndicator.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
        assertThat(testIndicator.getPublicAccess()).isEqualTo(UPDATED_PUBLIC_ACCESS);
        assertThat(testIndicator.getDimensionItemType()).isEqualTo(UPDATED_DIMENSION_ITEM_TYPE);
        assertThat(testIndicator.getAnnualized()).isEqualTo(UPDATED_ANNUALIZED);
        assertThat(testIndicator.getNumerator()).isEqualTo(UPDATED_NUMERATOR);
        assertThat(testIndicator.getNumeratorDescription()).isEqualTo(UPDATED_NUMERATOR_DESCRIPTION);
        assertThat(testIndicator.getDenominator()).isEqualTo(UPDATED_DENOMINATOR);
        assertThat(testIndicator.getDenominatorDescription()).isEqualTo(UPDATED_DENOMINATOR_DESCRIPTION);
        assertThat(testIndicator.getDisplayNumeratorDescription()).isEqualTo(UPDATED_DISPLAY_NUMERATOR_DESCRIPTION);
        assertThat(testIndicator.getDisplayDenominatorDescription()).isEqualTo(UPDATED_DISPLAY_DENOMINATOR_DESCRIPTION);
        assertThat(testIndicator.getDimensionItem()).isEqualTo(UPDATED_DIMENSION_ITEM);
        assertThat(testIndicator.getTrack()).isEqualTo(UPDATED_TRACK);
    }

    @Test
    @Transactional
    void patchNonExistingIndicator() throws Exception {
        int databaseSizeBeforeUpdate = indicatorRepository.findAll().size();
        indicator.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndicatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, indicator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indicator))
            )
            .andExpect(status().isBadRequest());

        // Validate the Indicator in the database
        List<Indicator> indicatorList = indicatorRepository.findAll();
        assertThat(indicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIndicator() throws Exception {
        int databaseSizeBeforeUpdate = indicatorRepository.findAll().size();
        indicator.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndicatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indicator))
            )
            .andExpect(status().isBadRequest());

        // Validate the Indicator in the database
        List<Indicator> indicatorList = indicatorRepository.findAll();
        assertThat(indicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIndicator() throws Exception {
        int databaseSizeBeforeUpdate = indicatorRepository.findAll().size();
        indicator.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndicatorMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(indicator))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Indicator in the database
        List<Indicator> indicatorList = indicatorRepository.findAll();
        assertThat(indicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIndicator() throws Exception {
        // Initialize the database
        indicator.setId(UUID.randomUUID().toString());
        indicatorRepository.saveAndFlush(indicator);

        int databaseSizeBeforeDelete = indicatorRepository.findAll().size();

        // Delete the indicator
        restIndicatorMockMvc
            .perform(delete(ENTITY_API_URL_ID, indicator.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Indicator> indicatorList = indicatorRepository.findAll();
        assertThat(indicatorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
