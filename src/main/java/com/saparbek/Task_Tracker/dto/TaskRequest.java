package com.saparbek.Task_Tracker.dto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Value;

public class TaskRequest {
    @NotBlank(message = "Description is required")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
