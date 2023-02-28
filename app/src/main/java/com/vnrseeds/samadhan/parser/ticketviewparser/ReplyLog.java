
package com.vnrseeds.samadhan.parser.ticketviewparser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReplyLog {

    @SerializedName("ticket_code")
    @Expose
    private String ticketCode;
    @SerializedName("tl_created_by_id")
    @Expose
    private String tlCreatedById;
    @SerializedName("tl_created_by")
    @Expose
    private String tlCreatedBy;
    @SerializedName("tl_message")
    @Expose
    private String tlMessage;
    @SerializedName("tl_created_date")
    @Expose
    private String tlCreatedDate;

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    public String getTlCreatedById() {
        return tlCreatedById;
    }

    public void setTlCreatedById(String tlCreatedById) {
        this.tlCreatedById = tlCreatedById;
    }

    public String getTlCreatedBy() {
        return tlCreatedBy;
    }

    public void setTlCreatedBy(String tlCreatedBy) {
        this.tlCreatedBy = tlCreatedBy;
    }

    public String getTlMessage() {
        return tlMessage;
    }

    public void setTlMessage(String tlMessage) {
        this.tlMessage = tlMessage;
    }

    public String getTlCreatedDate() {
        return tlCreatedDate;
    }

    public void setTlCreatedDate(String tlCreatedDate) {
        this.tlCreatedDate = tlCreatedDate;
    }

}
