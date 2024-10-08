package com.didate.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.didate.IntegrationTest;
import com.didate.domain.DHISUser;
import com.didate.domain.Program;
import com.didate.domain.ProgramIndicator;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.repository.ProgramIndicatorRepository;
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
 * Integration tests for the {@link ProgramIndicatorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProgramIndicatorResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DIMENSION_ITEM_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DIMENSION_ITEM_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_EXPRESSION = "AAAAAAAAAA";
    private static final String UPDATED_EXPRESSION = "BBBBBBBBBB";

    private static final String DEFAULT_FILTER = "AAAAAAAAAA";
    private static final String UPDATED_FILTER = "BBBBBBBBBB";

    private static final String DEFAULT_ANALYTICS_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ANALYTICS_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DIMENSION_ITEM = "AAAAAAAAAA";
    private static final String UPDATED_DIMENSION_ITEM = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_FORM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_FORM_NAME = "BBBBBBBBBB";

    private static final TypeTrack DEFAULT_TRACK = TypeTrack.NEW;
    private static final TypeTrack UPDATED_TRACK = TypeTrack.UPDATE;

    private static final String ENTITY_API_URL = "/api/program-indicators";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ProgramIndicatorRepository programIndicatorRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProgramIndicatorMockMvc;

    private ProgramIndicator programIndicator;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProgramIndicator createEntity(EntityManager em) {
        ProgramIndicator programIndicator = new ProgramIndicator()
            .name(DEFAULT_NAME)
            .created(DEFAULT_CREATED)
            .lastUpdated(DEFAULT_LAST_UPDATED)
            .shortName(DEFAULT_SHORT_NAME)
            .dimensionItemType(DEFAULT_DIMENSION_ITEM_TYPE)
            .expression(DEFAULT_EXPRESSION)
            .filter(DEFAULT_FILTER)
            .analyticsType(DEFAULT_ANALYTICS_TYPE)
            .dimensionItem(DEFAULT_DIMENSION_ITEM)
            .displayShortName(DEFAULT_DISPLAY_SHORT_NAME)
            .displayName(DEFAULT_DISPLAY_NAME)
            .displayFormName(DEFAULT_DISPLAY_FORM_NAME)
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
        programIndicator.setCreatedBy(dHISUser);
        // Add required entity
        programIndicator.setLastUpdatedBy(dHISUser);
        // Add required entity
        Program program;
        if (TestUtil.findAll(em, Program.class).isEmpty()) {
            program = ProgramResourceIT.createEntity(em);
            em.persist(program);
            em.flush();
        } else {
            program = TestUtil.findAll(em, Program.class).get(0);
        }
        programIndicator.setProgram(program);
        return programIndicator;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProgramIndicator createUpdatedEntity(EntityManager em) {
        ProgramIndicator programIndicator = new ProgramIndicator()
            .name(UPDATED_NAME)
            .created(UPDATED_CREATED)
            .lastUpdated(UPDATED_LAST_UPDATED)
            .shortName(UPDATED_SHORT_NAME)
            .dimensionItemType(UPDATED_DIMENSION_ITEM_TYPE)
            .expression(UPDATED_EXPRESSION)
            .filter(UPDATED_FILTER)
            .analyticsType(UPDATED_ANALYTICS_TYPE)
            .dimensionItem(UPDATED_DIMENSION_ITEM)
            .displayShortName(UPDATED_DISPLAY_SHORT_NAME)
            .displayName(UPDATED_DISPLAY_NAME)
            .displayFormName(UPDATED_DISPLAY_FORM_NAME)
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
        programIndicator.setCreatedBy(dHISUser);
        // Add required entity
        programIndicator.setLastUpdatedBy(dHISUser);
        // Add required entity
        Program program;
        if (TestUtil.findAll(em, Program.class).isEmpty()) {
            program = ProgramResourceIT.createUpdatedEntity(em);
            em.persist(program);
            em.flush();
        } else {
            program = TestUtil.findAll(em, Program.class).get(0);
        }
        programIndicator.setProgram(program);
        return programIndicator;
    }

    @BeforeEach
    public void initTest() {
        programIndicator = createEntity(em);
    }

    @Test
    @Transactional
    void createProgramIndicator() throws Exception {
        int databaseSizeBeforeCreate = programIndicatorRepository.findAll().size();
        // Create the ProgramIndicator
        restProgramIndicatorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(programIndicator))
            )
            .andExpect(status().isCreated());

        // Validate the ProgramIndicator in the database
        List<ProgramIndicator> programIndicatorList = programIndicatorRepository.findAll();
        assertThat(programIndicatorList).hasSize(databaseSizeBeforeCreate + 1);
        ProgramIndicator testProgramIndicator = programIndicatorList.get(programIndicatorList.size() - 1);
        assertThat(testProgramIndicator.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProgramIndicator.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testProgramIndicator.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
        assertThat(testProgramIndicator.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
        assertThat(testProgramIndicator.getDimensionItemType()).isEqualTo(DEFAULT_DIMENSION_ITEM_TYPE);
        assertThat(testProgramIndicator.getExpression()).isEqualTo(DEFAULT_EXPRESSION);
        assertThat(testProgramIndicator.getFilter()).isEqualTo(DEFAULT_FILTER);
        assertThat(testProgramIndicator.getAnalyticsType()).isEqualTo(DEFAULT_ANALYTICS_TYPE);
        assertThat(testProgramIndicator.getDimensionItem()).isEqualTo(DEFAULT_DIMENSION_ITEM);
        assertThat(testProgramIndicator.getDisplayShortName()).isEqualTo(DEFAULT_DISPLAY_SHORT_NAME);
        assertThat(testProgramIndicator.getDisplayName()).isEqualTo(DEFAULT_DISPLAY_NAME);
        assertThat(testProgramIndicator.getDisplayFormName()).isEqualTo(DEFAULT_DISPLAY_FORM_NAME);
        assertThat(testProgramIndicator.getTrack()).isEqualTo(DEFAULT_TRACK);
    }

    @Test
    @Transactional
    void createProgramIndicatorWithExistingId() throws Exception {
        // Create the ProgramIndicator with an existing ID
        programIndicator.setId("existing_id");

        int databaseSizeBeforeCreate = programIndicatorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProgramIndicatorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(programIndicator))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProgramIndicator in the database
        List<ProgramIndicator> programIndicatorList = programIndicatorRepository.findAll();
        assertThat(programIndicatorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = programIndicatorRepository.findAll().size();
        // set the field null
        programIndicator.setName(null);

        // Create the ProgramIndicator, which fails.

        restProgramIndicatorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(programIndicator))
            )
            .andExpect(status().isBadRequest());

        List<ProgramIndicator> programIndicatorList = programIndicatorRepository.findAll();
        assertThat(programIndicatorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = programIndicatorRepository.findAll().size();
        // set the field null
        programIndicator.setCreated(null);

        // Create the ProgramIndicator, which fails.

        restProgramIndicatorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(programIndicator))
            )
            .andExpect(status().isBadRequest());

        List<ProgramIndicator> programIndicatorList = programIndicatorRepository.findAll();
        assertThat(programIndicatorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastUpdatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = programIndicatorRepository.findAll().size();
        // set the field null
        programIndicator.setLastUpdated(null);

        // Create the ProgramIndicator, which fails.

        restProgramIndicatorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(programIndicator))
            )
            .andExpect(status().isBadRequest());

        List<ProgramIndicator> programIndicatorList = programIndicatorRepository.findAll();
        assertThat(programIndicatorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTrackIsRequired() throws Exception {
        int databaseSizeBeforeTest = programIndicatorRepository.findAll().size();
        // set the field null
        programIndicator.setTrack(null);

        // Create the ProgramIndicator, which fails.

        restProgramIndicatorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(programIndicator))
            )
            .andExpect(status().isBadRequest());

        List<ProgramIndicator> programIndicatorList = programIndicatorRepository.findAll();
        assertThat(programIndicatorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProgramIndicators() throws Exception {
        // Initialize the database
        programIndicator.setId(UUID.randomUUID().toString());
        programIndicatorRepository.saveAndFlush(programIndicator);

        // Get all the programIndicatorList
        restProgramIndicatorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(programIndicator.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].dimensionItemType").value(hasItem(DEFAULT_DIMENSION_ITEM_TYPE)))
            .andExpect(jsonPath("$.[*].expression").value(hasItem(DEFAULT_EXPRESSION)))
            .andExpect(jsonPath("$.[*].filter").value(hasItem(DEFAULT_FILTER)))
            .andExpect(jsonPath("$.[*].analyticsType").value(hasItem(DEFAULT_ANALYTICS_TYPE)))
            .andExpect(jsonPath("$.[*].dimensionItem").value(hasItem(DEFAULT_DIMENSION_ITEM)))
            .andExpect(jsonPath("$.[*].displayShortName").value(hasItem(DEFAULT_DISPLAY_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].displayName").value(hasItem(DEFAULT_DISPLAY_NAME)))
            .andExpect(jsonPath("$.[*].displayFormName").value(hasItem(DEFAULT_DISPLAY_FORM_NAME)))
            .andExpect(jsonPath("$.[*].track").value(hasItem(DEFAULT_TRACK.toString())));
    }

    @Test
    @Transactional
    void getProgramIndicator() throws Exception {
        // Initialize the database
        programIndicator.setId(UUID.randomUUID().toString());
        programIndicatorRepository.saveAndFlush(programIndicator);

        // Get the programIndicator
        restProgramIndicatorMockMvc
            .perform(get(ENTITY_API_URL_ID, programIndicator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(programIndicator.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.lastUpdated").value(DEFAULT_LAST_UPDATED.toString()))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME))
            .andExpect(jsonPath("$.dimensionItemType").value(DEFAULT_DIMENSION_ITEM_TYPE))
            .andExpect(jsonPath("$.expression").value(DEFAULT_EXPRESSION))
            .andExpect(jsonPath("$.filter").value(DEFAULT_FILTER))
            .andExpect(jsonPath("$.analyticsType").value(DEFAULT_ANALYTICS_TYPE))
            .andExpect(jsonPath("$.dimensionItem").value(DEFAULT_DIMENSION_ITEM))
            .andExpect(jsonPath("$.displayShortName").value(DEFAULT_DISPLAY_SHORT_NAME))
            .andExpect(jsonPath("$.displayName").value(DEFAULT_DISPLAY_NAME))
            .andExpect(jsonPath("$.displayFormName").value(DEFAULT_DISPLAY_FORM_NAME))
            .andExpect(jsonPath("$.track").value(DEFAULT_TRACK.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProgramIndicator() throws Exception {
        // Get the programIndicator
        restProgramIndicatorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProgramIndicator() throws Exception {
        // Initialize the database
        programIndicator.setId(UUID.randomUUID().toString());
        programIndicatorRepository.saveAndFlush(programIndicator);

        int databaseSizeBeforeUpdate = programIndicatorRepository.findAll().size();

        // Update the programIndicator
        ProgramIndicator updatedProgramIndicator = programIndicatorRepository.findById(programIndicator.getId()).get();
        // Disconnect from session so that the updates on updatedProgramIndicator are not directly saved in db
        em.detach(updatedProgramIndicator);
        updatedProgramIndicator
            .name(UPDATED_NAME)
            .created(UPDATED_CREATED)
            .lastUpdated(UPDATED_LAST_UPDATED)
            .shortName(UPDATED_SHORT_NAME)
            .dimensionItemType(UPDATED_DIMENSION_ITEM_TYPE)
            .expression(UPDATED_EXPRESSION)
            .filter(UPDATED_FILTER)
            .analyticsType(UPDATED_ANALYTICS_TYPE)
            .dimensionItem(UPDATED_DIMENSION_ITEM)
            .displayShortName(UPDATED_DISPLAY_SHORT_NAME)
            .displayName(UPDATED_DISPLAY_NAME)
            .displayFormName(UPDATED_DISPLAY_FORM_NAME)
            .track(UPDATED_TRACK);

        restProgramIndicatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProgramIndicator.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProgramIndicator))
            )
            .andExpect(status().isOk());

        // Validate the ProgramIndicator in the database
        List<ProgramIndicator> programIndicatorList = programIndicatorRepository.findAll();
        assertThat(programIndicatorList).hasSize(databaseSizeBeforeUpdate);
        ProgramIndicator testProgramIndicator = programIndicatorList.get(programIndicatorList.size() - 1);
        assertThat(testProgramIndicator.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProgramIndicator.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testProgramIndicator.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
        assertThat(testProgramIndicator.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testProgramIndicator.getDimensionItemType()).isEqualTo(UPDATED_DIMENSION_ITEM_TYPE);
        assertThat(testProgramIndicator.getExpression()).isEqualTo(UPDATED_EXPRESSION);
        assertThat(testProgramIndicator.getFilter()).isEqualTo(UPDATED_FILTER);
        assertThat(testProgramIndicator.getAnalyticsType()).isEqualTo(UPDATED_ANALYTICS_TYPE);
        assertThat(testProgramIndicator.getDimensionItem()).isEqualTo(UPDATED_DIMENSION_ITEM);
        assertThat(testProgramIndicator.getDisplayShortName()).isEqualTo(UPDATED_DISPLAY_SHORT_NAME);
        assertThat(testProgramIndicator.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testProgramIndicator.getDisplayFormName()).isEqualTo(UPDATED_DISPLAY_FORM_NAME);
        assertThat(testProgramIndicator.getTrack()).isEqualTo(UPDATED_TRACK);
    }

    @Test
    @Transactional
    void putNonExistingProgramIndicator() throws Exception {
        int databaseSizeBeforeUpdate = programIndicatorRepository.findAll().size();
        programIndicator.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgramIndicatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, programIndicator.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(programIndicator))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProgramIndicator in the database
        List<ProgramIndicator> programIndicatorList = programIndicatorRepository.findAll();
        assertThat(programIndicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProgramIndicator() throws Exception {
        int databaseSizeBeforeUpdate = programIndicatorRepository.findAll().size();
        programIndicator.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramIndicatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(programIndicator))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProgramIndicator in the database
        List<ProgramIndicator> programIndicatorList = programIndicatorRepository.findAll();
        assertThat(programIndicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProgramIndicator() throws Exception {
        int databaseSizeBeforeUpdate = programIndicatorRepository.findAll().size();
        programIndicator.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramIndicatorMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(programIndicator))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProgramIndicator in the database
        List<ProgramIndicator> programIndicatorList = programIndicatorRepository.findAll();
        assertThat(programIndicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProgramIndicatorWithPatch() throws Exception {
        // Initialize the database
        programIndicator.setId(UUID.randomUUID().toString());
        programIndicatorRepository.saveAndFlush(programIndicator);

        int databaseSizeBeforeUpdate = programIndicatorRepository.findAll().size();

        // Update the programIndicator using partial update
        ProgramIndicator partialUpdatedProgramIndicator = new ProgramIndicator();
        partialUpdatedProgramIndicator.setId(programIndicator.getId());

        partialUpdatedProgramIndicator
            .lastUpdated(UPDATED_LAST_UPDATED)
            .shortName(UPDATED_SHORT_NAME)
            .expression(UPDATED_EXPRESSION)
            .analyticsType(UPDATED_ANALYTICS_TYPE)
            .dimensionItem(UPDATED_DIMENSION_ITEM)
            .displayShortName(UPDATED_DISPLAY_SHORT_NAME)
            .displayFormName(UPDATED_DISPLAY_FORM_NAME);

        restProgramIndicatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProgramIndicator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProgramIndicator))
            )
            .andExpect(status().isOk());

        // Validate the ProgramIndicator in the database
        List<ProgramIndicator> programIndicatorList = programIndicatorRepository.findAll();
        assertThat(programIndicatorList).hasSize(databaseSizeBeforeUpdate);
        ProgramIndicator testProgramIndicator = programIndicatorList.get(programIndicatorList.size() - 1);
        assertThat(testProgramIndicator.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProgramIndicator.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testProgramIndicator.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
        assertThat(testProgramIndicator.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testProgramIndicator.getDimensionItemType()).isEqualTo(DEFAULT_DIMENSION_ITEM_TYPE);
        assertThat(testProgramIndicator.getExpression()).isEqualTo(UPDATED_EXPRESSION);
        assertThat(testProgramIndicator.getFilter()).isEqualTo(DEFAULT_FILTER);
        assertThat(testProgramIndicator.getAnalyticsType()).isEqualTo(UPDATED_ANALYTICS_TYPE);
        assertThat(testProgramIndicator.getDimensionItem()).isEqualTo(UPDATED_DIMENSION_ITEM);
        assertThat(testProgramIndicator.getDisplayShortName()).isEqualTo(UPDATED_DISPLAY_SHORT_NAME);
        assertThat(testProgramIndicator.getDisplayName()).isEqualTo(DEFAULT_DISPLAY_NAME);
        assertThat(testProgramIndicator.getDisplayFormName()).isEqualTo(UPDATED_DISPLAY_FORM_NAME);
        assertThat(testProgramIndicator.getTrack()).isEqualTo(DEFAULT_TRACK);
    }

    @Test
    @Transactional
    void fullUpdateProgramIndicatorWithPatch() throws Exception {
        // Initialize the database
        programIndicator.setId(UUID.randomUUID().toString());
        programIndicatorRepository.saveAndFlush(programIndicator);

        int databaseSizeBeforeUpdate = programIndicatorRepository.findAll().size();

        // Update the programIndicator using partial update
        ProgramIndicator partialUpdatedProgramIndicator = new ProgramIndicator();
        partialUpdatedProgramIndicator.setId(programIndicator.getId());

        partialUpdatedProgramIndicator
            .name(UPDATED_NAME)
            .created(UPDATED_CREATED)
            .lastUpdated(UPDATED_LAST_UPDATED)
            .shortName(UPDATED_SHORT_NAME)
            .dimensionItemType(UPDATED_DIMENSION_ITEM_TYPE)
            .expression(UPDATED_EXPRESSION)
            .filter(UPDATED_FILTER)
            .analyticsType(UPDATED_ANALYTICS_TYPE)
            .dimensionItem(UPDATED_DIMENSION_ITEM)
            .displayShortName(UPDATED_DISPLAY_SHORT_NAME)
            .displayName(UPDATED_DISPLAY_NAME)
            .displayFormName(UPDATED_DISPLAY_FORM_NAME)
            .track(UPDATED_TRACK);

        restProgramIndicatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProgramIndicator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProgramIndicator))
            )
            .andExpect(status().isOk());

        // Validate the ProgramIndicator in the database
        List<ProgramIndicator> programIndicatorList = programIndicatorRepository.findAll();
        assertThat(programIndicatorList).hasSize(databaseSizeBeforeUpdate);
        ProgramIndicator testProgramIndicator = programIndicatorList.get(programIndicatorList.size() - 1);
        assertThat(testProgramIndicator.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProgramIndicator.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testProgramIndicator.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
        assertThat(testProgramIndicator.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testProgramIndicator.getDimensionItemType()).isEqualTo(UPDATED_DIMENSION_ITEM_TYPE);
        assertThat(testProgramIndicator.getExpression()).isEqualTo(UPDATED_EXPRESSION);
        assertThat(testProgramIndicator.getFilter()).isEqualTo(UPDATED_FILTER);
        assertThat(testProgramIndicator.getAnalyticsType()).isEqualTo(UPDATED_ANALYTICS_TYPE);
        assertThat(testProgramIndicator.getDimensionItem()).isEqualTo(UPDATED_DIMENSION_ITEM);
        assertThat(testProgramIndicator.getDisplayShortName()).isEqualTo(UPDATED_DISPLAY_SHORT_NAME);
        assertThat(testProgramIndicator.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testProgramIndicator.getDisplayFormName()).isEqualTo(UPDATED_DISPLAY_FORM_NAME);
        assertThat(testProgramIndicator.getTrack()).isEqualTo(UPDATED_TRACK);
    }

    @Test
    @Transactional
    void patchNonExistingProgramIndicator() throws Exception {
        int databaseSizeBeforeUpdate = programIndicatorRepository.findAll().size();
        programIndicator.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgramIndicatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, programIndicator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(programIndicator))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProgramIndicator in the database
        List<ProgramIndicator> programIndicatorList = programIndicatorRepository.findAll();
        assertThat(programIndicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProgramIndicator() throws Exception {
        int databaseSizeBeforeUpdate = programIndicatorRepository.findAll().size();
        programIndicator.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramIndicatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(programIndicator))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProgramIndicator in the database
        List<ProgramIndicator> programIndicatorList = programIndicatorRepository.findAll();
        assertThat(programIndicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProgramIndicator() throws Exception {
        int databaseSizeBeforeUpdate = programIndicatorRepository.findAll().size();
        programIndicator.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramIndicatorMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(programIndicator))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProgramIndicator in the database
        List<ProgramIndicator> programIndicatorList = programIndicatorRepository.findAll();
        assertThat(programIndicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProgramIndicator() throws Exception {
        // Initialize the database
        programIndicator.setId(UUID.randomUUID().toString());
        programIndicatorRepository.saveAndFlush(programIndicator);

        int databaseSizeBeforeDelete = programIndicatorRepository.findAll().size();

        // Delete the programIndicator
        restProgramIndicatorMockMvc
            .perform(delete(ENTITY_API_URL_ID, programIndicator.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProgramIndicator> programIndicatorList = programIndicatorRepository.findAll();
        assertThat(programIndicatorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
