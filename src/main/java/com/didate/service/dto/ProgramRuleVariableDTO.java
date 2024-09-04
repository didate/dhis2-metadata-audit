package com.didate.service.dto;

import com.didate.domain.ProgramRuleVariable;

public class ProgramRuleVariableDTO extends AbstractDTO {

    private Integer revisionNumber;
    private ProgramDTO program;

    public ProgramRuleVariableDTO(ProgramRuleVariable programRuleVariable) {
        super(
            programRuleVariable.getId(),
            programRuleVariable.getName(),
            programRuleVariable.getCreated(),
            programRuleVariable.getLastUpdated(),
            programRuleVariable.getCreatedBy(),
            programRuleVariable.getLastUpdatedBy()
        );
        this.program = new ProgramDTO(programRuleVariable.getProgram());
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

    public ProgramDTO getProgram() {
        return program;
    }

    public void setProgram(ProgramDTO program) {
        this.program = program;
    }
}
