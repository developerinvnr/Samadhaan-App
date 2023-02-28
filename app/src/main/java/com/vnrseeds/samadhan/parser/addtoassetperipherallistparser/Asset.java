
package com.vnrseeds.samadhan.parser.addtoassetperipherallistparser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Asset {

    @SerializedName("asset_id")
    @Expose
    private Integer assetId;
    @SerializedName("asset_code")
    @Expose
    private String assetCode;
    @SerializedName("asset_consumable_name")
    @Expose
    private String assetConsumableName;
    @SerializedName("brand_name")
    @Expose
    private String brandName;
    @SerializedName("bm_name")
    @Expose
    private String bmName;
    @SerializedName("asset_serial_no")
    @Expose
    private String assetSerialNo;
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

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public String getAssetConsumableName() {
        return assetConsumableName;
    }

    public void setAssetConsumableName(String assetConsumableName) {
        this.assetConsumableName = assetConsumableName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBmName() {
        return bmName;
    }

    public void setBmName(String bmName) {
        this.bmName = bmName;
    }

    public String getAssetSerialNo() {
        return assetSerialNo;
    }

    public void setAssetSerialNo(String assetSerialNo) {
        this.assetSerialNo = assetSerialNo;
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

}
