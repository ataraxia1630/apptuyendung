package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.CV;
import com.example.workleap.data.model.entity.DailyStatistic;

public class OverviewResponse {
    String message;

    DailyStatistic dailyStatistic;

    public OverviewResponse() {
    }

    public OverviewResponse(String message, DailyStatistic dailyStatistic) {
        this.message = message;
        this.dailyStatistic = dailyStatistic;
    }

    public String getMessage() {
        return message;
    }

    public DailyStatistic getDailyStatistic() {
        return dailyStatistic;
    }
}
