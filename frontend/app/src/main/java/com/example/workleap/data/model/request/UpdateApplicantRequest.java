package com.example.workleap.data.model.request;

public class UpdateApplicantRequest {
    private String address;
    private String firstName;
    private String lastName;
    private String profileSummary;
    private byte[] cvFile;

    /*Skill           Skill[]
    Edu             ApplicantEducation[]
    Exp             Experience[]
    InterestedField InterestedField[]
    JobSaved        JobSaved[]
    JobApplied      JobApplied[]
    User            User[]*/

    public UpdateApplicantRequest() {};
    public UpdateApplicantRequest(String address, String firstName, String lastName, String profileSummary, byte[] cvFile)
    {
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profileSummary = profileSummary;
        this.cvFile = cvFile;
    }

    public String getAddress() { return address; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getProfileSummary() { return profileSummary; }
    public byte[] getCvFile() { return cvFile; }
}
