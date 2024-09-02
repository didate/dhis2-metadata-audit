package com.didate.service.dto;

import com.didate.domain.DHISUser;
import java.time.Instant;

public class DHISUserFullDTO extends DHISUserDTO {

    private String code;
    private String displayName;
    private String username;
    private Instant lastLogin;
    private String email;
    private String phoneNumber;
    private Boolean disabled;
    private Instant passwordLastUpdated;

    public DHISUserFullDTO(DHISUser dhisUser) {
        super(dhisUser);
        this.code = dhisUser.getCode();
        this.displayName = dhisUser.getDisplayName();
        this.username = dhisUser.getUsername();
        this.lastLogin = dhisUser.getLastLogin();
        this.email = dhisUser.getEmail();
        this.phoneNumber = dhisUser.getPhoneNumber();
        this.disabled = dhisUser.getDisabled();
        this.passwordLastUpdated = dhisUser.getPasswordLastUpdated();
    }

    // Getters and Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Instant getPasswordLastUpdated() {
        return passwordLastUpdated;
    }

    public void setPasswordLastUpdated(Instant passwordLastUpdated) {
        this.passwordLastUpdated = passwordLastUpdated;
    }
}
