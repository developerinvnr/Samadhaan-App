
package com.vnrseeds.samadhan.parser.ticketviewparser;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("raiseData")
    @Expose
    private RaiseData raiseData;
    @SerializedName("assignLogs")
    @Expose
    private List<AssignLog> assignLogs;
    @SerializedName("handleReplyLogs")
    @Expose
    private List<HandleReplyLog> handleReplyLogs;
    @SerializedName("internalNoteLogs")
    @Expose
    private List<InternalNoteLog> internalNoteLogs;
    @SerializedName("currentUserTypeList")
    @Expose
    private List<String> currentUserTypeList;
    @SerializedName("ticket_files")
    @Expose
    private List<String> ticketFiles;

    public RaiseData getRaiseData() {
        return raiseData;
    }

    public void setRaiseData(RaiseData raiseData) {
        this.raiseData = raiseData;
    }

    public List<AssignLog> getAssignLogs() {
        return assignLogs;
    }

    public void setAssignLogs(List<AssignLog> assignLogs) {
        this.assignLogs = assignLogs;
    }

    public List<HandleReplyLog> getHandleReplyLogs() {
        return handleReplyLogs;
    }

    public void setHandleReplyLogs(List<HandleReplyLog> handleReplyLogs) {
        this.handleReplyLogs = handleReplyLogs;
    }

    public List<InternalNoteLog> getInternalNoteLogs() {
        return internalNoteLogs;
    }

    public void setInternalNoteLogs(List<InternalNoteLog> internalNoteLogs) {
        this.internalNoteLogs = internalNoteLogs;
    }

    public List<String> getCurrentUserTypeList() {
        return currentUserTypeList;
    }

    public void setCurrentUserTypeList(List<String> currentUserTypeList) {
        this.currentUserTypeList = currentUserTypeList;
    }

    public List<String> getTicketFiles() {
        return ticketFiles;
    }

    public void setTicketFiles(List<String> ticketFiles) {
        this.ticketFiles = ticketFiles;
    }


}
