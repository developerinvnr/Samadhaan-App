package com.vnrseeds.samadhan.parser.brandmodelparser;

public class MonitorModelsData {
    private Integer bmId;
    private String bmName;

    public MonitorModelsData(Integer bmId, String bmName) {
        this.bmId = bmId;
        this.bmName = bmName;
    }

    public Integer getBmId() {
        return bmId;
    }

    public void setBmId(Integer bmId) {
        this.bmId = bmId;
    }

    public String getBmName() {
        return bmName;
    }

    public void setBmName(String bmName) {
        this.bmName = bmName;
    }
}
