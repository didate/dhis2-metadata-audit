package com.didate.service.dto;

import com.didate.domain.CategoryCombo;
import com.didate.domain.DataSet;
import com.didate.domain.Project;
import com.didate.domain.enumeration.TypeTrack;

public class DataSetFullDTO extends AbstractDTO {

    private String description;
    private String dimensionItemType;
    private String periodType;
    private String mobile;
    private Double version;
    private Double expiryDays;
    private Double timelyDays;
    private String notifyCompletingUser;
    private Double openFuturePeriods;
    private Double openPeriodsAfterCoEndDate;
    private Boolean fieldCombinationRequired;
    private Boolean validCompleteOnly;
    private Boolean noValueRequiresComment;
    private Boolean skipOffline;
    private Boolean dataElementDecoration;
    private Boolean renderAsTabs;
    private Boolean renderHorizontally;
    private Boolean compulsoryFieldsCompleteOnly;
    private String formType;
    private String displayName;
    private String dimensionItem;
    private String displayShortName;
    private String displayDescription;
    private String displayFormName;
    private Integer dataSetElementsCount;
    private Integer indicatorsCount;
    private Integer organisationUnitsCount;
    private String dataSetElementsContent;
    private String indicatorsContent;
    private String organisationUnitsContent;
    private TypeTrack track;
    private Project project;
    private CategoryCombo categoryCombo;

