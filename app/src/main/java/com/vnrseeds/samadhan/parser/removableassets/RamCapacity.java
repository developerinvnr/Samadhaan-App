
package com.vnrseeds.samadhan.parser.removableassets;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RamCapacity {

    @SerializedName("rc_id")
    @Expose
    private Integer rcId;
    @SerializedName("rc_capacity")
    @Expose
    private Integer rcCapacity;

    public Integer getRcId() {
        return rcId;
    }

    public void setRcId(Integer rcId) {
        this.rcId = rcId;
    }

    public Integer getRcCapacity() {
        return rcCapacity;
    }

    public void setRcCapacity(Integer rcCapacity) {
        this.rcCapacity = rcCapacity;
    }

}
