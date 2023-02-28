
package com.vnrseeds.samadhan.parser.assetlistparser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssetListData {

    @SerializedName("asset_id")
    @Expose
    private String assetId;
    @SerializedName("asset_code")
    @Expose
    private String assetCode;
    @SerializedName("ac_name")
    @Expose
    private String acName;
    @SerializedName("asset_type")
    @Expose
    private String assetType;
    @SerializedName("brand_name")
    @Expose
    private String brandName;
    @SerializedName("bm_name")
    @Expose
    private String bmName;
    @SerializedName("location_name")
    @Expose
    private String locationName;
    @SerializedName("department_name")
    @Expose
    private String departmentName;
    @SerializedName("ds_name")
    @Expose
    private String dsName;
    @SerializedName("user_name")
    @Expose
    private String custodianName;
    @SerializedName("asset_status")
    @Expose
    private String assetStatus;
    @SerializedName("ac_code")
    @Expose
    private String ac_code;

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

    public String getAcName() {
        return acName;
    }

    public void setAcName(String acName) {
        this.acName = acName;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
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

    public String getCustodianName() {
        return custodianName;
    }

    public void setCustodianName(String custodianName) {
        this.custodianName = custodianName;
    }

    public String getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(String assetStatus) {
        this.assetStatus = assetStatus;
    }

    public String getAc_code() {
        return ac_code;
    }

    public void setAc_code(String ac_code) {
        this.ac_code = ac_code;
    }
}
