
package com.vnrseeds.samadhan.parser.deployableassetparser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("asset_id")
    @Expose
    private Integer assetId;
    @SerializedName("asset_code")
    @Expose
    private String assetCode;
    @SerializedName("asset_name")
    @Expose
    private String assetName;
    @SerializedName("ac_name")
    @Expose
    private String acName;
    @SerializedName("ac_code")
    @Expose
    private String acCode;
    @SerializedName("brand_name")
    @Expose
    private String brandName;
    @SerializedName("bm_name")
    @Expose
    private String bmName;
    @SerializedName("asset_serial_no")
    @Expose
    private String assetSerialNo;
    @SerializedName("asset_current_status")
    @Expose
    private String assetCurrentStatus;

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

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getAcName() {
        return acName;
    }

    public void setAcName(String acName) {
        this.acName = acName;
    }

    public String getAcCode() {
        return acCode;
    }

    public void setAcCode(String acCode) {
        this.acCode = acCode;
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

    public String getAssetCurrentStatus() {
        return assetCurrentStatus;
    }

    public void setAssetCurrentStatus(String assetCurrentStatus) {
        this.assetCurrentStatus = assetCurrentStatus;
    }

}
