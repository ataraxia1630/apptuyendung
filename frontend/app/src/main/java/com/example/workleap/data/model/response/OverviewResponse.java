package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.DailyStatistic;

public class OverviewResponse {
    String message;

    DailyStatistic data;

    public OverviewResponse() {
    }

    public OverviewResponse(String message, DailyStatistic dailyStatistic) {
        this.message = message;
        this.data = dailyStatistic;
    }

    public String getMessage() {
        return message;
    }

    public DailyStatistic getData() {
        return data;
    }
}
