
package com.vnrseeds.samadhan.parser.assetcategoryparser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("ac_id")
    @Expose
    private Integer acId;
    @SerializedName("ac_name")
    @Expose
    private String acName;
    @SerializedName("ac_code")
    @Expose
    private String acCode;

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

    public String getAcCode() {
        return acCode;
    }

    public void setAcCode(String acCode) {
        this.acCode = acCode;
    }

}
