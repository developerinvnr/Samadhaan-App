
package com.vnrseeds.samadhan.parser.ticketassetparser;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("userAssets")
    @Expose
    private ArrayList<UserAsset> userAssets = null;
    @SerializedName("userApplications")
    @Expose
    private List<UserApplication> userApplications = null;
    @SerializedName("serviceTypes")
    @Expose
    private List<String> serviceTypes = null;
    @SerializedName("ticketStatusSummary")
    @Expose
    private TicketStatusSummary ticketStatusSummary;
    @SerializedName("ticketSPStatusSummary")
    @Expose
    private TicketSPStatusSummary ticketSPStatusSummary;


    public ArrayList<UserAsset> getUserAssets() {
        return userAssets;
    }

    public void setUserAssets(ArrayList<UserAsset> userAssets) {
        this.userAssets = userAssets;
    }

    public List<UserApplication> getUserApplications() {
        return userApplications;
    }

    public void setUserApplications(List<UserApplication> userApplications) {
        this.userApplications = userApplications;
    }

    public List<String> getServiceTypes() {
        return serviceTypes;
    }

    public void setServiceTypes(List<String> serviceTypes) {
        this.serviceTypes = serviceTypes;
    }

    public TicketStatusSummary getTicketStatusSummary() {
        return ticketStatusSummary;
    }

    public void setTicketStatusSummary(TicketStatusSummary ticketStatusSummary) {
        this.ticketStatusSummary = ticketStatusSummary;
    }

    public TicketSPStatusSummary getTicketSPStatusSummary() {
        return ticketSPStatusSummary;
    }

    public void setTicketSPStatusSummary(TicketSPStatusSummary ticketSPStatusSummary) {
        this.ticketSPStatusSummary = ticketSPStatusSummary;
    }
}
