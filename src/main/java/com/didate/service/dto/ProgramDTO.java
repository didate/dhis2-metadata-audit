package com.didate.service.dto;

import com.didate.domain.Program;

public class ProgramDTO extends AbstractDTO {

    private String programType;
    private Integer revisionNumber;

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

    public Integer getRevisionNumber() {
        return revisionNumber;
    }

    public void setRevisionNumber(Integer revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    public ProgramDTO revisionNumber(Integer revisionNumber) {
        setRevisionNumber(revisionNumber);
        return this;
    }
}
