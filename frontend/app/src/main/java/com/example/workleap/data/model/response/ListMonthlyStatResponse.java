package com.example.workleap.data.model.response;

import java.util.List;

public class ListMonthlyStatResponse {
    private String message;
    private List<MonthlyStat> userGrowth;
    private List<MonthlyStat> jobGrowth;
    public ListMonthlyStatResponse() {
    }

    public ListMonthlyStatResponse(String message, List<MonthlyStat> userGrowth,  List<MonthlyStat> jobGrowth) {
        this.message = message;
        this.userGrowth = userGrowth;
        this.jobGrowth = jobGrowth;
    }

    public List<MonthlyStat> getUserGrowth() {
        return userGrowth;
    }
    public List<MonthlyStat> getJobGrowth() {
        return jobGrowth;
    }
    public String getMessage(){ return message;}
}
