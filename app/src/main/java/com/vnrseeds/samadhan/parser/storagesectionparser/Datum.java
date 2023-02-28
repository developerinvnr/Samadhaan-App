package com.vnrseeds.samadhan.parser.storagesectionparser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("primary_location_id")
    @Expose
    private Integer primaryLocationId;
    @SerializedName("secondary_location_id")
    @Expose
    private Integer secondaryLocationId;
    @SerializedName("storage_type_id")
    @Expose
    private Integer storageTypeId;
    @SerializedName("section_name")
    @Expose
    private String sectionName;
    @SerializedName("storage_section_status")
    @Expose
    private String storageSectionStatus;
    @SerializedName("storage_section_created_by")
    @Expose
    private Integer storageSectionCreatedBy;
    @SerializedName("storage_section_updated_by")
    @Expose
    private Object storageSectionUpdatedBy;
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

    public Integer getSecondaryLocationId() {
        return secondaryLocationId;
    }

    public void setSecondaryLocationId(Integer secondaryLocationId) {
        this.secondaryLocationId = secondaryLocationId;
    }

    public Integer getStorageTypeId() {
        return storageTypeId;
    }

    public void setStorageTypeId(Integer storageTypeId) {
        this.storageTypeId = storageTypeId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getStorageSectionStatus() {
        return storageSectionStatus;
    }

    public void setStorageSectionStatus(String storageSectionStatus) {
        this.storageSectionStatus = storageSectionStatus;
    }

    public Integer getStorageSectionCreatedBy() {
        return storageSectionCreatedBy;
    }

    public void setStorageSectionCreatedBy(Integer storageSectionCreatedBy) {
        this.storageSectionCreatedBy = storageSectionCreatedBy;
    }

    public Object getStorageSectionUpdatedBy() {
        return storageSectionUpdatedBy;
    }

    public void setStorageSectionUpdatedBy(Object storageSectionUpdatedBy) {
        this.storageSectionUpdatedBy = storageSectionUpdatedBy;
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
