package com.didate.service.dto;

import com.didate.domain.Indicator;
import com.didate.domain.IndicatorType;
import com.didate.domain.Project;
import com.didate.domain.enumeration.TypeTrack;

public class IndicatorFullDTO extends AbstractDTO {

    private String displayShortName;

    private String displayName;

    private String displayFormName;

    private String publicAccess;

    private String dimensionItemType;

    private Boolean annualized;

    private String numerator;

    private String numeratorDescription;

    private String denominator;

    private String denominatorDescription;

    private String displayNumeratorDescription;

    private String displayDenominatorDescription;

    private String dimensionItem;

    private TypeTrack track;

    private Project project;

    private IndicatorType indicatorType;

    public IndicatorFullDTO(Indicator indicator) {
        super(
            indicator.getId(),
            indicator.getName(),
            indicator.getShortName(),
            indicator.getCreated(),
            indicator.getLastUpdated(),
            indicator.getCreatedBy(),
            indicator.getLastUpdatedBy()
        );
        this.displayShortName = indicator.getDisplayShortName();
        this.displayName = indicator.getDisplayName();
        this.displayFormName = indicator.getDisplayFormName();

        this.publicAccess = indicator.getPublicAccess();
        this.dimensionItemType = indicator.getDimensionItemType();
        this.annualized = indicator.getAnnualized();
        this.numerator = indicator.getNumerator();
        this.numeratorDescription = indicator.getNumeratorDescription();
        this.denominator = indicator.getDenominator();
        this.denominatorDescription = indicator.getDenominatorDescription();
        this.displayNumeratorDescription = indicator.getDisplayNumeratorDescription();
        this.displayDenominatorDescription = indicator.getDisplayDenominatorDescription();
        this.dimensionItem = indicator.getDimensionItem();
        this.track = indicator.getTrack();
        this.project = indicator.getProject();
        this.indicatorType = indicator.getIndicatorType();
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

    public Boolean getAnnualized() {
        return annualized;
    }

    public void setAnnualized(Boolean annualized) {
        this.annualized = annualized;
    }

    public String getNumerator() {
        return numerator;
    }

    public void setNumerator(String numerator) {
        this.numerator = numerator;
    }

    public String getNumeratorDescription() {
        return numeratorDescription;
    }

    public void setNumeratorDescription(String numeratorDescription) {
        this.numeratorDescription = numeratorDescription;
    }

    public String getDenominator() {
        return denominator;
    }

    public void setDenominator(String denominator) {
        this.denominator = denominator;
    }

    public String getDenominatorDescription() {
        return denominatorDescription;
    }

    public void setDenominatorDescription(String denominatorDescription) {
        this.denominatorDescription = denominatorDescription;
    }

    public String getDisplayNumeratorDescription() {
        return displayNumeratorDescription;
    }

    public void setDisplayNumeratorDescription(String displayNumeratorDescription) {
        this.displayNumeratorDescription = displayNumeratorDescription;
    }

    public String getDisplayDenominatorDescription() {
        return displayDenominatorDescription;
    }

    public void setDisplayDenominatorDescription(String displayDenominatorDescription) {
        this.displayDenominatorDescription = displayDenominatorDescription;
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

    public IndicatorType getIndicatorType() {
        return indicatorType;
    }

    public void setIndicatorType(IndicatorType indicatorType) {
        this.indicatorType = indicatorType;
    }
}
