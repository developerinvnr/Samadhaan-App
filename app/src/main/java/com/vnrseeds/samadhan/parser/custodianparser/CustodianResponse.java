
package com.vnrseeds.samadhan.parser.custodianparser;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustodianResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<CustodianData> data = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("headers")
    @Expose
    private List<Object> headers = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<CustodianData> getData() {
        return data;
    }

    public void setData(List<CustodianData> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Object> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Object> headers) {
        this.headers = headers;
    }

}
