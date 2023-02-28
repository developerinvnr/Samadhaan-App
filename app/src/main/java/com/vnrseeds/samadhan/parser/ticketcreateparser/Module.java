package com.vnrseeds.samadhan.parser.ticketcreateparser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Module {
    @SerializedName("module_id")
    @Expose
    private Integer moduleId;
    @SerializedName("module_application_id")
    @Expose
    private Integer moduleApplicationId;
    @SerializedName("module_name")
    @Expose
    private String moduleName;
    @SerializedName("module_has_submodule")
    @Expose
    private String moduleHasSubmodule;
    @SerializedName("module_created_at")
    @Expose
    private String moduleCreatedAt;
    @SerializedName("module_updated_at")
    @Expose
    private String moduleUpdatedAt;
    @SerializedName("module_created_by")
    @Expose
    private Integer moduleCreatedBy;
    @SerializedName("module_updated_by")
    @Expose
    private Integer moduleUpdatedBy;
    @SerializedName("module_status")
    @Expose
    private String moduleStatus;
    @SerializedName("application_id")
    @Expose
    private Integer applicationId;
    @SerializedName("application_name")
    @Expose
    private String applicationName;

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getModuleApplicationId() {
        return moduleApplicationId;
    }

    public void setModuleApplicationId(Integer moduleApplicationId) {
        this.moduleApplicationId = moduleApplicationId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleHasSubmodule() {
        return moduleHasSubmodule;
    }

    public void setModuleHasSubmodule(String moduleHasSubmodule) {
        this.moduleHasSubmodule = moduleHasSubmodule;
    }

    public String getModuleCreatedAt() {
        return moduleCreatedAt;
    }

    public void setModuleCreatedAt(String moduleCreatedAt) {
        this.moduleCreatedAt = moduleCreatedAt;
    }

    public String getModuleUpdatedAt() {
        return moduleUpdatedAt;
    }

    public void setModuleUpdatedAt(String moduleUpdatedAt) {
        this.moduleUpdatedAt = moduleUpdatedAt;
    }

    public Integer getModuleCreatedBy() {
        return moduleCreatedBy;
    }

    public void setModuleCreatedBy(Integer moduleCreatedBy) {
        this.moduleCreatedBy = moduleCreatedBy;
    }

    public Integer getModuleUpdatedBy() {
        return moduleUpdatedBy;
    }

    public void setModuleUpdatedBy(Integer moduleUpdatedBy) {
        this.moduleUpdatedBy = moduleUpdatedBy;
    }

    public String getModuleStatus() {
        return moduleStatus;
    }

    public void setModuleStatus(String moduleStatus) {
        this.moduleStatus = moduleStatus;
    }

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}
