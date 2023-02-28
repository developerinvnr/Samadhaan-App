package com.vnrseeds.samadhan.parser.itncreateparser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ticket {
    @SerializedName("ticket_id")
    @Expose
    private Integer ticketId;
    @SerializedName("ticket_code")
    @Expose
    private String ticketCode;
    @SerializedName("ticket_current_status")
    @Expose
    private String ticketCurrentStatus;
    @SerializedName("location_is_base_location")
    @Expose
    private String locationIsBaseLocation;

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    public String getTicketCurrentStatus() {
        return ticketCurrentStatus;
    }

    public void setTicketCurrentStatus(String ticketCurrentStatus) {
        this.ticketCurrentStatus = ticketCurrentStatus;
    }

    public String getLocationIsBaseLocation() {
        return locationIsBaseLocation;
    }

    public void setLocationIsBaseLocation(String locationIsBaseLocation) {
        this.locationIsBaseLocation = locationIsBaseLocation;
    }
}
