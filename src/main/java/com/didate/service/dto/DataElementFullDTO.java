package com.didate.service.dto;

import com.didate.domain.CategoryCombo;
import com.didate.domain.DHISUser;
import com.didate.domain.DataElement;
import com.didate.domain.OptionSet;
import com.didate.domain.Project;
import com.didate.domain.enumeration.TypeTrack;

public class DataElementFullDTO extends AbstractDTO {

    private String shortName;

    private String formName;

    private String description;

    private String displayShortName;

    private String displayName;

    private String displayFormName;

    private String publicAccess;

    private String dimensionItemType;

    private String aggregationType;

    private String valueType;

    private String domainType;

    private Boolean zeroIsSignificant;

    private String optionSetValue;

    private String dimensionItem;

    private TypeTrack track;

    private Project project;

    private CategoryCombo categoryCombo;

    private OptionSet optionSet;

    public DataElementFullDTO(DataElement dataElement) {
        super(
            dataElement.getId(),
            dataElement.getName(),
            dataElement.getCreated(),
            dataElement.getLastUpdated(),
            dataElement.getCreatedBy(),
            dataElement.getLastUpdatedBy()
        );
        this.shortName = dataElement.getShortName();
        this.formName = dataElement.getFormName();
        this.description = dataElement.getDescription();
        this.displayShortName = dataElement.getDisplayShortName();
        this.displayName = dataElement.getDisplayName();
        this.displayFormName = dataElement.getDisplayFormName();
        this.publicAccess = dataElement.getPublicAccess();
        this.dimensionItemType = dataElement.getDimensionItemType();
        this.aggregationType = dataElement.getAggregationType();
        this.valueType = dataElement.getValueType();
        this.domainType = dataElement.getDomainType();
        this.zeroIsSignificant = dataElement.getZeroIsSignificant();
        this.optionSetValue = dataElement.getOptionSetValue();
        this.dimensionItem = dataElement.getDimensionItem();
        this.track = dataElement.getTrack();
        this.project = dataElement.getProject();
        this.categoryCombo = dataElement.getCategoryCombo();
        this.optionSet = dataElement.getOptionSet();
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplayShortName() {
        return displayShortName;
    }

    public void setDisplayShortName(String displayShortName) {
        this.displayShortName = displayShortName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayFormName() {
        return displayFormName;
    }

    public void setDisplayFormName(String displayFormName) {
        this.displayFormName = displayFormName;
    }

    public String getPublicAccess() {
        return publicAccess;
    }

    public void setPublicAccess(String publicAccess) {
        this.publicAccess = publicAccess;
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

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getDomainType() {
        return domainType;
    }

    public void setDomainType(String domainType) {
        this.domainType = domainType;
    }

    public Boolean getZeroIsSignificant() {
        return zeroIsSignificant;
    }

    public void setZeroIsSignificant(Boolean zeroIsSignificant) {
        this.zeroIsSignificant = zeroIsSignificant;
    }

    public String getOptionSetValue() {
        return optionSetValue;
    }

    public void setOptionSetValue(String optionSetValue) {
        this.optionSetValue = optionSetValue;
    }

    public String getDimensionItem() {
        return dimensionItem;
    }

    public void setDimensionItem(String dimensionItem) {
        this.dimensionItem = dimensionItem;
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

    public OptionSet getOptionSet() {
        return optionSet;
    }

    public void setOptionSet(OptionSet optionSet) {
        this.optionSet = optionSet;
    }
}
