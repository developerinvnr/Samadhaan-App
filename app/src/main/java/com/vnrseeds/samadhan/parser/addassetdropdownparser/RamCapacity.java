
package com.vnrseeds.samadhan.parser.addassetdropdownparser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RamCapacity {

    @SerializedName("rc_id")
    @Expose
    private String rcId;
    @SerializedName("rc_capacity")
    @Expose
    private String rcCapacity;

    public String getRcId() {
        return rcId;
    }

    public void setRcId(String rcId) {
        this.rcId = rcId;
    }

    public String getRcCapacity() {
        return rcCapacity;
    }

    public void setRcCapacity(String rcCapacity) {
        this.rcCapacity = rcCapacity;
    }

}
