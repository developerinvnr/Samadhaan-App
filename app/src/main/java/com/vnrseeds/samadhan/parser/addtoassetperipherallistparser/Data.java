
package com.vnrseeds.samadhan.parser.addtoassetperipherallistparser;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("assets")
    @Expose
    private List<Asset> assets = null;

    public List<Asset> getAssets() {
        return assets;
    }

    public void setAssets(List<Asset> assets) {
        this.assets = assets;
    }

}
