
package com.vnrseeds.samadhan.parser.addassetdropdownparser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssetCategory {

    @SerializedName("ac_id")
    @Expose
    private Integer acId;
    @SerializedName("ac_name")
    @Expose
    private String acName;

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

}
