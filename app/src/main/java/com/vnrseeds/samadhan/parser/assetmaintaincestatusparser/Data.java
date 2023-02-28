package com.vnrseeds.samadhan.parser.assetmaintaincestatusparser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("service_is_under_maintenance")
    @Expose
    private Integer serviceIsUnderMaintenance;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getServiceIsUnderMaintenance() {
        return serviceIsUnderMaintenance;
    }

    public void setServiceIsUnderMaintenance(Integer serviceIsUnderMaintenance) {
        this.serviceIsUnderMaintenance = serviceIsUnderMaintenance;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
