package com.didate.domain;

import com.didate.domain.enumeration.TypeTrack;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.domain.Persistable;

/**
 * A ProgramRuleAction.
 */
@JsonIgnoreProperties(value = { "new" }, ignoreUnknown = true)
@Entity
@Table(name = "program_rule_action")
@Audited
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProgramRuleAction implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "last_updated")
    private Instant lastUpdated;

    @Column(name = "created")
    private String created;

    @Column(name = "program_rule_action_type")
    private String programRuleActionType;

    @Column(name = "evaluation_time")
    private String evaluationTime;

    @Column(name = "data")
    private String data;

    @Column(name = "template_uid")
    private String templateUid;

    @Column(name = "content")
    private String content;

    @Column(name = "display_content")
    private String displayContent;

    @NotAudited
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "track", nullable = false)
    private TypeTrack track;

    @Transient
    private boolean isPersisted;

    @ManyToOne
    private Project project;

    @ManyToOne(optional = false)
    @NotNull
    private DHISUser createdBy;

    @ManyToOne(optional = false)
    @NotNull
    private DHISUser lastUpdatedBy;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "project", "createdBy", "lastUpdatedBy", "program" }, allowSetters = true)
    private ProgramRule programRule;

    @ManyToOne
    @JsonIgnoreProperties(value = { "project", "createdBy", "lastUpdatedBy", "optionSet", "programs" }, allowSetters = true)
    private TrackedEntityAttribute trackedEntityAttribute;

    @ManyToOne
    @JsonIgnoreProperties(value = { "project", "createdBy", "lastUpdatedBy", "optionSet", "programs" }, allowSetters = true)
    private DataElement dataElement;

    @ManyToOne
    private OptionGroup optionGroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public ProgramRuleAction id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getLastUpdated() {
        return this.lastUpdated;
    }

    public ProgramRuleAction lastUpdated(Instant lastUpdated) {
        this.setLastUpdated(lastUpdated);
        return this;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getCreated() {
        return this.created;
    }

    public ProgramRuleAction created(String created) {
        this.setCreated(created);
        return this;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getProgramRuleActionType() {
        return this.programRuleActionType;
    }

    public ProgramRuleAction programRuleActionType(String programRuleActionType) {
        this.setProgramRuleActionType(programRuleActionType);
        return this;
    }

    public void setProgramRuleActionType(String programRuleActionType) {
        this.programRuleActionType = programRuleActionType;
    }

    public String getEvaluationTime() {
        return this.evaluationTime;
    }

    public ProgramRuleAction evaluationTime(String evaluationTime) {
        this.setEvaluationTime(evaluationTime);
        return this;
    }

    public void setEvaluationTime(String evaluationTime) {
        this.evaluationTime = evaluationTime;
    }

    public String getData() {
        return this.data;
    }

    public ProgramRuleAction data(String data) {
        this.setData(data);
        return this;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTemplateUid() {
        return this.templateUid;
    }

    public ProgramRuleAction templateUid(String templateUid) {
        this.setTemplateUid(templateUid);
        return this;
    }

    public void setTemplateUid(String templateUid) {
        this.templateUid = templateUid;
    }

    public String getContent() {
        return this.content;
    }

    public ProgramRuleAction content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDisplayContent() {
        return this.displayContent;
    }

    public ProgramRuleAction displayContent(String displayContent) {
        this.setDisplayContent(displayContent);
        return this;
    }

    public void setDisplayContent(String displayContent) {
        this.displayContent = displayContent;
    }

    public TypeTrack getTrack() {
        return this.track;
    }

    public ProgramRuleAction track(TypeTrack track) {
        this.setTrack(track);
        return this;
    }

    public void setTrack(TypeTrack track) {
        this.track = track;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public ProgramRuleAction setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public ProgramRuleAction project(Project project) {
        this.setProject(project);
        return this;
    }

    public DHISUser getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(DHISUser dHISUser) {
        this.createdBy = dHISUser;
    }

    public ProgramRuleAction createdBy(DHISUser dHISUser) {
        this.setCreatedBy(dHISUser);
        return this;
    }

    public DHISUser getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }

    public void setLastUpdatedBy(DHISUser dHISUser) {
        this.lastUpdatedBy = dHISUser;
    }

    public ProgramRuleAction lastUpdatedBy(DHISUser dHISUser) {
        this.setLastUpdatedBy(dHISUser);
        return this;
    }

    public ProgramRule getProgramRule() {
        return this.programRule;
    }

    public void setProgramRule(ProgramRule programRule) {
        this.programRule = programRule;
    }

    public ProgramRuleAction programRule(ProgramRule programRule) {
        this.setProgramRule(programRule);
        return this;
    }

    public TrackedEntityAttribute getTrackedEntityAttribute() {
        return this.trackedEntityAttribute;
    }

    public void setTrackedEntityAttribute(TrackedEntityAttribute trackedEntityAttribute) {
        this.trackedEntityAttribute = trackedEntityAttribute;
    }

    public ProgramRuleAction trackedEntityAttribute(TrackedEntityAttribute trackedEntityAttribute) {
        this.setTrackedEntityAttribute(trackedEntityAttribute);
        return this;
    }

    public DataElement getDataElement() {
        return this.dataElement;
    }

    public void setDataElement(DataElement trackedEntityAttribute) {
        this.dataElement = trackedEntityAttribute;
    }

    public ProgramRuleAction dataElement(DataElement trackedEntityAttribute) {
        this.setDataElement(trackedEntityAttribute);
        return this;
    }

    public OptionGroup getOptionGroup() {
        return this.optionGroup;
    }

    public void setOptionGroup(OptionGroup optionGroup) {
        this.optionGroup = optionGroup;
    }

    public ProgramRuleAction optionGroup(OptionGroup optionGroup) {
        this.setOptionGroup(optionGroup);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProgramRuleAction)) {
            return false;
        }
        return id != null && id.equals(((ProgramRuleAction) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProgramRuleAction{" +
            "id=" + getId() +
            ", lastUpdated='" + getLastUpdated() + "'" +
            ", created='" + getCreated() + "'" +
            ", programRuleActionType='" + getProgramRuleActionType() + "'" +
            ", evaluationTime='" + getEvaluationTime() + "'" +
            ", data='" + getData() + "'" +
            ", templateUid='" + getTemplateUid() + "'" +
            ", content='" + getContent() + "'" +
            ", displayContent='" + getDisplayContent() + "'" +
            ", track='" + getTrack() + "'" +
            "}";
    }
}
