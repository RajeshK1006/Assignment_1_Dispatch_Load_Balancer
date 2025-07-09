package com.loadBalancer.app.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// this dto class is used to get the response after performing the post requests
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage {
    private String message;
    private String status;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
