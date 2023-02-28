
package com.vnrseeds.samadhan.parser.ticketviewparser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RaiseData {

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
    private String ticketRaiseImage;
    @SerializedName("ticket_issue_ids")
    @Expose
    private String ticketIssueIds;
    @SerializedName("ticket_issue_other")
    @Expose
    private Object ticketIssueOther;
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
    private Object ticketHandleBy;
    @SerializedName("ticket_handle_date")
    @Expose
    private Object ticketHandleDate;
    @SerializedName("ticket_estimated_handle_date")
    @Expose
    private Object ticketEstimatedHandleDate;
    @SerializedName("ticket_handle_description")
    @Expose
    private Object ticketHandleDescription;
    @SerializedName("ticket_handle_private_notes")
    @Expose
    private Object ticketHandlePrivateNotes;
    @SerializedName("ticket_resolve_by")
    @Expose
    private Object ticketResolveBy;
    @SerializedName("ticket_resolve_date")
    @Expose
    private Object ticketResolveDate;
    @SerializedName("ticket_resolve_description")
    @Expose
    private Object ticketResolveDescription;
    @SerializedName("ticket_resolve_private_notes")
    @Expose
    private Object ticketResolvePrivateNotes;
    @SerializedName("ticket_close_by")
    @Expose
    private Object ticketCloseBy;
    @SerializedName("ticket_close_date")
    @Expose
    private Object ticketCloseDate;
    @SerializedName("ticket_close_description")
    @Expose
    private Object ticketCloseDescription;
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
    @SerializedName("assign_to")
    @Expose
    private Object assignTo;
    @SerializedName("assign_by")
    @Expose
    private Object assignBy;
    @SerializedName("handle_by")
    @Expose
    private Object handleBy;
    @SerializedName("resolve_by")
    @Expose
    private Object resolveBy;
    @SerializedName("close_by")
    @Expose
    private Object closeBy;

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

    public String getTicketRaiseImage() {
        return ticketRaiseImage;
    }

    public void setTicketRaiseImage(String ticketRaiseImage) {
        this.ticketRaiseImage = ticketRaiseImage;
    }

    public String getTicketIssueIds() {
        return ticketIssueIds;
    }

    public void setTicketIssueIds(String ticketIssueIds) {
        this.ticketIssueIds = ticketIssueIds;
    }

    public Object getTicketIssueOther() {
        return ticketIssueOther;
    }

    public void setTicketIssueOther(Object ticketIssueOther) {
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

    public Object getTicketHandleBy() {
        return ticketHandleBy;
    }

    public void setTicketHandleBy(Object ticketHandleBy) {
        this.ticketHandleBy = ticketHandleBy;
    }

    public Object getTicketHandleDate() {
        return ticketHandleDate;
    }

    public void setTicketHandleDate(Object ticketHandleDate) {
        this.ticketHandleDate = ticketHandleDate;
    }

    public Object getTicketEstimatedHandleDate() {
        return ticketEstimatedHandleDate;
    }

    public void setTicketEstimatedHandleDate(Object ticketEstimatedHandleDate) {
        this.ticketEstimatedHandleDate = ticketEstimatedHandleDate;
    }

    public Object getTicketHandleDescription() {
        return ticketHandleDescription;
    }

    public void setTicketHandleDescription(Object ticketHandleDescription) {
        this.ticketHandleDescription = ticketHandleDescription;
    }

    public Object getTicketHandlePrivateNotes() {
        return ticketHandlePrivateNotes;
    }

    public void setTicketHandlePrivateNotes(Object ticketHandlePrivateNotes) {
        this.ticketHandlePrivateNotes = ticketHandlePrivateNotes;
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

    public Object getTicketResolvePrivateNotes() {
        return ticketResolvePrivateNotes;
    }

    public void setTicketResolvePrivateNotes(Object ticketResolvePrivateNotes) {
        this.ticketResolvePrivateNotes = ticketResolvePrivateNotes;
    }

    public Object getTicketCloseBy() {
        return ticketCloseBy;
    }

    public void setTicketCloseBy(Object ticketCloseBy) {
        this.ticketCloseBy = ticketCloseBy;
    }

    public Object getTicketCloseDate() {
        return ticketCloseDate;
    }

    public void setTicketCloseDate(Object ticketCloseDate) {
        this.ticketCloseDate = ticketCloseDate;
    }

    public Object getTicketCloseDescription() {
        return ticketCloseDescription;
    }

    public void setTicketCloseDescription(Object ticketCloseDescription) {
        this.ticketCloseDescription = ticketCloseDescription;
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

    public Object getAssignTo() {
        return assignTo;
    }

    public void setAssignTo(Object assignTo) {
        this.assignTo = assignTo;
    }

    public Object getAssignBy() {
        return assignBy;
    }

    public void setAssignBy(Object assignBy) {
        this.assignBy = assignBy;
    }

    public Object getHandleBy() {
        return handleBy;
    }

    public void setHandleBy(Object handleBy) {
        this.handleBy = handleBy;
    }

    public Object getResolveBy() {
        return resolveBy;
    }

    public void setResolveBy(Object resolveBy) {
        this.resolveBy = resolveBy;
    }

    public Object getCloseBy() {
        return closeBy;
    }

    public void setCloseBy(Object closeBy) {
        this.closeBy = closeBy;
    }

}
