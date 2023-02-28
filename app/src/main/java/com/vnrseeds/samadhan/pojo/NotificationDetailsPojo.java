package com.vnrseeds.samadhan.pojo;

public class NotificationDetailsPojo {

    private Integer notificationId;
    private String notificationSubject;
    private String notificationMessage;
    private String notificationCreatedBy;
    private String notificationCreatedAt;
    private String notificationIsViewed;

    public NotificationDetailsPojo(Integer notificationId, String notificationSubject, String notificationMessage, String notificationCreatedAt, String notificationCreatedBy, String notificationIsViewed) {
        this.notificationId = notificationId;
        this.notificationSubject = notificationSubject;
        this.notificationMessage = notificationMessage;
        this.notificationCreatedAt = notificationCreatedAt;
        this.notificationCreatedBy = notificationCreatedBy;
        this.notificationIsViewed = notificationIsViewed;
    }

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
