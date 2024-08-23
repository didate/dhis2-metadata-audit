package com.didate.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.didate.IntegrationTest;
import com.didate.domain.PersonNotifier;
import com.didate.repository.PersonNotifierRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
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
 * Integration tests for the {@link PersonNotifierResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PersonNotifierResourceIT {

    private static final String DEFAULT_PERSON_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PERSON_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PERSON_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PERSON_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_PERSON_EMAIL = "F'6@D+ii.e7Y";
    private static final String UPDATED_PERSON_EMAIL = "^tKn^Y@ewCO.|dbz";

    private static final String DEFAULT_PERSON_ORGANIZATION = "AAAAAAAAAA";
    private static final String UPDATED_PERSON_ORGANIZATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/person-notifiers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PersonNotifierRepository personNotifierRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonNotifierMockMvc;

    private PersonNotifier personNotifier;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonNotifier createEntity(EntityManager em) {
        PersonNotifier personNotifier = new PersonNotifier()
            .personName(DEFAULT_PERSON_NAME)
            .personPhone(DEFAULT_PERSON_PHONE)
            .personEmail(DEFAULT_PERSON_EMAIL)
            .personOrganization(DEFAULT_PERSON_ORGANIZATION);
        return personNotifier;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonNotifier createUpdatedEntity(EntityManager em) {
        PersonNotifier personNotifier = new PersonNotifier()
            .personName(UPDATED_PERSON_NAME)
            .personPhone(UPDATED_PERSON_PHONE)
            .personEmail(UPDATED_PERSON_EMAIL)
            .personOrganization(UPDATED_PERSON_ORGANIZATION);
        return personNotifier;
    }

    @BeforeEach
    public void initTest() {
        personNotifier = createEntity(em);
    }

    @Test
    @Transactional
    void createPersonNotifier() throws Exception {
        int databaseSizeBeforeCreate = personNotifierRepository.findAll().size();
        // Create the PersonNotifier
        restPersonNotifierMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personNotifier))
            )
            .andExpect(status().isCreated());

        // Validate the PersonNotifier in the database
        List<PersonNotifier> personNotifierList = personNotifierRepository.findAll();
        assertThat(personNotifierList).hasSize(databaseSizeBeforeCreate + 1);
        PersonNotifier testPersonNotifier = personNotifierList.get(personNotifierList.size() - 1);
        assertThat(testPersonNotifier.getPersonName()).isEqualTo(DEFAULT_PERSON_NAME);
        assertThat(testPersonNotifier.getPersonPhone()).isEqualTo(DEFAULT_PERSON_PHONE);
        assertThat(testPersonNotifier.getPersonEmail()).isEqualTo(DEFAULT_PERSON_EMAIL);
        assertThat(testPersonNotifier.getPersonOrganization()).isEqualTo(DEFAULT_PERSON_ORGANIZATION);
    }

    @Test
    @Transactional
    void createPersonNotifierWithExistingId() throws Exception {
        // Create the PersonNotifier with an existing ID
        personNotifier.setId(1L);

        int databaseSizeBeforeCreate = personNotifierRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonNotifierMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personNotifier))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonNotifier in the database
        List<PersonNotifier> personNotifierList = personNotifierRepository.findAll();
        assertThat(personNotifierList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPersonNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = personNotifierRepository.findAll().size();
        // set the field null
        personNotifier.setPersonName(null);

        // Create the PersonNotifier, which fails.

        restPersonNotifierMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personNotifier))
            )
            .andExpect(status().isBadRequest());

        List<PersonNotifier> personNotifierList = personNotifierRepository.findAll();
        assertThat(personNotifierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPersonPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = personNotifierRepository.findAll().size();
        // set the field null
        personNotifier.setPersonPhone(null);

        // Create the PersonNotifier, which fails.

        restPersonNotifierMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personNotifier))
            )
            .andExpect(status().isBadRequest());

        List<PersonNotifier> personNotifierList = personNotifierRepository.findAll();
        assertThat(personNotifierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPersonEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = personNotifierRepository.findAll().size();
        // set the field null
        personNotifier.setPersonEmail(null);

        // Create the PersonNotifier, which fails.

        restPersonNotifierMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personNotifier))
            )
            .andExpect(status().isBadRequest());

        List<PersonNotifier> personNotifierList = personNotifierRepository.findAll();
        assertThat(personNotifierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPersonOrganizationIsRequired() throws Exception {
        int databaseSizeBeforeTest = personNotifierRepository.findAll().size();
        // set the field null
        personNotifier.setPersonOrganization(null);

        // Create the PersonNotifier, which fails.

        restPersonNotifierMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personNotifier))
            )
            .andExpect(status().isBadRequest());

        List<PersonNotifier> personNotifierList = personNotifierRepository.findAll();
        assertThat(personNotifierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPersonNotifiers() throws Exception {
        // Initialize the database
        personNotifierRepository.saveAndFlush(personNotifier);

        // Get all the personNotifierList
        restPersonNotifierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personNotifier.getId().intValue())))
            .andExpect(jsonPath("$.[*].personName").value(hasItem(DEFAULT_PERSON_NAME)))
            .andExpect(jsonPath("$.[*].personPhone").value(hasItem(DEFAULT_PERSON_PHONE)))
            .andExpect(jsonPath("$.[*].personEmail").value(hasItem(DEFAULT_PERSON_EMAIL)))
            .andExpect(jsonPath("$.[*].personOrganization").value(hasItem(DEFAULT_PERSON_ORGANIZATION)));
    }

    @Test
    @Transactional
    void getPersonNotifier() throws Exception {
        // Initialize the database
        personNotifierRepository.saveAndFlush(personNotifier);

        // Get the personNotifier
        restPersonNotifierMockMvc
            .perform(get(ENTITY_API_URL_ID, personNotifier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(personNotifier.getId().intValue()))
            .andExpect(jsonPath("$.personName").value(DEFAULT_PERSON_NAME))
            .andExpect(jsonPath("$.personPhone").value(DEFAULT_PERSON_PHONE))
            .andExpect(jsonPath("$.personEmail").value(DEFAULT_PERSON_EMAIL))
            .andExpect(jsonPath("$.personOrganization").value(DEFAULT_PERSON_ORGANIZATION));
    }

    @Test
    @Transactional
    void getNonExistingPersonNotifier() throws Exception {
        // Get the personNotifier
        restPersonNotifierMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPersonNotifier() throws Exception {
        // Initialize the database
        personNotifierRepository.saveAndFlush(personNotifier);

        int databaseSizeBeforeUpdate = personNotifierRepository.findAll().size();

        // Update the personNotifier
        PersonNotifier updatedPersonNotifier = personNotifierRepository.findById(personNotifier.getId()).get();
        // Disconnect from session so that the updates on updatedPersonNotifier are not directly saved in db
        em.detach(updatedPersonNotifier);
        updatedPersonNotifier
            .personName(UPDATED_PERSON_NAME)
            .personPhone(UPDATED_PERSON_PHONE)
            .personEmail(UPDATED_PERSON_EMAIL)
            .personOrganization(UPDATED_PERSON_ORGANIZATION);

        restPersonNotifierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPersonNotifier.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPersonNotifier))
            )
            .andExpect(status().isOk());

        // Validate the PersonNotifier in the database
        List<PersonNotifier> personNotifierList = personNotifierRepository.findAll();
        assertThat(personNotifierList).hasSize(databaseSizeBeforeUpdate);
        PersonNotifier testPersonNotifier = personNotifierList.get(personNotifierList.size() - 1);
        assertThat(testPersonNotifier.getPersonName()).isEqualTo(UPDATED_PERSON_NAME);
        assertThat(testPersonNotifier.getPersonPhone()).isEqualTo(UPDATED_PERSON_PHONE);
        assertThat(testPersonNotifier.getPersonEmail()).isEqualTo(UPDATED_PERSON_EMAIL);
        assertThat(testPersonNotifier.getPersonOrganization()).isEqualTo(UPDATED_PERSON_ORGANIZATION);
    }

    @Test
    @Transactional
    void putNonExistingPersonNotifier() throws Exception {
        int databaseSizeBeforeUpdate = personNotifierRepository.findAll().size();
        personNotifier.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonNotifierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personNotifier.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personNotifier))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonNotifier in the database
        List<PersonNotifier> personNotifierList = personNotifierRepository.findAll();
        assertThat(personNotifierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPersonNotifier() throws Exception {
        int databaseSizeBeforeUpdate = personNotifierRepository.findAll().size();
        personNotifier.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonNotifierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personNotifier))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonNotifier in the database
        List<PersonNotifier> personNotifierList = personNotifierRepository.findAll();
        assertThat(personNotifierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPersonNotifier() throws Exception {
        int databaseSizeBeforeUpdate = personNotifierRepository.findAll().size();
        personNotifier.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonNotifierMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personNotifier)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PersonNotifier in the database
        List<PersonNotifier> personNotifierList = personNotifierRepository.findAll();
        assertThat(personNotifierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePersonNotifierWithPatch() throws Exception {
        // Initialize the database
        personNotifierRepository.saveAndFlush(personNotifier);

        int databaseSizeBeforeUpdate = personNotifierRepository.findAll().size();

        // Update the personNotifier using partial update
        PersonNotifier partialUpdatedPersonNotifier = new PersonNotifier();
        partialUpdatedPersonNotifier.setId(personNotifier.getId());

        partialUpdatedPersonNotifier.personEmail(UPDATED_PERSON_EMAIL);

        restPersonNotifierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonNotifier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonNotifier))
            )
            .andExpect(status().isOk());

        // Validate the PersonNotifier in the database
        List<PersonNotifier> personNotifierList = personNotifierRepository.findAll();
        assertThat(personNotifierList).hasSize(databaseSizeBeforeUpdate);
        PersonNotifier testPersonNotifier = personNotifierList.get(personNotifierList.size() - 1);
        assertThat(testPersonNotifier.getPersonName()).isEqualTo(DEFAULT_PERSON_NAME);
        assertThat(testPersonNotifier.getPersonPhone()).isEqualTo(DEFAULT_PERSON_PHONE);
        assertThat(testPersonNotifier.getPersonEmail()).isEqualTo(UPDATED_PERSON_EMAIL);
        assertThat(testPersonNotifier.getPersonOrganization()).isEqualTo(DEFAULT_PERSON_ORGANIZATION);
    }

    @Test
    @Transactional
    void fullUpdatePersonNotifierWithPatch() throws Exception {
        // Initialize the database
        personNotifierRepository.saveAndFlush(personNotifier);

        int databaseSizeBeforeUpdate = personNotifierRepository.findAll().size();

        // Update the personNotifier using partial update
        PersonNotifier partialUpdatedPersonNotifier = new PersonNotifier();
        partialUpdatedPersonNotifier.setId(personNotifier.getId());

        partialUpdatedPersonNotifier
            .personName(UPDATED_PERSON_NAME)
            .personPhone(UPDATED_PERSON_PHONE)
            .personEmail(UPDATED_PERSON_EMAIL)
            .personOrganization(UPDATED_PERSON_ORGANIZATION);

        restPersonNotifierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonNotifier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonNotifier))
            )
            .andExpect(status().isOk());

        // Validate the PersonNotifier in the database
        List<PersonNotifier> personNotifierList = personNotifierRepository.findAll();
        assertThat(personNotifierList).hasSize(databaseSizeBeforeUpdate);
        PersonNotifier testPersonNotifier = personNotifierList.get(personNotifierList.size() - 1);
        assertThat(testPersonNotifier.getPersonName()).isEqualTo(UPDATED_PERSON_NAME);
        assertThat(testPersonNotifier.getPersonPhone()).isEqualTo(UPDATED_PERSON_PHONE);
        assertThat(testPersonNotifier.getPersonEmail()).isEqualTo(UPDATED_PERSON_EMAIL);
        assertThat(testPersonNotifier.getPersonOrganization()).isEqualTo(UPDATED_PERSON_ORGANIZATION);
    }

    @Test
    @Transactional
    void patchNonExistingPersonNotifier() throws Exception {
        int databaseSizeBeforeUpdate = personNotifierRepository.findAll().size();
        personNotifier.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonNotifierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, personNotifier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personNotifier))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonNotifier in the database
        List<PersonNotifier> personNotifierList = personNotifierRepository.findAll();
        assertThat(personNotifierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPersonNotifier() throws Exception {
        int databaseSizeBeforeUpdate = personNotifierRepository.findAll().size();
        personNotifier.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonNotifierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personNotifier))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonNotifier in the database
        List<PersonNotifier> personNotifierList = personNotifierRepository.findAll();
        assertThat(personNotifierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPersonNotifier() throws Exception {
        int databaseSizeBeforeUpdate = personNotifierRepository.findAll().size();
        personNotifier.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonNotifierMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(personNotifier))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PersonNotifier in the database
        List<PersonNotifier> personNotifierList = personNotifierRepository.findAll();
        assertThat(personNotifierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePersonNotifier() throws Exception {
        // Initialize the database
        personNotifierRepository.saveAndFlush(personNotifier);

        int databaseSizeBeforeDelete = personNotifierRepository.findAll().size();

        // Delete the personNotifier
        restPersonNotifierMockMvc
            .perform(delete(ENTITY_API_URL_ID, personNotifier.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PersonNotifier> personNotifierList = personNotifierRepository.findAll();
        assertThat(personNotifierList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
