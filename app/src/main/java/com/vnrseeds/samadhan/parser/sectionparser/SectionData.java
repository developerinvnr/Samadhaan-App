
package com.vnrseeds.samadhan.parser.sectionparser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SectionData {

    @SerializedName("ds_id")
    @Expose
    private Integer dsId;
    @SerializedName("ds_name")
    @Expose
    private String dsName;

    public Integer getDsId() {
        return dsId;
    }

    public void setDsId(Integer dsId) {
        this.dsId = dsId;
    }

    public String getDsName() {
        return dsName;
    }

    public void setDsName(String dsName) {
        this.dsName = dsName;
    }

}
