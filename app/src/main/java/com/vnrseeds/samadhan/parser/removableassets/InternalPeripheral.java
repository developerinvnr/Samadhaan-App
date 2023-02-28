
package com.vnrseeds.samadhan.parser.removableassets;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InternalPeripheral {

    @SerializedName("ac_id")
    @Expose
    private Integer acId;
    @SerializedName("ac_classification_id")
    @Expose
    private Integer acClassificationId;
    @SerializedName("ac_can_used_in")
    @Expose
    private String acCanUsedIn;
    @SerializedName("ac_workshop_applicable")
    @Expose
    private String acWorkshopApplicable;
    @SerializedName("ac_main_peripheral")
    @Expose
    private String acMainPeripheral;
    @SerializedName("ac_name")
    @Expose
    private String acName;
    @SerializedName("ac_code")
    @Expose
    private String acCode;
    @SerializedName("ac_icon")
    @Expose
    private Object acIcon;
    @SerializedName("ac_icon_big")
    @Expose
    private Object acIconBig;
    @SerializedName("ac_created_at")
    @Expose
    private String acCreatedAt;
    @SerializedName("ac_updated_at")
    @Expose
    private String acUpdatedAt;
    @SerializedName("ac_created_by")
    @Expose
    private Integer acCreatedBy;
    @SerializedName("ac_updated_by")
    @Expose
    private Integer acUpdatedBy;
    @SerializedName("ac_status")
    @Expose
    private String acStatus;

    public Integer getAcId() {
        return acId;
    }

    public void setAcId(Integer acId) {
        this.acId = acId;
    }

    public Integer getAcClassificationId() {
        return acClassificationId;
    }

    public void setAcClassificationId(Integer acClassificationId) {
        this.acClassificationId = acClassificationId;
    }

    public String getAcCanUsedIn() {
        return acCanUsedIn;
    }

    public void setAcCanUsedIn(String acCanUsedIn) {
        this.acCanUsedIn = acCanUsedIn;
    }

    public String getAcWorkshopApplicable() {
        return acWorkshopApplicable;
    }

    public void setAcWorkshopApplicable(String acWorkshopApplicable) {
        this.acWorkshopApplicable = acWorkshopApplicable;
    }

    public String getAcMainPeripheral() {
        return acMainPeripheral;
    }

    public void setAcMainPeripheral(String acMainPeripheral) {
        this.acMainPeripheral = acMainPeripheral;
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

    public Object getAcIcon() {
        return acIcon;
    }

    public void setAcIcon(Object acIcon) {
        this.acIcon = acIcon;
    }

    public Object getAcIconBig() {
        return acIconBig;
    }

    public void setAcIconBig(Object acIconBig) {
        this.acIconBig = acIconBig;
    }

    public String getAcCreatedAt() {
        return acCreatedAt;
    }

    public void setAcCreatedAt(String acCreatedAt) {
        this.acCreatedAt = acCreatedAt;
    }

    public String getAcUpdatedAt() {
        return acUpdatedAt;
    }

    public void setAcUpdatedAt(String acUpdatedAt) {
        this.acUpdatedAt = acUpdatedAt;
    }

    public Integer getAcCreatedBy() {
        return acCreatedBy;
    }

    public void setAcCreatedBy(Integer acCreatedBy) {
        this.acCreatedBy = acCreatedBy;
    }

    public Integer getAcUpdatedBy() {
        return acUpdatedBy;
    }

    public void setAcUpdatedBy(Integer acUpdatedBy) {
        this.acUpdatedBy = acUpdatedBy;
    }

    public String getAcStatus() {
        return acStatus;
    }

    public void setAcStatus(String acStatus) {
        this.acStatus = acStatus;
    }

}
