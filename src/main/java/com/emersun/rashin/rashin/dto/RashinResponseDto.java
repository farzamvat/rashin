package com.emersun.rashin.rashin.dto;

public class RashinResponseDto {
    private Integer status;
    private String message;
    private Long traceId;

    public RashinResponseDto() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTraceId() {
        return traceId;
    }

    public void setTraceId(Long traceId) {
        this.traceId = traceId;
    }

    @Override
    public String toString() {
        return "RashinResponseDto{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", traceId=" + traceId +
                '}';
    }
}
