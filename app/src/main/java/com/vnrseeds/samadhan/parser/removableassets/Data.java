
package com.vnrseeds.samadhan.parser.removableassets;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("internalPeripherals")
    @Expose
    private List<InternalPeripheral> internalPeripherals = null;
    @SerializedName("externalPeripherals")
    @Expose
    private List<ExternalPeripheral> externalPeripherals = null;
    @SerializedName("ramCapacityList")
    @Expose
    private List<RamCapacity> ramCapacityList = null;
    @SerializedName("myKits")
    @Expose
    private List<MyKit> myKits = null;

    public List<InternalPeripheral> getInternalPeripherals() {
        return internalPeripherals;
    }

    public void setInternalPeripherals(List<InternalPeripheral> internalPeripherals) {
        this.internalPeripherals = internalPeripherals;
    }

    public List<ExternalPeripheral> getExternalPeripherals() {
        return externalPeripherals;
    }

    public void setExternalPeripherals(List<ExternalPeripheral> externalPeripherals) {
        this.externalPeripherals = externalPeripherals;
    }

    public List<RamCapacity> getRamCapacityList() {
        return ramCapacityList;
    }

    public void setRamCapacityList(List<RamCapacity> ramCapacityList) {
        this.ramCapacityList = ramCapacityList;
    }

    public List<MyKit> getMyKits() {
        return myKits;
    }

    public void setMyKits(List<MyKit> myKits) {
        this.myKits = myKits;
    }

}
