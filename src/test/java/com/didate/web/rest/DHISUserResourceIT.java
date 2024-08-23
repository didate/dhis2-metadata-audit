package com.didate.web.rest;

import static com.didate.domain.DHISUserAsserts.*;
import static com.didate.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.didate.IntegrationTest;
import com.didate.domain.DHISUser;
import com.didate.domain.enumeration.TypeTrack;
import com.didate.repository.DHISUserRepository;
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
    private ObjectMapper om;

    @Autowired
    private DHISUserRepository dHISUserRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDHISUserMockMvc;

    private DHISUser dHISUser;

    private DHISUser insertedDHISUser;

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

    @AfterEach
    public void cleanup() {
        if (insertedDHISUser != null) {
            dHISUserRepository.delete(insertedDHISUser);
            insertedDHISUser = null;
        }
    }

    @Test
    @Transactional
    void createDHISUser() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the DHISUser
        var returnedDHISUser = om.readValue(
            restDHISUserMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dHISUser)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DHISUser.class
        );

        // Validate the DHISUser in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDHISUserUpdatableFieldsEquals(returnedDHISUser, getPersistedDHISUser(returnedDHISUser));

        insertedDHISUser = returnedDHISUser;
    }

    @Test
    @Transactional
    void createDHISUserWithExistingId() throws Exception {
        // Create the DHISUser with an existing ID
        dHISUser.setId("existing_id");

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDHISUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dHISUser)))
            .andExpect(status().isBadRequest());

        // Validate the DHISUser in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        dHISUser.setName(null);

        // Create the DHISUser, which fails.

        restDHISUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dHISUser)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUsernameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        dHISUser.setUsername(null);

        // Create the DHISUser, which fails.

        restDHISUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dHISUser)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTrackIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        dHISUser.setTrack(null);

        // Create the DHISUser, which fails.

        restDHISUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dHISUser)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDHISUsers() throws Exception {
        // Initialize the database
        insertedDHISUser = dHISUserRepository.saveAndFlush(dHISUser);

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
        insertedDHISUser = dHISUserRepository.saveAndFlush(dHISUser);

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
        insertedDHISUser = dHISUserRepository.saveAndFlush(dHISUser);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the dHISUser
        DHISUser updatedDHISUser = dHISUserRepository.findById(dHISUser.getId()).orElseThrow();
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
                    .content(om.writeValueAsBytes(updatedDHISUser))
            )
            .andExpect(status().isOk());

        // Validate the DHISUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDHISUserToMatchAllProperties(updatedDHISUser);
    }

    @Test
    @Transactional
    void putNonExistingDHISUser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dHISUser.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDHISUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dHISUser.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dHISUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the DHISUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDHISUser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dHISUser.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDHISUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(dHISUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the DHISUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDHISUser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dHISUser.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDHISUserMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dHISUser)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DHISUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDHISUserWithPatch() throws Exception {
        // Initialize the database
        insertedDHISUser = dHISUserRepository.saveAndFlush(dHISUser);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the dHISUser using partial update
        DHISUser partialUpdatedDHISUser = new DHISUser();
        partialUpdatedDHISUser.setId(dHISUser.getId());

        partialUpdatedDHISUser
            .displayName(UPDATED_DISPLAY_NAME)
            .lastLogin(UPDATED_LAST_LOGIN)
            .email(UPDATED_EMAIL)
            .disabled(UPDATED_DISABLED)
            .passwordLastUpdated(UPDATED_PASSWORD_LAST_UPDATED)
            .track(UPDATED_TRACK);

        restDHISUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDHISUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDHISUser))
            )
            .andExpect(status().isOk());

        // Validate the DHISUser in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDHISUserUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedDHISUser, dHISUser), getPersistedDHISUser(dHISUser));
    }

    @Test
    @Transactional
    void fullUpdateDHISUserWithPatch() throws Exception {
        // Initialize the database
        insertedDHISUser = dHISUserRepository.saveAndFlush(dHISUser);

        long databaseSizeBeforeUpdate = getRepositoryCount();

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
                    .content(om.writeValueAsBytes(partialUpdatedDHISUser))
            )
            .andExpect(status().isOk());

        // Validate the DHISUser in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDHISUserUpdatableFieldsEquals(partialUpdatedDHISUser, getPersistedDHISUser(partialUpdatedDHISUser));
    }

    @Test
    @Transactional
    void patchNonExistingDHISUser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dHISUser.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDHISUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dHISUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(dHISUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the DHISUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDHISUser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dHISUser.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDHISUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(dHISUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the DHISUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDHISUser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dHISUser.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDHISUserMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(dHISUser)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DHISUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDHISUser() throws Exception {
        // Initialize the database
        insertedDHISUser = dHISUserRepository.saveAndFlush(dHISUser);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the dHISUser
        restDHISUserMockMvc
            .perform(delete(ENTITY_API_URL_ID, dHISUser.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return dHISUserRepository.count();
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

    protected DHISUser getPersistedDHISUser(DHISUser dHISUser) {
        return dHISUserRepository.findById(dHISUser.getId()).orElseThrow();
    }

    protected void assertPersistedDHISUserToMatchAllProperties(DHISUser expectedDHISUser) {
        assertDHISUserAllPropertiesEquals(expectedDHISUser, getPersistedDHISUser(expectedDHISUser));
    }

    protected void assertPersistedDHISUserToMatchUpdatableProperties(DHISUser expectedDHISUser) {
        assertDHISUserAllUpdatablePropertiesEquals(expectedDHISUser, getPersistedDHISUser(expectedDHISUser));
    }
}
