
package com.vnrseeds.samadhan.parser.ticketslistparser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Datum {

    @SerializedName("ticket_id")
    @Expose
    private String ticketId;
    @SerializedName("ticket_code")
    @Expose
    private String ticketCode;
    @SerializedName("ticket_code_id")
    @Expose
    private String ticketCodeId;
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
    @SerializedName("ticket_raise_files")
    @Expose
    private List<String> ticketRaiseFiles;
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
    private String ticketAssignBy;
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
    private String ticketHandleBy;
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
    private String ticketEstimatedHandleBy;
    @SerializedName("ticket_handle_description")
    @Expose
    private String ticketHandleDescription;
    @SerializedName("ticket_is_work_in_progress")
    @Expose
    private String ticketIsWorkInProgress;
    @SerializedName("ticket_resolve_by")
    @Expose
    private String ticketResolveBy;
    @SerializedName("ticket_resolve_date")
    @Expose
    private String ticketResolveDate;
    @SerializedName("ticket_resolve_description")
    @Expose
    private String ticketResolveDescription;
    @SerializedName("ticket_is_add_to_asset")
    @Expose
    private String ticketIsAddToAsset;
    @SerializedName("ticket_is_site_visit")
    @Expose
    private String ticketIsSiteVisit;
    @SerializedName("ticket_site_visit_at")
    @Expose
    private String ticketSiteVisitAt;
    @SerializedName("ticket_resolve_file")
    @Expose
    private String ticketResolveFile;
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
    @SerializedName("ticket_withdraw_by")
    @Expose
    private String ticketWithdrawBy;
    @SerializedName("ticket_withdraw_description")
    @Expose
    private String ticketWithdrawDescription;
    @SerializedName("ticket_withdraw_date")
    @Expose
    private String ticketWithdrawDate;
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
    private String ticketViewedAt;
    @SerializedName("ticket_reply_by")
    @Expose
    private String ticketReplyBy;
    @SerializedName("ticket_reply_description")
    @Expose
    private String ticketReplyDescription;
    @SerializedName("ticket_reply_date")
    @Expose
    private String ticketReplyDate;
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
    @SerializedName("ticket_service_type_icon")
    @Expose
    private String ticketServiceTypeIcon;
    @SerializedName("ticket_service_type_icon_big")
    @Expose
    private String ticketServiceTypeIconBig;
    @SerializedName("ticket_service_type_name")
    @Expose
    private String ticketServiceTypeName;
    @SerializedName("ticket_asset_type")
    @Expose
    private String ticketAssetType;
    @SerializedName("ticket_asset_ac_id")
    @Expose
    private String ticketAssetAcId;
    @SerializedName("ticket_asset_ac_name")
    @Expose
    private String ticketAssetAcName;
    @SerializedName("priority_name")
    @Expose
    private String priorityName;
    @SerializedName("raise_by")
    @Expose
    private String raiseBy;
    @SerializedName("issue_name")
    @Expose
    private String issueName;
    @SerializedName("assign_to")
    @Expose
    private String assignTo;
    @SerializedName("assign_by")
    @Expose
    private String assignBy;
    @SerializedName("handle_by")
    @Expose
    private String handleBy;
    @SerializedName("resolve_by")
    @Expose
    private String resolveBy;
    @SerializedName("close_by")
    @Expose
    private String closeBy;
    @SerializedName("withdraw_by")
    @Expose
    private String withdrawBy;
    @SerializedName("reply_by")
    @Expose
    private String replyBy;
    @SerializedName("ticket_issue_ids_str")
    @Expose
    private String ticketIssueIdsStr;
    @SerializedName("ticket_service_type_current_status")
    @Expose
    private String ticketServiceTypeCurrentStatus;
    @SerializedName("ticket_site_visit_description")
    @Expose
    private String ticketSiteVisitDescription;
    @SerializedName("site_visit_date")
    @Expose
    private String ticketSiteVisitDate;
    @SerializedName("behalf_raise_by")
    @Expose
    private String behalfRaiseBy;
    @SerializedName("location_is_base_location")
    @Expose
    private String locationIsBaseLocation;
    @SerializedName("asset_is_byod")
    @Expose
    private String assetIsByod;
    @SerializedName("ticket_module_id")
    @Expose
    private String ticketModuleId;
    @SerializedName("ticket_sub_module_id")
    @Expose
    private String ticketSubModuleId;
    @SerializedName("module_name")
    @Expose
    private String moduleName;
    @SerializedName("sub_module_name")
    @Expose
    private String subModuleName;

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

    public String getTicketCodeId() {
        return ticketCodeId;
    }

    public void setTicketCodeId(String ticketCodeId) {
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

    public List<String> getTicketRaiseFiles() {
        return ticketRaiseFiles;
    }

    public void setTicketRaiseFiles(List<String> ticketRaiseFiles) {
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

    public String getTicketAssignBy() {
        return ticketAssignBy;
    }

    public void setTicketAssignBy(String ticketAssignBy) {
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

    public String getTicketEstimatedHandleDescription() {
        return ticketEstimatedHandleDescription;
    }

    public void setTicketEstimatedHandleDescription(String ticketEstimatedHandleDescription) {
        this.ticketEstimatedHandleDescription = ticketEstimatedHandleDescription;
    }

    public String getTicketEstimatedHandleBy() {
        return ticketEstimatedHandleBy;
    }

    public void setTicketEstimatedHandleBy(String ticketEstimatedHandleBy) {
        this.ticketEstimatedHandleBy = ticketEstimatedHandleBy;
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

    public String getTicketResolveBy() {
        return ticketResolveBy;
    }

    public void setTicketResolveBy(String ticketResolveBy) {
        this.ticketResolveBy = ticketResolveBy;
    }

    public String getTicketResolveDate() {
        return ticketResolveDate;
    }

    public void setTicketResolveDate(String ticketResolveDate) {
        this.ticketResolveDate = ticketResolveDate;
    }

    public String getTicketResolveDescription() {
        return ticketResolveDescription;
    }

    public void setTicketResolveDescription(String ticketResolveDescription) {
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

    public String getTicketSiteVisitAt() {
        return ticketSiteVisitAt;
    }

    public void setTicketSiteVisitAt(String ticketSiteVisitAt) {
        this.ticketSiteVisitAt = ticketSiteVisitAt;
    }

    public String getTicketResolveFile() {
        return ticketResolveFile;
    }

    public void setTicketResolveFile(String ticketResolveFile) {
        this.ticketResolveFile = ticketResolveFile;
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

    public String getTicketWithdrawBy() {
        return ticketWithdrawBy;
    }

    public void setTicketWithdrawBy(String ticketWithdrawBy) {
        this.ticketWithdrawBy = ticketWithdrawBy;
    }

    public String getTicketWithdrawDescription() {
        return ticketWithdrawDescription;
    }

    public void setTicketWithdrawDescription(String ticketWithdrawDescription) {
        this.ticketWithdrawDescription = ticketWithdrawDescription;
    }

    public String getTicketWithdrawDate() {
        return ticketWithdrawDate;
    }

    public void setTicketWithdrawDate(String ticketWithdrawDate) {
        this.ticketWithdrawDate = ticketWithdrawDate;
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

    public String getTicketViewedAt() {
        return ticketViewedAt;
    }

    public void setTicketViewedAt(String ticketViewedAt) {
        this.ticketViewedAt = ticketViewedAt;
    }

    public String getTicketReplyBy() {
        return ticketReplyBy;
    }

    public void setTicketReplyBy(String ticketReplyBy) {
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

    public String getTicketServiceTypeIcon() {
        return ticketServiceTypeIcon;
    }

    public void setTicketServiceTypeIcon(String ticketServiceTypeIcon) {
        this.ticketServiceTypeIcon = ticketServiceTypeIcon;
    }

    public String getTicketServiceTypeIconBig() {
        return ticketServiceTypeIconBig;
    }

    public void setTicketServiceTypeIconBig(String ticketServiceTypeIconBig) {
        this.ticketServiceTypeIconBig = ticketServiceTypeIconBig;
    }

    public String getTicketServiceTypeName() {
        return ticketServiceTypeName;
    }

    public void setTicketServiceTypeName(String ticketServiceTypeName) {
        this.ticketServiceTypeName = ticketServiceTypeName;
    }

    public String getTicketAssetType() {
        return ticketAssetType;
    }

    public void setTicketAssetType(String ticketAssetType) {
        this.ticketAssetType = ticketAssetType;
    }

    public String getTicketAssetAcId() {
        return ticketAssetAcId;
    }

    public void setTicketAssetAcId(String ticketAssetAcId) {
        this.ticketAssetAcId = ticketAssetAcId;
    }

    public String getTicketAssetAcName() {
        return ticketAssetAcName;
    }

    public void setTicketAssetAcName(String ticketAssetAcName) {
        this.ticketAssetAcName = ticketAssetAcName;
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

    public String getAssignTo() {
        return assignTo;
    }

    public void setAssignTo(String assignTo) {
        this.assignTo = assignTo;
    }

    public String getAssignBy() {
        return assignBy;
    }

    public void setAssignBy(String assignBy) {
        this.assignBy = assignBy;
    }

    public String getHandleBy() {
        return handleBy;
    }

    public void setHandleBy(String handleBy) {
        this.handleBy = handleBy;
    }

    public String getResolveBy() {
        return resolveBy;
    }

    public void setResolveBy(String resolveBy) {
        this.resolveBy = resolveBy;
    }

    public String getCloseBy() {
        return closeBy;
    }

    public void setCloseBy(String closeBy) {
        this.closeBy = closeBy;
    }

    public String getWithdrawBy() {
        return withdrawBy;
    }

    public void setWithdrawBy(String withdrawBy) {
        this.withdrawBy = withdrawBy;
    }

    public String getReplyBy() {
        return replyBy;
    }

    public void setReplyBy(String replyBy) {
        this.replyBy = replyBy;
    }

    public String getTicketIssueIdsStr() {
        return ticketIssueIdsStr;
    }

    public void setTicketIssueIdsStr(String ticketIssueIdsStr) {
        this.ticketIssueIdsStr = ticketIssueIdsStr;
    }

    public String getTicketServiceTypeCurrentStatus() {
        return ticketServiceTypeCurrentStatus;
    }

    public void setTicketServiceTypeCurrentStatus(String ticketServiceTypeCurrentStatus) {
        this.ticketServiceTypeCurrentStatus = ticketServiceTypeCurrentStatus;
    }

    public String getTicketSiteVisitDescription() {
        return ticketSiteVisitDescription;
    }

    public void setTicketSiteVisitDescription(String ticketSiteVisitDescription) {
        this.ticketSiteVisitDescription = ticketSiteVisitDescription;
    }

    public String getTicketSiteVisitDate() {
        return ticketSiteVisitDate;
    }

    public void setTicketSiteVisitDate(String ticketSiteVisitDate) {
        this.ticketSiteVisitDate = ticketSiteVisitDate;
    }

    public String getBehalfRaiseBy() {
        return behalfRaiseBy;
    }

    public void setBehalfRaiseBy(String behalfRaiseBy) {
        this.behalfRaiseBy = behalfRaiseBy;
    }

    public String getLocationIsBaseLocation() {
        return locationIsBaseLocation;
    }

    public void setLocationIsBaseLocation(String locationIsBaseLocation) {
        this.locationIsBaseLocation = locationIsBaseLocation;
    }

    public String getAssetIsByod() {
        return assetIsByod;
    }

    public void setAssetIsByod(String assetIsByod) {
        this.assetIsByod = assetIsByod;
    }

    public String getTicketModuleId() {
        return ticketModuleId;
    }

    public void setTicketModuleId(String ticketModuleId) {
        this.ticketModuleId = ticketModuleId;
    }

    public String getTicketSubModuleId() {
        return ticketSubModuleId;
    }

    public void setTicketSubModuleId(String ticketSubModuleId) {
        this.ticketSubModuleId = ticketSubModuleId;
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
}
