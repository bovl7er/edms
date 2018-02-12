package com.cmxv.modellayer.business;

import java.util.Date;

public class SearchFilter {

    String searchString;
    Date startDate;
    Date endDate;
    Integer docTypeId;

    public SearchFilter() {
    }

    public SearchFilter(String searchString, Date startDate, Date endDate, Integer docTypeId) {
        this.searchString = searchString;
        this.startDate = startDate;
        this.endDate = endDate;
        this.docTypeId = docTypeId;

    }

    public SearchFilter(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getDocTypeId() {
        return docTypeId;
    }

    public void setDocTypeId(Integer docTypeId) {
        this.docTypeId = docTypeId;
    }

    @Override
    public String toString() {
        return "SearchFilter{" + "searchString=" + searchString + ", startDate=" + startDate + ", endDate=" + endDate + ", docTypeId=" + docTypeId + '}';
    }



    


    
}
