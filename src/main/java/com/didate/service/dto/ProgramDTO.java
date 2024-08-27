package com.didate.service.dto;

import com.didate.domain.Program;

public class ProgramDTO extends AbstractDTO {

    private String programType;

    public ProgramDTO(Program program) {
        super(
            program.getId(),
            program.getName(),
            program.getCreated(),
            program.getLastUpdated(),
            program.getCreatedBy(),
            program.getLastUpdatedBy()
        );
        this.programType = program.getProgramType();
    }

    public String getProgramType() {
        return programType;
    }

    public void setProgramType(String programType) {
        this.programType = programType;
    }
}
