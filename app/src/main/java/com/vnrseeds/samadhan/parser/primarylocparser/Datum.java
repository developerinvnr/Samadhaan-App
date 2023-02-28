package com.vnrseeds.samadhan.parser.primarylocparser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("primary_location_name")
    @Expose
    private String primaryLocationName;
    @SerializedName("primary_location_code")
    @Expose
    private String primaryLocationCode;
    @SerializedName("primary_location_status")
    @Expose
    private String primaryLocationStatus;
    @SerializedName("primary_location_created_by")
    @Expose
    private Integer primaryLocationCreatedBy;
    @SerializedName("primary_location_updated_by")
    @Expose
    private Integer primaryLocationUpdatedBy;
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

    public String getPrimaryLocationName() {
        return primaryLocationName;
    }

    public void setPrimaryLocationName(String primaryLocationName) {
        this.primaryLocationName = primaryLocationName;
    }

    public String getPrimaryLocationCode() {
        return primaryLocationCode;
    }

    public void setPrimaryLocationCode(String primaryLocationCode) {
        this.primaryLocationCode = primaryLocationCode;
    }

    public String getPrimaryLocationStatus() {
        return primaryLocationStatus;
    }

    public void setPrimaryLocationStatus(String primaryLocationStatus) {
        this.primaryLocationStatus = primaryLocationStatus;
    }

    public Integer getPrimaryLocationCreatedBy() {
        return primaryLocationCreatedBy;
    }

    public void setPrimaryLocationCreatedBy(Integer primaryLocationCreatedBy) {
        this.primaryLocationCreatedBy = primaryLocationCreatedBy;
    }

    public Integer getPrimaryLocationUpdatedBy() {
        return primaryLocationUpdatedBy;
    }

    public void setPrimaryLocationUpdatedBy(Integer primaryLocationUpdatedBy) {
        this.primaryLocationUpdatedBy = primaryLocationUpdatedBy;
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
