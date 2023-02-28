package com.vnrseeds.samadhan.parser.ticketviewparser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssignLog {
    @SerializedName("assign_created_by_id")
    @Expose
    private Integer assignCreatedById;
    @SerializedName("assign_created_by")
    @Expose
    private String assignCreatedBy;
    @SerializedName("assign_to")
    @Expose
    private String assignTo;
    @SerializedName("assign_description")
    @Expose
    private String assignDescription;
    @SerializedName("assign_date")
    @Expose
    private String assignDate;

    public Integer getAssignCreatedById() {
        return assignCreatedById;
    }

    public void setAssignCreatedById(Integer assignCreatedById) {
        this.assignCreatedById = assignCreatedById;
    }

    public String getAssignCreatedBy() {
        return assignCreatedBy;
    }

    public void setAssignCreatedBy(String assignCreatedBy) {
        this.assignCreatedBy = assignCreatedBy;
    }

    public String getAssignTo() {
        return assignTo;
    }

    public void setAssignTo(String assignTo) {
        this.assignTo = assignTo;
    }

    public String getAssignDescription() {
        return assignDescription;
    }

    public void setAssignDescription(String assignDescription) {
        this.assignDescription = assignDescription;
    }

    public String getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(String assignDate) {
        this.assignDate = assignDate;
    }
}
