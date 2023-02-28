package com.vnrseeds.samadhan.pojo;

public class SoftwareListPojo {
    String application_id,application_name;
    String application_icon;

    public SoftwareListPojo() {
    }

    public SoftwareListPojo(String application_id, String application_name, String application_icon) {
        this.application_id = application_id;
        this.application_name = application_name;
        this.application_icon = application_icon;
    }

    public String getApplication_id() {
        return application_id;
    }

    public void setApplication_id(String application_id) {
        this.application_id = application_id;
    }

    public String getApplication_name() {
        return application_name;
    }

    public void setApplication_name(String application_name) {
        this.application_name = application_name;
    }

    public String getApplication_icon() {
        return application_icon;
    }

    public void setApplication_icon(String application_icon) {
        this.application_icon = application_icon;
    }
}
