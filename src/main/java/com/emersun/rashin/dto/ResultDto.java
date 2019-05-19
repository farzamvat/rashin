package com.emersun.rashin.dto;

public class ResultDto { private String message;

    public ResultDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
