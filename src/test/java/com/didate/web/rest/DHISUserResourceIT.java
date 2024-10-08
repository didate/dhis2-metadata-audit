package com.didate.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.didate.IntegrationTest;
import com.didate.domain.DHISUser;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.repository.DHISUserRepository;
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
 * Integration tests for the {@link DHISUserResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DHISUserResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_LOGIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_LOGIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DISABLED = false;
    private static final Boolean UPDATED_DISABLED = true;

    private static final Instant DEFAULT_PASSWORD_LAST_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PASSWORD_LAST_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final TypeTrack DEFAULT_TRACK = TypeTrack.NEW;
    private static final TypeTrack UPDATED_TRACK = TypeTrack.UPDATE;

    private static final String ENTITY_API_URL = "/api/dhis-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private DHISUserRepository dHISUserRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDHISUserMockMvc;

    private DHISUser dHISUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DHISUser createEntity(EntityManager em) {
        DHISUser dHISUser = new DHISUser()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .displayName(DEFAULT_DISPLAY_NAME)
            .username(DEFAULT_USERNAME)
            .lastLogin(DEFAULT_LAST_LOGIN)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .disabled(DEFAULT_DISABLED)
            .passwordLastUpdated(DEFAULT_PASSWORD_LAST_UPDATED)
            .created(DEFAULT_CREATED)
            .lastUpdated(DEFAULT_LAST_UPDATED)
            .track(DEFAULT_TRACK);
        return dHISUser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DHISUser createUpdatedEntity(EntityManager em) {
        DHISUser dHISUser = new DHISUser()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .displayName(UPDATED_DISPLAY_NAME)
            .username(UPDATED_USERNAME)
            .lastLogin(UPDATED_LAST_LOGIN)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .disabled(UPDATED_DISABLED)
            .passwordLastUpdated(UPDATED_PASSWORD_LAST_UPDATED)
            .created(UPDATED_CREATED)
            .lastUpdated(UPDATED_LAST_UPDATED)
            .track(UPDATED_TRACK);
        return dHISUser;
    }

    @BeforeEach
    public void initTest() {
        dHISUser = createEntity(em);
    }

    @Test
    @Transactional
    void createDHISUser() throws Exception {
        int databaseSizeBeforeCreate = dHISUserRepository.findAll().size();
        // Create the DHISUser
        restDHISUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dHISUser)))
            .andExpect(status().isCreated());

        // Validate the DHISUser in the database
        List<DHISUser> dHISUserList = dHISUserRepository.findAll();
        assertThat(dHISUserList).hasSize(databaseSizeBeforeCreate + 1);
        DHISUser testDHISUser = dHISUserList.get(dHISUserList.size() - 1);
        assertThat(testDHISUser.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDHISUser.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDHISUser.getDisplayName()).isEqualTo(DEFAULT_DISPLAY_NAME);
        assertThat(testDHISUser.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testDHISUser.getLastLogin()).isEqualTo(DEFAULT_LAST_LOGIN);
        assertThat(testDHISUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDHISUser.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testDHISUser.getDisabled()).isEqualTo(DEFAULT_DISABLED);
        assertThat(testDHISUser.getPasswordLastUpdated()).isEqualTo(DEFAULT_PASSWORD_LAST_UPDATED);
        assertThat(testDHISUser.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testDHISUser.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
        assertThat(testDHISUser.getTrack()).isEqualTo(DEFAULT_TRACK);
    }

    @Test
    @Transactional
    void createDHISUserWithExistingId() throws Exception {
        // Create the DHISUser with an existing ID
        dHISUser.setId("existing_id");

        int databaseSizeBeforeCreate = dHISUserRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDHISUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dHISUser)))
            .andExpect(status().isBadRequest());

        // Validate the DHISUser in the database
        List<DHISUser> dHISUserList = dHISUserRepository.findAll();
        assertThat(dHISUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dHISUserRepository.findAll().size();
        // set the field null
        dHISUser.setName(null);

        // Create the DHISUser, which fails.

        restDHISUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dHISUser)))
            .andExpect(status().isBadRequest());

        List<DHISUser> dHISUserList = dHISUserRepository.findAll();
        assertThat(dHISUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dHISUserRepository.findAll().size();
        // set the field null
        dHISUser.setUsername(null);

        // Create the DHISUser, which fails.

        restDHISUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dHISUser)))
            .andExpect(status().isBadRequest());

        List<DHISUser> dHISUserList = dHISUserRepository.findAll();
        assertThat(dHISUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTrackIsRequired() throws Exception {
        int databaseSizeBeforeTest = dHISUserRepository.findAll().size();
        // set the field null
        dHISUser.setTrack(null);

        // Create the DHISUser, which fails.

        restDHISUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dHISUser)))
            .andExpect(status().isBadRequest());

        List<DHISUser> dHISUserList = dHISUserRepository.findAll();
        assertThat(dHISUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDHISUsers() throws Exception {
        // Initialize the database
        dHISUser.setId(UUID.randomUUID().toString());
        dHISUserRepository.saveAndFlush(dHISUser);

        // Get all the dHISUserList
        restDHISUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dHISUser.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].displayName").value(hasItem(DEFAULT_DISPLAY_NAME)))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].lastLogin").value(hasItem(DEFAULT_LAST_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].disabled").value(hasItem(DEFAULT_DISABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].passwordLastUpdated").value(hasItem(DEFAULT_PASSWORD_LAST_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].track").value(hasItem(DEFAULT_TRACK.toString())));
    }

    @Test
    @Transactional
    void getDHISUser() throws Exception {
        // Initialize the database
        dHISUser.setId(UUID.randomUUID().toString());
        dHISUserRepository.saveAndFlush(dHISUser);

        // Get the dHISUser
        restDHISUserMockMvc
            .perform(get(ENTITY_API_URL_ID, dHISUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dHISUser.getId()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.displayName").value(DEFAULT_DISPLAY_NAME))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.lastLogin").value(DEFAULT_LAST_LOGIN.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.disabled").value(DEFAULT_DISABLED.booleanValue()))
            .andExpect(jsonPath("$.passwordLastUpdated").value(DEFAULT_PASSWORD_LAST_UPDATED.toString()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.lastUpdated").value(DEFAULT_LAST_UPDATED.toString()))
            .andExpect(jsonPath("$.track").value(DEFAULT_TRACK.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDHISUser() throws Exception {
        // Get the dHISUser
        restDHISUserMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDHISUser() throws Exception {
        // Initialize the database
        dHISUser.setId(UUID.randomUUID().toString());
        dHISUserRepository.saveAndFlush(dHISUser);

        int databaseSizeBeforeUpdate = dHISUserRepository.findAll().size();

        // Update the dHISUser
        DHISUser updatedDHISUser = dHISUserRepository.findById(dHISUser.getId()).get();
        // Disconnect from session so that the updates on updatedDHISUser are not directly saved in db
        em.detach(updatedDHISUser);
        updatedDHISUser
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .displayName(UPDATED_DISPLAY_NAME)
            .username(UPDATED_USERNAME)
            .lastLogin(UPDATED_LAST_LOGIN)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .disabled(UPDATED_DISABLED)
            .passwordLastUpdated(UPDATED_PASSWORD_LAST_UPDATED)
            .created(UPDATED_CREATED)
            .lastUpdated(UPDATED_LAST_UPDATED)
            .track(UPDATED_TRACK);

        restDHISUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDHISUser.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDHISUser))
            )
            .andExpect(status().isOk());

        // Validate the DHISUser in the database
        List<DHISUser> dHISUserList = dHISUserRepository.findAll();
        assertThat(dHISUserList).hasSize(databaseSizeBeforeUpdate);
        DHISUser testDHISUser = dHISUserList.get(dHISUserList.size() - 1);
        assertThat(testDHISUser.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDHISUser.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDHISUser.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testDHISUser.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testDHISUser.getLastLogin()).isEqualTo(UPDATED_LAST_LOGIN);
        assertThat(testDHISUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDHISUser.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testDHISUser.getDisabled()).isEqualTo(UPDATED_DISABLED);
        assertThat(testDHISUser.getPasswordLastUpdated()).isEqualTo(UPDATED_PASSWORD_LAST_UPDATED);
        assertThat(testDHISUser.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testDHISUser.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
        assertThat(testDHISUser.getTrack()).isEqualTo(UPDATED_TRACK);
    }

    @Test
    @Transactional
    void putNonExistingDHISUser() throws Exception {
        int databaseSizeBeforeUpdate = dHISUserRepository.findAll().size();
        dHISUser.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDHISUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dHISUser.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dHISUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the DHISUser in the database
        List<DHISUser> dHISUserList = dHISUserRepository.findAll();
        assertThat(dHISUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDHISUser() throws Exception {
        int databaseSizeBeforeUpdate = dHISUserRepository.findAll().size();
        dHISUser.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDHISUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dHISUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the DHISUser in the database
        List<DHISUser> dHISUserList = dHISUserRepository.findAll();
        assertThat(dHISUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDHISUser() throws Exception {
        int databaseSizeBeforeUpdate = dHISUserRepository.findAll().size();
        dHISUser.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDHISUserMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dHISUser)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DHISUser in the database
        List<DHISUser> dHISUserList = dHISUserRepository.findAll();
        assertThat(dHISUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDHISUserWithPatch() throws Exception {
        // Initialize the database
        dHISUser.setId(UUID.randomUUID().toString());
        dHISUserRepository.saveAndFlush(dHISUser);

        int databaseSizeBeforeUpdate = dHISUserRepository.findAll().size();

        // Update the dHISUser using partial update
        DHISUser partialUpdatedDHISUser = new DHISUser();
        partialUpdatedDHISUser.setId(dHISUser.getId());

        partialUpdatedDHISUser
            .code(UPDATED_CODE)
            .displayName(UPDATED_DISPLAY_NAME)
            .username(UPDATED_USERNAME)
            .lastLogin(UPDATED_LAST_LOGIN)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .passwordLastUpdated(UPDATED_PASSWORD_LAST_UPDATED)
            .created(UPDATED_CREATED)
            .lastUpdated(UPDATED_LAST_UPDATED)
            .track(UPDATED_TRACK);

        restDHISUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDHISUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDHISUser))
            )
            .andExpect(status().isOk());

        // Validate the DHISUser in the database
        List<DHISUser> dHISUserList = dHISUserRepository.findAll();
        assertThat(dHISUserList).hasSize(databaseSizeBeforeUpdate);
        DHISUser testDHISUser = dHISUserList.get(dHISUserList.size() - 1);
        assertThat(testDHISUser.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDHISUser.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDHISUser.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testDHISUser.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testDHISUser.getLastLogin()).isEqualTo(UPDATED_LAST_LOGIN);
        assertThat(testDHISUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDHISUser.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testDHISUser.getDisabled()).isEqualTo(DEFAULT_DISABLED);
        assertThat(testDHISUser.getPasswordLastUpdated()).isEqualTo(UPDATED_PASSWORD_LAST_UPDATED);
        assertThat(testDHISUser.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testDHISUser.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
        assertThat(testDHISUser.getTrack()).isEqualTo(UPDATED_TRACK);
    }

    @Test
    @Transactional
    void fullUpdateDHISUserWithPatch() throws Exception {
        // Initialize the database
        dHISUser.setId(UUID.randomUUID().toString());
        dHISUserRepository.saveAndFlush(dHISUser);

        int databaseSizeBeforeUpdate = dHISUserRepository.findAll().size();

        // Update the dHISUser using partial update
        DHISUser partialUpdatedDHISUser = new DHISUser();
        partialUpdatedDHISUser.setId(dHISUser.getId());

        partialUpdatedDHISUser
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .displayName(UPDATED_DISPLAY_NAME)
            .username(UPDATED_USERNAME)
            .lastLogin(UPDATED_LAST_LOGIN)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .disabled(UPDATED_DISABLED)
            .passwordLastUpdated(UPDATED_PASSWORD_LAST_UPDATED)
            .created(UPDATED_CREATED)
            .lastUpdated(UPDATED_LAST_UPDATED)
            .track(UPDATED_TRACK);

        restDHISUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDHISUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDHISUser))
            )
            .andExpect(status().isOk());

        // Validate the DHISUser in the database
        List<DHISUser> dHISUserList = dHISUserRepository.findAll();
        assertThat(dHISUserList).hasSize(databaseSizeBeforeUpdate);
        DHISUser testDHISUser = dHISUserList.get(dHISUserList.size() - 1);
        assertThat(testDHISUser.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDHISUser.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDHISUser.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testDHISUser.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testDHISUser.getLastLogin()).isEqualTo(UPDATED_LAST_LOGIN);
        assertThat(testDHISUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDHISUser.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testDHISUser.getDisabled()).isEqualTo(UPDATED_DISABLED);
        assertThat(testDHISUser.getPasswordLastUpdated()).isEqualTo(UPDATED_PASSWORD_LAST_UPDATED);
        assertThat(testDHISUser.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testDHISUser.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
        assertThat(testDHISUser.getTrack()).isEqualTo(UPDATED_TRACK);
    }

    @Test
    @Transactional
    void patchNonExistingDHISUser() throws Exception {
        int databaseSizeBeforeUpdate = dHISUserRepository.findAll().size();
        dHISUser.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDHISUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dHISUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dHISUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the DHISUser in the database
        List<DHISUser> dHISUserList = dHISUserRepository.findAll();
        assertThat(dHISUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDHISUser() throws Exception {
        int databaseSizeBeforeUpdate = dHISUserRepository.findAll().size();
        dHISUser.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDHISUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dHISUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the DHISUser in the database
        List<DHISUser> dHISUserList = dHISUserRepository.findAll();
        assertThat(dHISUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDHISUser() throws Exception {
        int databaseSizeBeforeUpdate = dHISUserRepository.findAll().size();
        dHISUser.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDHISUserMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dHISUser)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DHISUser in the database
        List<DHISUser> dHISUserList = dHISUserRepository.findAll();
        assertThat(dHISUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDHISUser() throws Exception {
        // Initialize the database
        dHISUser.setId(UUID.randomUUID().toString());
        dHISUserRepository.saveAndFlush(dHISUser);

        int databaseSizeBeforeDelete = dHISUserRepository.findAll().size();

        // Delete the dHISUser
        restDHISUserMockMvc
            .perform(delete(ENTITY_API_URL_ID, dHISUser.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DHISUser> dHISUserList = dHISUserRepository.findAll();
        assertThat(dHISUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
