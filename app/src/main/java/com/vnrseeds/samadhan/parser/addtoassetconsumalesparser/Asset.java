
package com.vnrseeds.samadhan.parser.addtoassetconsumalesparser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Asset {

    @SerializedName("ac_id")
    @Expose
    private Integer acId;
    @SerializedName("ac_name")
    @Expose
    private String acName;
    @SerializedName("unit_name")
    @Expose
    private String unitName;
    @SerializedName("primary_storage")
    @Expose
    private String primaryStorage;
    @SerializedName("secondary_storage")
    @Expose
    private String secondaryStorage;
    @SerializedName("storage_type")
    @Expose
    private String storageType;
    @SerializedName("section_name")
    @Expose
    private String sectionName;
    @SerializedName("asset_box_number")
    @Expose
    private String assetBoxNumber;
    @SerializedName("available")
    @Expose
    private Integer available;

    public Integer getAcId() {
        return acId;
    }

    public void setAcId(Integer acId) {
        this.acId = acId;
    }

    public String getAcName() {
        return acName;
    }

    public void setAcName(String acName) {
        this.acName = acName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getPrimaryStorage() {
        return primaryStorage;
    }

    public void setPrimaryStorage(String primaryStorage) {
        this.primaryStorage = primaryStorage;
    }

    public String getSecondaryStorage() {
        return secondaryStorage;
    }

    public void setSecondaryStorage(String secondaryStorage) {
        this.secondaryStorage = secondaryStorage;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getAssetBoxNumber() {
        return assetBoxNumber;
    }

    public void setAssetBoxNumber(String assetBoxNumber) {
        this.assetBoxNumber = assetBoxNumber;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

}
