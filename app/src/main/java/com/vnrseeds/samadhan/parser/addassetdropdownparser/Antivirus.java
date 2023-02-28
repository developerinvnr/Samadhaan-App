
package com.vnrseeds.samadhan.parser.addassetdropdownparser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Antivirus {

    @SerializedName("antivirus_id")
    @Expose
    private String antivirusId;
    @SerializedName("antivirus_name")
    @Expose
    private String antivirusName;

    public String getAntivirusId() {
        return antivirusId;
    }

    public void setAntivirusId(String antivirusId) {
        this.antivirusId = antivirusId;
    }

    public String getAntivirusName() {
        return antivirusName;
    }

    public void setAntivirusName(String antivirusName) {
        this.antivirusName = antivirusName;
    }

}
