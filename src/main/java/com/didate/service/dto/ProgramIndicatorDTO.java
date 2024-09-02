package com.didate.service.dto;

import com.didate.domain.CategoryCombo;
import com.didate.domain.ProgramIndicator;

public class ProgramIndicatorDTO extends AbstractDTO {

    public ProgramIndicatorDTO(ProgramIndicator programIndicator) {
        super(
            programIndicator.getId(),
            programIndicator.getName(),
            programIndicator.getShortName(),
            programIndicator.getCreated(),
            programIndicator.getLastUpdated(),
            programIndicator.getCreatedBy(),
            programIndicator.getLastUpdatedBy()
        );
    }

    private Integer revisionNumber;

    public Integer getRevisionNumber() {
        return revisionNumber;
    }

    public void setRevisionNumber(Integer revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    public ProgramIndicatorDTO revisionNumber(Integer revisionNumber) {
        setRevisionNumber(revisionNumber);
        return this;
    }
}
