package com.didate.service.dto;

import com.didate.domain.ProgramRule;
import com.didate.domain.Project;
import com.didate.domain.enumeration.TypeTrack;

public class ProgramRuleFullDTO extends ProgramRuleDTO {

    private String displayName;
    private Integer priority;
    private String condition;
    private TypeTrack track;
    private Project project;

    public ProgramRuleFullDTO(ProgramRule programRule) {
        super(programRule);
        this.displayName = programRule.getDisplayName();
        this.priority = programRule.getPriority();
        this.condition = programRule.getCondition();
        this.track = programRule.getTrack();
        this.project = programRule.getProject();
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
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
}
