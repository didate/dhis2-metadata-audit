package com.didate.service.dto;

import com.didate.domain.OptionGroup;

public class OptionGroupDTO extends AbstractDTO {

    public OptionGroupDTO(OptionGroup optionGroup) {
        super(
            optionGroup.getId(),
            optionGroup.getName(),
            optionGroup.getShortName(),
            optionGroup.getCreated(),
            optionGroup.getLastUpdated(),
            optionGroup.getCreatedBy(),
            optionGroup.getLastUpdatedBy()
        );
    }

    private Integer revisionNumber;

    public Integer getRevisionNumber() {
        return revisionNumber;
    }

    public void setRevisionNumber(Integer revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    public OptionGroupDTO revisionNumber(Integer revisionNumber) {
        setRevisionNumber(revisionNumber);
        return this;
    }
}
