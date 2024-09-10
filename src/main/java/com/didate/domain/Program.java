package com.didate.domain;

import com.didate.domain.enumeration.TypeTrack;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.domain.Persistable;

/**
 * A Program.
 */
@JsonIgnoreProperties(value = { "new" }, ignoreUnknown = true)
@Entity
@Table(name = "program")
@Audited
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Program implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "created")
    private Instant created;

    @Column(name = "last_updated")
    private Instant lastUpdated;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "description")
    private String description;

    @Column(name = "version")
    private Double version;

    @Column(name = "enrollment_date_label")
    private String enrollmentDateLabel;

    @Column(name = "incident_date_label")
    private String incidentDateLabel;

    @Column(name = "program_type")
    private String programType;

    @Column(name = "display_incident_date")
    private Boolean displayIncidentDate;

    @Column(name = "ignore_overdue_events")
    private Boolean ignoreOverdueEvents;

    //@Column(name = "user_roles")
    //private String userRoles;

    @Column(name = "only_enroll_once")
    private Boolean onlyEnrollOnce;

    //@Column(name = "notification_templates")
    //private String notificationTemplates;

    @Column(name = "select_enrollment_dates_in_future")
    private Boolean selectEnrollmentDatesInFuture;

    @Column(name = "select_incident_dates_in_future")
    private Boolean selectIncidentDatesInFuture;

    //  @Column(name = "tracked_entity_type")
    // private String trackedEntityType;

    // @Column(name = "style")
    // private String style;

    @Column(name = "skip_offline")
    private Boolean skipOffline;

    @Column(name = "display_front_page_list")
    private Boolean displayFrontPageList;

    @Column(name = "use_first_stage_during_registration")
    private Boolean useFirstStageDuringRegistration;

    @Column(name = "expiry_days")
    private Double expiryDays;

    @Column(name = "complete_events_expiry_days")
    private Double completeEventsExpiryDays;

    @Column(name = "open_days_after_co_end_date")
    private Double openDaysAfterCoEndDate;

    @Column(name = "min_attributes_required_to_search")
    private Double minAttributesRequiredToSearch;

    @Column(name = "max_tei_count_to_return")
    private Double maxTeiCountToReturn;

    @Column(name = "access_level")
    private String accessLevel;

    @Column(name = "display_enrollment_date_label")
    private String displayEnrollmentDateLabel;

    @Column(name = "display_incident_date_label")
    private String displayIncidentDateLabel;

    @Column(name = "registration")
    private Boolean registration;

    @Column(name = "without_registration")
    private Boolean withoutRegistration;

    @Column(name = "display_short_name")
    private String displayShortName;

    @Column(name = "display_description")
    private String displayDescription;

    @Column(name = "display_form_name")
    private String displayFormName;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "organisation_units_count")
    private Integer organisationUnitsCount;

    @Column(name = "program_stages_count")
    private Integer programStagesCount;

    @Column(name = "program_indicators_count")
    private Integer programIndicatorsCount;

    @Column(name = "program_tracked_entity_attributes_count")
    private Integer programTrackedEntityAttributesCount;

    @Column(name = "organisation_units_content")
    private String organisationUnitsContent;

    @Column(name = "program_stages_content")
    private String programStagesContent;

    @Column(name = "program_indicators_content")
    private String programIndicatorsContent;

    @Column(name = "program_tracked_entity_attributes_content")
    private String programTrackedEntityAttributesContent;

    @NotAudited
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "track", nullable = false)
    private TypeTrack track;

    @Transient
    private boolean isPersisted;

    @ManyToOne
    private Project project;

    @ManyToOne(optional = false)
    @NotNull
    private DHISUser createdBy;

    @ManyToOne(optional = false)
    @NotNull
    private DHISUser lastUpdatedBy;

    @ManyToOne
    private CategoryCombo categoryCombo;

    @ManyToMany
    @JoinTable(
        name = "rel_program__program_tracked_entity_attributes",
        joinColumns = @JoinColumn(name = "program_id"),
        inverseJoinColumns = @JoinColumn(name = "program_tracked_entity_attributes_id")
    )
    @JsonIgnoreProperties(value = { "project", "createdBy", "lastUpdatedBy", "optionSet", "programs" }, allowSetters = true)
    private Set<TrackedEntityAttribute> programTrackedEntityAttributes = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_program__organisation_units",
        joinColumns = @JoinColumn(name = "program_id"),
        inverseJoinColumns = @JoinColumn(name = "organisation_units_id")
    )
    @JsonIgnoreProperties(value = { "createdBy", "lastUpdatedBy", "programs", "dataSets" }, allowSetters = true)
    private Set<OrganisationUnit> organisationUnits = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_program__program_indicators",
        joinColumns = @JoinColumn(name = "program_id"),
        inverseJoinColumns = @JoinColumn(name = "program_indicators_id")
    )
    @JsonIgnoreProperties(value = { "createdBy", "lastUpdatedBy", "program", "programs" }, allowSetters = true)
    private Set<ProgramIndicator> programIndicators = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_program__program_stage",
        joinColumns = @JoinColumn(name = "program_id"),
        inverseJoinColumns = @JoinColumn(name = "program_stage_id")
    )
    @JsonIgnoreProperties(value = { "createdBy", "lastUpdatedBy", "program", "programStageDataElements", "programs" }, allowSetters = true)
    private Set<ProgramStage> programStages = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Program id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Program name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreated() {
        return this.created;
    }

    public Program created(Instant created) {
        this.setCreated(created);
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getLastUpdated() {
        return this.lastUpdated;
    }

    public Program lastUpdated(Instant lastUpdated) {
        this.setLastUpdated(lastUpdated);
        return this;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getShortName() {
        return this.shortName;
    }

    public Program shortName(String shortName) {
        this.setShortName(shortName);
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDescription() {
        return this.description;
    }

    public Program description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getVersion() {
        return this.version;
    }

    public Program version(Double version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Double version) {
        this.version = version;
    }

    public String getEnrollmentDateLabel() {
        return this.enrollmentDateLabel;
    }

    public Program enrollmentDateLabel(String enrollmentDateLabel) {
        this.setEnrollmentDateLabel(enrollmentDateLabel);
        return this;
    }

    public void setEnrollmentDateLabel(String enrollmentDateLabel) {
        this.enrollmentDateLabel = enrollmentDateLabel;
    }

    public String getIncidentDateLabel() {
        return this.incidentDateLabel;
    }

    public Program incidentDateLabel(String incidentDateLabel) {
        this.setIncidentDateLabel(incidentDateLabel);
        return this;
    }

    public void setIncidentDateLabel(String incidentDateLabel) {
        this.incidentDateLabel = incidentDateLabel;
    }

    public String getProgramType() {
        return this.programType;
    }

    public Program programType(String programType) {
        this.setProgramType(programType);
        return this;
    }

    public void setProgramType(String programType) {
        this.programType = programType;
    }

    public Boolean getDisplayIncidentDate() {
        return this.displayIncidentDate;
    }

    public Program displayIncidentDate(Boolean displayIncidentDate) {
        this.setDisplayIncidentDate(displayIncidentDate);
        return this;
    }

    public void setDisplayIncidentDate(Boolean displayIncidentDate) {
        this.displayIncidentDate = displayIncidentDate;
    }

    public Boolean getIgnoreOverdueEvents() {
        return this.ignoreOverdueEvents;
    }

    public Program ignoreOverdueEvents(Boolean ignoreOverdueEvents) {
        this.setIgnoreOverdueEvents(ignoreOverdueEvents);
        return this;
    }

    public void setIgnoreOverdueEvents(Boolean ignoreOverdueEvents) {
        this.ignoreOverdueEvents = ignoreOverdueEvents;
    }

    /*  public String getUserRoles() {
        return this.userRoles;
    }

    public Program userRoles(String userRoles) {
        this.setUserRoles(userRoles);
        return this;
    }

    public void setUserRoles(String userRoles) {
        this.userRoles = userRoles;
    } */

    public Boolean getOnlyEnrollOnce() {
        return this.onlyEnrollOnce;
    }

    public Program onlyEnrollOnce(Boolean onlyEnrollOnce) {
        this.setOnlyEnrollOnce(onlyEnrollOnce);
        return this;
    }

    public void setOnlyEnrollOnce(Boolean onlyEnrollOnce) {
        this.onlyEnrollOnce = onlyEnrollOnce;
    }

    /*  public String getNotificationTemplates() {
        return this.notificationTemplates;
    }

    public Program notificationTemplates(String notificationTemplates) {
        this.setNotificationTemplates(notificationTemplates);
        return this;
    }

    public void setNotificationTemplates(String notificationTemplates) {
        this.notificationTemplates = notificationTemplates;
    }
 */
    public Boolean getSelectEnrollmentDatesInFuture() {
        return this.selectEnrollmentDatesInFuture;
    }

    public Program selectEnrollmentDatesInFuture(Boolean selectEnrollmentDatesInFuture) {
        this.setSelectEnrollmentDatesInFuture(selectEnrollmentDatesInFuture);
        return this;
    }

    public void setSelectEnrollmentDatesInFuture(Boolean selectEnrollmentDatesInFuture) {
        this.selectEnrollmentDatesInFuture = selectEnrollmentDatesInFuture;
    }

    public Boolean getSelectIncidentDatesInFuture() {
        return this.selectIncidentDatesInFuture;
    }

    public Program selectIncidentDatesInFuture(Boolean selectIncidentDatesInFuture) {
        this.setSelectIncidentDatesInFuture(selectIncidentDatesInFuture);
        return this;
    }

    public void setSelectIncidentDatesInFuture(Boolean selectIncidentDatesInFuture) {
        this.selectIncidentDatesInFuture = selectIncidentDatesInFuture;
    }

    public Boolean getSkipOffline() {
        return this.skipOffline;
    }

    public Program skipOffline(Boolean skipOffline) {
        this.setSkipOffline(skipOffline);
        return this;
    }

    public void setSkipOffline(Boolean skipOffline) {
        this.skipOffline = skipOffline;
    }

    public Boolean getDisplayFrontPageList() {
        return this.displayFrontPageList;
    }

    public Program displayFrontPageList(Boolean displayFrontPageList) {
        this.setDisplayFrontPageList(displayFrontPageList);
        return this;
    }

    public void setDisplayFrontPageList(Boolean displayFrontPageList) {
        this.displayFrontPageList = displayFrontPageList;
    }

    public Boolean getUseFirstStageDuringRegistration() {
        return this.useFirstStageDuringRegistration;
    }

    public Program useFirstStageDuringRegistration(Boolean useFirstStageDuringRegistration) {
        this.setUseFirstStageDuringRegistration(useFirstStageDuringRegistration);
        return this;
    }

    public void setUseFirstStageDuringRegistration(Boolean useFirstStageDuringRegistration) {
        this.useFirstStageDuringRegistration = useFirstStageDuringRegistration;
    }

    public Double getExpiryDays() {
        return this.expiryDays;
    }

    public Program expiryDays(Double expiryDays) {
        this.setExpiryDays(expiryDays);
        return this;
    }

    public void setExpiryDays(Double expiryDays) {
        this.expiryDays = expiryDays;
    }

    public Double getCompleteEventsExpiryDays() {
        return this.completeEventsExpiryDays;
    }

    public Program completeEventsExpiryDays(Double completeEventsExpiryDays) {
        this.setCompleteEventsExpiryDays(completeEventsExpiryDays);
        return this;
    }

    public void setCompleteEventsExpiryDays(Double completeEventsExpiryDays) {
        this.completeEventsExpiryDays = completeEventsExpiryDays;
    }

    public Double getOpenDaysAfterCoEndDate() {
        return this.openDaysAfterCoEndDate;
    }

    public Program openDaysAfterCoEndDate(Double openDaysAfterCoEndDate) {
        this.setOpenDaysAfterCoEndDate(openDaysAfterCoEndDate);
        return this;
    }

    public void setOpenDaysAfterCoEndDate(Double openDaysAfterCoEndDate) {
        this.openDaysAfterCoEndDate = openDaysAfterCoEndDate;
    }

    public Double getMinAttributesRequiredToSearch() {
        return this.minAttributesRequiredToSearch;
    }

    public Program minAttributesRequiredToSearch(Double minAttributesRequiredToSearch) {
        this.setMinAttributesRequiredToSearch(minAttributesRequiredToSearch);
        return this;
    }

    public void setMinAttributesRequiredToSearch(Double minAttributesRequiredToSearch) {
        this.minAttributesRequiredToSearch = minAttributesRequiredToSearch;
    }

    public Double getMaxTeiCountToReturn() {
        return this.maxTeiCountToReturn;
    }

    public Program maxTeiCountToReturn(Double maxTeiCountToReturn) {
        this.setMaxTeiCountToReturn(maxTeiCountToReturn);
        return this;
    }

    public void setMaxTeiCountToReturn(Double maxTeiCountToReturn) {
        this.maxTeiCountToReturn = maxTeiCountToReturn;
    }

    public String getAccessLevel() {
        return this.accessLevel;
    }

    public Program accessLevel(String accessLevel) {
        this.setAccessLevel(accessLevel);
        return this;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getDisplayEnrollmentDateLabel() {
        return this.displayEnrollmentDateLabel;
    }

    public Program displayEnrollmentDateLabel(String displayEnrollmentDateLabel) {
        this.setDisplayEnrollmentDateLabel(displayEnrollmentDateLabel);
        return this;
    }

    public void setDisplayEnrollmentDateLabel(String displayEnrollmentDateLabel) {
        this.displayEnrollmentDateLabel = displayEnrollmentDateLabel;
    }

    public String getDisplayIncidentDateLabel() {
        return this.displayIncidentDateLabel;
    }

    public Program displayIncidentDateLabel(String displayIncidentDateLabel) {
        this.setDisplayIncidentDateLabel(displayIncidentDateLabel);
        return this;
    }

    public void setDisplayIncidentDateLabel(String displayIncidentDateLabel) {
        this.displayIncidentDateLabel = displayIncidentDateLabel;
    }

    public Boolean getRegistration() {
        return this.registration;
    }

    public Program registration(Boolean registration) {
        this.setRegistration(registration);
        return this;
    }

    public void setRegistration(Boolean registration) {
        this.registration = registration;
    }

    public Boolean getWithoutRegistration() {
        return this.withoutRegistration;
    }

    public Program withoutRegistration(Boolean withoutRegistration) {
        this.setWithoutRegistration(withoutRegistration);
        return this;
    }

    public void setWithoutRegistration(Boolean withoutRegistration) {
        this.withoutRegistration = withoutRegistration;
    }

    public String getDisplayShortName() {
        return this.displayShortName;
    }

    public Program displayShortName(String displayShortName) {
        this.setDisplayShortName(displayShortName);
        return this;
    }

    public void setDisplayShortName(String displayShortName) {
        this.displayShortName = displayShortName;
    }

    public String getDisplayDescription() {
        return this.displayDescription;
    }

    public Program displayDescription(String displayDescription) {
        this.setDisplayDescription(displayDescription);
        return this;
    }

    public void setDisplayDescription(String displayDescription) {
        this.displayDescription = displayDescription;
    }

    public String getDisplayFormName() {
        return this.displayFormName;
    }

    public Program displayFormName(String displayFormName) {
        this.setDisplayFormName(displayFormName);
        return this;
    }

    public void setDisplayFormName(String displayFormName) {
        this.displayFormName = displayFormName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public Program displayName(String displayName) {
        this.setDisplayName(displayName);
        return this;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Integer getOrganisationUnitsCount() {
        return this.organisationUnitsCount;
    }

    public Program organisationUnitsCount(Integer organisationUnitsCount) {
        this.setOrganisationUnitsCount(organisationUnitsCount);
        return this;
    }

    public void setOrganisationUnitsCount(Integer organisationUnitsCount) {
        this.organisationUnitsCount = organisationUnitsCount;
    }

    public Integer getProgramStagesCount() {
        return this.programStagesCount;
    }

    public Program programStagesCount(Integer programStagesCount) {
        this.setProgramStagesCount(programStagesCount);
        return this;
    }

    public void setProgramStagesCount(Integer programStagesCount) {
        this.programStagesCount = programStagesCount;
    }

    public Integer getProgramIndicatorsCount() {
        return this.programIndicatorsCount;
    }

    public Program programIndicatorsCount(Integer programIndicatorsCount) {
        this.setProgramIndicatorsCount(programIndicatorsCount);
        return this;
    }

    public void setProgramIndicatorsCount(Integer programIndicatorsCount) {
        this.programIndicatorsCount = programIndicatorsCount;
    }

    public Integer getProgramTrackedEntityAttributesCount() {
        return this.programTrackedEntityAttributesCount;
    }

    public Program programTrackedEntityAttributesCount(Integer programTrackedEntityAttributesCount) {
        this.setProgramTrackedEntityAttributesCount(programTrackedEntityAttributesCount);
        return this;
    }

    public void setProgramTrackedEntityAttributesCount(Integer programTrackedEntityAttributesCount) {
        this.programTrackedEntityAttributesCount = programTrackedEntityAttributesCount;
    }

    public String getOrganisationUnitsContent() {
        return this.organisationUnitsContent;
    }

    public Program organisationUnitsContent(String organisationUnitsContent) {
        this.setOrganisationUnitsContent(organisationUnitsContent);
        return this;
    }

    public void setOrganisationUnitsContent(String organisationUnitsContent) {
        this.organisationUnitsContent = organisationUnitsContent;
    }

    public String getProgramStagesContent() {
        return this.programStagesContent;
    }

    public Program programStagesContent(String programStagesContent) {
        this.setProgramStagesContent(programStagesContent);
        return this;
    }

    public void setProgramStagesContent(String programStagesContent) {
        this.programStagesContent = programStagesContent;
    }

    public String getProgramIndicatorsContent() {
        return this.programIndicatorsContent;
    }

    public Program programIndicatorsContent(String programIndicatorsContent) {
        this.setProgramIndicatorsContent(programIndicatorsContent);
        return this;
    }

    public void setProgramIndicatorsContent(String programIndicatorsContent) {
        this.programIndicatorsContent = programIndicatorsContent;
    }

    public String getProgramTrackedEntityAttributesContent() {
        return this.programTrackedEntityAttributesContent;
    }

    public Program programTrackedEntityAttributesContent(String programTrackedEntityAttributesContent) {
        this.setProgramTrackedEntityAttributesContent(programTrackedEntityAttributesContent);
        return this;
    }

    public void setProgramTrackedEntityAttributesContent(String programTrackedEntityAttributesContent) {
        this.programTrackedEntityAttributesContent = programTrackedEntityAttributesContent;
    }

    public TypeTrack getTrack() {
        return this.track;
    }

    public Program track(TypeTrack track) {
        this.setTrack(track);
        return this;
    }

    public void setTrack(TypeTrack track) {
        this.track = track;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public Program setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Program project(Project project) {
        this.setProject(project);
        return this;
    }

    public DHISUser getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(DHISUser dHISUser) {
        this.createdBy = dHISUser;
    }

    public Program createdBy(DHISUser dHISUser) {
        this.setCreatedBy(dHISUser);
        return this;
    }

    public DHISUser getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }

    public void setLastUpdatedBy(DHISUser dHISUser) {
        this.lastUpdatedBy = dHISUser;
    }

    public Program lastUpdatedBy(DHISUser dHISUser) {
        this.setLastUpdatedBy(dHISUser);
        return this;
    }

    public CategoryCombo getCategoryCombo() {
        return this.categoryCombo;
    }

    public void setCategoryCombo(CategoryCombo categorycombo) {
        this.categoryCombo = categorycombo;
    }

    public Program categoryCombo(CategoryCombo categorycombo) {
        this.setCategoryCombo(categorycombo);
        return this;
    }

    public Set<TrackedEntityAttribute> getProgramTrackedEntityAttributes() {
        return this.programTrackedEntityAttributes;
    }

    public void setProgramTrackedEntityAttributes(Set<TrackedEntityAttribute> trackedEntityAttributes) {
        this.programTrackedEntityAttributes = trackedEntityAttributes;
    }

    public Program programTrackedEntityAttributes(Set<TrackedEntityAttribute> trackedEntityAttributes) {
        this.setProgramTrackedEntityAttributes(trackedEntityAttributes);
        return this;
    }

    public Program addProgramTrackedEntityAttributes(TrackedEntityAttribute trackedEntityAttribute) {
        this.programTrackedEntityAttributes.add(trackedEntityAttribute);
        trackedEntityAttribute.getPrograms().add(this);
        return this;
    }

    public Program removeProgramTrackedEntityAttributes(TrackedEntityAttribute trackedEntityAttribute) {
        this.programTrackedEntityAttributes.remove(trackedEntityAttribute);
        trackedEntityAttribute.getPrograms().remove(this);
        return this;
    }

    public Set<OrganisationUnit> getOrganisationUnits() {
        return this.organisationUnits;
    }

    public void setOrganisationUnits(Set<OrganisationUnit> organisationUnits) {
        this.organisationUnits = organisationUnits;
    }

    public Program organisationUnits(Set<OrganisationUnit> organisationUnits) {
        this.setOrganisationUnits(organisationUnits);
        return this;
    }

    public Program addOrganisationUnits(OrganisationUnit organisationUnit) {
        this.organisationUnits.add(organisationUnit);
        organisationUnit.getPrograms().add(this);
        return this;
    }

    public Program removeOrganisationUnits(OrganisationUnit organisationUnit) {
        this.organisationUnits.remove(organisationUnit);
        organisationUnit.getPrograms().remove(this);
        return this;
    }

    public Set<ProgramIndicator> getProgramIndicators() {
        return this.programIndicators;
    }

    public void setProgramIndicators(Set<ProgramIndicator> programIndicators) {
        this.programIndicators = programIndicators;
    }

    public Program programIndicators(Set<ProgramIndicator> programIndicators) {
        this.setProgramIndicators(programIndicators);
        return this;
    }

    public Program addProgramIndicators(ProgramIndicator programIndicator) {
        this.programIndicators.add(programIndicator);
        programIndicator.getPrograms().add(this);
        return this;
    }

    public Program removeProgramIndicators(ProgramIndicator programIndicator) {
        this.programIndicators.remove(programIndicator);
        programIndicator.getPrograms().remove(this);
        return this;
    }

    public Set<ProgramStage> getProgramStages() {
        return this.programStages;
    }

    public void setProgramStages(Set<ProgramStage> programStages) {
        this.programStages = programStages;
    }

    public Program programStages(Set<ProgramStage> programStages) {
        this.setProgramStages(programStages);
        return this;
    }

    public Program addProgramStage(ProgramStage programStage) {
        this.programStages.add(programStage);
        programStage.getPrograms().add(this);
        return this;
    }

    public Program removeProgramStage(ProgramStage programStage) {
        this.programStages.remove(programStage);
        programStage.getPrograms().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Program)) {
            return false;
        }
        return id != null && id.equals(((Program) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "Program [id=" +
            id +
            ", name=" +
            name +
            ", created=" +
            created +
            ", lastUpdated=" +
            lastUpdated +
            ", createdBy=" +
            createdBy +
            ", lastUpdatedBy=" +
            lastUpdatedBy +
            "]"
        );
    }
}
