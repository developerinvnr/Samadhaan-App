package com.vnrseeds.samadhan.parser.ticketassetparser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class TicketSPStatusSummary {
    @SerializedName("assign")
    @Expose
    private String assign;
    @SerializedName("diagnosis")
    @Expose
    private String diagnosis;
    @SerializedName("resolve")
    @Expose
    private String resolve;

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
}
