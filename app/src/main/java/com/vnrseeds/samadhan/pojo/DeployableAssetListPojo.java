package com.vnrseeds.samadhan.pojo;

public class DeployableAssetListPojo {
    private int srno;
    private int assetId;
    private String assetCode;
    private String assetName;
    private String acName;
    private String acCode;
    private String brandName;
    private String bmName;
    private String assetSerialNo;
    private String assetCurrentStatus;

    public DeployableAssetListPojo(int srno, int assetId, String assetCode, String assetName, String acName, String acCode, String brandName, String bmName, String assetSerialNo, String assetCurrentStatus) {
        this.srno = srno;
        this.assetId = assetId;
        this.assetCode = assetCode;
        this.assetName = assetName;
        this.acName = acName;
        this.acCode = acCode;
        this.brandName = brandName;
        this.bmName = bmName;
        this.assetSerialNo = assetSerialNo;
        this.assetCurrentStatus = assetCurrentStatus;
    }

    public int getSrno() {
        return srno;
    }

    public void setSrno(int srno) {
        this.srno = srno;
    }

    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getAcName() {
        return acName;
    }

    public void setAcName(String acName) {
        this.acName = acName;
    }

    public String getAcCode() {
        return acCode;
    }

    public void setAcCode(String acCode) {
        this.acCode = acCode;
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

    public String getAssetSerialNo() {
        return assetSerialNo;
    }

    public void setAssetSerialNo(String assetSerialNo) {
        this.assetSerialNo = assetSerialNo;
    }

    public String getAssetCurrentStatus() {
        return assetCurrentStatus;
    }

    public void setAssetCurrentStatus(String assetCurrentStatus) {
        this.assetCurrentStatus = assetCurrentStatus;
    }
}
