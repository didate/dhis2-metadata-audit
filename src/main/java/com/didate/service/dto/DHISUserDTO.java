package com.didate.service.dto;

import com.didate.domain.DHISUser;
import java.time.Instant;

public class DHISUserDTO extends AbstractDTO {

    private String username;
    private Instant lastLogin;
    private Boolean disabled;
    private Integer revisionNumber;

    public DHISUserDTO(DHISUser dhisUser) {
        super(
            dhisUser.getId(),
            dhisUser.getName(),
            dhisUser.getCreated(),
            dhisUser.getLastUpdated(),
            new DHISUser().username(dhisUser.getCreatedBy()),
            new DHISUser().username(dhisUser.getLastUpdatedBy())
        );
        this.disabled = dhisUser.getDisabled();
        this.username = dhisUser.getUsername();
        this.lastLogin = dhisUser.getLastLogin();
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Instant getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Instant lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }
}
