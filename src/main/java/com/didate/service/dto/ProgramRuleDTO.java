package com.didate.service.dto;

import com.didate.domain.Program;
import com.didate.domain.ProgramRule;

public class ProgramRuleDTO extends AbstractDTO {

    public ProgramRuleDTO(ProgramRule programRule) {
        super(
            programRule.getId(),
            programRule.getName(),
            programRule.getCreated().toString(),
            programRule.getLastUpdated().toString(),
            programRule.getCreatedBy(),
            programRule.getLastUpdatedBy()
        );
    }

    private Integer revisionNumber;

    public Integer getRevisionNumber() {
        return revisionNumber;
    }

    public void setRevisionNumber(Integer revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    public ProgramRuleDTO revisionNumber(Integer revisionNumber) {
        setRevisionNumber(revisionNumber);
        return this;
    }
}
