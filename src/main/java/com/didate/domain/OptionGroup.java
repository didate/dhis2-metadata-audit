package com.didate.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import org.hibernate.envers.Audited;

/**
 * A OptionGroup.
 */
@Entity
@Table(name = "option_group")
@Audited
@JsonIgnoreProperties(ignoreUnknown = true)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OptionGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public OptionGroup id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OptionGroup)) {
            return false;
        }
        return getId() != null && getId().equals(((OptionGroup) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OptionGroup{" +
            "id=" + getId() +
            "}";
    }
}
