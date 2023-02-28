
package com.vnrseeds.samadhan.parser.assetdataparser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Custodian {

    @SerializedName("custodian_id")
    @Expose
    private Integer custodianId;
    @SerializedName("custodian_name")
    @Expose
    private String custodianName;

    public Integer getCustodianId() {
        return custodianId;
    }

    public void setCustodianId(Integer custodianId) {
        this.custodianId = custodianId;
    }

    public String getCustodianName() {
        return custodianName;
    }

    public void setCustodianName(String custodianName) {
        this.custodianName = custodianName;
    }

}
