package com.didate.service.dto;

import com.didate.domain.CategoryCombo;
import com.didate.domain.Program;

public class ProgramFullDTO extends AbstractDTO {

    public ProgramFullDTO(Program program) {
        super(
            program.getId(),
            program.getName(),
            program.getShortName(),
            program.getCreated(),
            program.getLastUpdated(),
            program.getCreatedBy(),
            program.getLastUpdatedBy()
        );
        this.description = program.getDescription();
        this.version = program.getVersion();
        this.enrollmentDateLabel = program.getEnrollmentDateLabel();
        this.incidentDateLabel = program.getIncidentDateLabel();
        this.programType = program.getProgramType();
        this.displayIncidentDate = program.getDisplayIncidentDate();
        this.ignoreOverdueEvents = program.getIgnoreOverdueEvents();
        this.onlyEnrollOnce = program.getOnlyEnrollOnce();
        this.selectEnrollmentDatesInFuture = program.getSelectEnrollmentDatesInFuture();
        this.selectIncidentDatesInFuture = program.getSelectIncidentDatesInFuture();
        this.skipOffline = program.getSkipOffline();
        this.displayFrontPageList = program.getDisplayFrontPageList();
        this.useFirstStageDuringRegistration = program.getUseFirstStageDuringRegistration();
        this.expiryDays = program.getExpiryDays();
        this.completeEventsExpiryDays = program.getCompleteEventsExpiryDays();
        this.openDaysAfterCoEndDate = program.getOpenDaysAfterCoEndDate();
        this.minAttributesRequiredToSearch = program.getMinAttributesRequiredToSearch();
        this.maxTeiCountToReturn = program.getMaxTeiCountToReturn();
        this.accessLevel = program.getAccessLevel();
        this.displayEnrollmentDateLabel = program.getDisplayEnrollmentDateLabel();
        this.displayIncidentDateLabel = program.getDisplayIncidentDateLabel();
        this.registration = program.getRegistration();
        this.withoutRegistration = program.getWithoutRegistration();
        this.displayShortName = program.getDisplayShortName();
        this.displayDescription = program.getDisplayDescription();
        this.displayFormName = program.getDisplayFormName();
        this.displayName = program.getDisplayName();
        this.organisationUnitsCount = program.getOrganisationUnitsCount();
        this.programStagesCount = program.getProgramStagesCount();
        this.programIndicatorsCount = program.getProgramIndicatorsCount();
        this.programTrackedEntityAttributesCount = program.getProgramTrackedEntityAttributesCount();
        this.organisationUnitsContent = program.getOrganisationUnitsContent();
        this.programStagesContent = program.getProgramStagesContent();
        this.programIndicatorsContent = program.getProgramIndicatorsContent();
        this.programTrackedEntityAttributesContent = program.getProgramTrackedEntityAttributesContent();
        this.categoryCombo = program.getCategoryCombo();
    }

    private String description;

    private Double version;

    private String enrollmentDateLabel;

    private String incidentDateLabel;

    private String programType;

    private Boolean displayIncidentDate;

    private Boolean ignoreOverdueEvents;

    private Boolean onlyEnrollOnce;

    private Boolean selectEnrollmentDatesInFuture;

    private Boolean selectIncidentDatesInFuture;

    private Boolean skipOffline;

    private Boolean displayFrontPageList;

    private Boolean useFirstStageDuringRegistration;

    private Double expiryDays;

    private Double completeEventsExpiryDays;

    private Double openDaysAfterCoEndDate;

    private Double minAttributesRequiredToSearch;

    private Double maxTeiCountToReturn;

    private String accessLevel;

    private String displayEnrollmentDateLabel;

    private String displayIncidentDateLabel;

    private Boolean registration;

    private Boolean withoutRegistration;

    private String displayShortName;

    private String displayDescription;

    private String displayFormName;

    private String displayName;

    private Integer organisationUnitsCount;

    private Integer programStagesCount;

    private Integer programIndicatorsCount;

    private Integer programTrackedEntityAttributesCount;

    private String organisationUnitsContent;

    private String programStagesContent;

    private String programIndicatorsContent;

    private String programTrackedEntityAttributesContent;

