
package com.vnrseeds.samadhan.parser.addassetdropdownparser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ram {

    @SerializedName("ram_id")
    @Expose
    private String ramId;
    @SerializedName("ram_name")
    @Expose
    private String ramName;

    public String getRamId() {
        return ramId;
    }

    public void setRamId(String ramId) {
        this.ramId = ramId;
    }

    public String getRamName() {
        return ramName;
    }

    public void setRamName(String ramName) {
        this.ramName = ramName;
    }

}
