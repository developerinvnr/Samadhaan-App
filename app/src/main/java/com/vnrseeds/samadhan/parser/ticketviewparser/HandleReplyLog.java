
package com.vnrseeds.samadhan.parser.ticketviewparser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HandleReplyLog {

    @SerializedName("ticket_status")
    @Expose
    private String ticketStatus;
    @SerializedName("handle_created_by_id")
    @Expose
    private String handleCreatedById;
    @SerializedName("handle_created_by")
    @Expose
    private String handleCreatedBy;
    @SerializedName("handle_description")
    @Expose
    private String handleDescription;
    @SerializedName("handle_is_work_in_progress")
    @Expose
    private String handleIsWorkInProgress;
    @SerializedName("handle_request_type")
    @Expose
    private String handleRequestType;
    @SerializedName("handle_change_request_status")
    @Expose
    private String handleChangeRequestStatus;
    @SerializedName("handle_is_discussion_required")
    @Expose
    private String handleIsDiscussionRequired;
    @SerializedName("handle_date")
    @Expose
    private String handleDate;
    @SerializedName("estimated_created_by_id")
    @Expose
    private String estimatedCreatedById;
    @SerializedName("estimated_created_by")
    @Expose
    private String estimatedCreatedBy;
    @SerializedName("estimated_date")
    @Expose
    private String estimatedDate;
    @SerializedName("estimated_description")
    @Expose
    private String estimatedDescription;
    @SerializedName("estimated_created_date")
    @Expose
    private String estimatedCreatedDate;
    @SerializedName("resolve_created_by_id")
    @Expose
    private String resolveCreatedById;
    @SerializedName("resolve_created_by")
    @Expose
    private String resolveCreatedBy;
    @SerializedName("resolve_description")
    @Expose
    private String resolveDescription;
    @SerializedName("resolve_file")
    @Expose
    private String resolveFile;
    @SerializedName("resolve_date")
    @Expose
    private String resolveDate;
    @SerializedName("reopen_created_by_id")
    @Expose
    private String reopenCreatedById;
    @SerializedName("reopen_created_by")
    @Expose
    private String reopenCreatedBy;
    @SerializedName("reopen_description")
    @Expose
    private String reopenDescription;
    @SerializedName("reopen_date")
    @Expose
    private String reopenDate;
    @SerializedName("reply_created_by_id")
    @Expose
    private Integer replyCreatedById;
    @SerializedName("reply_created_by")
    @Expose
    private String replyCreatedBy;
    @SerializedName("reply_description")
    @Expose
    private String replyDescription;
    @SerializedName("reply_files")
    @Expose
    private List<String> replyFiles;
    @SerializedName("reply_date")
    @Expose
    private String replyDate;
    @SerializedName("withdraw_created_by_id")
    @Expose
    private String withdrawCreatedById;
    @SerializedName("withdraw_created_by")
    @Expose
    private String withdrawCreatedBy;
    @SerializedName("withdraw_description")
    @Expose
    private String withdrawDescription;
    @SerializedName("withdraw_date")
    @Expose
    private String withdrawDate;
    @SerializedName("close_created_by_id")
    @Expose
    private String closeCreatedById;
    @SerializedName("close_created_by")
    @Expose
    private String closeCreatedBy;
    @SerializedName("close_description")
    @Expose
    private String closeDescription;
    @SerializedName("close_rating")
    @Expose
    private String closeRating;
    @SerializedName("close_date")
    @Expose
    private String closeDate;

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public String getHandleCreatedById() {
        return handleCreatedById;
    }

    public void setHandleCreatedById(String handleCreatedById) {
        this.handleCreatedById = handleCreatedById;
    }

    public String getHandleCreatedBy() {
        return handleCreatedBy;
    }

    public void setHandleCreatedBy(String handleCreatedBy) {
        this.handleCreatedBy = handleCreatedBy;
    }

    public String getHandleDescription() {
        return handleDescription;
    }

    public void setHandleDescription(String handleDescription) {
        this.handleDescription = handleDescription;
    }

    public String getHandleRequestType() {
        return handleRequestType;
    }

    public void setHandleRequestType(String handleRequestType) {
        this.handleRequestType = handleRequestType;
    }

    public String getHandleChangeRequestStatus() {
        return handleChangeRequestStatus;
    }

    public void setHandleChangeRequestStatus(String handleChangeRequestStatus) {
        this.handleChangeRequestStatus = handleChangeRequestStatus;
    }

    public String getHandleIsWorkInProgress() {
        return handleIsWorkInProgress;
    }

    public void setHandleIsWorkInProgress(String handleIsWorkInProgress) {
        this.handleIsWorkInProgress = handleIsWorkInProgress;
    }

    public String getHandleIsDiscussionRequired() {
        return handleIsDiscussionRequired;
    }

    public void setHandleIsDiscussionRequired(String handleIsDiscussionRequired) {
        this.handleIsDiscussionRequired = handleIsDiscussionRequired;
    }

    public String getHandleDate() {
        return handleDate;
    }

    public void setHandleDate(String handleDate) {
        this.handleDate = handleDate;
    }

    public String getEstimatedCreatedById() {
        return estimatedCreatedById;
    }

    public void setEstimatedCreatedById(String estimatedCreatedById) {
        this.estimatedCreatedById = estimatedCreatedById;
    }

    public String getEstimatedCreatedBy() {
        return estimatedCreatedBy;
    }

    public void setEstimatedCreatedBy(String estimatedCreatedBy) {
        this.estimatedCreatedBy = estimatedCreatedBy;
    }

    public String getEstimatedDate() {
        return estimatedDate;
    }

    public void setEstimatedDate(String estimatedDate) {
        this.estimatedDate = estimatedDate;
    }

    public String getEstimatedDescription() {
        return estimatedDescription;
    }

    public void setEstimatedDescription(String estimatedDescription) {
        this.estimatedDescription = estimatedDescription;
    }

    public String getEstimatedCreatedDate() {
        return estimatedCreatedDate;
    }

    public void setEstimatedCreatedDate(String estimatedCreatedDate) {
        this.estimatedCreatedDate = estimatedCreatedDate;
    }

    public String getResolveCreatedById() {
        return resolveCreatedById;
    }

    public void setResolveCreatedById(String resolveCreatedById) {
        this.resolveCreatedById = resolveCreatedById;
    }

    public String getResolveCreatedBy() {
        return resolveCreatedBy;
    }

    public void setResolveCreatedBy(String resolveCreatedBy) {
        this.resolveCreatedBy = resolveCreatedBy;
    }

    public String getResolveDescription() {
        return resolveDescription;
    }

    public void setResolveDescription(String resolveDescription) {
        this.resolveDescription = resolveDescription;
    }

    public String getResolveFile() {
        return resolveFile;
    }

    public void setResolveFile(String resolveFile) {
        this.resolveFile = resolveFile;
    }

    public String getResolveDate() {
        return resolveDate;
    }

    public void setResolveDate(String resolveDate) {
        this.resolveDate = resolveDate;
    }

    public String getReopenCreatedById() {
        return reopenCreatedById;
    }

    public void setReopenCreatedById(String reopenCreatedById) {
        this.reopenCreatedById = reopenCreatedById;
    }

    public String getReopenCreatedBy() {
        return reopenCreatedBy;
    }

    public void setReopenCreatedBy(String reopenCreatedBy) {
        this.reopenCreatedBy = reopenCreatedBy;
    }

    public String getReopenDescription() {
        return reopenDescription;
    }

    public void setReopenDescription(String reopenDescription) {
        this.reopenDescription = reopenDescription;
    }

    public String getReopenDate() {
        return reopenDate;
    }

    public void setReopenDate(String reopenDate) {
        this.reopenDate = reopenDate;
    }

    public Integer getReplyCreatedById() {
        return replyCreatedById;
    }

    public void setReplyCreatedById(Integer replyCreatedById) {
        this.replyCreatedById = replyCreatedById;
    }

    public String getReplyCreatedBy() {
        return replyCreatedBy;
    }

    public void setReplyCreatedBy(String replyCreatedBy) {
        this.replyCreatedBy = replyCreatedBy;
    }

    public String getReplyDescription() {
        return replyDescription;
    }

    public void setReplyDescription(String replyDescription) {
        this.replyDescription = replyDescription;
    }

    public List<String> getReplyFiles() {
        return replyFiles;
    }

    public void setReplyFiles(List<String> replyFiles) {
        this.replyFiles = replyFiles;
    }

    public String getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(String replyDate) {
        this.replyDate = replyDate;
    }

    public String getWithdrawCreatedById() {
        return withdrawCreatedById;
    }

    public void setWithdrawCreatedById(String withdrawCreatedById) {
        this.withdrawCreatedById = withdrawCreatedById;
    }

    public String getWithdrawCreatedBy() {
        return withdrawCreatedBy;
    }

    public void setWithdrawCreatedBy(String withdrawCreatedBy) {
        this.withdrawCreatedBy = withdrawCreatedBy;
    }

    public String getWithdrawDescription() {
        return withdrawDescription;
    }

    public void setWithdrawDescription(String withdrawDescription) {
        this.withdrawDescription = withdrawDescription;
    }

    public String getWithdrawDate() {
        return withdrawDate;
    }

    public void setWithdrawDate(String withdrawDate) {
        this.withdrawDate = withdrawDate;
    }

    public String getCloseCreatedById() {
        return closeCreatedById;
    }

    public void setCloseCreatedById(String closeCreatedById) {
        this.closeCreatedById = closeCreatedById;
    }

    public String getCloseCreatedBy() {
        return closeCreatedBy;
    }

    public void setCloseCreatedBy(String closeCreatedBy) {
        this.closeCreatedBy = closeCreatedBy;
    }

    public String getCloseDescription() {
        return closeDescription;
    }

    public void setCloseDescription(String closeDescription) {
        this.closeDescription = closeDescription;
    }

    public String getCloseRating() {
        return closeRating;
    }

    public void setCloseRating(String closeRating) {
        this.closeRating = closeRating;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }
}
