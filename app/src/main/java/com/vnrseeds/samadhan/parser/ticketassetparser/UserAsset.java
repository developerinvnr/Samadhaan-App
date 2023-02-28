
package com.vnrseeds.samadhan.parser.ticketassetparser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserAsset {

    @SerializedName("asset_id")
    @Expose
    private String assetId;
    @SerializedName("asset_code")
    @Expose
    private String assetCode;
    @SerializedName("asset_type")
    @Expose
    private String assetType;
    @SerializedName("ac_name")
    @Expose
    private String acName;
    @SerializedName("ac_code")
    @Expose
    private String acCode;
    @SerializedName("brand_name")
    @Expose
    private String brandName;
    @SerializedName("model_name")
    @Expose
    private String modelName;
    @SerializedName("asset_serial_no")
    @Expose
    private String assetSerialNo;
    @SerializedName("asset_installation_date")
    @Expose
    private String assetInstallationDate;
    @SerializedName("asset_ac_id")
    @Expose
    private String asset_ac_id;
    @SerializedName("ac_icon")
    @Expose
    private String acIcon;
    @SerializedName("location_name")
    @Expose
    private String locationName;
    @SerializedName("department_name")
    @Expose
    private String departmentName;
    @SerializedName("ds_name")
    @Expose
    private String dsName;
    @SerializedName("asset_custodian")
    @Expose
    private String assetCustodian;
    @SerializedName("asset_is_byod")
    @Expose
    private String assetIsByod;
    @SerializedName("ticket_is_active")
    @Expose
    private Integer ticketIsActive;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getAcName() {
        return acName;
    }

    public void setAcName(String acName) {
        this.acName = acName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getAssetSerialNo() {
        return assetSerialNo;
    }

    public void setAssetSerialNo(String assetSerialNo) {
        this.assetSerialNo = assetSerialNo;
    }

    public String getAssetInstallationDate() {
        return assetInstallationDate;
    }

    public void setAssetInstallationDate(String assetInstallationDate) {
        this.assetInstallationDate = assetInstallationDate;
    }

    public String getAsset_ac_id() {
        return asset_ac_id;
    }

    public void setAsset_ac_id(String asset_ac_id) {
        this.asset_ac_id = asset_ac_id;
    }

    public String getAcIcon() {
        return acIcon;
    }

    public void setAcIcon(String acIcon) {
        this.acIcon = acIcon;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDsName() {
        return dsName;
    }

    public void setDsName(String dsName) {
        this.dsName = dsName;
    }

    public String getAssetCustodian() {
        return assetCustodian;
    }

    public void setAssetCustodian(String assetCustodian) {
        this.assetCustodian = assetCustodian;
    }

    public String getAcCode() {
        return acCode;
    }

    public void setAcCode(String acCode) {
        this.acCode = acCode;
    }

    public String getAssetIsByod() {
        return assetIsByod;
    }

    public void setAssetIsByod(String assetIsByod) {
        this.assetIsByod = assetIsByod;
    }

    public Integer getTicketIsActive() {
        return ticketIsActive;
    }

    public void setTicketIsActive(Integer ticketIsActive) {
        this.ticketIsActive = ticketIsActive;
    }
}
