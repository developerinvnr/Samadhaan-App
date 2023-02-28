package com.vnrseeds.samadhan.parser.srnlistparser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Srn {
    @SerializedName("srn_id")
    @Expose
    private Integer srnId;
    @SerializedName("srn_code")
    @Expose
    private String srnCode;
    @SerializedName("srn_ticket_id")
    @Expose
    private Integer srnTicketId;
    @SerializedName("srn_ticket_current_status")
    @Expose
    private String srnTicketCurrentStatus;
    @SerializedName("srn_for")
    @Expose
    private String srnFor;
    @SerializedName("srn_transfer_to")
    @Expose
    private String srnTransferTo;
    @SerializedName("srn_from_workshop_id")
    @Expose
    private Integer srnFromWorkshopId;
    @SerializedName("srn_vendor_id")
    @Expose
    private Integer srnVendorId;
    @SerializedName("srn_vendor_address")
    @Expose
    private String srnVendorAddress;
    @SerializedName("srn_mode")
    @Expose
    private String srnMode;
    @SerializedName("srn_person_name")
    @Expose
    private String srnPersonName;
    @SerializedName("srn_mobile")
    @Expose
    private String srnMobile;
    @SerializedName("srn_courier_company")
    @Expose
    private Object srnCourierCompany;
    @SerializedName("srn_courier_docket_no")
    @Expose
    private Object srnCourierDocketNo;
    @SerializedName("srn_transporter_name")
    @Expose
    private Object srnTransporterName;
    @SerializedName("srn_vehicle_no")
    @Expose
    private Object srnVehicleNo;
    @SerializedName("srn_driver_name")
    @Expose
    private Object srnDriverName;
    @SerializedName("srn_particular")
    @Expose
    private String srnParticular;
    @SerializedName("srn_quantity")
    @Expose
    private Integer srnQuantity;
    @SerializedName("srn_remarks")
    @Expose
    private String srnRemarks;
    @SerializedName("srn_file")
    @Expose
    private String srnFile;
    @SerializedName("srn_create_entry")
    @Expose
    private String srnCreateEntry;
    @SerializedName("srn_update_entry")
    @Expose
    private Object srnUpdateEntry;
    @SerializedName("srn_created_by")
    @Expose
    private Integer srnCreatedBy;
    @SerializedName("srn_created_at")
    @Expose
    private String srnCreatedAt;
    @SerializedName("srn_updated_by")
    @Expose
    private Object srnUpdatedBy;
    @SerializedName("srn_updated_at")
    @Expose
    private Object srnUpdatedAt;
    @SerializedName("srn_received_for")
    @Expose
    private String srnReceivedFor;
    @SerializedName("srn_sc_received_by")
    @Expose
    private Object srnScReceivedBy;
    @SerializedName("srn_sc_received_at")
    @Expose
    private String srnScReceivedAt;
    @SerializedName("srn_received_by")
    @Expose
    private Object srnReceivedBy;
    @SerializedName("srn_received_at")
    @Expose
    private String srnReceivedAt;
    @SerializedName("srn_received_transfer_to")
    @Expose
    private Object srnReceivedTransferTo;
    @SerializedName("srn_received_remarks")
    @Expose
    private Object srnReceivedRemarks;
    @SerializedName("srn_received_file")
    @Expose
    private Object srnReceivedFile;
    @SerializedName("srn_cancel_by")
    @Expose
    private Object srnCancelBy;
    @SerializedName("srn_cancel_at")
    @Expose
    private Object srnCancelAt;
    @SerializedName("srn_cancel_remarks")
    @Expose
    private Object srnCancelRemarks;
    @SerializedName("srn_store_primary_location_id")
    @Expose
    private Object srnStorePrimaryLocationId;
    @SerializedName("srn_store_secondary_location_id")
    @Expose
    private Object srnStoreSecondaryLocationId;
    @SerializedName("srn_store_storage_type_id")
    @Expose
    private Object srnStoreStorageTypeId;
    @SerializedName("srn_store_storage_section_id")
    @Expose
    private Object srnStoreStorageSectionId;
    @SerializedName("srn_store_box_number")
    @Expose
    private Object srnStoreBoxNumber;
    @SerializedName("srn_status")
    @Expose
    private String srnStatus;
    @SerializedName("ticket_raise_by")
    @Expose
    private Integer ticketRaiseBy;
    @SerializedName("ticket_code")
    @Expose
    private String ticketCode;
    @SerializedName("location_name")
    @Expose
    private String locationName;
    @SerializedName("srn_from_workshop_name")
    @Expose
    private String srnFromWorkshopName;
    @SerializedName("ticket_raise_by_name")
    @Expose
    private String ticketRaiseByName;
    @SerializedName("vendor_name")
    @Expose
    private String vendorName;
    @SerializedName("srn_created_by_name")
    @Expose
    private String srnCreatedByName;
    @SerializedName("srn_received_by_name")
    @Expose
    private Object srnReceivedByName;
    @SerializedName("srn_cancel_by_name")
    @Expose
    private Object srnCancelByName;

    public Integer getSrnId() {
        return srnId;
    }

    public void setSrnId(Integer srnId) {
        this.srnId = srnId;
    }

    public String getSrnCode() {
        return srnCode;
    }

    public void setSrnCode(String srnCode) {
        this.srnCode = srnCode;
    }

    public Integer getSrnTicketId() {
        return srnTicketId;
    }

    public void setSrnTicketId(Integer srnTicketId) {
        this.srnTicketId = srnTicketId;
    }

    public String getSrnTicketCurrentStatus() {
        return srnTicketCurrentStatus;
    }

    public void setSrnTicketCurrentStatus(String srnTicketCurrentStatus) {
        this.srnTicketCurrentStatus = srnTicketCurrentStatus;
    }

    public String getSrnFor() {
        return srnFor;
    }

    public void setSrnFor(String srnFor) {
        this.srnFor = srnFor;
    }

    public String getSrnTransferTo() {
        return srnTransferTo;
    }

    public void setSrnTransferTo(String srnTransferTo) {
        this.srnTransferTo = srnTransferTo;
    }

    public Integer getSrnFromWorkshopId() {
        return srnFromWorkshopId;
    }

    public void setSrnFromWorkshopId(Integer srnFromWorkshopId) {
        this.srnFromWorkshopId = srnFromWorkshopId;
    }

    public Integer getSrnVendorId() {
        return srnVendorId;
    }

    public void setSrnVendorId(Integer srnVendorId) {
        this.srnVendorId = srnVendorId;
    }

    public String getSrnVendorAddress() {
        return srnVendorAddress;
    }

    public void setSrnVendorAddress(String srnVendorAddress) {
        this.srnVendorAddress = srnVendorAddress;
    }

    public String getSrnMode() {
        return srnMode;
    }

    public void setSrnMode(String srnMode) {
        this.srnMode = srnMode;
    }

    public String getSrnPersonName() {
        return srnPersonName;
    }

    public void setSrnPersonName(String srnPersonName) {
        this.srnPersonName = srnPersonName;
    }

    public String getSrnMobile() {
        return srnMobile;
    }

    public void setSrnMobile(String srnMobile) {
        this.srnMobile = srnMobile;
    }

    public Object getSrnCourierCompany() {
        return srnCourierCompany;
    }

    public void setSrnCourierCompany(Object srnCourierCompany) {
        this.srnCourierCompany = srnCourierCompany;
    }

    public Object getSrnCourierDocketNo() {
        return srnCourierDocketNo;
    }

    public void setSrnCourierDocketNo(Object srnCourierDocketNo) {
        this.srnCourierDocketNo = srnCourierDocketNo;
    }

    public Object getSrnTransporterName() {
        return srnTransporterName;
    }

    public void setSrnTransporterName(Object srnTransporterName) {
        this.srnTransporterName = srnTransporterName;
    }

    public Object getSrnVehicleNo() {
        return srnVehicleNo;
    }

    public void setSrnVehicleNo(Object srnVehicleNo) {
        this.srnVehicleNo = srnVehicleNo;
    }

    public Object getSrnDriverName() {
        return srnDriverName;
    }

    public void setSrnDriverName(Object srnDriverName) {
        this.srnDriverName = srnDriverName;
    }

    public String getSrnParticular() {
        return srnParticular;
    }

    public void setSrnParticular(String srnParticular) {
        this.srnParticular = srnParticular;
    }

    public Integer getSrnQuantity() {
        return srnQuantity;
    }

    public void setSrnQuantity(Integer srnQuantity) {
        this.srnQuantity = srnQuantity;
    }

    public String getSrnRemarks() {
        return srnRemarks;
    }

    public void setSrnRemarks(String srnRemarks) {
        this.srnRemarks = srnRemarks;
    }

    public String getSrnFile() {
        return srnFile;
    }

    public void setSrnFile(String srnFile) {
        this.srnFile = srnFile;
    }

    public String getSrnCreateEntry() {
        return srnCreateEntry;
    }

    public void setSrnCreateEntry(String srnCreateEntry) {
        this.srnCreateEntry = srnCreateEntry;
    }

    public Object getSrnUpdateEntry() {
        return srnUpdateEntry;
    }

    public void setSrnUpdateEntry(Object srnUpdateEntry) {
        this.srnUpdateEntry = srnUpdateEntry;
    }

    public Integer getSrnCreatedBy() {
        return srnCreatedBy;
    }

    public void setSrnCreatedBy(Integer srnCreatedBy) {
        this.srnCreatedBy = srnCreatedBy;
    }

    public String getSrnCreatedAt() {
        return srnCreatedAt;
    }

    public void setSrnCreatedAt(String srnCreatedAt) {
        this.srnCreatedAt = srnCreatedAt;
    }

    public Object getSrnUpdatedBy() {
        return srnUpdatedBy;
    }

    public void setSrnUpdatedBy(Object srnUpdatedBy) {
        this.srnUpdatedBy = srnUpdatedBy;
    }

    public Object getSrnUpdatedAt() {
        return srnUpdatedAt;
    }

    public void setSrnUpdatedAt(Object srnUpdatedAt) {
        this.srnUpdatedAt = srnUpdatedAt;
    }

    public String getSrnReceivedFor() {
        return srnReceivedFor;
    }

    public void setSrnReceivedFor(String srnReceivedFor) {
        this.srnReceivedFor = srnReceivedFor;
    }

    public Object getSrnScReceivedBy() {
        return srnScReceivedBy;
    }

    public void setSrnScReceivedBy(Object srnScReceivedBy) {
        this.srnScReceivedBy = srnScReceivedBy;
    }

    public String getSrnScReceivedAt() {
        return srnScReceivedAt;
    }

    public void setSrnScReceivedAt(String srnScReceivedAt) {
        this.srnScReceivedAt = srnScReceivedAt;
    }

    public Object getSrnReceivedBy() {
        return srnReceivedBy;
    }

    public void setSrnReceivedBy(Object srnReceivedBy) {
        this.srnReceivedBy = srnReceivedBy;
    }

    public String getSrnReceivedAt() {
        return srnReceivedAt;
    }

    public void setSrnReceivedAt(String srnReceivedAt) {
        this.srnReceivedAt = srnReceivedAt;
    }

    public Object getSrnReceivedTransferTo() {
        return srnReceivedTransferTo;
    }

    public void setSrnReceivedTransferTo(Object srnReceivedTransferTo) {
        this.srnReceivedTransferTo = srnReceivedTransferTo;
    }

    public Object getSrnReceivedRemarks() {
        return srnReceivedRemarks;
    }

    public void setSrnReceivedRemarks(Object srnReceivedRemarks) {
        this.srnReceivedRemarks = srnReceivedRemarks;
    }

    public Object getSrnReceivedFile() {
        return srnReceivedFile;
    }

    public void setSrnReceivedFile(Object srnReceivedFile) {
        this.srnReceivedFile = srnReceivedFile;
    }

    public Object getSrnCancelBy() {
        return srnCancelBy;
    }

    public void setSrnCancelBy(Object srnCancelBy) {
        this.srnCancelBy = srnCancelBy;
    }

    public Object getSrnCancelAt() {
        return srnCancelAt;
    }

    public void setSrnCancelAt(Object srnCancelAt) {
        this.srnCancelAt = srnCancelAt;
    }

    public Object getSrnCancelRemarks() {
        return srnCancelRemarks;
    }

    public void setSrnCancelRemarks(Object srnCancelRemarks) {
        this.srnCancelRemarks = srnCancelRemarks;
    }

    public Object getSrnStorePrimaryLocationId() {
        return srnStorePrimaryLocationId;
    }

    public void setSrnStorePrimaryLocationId(Object srnStorePrimaryLocationId) {
        this.srnStorePrimaryLocationId = srnStorePrimaryLocationId;
    }

    public Object getSrnStoreSecondaryLocationId() {
        return srnStoreSecondaryLocationId;
    }

    public void setSrnStoreSecondaryLocationId(Object srnStoreSecondaryLocationId) {
        this.srnStoreSecondaryLocationId = srnStoreSecondaryLocationId;
    }

    public Object getSrnStoreStorageTypeId() {
        return srnStoreStorageTypeId;
    }

    public void setSrnStoreStorageTypeId(Object srnStoreStorageTypeId) {
        this.srnStoreStorageTypeId = srnStoreStorageTypeId;
    }

    public Object getSrnStoreStorageSectionId() {
        return srnStoreStorageSectionId;
    }

    public void setSrnStoreStorageSectionId(Object srnStoreStorageSectionId) {
        this.srnStoreStorageSectionId = srnStoreStorageSectionId;
    }

    public Object getSrnStoreBoxNumber() {
        return srnStoreBoxNumber;
    }

    public void setSrnStoreBoxNumber(Object srnStoreBoxNumber) {
        this.srnStoreBoxNumber = srnStoreBoxNumber;
    }

    public String getSrnStatus() {
        return srnStatus;
    }

    public void setSrnStatus(String srnStatus) {
        this.srnStatus = srnStatus;
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

    public String getSrnFromWorkshopName() {
        return srnFromWorkshopName;
    }

    public void setSrnFromWorkshopName(String srnFromWorkshopName) {
        this.srnFromWorkshopName = srnFromWorkshopName;
    }

    public String getTicketRaiseByName() {
        return ticketRaiseByName;
    }

    public void setTicketRaiseByName(String ticketRaiseByName) {
        this.ticketRaiseByName = ticketRaiseByName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getSrnCreatedByName() {
        return srnCreatedByName;
    }

    public void setSrnCreatedByName(String srnCreatedByName) {
        this.srnCreatedByName = srnCreatedByName;
    }

    public Object getSrnReceivedByName() {
        return srnReceivedByName;
    }

    public void setSrnReceivedByName(Object srnReceivedByName) {
        this.srnReceivedByName = srnReceivedByName;
    }

    public Object getSrnCancelByName() {
        return srnCancelByName;
    }

    public void setSrnCancelByName(Object srnCancelByName) {
        this.srnCancelByName = srnCancelByName;
    }
}
