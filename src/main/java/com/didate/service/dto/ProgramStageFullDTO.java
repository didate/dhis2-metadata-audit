package com.didate.service.dto;

import com.didate.domain.ProgramStage;
import java.io.Serializable;

public class ProgramStageFullDTO extends AbstractDTO implements Serializable {

    private Integer minDaysFromStart;
    private String executionDateLabel;
    private Boolean autoGenerateEvent;
    private String validationStrategy;
    private Boolean displayGenerateEventBox;
    private String featureType;
    private Boolean blockEntryForm;
    private Boolean preGenerateUID;
    private Boolean remindCompleted;
    private Boolean generatedByEnrollmentDate;
    private Boolean allowGenerateNextVisit;
    private Boolean openAfterEnrollment;
    private Integer sortOrder;
    private Boolean hideDueDate;
    private Boolean enableUserAssignment;
    private Boolean referral;
    private String displayExecutionDateLabel;
    private String formType;
    private String displayFormName;
    private String displayName;
    private Boolean repeatable;
    private Integer programStageDataElementsCount;
    private String programStageDataElementsContent;
    private ProgramDTO program;

    public ProgramStageFullDTO() {
        // Default constructor
    }

    public ProgramStageFullDTO(ProgramStage programStage) {
        super(
            programStage.getId(),
            programStage.getName(),
            programStage.getCreated(),
            programStage.getLastUpdated(),
            programStage.getCreatedBy(),
            programStage.getLastUpdatedBy()
        );
        this.minDaysFromStart = programStage.getMinDaysFromStart();
        this.executionDateLabel = programStage.getExecutionDateLabel();
        this.autoGenerateEvent = programStage.getAutoGenerateEvent();
        this.validationStrategy = programStage.getValidationStrategy();
        this.displayGenerateEventBox = programStage.getDisplayGenerateEventBox();
        this.featureType = programStage.getFeatureType();
        this.blockEntryForm = programStage.getBlockEntryForm();
        this.preGenerateUID = programStage.getPreGenerateUID();
        this.remindCompleted = programStage.getRemindCompleted();
        this.generatedByEnrollmentDate = programStage.getGeneratedByEnrollmentDate();
        this.allowGenerateNextVisit = programStage.getAllowGenerateNextVisit();
        this.openAfterEnrollment = programStage.getOpenAfterEnrollment();
        this.sortOrder = programStage.getSortOrder();
        this.hideDueDate = programStage.getHideDueDate();
        this.enableUserAssignment = programStage.getEnableUserAssignment();
        this.referral = programStage.getReferral();
        this.displayExecutionDateLabel = programStage.getDisplayExecutionDateLabel();
        this.formType = programStage.getFormType();
        this.displayFormName = programStage.getDisplayFormName();
        this.displayName = programStage.getDisplayName();
        this.repeatable = programStage.getRepeatable();
        this.programStageDataElementsCount = programStage.getProgramStageDataElementsCount();
        this.programStageDataElementsContent = programStage.getProgramStageDataElementsContent();
        this.program = new ProgramDTO(programStage.getProgram());
    }

    // Getters and Setters
    public Integer getMinDaysFromStart() {
        return minDaysFromStart;
    }

    public void setMinDaysFromStart(Integer minDaysFromStart) {
        this.minDaysFromStart = minDaysFromStart;
    }

    public String getExecutionDateLabel() {
        return executionDateLabel;
    }

    public void setExecutionDateLabel(String executionDateLabel) {
        this.executionDateLabel = executionDateLabel;
    }

    public Boolean getAutoGenerateEvent() {
        return autoGenerateEvent;
    }

    public void setAutoGenerateEvent(Boolean autoGenerateEvent) {
        this.autoGenerateEvent = autoGenerateEvent;
    }

    public String getValidationStrategy() {
        return validationStrategy;
    }

    public void setValidationStrategy(String validationStrategy) {
        this.validationStrategy = validationStrategy;
    }

    public Boolean getDisplayGenerateEventBox() {
        return displayGenerateEventBox;
    }

    public void setDisplayGenerateEventBox(Boolean displayGenerateEventBox) {
        this.displayGenerateEventBox = displayGenerateEventBox;
    }

    public String getFeatureType() {
        return featureType;
    }

    public void setFeatureType(String featureType) {
        this.featureType = featureType;
    }

    public Boolean getBlockEntryForm() {
        return blockEntryForm;
    }

    public void setBlockEntryForm(Boolean blockEntryForm) {
        this.blockEntryForm = blockEntryForm;
    }

    public Boolean getPreGenerateUID() {
        return preGenerateUID;
    }

    public void setPreGenerateUID(Boolean preGenerateUID) {
        this.preGenerateUID = preGenerateUID;
    }

    public Boolean getRemindCompleted() {
        return remindCompleted;
    }

    public void setRemindCompleted(Boolean remindCompleted) {
        this.remindCompleted = remindCompleted;
    }

    public Boolean getGeneratedByEnrollmentDate() {
        return generatedByEnrollmentDate;
    }

    public void setGeneratedByEnrollmentDate(Boolean generatedByEnrollmentDate) {
        this.generatedByEnrollmentDate = generatedByEnrollmentDate;
    }

    public Boolean getAllowGenerateNextVisit() {
        return allowGenerateNextVisit;
    }

    public void setAllowGenerateNextVisit(Boolean allowGenerateNextVisit) {
        this.allowGenerateNextVisit = allowGenerateNextVisit;
    }

    public Boolean getOpenAfterEnrollment() {
        return openAfterEnrollment;
    }

    public void setOpenAfterEnrollment(Boolean openAfterEnrollment) {
        this.openAfterEnrollment = openAfterEnrollment;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean getHideDueDate() {
        return hideDueDate;
    }

    public void setHideDueDate(Boolean hideDueDate) {
        this.hideDueDate = hideDueDate;
    }

    public Boolean getEnableUserAssignment() {
        return enableUserAssignment;
    }

    public void setEnableUserAssignment(Boolean enableUserAssignment) {
        this.enableUserAssignment = enableUserAssignment;
    }

    public Boolean getReferral() {
        return referral;
    }

    public void setReferral(Boolean referral) {
        this.referral = referral;
    }

    public String getDisplayExecutionDateLabel() {
        return displayExecutionDateLabel;
    }

    public void setDisplayExecutionDateLabel(String displayExecutionDateLabel) {
        this.displayExecutionDateLabel = displayExecutionDateLabel;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
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

    public Boolean getRepeatable() {
        return repeatable;
    }

    public void setRepeatable(Boolean repeatable) {
        this.repeatable = repeatable;
    }

    public Integer getProgramStageDataElementsCount() {
        return programStageDataElementsCount;
    }

    public void setProgramStageDataElementsCount(Integer programStageDataElementsCount) {
        this.programStageDataElementsCount = programStageDataElementsCount;
    }

    public String getProgramStageDataElementsContent() {
        return programStageDataElementsContent;
    }

    public void setProgramStageDataElementsContent(String programStageDataElementsContent) {
        this.programStageDataElementsContent = programStageDataElementsContent;
    }

    public ProgramDTO getProgram() {
        return program;
    }

    public void setProgram(ProgramDTO program) {
        this.program = program;
    }
}
