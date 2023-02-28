
package com.vnrseeds.samadhan.parser.issuelistparser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("issue_id")
    @Expose
    private String issueId;
    @SerializedName("issue_name")
    @Expose
    private String issueName;
    @SerializedName("issue_hindi_name")
    @Expose
    private String issueHindiName;

    public Datum(String issueId, String issueName, String issueHindiName) {
        this.issueId = issueId;
        this.issueName = issueName;
        this.issueHindiName = issueHindiName;
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public String getIssueName() {
        return issueName;
    }

    public void setIssueName(String issueName) {
        this.issueName = issueName;
    }

    public String getIssueHindiName() {
        return issueHindiName;
    }

    public void setIssueHindiName(String issueHindiName) {
        this.issueHindiName = issueHindiName;
    }
}
