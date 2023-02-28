package com.vnrseeds.samadhan.pojo;

public class AssetListPojo {
    int srno;
    String asset_id,asset_ac_id,asset_code,asset_type,brand_name,model_name,asset_serial_no,asset_installation_date,ac_name,ac_icon,location_name,department_name,ds_name,assetCustodian,ac_code,assetIsByod;
    int ticketIsActive;

    public AssetListPojo() {
    }

    public AssetListPojo(int srno, String asset_id, String asset_ac_id, String asset_code, String asset_type, String brand_name, String model_name, String asset_serial_no, String asset_installation_date, String ac_name,String ac_icon, String location_name, String department_name, String ds_name, String assetCustodian, String ac_code, String assetIsByod, int ticketIsActive) {
        this.srno = srno;
        this.asset_id = asset_id;
        this.asset_ac_id = asset_ac_id;
        this.asset_code = asset_code;
        this.asset_type = asset_type;
        this.brand_name = brand_name;
        this.model_name = model_name;
        this.asset_serial_no = asset_serial_no;
        this.asset_installation_date = asset_installation_date;
        this.ac_name = ac_name;
        this.ac_icon = ac_icon;
        this.location_name = location_name;
        this.department_name = department_name;
        this.ds_name = ds_name;
        this.assetCustodian = assetCustodian;
        this.ac_code = ac_code;
        this.assetIsByod = assetIsByod;
        this.ticketIsActive = ticketIsActive;
    }

    public int getSrno() {
        return srno;
    }

    public void setSrno(int srno) {
        this.srno = srno;
    }

    public String getAsset_id() {
        return asset_id;
    }

    public void setAsset_id(String asset_id) {
        this.asset_id = asset_id;
    }

    public String getAsset_ac_id() {
        return asset_ac_id;
    }

    public void setAsset_ac_id(String asset_ac_id) {
        this.asset_ac_id = asset_ac_id;
    }

    public String getAsset_code() {
        return asset_code;
    }

    public void setAsset_code(String asset_code) {
        this.asset_code = asset_code;
    }

    public String getAsset_type() {
        return asset_type;
    }

    public void setAsset_type(String asset_type) {
        this.asset_type = asset_type;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public String getAsset_serial_no() {
        return asset_serial_no;
    }

    public void setAsset_serial_no(String asset_serial_no) {
        this.asset_serial_no = asset_serial_no;
    }

    public String getAsset_installation_date() {
        return asset_installation_date;
    }

    public void setAsset_installation_date(String asset_installation_date) {
        this.asset_installation_date = asset_installation_date;
    }

    public String getAc_name() {
        return ac_name;
    }

    public void setAc_name(String ac_name) {
        this.ac_name = ac_name;
    }

    public String getAc_icon() {
        return ac_icon;
    }

    public void setAc_icon(String ac_icon) {
        this.ac_icon = ac_icon;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getDs_name() {
        return ds_name;
    }

    public void setDs_name(String ds_name) {
        this.ds_name = ds_name;
    }

    public String getAssetCustodian() {
        return assetCustodian;
    }

    public void setAssetCustodian(String assetCustodian) {
        this.assetCustodian = assetCustodian;
    }

    public String getAc_code() {
        return ac_code;
    }

    public void setAc_code(String ac_code) {
        this.ac_code = ac_code;
    }

    public String getAssetIsByod() {
        return assetIsByod;
    }

    public void setAssetIsByod(String assetIsByod) {
        this.assetIsByod = assetIsByod;
    }

    public int getTicketIsActive() {
        return ticketIsActive;
    }

    public void setTicketIsActive(int ticketIsActive) {
        this.ticketIsActive = ticketIsActive;
    }
}
