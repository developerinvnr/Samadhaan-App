
package com.vnrseeds.samadhan.parser.assetdataparser;

import com.vnrseeds.samadhan.parser.custodianparser.CustodianData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("assetData")
    @Expose
    private AssetData assetData;
    @SerializedName("brandModelList")
    @Expose
    private List<com.vnrseeds.samadhan.parser.brandmodelparser.Data> brandModelList = null;
    @SerializedName("monitorBrandModelList")
    @Expose
    private List<MonitorBrandModel> monitorBrandModelList = null;
    @SerializedName("keyboardBrandModelList")
    @Expose
    private List<KeyboardBrandModel> keyboardBrandModelList = null;
    @SerializedName("mouseBrandModelList")
    @Expose
    private List<MouseBrandModel> mouseBrandModelList = null;
    @SerializedName("departmentList")
    @Expose
    private List<Department> departmentList = null;
    @SerializedName("sectionList")
    @Expose
    private List<Section> sectionList = null;
    @SerializedName("custodianList")
    @Expose
    private List<CustodianData> custodianList = null;

    public AssetData getAssetData() {
        return assetData;
    }

    public void setAssetData(AssetData assetData) {
        this.assetData = assetData;
    }

    public List<com.vnrseeds.samadhan.parser.brandmodelparser.Data> getBrandModelList() {
        return brandModelList;
    }

    public void setBrandModelList(List<com.vnrseeds.samadhan.parser.brandmodelparser.Data> brandModelList) {
        this.brandModelList = brandModelList;
    }

    public List<MonitorBrandModel> getMonitorBrandModelList() {
        return monitorBrandModelList;
    }

    public void setMonitorBrandModelList(List<MonitorBrandModel> monitorBrandModelList) {
        this.monitorBrandModelList = monitorBrandModelList;
    }

    public List<KeyboardBrandModel> getKeyboardBrandModelList() {
        return keyboardBrandModelList;
    }

    public void setKeyboardBrandModelList(List<KeyboardBrandModel> keyboardBrandModelList) {
        this.keyboardBrandModelList = keyboardBrandModelList;
    }

    public List<MouseBrandModel> getMouseBrandModelList() {
        return mouseBrandModelList;
    }

    public void setMouseBrandModelList(List<MouseBrandModel> mouseBrandModelList) {
        this.mouseBrandModelList = mouseBrandModelList;
    }

    public List<Department> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList = departmentList;
    }

    public List<Section> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<Section> sectionList) {
        this.sectionList = sectionList;
    }

    public List<CustodianData> getCustodianList() {
        return custodianList;
    }

    public void setCustodianList(List<CustodianData> custodianList) {
        this.custodianList = custodianList;
    }
}
