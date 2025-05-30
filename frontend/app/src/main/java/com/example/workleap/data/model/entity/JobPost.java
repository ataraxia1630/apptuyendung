package com.example.workleap.data.model.entity;

// JobPost.java

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class JobPost {
    private String id;
    private String companyId;
    private String jobCategoryId;
    private String jobTypeId;
    private String title;
    private String description;
    private String position;
    private String workingAddress;
    private String educationRequirement;
    private String skillRequirement;
    private String responsibility;
    private BigDecimal salary_start;
    private BigDecimal salary_end;
    private String currency;
    private String status;  // OPENING or other values
    private Date applyUntil;
    private List<JobApplied> jobApplied; // Assuming you use this in your API response
    private Date created_at;
    private Date updated_at;
    private Company Company; // Assuming you define the Company model
    private JobType jobType; // Assuming you define the JobType model
    private JobCategory jobCategory; // Assuming you define the JobCategory model
    private List<JobSaved> jobSaved; // Assuming you define JobSaved model

    //Constructor
    public JobPost(String companyId,
                   String jobCategoryId,
                   String jobTypeId,
                   String title,
                   String description,
                   String position,
                   String workingAddress,
                   String educationRequirement,
                   String skillRequirement,
                   String responsibility,
                   BigDecimal salary_start,
                   BigDecimal salary_end,
                   String currency,
                   String status,
                   Date applyUntil) {
        this.companyId = companyId;
        this.jobCategoryId = jobCategoryId;
        this.jobTypeId = jobTypeId;
        this.title = title;
        this.description = description;
        this.position = position;
        this.workingAddress = workingAddress;
        this.educationRequirement = educationRequirement;
        this.skillRequirement = skillRequirement;
        this.responsibility = responsibility;
        this.salary_start = salary_start;
        this.salary_end = salary_end;
        this.currency = currency;
        this.status = status;
        this.applyUntil = applyUntil;
    }
    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getJobCategoryId() {
        return jobCategoryId;
    }

    public void setJobCategoryId(String jobCategoryId) {
        this.jobCategoryId = jobCategoryId;
    }

    public String getJobTypeId() {
        return jobTypeId;
    }

    public void setJobTypeId(String jobTypeId) {
        this.jobTypeId = jobTypeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getWorkingAddress() {
        return workingAddress;
    }

    public void setWorkingAddress(String workingAddress) {
        this.workingAddress = workingAddress;
    }

    public String getEducationRequirement() {
        return educationRequirement;
    }

    public void setEducationRequirement(String educationRequirement) {
        this.educationRequirement = educationRequirement;
    }

    public String getSkillRequirement() {
        return skillRequirement;
    }

    public void setSkillRequirement(String skillRequirement) {
        this.skillRequirement = skillRequirement;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }

    public BigDecimal getSalaryStart() {
        return salary_start;
    }

    public void setSalary_start(BigDecimal salary_start) {
        this.salary_start = salary_start;
    }

    public BigDecimal getSalaryEnd() {
        return salary_end;
    }

    public void setSalaryEnd(BigDecimal salary_end) {
        this.salary_end = salary_end;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getApplyUntil() {
        return applyUntil;
    }

    public void setApplyUntil(Date applyUntil) {
        this.applyUntil = applyUntil;
    }

    public List<JobApplied> getJobApplied() {
        return jobApplied;
    }

    public void setJobApplied(List<JobApplied> jobApplied) {
        this.jobApplied = jobApplied;
    }

    public Date getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Company getCompany() {
        return Company;
    }

    public void setCompany(Company company) {
        this.Company = company;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public JobCategory getJobCategory() {
        return jobCategory;
    }

    public void setJobCategory(JobCategory jobCategory) {
        this.jobCategory = jobCategory;
    }

    public List<JobSaved> getJobSaved() {
        return jobSaved;
    }

    public void setJobSaved(List<JobSaved> jobSaved) {
        this.jobSaved = jobSaved;
    }
}
