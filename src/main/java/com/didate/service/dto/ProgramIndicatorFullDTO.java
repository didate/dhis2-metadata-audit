package com.didate.service.dto;

import com.didate.domain.ProgramIndicator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class ProgramIndicatorFullDTO extends ProgramIndicatorDTO {

    private String dimensionItemType;

    private String expression;

    private String filter;

    private String analyticsType;

    private String dimensionItem;

    private String displayShortName;

    private String displayName;

    private String displayFormName;

    @JsonIgnoreProperties(value = "programIndicators", allowSetters = true)
    private ProgramDTO program;

    public ProgramIndicatorFullDTO(ProgramIndicator programIndicator) {
        super(programIndicator);
        this.dimensionItemType = programIndicator.getDimensionItemType();
        this.expression = programIndicator.getExpression();
        this.filter = programIndicator.getFilter();
        this.analyticsType = programIndicator.getAnalyticsType();
        this.dimensionItem = programIndicator.getDimensionItem();
        this.displayShortName = programIndicator.getDisplayShortName();
        this.displayName = programIndicator.getDisplayName();
        this.displayFormName = programIndicator.getDisplayFormName();
        this.program = programIndicator.getProgram() != null ? new ProgramDTO(programIndicator.getProgram()) : null;
    }

    public String getDimensionItemType() {
        return dimensionItemType;
    }

    public void setDimensionItemType(String dimensionItemType) {
        this.dimensionItemType = dimensionItemType;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getAnalyticsType() {
        return analyticsType;
    }

    public void setAnalyticsType(String analyticsType) {
        this.analyticsType = analyticsType;
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

    public ProgramDTO getProgram() {
        return program;
    }

    public void setProgram(ProgramDTO program) {
        this.program = program;
    }
}
