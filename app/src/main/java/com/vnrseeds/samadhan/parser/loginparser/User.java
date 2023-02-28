package com.vnrseeds.samadhan.parser.loginparser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("user_id")
    @Expose
    private String user_id;

    @SerializedName("user_location_id")
    @Expose
    private String user_location_id;

    @SerializedName("user_department_id")
    @Expose
    private String user_department_id;

    @SerializedName("user_ur_id")
    @Expose
    private String user_ur_id;

    @SerializedName("user_name")
    @Expose
    private String user_name;

    @SerializedName("user_email")
    @Expose
    private String user_email;

    @SerializedName("user_mobile")
    @Expose
    private String user_mobile;

    @SerializedName("user_photo")
    @Expose
    private String user_photo;

    @SerializedName("user_is_developer")
    @Expose
    private String user_is_developer;

    @SerializedName("user_created_at")
    @Expose
    private String user_created_at;

    @SerializedName("user_created_by")
    @Expose
    private String user_created_by;

    @SerializedName("user_updated_at")
    @Expose
    private String user_updated_at;

    @SerializedName("user_updated_by")
    @Expose
    private String user_updated_by;

    @SerializedName("user_status")
    @Expose
    private String user_status;
    @SerializedName("district_name")
    @Expose
    private String districtName;
    @SerializedName("state_name")
    @Expose
    private String stateName;
    @SerializedName("country_name")
    @Expose
    private String countryName;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("location_name")
    @Expose
    private String locationName;
    @SerializedName("department_name")
    @Expose
    private String departmentName;
    @SerializedName("ds_name")
    @Expose
    private String dsName;
    @SerializedName("reporting_name")
    @Expose
    private String reportingName;
    @SerializedName("user_is_reset_password")
    @Expose
    private Integer userIsResetPassword;
    @SerializedName("city_name")
    @Expose
    private String cityName;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_location_id() {
        return user_location_id;
    }

    public void setUser_location_id(String user_location_id) {
        this.user_location_id = user_location_id;
    }

    public String getUser_department_id() {
        return user_department_id;
    }

    public void setUser_department_id(String user_department_id) {
        this.user_department_id = user_department_id;
    }

    public String getUser_ur_id() {
        return user_ur_id;
    }

    public void setUser_ur_id(String user_ur_id) {
        this.user_ur_id = user_ur_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getUser_photo() {
        return user_photo;
    }

    public void setUser_photo(String user_photo) {
        this.user_photo = user_photo;
    }

    public String getUser_is_developer() {
        return user_is_developer;
    }

    public void setUser_is_developer(String user_is_developer) {
        this.user_is_developer = user_is_developer;
    }

    public String getUser_created_at() {
        return user_created_at;
    }

    public void setUser_created_at(String user_created_at) {
        this.user_created_at = user_created_at;
    }

    public String getUser_created_by() {
        return user_created_by;
    }

    public void setUser_created_by(String user_created_by) {
        this.user_created_by = user_created_by;
    }

    public String getUser_updated_at() {
        return user_updated_at;
    }

    public void setUser_updated_at(String user_updated_at) {
        this.user_updated_at = user_updated_at;
    }

    public String getUser_updated_by() {
        return user_updated_by;
    }

    public void setUser_updated_by(String user_updated_by) {
        this.user_updated_by = user_updated_by;
    }

    public String getUser_status() {
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getReportingName() {
        return reportingName;
    }

    public void setReportingName(String reportingName) {
        this.reportingName = reportingName;
    }

    public Integer getUserIsResetPassword() {
        return userIsResetPassword;
    }

    public void setUserIsResetPassword(Integer userIsResetPassword) {
        this.userIsResetPassword = userIsResetPassword;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
