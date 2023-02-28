
package com.vnrseeds.samadhan.parser.addassetdropdownparser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Software {

    @SerializedName("software_id")
    @Expose
    private String softwareId;
    @SerializedName("software_name")
    @Expose
    private String softwareName;

    public String getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(String softwareId) {
        this.softwareId = softwareId;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
    }

}
