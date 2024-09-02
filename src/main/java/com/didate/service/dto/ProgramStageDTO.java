package com.didate.service.dto;

import com.didate.domain.ProgramStage;

public class ProgramStageDTO extends AbstractDTO {

    private ProgramDTO program;
    private Boolean repeatable;

    public ProgramStageDTO(ProgramStage programStage) {
        super(
            programStage.getId(),
            programStage.getName(),
            programStage.getCreated(),
            programStage.getLastUpdated(),
            programStage.getCreatedBy(),
            programStage.getLastUpdatedBy()
        );
        this.program = new ProgramDTO(programStage.getProgram());
        this.repeatable = programStage.getRepeatable();
    }

    private Integer revisionNumber;

    public Integer getRevisionNumber() {
        return revisionNumber;
    }

    public void setRevisionNumber(Integer revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    public ProgramStageDTO revisionNumber(Integer revisionNumber) {
        setRevisionNumber(revisionNumber);
        return this;
    }

    public ProgramDTO getProgram() {
        return program;
    }

    public void setProgram(ProgramDTO program) {
        this.program = program;
    }

    public Boolean getRepeatable() {
        return repeatable;
    }

    public void setRepeatable(Boolean repeatable) {
        this.repeatable = repeatable;
    }
}