    public DataSetFullDTO(DataSet dataSet) {
        super(
            dataSet.getId(),
            dataSet.getName(),
            dataSet.getShortName(),
            dataSet.getCreated().toString(),
            dataSet.getLastUpdated().toString(),
            dataSet.getCreatedBy(),
            dataSet.getLastUpdatedBy()
        );
        this.description = dataSet.getDescription();
        this.dimensionItemType = dataSet.getDimensionItemType();
        this.periodType = dataSet.getPeriodType();
        this.mobile = dataSet.getMobile();
        this.version = dataSet.getVersion();
        this.expiryDays = dataSet.getExpiryDays();
        this.timelyDays = dataSet.getTimelyDays();
        this.notifyCompletingUser = dataSet.getNotifyCompletingUser();
        this.openFuturePeriods = dataSet.getOpenFuturePeriods();
        this.openPeriodsAfterCoEndDate = dataSet.getOpenPeriodsAfterCoEndDate();
        this.fieldCombinationRequired = dataSet.getFieldCombinationRequired();
        this.validCompleteOnly = dataSet.getValidCompleteOnly();
        this.noValueRequiresComment = dataSet.getNoValueRequiresComment();
        this.skipOffline = dataSet.getSkipOffline();
        this.dataElementDecoration = dataSet.getDataElementDecoration();
        this.renderAsTabs = dataSet.getRenderAsTabs();
        this.renderHorizontally = dataSet.getRenderHorizontally();
        this.compulsoryFieldsCompleteOnly = dataSet.getCompulsoryFieldsCompleteOnly();
        this.formType = dataSet.getFormType();
        this.displayName = dataSet.getDisplayName();
        this.dimensionItem = dataSet.getDimensionItem();
        this.displayShortName = dataSet.getDisplayShortName();
        this.displayDescription = dataSet.getDisplayDescription();
        this.displayFormName = dataSet.getDisplayFormName();
        this.dataSetElementsCount = dataSet.getDataSetElementsCount();
        this.indicatorsCount = dataSet.getIndicatorsCount();
        this.organisationUnitsCount = dataSet.getOrganisationUnitsCount();
        this.dataSetElementsContent = dataSet.getDataSetElementsContent();
        this.indicatorsContent = dataSet.getIndicatorsContent();
        this.organisationUnitsContent = dataSet.getOrganisationUnitsContent();
        this.track = dataSet.getTrack();
        this.project = dataSet.getProject();
        this.categoryCombo = dataSet.getCategoryCombo();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDimensionItemType() {
        return dimensionItemType;
    }

    public void setDimensionItemType(String dimensionItemType) {
        this.dimensionItemType = dimensionItemType;
    }

    public String getPeriodType() {
        return periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
    }

    public Double getExpiryDays() {
        return expiryDays;
    }

    public void setExpiryDays(Double expiryDays) {
        this.expiryDays = expiryDays;
    }

    public Double getTimelyDays() {
        return timelyDays;
    }

    public void setTimelyDays(Double timelyDays) {
        this.timelyDays = timelyDays;
    }

    public String getNotifyCompletingUser() {
        return notifyCompletingUser;
    }

    public void setNotifyCompletingUser(String notifyCompletingUser) {
        this.notifyCompletingUser = notifyCompletingUser;
    }

    public Double getOpenFuturePeriods() {
        return openFuturePeriods;
    }

    public void setOpenFuturePeriods(Double openFuturePeriods) {
        this.openFuturePeriods = openFuturePeriods;
    }

    public Double getOpenPeriodsAfterCoEndDate() {
        return openPeriodsAfterCoEndDate;
    }

    public void setOpenPeriodsAfterCoEndDate(Double openPeriodsAfterCoEndDate) {
        this.openPeriodsAfterCoEndDate = openPeriodsAfterCoEndDate;
    }

    public Boolean getFieldCombinationRequired() {
        return fieldCombinationRequired;
    }

    public void setFieldCombinationRequired(Boolean fieldCombinationRequired) {
        this.fieldCombinationRequired = fieldCombinationRequired;
    }

    public Boolean getValidCompleteOnly() {
        return validCompleteOnly;
    }

    public void setValidCompleteOnly(Boolean validCompleteOnly) {
        this.validCompleteOnly = validCompleteOnly;
    }

    public Boolean getNoValueRequiresComment() {
        return noValueRequiresComment;
    }

    public void setNoValueRequiresComment(Boolean noValueRequiresComment) {
        this.noValueRequiresComment = noValueRequiresComment;
    }

    public Boolean getSkipOffline() {
        return skipOffline;
    }

    public void setSkipOffline(Boolean skipOffline) {
        this.skipOffline = skipOffline;
    }

    public Boolean getDataElementDecoration() {
        return dataElementDecoration;
    }

    public void setDataElementDecoration(Boolean dataElementDecoration) {
        this.dataElementDecoration = dataElementDecoration;
    }

    public Boolean getRenderAsTabs() {
        return renderAsTabs;
    }

    public void setRenderAsTabs(Boolean renderAsTabs) {
        this.renderAsTabs = renderAsTabs;
    }

    public Boolean getRenderHorizontally() {
        return renderHorizontally;
    }

    public void setRenderHorizontally(Boolean renderHorizontally) {
        this.renderHorizontally = renderHorizontally;
    }

    public Boolean getCompulsoryFieldsCompleteOnly() {
        return compulsoryFieldsCompleteOnly;
    }

    public void setCompulsoryFieldsCompleteOnly(Boolean compulsoryFieldsCompleteOnly) {
        this.compulsoryFieldsCompleteOnly = compulsoryFieldsCompleteOnly;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDimensionItem() {
        return dimensionItem;
    }

    public void setDimensionItem(String dimensionItem) {
        this.dimensionItem = dimensionItem;
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

    public Integer getDataSetElementsCount() {
        return dataSetElementsCount;
    }

    public void setDataSetElementsCount(Integer dataSetElementsCount) {
        this.dataSetElementsCount = dataSetElementsCount;
    }

    public Integer getIndicatorsCount() {
        return indicatorsCount;
    }

    public void setIndicatorsCount(Integer indicatorsCount) {
        this.indicatorsCount = indicatorsCount;
    }

    public Integer getOrganisationUnitsCount() {
        return organisationUnitsCount;
    }

    public void setOrganisationUnitsCount(Integer organisationUnitsCount) {
        this.organisationUnitsCount = organisationUnitsCount;
    }

    public String getDataSetElementsContent() {
        return dataSetElementsContent;
    }

    public void setDataSetElementsContent(String dataSetElementsContent) {
        this.dataSetElementsContent = dataSetElementsContent;
    }

    public String getIndicatorsContent() {
        return indicatorsContent;
    }

    public void setIndicatorsContent(String indicatorsContent) {
        this.indicatorsContent = indicatorsContent;
    }

    public String getOrganisationUnitsContent() {
        return organisationUnitsContent;
    }

    public void setOrganisationUnitsContent(String organisationUnitsContent) {
        this.organisationUnitsContent = organisationUnitsContent;
    }

    public TypeTrack getTrack() {
        return track;
    }

    public void setTrack(TypeTrack track) {
        this.track = track;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public CategoryCombo getCategoryCombo() {
        return categoryCombo;
    }

    public void setCategoryCombo(CategoryCombo categoryCombo) {
        this.categoryCombo = categoryCombo;
    }
}
