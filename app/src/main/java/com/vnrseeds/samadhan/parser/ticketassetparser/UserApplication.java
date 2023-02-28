package com.vnrseeds.samadhan.parser.ticketassetparser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserApplication {
    @SerializedName("application_id")
    @Expose
    private String applicationId;
    @SerializedName("application_name")
    @Expose
    private String applicationName;
    @SerializedName("application_version")
    @Expose
    private String applicationVersion;
    @SerializedName("application_icon")
    @Expose
    private String applicationIcon;

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationVersion() {
        return applicationVersion;
    }

    public void setApplicationVersion(String applicationVersion) {
        this.applicationVersion = applicationVersion;
    }

    public String getApplicationIcon() {
        return applicationIcon;
    }

    public void setApplicationIcon(String applicationIcon) {
        this.applicationIcon = applicationIcon;
    }
}
