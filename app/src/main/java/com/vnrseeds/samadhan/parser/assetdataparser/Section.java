
package com.vnrseeds.samadhan.parser.assetdataparser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Section {

    @SerializedName("ds_id")
    @Expose
    private String dsId;
    @SerializedName("ds_name")
    @Expose
    private String dsName;

    public String getDsId() {
        return dsId;
    }

    public void setDsId(String dsId) {
        this.dsId = dsId;
    }

    public String getDsName() {
        return dsName;
    }

    public void setDsName(String dsName) {
        this.dsName = dsName;
    }

}
