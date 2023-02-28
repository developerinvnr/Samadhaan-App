
package com.vnrseeds.samadhan.parser.noticeparser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("notification_id")
    @Expose
    private Integer notificationId;
    @SerializedName("notification_subject")
    @Expose
    private String notificationSubject;
    @SerializedName("notification_message")
    @Expose
    private String notificationMessage;
    @SerializedName("notification_affected_from")
    @Expose
    private String notificationAffectedFrom;
    @SerializedName("notification_affected_to")
    @Expose
    private String notificationAffectedTo;
    @SerializedName("notification_created_by")
    @Expose
    private String notificationCreatedBy;
    @SerializedName("notification_created_at")
    @Expose
    private String notificationCreatedAt;
    @SerializedName("notification_is_viewed")
    @Expose
    private String notificationIsViewed;

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotificationSubject() {
        return notificationSubject;
    }

    public void setNotificationSubject(String notificationSubject) {
        this.notificationSubject = notificationSubject;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public String getNotificationAffectedFrom() {
        return notificationAffectedFrom;
    }

    public void setNotificationAffectedFrom(String notificationAffectedFrom) {
        this.notificationAffectedFrom = notificationAffectedFrom;
    }

    public String getNotificationAffectedTo() {
        return notificationAffectedTo;
    }

    public void setNotificationAffectedTo(String notificationAffectedTo) {
        this.notificationAffectedTo = notificationAffectedTo;
    }

    public String getNotificationCreatedBy() {
        return notificationCreatedBy;
    }

    public void setNotificationCreatedBy(String notificationCreatedBy) {
        this.notificationCreatedBy = notificationCreatedBy;
    }

    public String getNotificationCreatedAt() {
        return notificationCreatedAt;
    }

    public void setNotificationCreatedAt(String notificationCreatedAt) {
        this.notificationCreatedAt = notificationCreatedAt;
    }

    public String getNotificationIsViewed() {
        return notificationIsViewed;
    }

    public void setNotificationIsViewed(String notificationIsViewed) {
        this.notificationIsViewed = notificationIsViewed;
    }

}
