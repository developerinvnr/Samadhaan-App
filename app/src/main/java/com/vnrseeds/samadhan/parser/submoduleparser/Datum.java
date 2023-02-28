package com.vnrseeds.samadhan.parser.submoduleparser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("sm_id")
    @Expose
    private Integer smId;
    @SerializedName("sm_name")
    @Expose
    private String smName;

    public Integer getSmId() {
        return smId;
    }

    public void setSmId(Integer smId) {
        this.smId = smId;
    }

    public String getSmName() {
        return smName;
    }

    public void setSmName(String smName) {
        this.smName = smName;
    }
}
