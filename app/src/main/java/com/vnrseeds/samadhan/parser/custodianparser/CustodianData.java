
package com.vnrseeds.samadhan.parser.custodianparser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustodianData {

    @SerializedName("user_id")
    @Expose
    private Integer custodianId;
    @SerializedName("user_name")
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
