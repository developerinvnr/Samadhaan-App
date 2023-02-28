
package com.vnrseeds.samadhan.parser.issuegroupparser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("ig_id")
    @Expose
    private Integer igId;
    @SerializedName("ig_name")
    @Expose
    private String igName;

    public Integer getIgId() {
        return igId;
    }

    public void setIgId(Integer igId) {
        this.igId = igId;
    }

    public String getIgName() {
        return igName;
    }

    public void setIgName(String igName) {
        this.igName = igName;
    }

}
