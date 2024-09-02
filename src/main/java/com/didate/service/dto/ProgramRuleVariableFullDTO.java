package com.didate.service.dto;

import com.didate.domain.ProgramRuleVariable;

public class ProgramRuleVariableFullDTO extends ProgramRuleVariableDTO {

    private String displayName;

    private String programRuleVariableSourceType;

    private Boolean useCodeForOptionSet;

    public ProgramRuleVariableFullDTO(ProgramRuleVariable programRuleVariable) {
        super(programRuleVariable);
        this.displayName = programRuleVariable.getDisplayName();
        this.programRuleVariableSourceType = programRuleVariable.getProgramRuleVariableSourceType();
        this.useCodeForOptionSet = programRuleVariable.getUseCodeForOptionSet();
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProgramRuleVariableSourceType() {
        return programRuleVariableSourceType;
    }

    public void setProgramRuleVariableSourceType(String programRuleVariableSourceType) {
        this.programRuleVariableSourceType = programRuleVariableSourceType;
    }

    public Boolean getUseCodeForOptionSet() {
        return useCodeForOptionSet;
    }

    public void setUseCodeForOptionSet(Boolean useCodeForOptionSet) {
        this.useCodeForOptionSet = useCodeForOptionSet;
    }
}
