package com.didate.service.dto;

import com.didate.domain.TrackedEntityAttribute;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class TrackedEntityAttributeFullDTO extends TrackedEntityAttributeDTO {

    private Boolean generated;

    private String valueType;

    private Boolean confidential;

    private String displayFormName;

    private Boolean uniquee;

    private String dimensionItemType;

    private String aggregationType;

    private Boolean displayInListNoProgram;

    private String displayName;

    private String patterne;

    private Boolean skipSynchronization;

    private String displayShortName;

    private Integer periodOffset;

    private Boolean displayOnVisitSchedule;

    private String formName;

    private Boolean orgunitScope;

    private String dimensionItem;

    private Boolean inherit;

    private Boolean optionSetValue;

    @JsonIgnoreProperties(value = "trackedEntityAttributes", allowSetters = true)
    private OptionSetDTO optionSet;

    public TrackedEntityAttributeFullDTO(TrackedEntityAttribute trackedEntityAttribute) {
        super(trackedEntityAttribute);
        this.generated = trackedEntityAttribute.getGenerated();
        this.valueType = trackedEntityAttribute.getValueType();
        this.confidential = trackedEntityAttribute.getConfidential();
        this.displayFormName = trackedEntityAttribute.getDisplayFormName();
        this.uniquee = trackedEntityAttribute.getUniquee();
        this.dimensionItemType = trackedEntityAttribute.getDimensionItemType();
        this.aggregationType = trackedEntityAttribute.getAggregationType();
        this.displayInListNoProgram = trackedEntityAttribute.getDisplayInListNoProgram();
        this.displayName = trackedEntityAttribute.getDisplayName();
        this.patterne = trackedEntityAttribute.getPatterne();
        this.skipSynchronization = trackedEntityAttribute.getSkipSynchronization();
        this.displayShortName = trackedEntityAttribute.getDisplayShortName();
        this.periodOffset = trackedEntityAttribute.getPeriodOffset();
        this.displayOnVisitSchedule = trackedEntityAttribute.getDisplayOnVisitSchedule();
        this.formName = trackedEntityAttribute.getFormName();
        this.orgunitScope = trackedEntityAttribute.getOrgunitScope();
        this.dimensionItem = trackedEntityAttribute.getDimensionItem();
        this.inherit = trackedEntityAttribute.getInherit();
        this.optionSetValue = trackedEntityAttribute.getOptionSetValue();
        this.optionSet = trackedEntityAttribute.getOptionSet() != null ? new OptionSetDTO(trackedEntityAttribute.getOptionSet()) : null;
    }

    public Boolean getGenerated() {
        return generated;
    }

    public void setGenerated(Boolean generated) {
        this.generated = generated;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public Boolean getConfidential() {
        return confidential;
    }

    public void setConfidential(Boolean confidential) {
        this.confidential = confidential;
    }

    public String getDisplayFormName() {
        return displayFormName;
    }

    public void setDisplayFormName(String displayFormName) {
        this.displayFormName = displayFormName;
    }

    public Boolean getUniquee() {
        return uniquee;
    }

    public void setUniquee(Boolean uniquee) {
        this.uniquee = uniquee;
    }

    public String getDimensionItemType() {
        return dimensionItemType;
    }

    public void setDimensionItemType(String dimensionItemType) {
        this.dimensionItemType = dimensionItemType;
    }

    public String getAggregationType() {
        return aggregationType;
    }

    public void setAggregationType(String aggregationType) {
        this.aggregationType = aggregationType;
    }

    public Boolean getDisplayInListNoProgram() {
        return displayInListNoProgram;
    }

    public void setDisplayInListNoProgram(Boolean displayInListNoProgram) {
        this.displayInListNoProgram = displayInListNoProgram;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPatterne() {
        return patterne;
    }

    public void setPatterne(String patterne) {
        this.patterne = patterne;
    }

    public Boolean getSkipSynchronization() {
        return skipSynchronization;
    }

    public void setSkipSynchronization(Boolean skipSynchronization) {
        this.skipSynchronization = skipSynchronization;
    }

    public String getDisplayShortName() {
        return displayShortName;
    }

    public void setDisplayShortName(String displayShortName) {
        this.displayShortName = displayShortName;
    }

    public Integer getPeriodOffset() {
        return periodOffset;
    }

    public void setPeriodOffset(Integer periodOffset) {
        this.periodOffset = periodOffset;
    }

    public Boolean getDisplayOnVisitSchedule() {
        return displayOnVisitSchedule;
    }

    public void setDisplayOnVisitSchedule(Boolean displayOnVisitSchedule) {
        this.displayOnVisitSchedule = displayOnVisitSchedule;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public Boolean getOrgunitScope() {
        return orgunitScope;
    }

    public void setOrgunitScope(Boolean orgunitScope) {
        this.orgunitScope = orgunitScope;
    }

    public String getDimensionItem() {
        return dimensionItem;
    }

    public void setDimensionItem(String dimensionItem) {
        this.dimensionItem = dimensionItem;
    }

    public Boolean getInherit() {
        return inherit;
    }

    public void setInherit(Boolean inherit) {
        this.inherit = inherit;
    }

    public Boolean getOptionSetValue() {
        return optionSetValue;
    }

    public void setOptionSetValue(Boolean optionSetValue) {
        this.optionSetValue = optionSetValue;
    }

    public OptionSetDTO getOptionSet() {
        return optionSet;
    }

    public void setOptionSet(OptionSetDTO optionSet) {
        this.optionSet = optionSet;
    }
}
