package com.didate.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A PersonNotifier.
 */
@Entity
@Table(name = "person_notifier")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PersonNotifier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "person_name", nullable = false)
    private String personName;

    @NotNull
    @Column(name = "person_phone", nullable = false)
    private String personPhone;

    @NotNull
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "person_email", nullable = false)
    private String personEmail;

    @NotNull
    @Column(name = "person_organization", nullable = false)
    private String personOrganization;

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PersonNotifier id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPersonName() {
        return this.personName;
    }

    public PersonNotifier personName(String personName) {
        this.setPersonName(personName);
        return this;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonPhone() {
        return this.personPhone;
    }

    public PersonNotifier personPhone(String personPhone) {
        this.setPersonPhone(personPhone);
        return this;
    }

    public void setPersonPhone(String personPhone) {
        this.personPhone = personPhone;
    }

    public String getPersonEmail() {
        return this.personEmail;
    }

    public PersonNotifier personEmail(String personEmail) {
        this.setPersonEmail(personEmail);
        return this;
    }

    public void setPersonEmail(String personEmail) {
        this.personEmail = personEmail;
    }

    public String getPersonOrganization() {
        return this.personOrganization;
    }

    public PersonNotifier personOrganization(String personOrganization) {
        this.setPersonOrganization(personOrganization);
        return this;
    }

    public void setPersonOrganization(String personOrganization) {
        this.personOrganization = personOrganization;
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public PersonNotifier project(Project project) {
        this.setProject(project);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonNotifier)) {
            return false;
        }
        return getId() != null && getId().equals(((PersonNotifier) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonNotifier{" +
            "id=" + getId() +
            ", personName='" + getPersonName() + "'" +
            ", personPhone='" + getPersonPhone() + "'" +
            ", personEmail='" + getPersonEmail() + "'" +
            ", personOrganization='" + getPersonOrganization() + "'" +
            "}";
    }
}
