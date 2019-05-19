package com.emersun.rashin.rashin.dto;

public class RashinMoDto {
    private String msisdn;
    private String shortCode;
    private String message;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RashinMoDto{" +
                "msisdn='" + msisdn + '\'' +
                ", shortCode='" + shortCode + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
