package com.vnrseeds.samadhan.parser.ticketassetparser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class TicketStatusSummary {
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("open")
    @Expose
    private String open;
    @SerializedName("assign")
    @Expose
    private String assign;
    @SerializedName("diagnosis")
    @Expose
    private String diagnosis;
    @SerializedName("resolve")
    @Expose
    private String resolve;
    @SerializedName("close")
    @Expose
    private String close;
    @SerializedName("withdraw")
    @Expose
    private String withdraw;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getAssign() {
        return assign;
    }

    public void setAssign(String assign) {
        this.assign = assign;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getResolve() {
        return resolve;
    }

    public void setResolve(String resolve) {
        this.resolve = resolve;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(String withdraw) {
        this.withdraw = withdraw;
    }
}
