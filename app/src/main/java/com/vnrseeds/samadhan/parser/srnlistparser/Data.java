package com.vnrseeds.samadhan.parser.srnlistparser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {
    @SerializedName("srnList")
    @Expose
    private List<Srn> srnList;
    @SerializedName("canCreateSRN")
    @Expose
    private Integer canCreateSRN;

    public List<Srn> getSrnList() {
        return srnList;
    }

    public void setSrnList(List<Srn> srnList) {
        this.srnList = srnList;
    }

    public Integer getCanCreateSRN() {
        return canCreateSRN;
    }

    public void setCanCreateSRN(Integer canCreateSRN) {
        this.canCreateSRN = canCreateSRN;
    }
}
