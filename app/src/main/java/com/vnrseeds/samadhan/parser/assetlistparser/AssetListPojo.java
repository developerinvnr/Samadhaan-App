package com.vnrseeds.samadhan.parser.assetlistparser;

public class AssetListPojo {
    private int srno;
    private String assetId;
    private String assetCode;
    private String acName;
    private String assetType;
    private String brandName;
    private String bmName;
    private String locationName;
    private String departmentName;
    private String dsName;
    private String custodianName;
    private String assetStatus;
    private String acCode;

    public AssetListPojo() {
    }

    public AssetListPojo(int srno,String assetId, String assetCode, String acName, String assetType, String brandName, String bmName, String locationName, String departmentName, String dsName, String custodianName, String assetStatus, String acCode) {
        this.srno = srno;
        this.assetId = assetId;
        this.assetCode = assetCode;
        this.acName = acName;
        this.assetType = assetType;
        this.brandName = brandName;
        this.bmName = bmName;
        this.locationName = locationName;
        this.departmentName = departmentName;
        this.dsName = dsName;
        this.custodianName = custodianName;
        this.assetStatus = assetStatus;
        this.acCode = acCode;
    }

    public int getSrno() {
        return srno;
    }

    public void setSrno(int srno) {
        this.srno = srno;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public String getAcName() {
        return acName;
    }

    public void setAcName(String acName) {
        this.acName = acName;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBmName() {
        return bmName;
    }

    public void setBmName(String bmName) {
        this.bmName = bmName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDsName() {
        return dsName;
    }

    public void setDsName(String dsName) {
        this.dsName = dsName;
    }

    public String getCustodianName() {
        return custodianName;
    }

    public void setCustodianName(String custodianName) {
        this.custodianName = custodianName;
    }

    public String getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(String assetStatus) {
        this.assetStatus = assetStatus;
    }

    public String getAcCode() {
        return acCode;
    }

    public void setAcCode(String acCode) {
        this.acCode = acCode;
    }
}
