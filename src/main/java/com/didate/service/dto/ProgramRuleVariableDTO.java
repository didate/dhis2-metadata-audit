package com.didate.service.dto;

import com.didate.domain.ProgramRuleVariable;

public class ProgramRuleVariableDTO extends AbstractDTO {

    private Integer revisionNumber;

    public ProgramRuleVariableDTO(ProgramRuleVariable programRuleVariable) {
        super(
            programRuleVariable.getId(),
            programRuleVariable.getName(),
            programRuleVariable.getCreated().toString(),
            programRuleVariable.getLastUpdated().toString(),
            programRuleVariable.getCreatedBy(),
            programRuleVariable.getLastUpdatedBy()
        );
    }

    public Integer getRevisionNumber() {
        return revisionNumber;
    }

    public void setRevisionNumber(Integer revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    public ProgramRuleVariableDTO revisionNumber(Integer revisionNumber) {
        setRevisionNumber(revisionNumber);
        return this;
    }
}
