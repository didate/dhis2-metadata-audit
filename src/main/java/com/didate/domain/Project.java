package com.didate.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Project.
 */
@Entity
@Table(name = "project")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "project_name", nullable = false)
    private String projectName;

    @NotNull
    @Column(name = "dhis_2_url", nullable = false)
    private String dhis2URL;

    @NotNull
    @Column(name = "dhis_2_version", nullable = false)
    private Double dhis2Version;

    @NotNull
    @Column(name = "token", nullable = false)
    private String token;

    @NotNull
    @Column(name = "email_notification", nullable = false)
    private Boolean emailNotification;

    @Column(name = "notification_time")
    private String notificationTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Project id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public Project projectName(String projectName) {
        this.setProjectName(projectName);
        return this;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDhis2URL() {
        return this.dhis2URL;
    }

    public Project dhis2URL(String dhis2URL) {
        this.setDhis2URL(dhis2URL);
        return this;
    }

    public void setDhis2URL(String dhis2URL) {
        this.dhis2URL = dhis2URL;
    }

    public Double getDhis2Version() {
        return this.dhis2Version;
    }

    public Project dhis2Version(Double dhis2Version) {
        this.setDhis2Version(dhis2Version);
        return this;
    }

    public void setDhis2Version(Double dhis2Version) {
        this.dhis2Version = dhis2Version;
    }

    public String getToken() {
        return this.token;
    }

    public Project token(String token) {
        this.setToken(token);
        return this;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getEmailNotification() {
        return this.emailNotification;
    }

    public Project emailNotification(Boolean emailNotification) {
        this.setEmailNotification(emailNotification);
        return this;
    }

    public void setEmailNotification(Boolean emailNotification) {
        this.emailNotification = emailNotification;
    }

    public String getNotificationTime() {
        return this.notificationTime;
    }

    public Project notificationTime(String notificationTime) {
        this.setNotificationTime(notificationTime);
        return this;
    }

    public void setNotificationTime(String notificationTime) {
        this.notificationTime = notificationTime;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        return getId() != null && getId().equals(((Project) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Project{" +
            "id=" + getId() +
            ", projectName='" + getProjectName() + "'" +
            ", dhis2URL='" + getDhis2URL() + "'" +
            ", dhis2Version=" + getDhis2Version() +
            ", token='" + getToken() + "'" +
            ", emailNotification='" + getEmailNotification() + "'" +
            ", notificationTime='" + getNotificationTime() + "'" +
            "}";
    }
}
