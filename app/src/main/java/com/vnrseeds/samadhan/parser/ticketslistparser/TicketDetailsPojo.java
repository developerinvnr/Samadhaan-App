package com.vnrseeds.samadhan.parser.ticketslistparser;

import java.util.List;

public class TicketDetailsPojo {
    private int srno;
    private String ticketNo;
    private String custodianName;
    private String priority;
    private String issueType;
    private String issueTitle;
    private String departmentName;
    private String locationName;
    private String sectionName;
    private String issueDesc;
    private String ticketStatus;
    private String ticketDate;
    private String assignTo;
    private String assetCategory;
    private String serviceType;
    private List<String> issueImage;
    private String ticketId;
    private String ticketIsViewed;
    private String ticket_priority_id;
    private String ticket_user_id;
    private String ticket_issue_ids;
    private String ticket_raised_by_id;
    private String ticket_user_type;
    private String ticket_asset_type;
    private String ticketAssetCatagoryID;
    private String ticket_service_type_id;
    private String ticket_is_work_in_progress;
    private String ticketResolveDescription;
    private String ticketResolveDate;
    private String ticketEstimatedHandleDate;
    private String issueName;
    private String ticketIssueIdsStr;
    private String ticketAssetAcName;
    private String ticketEstimatedHandleDescription;
    private String ticketIssueOther;
    private String ticketCloseBy;
    private String ticketCloseDate;
    private String ticketCloseRating;
    private String ticketCloseDescription;
    private String ticketServiceTypeCurrentStatus;
    private String raiseBy;
    private String ticketSiteVisitDescription;
    private String ticketSiteVisitDate;
    private String ticketReopenNumber;
    private String locationIsBaseLocation;
    private String ticketServiceTypeIcon;
    private String assetIsByod;
    private String ticketModuleId;
    private String ticketSubModuleId;
    private String ticketIsAddToAsset;
    private String ticketIsSiteVisit;
    private String ticketSiteVisitAt;
    private String ticketResolveFile;
    private String moduleName;
    private String subModuleName;

    public TicketDetailsPojo() {
    }

    public TicketDetailsPojo(int srno, String ticketNo, String custodianName, String priority, String issueType, String issueTitle, String departmentName, String locationName, String sectionName, String issueDesc, String ticketStatus, String ticketDate, String assignTo, String assetCategory, String serviceType, List<String> issueImage, String ticketId, String ticketIsViewed, String ticket_priority_id, String ticket_user_id, String ticket_issue_ids, String ticket_raised_by_id, String ticket_user_type, String ticket_asset_type, String ticketAssetCatagoryID, String ticket_service_type_id, String ticket_is_work_in_progress, String ticketResolveDescription, String ticketResolveDate, String ticketEstimatedHandleDate, String issueName, String ticketAssetAcName, String ticketEstimatedHandleDescription, String ticketIssueOther, String ticketCloseBy, String ticketCloseDate, String ticketCloseRating, String ticketCloseDescription, String ticketServiceTypeCurrentStatus, String raiseBy, String ticketSiteVisitDescription, String ticketSiteVisitDate, String ticketReopenNumber, String locationIsBaseLocation, String ticketServiceTypeIcon, String assetIsByod, String ticketModuleId, String ticketSubModuleId, String ticketIsAddToAsset, String ticketIsSiteVisit, String ticketSiteVisitAt, String ticketResolveFile, String moduleName, String subModuleName) {
        this.srno = srno;
        this.ticketNo = ticketNo;
        this.custodianName = custodianName;
        this.priority = priority;
        this.issueType = issueType;
        this.issueTitle = issueTitle;
        this.departmentName = departmentName;
        this.locationName = locationName;
        this.sectionName = sectionName;
        this.issueDesc = issueDesc;
        this.ticketStatus = ticketStatus;
        this.ticketDate = ticketDate;
        this.assignTo = assignTo;
        this.assetCategory = assetCategory;
        this.serviceType = serviceType;
        this.issueImage = issueImage;
        this.ticketId = ticketId;
        this.ticketIsViewed = ticketIsViewed;
        this.ticket_priority_id = ticket_priority_id;
        this.ticket_user_id = ticket_user_id;
        this.ticket_issue_ids = ticket_issue_ids;
        this.ticket_raised_by_id = ticket_raised_by_id;
        this.ticket_user_type = ticket_user_type;
        this.ticket_asset_type = ticket_asset_type;
        this.ticketAssetCatagoryID = ticketAssetCatagoryID;
        this.ticket_service_type_id = ticket_service_type_id;
        this.ticket_is_work_in_progress = ticket_is_work_in_progress;
        this.ticketResolveDescription = ticketResolveDescription;
        this.ticketResolveDate = ticketResolveDate;
        this.ticketEstimatedHandleDate = ticketEstimatedHandleDate;
        this.issueName = issueName;
        this.ticketAssetAcName = ticketAssetAcName;
        this.ticketEstimatedHandleDescription = ticketEstimatedHandleDescription;
        this.ticketIssueOther = ticketIssueOther;
        this.ticketCloseBy = ticketCloseBy;
        this.ticketCloseDate = ticketCloseDate;
        this.ticketCloseRating = ticketCloseRating;
        this.ticketCloseDescription = ticketCloseDescription;
        this.ticketServiceTypeCurrentStatus = ticketServiceTypeCurrentStatus;
        this.raiseBy = raiseBy;
        this.ticketSiteVisitDescription = ticketSiteVisitDescription;
        this.ticketSiteVisitDate = ticketSiteVisitDate;
        this.ticketReopenNumber = ticketReopenNumber;
        this.locationIsBaseLocation = locationIsBaseLocation;
        this.ticketServiceTypeIcon = ticketServiceTypeIcon;
        this.assetIsByod = assetIsByod;
        this.ticketModuleId = ticketModuleId;
        this.ticketSubModuleId = ticketSubModuleId;
        this.ticketIsAddToAsset = ticketIsAddToAsset;
        this.ticketIsSiteVisit = ticketIsSiteVisit;
        this.ticketSiteVisitAt = ticketSiteVisitAt;
        this.ticketResolveFile = ticketResolveFile;
        this.moduleName = moduleName;
        this.subModuleName = subModuleName;
    }

