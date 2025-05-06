package com.saparbek.Task_Tracker.dto;

public class ValidationErrorResponse {
    private String field;
    private String message;
    private int status;

    public ValidationErrorResponse(String field, String message, int status) {
        this.field = field;
        this.message = message;
        this.status = status;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
