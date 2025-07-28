package com.project.demo_api_rest.exception;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorResponse {

    private String message;
    private int statusCode;
    private LocalDateTime timestamp;
    private String errorDetails;

    public ErrorResponse(String message, int statusCode, String errorDetails) {
        this.message = message;
        this.statusCode = statusCode;
        this.errorDetails = errorDetails;
    }
    
}
