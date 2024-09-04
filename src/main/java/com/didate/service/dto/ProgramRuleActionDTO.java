package com.didate.service.dto;

import com.didate.domain.ProgramRuleAction;

public class ProgramRuleActionDTO extends AbstractDTO {

    public ProgramRuleActionDTO(ProgramRuleAction programRuleAction) {
        super(
            programRuleAction.getId(),
            programRuleAction.getCreated(),
            programRuleAction.getLastUpdated(),
            programRuleAction.getCreatedBy(),
            programRuleAction.getLastUpdatedBy()
        );
        this.programRule = new ProgramRuleDTO(programRuleAction.getProgramRule());
    }

    private Integer revisionNumber;
    private ProgramRuleDTO programRule;

    public Integer getRevisionNumber() {
        return revisionNumber;
    }

    public void setRevisionNumber(Integer revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    public ProgramRuleActionDTO revisionNumber(Integer revisionNumber) {
        setRevisionNumber(revisionNumber);
        return this;
    }

    public ProgramRuleDTO getProgramRule() {
        return programRule;
    }

    public void setProgramRule(ProgramRuleDTO programRule) {
        this.programRule = programRule;
    }
}
