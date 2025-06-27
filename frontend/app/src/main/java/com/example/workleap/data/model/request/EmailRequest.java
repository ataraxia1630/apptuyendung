package com.example.workleap.data.model.request;

import android.provider.ContactsContract;

public class EmailRequest {
    String email;
    public EmailRequest() {};
    public EmailRequest(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
