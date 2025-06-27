package com.example.workleap.data.model.request;

public class EmailOtpRequest {
    String email;
    String otp;
    public EmailOtpRequest() {};
    public EmailOtpRequest(String email, String otp) {
        this.email = email;
        this.otp = otp;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
