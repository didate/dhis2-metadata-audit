package com.didate.domain;

import com.didate.deserialize.DataElementSetDeserializer;
import com.didate.domain.enumeration.TypeTrack;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.domain.Persistable;

/**
 * A ProgramStage.
 */
@JsonIgnoreProperties(value = { "new" }, ignoreUnknown = true)
@Entity
@Table(name = "program_stage")
@Audited
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProgramStage implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "created")
    private Instant created;

    @Column(name = "last_updated")
    private Instant lastUpdated;

    @Column(name = "min_days_from_start")
    private Integer minDaysFromStart;

    @Column(name = "execution_date_label")
    private String executionDateLabel;

    @Column(name = "auto_generate_event")
    private Boolean autoGenerateEvent;

    @Column(name = "validation_strategy")
    private String validationStrategy;

    @Column(name = "display_generate_event_box")
    private Boolean displayGenerateEventBox;

    @Column(name = "feature_type")
    private String featureType;

    @Column(name = "block_entry_form")
    private Boolean blockEntryForm;

    @Column(name = "pre_generate_uid")
    private Boolean preGenerateUID;

    @Column(name = "remind_completed")
    private Boolean remindCompleted;

    @Column(name = "generated_by_enrollment_date")
    private Boolean generatedByEnrollmentDate;

    @Column(name = "allow_generate_next_visit")
    private Boolean allowGenerateNextVisit;

    @Column(name = "open_after_enrollment")
    private Boolean openAfterEnrollment;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "hide_due_date")
    private Boolean hideDueDate;

    @Column(name = "enable_user_assignment")
    private Boolean enableUserAssignment;

    @Column(name = "referral")
    private Boolean referral;

    @Column(name = "display_execution_date_label")
    private String displayExecutionDateLabel;

    @Column(name = "form_type")
    private String formType;

    @Column(name = "display_form_name")
    private String displayFormName;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "repeatable")
    private Boolean repeatable;

    @Column(name = "program_stage_data_elements_count")
    private Integer programStageDataElementsCount;

    @Column(name = "program_stage_data_elements_content")
    private String programStageDataElementsContent;

    @Transient
    private boolean isPersisted;

    @ManyToOne(optional = false)
    @NotNull
    private DHISUser createdBy;

    @ManyToOne(optional = false)
    @NotNull
    private DHISUser lastUpdatedBy;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "project",
            "createdBy",
            "lastUpdatedBy",
            "categoryCombo",
            "programTrackedEntityAttributes",
            "organisationUnits",
            "programIndicators",
            "programStages",
        },
        allowSetters = true
    )
    private Program program;

    @ManyToMany
    @JoinTable(
        name = "rel_program_stage__program_stage_data_elements",
        joinColumns = @JoinColumn(name = "program_stage_id"),
        inverseJoinColumns = @JoinColumn(name = "program_stage_data_elements_id")
    )
    @JsonIgnoreProperties(
        value = { "project", "createdBy", "lastUpdatedBy", "categoryCombo", "optionSet", "dataSets", "programStages" },
        allowSetters = true
    )
    @JsonDeserialize(using = DataElementSetDeserializer.class)
    private Set<DataElement> programStageDataElements = new HashSet<>();

    @ManyToMany(mappedBy = "programStages")
    @JsonIgnoreProperties(
        value = {
            "project",
            "createdBy",
            "lastUpdatedBy",
            "categoryCombo",
            "programTrackedEntityAttributes",
            "organisationUnits",
            "programIndicators",
            "programStages",
        },
        allowSetters = true
    )
    private Set<Program> programs = new HashSet<>();

    @ManyToOne
    private Project project;

    @NotAudited
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "track", nullable = false)
    private TypeTrack track;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public ProgramStage id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ProgramStage name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreated() {
        return this.created;
    }

    public ProgramStage created(Instant created) {
        this.setCreated(created);
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getLastUpdated() {
        return this.lastUpdated;
    }

    public ProgramStage lastUpdated(Instant lastUpdated) {
        this.setLastUpdated(lastUpdated);
        return this;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Integer getMinDaysFromStart() {
        return this.minDaysFromStart;
    }

    public ProgramStage minDaysFromStart(Integer minDaysFromStart) {
        this.setMinDaysFromStart(minDaysFromStart);
        return this;
    }

    public void setMinDaysFromStart(Integer minDaysFromStart) {
        this.minDaysFromStart = minDaysFromStart;
    }

    public String getExecutionDateLabel() {
        return this.executionDateLabel;
    }

    public ProgramStage executionDateLabel(String executionDateLabel) {
        this.setExecutionDateLabel(executionDateLabel);
        return this;
    }

    public void setExecutionDateLabel(String executionDateLabel) {
        this.executionDateLabel = executionDateLabel;
    }

    public Boolean getAutoGenerateEvent() {
        return this.autoGenerateEvent;
    }

    public ProgramStage autoGenerateEvent(Boolean autoGenerateEvent) {
        this.setAutoGenerateEvent(autoGenerateEvent);
        return this;
    }

    public void setAutoGenerateEvent(Boolean autoGenerateEvent) {
        this.autoGenerateEvent = autoGenerateEvent;
    }

    public String getValidationStrategy() {
        return this.validationStrategy;
    }

    public ProgramStage validationStrategy(String validationStrategy) {
        this.setValidationStrategy(validationStrategy);
        return this;
    }

    public void setValidationStrategy(String validationStrategy) {
        this.validationStrategy = validationStrategy;
    }

    public Boolean getDisplayGenerateEventBox() {
        return this.displayGenerateEventBox;
    }

    public ProgramStage displayGenerateEventBox(Boolean displayGenerateEventBox) {
        this.setDisplayGenerateEventBox(displayGenerateEventBox);
        return this;
    }

    public void setDisplayGenerateEventBox(Boolean displayGenerateEventBox) {
        this.displayGenerateEventBox = displayGenerateEventBox;
    }

    public String getFeatureType() {
        return this.featureType;
    }

    public ProgramStage featureType(String featureType) {
        this.setFeatureType(featureType);
        return this;
    }

    public void setFeatureType(String featureType) {
        this.featureType = featureType;
    }

    public Boolean getBlockEntryForm() {
        return this.blockEntryForm;
    }

    public ProgramStage blockEntryForm(Boolean blockEntryForm) {
        this.setBlockEntryForm(blockEntryForm);
        return this;
    }

    public void setBlockEntryForm(Boolean blockEntryForm) {
        this.blockEntryForm = blockEntryForm;
    }

    public TypeTrack getTrack() {
        return this.track;
    }

    public ProgramStage track(TypeTrack track) {
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

    public ProgramStage project(Project project) {
        this.setProject(project);
        return this;
    }

    public Boolean getPreGenerateUID() {
        return this.preGenerateUID;
    }

    public ProgramStage preGenerateUID(Boolean preGenerateUID) {
        this.setPreGenerateUID(preGenerateUID);
        return this;
    }

    public void setPreGenerateUID(Boolean preGenerateUID) {
        this.preGenerateUID = preGenerateUID;
    }

    public Boolean getRemindCompleted() {
        return this.remindCompleted;
    }

    public ProgramStage remindCompleted(Boolean remindCompleted) {
        this.setRemindCompleted(remindCompleted);
        return this;
    }

    public void setRemindCompleted(Boolean remindCompleted) {
        this.remindCompleted = remindCompleted;
    }

    public Boolean getGeneratedByEnrollmentDate() {
        return this.generatedByEnrollmentDate;
    }

    public ProgramStage generatedByEnrollmentDate(Boolean generatedByEnrollmentDate) {
        this.setGeneratedByEnrollmentDate(generatedByEnrollmentDate);
        return this;
    }

    public void setGeneratedByEnrollmentDate(Boolean generatedByEnrollmentDate) {
        this.generatedByEnrollmentDate = generatedByEnrollmentDate;
    }

    public Boolean getAllowGenerateNextVisit() {
        return this.allowGenerateNextVisit;
    }

    public ProgramStage allowGenerateNextVisit(Boolean allowGenerateNextVisit) {
        this.setAllowGenerateNextVisit(allowGenerateNextVisit);
        return this;
    }

    public void setAllowGenerateNextVisit(Boolean allowGenerateNextVisit) {
        this.allowGenerateNextVisit = allowGenerateNextVisit;
    }

    public Boolean getOpenAfterEnrollment() {
        return this.openAfterEnrollment;
    }

    public ProgramStage openAfterEnrollment(Boolean openAfterEnrollment) {
        this.setOpenAfterEnrollment(openAfterEnrollment);
        return this;
    }

    public void setOpenAfterEnrollment(Boolean openAfterEnrollment) {
        this.openAfterEnrollment = openAfterEnrollment;
    }

    public Integer getSortOrder() {
        return this.sortOrder;
    }

    public ProgramStage sortOrder(Integer sortOrder) {
        this.setSortOrder(sortOrder);
        return this;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean getHideDueDate() {
        return this.hideDueDate;
    }

    public ProgramStage hideDueDate(Boolean hideDueDate) {
        this.setHideDueDate(hideDueDate);
        return this;
    }

    public void setHideDueDate(Boolean hideDueDate) {
        this.hideDueDate = hideDueDate;
    }

    public Boolean getEnableUserAssignment() {
        return this.enableUserAssignment;
    }

    public ProgramStage enableUserAssignment(Boolean enableUserAssignment) {
        this.setEnableUserAssignment(enableUserAssignment);
        return this;
    }

    public void setEnableUserAssignment(Boolean enableUserAssignment) {
        this.enableUserAssignment = enableUserAssignment;
    }

    public Boolean getReferral() {
        return this.referral;
    }

    public ProgramStage referral(Boolean referral) {
        this.setReferral(referral);
        return this;
    }

    public void setReferral(Boolean referral) {
        this.referral = referral;
    }

    public String getDisplayExecutionDateLabel() {
        return this.displayExecutionDateLabel;
    }

    public ProgramStage displayExecutionDateLabel(String displayExecutionDateLabel) {
        this.setDisplayExecutionDateLabel(displayExecutionDateLabel);
        return this;
    }

    public void setDisplayExecutionDateLabel(String displayExecutionDateLabel) {
        this.displayExecutionDateLabel = displayExecutionDateLabel;
    }

    public String getFormType() {
        return this.formType;
    }

    public ProgramStage formType(String formType) {
        this.setFormType(formType);
        return this;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public String getDisplayFormName() {
        return this.displayFormName;
    }

    public ProgramStage displayFormName(String displayFormName) {
        this.setDisplayFormName(displayFormName);
        return this;
    }

    public void setDisplayFormName(String displayFormName) {
        this.displayFormName = displayFormName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public ProgramStage displayName(String displayName) {
        this.setDisplayName(displayName);
        return this;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Boolean getRepeatable() {
        return this.repeatable;
    }

    public ProgramStage repeatable(Boolean repeatable) {
        this.setRepeatable(repeatable);
        return this;
    }

    public void setRepeatable(Boolean repeatable) {
        this.repeatable = repeatable;
    }

    public Integer getProgramStageDataElementsCount() {
        return this.programStageDataElementsCount;
    }

    public ProgramStage programStageDataElementsCount(Integer programStageDataElementsCount) {
        this.setProgramStageDataElementsCount(programStageDataElementsCount);
        return this;
    }

    public void setProgramStageDataElementsCount(Integer programStageDataElementsCount) {
        this.programStageDataElementsCount = programStageDataElementsCount;
    }

    public String getProgramStageDataElementsContent() {
        return this.programStageDataElementsContent;
    }

    public ProgramStage programStageDataElementsContent(String programStageDataElementsContent) {
        this.setProgramStageDataElementsContent(programStageDataElementsContent);
        return this;
    }

    public void setProgramStageDataElementsContent(String programStageDataElementsContent) {
        this.programStageDataElementsContent = programStageDataElementsContent;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public ProgramStage setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    public DHISUser getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(DHISUser dHISUser) {
        this.createdBy = dHISUser;
    }

    public ProgramStage createdBy(DHISUser dHISUser) {
        this.setCreatedBy(dHISUser);
        return this;
    }

    public DHISUser getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }

    public void setLastUpdatedBy(DHISUser dHISUser) {
        this.lastUpdatedBy = dHISUser;
    }

    public ProgramStage lastUpdatedBy(DHISUser dHISUser) {
        this.setLastUpdatedBy(dHISUser);
        return this;
    }

    public Program getProgram() {
        return this.program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public ProgramStage program(Program program) {
        this.setProgram(program);
        return this;
    }

    public Set<DataElement> getProgramStageDataElements() {
        return this.programStageDataElements;
    }

    public void setProgramStageDataElements(Set<DataElement> dataelements) {
        this.programStageDataElements = dataelements;
    }

    public ProgramStage programStageDataElements(Set<DataElement> dataelements) {
        this.setProgramStageDataElements(dataelements);
        return this;
    }

    public ProgramStage addProgramStageDataElements(DataElement dataelement) {
        this.programStageDataElements.add(dataelement);
        dataelement.getProgramStages().add(this);
        return this;
    }

    public ProgramStage removeProgramStageDataElements(DataElement dataelement) {
        this.programStageDataElements.remove(dataelement);
        dataelement.getProgramStages().remove(this);
        return this;
    }

    public Set<Program> getPrograms() {
        return this.programs;
    }

    public void setPrograms(Set<Program> programs) {
        if (this.programs != null) {
            this.programs.forEach(i -> i.removeProgramStage(this));
        }
        if (programs != null) {
            programs.forEach(i -> i.addProgramStage(this));
        }
        this.programs = programs;
    }

    public ProgramStage programs(Set<Program> programs) {
        this.setPrograms(programs);
        return this;
    }

    public ProgramStage addPrograms(Program program) {
        this.programs.add(program);
        program.getProgramStages().add(this);
        return this;
    }

    public ProgramStage removePrograms(Program program) {
        this.programs.remove(program);
        program.getProgramStages().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProgramStage)) {
            return false;
        }
        return id != null && id.equals(((ProgramStage) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProgramStage{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", created='" + getCreated() + "'" +
            ", lastUpdated='" + getLastUpdated() + "'" +
            ", minDaysFromStart=" + getMinDaysFromStart() +
            ", executionDateLabel='" + getExecutionDateLabel() + "'" +
            ", autoGenerateEvent='" + getAutoGenerateEvent() + "'" +
            ", validationStrategy='" + getValidationStrategy() + "'" +
            ", displayGenerateEventBox='" + getDisplayGenerateEventBox() + "'" +
            ", featureType='" + getFeatureType() + "'" +
            ", blockEntryForm='" + getBlockEntryForm() + "'" +
            ", preGenerateUID='" + getPreGenerateUID() + "'" +
            ", remindCompleted='" + getRemindCompleted() + "'" +
            ", generatedByEnrollmentDate='" + getGeneratedByEnrollmentDate() + "'" +
            ", allowGenerateNextVisit='" + getAllowGenerateNextVisit() + "'" +
            ", openAfterEnrollment='" + getOpenAfterEnrollment() + "'" +
            ", sortOrder=" + getSortOrder() +
            ", hideDueDate='" + getHideDueDate() + "'" +
            ", enableUserAssignment='" + getEnableUserAssignment() + "'" +
            ", referral='" + getReferral() + "'" +
            ", displayExecutionDateLabel='" + getDisplayExecutionDateLabel() + "'" +
            ", formType='" + getFormType() + "'" +
            ", displayFormName='" + getDisplayFormName() + "'" +
            ", displayName='" + getDisplayName() + "'" +
            ", repeatable='" + getRepeatable() + "'" +
            ", programStageDataElementsCount=" + getProgramStageDataElementsCount() +
            ", programStageDataElementsContent=" + getProgramStageDataElementsContent() +
            "}";
    }
}
