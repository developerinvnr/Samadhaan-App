
package com.vnrseeds.samadhan.parser.sitevisitparser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SiteVisit {

    @SerializedName("tsv_id")
    @Expose
    private Integer tsvId;
    @SerializedName("tsv_ticket_id")
    @Expose
    private Integer tsvTicketId;
    @SerializedName("tsv_visited_by")
    @Expose
    private Integer tsvVisitedBy;
    @SerializedName("tsv_visited_at")
    @Expose
    private String tsvVisitedAt;
    @SerializedName("tsv_description")
    @Expose
    private String tsvDescription;
    @SerializedName("tsv_created_by")
    @Expose
    private Integer tsvCreatedBy;
    @SerializedName("tsv_created_at")
    @Expose
    private String tsvCreatedAt;
    @SerializedName("tsv_cancel_by")
    @Expose
    private Object tsvCancelBy;
    @SerializedName("tsv_cancel_at")
    @Expose
    private Object tsvCancelAt;
    @SerializedName("tsv_cancel_remarks")
    @Expose
    private Object tsvCancelRemarks;
    @SerializedName("tsv_confirm_by")
    @Expose
    private Object tsvConfirmBy;
    @SerializedName("tsv_confirm_at")
    @Expose
    private Object tsvConfirmAt;
    @SerializedName("tsv_create_entry")
    @Expose
    private String tsvCreateEntry;
    @SerializedName("tsv_update_entry")
    @Expose
    private Object tsvUpdateEntry;
    @SerializedName("tsv_updated_by")
    @Expose
    private Object tsvUpdatedBy;
    @SerializedName("tsv_updated_at")
    @Expose
    private Object tsvUpdatedAt;
    @SerializedName("tsv_is_rescheduled")
    @Expose
    private String tsvIsRescheduled;
    @SerializedName("tsv_status")
    @Expose
    private String tsvStatus;
    @SerializedName("tsv_visited_by_name")
    @Expose
    private String tsvVisitedByName;
    @SerializedName("tsv_created_by_name")
    @Expose
    private String tsvCreatedByName;
    @SerializedName("tsv_cancel_by_name")
    @Expose
    private Object tsvCancelByName;
    @SerializedName("tsv_confirm_by_name")
    @Expose
    private Object tsvConfirmByName;
    @SerializedName("tsv_updated_by_name")
    @Expose
    private Object tsvUpdatedByName;

    public Integer getTsvId() {
        return tsvId;
    }

    public void setTsvId(Integer tsvId) {
        this.tsvId = tsvId;
    }

    public Integer getTsvTicketId() {
        return tsvTicketId;
    }

    public void setTsvTicketId(Integer tsvTicketId) {
        this.tsvTicketId = tsvTicketId;
    }

    public Integer getTsvVisitedBy() {
        return tsvVisitedBy;
    }

    public void setTsvVisitedBy(Integer tsvVisitedBy) {
        this.tsvVisitedBy = tsvVisitedBy;
    }

    public String getTsvVisitedAt() {
        return tsvVisitedAt;
    }

    public void setTsvVisitedAt(String tsvVisitedAt) {
        this.tsvVisitedAt = tsvVisitedAt;
    }

    public String getTsvDescription() {
        return tsvDescription;
    }

    public void setTsvDescription(String tsvDescription) {
        this.tsvDescription = tsvDescription;
    }

    public Integer getTsvCreatedBy() {
        return tsvCreatedBy;
    }

    public void setTsvCreatedBy(Integer tsvCreatedBy) {
        this.tsvCreatedBy = tsvCreatedBy;
    }

    public String getTsvCreatedAt() {
        return tsvCreatedAt;
    }

    public void setTsvCreatedAt(String tsvCreatedAt) {
        this.tsvCreatedAt = tsvCreatedAt;
    }

    public Object getTsvCancelBy() {
        return tsvCancelBy;
    }

    public void setTsvCancelBy(Object tsvCancelBy) {
        this.tsvCancelBy = tsvCancelBy;
    }

    public Object getTsvCancelAt() {
        return tsvCancelAt;
    }

    public void setTsvCancelAt(Object tsvCancelAt) {
        this.tsvCancelAt = tsvCancelAt;
    }

    public Object getTsvCancelRemarks() {
        return tsvCancelRemarks;
    }

    public void setTsvCancelRemarks(Object tsvCancelRemarks) {
        this.tsvCancelRemarks = tsvCancelRemarks;
    }

    public Object getTsvConfirmBy() {
        return tsvConfirmBy;
    }

    public void setTsvConfirmBy(Object tsvConfirmBy) {
        this.tsvConfirmBy = tsvConfirmBy;
    }

    public Object getTsvConfirmAt() {
        return tsvConfirmAt;
    }

    public void setTsvConfirmAt(Object tsvConfirmAt) {
        this.tsvConfirmAt = tsvConfirmAt;
    }

    public String getTsvCreateEntry() {
        return tsvCreateEntry;
    }

    public void setTsvCreateEntry(String tsvCreateEntry) {
        this.tsvCreateEntry = tsvCreateEntry;
    }

    public Object getTsvUpdateEntry() {
        return tsvUpdateEntry;
    }

    public void setTsvUpdateEntry(Object tsvUpdateEntry) {
        this.tsvUpdateEntry = tsvUpdateEntry;
    }

    public Object getTsvUpdatedBy() {
        return tsvUpdatedBy;
    }

    public void setTsvUpdatedBy(Object tsvUpdatedBy) {
        this.tsvUpdatedBy = tsvUpdatedBy;
    }

    public Object getTsvUpdatedAt() {
        return tsvUpdatedAt;
    }

    public void setTsvUpdatedAt(Object tsvUpdatedAt) {
        this.tsvUpdatedAt = tsvUpdatedAt;
    }

    public String getTsvIsRescheduled() {
        return tsvIsRescheduled;
    }

    public void setTsvIsRescheduled(String tsvIsRescheduled) {
        this.tsvIsRescheduled = tsvIsRescheduled;
    }

    public String getTsvStatus() {
        return tsvStatus;
    }

    public void setTsvStatus(String tsvStatus) {
        this.tsvStatus = tsvStatus;
    }

    public String getTsvVisitedByName() {
        return tsvVisitedByName;
    }

    public void setTsvVisitedByName(String tsvVisitedByName) {
        this.tsvVisitedByName = tsvVisitedByName;
    }

    public String getTsvCreatedByName() {
        return tsvCreatedByName;
    }

    public void setTsvCreatedByName(String tsvCreatedByName) {
        this.tsvCreatedByName = tsvCreatedByName;
    }

    public Object getTsvCancelByName() {
        return tsvCancelByName;
    }

    public void setTsvCancelByName(Object tsvCancelByName) {
        this.tsvCancelByName = tsvCancelByName;
    }

    public Object getTsvConfirmByName() {
        return tsvConfirmByName;
    }

    public void setTsvConfirmByName(Object tsvConfirmByName) {
        this.tsvConfirmByName = tsvConfirmByName;
    }

    public Object getTsvUpdatedByName() {
        return tsvUpdatedByName;
    }

    public void setTsvUpdatedByName(Object tsvUpdatedByName) {
        this.tsvUpdatedByName = tsvUpdatedByName;
    }

}
