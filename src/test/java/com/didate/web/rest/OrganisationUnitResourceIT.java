package com.didate.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.didate.IntegrationTest;
import com.didate.domain.DHISUser;
import com.didate.domain.OrganisationUnit;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.repository.OrganisationUnitRepository;
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
 * Integration tests for the {@link OrganisationUnitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrganisationUnitResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    private static final Instant DEFAULT_OPENING_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_OPENING_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_LEVEL = 1;
    private static final Integer UPDATED_LEVEL = 2;

    private static final TypeTrack DEFAULT_TRACK = TypeTrack.NEW;
    private static final TypeTrack UPDATED_TRACK = TypeTrack.UPDATE;

    private static final String ENTITY_API_URL = "/api/organisation-units";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private OrganisationUnitRepository organisationUnitRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrganisationUnitMockMvc;

    private OrganisationUnit organisationUnit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganisationUnit createEntity(EntityManager em) {
        OrganisationUnit organisationUnit = new OrganisationUnit()
            .name(DEFAULT_NAME)
            .created(DEFAULT_CREATED)
            .lastUpdated(DEFAULT_LAST_UPDATED)
            .path(DEFAULT_PATH)
            .openingDate(DEFAULT_OPENING_DATE)
            .level(DEFAULT_LEVEL)
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
        organisationUnit.setCreatedBy(dHISUser);
        // Add required entity
        organisationUnit.setLastUpdatedBy(dHISUser);
        return organisationUnit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganisationUnit createUpdatedEntity(EntityManager em) {
        OrganisationUnit organisationUnit = new OrganisationUnit()
            .name(UPDATED_NAME)
            .created(UPDATED_CREATED)
            .lastUpdated(UPDATED_LAST_UPDATED)
            .path(UPDATED_PATH)
            .openingDate(UPDATED_OPENING_DATE)
            .level(UPDATED_LEVEL)
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
        organisationUnit.setCreatedBy(dHISUser);
        // Add required entity
        organisationUnit.setLastUpdatedBy(dHISUser);
        return organisationUnit;
    }

    @BeforeEach
    public void initTest() {
        organisationUnit = createEntity(em);
    }

    @Test
    @Transactional
    void createOrganisationUnit() throws Exception {
        int databaseSizeBeforeCreate = organisationUnitRepository.findAll().size();
        // Create the OrganisationUnit
        restOrganisationUnitMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organisationUnit))
            )
            .andExpect(status().isCreated());

        // Validate the OrganisationUnit in the database
        List<OrganisationUnit> organisationUnitList = organisationUnitRepository.findAll();
        assertThat(organisationUnitList).hasSize(databaseSizeBeforeCreate + 1);
        OrganisationUnit testOrganisationUnit = organisationUnitList.get(organisationUnitList.size() - 1);
        assertThat(testOrganisationUnit.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrganisationUnit.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testOrganisationUnit.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
        assertThat(testOrganisationUnit.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testOrganisationUnit.getOpeningDate()).isEqualTo(DEFAULT_OPENING_DATE);
        assertThat(testOrganisationUnit.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testOrganisationUnit.getTrack()).isEqualTo(DEFAULT_TRACK);
    }

    @Test
    @Transactional
    void createOrganisationUnitWithExistingId() throws Exception {
        // Create the OrganisationUnit with an existing ID
        organisationUnit.setId("existing_id");

        int databaseSizeBeforeCreate = organisationUnitRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganisationUnitMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organisationUnit))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganisationUnit in the database
        List<OrganisationUnit> organisationUnitList = organisationUnitRepository.findAll();
        assertThat(organisationUnitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = organisationUnitRepository.findAll().size();
        // set the field null
        organisationUnit.setName(null);

        // Create the OrganisationUnit, which fails.

        restOrganisationUnitMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organisationUnit))
            )
            .andExpect(status().isBadRequest());

        List<OrganisationUnit> organisationUnitList = organisationUnitRepository.findAll();
        assertThat(organisationUnitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = organisationUnitRepository.findAll().size();
        // set the field null
        organisationUnit.setCreated(null);

        // Create the OrganisationUnit, which fails.

        restOrganisationUnitMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organisationUnit))
            )
            .andExpect(status().isBadRequest());

        List<OrganisationUnit> organisationUnitList = organisationUnitRepository.findAll();
        assertThat(organisationUnitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastUpdatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = organisationUnitRepository.findAll().size();
        // set the field null
        organisationUnit.setLastUpdated(null);

        // Create the OrganisationUnit, which fails.

        restOrganisationUnitMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organisationUnit))
            )
            .andExpect(status().isBadRequest());

        List<OrganisationUnit> organisationUnitList = organisationUnitRepository.findAll();
        assertThat(organisationUnitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOpeningDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = organisationUnitRepository.findAll().size();
        // set the field null
        organisationUnit.setOpeningDate(null);

        // Create the OrganisationUnit, which fails.

        restOrganisationUnitMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organisationUnit))
            )
            .andExpect(status().isBadRequest());

        List<OrganisationUnit> organisationUnitList = organisationUnitRepository.findAll();
        assertThat(organisationUnitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTrackIsRequired() throws Exception {
        int databaseSizeBeforeTest = organisationUnitRepository.findAll().size();
        // set the field null
        organisationUnit.setTrack(null);

        // Create the OrganisationUnit, which fails.

        restOrganisationUnitMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organisationUnit))
            )
            .andExpect(status().isBadRequest());

        List<OrganisationUnit> organisationUnitList = organisationUnitRepository.findAll();
        assertThat(organisationUnitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOrganisationUnits() throws Exception {
        // Initialize the database
        organisationUnit.setId(UUID.randomUUID().toString());
        organisationUnitRepository.saveAndFlush(organisationUnit);

        // Get all the organisationUnitList
        restOrganisationUnitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organisationUnit.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH)))
            .andExpect(jsonPath("$.[*].openingDate").value(hasItem(DEFAULT_OPENING_DATE.toString())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].track").value(hasItem(DEFAULT_TRACK.toString())));
    }

    @Test
    @Transactional
    void getOrganisationUnit() throws Exception {
        // Initialize the database
        organisationUnit.setId(UUID.randomUUID().toString());
        organisationUnitRepository.saveAndFlush(organisationUnit);

        // Get the organisationUnit
        restOrganisationUnitMockMvc
            .perform(get(ENTITY_API_URL_ID, organisationUnit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(organisationUnit.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.lastUpdated").value(DEFAULT_LAST_UPDATED.toString()))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH))
            .andExpect(jsonPath("$.openingDate").value(DEFAULT_OPENING_DATE.toString()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL))
            .andExpect(jsonPath("$.track").value(DEFAULT_TRACK.toString()));
    }

    @Test
    @Transactional
    void getNonExistingOrganisationUnit() throws Exception {
        // Get the organisationUnit
        restOrganisationUnitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrganisationUnit() throws Exception {
        // Initialize the database
        organisationUnit.setId(UUID.randomUUID().toString());
        organisationUnitRepository.saveAndFlush(organisationUnit);

        int databaseSizeBeforeUpdate = organisationUnitRepository.findAll().size();

        // Update the organisationUnit
        OrganisationUnit updatedOrganisationUnit = organisationUnitRepository.findById(organisationUnit.getId()).get();
        // Disconnect from session so that the updates on updatedOrganisationUnit are not directly saved in db
        em.detach(updatedOrganisationUnit);
        updatedOrganisationUnit
            .name(UPDATED_NAME)
            .created(UPDATED_CREATED)
            .lastUpdated(UPDATED_LAST_UPDATED)
            .path(UPDATED_PATH)
            .openingDate(UPDATED_OPENING_DATE)
            .level(UPDATED_LEVEL)
            .track(UPDATED_TRACK);

        restOrganisationUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOrganisationUnit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOrganisationUnit))
            )
            .andExpect(status().isOk());

        // Validate the OrganisationUnit in the database
        List<OrganisationUnit> organisationUnitList = organisationUnitRepository.findAll();
        assertThat(organisationUnitList).hasSize(databaseSizeBeforeUpdate);
        OrganisationUnit testOrganisationUnit = organisationUnitList.get(organisationUnitList.size() - 1);
        assertThat(testOrganisationUnit.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrganisationUnit.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testOrganisationUnit.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
        assertThat(testOrganisationUnit.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testOrganisationUnit.getOpeningDate()).isEqualTo(UPDATED_OPENING_DATE);
        assertThat(testOrganisationUnit.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testOrganisationUnit.getTrack()).isEqualTo(UPDATED_TRACK);
    }

    @Test
    @Transactional
    void putNonExistingOrganisationUnit() throws Exception {
        int databaseSizeBeforeUpdate = organisationUnitRepository.findAll().size();
        organisationUnit.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganisationUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organisationUnit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organisationUnit))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganisationUnit in the database
        List<OrganisationUnit> organisationUnitList = organisationUnitRepository.findAll();
        assertThat(organisationUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrganisationUnit() throws Exception {
        int databaseSizeBeforeUpdate = organisationUnitRepository.findAll().size();
        organisationUnit.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganisationUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organisationUnit))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganisationUnit in the database
        List<OrganisationUnit> organisationUnitList = organisationUnitRepository.findAll();
        assertThat(organisationUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrganisationUnit() throws Exception {
        int databaseSizeBeforeUpdate = organisationUnitRepository.findAll().size();
        organisationUnit.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganisationUnitMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organisationUnit))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrganisationUnit in the database
        List<OrganisationUnit> organisationUnitList = organisationUnitRepository.findAll();
        assertThat(organisationUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrganisationUnitWithPatch() throws Exception {
        // Initialize the database
        organisationUnit.setId(UUID.randomUUID().toString());
        organisationUnitRepository.saveAndFlush(organisationUnit);

        int databaseSizeBeforeUpdate = organisationUnitRepository.findAll().size();

        // Update the organisationUnit using partial update
        OrganisationUnit partialUpdatedOrganisationUnit = new OrganisationUnit();
        partialUpdatedOrganisationUnit.setId(organisationUnit.getId());

        partialUpdatedOrganisationUnit.name(UPDATED_NAME).lastUpdated(UPDATED_LAST_UPDATED).level(UPDATED_LEVEL);

        restOrganisationUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganisationUnit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrganisationUnit))
            )
            .andExpect(status().isOk());

        // Validate the OrganisationUnit in the database
        List<OrganisationUnit> organisationUnitList = organisationUnitRepository.findAll();
        assertThat(organisationUnitList).hasSize(databaseSizeBeforeUpdate);
        OrganisationUnit testOrganisationUnit = organisationUnitList.get(organisationUnitList.size() - 1);
        assertThat(testOrganisationUnit.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrganisationUnit.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testOrganisationUnit.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
        assertThat(testOrganisationUnit.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testOrganisationUnit.getOpeningDate()).isEqualTo(DEFAULT_OPENING_DATE);
        assertThat(testOrganisationUnit.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testOrganisationUnit.getTrack()).isEqualTo(DEFAULT_TRACK);
    }

    @Test
    @Transactional
    void fullUpdateOrganisationUnitWithPatch() throws Exception {
        // Initialize the database
        organisationUnit.setId(UUID.randomUUID().toString());
        organisationUnitRepository.saveAndFlush(organisationUnit);

        int databaseSizeBeforeUpdate = organisationUnitRepository.findAll().size();

        // Update the organisationUnit using partial update
        OrganisationUnit partialUpdatedOrganisationUnit = new OrganisationUnit();
        partialUpdatedOrganisationUnit.setId(organisationUnit.getId());

        partialUpdatedOrganisationUnit
            .name(UPDATED_NAME)
            .created(UPDATED_CREATED)
            .lastUpdated(UPDATED_LAST_UPDATED)
            .path(UPDATED_PATH)
            .openingDate(UPDATED_OPENING_DATE)
            .level(UPDATED_LEVEL)
            .track(UPDATED_TRACK);

        restOrganisationUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganisationUnit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrganisationUnit))
            )
            .andExpect(status().isOk());

        // Validate the OrganisationUnit in the database
        List<OrganisationUnit> organisationUnitList = organisationUnitRepository.findAll();
        assertThat(organisationUnitList).hasSize(databaseSizeBeforeUpdate);
        OrganisationUnit testOrganisationUnit = organisationUnitList.get(organisationUnitList.size() - 1);
        assertThat(testOrganisationUnit.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrganisationUnit.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testOrganisationUnit.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
        assertThat(testOrganisationUnit.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testOrganisationUnit.getOpeningDate()).isEqualTo(UPDATED_OPENING_DATE);
        assertThat(testOrganisationUnit.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testOrganisationUnit.getTrack()).isEqualTo(UPDATED_TRACK);
    }

    @Test
    @Transactional
    void patchNonExistingOrganisationUnit() throws Exception {
        int databaseSizeBeforeUpdate = organisationUnitRepository.findAll().size();
        organisationUnit.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganisationUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, organisationUnit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organisationUnit))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganisationUnit in the database
        List<OrganisationUnit> organisationUnitList = organisationUnitRepository.findAll();
        assertThat(organisationUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrganisationUnit() throws Exception {
        int databaseSizeBeforeUpdate = organisationUnitRepository.findAll().size();
        organisationUnit.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganisationUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organisationUnit))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganisationUnit in the database
        List<OrganisationUnit> organisationUnitList = organisationUnitRepository.findAll();
        assertThat(organisationUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrganisationUnit() throws Exception {
        int databaseSizeBeforeUpdate = organisationUnitRepository.findAll().size();
        organisationUnit.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganisationUnitMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organisationUnit))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrganisationUnit in the database
        List<OrganisationUnit> organisationUnitList = organisationUnitRepository.findAll();
        assertThat(organisationUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrganisationUnit() throws Exception {
        // Initialize the database
        organisationUnit.setId(UUID.randomUUID().toString());
        organisationUnitRepository.saveAndFlush(organisationUnit);

        int databaseSizeBeforeDelete = organisationUnitRepository.findAll().size();

        // Delete the organisationUnit
        restOrganisationUnitMockMvc
            .perform(delete(ENTITY_API_URL_ID, organisationUnit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrganisationUnit> organisationUnitList = organisationUnitRepository.findAll();
        assertThat(organisationUnitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
