package com.example.workleap.data.model.response;

import com.github.mikephil.charting.data.Entry;

public class MonthlyStat {
    private String month;
    private int count;

    public MonthlyStat(){}

    public MonthlyStat(String month, int count)
    {
        this.month = month;
        this.count = count;
    }
    public String getMonth() { return month; }
    public int getCount() { return count; }
}
