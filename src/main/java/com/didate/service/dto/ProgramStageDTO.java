package com.didate.service.dto;

import com.didate.domain.ProgramStage;

public class ProgramStageDTO extends AbstractDTO {

    private ProgramDTO program;
    private Boolean repeatable;

    public ProgramStageDTO(ProgramStage programStage) {
        super(
            programStage.getId(),
            programStage.getName(),
            programStage.getCreated().toString(),
            programStage.getLastUpdated().toString(),
            programStage.getCreatedBy(),
            programStage.getLastUpdatedBy()
        );
        this.program = new ProgramDTO(programStage.getProgram());
        this.repeatable = programStage.getRepeatable();
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
