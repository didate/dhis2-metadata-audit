package com.didate.service.dto;

import com.didate.domain.ProgramRule;

public class ProgramRuleDTO extends AbstractDTO {

    public ProgramRuleDTO(ProgramRule programRule) {
        super(
            programRule.getId(),
            programRule.getName(),
            programRule.getCreated(),
            programRule.getLastUpdated(),
            programRule.getCreatedBy(),
            programRule.getLastUpdatedBy()
        );
        this.program = new ProgramDTO(programRule.getProgram());
    }

    private Integer revisionNumber;
    private ProgramDTO program;

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

    public ProgramDTO getProgram() {
        return program;
    }

    public void setProgram(ProgramDTO program) {
        this.program = program;
    }
}