    private CategoryCombo categoryCombo;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
    }

    public String getEnrollmentDateLabel() {
        return enrollmentDateLabel;
    }

    public void setEnrollmentDateLabel(String enrollmentDateLabel) {
        this.enrollmentDateLabel = enrollmentDateLabel;
    }

    public String getIncidentDateLabel() {
        return incidentDateLabel;
    }

    public void setIncidentDateLabel(String incidentDateLabel) {
        this.incidentDateLabel = incidentDateLabel;
    }

    public String getProgramType() {
        return programType;
    }

    public void setProgramType(String programType) {
        this.programType = programType;
    }

    public Boolean getDisplayIncidentDate() {
        return displayIncidentDate;
    }

    public void setDisplayIncidentDate(Boolean displayIncidentDate) {
        this.displayIncidentDate = displayIncidentDate;
    }

    public Boolean getIgnoreOverdueEvents() {
        return ignoreOverdueEvents;
    }

    public void setIgnoreOverdueEvents(Boolean ignoreOverdueEvents) {
        this.ignoreOverdueEvents = ignoreOverdueEvents;
    }

    public Boolean getOnlyEnrollOnce() {
        return onlyEnrollOnce;
    }

    public void setOnlyEnrollOnce(Boolean onlyEnrollOnce) {
        this.onlyEnrollOnce = onlyEnrollOnce;
    }

    public Boolean getSelectEnrollmentDatesInFuture() {
        return selectEnrollmentDatesInFuture;
    }

    public void setSelectEnrollmentDatesInFuture(Boolean selectEnrollmentDatesInFuture) {
        this.selectEnrollmentDatesInFuture = selectEnrollmentDatesInFuture;
    }

    public Boolean getSelectIncidentDatesInFuture() {
        return selectIncidentDatesInFuture;
    }

    public void setSelectIncidentDatesInFuture(Boolean selectIncidentDatesInFuture) {
        this.selectIncidentDatesInFuture = selectIncidentDatesInFuture;
    }

    public Boolean getSkipOffline() {
        return skipOffline;
    }

    public void setSkipOffline(Boolean skipOffline) {
        this.skipOffline = skipOffline;
    }

    public Boolean getDisplayFrontPageList() {
        return displayFrontPageList;
    }

    public void setDisplayFrontPageList(Boolean displayFrontPageList) {
        this.displayFrontPageList = displayFrontPageList;
    }

    public Boolean getUseFirstStageDuringRegistration() {
        return useFirstStageDuringRegistration;
    }

    public void setUseFirstStageDuringRegistration(Boolean useFirstStageDuringRegistration) {
        this.useFirstStageDuringRegistration = useFirstStageDuringRegistration;
    }

    public Double getExpiryDays() {
        return expiryDays;
    }

    public void setExpiryDays(Double expiryDays) {
        this.expiryDays = expiryDays;
    }

    public Double getCompleteEventsExpiryDays() {
        return completeEventsExpiryDays;
    }

    public void setCompleteEventsExpiryDays(Double completeEventsExpiryDays) {
        this.completeEventsExpiryDays = completeEventsExpiryDays;
    }

    public Double getOpenDaysAfterCoEndDate() {
        return openDaysAfterCoEndDate;
    }

    public void setOpenDaysAfterCoEndDate(Double openDaysAfterCoEndDate) {
        this.openDaysAfterCoEndDate = openDaysAfterCoEndDate;
    }

    public Double getMinAttributesRequiredToSearch() {
        return minAttributesRequiredToSearch;
    }

    public void setMinAttributesRequiredToSearch(Double minAttributesRequiredToSearch) {
        this.minAttributesRequiredToSearch = minAttributesRequiredToSearch;
    }

    public Double getMaxTeiCountToReturn() {
        return maxTeiCountToReturn;
    }

    public void setMaxTeiCountToReturn(Double maxTeiCountToReturn) {
        this.maxTeiCountToReturn = maxTeiCountToReturn;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getDisplayEnrollmentDateLabel() {
        return displayEnrollmentDateLabel;
    }

    public void setDisplayEnrollmentDateLabel(String displayEnrollmentDateLabel) {
        this.displayEnrollmentDateLabel = displayEnrollmentDateLabel;
    }

    public String getDisplayIncidentDateLabel() {
        return displayIncidentDateLabel;
    }

    public void setDisplayIncidentDateLabel(String displayIncidentDateLabel) {
        this.displayIncidentDateLabel = displayIncidentDateLabel;
    }

    public Boolean getRegistration() {
        return registration;
    }

    public void setRegistration(Boolean registration) {
        this.registration = registration;
    }

    public Boolean getWithoutRegistration() {
        return withoutRegistration;
    }

    public void setWithoutRegistration(Boolean withoutRegistration) {
        this.withoutRegistration = withoutRegistration;
    }

    public String getDisplayShortName() {
        return displayShortName;
    }

    public void setDisplayShortName(String displayShortName) {
        this.displayShortName = displayShortName;
    }

    public String getDisplayDescription() {
        return displayDescription;
    }

    public void setDisplayDescription(String displayDescription) {
        this.displayDescription = displayDescription;
    }

    public String getDisplayFormName() {
        return displayFormName;
    }

    public void setDisplayFormName(String displayFormName) {
        this.displayFormName = displayFormName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Integer getOrganisationUnitsCount() {
        return organisationUnitsCount;
    }

    public void setOrganisationUnitsCount(Integer organisationUnitsCount) {
        this.organisationUnitsCount = organisationUnitsCount;
    }

    public Integer getProgramStagesCount() {
        return programStagesCount;
    }

    public void setProgramStagesCount(Integer programStagesCount) {
        this.programStagesCount = programStagesCount;
    }

    public Integer getProgramIndicatorsCount() {
        return programIndicatorsCount;
    }

    public void setProgramIndicatorsCount(Integer programIndicatorsCount) {
        this.programIndicatorsCount = programIndicatorsCount;
    }

    public Integer getProgramTrackedEntityAttributesCount() {
        return programTrackedEntityAttributesCount;
    }

    public void setProgramTrackedEntityAttributesCount(Integer programTrackedEntityAttributesCount) {
        this.programTrackedEntityAttributesCount = programTrackedEntityAttributesCount;
    }

    public String getOrganisationUnitsContent() {
        return organisationUnitsContent;
    }

    public void setOrganisationUnitsContent(String organisationUnitsContent) {
        this.organisationUnitsContent = organisationUnitsContent;
    }

    public String getProgramStagesContent() {
        return programStagesContent;
    }

    public void setProgramStagesContent(String programStagesContent) {
        this.programStagesContent = programStagesContent;
    }

    public String getProgramIndicatorsContent() {
        return programIndicatorsContent;
    }

    public void setProgramIndicatorsContent(String programIndicatorsContent) {
        this.programIndicatorsContent = programIndicatorsContent;
    }

    public String getProgramTrackedEntityAttributesContent() {
        return programTrackedEntityAttributesContent;
    }

    public void setProgramTrackedEntityAttributesContent(String programTrackedEntityAttributesContent) {
        this.programTrackedEntityAttributesContent = programTrackedEntityAttributesContent;
    }

    public CategoryCombo getCategoryCombo() {
        return categoryCombo;
    }

    public void setCategoryCombo(CategoryCombo categoryCombo) {
        this.categoryCombo = categoryCombo;
    }
}
