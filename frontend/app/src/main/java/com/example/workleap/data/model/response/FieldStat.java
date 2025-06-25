package com.example.workleap.data.model.response;

public class FieldStat {
    private String fieldId;
    private String name;
    private int count;
    private String percentage;

    public FieldStat(){}
    public String getFieldId() {
        return fieldId;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public String getPercentage() {
        return percentage;
    }
}
