
package com.vnrseeds.samadhan.parser.brandmodelparser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("bm_id")
    @Expose
    private Integer bmId;
    @SerializedName("bm_name")
    @Expose
    private String bmName;

    public Integer getBmId() {
        return bmId;
    }

    public void setBmId(Integer bmId) {
        this.bmId = bmId;
    }

    public String getBmName() {
        return bmName;
    }

    public void setBmName(String bmName) {
        this.bmName = bmName;
    }

}
