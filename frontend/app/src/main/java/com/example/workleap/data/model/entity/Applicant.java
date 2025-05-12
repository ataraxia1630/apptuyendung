package com.example.workleap.data.model.entity;

import java.util.Date;

public class Applicant {
    private String id;
    private String address;
    private String firstName;
    private String lastName;
    private String profileSummary;
    private byte[] cvFile;
    private Date createdAt;
    private Date updatedAt;

    /*Skill           Skill[]
    Edu             ApplicantEducation[]
    Exp             Experience[]
    InterestedField InterestedField[]
    JobSaved        JobSaved[]
    JobApplied      JobApplied[]
    User            User[]*/

    public Applicant() {}

    public Applicant(String id, String address, String firstName, String lastName, String profileSummary, byte[] cvFile, Date createdAt, Date updatedAt) {
        this.id = id;
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profileSummary = profileSummary;
        this.cvFile = cvFile;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getProfileSummary() { return profileSummary; }
    public void setProfileSummary(String profileSummary) { this.profileSummary = profileSummary; }
    public byte[] getCvFile() { return cvFile; }
    public void setCvFile(byte[] cvFile) { this.cvFile = cvFile; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "Applicant{" +
                "id='" + id + '\'' +
                ", address='" + address + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", profileSummary='" + profileSummary + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
