
package com.vnrseeds.samadhan.parser.addtoticketparser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("ticket_id")
    @Expose
    private String ticketId;
    @SerializedName("ticket_code")
    @Expose
    private String ticketCode;
    @SerializedName("ticket_user_type")
    @Expose
    private String ticketUserType;
    @SerializedName("ticket_service_type")
    @Expose
    private String ticketServiceType;
    @SerializedName("ticket_service_type_id")
    @Expose
    private String ticketServiceTypeId;
    @SerializedName("ticket_priority_id")
    @Expose
    private String ticketPriorityId;
    @SerializedName("ticket_user_id")
    @Expose
    private String ticketUserId;
    @SerializedName("ticket_raise_by")
    @Expose
    private String ticketRaiseBy;
    @SerializedName("ticket_raise_date")
    @Expose
    private String ticketRaiseDate;
    @SerializedName("ticket_raise_image")
    @Expose
    private Object ticketRaiseImage;
    @SerializedName("ticket_issue_ids")
    @Expose
    private String ticketIssueIds;
    @SerializedName("ticket_issue_other")
    @Expose
    private String ticketIssueOther;
    @SerializedName("ticket_description")
    @Expose
    private String ticketDescription;
    @SerializedName("ticket_current_status")
    @Expose
    private String ticketCurrentStatus;
    @SerializedName("ticket_assign_by")
    @Expose
    private Object ticketAssignBy;
    @SerializedName("ticket_assign_date")
    @Expose
    private Object ticketAssignDate;
    @SerializedName("ticket_assign_to")
    @Expose
    private Object ticketAssignTo;
    @SerializedName("ticket_assign_description")
    @Expose
    private Object ticketAssignDescription;
    @SerializedName("ticket_handle_by")
    @Expose
    private String ticketHandleBy;
    @SerializedName("ticket_handle_date")
    @Expose
    private String ticketHandleDate;
    @SerializedName("ticket_estimated_handle_date")
    @Expose
    private String ticketEstimatedHandleDate;
    @SerializedName("ticket_handle_description")
    @Expose
    private String ticketHandleDescription;
    @SerializedName("ticket_is_work_in_progress")
    @Expose
    private String ticketIsWorkInProgress;
    @SerializedName("ticket_resolve_by")
    @Expose
    private Object ticketResolveBy;
    @SerializedName("ticket_resolve_date")
    @Expose
    private Object ticketResolveDate;
    @SerializedName("ticket_resolve_description")
    @Expose
    private Object ticketResolveDescription;
    @SerializedName("ticket_reopen_number")
    @Expose
    private String ticketReopenNumber;
    @SerializedName("ticket_reopen_by")
    @Expose
    private String ticketReopenBy;
    @SerializedName("ticket_reopen_description")
    @Expose
    private String ticketReopenDescription;
    @SerializedName("ticket_reopen_date")
    @Expose
    private String ticketReopenDate;
    @SerializedName("ticket_close_by")
    @Expose
    private String ticketCloseBy;
    @SerializedName("ticket_close_date")
    @Expose
    private String ticketCloseDate;
    @SerializedName("ticket_close_rating")
    @Expose
    private String ticketCloseRating;
    @SerializedName("ticket_close_description")
    @Expose
    private String ticketCloseDescription;
    @SerializedName("ticket_cancel_by")
    @Expose
    private Object ticketCancelBy;
    @SerializedName("ticket_cancel_description")
    @Expose
    private Object ticketCancelDescription;
    @SerializedName("ticket_cancel_date")
    @Expose
    private Object ticketCancelDate;
    @SerializedName("ticket_create_entry")
    @Expose
    private String ticketCreateEntry;
    @SerializedName("ticket_update_entry")
    @Expose
    private String ticketUpdateEntry;
    @SerializedName("ticket_is_viewed")
    @Expose
    private String ticketIsViewed;
    @SerializedName("ticket_viewed_by")
    @Expose
    private String ticketViewedBy;
    @SerializedName("ticket_add_log")
    @Expose
    private String ticketAddLog;
    @SerializedName("ticket_viewed_at")
    @Expose
    private Object ticketViewedAt;
    @SerializedName("ticket_created_by")
    @Expose
    private String ticketCreatedBy;
    @SerializedName("ticket_created_at")
    @Expose
    private String ticketCreatedAt;
    @SerializedName("ticket_updated_by")
    @Expose
    private String ticketUpdatedBy;
    @SerializedName("ticket_updated_at")
    @Expose
    private String ticketUpdatedAt;
    @SerializedName("ticket_status")
    @Expose
    private String ticketStatus;
    @SerializedName("ticket_service_type_name")
    @Expose
    private String ticketServiceTypeName;
    @SerializedName("priority_name")
    @Expose
    private String priorityName;
    @SerializedName("raise_by")
    @Expose
    private String raiseBy;
    @SerializedName("issue_name")
    @Expose
    private String issueName;
    @SerializedName("is_raised_already")
    @Expose
    private Object isRaisedAlready;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    public String getTicketUserType() {
        return ticketUserType;
    }

    public void setTicketUserType(String ticketUserType) {
        this.ticketUserType = ticketUserType;
    }

    public String getTicketServiceType() {
        return ticketServiceType;
    }

    public void setTicketServiceType(String ticketServiceType) {
        this.ticketServiceType = ticketServiceType;
    }

    public String getTicketServiceTypeId() {
        return ticketServiceTypeId;
    }

    public void setTicketServiceTypeId(String ticketServiceTypeId) {
        this.ticketServiceTypeId = ticketServiceTypeId;
    }

    public String getTicketPriorityId() {
        return ticketPriorityId;
    }

    public void setTicketPriorityId(String ticketPriorityId) {
        this.ticketPriorityId = ticketPriorityId;
    }

    public String getTicketUserId() {
        return ticketUserId;
    }

    public void setTicketUserId(String ticketUserId) {
        this.ticketUserId = ticketUserId;
    }

    public String getTicketRaiseBy() {
        return ticketRaiseBy;
    }

    public void setTicketRaiseBy(String ticketRaiseBy) {
        this.ticketRaiseBy = ticketRaiseBy;
    }

    public String getTicketRaiseDate() {
        return ticketRaiseDate;
    }

    public void setTicketRaiseDate(String ticketRaiseDate) {
        this.ticketRaiseDate = ticketRaiseDate;
    }

    public Object getTicketRaiseImage() {
        return ticketRaiseImage;
    }

    public void setTicketRaiseImage(Object ticketRaiseImage) {
        this.ticketRaiseImage = ticketRaiseImage;
    }

    public String getTicketIssueIds() {
        return ticketIssueIds;
    }

    public void setTicketIssueIds(String ticketIssueIds) {
        this.ticketIssueIds = ticketIssueIds;
    }

    public String getTicketIssueOther() {
        return ticketIssueOther;
    }

    public void setTicketIssueOther(String ticketIssueOther) {
        this.ticketIssueOther = ticketIssueOther;
    }

    public String getTicketDescription() {
        return ticketDescription;
    }

    public void setTicketDescription(String ticketDescription) {
        this.ticketDescription = ticketDescription;
    }

    public String getTicketCurrentStatus() {
        return ticketCurrentStatus;
    }

    public void setTicketCurrentStatus(String ticketCurrentStatus) {
        this.ticketCurrentStatus = ticketCurrentStatus;
    }

    public Object getTicketAssignBy() {
        return ticketAssignBy;
    }

    public void setTicketAssignBy(Object ticketAssignBy) {
        this.ticketAssignBy = ticketAssignBy;
    }

    public Object getTicketAssignDate() {
        return ticketAssignDate;
    }

    public void setTicketAssignDate(Object ticketAssignDate) {
        this.ticketAssignDate = ticketAssignDate;
    }

    public Object getTicketAssignTo() {
        return ticketAssignTo;
    }

    public void setTicketAssignTo(Object ticketAssignTo) {
        this.ticketAssignTo = ticketAssignTo;
    }

    public Object getTicketAssignDescription() {
        return ticketAssignDescription;
    }

    public void setTicketAssignDescription(Object ticketAssignDescription) {
        this.ticketAssignDescription = ticketAssignDescription;
    }

    public String getTicketHandleBy() {
        return ticketHandleBy;
    }

    public void setTicketHandleBy(String ticketHandleBy) {
        this.ticketHandleBy = ticketHandleBy;
    }

    public String getTicketHandleDate() {
        return ticketHandleDate;
    }

    public void setTicketHandleDate(String ticketHandleDate) {
        this.ticketHandleDate = ticketHandleDate;
    }

    public String getTicketEstimatedHandleDate() {
        return ticketEstimatedHandleDate;
    }

    public void setTicketEstimatedHandleDate(String ticketEstimatedHandleDate) {
        this.ticketEstimatedHandleDate = ticketEstimatedHandleDate;
    }

    public String getTicketHandleDescription() {
        return ticketHandleDescription;
    }

    public void setTicketHandleDescription(String ticketHandleDescription) {
        this.ticketHandleDescription = ticketHandleDescription;
    }

    public String getTicketIsWorkInProgress() {
        return ticketIsWorkInProgress;
    }

    public void setTicketIsWorkInProgress(String ticketIsWorkInProgress) {
        this.ticketIsWorkInProgress = ticketIsWorkInProgress;
    }

    public Object getTicketResolveBy() {
        return ticketResolveBy;
    }

    public void setTicketResolveBy(Object ticketResolveBy) {
        this.ticketResolveBy = ticketResolveBy;
    }

    public Object getTicketResolveDate() {
        return ticketResolveDate;
    }

    public void setTicketResolveDate(Object ticketResolveDate) {
        this.ticketResolveDate = ticketResolveDate;
    }

    public Object getTicketResolveDescription() {
        return ticketResolveDescription;
    }

    public void setTicketResolveDescription(Object ticketResolveDescription) {
        this.ticketResolveDescription = ticketResolveDescription;
    }

    public String getTicketReopenNumber() {
        return ticketReopenNumber;
    }

    public void setTicketReopenNumber(String ticketReopenNumber) {
        this.ticketReopenNumber = ticketReopenNumber;
    }

    public String getTicketReopenBy() {
        return ticketReopenBy;
    }

    public void setTicketReopenBy(String ticketReopenBy) {
        this.ticketReopenBy = ticketReopenBy;
    }

    public String getTicketReopenDescription() {
        return ticketReopenDescription;
    }

    public void setTicketReopenDescription(String ticketReopenDescription) {
        this.ticketReopenDescription = ticketReopenDescription;
    }

    public String getTicketReopenDate() {
        return ticketReopenDate;
    }

    public void setTicketReopenDate(String ticketReopenDate) {
        this.ticketReopenDate = ticketReopenDate;
    }

    public String getTicketCloseBy() {
        return ticketCloseBy;
    }

    public void setTicketCloseBy(String ticketCloseBy) {
        this.ticketCloseBy = ticketCloseBy;
    }

    public String getTicketCloseDate() {
        return ticketCloseDate;
    }

    public void setTicketCloseDate(String ticketCloseDate) {
        this.ticketCloseDate = ticketCloseDate;
    }

    public String getTicketCloseRating() {
        return ticketCloseRating;
    }

    public void setTicketCloseRating(String ticketCloseRating) {
        this.ticketCloseRating = ticketCloseRating;
    }

    public String getTicketCloseDescription() {
        return ticketCloseDescription;
    }

    public void setTicketCloseDescription(String ticketCloseDescription) {
        this.ticketCloseDescription = ticketCloseDescription;
    }

    public Object getTicketCancelBy() {
        return ticketCancelBy;
    }

    public void setTicketCancelBy(Object ticketCancelBy) {
        this.ticketCancelBy = ticketCancelBy;
    }

    public Object getTicketCancelDescription() {
        return ticketCancelDescription;
    }

    public void setTicketCancelDescription(Object ticketCancelDescription) {
        this.ticketCancelDescription = ticketCancelDescription;
    }

    public Object getTicketCancelDate() {
        return ticketCancelDate;
    }

    public void setTicketCancelDate(Object ticketCancelDate) {
        this.ticketCancelDate = ticketCancelDate;
    }

    public String getTicketCreateEntry() {
        return ticketCreateEntry;
    }

    public void setTicketCreateEntry(String ticketCreateEntry) {
        this.ticketCreateEntry = ticketCreateEntry;
    }

    public String getTicketUpdateEntry() {
        return ticketUpdateEntry;
    }

    public void setTicketUpdateEntry(String ticketUpdateEntry) {
        this.ticketUpdateEntry = ticketUpdateEntry;
    }

    public String getTicketIsViewed() {
        return ticketIsViewed;
    }

    public void setTicketIsViewed(String ticketIsViewed) {
        this.ticketIsViewed = ticketIsViewed;
    }

    public String getTicketViewedBy() {
        return ticketViewedBy;
    }

    public void setTicketViewedBy(String ticketViewedBy) {
        this.ticketViewedBy = ticketViewedBy;
    }

    public String getTicketAddLog() {
        return ticketAddLog;
    }

    public void setTicketAddLog(String ticketAddLog) {
        this.ticketAddLog = ticketAddLog;
    }

    public Object getTicketViewedAt() {
        return ticketViewedAt;
    }

    public void setTicketViewedAt(Object ticketViewedAt) {
        this.ticketViewedAt = ticketViewedAt;
    }

    public String getTicketCreatedBy() {
        return ticketCreatedBy;
    }

    public void setTicketCreatedBy(String ticketCreatedBy) {
        this.ticketCreatedBy = ticketCreatedBy;
    }

    public String getTicketCreatedAt() {
        return ticketCreatedAt;
    }

    public void setTicketCreatedAt(String ticketCreatedAt) {
        this.ticketCreatedAt = ticketCreatedAt;
    }

    public String getTicketUpdatedBy() {
        return ticketUpdatedBy;
    }

    public void setTicketUpdatedBy(String ticketUpdatedBy) {
        this.ticketUpdatedBy = ticketUpdatedBy;
    }

    public String getTicketUpdatedAt() {
        return ticketUpdatedAt;
    }

    public void setTicketUpdatedAt(String ticketUpdatedAt) {
        this.ticketUpdatedAt = ticketUpdatedAt;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public String getTicketServiceTypeName() {
        return ticketServiceTypeName;
    }

    public void setTicketServiceTypeName(String ticketServiceTypeName) {
        this.ticketServiceTypeName = ticketServiceTypeName;
    }

    public String getPriorityName() {
        return priorityName;
    }

    public void setPriorityName(String priorityName) {
        this.priorityName = priorityName;
    }

    public String getRaiseBy() {
        return raiseBy;
    }

    public void setRaiseBy(String raiseBy) {
        this.raiseBy = raiseBy;
    }

    public String getIssueName() {
        return issueName;
    }

    public void setIssueName(String issueName) {
        this.issueName = issueName;
    }

    public Object getIsRaisedAlready() {
        return isRaisedAlready;
    }

    public void setIsRaisedAlready(Object isRaisedAlready) {
        this.isRaisedAlready = isRaisedAlready;
    }

}
