
package com.vnrseeds.samadhan.parser.sitevisitparser;
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

}
