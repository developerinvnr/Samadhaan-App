
package com.vnrseeds.samadhan.parser.addassetdropdownparser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NetworkType {

    @SerializedName("nt_id")
    @Expose
    private String ntId;
    @SerializedName("nt_name")
    @Expose
    private String ntName;

    public String getNtId() {
        return ntId;
    }

    public void setNtId(String ntId) {
        this.ntId = ntId;
    }

    public String getNtName() {
        return ntName;
    }

    public void setNtName(String ntName) {
        this.ntName = ntName;
    }

}
