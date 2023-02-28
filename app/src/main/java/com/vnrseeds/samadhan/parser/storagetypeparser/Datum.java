package com.vnrseeds.samadhan.parser.storagetypeparser;

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
    @SerializedName("storage_type")
    @Expose
    private String storageType;
    @SerializedName("storage_type_status")
    @Expose
    private String storageTypeStatus;
    @SerializedName("storage_type_created_by")
    @Expose
    private Integer storageTypeCreatedBy;
    @SerializedName("storage_type_updated_by")
    @Expose
    private Object storageTypeUpdatedBy;
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

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public String getStorageTypeStatus() {
        return storageTypeStatus;
    }

    public void setStorageTypeStatus(String storageTypeStatus) {
        this.storageTypeStatus = storageTypeStatus;
    }

    public Integer getStorageTypeCreatedBy() {
        return storageTypeCreatedBy;
    }

    public void setStorageTypeCreatedBy(Integer storageTypeCreatedBy) {
        this.storageTypeCreatedBy = storageTypeCreatedBy;
    }

    public Object getStorageTypeUpdatedBy() {
        return storageTypeUpdatedBy;
    }

    public void setStorageTypeUpdatedBy(Object storageTypeUpdatedBy) {
        this.storageTypeUpdatedBy = storageTypeUpdatedBy;
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
