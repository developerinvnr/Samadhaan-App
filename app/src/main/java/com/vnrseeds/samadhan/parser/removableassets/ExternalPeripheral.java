
package com.vnrseeds.samadhan.parser.removableassets;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExternalPeripheral {

    @SerializedName("asset_history_id")
    @Expose
    private Integer assetHistoryId;
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

    public Integer getAssetHistoryId() {
        return assetHistoryId;
    }

    public void setAssetHistoryId(Integer assetHistoryId) {
        this.assetHistoryId = assetHistoryId;
    }

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

}
