package com.didate.service.dhis2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pager {

    @JsonProperty("page")
    private int page;

    @JsonProperty("pageSize")
    private int pageSize;

    @JsonProperty("total")
    private int total;

    @JsonProperty("nextPage")
    private String nextPage;

    // Getters and Setters
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public Pager nextPage(String nextPage) {
        setNextPage(nextPage);
        return this;
    }

    @Override
    public String toString() {
        return "Pager{" + "page=" + page + ", pageSize=" + pageSize + ", total=" + total + '}';
    }
}
