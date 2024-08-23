package com.didate.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.envers.Audited;

/**
 * A Program.
 */
@Entity
@Audited
@Table(name = "program")
@SuppressWarnings("common-java:DuplicatedBlocks")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Program implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "created")
    private String created;

    @Column(name = "last_updated")
    private String lastUpdated;

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

    @Column(name = "user_roles")
    private String userRoles;

    @Column(name = "program_indicators")
    private String programIndicators;

    @Column(name = "program_rule_variables")
    private String programRuleVariables;

    @Column(name = "only_enroll_once")
    private Boolean onlyEnrollOnce;

    @Column(name = "notification_templates")
    private String notificationTemplates;

    @Column(name = "select_enrollment_dates_in_future")
    private Boolean selectEnrollmentDatesInFuture;

    @Column(name = "select_incident_dates_in_future")
    private Boolean selectIncidentDatesInFuture;

    @Column(name = "tracked_entity_type")
    private String trackedEntityType;

    @Column(name = "style")
    private String style;

    @Column(name = "category_combo")
    private String categoryCombo;

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

    @Column(name = "attribute_values_count")
    private Integer attributeValuesCount;

    @Column(name = "organisation_units_count")
    private Integer organisationUnitsCount;

    @Column(name = "program_stages_count")
    private Integer programStagesCount;

    @Column(name = "program_sections_count")
    private Integer programSectionsCount;

    @Column(name = "program_tracked_entity_attributes_count")
    private Integer programTrackedEntityAttributesCount;

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    @ManyToOne(optional = false)
    @NotNull
    private DHISUser createdBy;

    @ManyToOne(optional = false)
    @NotNull
    private DHISUser lastUpdatedBy;

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

    public String getCreated() {
        return this.created;
    }

    public Program created(String created) {
        this.setCreated(created);
        return this;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getLastUpdated() {
        return this.lastUpdated;
    }

    public Program lastUpdated(String lastUpdated) {
        this.setLastUpdated(lastUpdated);
        return this;
    }

    public void setLastUpdated(String lastUpdated) {
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

    public String getUserRoles() {
        return this.userRoles;
    }

    public Program userRoles(String userRoles) {
        this.setUserRoles(userRoles);
        return this;
    }

    public void setUserRoles(String userRoles) {
        this.userRoles = userRoles;
    }

    public String getProgramIndicators() {
        return this.programIndicators;
    }

    public Program programIndicators(String programIndicators) {
        this.setProgramIndicators(programIndicators);
        return this;
    }

    public void setProgramIndicators(String programIndicators) {
        this.programIndicators = programIndicators;
    }

    public String getProgramRuleVariables() {
        return this.programRuleVariables;
    }

    public Program programRuleVariables(String programRuleVariables) {
        this.setProgramRuleVariables(programRuleVariables);
        return this;
    }

    public void setProgramRuleVariables(String programRuleVariables) {
        this.programRuleVariables = programRuleVariables;
    }

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

    public String getNotificationTemplates() {
        return this.notificationTemplates;
    }

    public Program notificationTemplates(String notificationTemplates) {
        this.setNotificationTemplates(notificationTemplates);
        return this;
    }

    public void setNotificationTemplates(String notificationTemplates) {
        this.notificationTemplates = notificationTemplates;
    }

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

    public String getTrackedEntityType() {
        return this.trackedEntityType;
    }

    public Program trackedEntityType(String trackedEntityType) {
        this.setTrackedEntityType(trackedEntityType);
        return this;
    }

    public void setTrackedEntityType(String trackedEntityType) {
        this.trackedEntityType = trackedEntityType;
    }

    public String getStyle() {
        return this.style;
    }

    public Program style(String style) {
        this.setStyle(style);
        return this;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getCategoryCombo() {
        return this.categoryCombo;
    }

    public Program categoryCombo(String categoryCombo) {
        this.setCategoryCombo(categoryCombo);
        return this;
    }

    public void setCategoryCombo(String categoryCombo) {
        this.categoryCombo = categoryCombo;
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

    public Integer getAttributeValuesCount() {
        return this.attributeValuesCount;
    }

    public Program attributeValuesCount(Integer attributeValuesCount) {
        this.setAttributeValuesCount(attributeValuesCount);
        return this;
    }

    public void setAttributeValuesCount(Integer attributeValuesCount) {
        this.attributeValuesCount = attributeValuesCount;
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

    public Integer getProgramSectionsCount() {
        return this.programSectionsCount;
    }

    public Program programSectionsCount(Integer programSectionsCount) {
        this.setProgramSectionsCount(programSectionsCount);
        return this;
    }

    public void setProgramSectionsCount(Integer programSectionsCount) {
        this.programSectionsCount = programSectionsCount;
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

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Program)) {
            return false;
        }
        return getId() != null && getId().equals(((Program) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Program{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", created='" + getCreated() + "'" +
            ", lastUpdated='" + getLastUpdated() + "'" +
            ", shortName='" + getShortName() + "'" +
            ", description='" + getDescription() + "'" +
            ", version=" + getVersion() +
            ", enrollmentDateLabel='" + getEnrollmentDateLabel() + "'" +
            ", incidentDateLabel='" + getIncidentDateLabel() + "'" +
            ", programType='" + getProgramType() + "'" +
            ", displayIncidentDate='" + getDisplayIncidentDate() + "'" +
            ", ignoreOverdueEvents='" + getIgnoreOverdueEvents() + "'" +
            ", userRoles='" + getUserRoles() + "'" +
            ", programIndicators='" + getProgramIndicators() + "'" +
            ", programRuleVariables='" + getProgramRuleVariables() + "'" +
            ", onlyEnrollOnce='" + getOnlyEnrollOnce() + "'" +
            ", notificationTemplates='" + getNotificationTemplates() + "'" +
            ", selectEnrollmentDatesInFuture='" + getSelectEnrollmentDatesInFuture() + "'" +
            ", selectIncidentDatesInFuture='" + getSelectIncidentDatesInFuture() + "'" +
            ", trackedEntityType='" + getTrackedEntityType() + "'" +
            ", style='" + getStyle() + "'" +
            ", categoryCombo='" + getCategoryCombo() + "'" +
            ", skipOffline='" + getSkipOffline() + "'" +
            ", displayFrontPageList='" + getDisplayFrontPageList() + "'" +
            ", useFirstStageDuringRegistration='" + getUseFirstStageDuringRegistration() + "'" +
            ", expiryDays=" + getExpiryDays() +
            ", completeEventsExpiryDays=" + getCompleteEventsExpiryDays() +
            ", openDaysAfterCoEndDate=" + getOpenDaysAfterCoEndDate() +
            ", minAttributesRequiredToSearch=" + getMinAttributesRequiredToSearch() +
            ", maxTeiCountToReturn=" + getMaxTeiCountToReturn() +
            ", accessLevel='" + getAccessLevel() + "'" +
            ", displayEnrollmentDateLabel='" + getDisplayEnrollmentDateLabel() + "'" +
            ", displayIncidentDateLabel='" + getDisplayIncidentDateLabel() + "'" +
            ", registration='" + getRegistration() + "'" +
            ", withoutRegistration='" + getWithoutRegistration() + "'" +
            ", displayShortName='" + getDisplayShortName() + "'" +
            ", displayDescription='" + getDisplayDescription() + "'" +
            ", displayFormName='" + getDisplayFormName() + "'" +
            ", displayName='" + getDisplayName() + "'" +
            ", attributeValuesCount=" + getAttributeValuesCount() +
            ", organisationUnitsCount=" + getOrganisationUnitsCount() +
            ", programStagesCount=" + getProgramStagesCount() +
            ", programSectionsCount=" + getProgramSectionsCount() +
            ", programTrackedEntityAttributesCount=" + getProgramTrackedEntityAttributesCount() +
            "}";
    }
}
