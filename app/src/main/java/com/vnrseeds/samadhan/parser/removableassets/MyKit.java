
package com.vnrseeds.samadhan.parser.removableassets;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyKit {

    @SerializedName("kit_id")
    @Expose
    private Integer kitId;
    @SerializedName("kit_name")
    @Expose
    private String kitName;

    public Integer getKitId() {
        return kitId;
    }

    public void setKitId(Integer kitId) {
        this.kitId = kitId;
    }

    public String getKitName() {
        return kitName;
    }

    public void setKitName(String kitName) {
        this.kitName = kitName;
    }

}
