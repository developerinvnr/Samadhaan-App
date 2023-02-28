
package com.vnrseeds.samadhan.parser.srnparser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceCenter {

    @SerializedName("vendor_id")
    @Expose
    private Integer vendorId;
    @SerializedName("vendor_name")
    @Expose
    private String vendorName;
    @SerializedName("address_1")
    @Expose
    private String address1;
    @SerializedName("city_name_1")
    @Expose
    private String cityName1;
    @SerializedName("district_name_1")
    @Expose
    private String districtName1;
    @SerializedName("state_name_1")
    @Expose
    private String stateName1;
    @SerializedName("country_name_1")
    @Expose
    private String countryName1;
    @SerializedName("pincode_1")
    @Expose
    private String pincode1;
    @SerializedName("vendor_second_address")
    @Expose
    private String vendorSecondAddress;
    @SerializedName("address_2")
    @Expose
    private Object address2;
    @SerializedName("city_name_2")
    @Expose
    private Object cityName2;
    @SerializedName("district_name_2")
    @Expose
    private Object districtName2;
    @SerializedName("state_name_2")
    @Expose
    private Object stateName2;
    @SerializedName("country_name_2")
    @Expose
    private Object countryName2;
    @SerializedName("pincode_2")
    @Expose
    private Object pincode2;

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getCityName1() {
        return cityName1;
    }

    public void setCityName1(String cityName1) {
        this.cityName1 = cityName1;
    }

    public String getDistrictName1() {
        return districtName1;
    }

    public void setDistrictName1(String districtName1) {
        this.districtName1 = districtName1;
    }

    public String getStateName1() {
        return stateName1;
    }

    public void setStateName1(String stateName1) {
        this.stateName1 = stateName1;
    }

    public String getCountryName1() {
        return countryName1;
    }

    public void setCountryName1(String countryName1) {
        this.countryName1 = countryName1;
    }

    public String getPincode1() {
        return pincode1;
    }

    public void setPincode1(String pincode1) {
        this.pincode1 = pincode1;
    }

    public String getVendorSecondAddress() {
        return vendorSecondAddress;
    }

    public void setVendorSecondAddress(String vendorSecondAddress) {
        this.vendorSecondAddress = vendorSecondAddress;
    }

    public Object getAddress2() {
        return address2;
    }

    public void setAddress2(Object address2) {
        this.address2 = address2;
    }

    public Object getCityName2() {
        return cityName2;
    }

    public void setCityName2(Object cityName2) {
        this.cityName2 = cityName2;
    }

    public Object getDistrictName2() {
        return districtName2;
    }

    public void setDistrictName2(Object districtName2) {
        this.districtName2 = districtName2;
    }

    public Object getStateName2() {
        return stateName2;
    }

    public void setStateName2(Object stateName2) {
        this.stateName2 = stateName2;
    }

    public Object getCountryName2() {
        return countryName2;
    }

    public void setCountryName2(Object countryName2) {
        this.countryName2 = countryName2;
    }

    public Object getPincode2() {
        return pincode2;
    }

    public void setPincode2(Object pincode2) {
        this.pincode2 = pincode2;
    }

}
