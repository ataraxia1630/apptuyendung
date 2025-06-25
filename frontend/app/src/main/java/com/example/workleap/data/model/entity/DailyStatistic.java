package com.example.workleap.data.model.entity;

import java.util.Date;

public class DailyStatistic {
    private String id;
    private Date date;
    private int userCount;
    private int jobPostCount;
    private int companyCount;
    private int applicationCount;
    private Date created_at;
    private Date updated_at;

    public DailyStatistic() {}

    public DailyStatistic(String id, Date date, int userCount, int jobPostCount, int companyCount, int applicationCount, Date created_at, Date updated_at) {
        this.id = id;
        this.date = date;
        this.userCount = userCount;
        this.jobPostCount = jobPostCount;
        this.companyCount = companyCount;
        this.applicationCount = applicationCount;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    // Getter và Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public int getJobPostCount() {
        return jobPostCount;
    }

    public void setJobPostCount(int jobPostCount) {
        this.jobPostCount = jobPostCount;
    }

    public int getCompanyCount() {
        return companyCount;
    }

    public void setCompanyCount(int companyCount) {
        this.companyCount = companyCount;
    }

    public int getApplicationCount() {
        return applicationCount;
    }

    public void setApplicationCount(int applicationCount) {
        this.applicationCount = applicationCount;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
