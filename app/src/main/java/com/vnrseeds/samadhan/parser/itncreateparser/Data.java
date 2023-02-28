
package com.vnrseeds.samadhan.parser.itncreateparser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
public class Data {

    @SerializedName("ticket")
    @Expose
    private Ticket ticket;
    @SerializedName("serviceProviderList")
    @Expose
    private List<ServiceProvider> serviceProviderList;
    @SerializedName("locations")
    @Expose
    private List<Location> locations;
    @SerializedName("workshops")
    @Expose
    private List<Workshop> workshops;
    @SerializedName("transferModeList")
    @Expose
    private List<String> transferModeList;
    @SerializedName("packingTypeList")
    @Expose
    private List<String> packingTypeList;
    @SerializedName("user_is_service_provider")
    @Expose
    private Integer userIsServiceProvider;
    @SerializedName("latestITN")
    @Expose
    private Object latestITN;

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public List<ServiceProvider> getServiceProviderList() {
        return serviceProviderList;
    }

    public void setServiceProviderList(List<ServiceProvider> serviceProviderList) {
        this.serviceProviderList = serviceProviderList;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public List<Workshop> getWorkshops() {
        return workshops;
    }

    public void setWorkshops(List<Workshop> workshops) {
        this.workshops = workshops;
    }

    public List<String> getTransferModeList() {
        return transferModeList;
    }

    public void setTransferModeList(List<String> transferModeList) {
        this.transferModeList = transferModeList;
    }

    public List<String> getPackingTypeList() {
        return packingTypeList;
    }

    public void setPackingTypeList(List<String> packingTypeList) {
        this.packingTypeList = packingTypeList;
    }

    public Integer getUserIsServiceProvider() {
        return userIsServiceProvider;
    }

    public void setUserIsServiceProvider(Integer userIsServiceProvider) {
        this.userIsServiceProvider = userIsServiceProvider;
    }

    public Object getLatestITN() {
        return latestITN;
    }

    public void setLatestITN(Object latestITN) {
        this.latestITN = latestITN;
    }

}
