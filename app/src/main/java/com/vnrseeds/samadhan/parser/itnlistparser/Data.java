
package com.vnrseeds.samadhan.parser.itnlistparser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("itnList")
    @Expose
    private List<Itn> itnList;
    @SerializedName("canCreateITN")
    @Expose
    private Integer canCreateITN;

    public List<Itn> getItnList() {
        return itnList;
    }

    public void setItnList(List<Itn> itnList) {
        this.itnList = itnList;
    }

    public Integer getCanCreateITN() {
        return canCreateITN;
    }

    public void setCanCreateITN(Integer canCreateITN) {
        this.canCreateITN = canCreateITN;
    }
}
