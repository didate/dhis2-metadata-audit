package com.didate.service.dto;

import com.didate.domain.TrackedEntityAttribute;

public class TrackedEntityAttributeDTO extends AbstractDTO {

    public TrackedEntityAttributeDTO(TrackedEntityAttribute trackedEntityAttribute) {
        super(
            trackedEntityAttribute.getId(),
            trackedEntityAttribute.getName(),
            trackedEntityAttribute.getShortName(),
            trackedEntityAttribute.getCreated().toString(),
            trackedEntityAttribute.getLastUpdated().toString(),
            trackedEntityAttribute.getCreatedBy(),
            trackedEntityAttribute.getLastUpdatedBy()
        );
    }

    private Integer revisionNumber;

    public Integer getRevisionNumber() {
        return revisionNumber;
    }

    public void setRevisionNumber(Integer revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    public TrackedEntityAttributeDTO revisionNumber(Integer revisionNumber) {
        setRevisionNumber(revisionNumber);
        return this;
    }
}
