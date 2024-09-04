package com.didate.service.dto;

import com.didate.domain.ProgramRuleAction;
import com.didate.domain.Project;
import com.didate.domain.enumeration.TypeTrack;

public class ProgramRuleActionFullDTO extends ProgramRuleActionDTO {

    private String programRuleActionType;

    private String evaluationTime;

    private String data;

    private String templateUid;

    private String content;

    private String displayContent;

    private TypeTrack track;

    private Project project;

    private TrackedEntityAttributeDTO trackedEntityAttribute;

    private DataElementDTO dataElement;

    private OptionGroupDTO optionGroup;

    public ProgramRuleActionFullDTO(ProgramRuleAction programRuleAction) {
        super(programRuleAction);
        this.programRuleActionType = programRuleAction.getProgramRuleActionType();
        this.evaluationTime = programRuleAction.getEvaluationTime();
        this.data = programRuleAction.getData();
        this.templateUid = programRuleAction.getTemplateUid();
        this.content = programRuleAction.getContent();
        this.displayContent = programRuleAction.getDisplayContent();
        this.track = programRuleAction.getTrack();
        this.project = programRuleAction.getProject();
        this.trackedEntityAttribute = new TrackedEntityAttributeDTO(programRuleAction.getTrackedEntityAttribute());
        this.dataElement = new DataElementDTO(programRuleAction.getDataElement());
        this.optionGroup = new OptionGroupDTO(programRuleAction.getOptionGroup());
    }

    public String getProgramRuleActionType() {
        return programRuleActionType;
    }

    public void setProgramRuleActionType(String programRuleActionType) {
        this.programRuleActionType = programRuleActionType;
    }

    public String getEvaluationTime() {
        return evaluationTime;
    }

    public void setEvaluationTime(String evaluationTime) {
        this.evaluationTime = evaluationTime;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTemplateUid() {
        return templateUid;
    }

    public void setTemplateUid(String templateUid) {
        this.templateUid = templateUid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDisplayContent() {
        return displayContent;
    }

    public void setDisplayContent(String displayContent) {
        this.displayContent = displayContent;
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

    public TrackedEntityAttributeDTO getTrackedEntityAttribute() {
        return trackedEntityAttribute;
    }

    public void setTrackedEntityAttribute(TrackedEntityAttributeDTO trackedEntityAttribute) {
        this.trackedEntityAttribute = trackedEntityAttribute;
    }

    public DataElementDTO getDataElement() {
        return dataElement;
    }

    public void setDataElement(DataElementDTO dataElement) {
        this.dataElement = dataElement;
    }

    public OptionGroupDTO getOptionGroup() {
        return optionGroup;
    }

    public void setOptionGroup(OptionGroupDTO optionGroup) {
        this.optionGroup = optionGroup;
    }
}
