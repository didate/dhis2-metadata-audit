package com.didate.web.rest;

import static com.didate.domain.OptionGroupAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.didate.IntegrationTest;
import com.didate.domain.OptionGroup;
import com.didate.repository.OptionGroupRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link OptionGroupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OptionGroupResourceIT {

    private static final String ENTITY_API_URL = "/api/option-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OptionGroupRepository optionGroupRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOptionGroupMockMvc;

    private OptionGroup optionGroup;

    private OptionGroup insertedOptionGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OptionGroup createEntity(EntityManager em) {
        OptionGroup optionGroup = new OptionGroup();
        return optionGroup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OptionGroup createUpdatedEntity(EntityManager em) {
        OptionGroup optionGroup = new OptionGroup();
        return optionGroup;
    }

    @BeforeEach
    public void initTest() {
        optionGroup = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedOptionGroup != null) {
            optionGroupRepository.delete(insertedOptionGroup);
            insertedOptionGroup = null;
        }
    }

    @Test
    @Transactional
    void createOptionGroup() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the OptionGroup
        var returnedOptionGroup = om.readValue(
            restOptionGroupMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(optionGroup)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            OptionGroup.class
        );

        // Validate the OptionGroup in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOptionGroupUpdatableFieldsEquals(returnedOptionGroup, getPersistedOptionGroup(returnedOptionGroup));

        insertedOptionGroup = returnedOptionGroup;
    }

    @Test
    @Transactional
    void createOptionGroupWithExistingId() throws Exception {
        // Create the OptionGroup with an existing ID
        optionGroup.setId("existing_id");

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOptionGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(optionGroup)))
            .andExpect(status().isBadRequest());

        // Validate the OptionGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOptionGroups() throws Exception {
        // Initialize the database
        insertedOptionGroup = optionGroupRepository.saveAndFlush(optionGroup);

        // Get all the optionGroupList
        restOptionGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(optionGroup.getId())));
    }

    @Test
    @Transactional
    void getOptionGroup() throws Exception {
        // Initialize the database
        insertedOptionGroup = optionGroupRepository.saveAndFlush(optionGroup);

        // Get the optionGroup
        restOptionGroupMockMvc
            .perform(get(ENTITY_API_URL_ID, optionGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(optionGroup.getId()));
    }

    @Test
    @Transactional
    void getNonExistingOptionGroup() throws Exception {
        // Get the optionGroup
        restOptionGroupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteOptionGroup() throws Exception {
        // Initialize the database
        insertedOptionGroup = optionGroupRepository.saveAndFlush(optionGroup);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the optionGroup
        restOptionGroupMockMvc
            .perform(delete(ENTITY_API_URL_ID, optionGroup.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return optionGroupRepository.count();
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

    protected OptionGroup getPersistedOptionGroup(OptionGroup optionGroup) {
        return optionGroupRepository.findById(optionGroup.getId()).orElseThrow();
    }

    protected void assertPersistedOptionGroupToMatchAllProperties(OptionGroup expectedOptionGroup) {
        assertOptionGroupAllPropertiesEquals(expectedOptionGroup, getPersistedOptionGroup(expectedOptionGroup));
    }

    protected void assertPersistedOptionGroupToMatchUpdatableProperties(OptionGroup expectedOptionGroup) {
        assertOptionGroupAllUpdatablePropertiesEquals(expectedOptionGroup, getPersistedOptionGroup(expectedOptionGroup));
    }
}
