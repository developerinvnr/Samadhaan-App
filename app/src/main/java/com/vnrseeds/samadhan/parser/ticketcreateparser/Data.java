
package com.vnrseeds.samadhan.parser.ticketcreateparser;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("priorityList")
    @Expose
    private List<Priority> priorityList = null;
    @SerializedName("moduleList")
    @Expose
    private List<Module> moduleList;

    public List<Priority> getPriorityList() {
        return priorityList;
    }

    public void setPriorityList(List<Priority> priorityList) {
        this.priorityList = priorityList;
    }

    public List<Module> getModuleList() {
        return moduleList;
    }

    public void setModuleList(List<Module> moduleList) {
        this.moduleList = moduleList;
    }
}
