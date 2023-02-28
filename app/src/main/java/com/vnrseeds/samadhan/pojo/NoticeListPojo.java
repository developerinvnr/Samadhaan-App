package com.vnrseeds.samadhan.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoticeListPojo {

    private Integer notificationId;
    private String notificationSubject;
    private String notificationMessage;
    private String notificationAffectedFrom;
    private String notificationAffectedTo;
    private String notificationCreatedBy;
    private String notificationCreatedAt;
    private String notificationIsViewed;

    public NoticeListPojo(Integer notificationId, String notificationSubject, String notificationMessage, String notificationAffectedFrom, String notificationAffectedTo, String notificationCreatedBy, String notificationCreatedAt, String notificationIsViewed) {
        this.notificationId = notificationId;
        this.notificationSubject = notificationSubject;
        this.notificationMessage = notificationMessage;
        this.notificationAffectedFrom = notificationAffectedFrom;
        this.notificationAffectedTo = notificationAffectedTo;
        this.notificationCreatedBy = notificationCreatedBy;
        this.notificationCreatedAt = notificationCreatedAt;
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
