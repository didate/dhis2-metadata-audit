package com.didate.service.dto;

import com.didate.domain.DHISUser;

public class DHISUserDTO extends AbstractDTO {

    public DHISUserDTO(DHISUser dhisUser) {
        super(dhisUser.getId(), dhisUser.getName(), dhisUser.getCreated(), dhisUser.getLastUpdated());
    }

    private Integer revisionNumber;

    public Integer getRevisionNumber() {
        return revisionNumber;
    }

    public void setRevisionNumber(Integer revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    public DHISUserDTO revisionNumber(Integer revisionNumber) {
        setRevisionNumber(revisionNumber);
        return this;
    }
}
