
package com.vnrseeds.samadhan.parser.priorityparser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Datum {

    @SerializedName("priority_id")
    @Expose
    private Integer priorityId;
    @SerializedName("priority_name")
    @Expose
    private String priorityName;

    public Integer getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(Integer priorityId) {
        this.priorityId = priorityId;
    }

    public String getPriorityName() {
        return priorityName;
    }

    public void setPriorityName(String priorityName) {
        this.priorityName = priorityName;
    }

}
