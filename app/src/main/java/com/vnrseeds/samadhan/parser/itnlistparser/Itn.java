package com.vnrseeds.samadhan.parser.itnlistparser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Itn {
    @SerializedName("itn_id")
    @Expose
    private Integer itnId;
    @SerializedName("itn_code")
    @Expose
    private String itnCode;
    @SerializedName("itn_ticket_id")
    @Expose
    private Integer itnTicketId;
    @SerializedName("itn_ticket_current_status")
    @Expose
    private String itnTicketCurrentStatus;
    @SerializedName("itn_for")
    @Expose
    private String itnFor;
    @SerializedName("itn_transfer_to")
    @Expose
    private String itnTransferTo;
    @SerializedName("itn_send_to")
    @Expose
    private Integer itnSendTo;
    @SerializedName("itn_send_location_id")
    @Expose
    private Object itnSendLocationId;
    @SerializedName("itn_to_workshop_id")
    @Expose
    private Object itnToWorkshopId;
    @SerializedName("itn_from_workshop_id")
    @Expose
    private Object itnFromWorkshopId;
    @SerializedName("itn_mode")
    @Expose
    private String itnMode;
    @SerializedName("itn_person_name")
    @Expose
    private String itnPersonName;
    @SerializedName("itn_mobile")
    @Expose
    private String itnMobile;
    @SerializedName("itn_courier_company")
    @Expose
    private Object itnCourierCompany;
    @SerializedName("itn_courier_docket_no")
    @Expose
    private Object itnCourierDocketNo;
    @SerializedName("itn_transporter_name")
    @Expose
    private Object itnTransporterName;
    @SerializedName("itn_vehicle_no")
    @Expose
    private Object itnVehicleNo;
    @SerializedName("itn_driver_name")
    @Expose
    private Object itnDriverName;
    @SerializedName("itn_particular")
    @Expose
    private String itnParticular;
    @SerializedName("itn_quantity")
    @Expose
    private Integer itnQuantity;
    @SerializedName("itn_remarks")
    @Expose
    private String itnRemarks;
    @SerializedName("itn_file")
    @Expose
    private String itnFile;
    @SerializedName("itn_create_entry")
    @Expose
    private String itnCreateEntry;
    @SerializedName("itn_update_entry")
    @Expose
    private Object itnUpdateEntry;
    @SerializedName("itn_created_by")
    @Expose
    private Integer itnCreatedBy;
    @SerializedName("itn_created_at")
    @Expose
    private String itnCreatedAt;
    @SerializedName("itn_updated_by")
    @Expose
    private Object itnUpdatedBy;
    @SerializedName("itn_updated_at")
    @Expose
    private Object itnUpdatedAt;
    @SerializedName("itn_received_by")
    @Expose
    private Object itnReceivedBy;
    @SerializedName("itn_received_at")
    @Expose
    private Object itnReceivedAt;
    @SerializedName("itn_received_remarks")
    @Expose
    private Object itnReceivedRemarks;
    @SerializedName("itn_received_file")
    @Expose
    private Object itnReceivedFile;
    @SerializedName("itn_cancel_by")
    @Expose
    private Object itnCancelBy;
    @SerializedName("itn_cancel_at")
    @Expose
    private Object itnCancelAt;
    @SerializedName("itn_cancel_remarks")
    @Expose
    private Object itnCancelRemarks;
    @SerializedName("itn_status")
    @Expose
    private String itnStatus;
    @SerializedName("ticket_raise_by")
    @Expose
    private Integer ticketRaiseBy;
    @SerializedName("ticket_code")
    @Expose
    private String ticketCode;
    @SerializedName("location_name")
    @Expose
    private String locationName;
    @SerializedName("itn_send_location_name")
    @Expose
    private Object itnSendLocationName;
    @SerializedName("itn_to_workshop_name")
    @Expose
    private Object itnToWorkshopName;
    @SerializedName("itn_from_workshop_name")
    @Expose
    private Object itnFromWorkshopName;
    @SerializedName("ticket_raise_by_name")
    @Expose
    private String ticketRaiseByName;
    @SerializedName("itn_send_to_name")
    @Expose
    private String itnSendToName;
    @SerializedName("itn_created_by_name")
    @Expose
    private String itnCreatedByName;
    @SerializedName("itn_received_by_name")
    @Expose
    private String itnReceivedByName;
    @SerializedName("itn_cancel_by_name")
    @Expose
    private String itnCancelByName;

    public Integer getItnId() {
        return itnId;
    }

    public void setItnId(Integer itnId) {
        this.itnId = itnId;
    }

    public String getItnCode() {
        return itnCode;
    }

    public void setItnCode(String itnCode) {
        this.itnCode = itnCode;
    }

    public Integer getItnTicketId() {
        return itnTicketId;
    }

    public void setItnTicketId(Integer itnTicketId) {
        this.itnTicketId = itnTicketId;
    }

    public String getItnTicketCurrentStatus() {
        return itnTicketCurrentStatus;
    }

    public void setItnTicketCurrentStatus(String itnTicketCurrentStatus) {
        this.itnTicketCurrentStatus = itnTicketCurrentStatus;
    }

    public String getItnFor() {
        return itnFor;
    }

    public void setItnFor(String itnFor) {
        this.itnFor = itnFor;
    }

    public String getItnTransferTo() {
        return itnTransferTo;
    }

    public void setItnTransferTo(String itnTransferTo) {
        this.itnTransferTo = itnTransferTo;
    }

    public Integer getItnSendTo() {
        return itnSendTo;
    }

    public void setItnSendTo(Integer itnSendTo) {
        this.itnSendTo = itnSendTo;
    }

    public Object getItnSendLocationId() {
        return itnSendLocationId;
    }

    public void setItnSendLocationId(Object itnSendLocationId) {
        this.itnSendLocationId = itnSendLocationId;
    }

    public Object getItnToWorkshopId() {
        return itnToWorkshopId;
    }

    public void setItnToWorkshopId(Object itnToWorkshopId) {
        this.itnToWorkshopId = itnToWorkshopId;
    }

    public Object getItnFromWorkshopId() {
        return itnFromWorkshopId;
    }

    public void setItnFromWorkshopId(Object itnFromWorkshopId) {
        this.itnFromWorkshopId = itnFromWorkshopId;
    }

    public String getItnMode() {
        return itnMode;
    }

    public void setItnMode(String itnMode) {
        this.itnMode = itnMode;
    }

    public String getItnPersonName() {
        return itnPersonName;
    }

    public void setItnPersonName(String itnPersonName) {
        this.itnPersonName = itnPersonName;
    }

    public String getItnMobile() {
        return itnMobile;
    }

    public void setItnMobile(String itnMobile) {
        this.itnMobile = itnMobile;
    }

    public Object getItnCourierCompany() {
        return itnCourierCompany;
    }

    public void setItnCourierCompany(Object itnCourierCompany) {
        this.itnCourierCompany = itnCourierCompany;
    }

    public Object getItnCourierDocketNo() {
        return itnCourierDocketNo;
    }

    public void setItnCourierDocketNo(Object itnCourierDocketNo) {
        this.itnCourierDocketNo = itnCourierDocketNo;
    }

    public Object getItnTransporterName() {
        return itnTransporterName;
    }

    public void setItnTransporterName(Object itnTransporterName) {
        this.itnTransporterName = itnTransporterName;
    }

    public Object getItnVehicleNo() {
        return itnVehicleNo;
    }

    public void setItnVehicleNo(Object itnVehicleNo) {
        this.itnVehicleNo = itnVehicleNo;
    }

    public Object getItnDriverName() {
        return itnDriverName;
    }

    public void setItnDriverName(Object itnDriverName) {
        this.itnDriverName = itnDriverName;
    }

    public String getItnParticular() {
        return itnParticular;
    }

    public void setItnParticular(String itnParticular) {
        this.itnParticular = itnParticular;
    }

    public Integer getItnQuantity() {
        return itnQuantity;
    }

    public void setItnQuantity(Integer itnQuantity) {
        this.itnQuantity = itnQuantity;
    }

    public String getItnRemarks() {
        return itnRemarks;
    }

    public void setItnRemarks(String itnRemarks) {
        this.itnRemarks = itnRemarks;
    }

    public String getItnFile() {
        return itnFile;
    }

    public void setItnFile(String itnFile) {
        this.itnFile = itnFile;
    }

    public String getItnCreateEntry() {
        return itnCreateEntry;
    }

    public void setItnCreateEntry(String itnCreateEntry) {
        this.itnCreateEntry = itnCreateEntry;
    }

    public Object getItnUpdateEntry() {
        return itnUpdateEntry;
    }

    public void setItnUpdateEntry(Object itnUpdateEntry) {
        this.itnUpdateEntry = itnUpdateEntry;
    }

    public Integer getItnCreatedBy() {
        return itnCreatedBy;
    }

    public void setItnCreatedBy(Integer itnCreatedBy) {
        this.itnCreatedBy = itnCreatedBy;
    }

    public String getItnCreatedAt() {
        return itnCreatedAt;
    }

    public void setItnCreatedAt(String itnCreatedAt) {
        this.itnCreatedAt = itnCreatedAt;
    }

    public Object getItnUpdatedBy() {
        return itnUpdatedBy;
    }

    public void setItnUpdatedBy(Object itnUpdatedBy) {
        this.itnUpdatedBy = itnUpdatedBy;
    }

    public Object getItnUpdatedAt() {
        return itnUpdatedAt;
    }

    public void setItnUpdatedAt(Object itnUpdatedAt) {
        this.itnUpdatedAt = itnUpdatedAt;
    }

    public Object getItnReceivedBy() {
        return itnReceivedBy;
    }

    public void setItnReceivedBy(Object itnReceivedBy) {
        this.itnReceivedBy = itnReceivedBy;
    }

    public Object getItnReceivedAt() {
        return itnReceivedAt;
    }

    public void setItnReceivedAt(Object itnReceivedAt) {
        this.itnReceivedAt = itnReceivedAt;
    }

    public Object getItnReceivedRemarks() {
        return itnReceivedRemarks;
    }

    public void setItnReceivedRemarks(Object itnReceivedRemarks) {
        this.itnReceivedRemarks = itnReceivedRemarks;
    }

    public Object getItnReceivedFile() {
        return itnReceivedFile;
    }

    public void setItnReceivedFile(Object itnReceivedFile) {
        this.itnReceivedFile = itnReceivedFile;
    }

    public Object getItnCancelBy() {
        return itnCancelBy;
    }

    public void setItnCancelBy(Object itnCancelBy) {
        this.itnCancelBy = itnCancelBy;
    }

    public Object getItnCancelAt() {
        return itnCancelAt;
    }

    public void setItnCancelAt(Object itnCancelAt) {
        this.itnCancelAt = itnCancelAt;
    }

    public Object getItnCancelRemarks() {
        return itnCancelRemarks;
    }

    public void setItnCancelRemarks(Object itnCancelRemarks) {
        this.itnCancelRemarks = itnCancelRemarks;
    }

    public String getItnStatus() {
        return itnStatus;
    }

    public void setItnStatus(String itnStatus) {
        this.itnStatus = itnStatus;
    }

    public Integer getTicketRaiseBy() {
        return ticketRaiseBy;
    }

    public void setTicketRaiseBy(Integer ticketRaiseBy) {
        this.ticketRaiseBy = ticketRaiseBy;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Object getItnSendLocationName() {
        return itnSendLocationName;
    }

    public void setItnSendLocationName(Object itnSendLocationName) {
        this.itnSendLocationName = itnSendLocationName;
    }

    public Object getItnToWorkshopName() {
        return itnToWorkshopName;
    }

    public void setItnToWorkshopName(Object itnToWorkshopName) {
        this.itnToWorkshopName = itnToWorkshopName;
    }

    public Object getItnFromWorkshopName() {
        return itnFromWorkshopName;
    }

    public void setItnFromWorkshopName(Object itnFromWorkshopName) {
        this.itnFromWorkshopName = itnFromWorkshopName;
    }

    public String getTicketRaiseByName() {
        return ticketRaiseByName;
    }

    public void setTicketRaiseByName(String ticketRaiseByName) {
        this.ticketRaiseByName = ticketRaiseByName;
    }

    public String getItnSendToName() {
        return itnSendToName;
    }

    public void setItnSendToName(String itnSendToName) {
        this.itnSendToName = itnSendToName;
    }

    public String getItnCreatedByName() {
        return itnCreatedByName;
    }

    public void setItnCreatedByName(String itnCreatedByName) {
        this.itnCreatedByName = itnCreatedByName;
    }

    public String getItnReceivedByName() {
        return itnReceivedByName;
    }

    public void setItnReceivedByName(String itnReceivedByName) {
        this.itnReceivedByName = itnReceivedByName;
    }

    public String getItnCancelByName() {
        return itnCancelByName;
    }

    public void setItnCancelByName(String itnCancelByName) {
        this.itnCancelByName = itnCancelByName;
    }
}
