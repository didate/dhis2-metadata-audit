package com.didate.service.dhis2.response;

import com.didate.domain.Program;
import com.didate.service.dhis2.Pager;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ProgramResponse {

    @JsonProperty("pager")
    private Pager pager;

    @JsonProperty("programs")
    private List<Program> programs;

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public List<Program> getPrograms() {
        return programs;
    }

    public void setPrograms(List<Program> programs) {
        this.programs = programs;
    }
}
