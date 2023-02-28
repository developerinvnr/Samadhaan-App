
package com.vnrseeds.samadhan.parser.sitevisitparser;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("ticket")
    @Expose
    private Ticket ticket;
    @SerializedName("siteVisits")
    @Expose
    private List<SiteVisit> siteVisits;
    @SerializedName("latestSiteVisit")
    @Expose
    private LatestSiteVisit latestSiteVisit;

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public List<SiteVisit> getSiteVisits() {
        return siteVisits;
    }

    public void setSiteVisits(List<SiteVisit> siteVisits) {
        this.siteVisits = siteVisits;
    }

    public LatestSiteVisit getLatestSiteVisit() {
        return latestSiteVisit;
    }

    public void setLatestSiteVisit(LatestSiteVisit latestSiteVisit) {
        this.latestSiteVisit = latestSiteVisit;
    }

}
