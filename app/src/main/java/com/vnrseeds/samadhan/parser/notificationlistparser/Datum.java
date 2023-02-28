
package com.vnrseeds.samadhan.parser.notificationlistparser;
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
