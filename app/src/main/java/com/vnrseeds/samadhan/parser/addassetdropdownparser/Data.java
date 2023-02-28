
package com.vnrseeds.samadhan.parser.addassetdropdownparser;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("assetCategoryList")
    @Expose
    private List<AssetCategory> assetCategoryList = null;
    @SerializedName("brandList")
    @Expose
    private List<Brand> brandList = null;
    @SerializedName("processorList")
    @Expose
    private List<Processor> processorList = null;
    @SerializedName("ramList")
    @Expose
    private List<Ram> ramList = null;
    @SerializedName("ramCapacityList")
    @Expose
    private List<RamCapacity> ramCapacityList = null;
    @SerializedName("vendorList")
    @Expose
    private List<Vendor> vendorList = null;
    @SerializedName("locationList")
    @Expose
    private List<Location> locationList = null;
    @SerializedName("networkTypeList")
    @Expose
    private List<NetworkType> networkTypeList = null;
    @SerializedName("operatingSystemList")
    @Expose
    private List<OperatingSystem> operatingSystemList = null;
    @SerializedName("antivirusList")
    @Expose
    private List<Antivirus> antivirusList = null;
    @SerializedName("browserList")
    @Expose
    private List<String> browserList = null;
    @SerializedName("officeTypeList")
    @Expose
    private List<String> officeTypeList = null;
    @SerializedName("softwareList")
    @Expose
    private List<Software> softwareList = null;
    @SerializedName("cameraTypeList")
    @Expose
    private List<String> cameraTypeList = null;
    @SerializedName("cameraFormFactorList")
    @Expose
    private List<String> cameraFormFactorList = null;
    @SerializedName("channelList")
    @Expose
    private List<String> channelList = null;
    @SerializedName("storageCapacityList")
    @Expose
    private List<String> storageCapacityList = null;
    @SerializedName("printerConnectionTypeList")
    @Expose
    private List<String> printerConnectionTypeList = null;
    @SerializedName("cameraAvailableList")
    @Expose
    private List<String> cameraAvailableList = null;
    @SerializedName("SIMBrandList")
    @Expose
    private List<String> sIMBrandList = null;
    @SerializedName("ownedByList")
    @Expose
    private List<String> ownedByList = null;
    @SerializedName("portTypeList")
    @Expose
    private List<String> portTypeList = null;
    @SerializedName("modeList")
    @Expose
    private List<String> modeList = null;
    @SerializedName("speakerTypeList")
    @Expose
    private List<String> speakerTypeList = null;
    @SerializedName("switchTypeList")
    @Expose
    private List<String> switchTypeList = null;
    @SerializedName("switchPortTypeList")
    @Expose
    private List<String> switchPortTypeList = null;
    @SerializedName("LANPortList")
    @Expose
    private List<Integer> lANPortList = null;
    @SerializedName("tvTypeList")
    @Expose
    private List<String> tvTypeList = null;



    public List<AssetCategory> getAssetCategoryList() {
        return assetCategoryList;
    }

    public void setAssetCategoryList(List<AssetCategory> assetCategoryList) {
        this.assetCategoryList = assetCategoryList;
    }

    public List<Brand> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<Brand> brandList) {
        this.brandList = brandList;
    }

    public List<Processor> getProcessorList() {
        return processorList;
    }

    public void setProcessorList(List<Processor> processorList) {
        this.processorList = processorList;
    }

    public List<Ram> getRamList() {
        return ramList;
    }

    public void setRamList(List<Ram> ramList) {
        this.ramList = ramList;
    }

    public List<RamCapacity> getRamCapacityList() {
        return ramCapacityList;
    }

    public void setRamCapacityList(List<RamCapacity> ramCapacityList) {
        this.ramCapacityList = ramCapacityList;
    }

    public List<Vendor> getVendorList() {
        return vendorList;
    }

    public void setVendorList(List<Vendor> vendorList) {
        this.vendorList = vendorList;
    }

    public List<Location> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }

    public List<NetworkType> getNetworkTypeList() {
        return networkTypeList;
    }

    public void setNetworkTypeList(List<NetworkType> networkTypeList) {
        this.networkTypeList = networkTypeList;
    }

    public List<OperatingSystem> getOperatingSystemList() {
        return operatingSystemList;
    }

    public void setOperatingSystemList(List<OperatingSystem> operatingSystemList) {
        this.operatingSystemList = operatingSystemList;
    }

    public List<Antivirus> getAntivirusList() {
        return antivirusList;
    }

    public void setAntivirusList(List<Antivirus> antivirusList) {
        this.antivirusList = antivirusList;
    }

    public List<String> getBrowserList() {
        return browserList;
    }

    public void setBrowserList(List<String> browserList) {
        this.browserList = browserList;
    }

    public List<String> getOfficeTypeList() {
        return officeTypeList;
    }

    public void setOfficeTypeList(List<String> officeTypeList) {
        this.officeTypeList = officeTypeList;
    }

    public List<Software> getSoftwareList() {
        return softwareList;
    }

    public void setSoftwareList(List<Software> softwareList) {
        this.softwareList = softwareList;
    }

    public List<String> getCameraTypeList() {
        return cameraTypeList;
    }

    public void setCameraTypeList(List<String> cameraTypeList) {
        this.cameraTypeList = cameraTypeList;
    }

    public List<String> getCameraFormFactorList() {
        return cameraFormFactorList;
    }

    public void setCameraFormFactorList(List<String> cameraFormFactorList) {
        this.cameraFormFactorList = cameraFormFactorList;
    }

    public List<String> getChannelList() {
        return channelList;
    }

    public void setChannelList(List<String> channelList) {
        this.channelList = channelList;
    }

    public List<String> getStorageCapacityList() {
        return storageCapacityList;
    }

    public void setStorageCapacityList(List<String> storageCapacityList) {
        this.storageCapacityList = storageCapacityList;
    }

    public List<String> getPrinterConnectionTypeList() {
        return printerConnectionTypeList;
    }

    public void setPrinterConnectionTypeList(List<String> printerConnectionTypeList) {
        this.printerConnectionTypeList = printerConnectionTypeList;
    }

    public List<String> getCameraAvailableList() {
        return cameraAvailableList;
    }

    public void setCameraAvailableList(List<String> cameraAvailableList) {
        this.cameraAvailableList = cameraAvailableList;
    }

    public List<String> getsIMBrandList() {
        return sIMBrandList;
    }

    public void setsIMBrandList(List<String> sIMBrandList) {
        this.sIMBrandList = sIMBrandList;
    }

    public List<String> getOwnedByList() {
        return ownedByList;
    }

    public void setOwnedByList(List<String> ownedByList) {
        this.ownedByList = ownedByList;
    }

    public List<String> getPortTypeList() {
        return portTypeList;
    }

    public void setPortTypeList(List<String> portTypeList) {
        this.portTypeList = portTypeList;
    }

    public List<String> getModeList() {
        return modeList;
    }

    public void setModeList(List<String> modeList) {
        this.modeList = modeList;
    }

    public List<String> getSpeakerTypeList() {
        return speakerTypeList;
    }

    public void setSpeakerTypeList(List<String> speakerTypeList) {
        this.speakerTypeList = speakerTypeList;
    }

    public List<String> getSwitchTypeList() {
        return switchTypeList;
    }

    public void setSwitchTypeList(List<String> switchTypeList) {
        this.switchTypeList = switchTypeList;
    }

    public List<String> getSwitchPortTypeList() {
        return switchPortTypeList;
    }

    public void setSwitchPortTypeList(List<String> switchPortTypeList) {
        this.switchPortTypeList = switchPortTypeList;
    }

    public List<Integer> getlANPortList() {
        return lANPortList;
    }

    public void setlANPortList(List<Integer> lANPortList) {
        this.lANPortList = lANPortList;
    }

    public List<String> getTvTypeList() {
        return tvTypeList;
    }

    public void setTvTypeList(List<String> tvTypeList) {
        this.tvTypeList = tvTypeList;
    }
}