    public int getSrno() {
        return srno;
    }

    public void setSrno(int srno) {
        this.srno = srno;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getCustodianName() {
        return custodianName;
    }

    public void setCustodianName(String custodianName) {
        this.custodianName = custodianName;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public String getIssueTitle() {
        return issueTitle;
    }

    public void setIssueTitle(String issueTitle) {
        this.issueTitle = issueTitle;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getIssueDesc() {
        return issueDesc;
    }

    public void setIssueDesc(String issueDesc) {
        this.issueDesc = issueDesc;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public String getTicketDate() {
        return ticketDate;
    }

    public void setTicketDate(String ticketDate) {
        this.ticketDate = ticketDate;
    }

    public String getAssignTo() {
        return assignTo;
    }

    public void setAssignTo(String assignTo) {
        this.assignTo = assignTo;
    }

    public String getAssetCategory() {
        return assetCategory;
    }

    public void setAssetCategory(String assetCategory) {
        this.assetCategory = assetCategory;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public List<String> getIssueImage() {
        return issueImage;
    }

    public void setIssueImage(List<String> issueImage) {
        this.issueImage = issueImage;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketIsViewed() {
        return ticketIsViewed;
    }

    public void setTicketIsViewed(String ticketIsViewed) {
        this.ticketIsViewed = ticketIsViewed;
    }

    public String getTicket_priority_id() {
        return ticket_priority_id;
    }

    public void setTicket_priority_id(String ticket_priority_id) {
        this.ticket_priority_id = ticket_priority_id;
    }

    public String getTicket_user_id() {
        return ticket_user_id;
    }

    public void setTicket_user_id(String ticket_user_id) {
        this.ticket_user_id = ticket_user_id;
    }

    public String getTicket_issue_ids() {
        return ticket_issue_ids;
    }

    public void setTicket_issue_ids(String ticket_issue_ids) {
        this.ticket_issue_ids = ticket_issue_ids;
    }

    public String getTicket_raised_by_id() {
        return ticket_raised_by_id;
    }

    public void setTicket_raised_by_id(String ticket_raised_by_id) {
        this.ticket_raised_by_id = ticket_raised_by_id;
    }

    public String getTicket_user_type() {
        return ticket_user_type;
    }

    public void setTicket_user_type(String ticket_user_type) {
        this.ticket_user_type = ticket_user_type;
    }

    public String getTicket_asset_type() {
        return ticket_asset_type;
    }

    public void setTicket_asset_type(String ticket_asset_type) {
        this.ticket_asset_type = ticket_asset_type;
    }

    public String getTicketAssetCatagoryID() {
        return ticketAssetCatagoryID;
    }

    public void setTicketAssetCatagoryID(String ticketAssetCatagoryID) {
        this.ticketAssetCatagoryID = ticketAssetCatagoryID;
    }

    public String getTicket_service_type_id() {
        return ticket_service_type_id;
    }

    public void setTicket_service_type_id(String ticket_service_type_id) {
        this.ticket_service_type_id = ticket_service_type_id;
    }

    public String getTicket_is_work_in_progress() {
        return ticket_is_work_in_progress;
    }

    public void setTicket_is_work_in_progress(String ticket_is_work_in_progress) {
        this.ticket_is_work_in_progress = ticket_is_work_in_progress;
    }

    public String getTicketResolveDescription() {
        return ticketResolveDescription;
    }

    public void setTicketResolveDescription(String ticketResolveDescription) {
        this.ticketResolveDescription = ticketResolveDescription;
    }

    public String getTicketResolveDate() {
        return ticketResolveDate;
    }

    public void setTicketResolveDate(String ticketResolveDate) {
        this.ticketResolveDate = ticketResolveDate;
    }

    public String getTicketEstimatedHandleDate() {
        return ticketEstimatedHandleDate;
    }

    public void setTicketEstimatedHandleDate(String ticketEstimatedHandleDate) {
        this.ticketEstimatedHandleDate = ticketEstimatedHandleDate;
    }

    public String getIssueName() {
        return issueName;
    }

    public void setIssueName(String issueName) {
        this.issueName = issueName;
    }

    public String getTicketIssueIdsStr() {
        return ticketIssueIdsStr;
    }

    public void setTicketIssueIdsStr(String ticketIssueIdsStr) {
        this.ticketIssueIdsStr = ticketIssueIdsStr;
    }

    public String getTicketAssetAcName() {
        return ticketAssetAcName;
    }

    public void setTicketAssetAcName(String ticketAssetAcName) {
        this.ticketAssetAcName = ticketAssetAcName;
    }

    public String getTicketEstimatedHandleDescription() {
        return ticketEstimatedHandleDescription;
    }

    public void setTicketEstimatedHandleDescription(String ticketEstimatedHandleDescription) {
        this.ticketEstimatedHandleDescription = ticketEstimatedHandleDescription;
    }

    public String getTicketIssueOther() {
        return ticketIssueOther;
    }

    public void setTicketIssueOther(String ticketIssueOther) {
        this.ticketIssueOther = ticketIssueOther;
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

    public String getTicketServiceTypeCurrentStatus() {
        return ticketServiceTypeCurrentStatus;
    }

    public void setTicketServiceTypeCurrentStatus(String ticketServiceTypeCurrentStatus) {
        this.ticketServiceTypeCurrentStatus = ticketServiceTypeCurrentStatus;
    }

    public String getRaiseBy() {
        return raiseBy;
    }

    public void setRaiseBy(String raiseBy) {
        this.raiseBy = raiseBy;
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

    public String getTicketReopenNumber() {
        return ticketReopenNumber;
    }

    public void setTicketReopenNumber(String ticketReopenNumber) {
        this.ticketReopenNumber = ticketReopenNumber;
    }

    public String getLocationIsBaseLocation() {
        return locationIsBaseLocation;
    }

    public void setLocationIsBaseLocation(String locationIsBaseLocation) {
        this.locationIsBaseLocation = locationIsBaseLocation;
    }

    public String getTicketServiceTypeIcon() {
        return ticketServiceTypeIcon;
    }

    public void setTicketServiceTypeIcon(String ticketServiceTypeIcon) {
        this.ticketServiceTypeIcon = ticketServiceTypeIcon;
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
