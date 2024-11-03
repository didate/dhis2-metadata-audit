package com.didate.domain;

import com.didate.deserialize.UsernameDeserializer;
import com.didate.domain.enumeration.TypeTrack;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.domain.Persistable;

/**
 * A DHISUser.
 */
@JsonIgnoreProperties(value = { "new" }, ignoreUnknown = true)
@Entity
@Table(name = "dhis_user")
@Audited
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DHISUser implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "display_name")
    private String displayName;

    @NotNull
    @Column(name = "username", nullable = false)
    @JsonProperty("username")
    private String username;

    @Column(name = "last_login")
    private Instant lastLogin;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "disabled")
    private Boolean disabled;

    @Column(name = "password_last_updated")
    @JsonProperty("passwordLastUpdated")
    private Instant passwordLastUpdated;

    @Column(name = "created")
    private Instant created;

    @Column(name = "last_updated")
    private Instant lastUpdated;

    @Column(name = "created_by")
    @JsonDeserialize(using = UsernameDeserializer.class)
    private String createdBy;

    @Column(name = "last_updated_by")
    @JsonDeserialize(using = UsernameDeserializer.class)
    private String lastUpdatedBy;

    @NotAudited
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "track", nullable = false)
    private TypeTrack track;

    @ManyToOne
    private Project project;

    @Transient
    private boolean isPersisted;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public DHISUser id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public DHISUser code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public DHISUser name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public DHISUser displayName(String displayName) {
        this.setDisplayName(displayName);
        return this;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUsername() {
        return this.username;
    }

    public DHISUser username(String username) {
        this.setUsername(username);
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Instant getLastLogin() {
        return this.lastLogin;
    }

    public DHISUser lastLogin(Instant lastLogin) {
        this.setLastLogin(lastLogin);
        return this;
    }

    public void setLastLogin(Instant lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getEmail() {
        return this.email;
    }

    public DHISUser email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public DHISUser phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getDisabled() {
        return this.disabled;
    }

    public DHISUser disabled(Boolean disabled) {
        this.setDisabled(disabled);
        return this;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Instant getPasswordLastUpdated() {
        return this.passwordLastUpdated;
    }

    public DHISUser passwordLastUpdated(Instant passwordLastUpdated) {
        this.setPasswordLastUpdated(passwordLastUpdated);
        return this;
    }

    public void setPasswordLastUpdated(Instant passwordLastUpdated) {
        this.passwordLastUpdated = passwordLastUpdated;
    }

    public Instant getCreated() {
        return this.created;
    }

    public DHISUser created(Instant created) {
        this.setCreated(created);
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getLastUpdated() {
        return this.lastUpdated;
    }

    public DHISUser lastUpdated(Instant lastUpdated) {
        this.setLastUpdated(lastUpdated);
        return this;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public TypeTrack getTrack() {
        return this.track;
    }

    public DHISUser track(TypeTrack track) {
        this.setTrack(track);
        return this;
    }

    public void setTrack(TypeTrack track) {
        this.track = track;
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public DHISUser project(Project project) {
        this.setProject(project);
        return this;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public DHISUser setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DHISUser)) {
            return false;
        }
        return id != null && id.equals(((DHISUser) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public DHISUser createdBy(String createdBy) {
        setCreatedBy(createdBy);
        return this;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public DHISUser lastUpdatedBy(String lastUpdatedBy) {
        setLastUpdatedBy(lastUpdatedBy);
        return this;
    }

    public boolean isPersisted() {
        return isPersisted;
    }

    public void setPersisted(boolean isPersisted) {
        this.isPersisted = isPersisted;
    }

    @Override
    public String toString() {
        return (
            "DHISUser [id=" +
            id +
            ", name=" +
            name +
            ", code=" +
            code +
            ", displayName=" +
            displayName +
            ", username=" +
            username +
            ", lastLogin=" +
            lastLogin +
            ", email=" +
            email +
            ", phoneNumber=" +
            phoneNumber +
            ", disabled=" +
            disabled +
            ", passwordLastUpdated=" +
            passwordLastUpdated +
            ", created=" +
            created +
            ", lastUpdated=" +
            lastUpdated +
            ", createdBy=" +
            createdBy +
            ", lastUpdatedBy=" +
            lastUpdatedBy +
            ", track=" +
            track +
            ", project=" +
            project +
            "]"
        );
    }
}
