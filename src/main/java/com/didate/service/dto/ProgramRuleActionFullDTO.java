package com.didate.service.dto;

import com.didate.domain.DHISUser;
import com.didate.domain.DataElement;
import com.didate.domain.OptionGroup;
import com.didate.domain.ProgramRule;
import com.didate.domain.ProgramRuleAction;
import com.didate.domain.Project;
import com.didate.domain.TrackedEntityAttribute;
import com.didate.domain.enumeration.TypeTrack;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import org.hibernate.envers.NotAudited;

public class ProgramRuleActionFullDTO extends ProgramRuleActionDTO {

    private String programRuleActionType;

    private String evaluationTime;

    private String data;

    private String templateUid;

    private String content;

    private String displayContent;

    private TypeTrack track;

    private Project project;

    private ProgramRule programRule;

    private TrackedEntityAttribute trackedEntityAttribute;

    private DataElement dataElement;

    private OptionGroup optionGroup;

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
        this.programRule = programRuleAction.getProgramRule();
        this.trackedEntityAttribute = programRuleAction.getTrackedEntityAttribute();
        this.dataElement = programRuleAction.getDataElement();
        this.optionGroup = programRuleAction.getOptionGroup();
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

    public ProgramRule getProgramRule() {
        return programRule;
    }

    public void setProgramRule(ProgramRule programRule) {
        this.programRule = programRule;
    }

    public TrackedEntityAttribute getTrackedEntityAttribute() {
        return trackedEntityAttribute;
    }

    public void setTrackedEntityAttribute(TrackedEntityAttribute trackedEntityAttribute) {
        this.trackedEntityAttribute = trackedEntityAttribute;
    }

    public DataElement getDataElement() {
        return dataElement;
    }

    public void setDataElement(DataElement dataElement) {
        this.dataElement = dataElement;
    }

    public OptionGroup getOptionGroup() {
        return optionGroup;
    }

    public void setOptionGroup(OptionGroup optionGroup) {
        this.optionGroup = optionGroup;
    }
}
