
package com.vnrseeds.samadhan.parser.srnparser;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("ticket")
    @Expose
    private Ticket ticket;
    @SerializedName("vendors")
    @Expose
    private List<Vendor> vendors;
    @SerializedName("serviceCenters")
    @Expose
    private List<ServiceCenter> serviceCenters;
    @SerializedName("workshops")
    @Expose
    private List<Workshop> workshops;
    @SerializedName("modes")
    @Expose
    private List<String> modes;
    @SerializedName("packingTypes")
    @Expose
    private List<String> packingTypes;
    @SerializedName("srnList")
    @Expose
    private List<Object> srnList;
    @SerializedName("user_is_service_provider")
    @Expose
    private Integer userIsServiceProvider;
    @SerializedName("latestSRN")
    @Expose
    private Object latestSRN;
    @SerializedName("primaryLocations")
    @Expose
    private List<PrimaryLocation> primaryLocations;

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public List<Vendor> getVendors() {
        return vendors;
    }

    public void setVendors(List<Vendor> vendors) {
        this.vendors = vendors;
    }

    public List<ServiceCenter> getServiceCenters() {
        return serviceCenters;
    }

    public void setServiceCenters(List<ServiceCenter> serviceCenters) {
        this.serviceCenters = serviceCenters;
    }

    public List<Workshop> getWorkshops() {
        return workshops;
    }

    public void setWorkshops(List<Workshop> workshops) {
        this.workshops = workshops;
    }

    public List<String> getModes() {
        return modes;
    }

    public void setModes(List<String> modes) {
        this.modes = modes;
    }

    public List<String> getPackingTypes() {
        return packingTypes;
    }

    public void setPackingTypes(List<String> packingTypes) {
        this.packingTypes = packingTypes;
    }

    public List<Object> getSrnList() {
        return srnList;
    }

    public void setSrnList(List<Object> srnList) {
        this.srnList = srnList;
    }

    public Integer getUserIsServiceProvider() {
        return userIsServiceProvider;
    }

    public void setUserIsServiceProvider(Integer userIsServiceProvider) {
        this.userIsServiceProvider = userIsServiceProvider;
    }

    public Object getLatestSRN() {
        return latestSRN;
    }

    public void setLatestSRN(Object latestSRN) {
        this.latestSRN = latestSRN;
    }

    public List<PrimaryLocation> getPrimaryLocations() {
        return primaryLocations;
    }

    public void setPrimaryLocations(List<PrimaryLocation> primaryLocations) {
        this.primaryLocations = primaryLocations;
    }
}
