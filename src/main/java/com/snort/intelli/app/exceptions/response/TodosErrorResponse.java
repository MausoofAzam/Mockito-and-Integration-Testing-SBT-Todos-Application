package com.snort.intelli.app.exceptions.response;

import java.time.LocalDateTime;


public class TodosErrorResponse {
    private LocalDateTime timestamp;
    private String status;
    private String errorCode;
    private String reason;

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "TodosErrorResponse{" +
                "timestamp=" + timestamp +
                ", status='" + status + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }

    public TodosErrorResponse(LocalDateTime timestamp, String status, String errorCode, String reason) {
        this.timestamp = timestamp;
        this.status = status;
        this.errorCode = errorCode;
        this.reason = reason;
    }
}
