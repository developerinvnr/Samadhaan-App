package com.vnrseeds.samadhan.parser.secondarylocparser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("primary_location_id")
    @Expose
    private Integer primaryLocationId;
    @SerializedName("secondary_location_name")
    @Expose
    private String secondaryLocationName;
    @SerializedName("secondary_location_code")
    @Expose
    private String secondaryLocationCode;
    @SerializedName("secondary_location_status")
    @Expose
    private String secondaryLocationStatus;
    @SerializedName("secondary_location_created_by")
    @Expose
    private Integer secondaryLocationCreatedBy;
    @SerializedName("secondary_location_updated_by")
    @Expose
    private Object secondaryLocationUpdatedBy;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPrimaryLocationId() {
        return primaryLocationId;
    }

    public void setPrimaryLocationId(Integer primaryLocationId) {
        this.primaryLocationId = primaryLocationId;
    }

    public String getSecondaryLocationName() {
        return secondaryLocationName;
    }

    public void setSecondaryLocationName(String secondaryLocationName) {
        this.secondaryLocationName = secondaryLocationName;
    }

    public String getSecondaryLocationCode() {
        return secondaryLocationCode;
    }

    public void setSecondaryLocationCode(String secondaryLocationCode) {
        this.secondaryLocationCode = secondaryLocationCode;
    }

    public String getSecondaryLocationStatus() {
        return secondaryLocationStatus;
    }

    public void setSecondaryLocationStatus(String secondaryLocationStatus) {
        this.secondaryLocationStatus = secondaryLocationStatus;
    }

    public Integer getSecondaryLocationCreatedBy() {
        return secondaryLocationCreatedBy;
    }

    public void setSecondaryLocationCreatedBy(Integer secondaryLocationCreatedBy) {
        this.secondaryLocationCreatedBy = secondaryLocationCreatedBy;
    }

    public Object getSecondaryLocationUpdatedBy() {
        return secondaryLocationUpdatedBy;
    }

    public void setSecondaryLocationUpdatedBy(Object secondaryLocationUpdatedBy) {
        this.secondaryLocationUpdatedBy = secondaryLocationUpdatedBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }
}
