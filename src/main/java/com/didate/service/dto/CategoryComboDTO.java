package com.didate.service.dto;

import com.didate.domain.CategoryCombo;

public class CategoryComboDTO extends AbstractDTO {

    public CategoryComboDTO(CategoryCombo categoryCombo) {
        super(
            categoryCombo.getId(),
            categoryCombo.getName(),
            categoryCombo.getShortName(),
            categoryCombo.getCreated().toString(),
            categoryCombo.getLastUpdated().toString(),
            categoryCombo.getCreatedBy(),
            categoryCombo.getLastUpdatedBy()
        );
    }

    private Integer revisionNumber;

    public Integer getRevisionNumber() {
        return revisionNumber;
    }

    public void setRevisionNumber(Integer revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    public CategoryComboDTO revisionNumber(Integer revisionNumber) {
        setRevisionNumber(revisionNumber);
        return this;
    }
}
