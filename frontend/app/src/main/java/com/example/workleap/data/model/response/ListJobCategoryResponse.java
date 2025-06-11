package com.example.workleap.data.model.response;

import com.example.workleap.data.model.entity.JobCategory;
import com.example.workleap.data.model.entity.JobPost;

import java.util.List;

public class ListJobCategoryResponse {
    String message;

    List<JobCategory> categories;

    public ListJobCategoryResponse() {
    }

    public ListJobCategoryResponse(String message, List<JobCategory> jobCategories) {
        this.message = message;
        this.categories = jobCategories;
    }

    public String getMessage() {
        return message;
    }

    public List<JobCategory> getAllJobCategory() {
        return categories;
    }
}
