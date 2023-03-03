package com.vnrseeds.samadhan.parser.ticketviewparser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParentTicket {
    @SerializedName("ticket_id")
    @Expose
    private Integer ticketId;
    @SerializedName("ticket_code")
    @Expose
    private String ticketCode;
    @SerializedName("ticket_code_id")
    @Expose
    private Integer ticketCodeId;
    @SerializedName("ticket_user_type")
    @Expose
    private String ticketUserType;
    @SerializedName("ticket_service_type")
    @Expose
    private String ticketServiceType;
    @SerializedName("ticket_service_type_id")
    @Expose
    private Integer ticketServiceTypeId;
    @SerializedName("ticket_module_id")
    @Expose
    private Object ticketModuleId;
    @SerializedName("ticket_sub_module_id")
    @Expose
    private Object ticketSubModuleId;
    @SerializedName("ticket_priority_id")
    @Expose
    private Integer ticketPriorityId;
    @SerializedName("ticket_user_id")
    @Expose
    private Integer ticketUserId;
    @SerializedName("ticket_raise_by")
    @Expose
    private Integer ticketRaiseBy;
    @SerializedName("ticket_raise_date")
    @Expose
    private String ticketRaiseDate;
    @SerializedName("ticket_raise_files")
    @Expose
    private Object ticketRaiseFiles;
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
    private Integer ticketAssignBy;
    @SerializedName("ticket_assign_date")
    @Expose
    private String ticketAssignDate;
    @SerializedName("ticket_assign_to")
    @Expose
    private String ticketAssignTo;
    @SerializedName("ticket_assign_description")
    @Expose
    private String ticketAssignDescription;
    @SerializedName("ticket_handle_by")
    @Expose
    private Integer ticketHandleBy;
    @SerializedName("ticket_handle_date")
    @Expose
    private String ticketHandleDate;
    @SerializedName("ticket_estimated_handle_date")
    @Expose
    private String ticketEstimatedHandleDate;
    @SerializedName("ticket_estimated_handle_description")
    @Expose
    private String ticketEstimatedHandleDescription;
    @SerializedName("ticket_estimated_handle_by")
    @Expose
    private Integer ticketEstimatedHandleBy;
    @SerializedName("ticket_handle_description")
    @Expose
    private String ticketHandleDescription;
    @SerializedName("ticket_request_type")
    @Expose
    private Object ticketRequestType;
    @SerializedName("ticket_is_work_in_progress")
    @Expose
    private String ticketIsWorkInProgress;
    @SerializedName("ticket_is_hold")
    @Expose
    private String ticketIsHold;
    @SerializedName("ticket_is_change_request")
    @Expose
    private String ticketIsChangeRequest;
    @SerializedName("ticket_change_request_status")
    @Expose
    private Object ticketChangeRequestStatus;
    @SerializedName("ticket_is_discussion_required")
    @Expose
    private String ticketIsDiscussionRequired;
    @SerializedName("ticket_resolve_by")
    @Expose
    private Object ticketResolveBy;
    @SerializedName("ticket_resolve_date")
    @Expose
    private Object ticketResolveDate;
    @SerializedName("ticket_resolve_description")
    @Expose
    private Object ticketResolveDescription;
    @SerializedName("ticket_is_add_to_asset")
    @Expose
    private String ticketIsAddToAsset;
    @SerializedName("ticket_is_site_visit")
    @Expose
    private String ticketIsSiteVisit;
    @SerializedName("ticket_site_visit_at")
    @Expose
    private Object ticketSiteVisitAt;
    @SerializedName("ticket_resolve_file")
    @Expose
    private Object ticketResolveFile;
    @SerializedName("ticket_reopen_number")
    @Expose
    private Integer ticketReopenNumber;
    @SerializedName("ticket_reopen_by")
    @Expose
    private Object ticketReopenBy;
    @SerializedName("ticket_reopen_description")
    @Expose
    private Object ticketReopenDescription;
    @SerializedName("ticket_reopen_date")
    @Expose
    private Object ticketReopenDate;
    @SerializedName("ticket_close_by")
    @Expose
    private Object ticketCloseBy;
    @SerializedName("ticket_close_date")
    @Expose
    private Object ticketCloseDate;
    @SerializedName("ticket_close_rating")
    @Expose
    private String ticketCloseRating;
    @SerializedName("ticket_close_description")
    @Expose
    private Object ticketCloseDescription;
    @SerializedName("ticket_withdraw_by")
    @Expose
    private Object ticketWithdrawBy;
    @SerializedName("ticket_withdraw_description")
    @Expose
    private Object ticketWithdrawDescription;
    @SerializedName("ticket_withdraw_date")
    @Expose
    private Object ticketWithdrawDate;
    @SerializedName("ticket_create_entry")
    @Expose
    private String ticketCreateEntry;
    @SerializedName("ticket_update_entry")
    @Expose
    private Object ticketUpdateEntry;
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
    @SerializedName("ticket_reply_by")
    @Expose
    private Integer ticketReplyBy;
    @SerializedName("ticket_reply_description")
    @Expose
    private String ticketReplyDescription;
    @SerializedName("ticket_reply_date")
    @Expose
    private String ticketReplyDate;
    @SerializedName("ticket_reply_files")
    @Expose
    private Object ticketReplyFiles;
    @SerializedName("ticket_parent_id")
    @Expose
    private Integer ticketParentId;
    @SerializedName("ticket_created_by")
    @Expose
    private Integer ticketCreatedBy;
    @SerializedName("ticket_created_at")
    @Expose
    private String ticketCreatedAt;
    @SerializedName("ticket_updated_by")
    @Expose
    private Object ticketUpdatedBy;
    @SerializedName("ticket_updated_at")
    @Expose
    private Object ticketUpdatedAt;
    @SerializedName("ticket_status")
    @Expose
    private String ticketStatus;
    @SerializedName("ticket_service_type_name")
    @Expose
    private String ticketServiceTypeName;
    @SerializedName("ticket_service_type_current_status")
    @Expose
    private String ticketServiceTypeCurrentStatus;
    @SerializedName("ticket_asset_ac_id")
    @Expose
    private String ticketAssetAcId;
    @SerializedName("module_name")
    @Expose
    private String moduleName;
    @SerializedName("sub_module_name")
    @Expose
    private String subModuleName;
    @SerializedName("raise_by")
    @Expose
    private String raiseBy;
    @SerializedName("behalf_raise_by")
    @Expose
    private String behalfRaiseBy;
    @SerializedName("issue_name")
    @Expose
    private String issueName;
    @SerializedName("issue_hindi_name")
    @Expose
    private String issueHindiName;
    @SerializedName("assign_to")
    @Expose
    private String assignTo;
    @SerializedName("priority_name")
    @Expose
    private String priorityName;
    @SerializedName("priority_text_color")
    @Expose
    private String priorityTextColor;
    @SerializedName("priority_bg_color")
    @Expose
    private String priorityBgColor;
    @SerializedName("ts_text_color")
    @Expose
    private String tsTextColor;
    @SerializedName("ts_bg_color")
    @Expose
    private String tsBgColor;

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

    public Integer getTicketCodeId() {
        return ticketCodeId;
    }

    public void setTicketCodeId(Integer ticketCodeId) {
        this.ticketCodeId = ticketCodeId;
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

    public Integer getTicketServiceTypeId() {
        return ticketServiceTypeId;
    }

    public void setTicketServiceTypeId(Integer ticketServiceTypeId) {
        this.ticketServiceTypeId = ticketServiceTypeId;
    }

    public Object getTicketModuleId() {
        return ticketModuleId;
    }

    public void setTicketModuleId(Object ticketModuleId) {
        this.ticketModuleId = ticketModuleId;
    }

    public Object getTicketSubModuleId() {
        return ticketSubModuleId;
    }

    public void setTicketSubModuleId(Object ticketSubModuleId) {
        this.ticketSubModuleId = ticketSubModuleId;
    }

    public Integer getTicketPriorityId() {
        return ticketPriorityId;
    }

    public void setTicketPriorityId(Integer ticketPriorityId) {
        this.ticketPriorityId = ticketPriorityId;
    }

    public Integer getTicketUserId() {
        return ticketUserId;
    }

    public void setTicketUserId(Integer ticketUserId) {
        this.ticketUserId = ticketUserId;
    }

    public Integer getTicketRaiseBy() {
        return ticketRaiseBy;
    }

    public void setTicketRaiseBy(Integer ticketRaiseBy) {
        this.ticketRaiseBy = ticketRaiseBy;
    }

    public String getTicketRaiseDate() {
        return ticketRaiseDate;
    }

    public void setTicketRaiseDate(String ticketRaiseDate) {
        this.ticketRaiseDate = ticketRaiseDate;
    }

    public Object getTicketRaiseFiles() {
        return ticketRaiseFiles;
    }

    public void setTicketRaiseFiles(Object ticketRaiseFiles) {
        this.ticketRaiseFiles = ticketRaiseFiles;
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

    public Integer getTicketAssignBy() {
        return ticketAssignBy;
    }

    public void setTicketAssignBy(Integer ticketAssignBy) {
        this.ticketAssignBy = ticketAssignBy;
    }

    public String getTicketAssignDate() {
        return ticketAssignDate;
    }

    public void setTicketAssignDate(String ticketAssignDate) {
        this.ticketAssignDate = ticketAssignDate;
    }

    public String getTicketAssignTo() {
        return ticketAssignTo;
    }

    public void setTicketAssignTo(String ticketAssignTo) {
        this.ticketAssignTo = ticketAssignTo;
    }

    public String getTicketAssignDescription() {
        return ticketAssignDescription;
    }

    public void setTicketAssignDescription(String ticketAssignDescription) {
        this.ticketAssignDescription = ticketAssignDescription;
    }

    public Integer getTicketHandleBy() {
        return ticketHandleBy;
    }

    public void setTicketHandleBy(Integer ticketHandleBy) {
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

    public String getTicketEstimatedHandleDescription() {
        return ticketEstimatedHandleDescription;
    }

    public void setTicketEstimatedHandleDescription(String ticketEstimatedHandleDescription) {
        this.ticketEstimatedHandleDescription = ticketEstimatedHandleDescription;
    }

    public Integer getTicketEstimatedHandleBy() {
        return ticketEstimatedHandleBy;
    }

    public void setTicketEstimatedHandleBy(Integer ticketEstimatedHandleBy) {
        this.ticketEstimatedHandleBy = ticketEstimatedHandleBy;
    }

    public String getTicketHandleDescription() {
        return ticketHandleDescription;
    }

    public void setTicketHandleDescription(String ticketHandleDescription) {
        this.ticketHandleDescription = ticketHandleDescription;
    }

    public Object getTicketRequestType() {
        return ticketRequestType;
    }

    public void setTicketRequestType(Object ticketRequestType) {
        this.ticketRequestType = ticketRequestType;
    }

    public String getTicketIsWorkInProgress() {
        return ticketIsWorkInProgress;
    }

    public void setTicketIsWorkInProgress(String ticketIsWorkInProgress) {
        this.ticketIsWorkInProgress = ticketIsWorkInProgress;
    }

    public String getTicketIsHold() {
        return ticketIsHold;
    }

    public void setTicketIsHold(String ticketIsHold) {
        this.ticketIsHold = ticketIsHold;
    }

    public String getTicketIsChangeRequest() {
        return ticketIsChangeRequest;
    }

    public void setTicketIsChangeRequest(String ticketIsChangeRequest) {
        this.ticketIsChangeRequest = ticketIsChangeRequest;
    }

    public Object getTicketChangeRequestStatus() {
        return ticketChangeRequestStatus;
    }

    public void setTicketChangeRequestStatus(Object ticketChangeRequestStatus) {
        this.ticketChangeRequestStatus = ticketChangeRequestStatus;
    }

    public String getTicketIsDiscussionRequired() {
        return ticketIsDiscussionRequired;
    }

    public void setTicketIsDiscussionRequired(String ticketIsDiscussionRequired) {
        this.ticketIsDiscussionRequired = ticketIsDiscussionRequired;
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

    public String getTicketIsAddToAsset() {
        return ticketIsAddToAsset;
    }

    public void setTicketIsAddToAsset(String ticketIsAddToAsset) {
        this.ticketIsAddToAsset = ticketIsAddToAsset;
    }

    public String getTicketIsSiteVisit() {
        return ticketIsSiteVisit;
    }

    public void setTicketIsSiteVisit(String ticketIsSiteVisit) {
        this.ticketIsSiteVisit = ticketIsSiteVisit;
    }

    public Object getTicketSiteVisitAt() {
        return ticketSiteVisitAt;
    }

    public void setTicketSiteVisitAt(Object ticketSiteVisitAt) {
        this.ticketSiteVisitAt = ticketSiteVisitAt;
    }

    public Object getTicketResolveFile() {
        return ticketResolveFile;
    }

    public void setTicketResolveFile(Object ticketResolveFile) {
        this.ticketResolveFile = ticketResolveFile;
    }

    public Integer getTicketReopenNumber() {
        return ticketReopenNumber;
    }

    public void setTicketReopenNumber(Integer ticketReopenNumber) {
        this.ticketReopenNumber = ticketReopenNumber;
    }

    public Object getTicketReopenBy() {
        return ticketReopenBy;
    }

    public void setTicketReopenBy(Object ticketReopenBy) {
        this.ticketReopenBy = ticketReopenBy;
    }

    public Object getTicketReopenDescription() {
        return ticketReopenDescription;
    }

    public void setTicketReopenDescription(Object ticketReopenDescription) {
        this.ticketReopenDescription = ticketReopenDescription;
    }

    public Object getTicketReopenDate() {
        return ticketReopenDate;
    }

    public void setTicketReopenDate(Object ticketReopenDate) {
        this.ticketReopenDate = ticketReopenDate;
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

    public String getTicketCloseRating() {
        return ticketCloseRating;
    }

    public void setTicketCloseRating(String ticketCloseRating) {
        this.ticketCloseRating = ticketCloseRating;
    }

    public Object getTicketCloseDescription() {
        return ticketCloseDescription;
    }

    public void setTicketCloseDescription(Object ticketCloseDescription) {
        this.ticketCloseDescription = ticketCloseDescription;
    }

    public Object getTicketWithdrawBy() {
        return ticketWithdrawBy;
    }

    public void setTicketWithdrawBy(Object ticketWithdrawBy) {
        this.ticketWithdrawBy = ticketWithdrawBy;
    }

    public Object getTicketWithdrawDescription() {
        return ticketWithdrawDescription;
    }

    public void setTicketWithdrawDescription(Object ticketWithdrawDescription) {
        this.ticketWithdrawDescription = ticketWithdrawDescription;
    }

    public Object getTicketWithdrawDate() {
        return ticketWithdrawDate;
    }

    public void setTicketWithdrawDate(Object ticketWithdrawDate) {
        this.ticketWithdrawDate = ticketWithdrawDate;
    }

    public String getTicketCreateEntry() {
        return ticketCreateEntry;
    }

    public void setTicketCreateEntry(String ticketCreateEntry) {
        this.ticketCreateEntry = ticketCreateEntry;
    }

    public Object getTicketUpdateEntry() {
        return ticketUpdateEntry;
    }

    public void setTicketUpdateEntry(Object ticketUpdateEntry) {
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

    public Integer getTicketReplyBy() {
        return ticketReplyBy;
    }

    public void setTicketReplyBy(Integer ticketReplyBy) {
        this.ticketReplyBy = ticketReplyBy;
    }

    public String getTicketReplyDescription() {
        return ticketReplyDescription;
    }

    public void setTicketReplyDescription(String ticketReplyDescription) {
        this.ticketReplyDescription = ticketReplyDescription;
    }

    public String getTicketReplyDate() {
        return ticketReplyDate;
    }

    public void setTicketReplyDate(String ticketReplyDate) {
        this.ticketReplyDate = ticketReplyDate;
    }

    public Object getTicketReplyFiles() {
        return ticketReplyFiles;
    }

    public void setTicketReplyFiles(Object ticketReplyFiles) {
        this.ticketReplyFiles = ticketReplyFiles;
    }

    public Integer getTicketParentId() {
        return ticketParentId;
    }

    public void setTicketParentId(Integer ticketParentId) {
        this.ticketParentId = ticketParentId;
    }

    public Integer getTicketCreatedBy() {
        return ticketCreatedBy;
    }

    public void setTicketCreatedBy(Integer ticketCreatedBy) {
        this.ticketCreatedBy = ticketCreatedBy;
    }

    public String getTicketCreatedAt() {
        return ticketCreatedAt;
    }

    public void setTicketCreatedAt(String ticketCreatedAt) {
        this.ticketCreatedAt = ticketCreatedAt;
    }

    public Object getTicketUpdatedBy() {
        return ticketUpdatedBy;
    }

    public void setTicketUpdatedBy(Object ticketUpdatedBy) {
        this.ticketUpdatedBy = ticketUpdatedBy;
    }

    public Object getTicketUpdatedAt() {
        return ticketUpdatedAt;
    }

    public void setTicketUpdatedAt(Object ticketUpdatedAt) {
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

    public String getTicketServiceTypeCurrentStatus() {
        return ticketServiceTypeCurrentStatus;
    }

    public void setTicketServiceTypeCurrentStatus(String ticketServiceTypeCurrentStatus) {
        this.ticketServiceTypeCurrentStatus = ticketServiceTypeCurrentStatus;
    }

    public String getTicketAssetAcId() {
        return ticketAssetAcId;
    }

    public void setTicketAssetAcId(String ticketAssetAcId) {
        this.ticketAssetAcId = ticketAssetAcId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getSubModuleName() {
        return subModuleName;
    }

    public void setSubModuleName(String subModuleName) {
        this.subModuleName = subModuleName;
    }

    public String getRaiseBy() {
        return raiseBy;
    }

    public void setRaiseBy(String raiseBy) {
        this.raiseBy = raiseBy;
    }

    public String getBehalfRaiseBy() {
        return behalfRaiseBy;
    }

    public void setBehalfRaiseBy(String behalfRaiseBy) {
        this.behalfRaiseBy = behalfRaiseBy;
    }

    public String getIssueName() {
        return issueName;
    }

    public void setIssueName(String issueName) {
        this.issueName = issueName;
    }

    public String getIssueHindiName() {
        return issueHindiName;
    }

    public void setIssueHindiName(String issueHindiName) {
        this.issueHindiName = issueHindiName;
    }

    public String getAssignTo() {
        return assignTo;
    }

    public void setAssignTo(String assignTo) {
        this.assignTo = assignTo;
    }

    public String getPriorityName() {
        return priorityName;
    }

    public void setPriorityName(String priorityName) {
        this.priorityName = priorityName;
    }

    public String getPriorityTextColor() {
        return priorityTextColor;
    }

    public void setPriorityTextColor(String priorityTextColor) {
        this.priorityTextColor = priorityTextColor;
    }

    public String getPriorityBgColor() {
        return priorityBgColor;
    }

    public void setPriorityBgColor(String priorityBgColor) {
        this.priorityBgColor = priorityBgColor;
    }

    public String getTsTextColor() {
        return tsTextColor;
    }

    public void setTsTextColor(String tsTextColor) {
        this.tsTextColor = tsTextColor;
    }

    public String getTsBgColor() {
        return tsBgColor;
    }

    public void setTsBgColor(String tsBgColor) {
        this.tsBgColor = tsBgColor;
    }
}
