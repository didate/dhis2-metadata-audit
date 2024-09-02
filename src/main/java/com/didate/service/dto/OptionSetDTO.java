package com.didate.service.dto;

import com.didate.domain.OptionSet;

public class OptionSetDTO extends AbstractDTO {

    public OptionSetDTO(OptionSet optionSet) {
        super(
            optionSet.getId(),
            optionSet.getName(),
            optionSet.getShortName(),
            optionSet.getCreated(),
            optionSet.getLastUpdated(),
            optionSet.getCreatedBy(),
            optionSet.getLastUpdatedBy()
        );
    }

    private Integer revisionNumber;

    public Integer getRevisionNumber() {
        return revisionNumber;
    }

    public void setRevisionNumber(Integer revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    public OptionSetDTO revisionNumber(Integer revisionNumber) {
        setRevisionNumber(revisionNumber);
        return this;
    }
}
