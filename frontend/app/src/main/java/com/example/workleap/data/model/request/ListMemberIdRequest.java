package com.example.workleap.data.model.request;

import java.util.List;

public class ListMemberIdRequest {
    List<String> members;

    public ListMemberIdRequest() {}

    public ListMemberIdRequest(List<String> members) {
        this.members = members;
    }

    // Getter
    public List<String> getMembers() { return members; }
    public void setMembers(List<String> members) { this.members = members; }
}
