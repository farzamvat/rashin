package com.emersun.rashin.collections;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Document
public class User {
    @Id
    private String id;
    private Boolean hasSubscribe = false;
    private String mobile;
    private LocalDateTime createdAt;
    private Long traceId;
    private String deviceToken;
    private String chargePin;
    private LocalDateTime subscribedAt;
    private LocalDateTime unsubscribedAt;
    private OtpStatus otpStatus = OtpStatus.NOT_PUSHED_YET;
    private Channel channel;
    private Integer renewals;
    private LocalDateTime otpRequestDate;
    @DBRef
    private AgentApplication agentApplication;

    public User () {

    }

    public User (String mobile,Channel channel) {
        this.mobile = mobile;
        this.createdAt = LocalDateTime.now(ZoneId.of("Asia/Tehran"));
        this.channel = channel;
    }

    public AgentApplication getAgentApplication() {
        return agentApplication;
    }

    public void setAgentApplication(AgentApplication agentApplication) {
        this.agentApplication = agentApplication;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public LocalDateTime getOtpRequestDate() {
        return otpRequestDate;
    }

    public void setOtpRequestDate(LocalDateTime otpRequestDate) {
        this.otpRequestDate = otpRequestDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getHasSubscribe() {
        return hasSubscribe;
    }

    public void setHasSubscribe(Boolean hasSubscribe) {
        this.hasSubscribe = hasSubscribe;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getTraceId() {
        return traceId;
    }

    public void setTraceId(Long traceId) {
        this.traceId = traceId;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getChargePin() {
        return chargePin;
    }

    public void setChargePin(String chargePin) {
        this.chargePin = chargePin;
    }

    public LocalDateTime getSubscribedAt() {
        return subscribedAt;
    }

    public void setSubscribedAt(LocalDateTime subscribedAt) {
        this.subscribedAt = subscribedAt;
    }

    public LocalDateTime getUnsubscribedAt() {
        return unsubscribedAt;
    }

    public void setUnsubscribedAt(LocalDateTime unsubscribedAt) {
        this.unsubscribedAt = unsubscribedAt;
    }

    public OtpStatus getOtpStatus() {
        return otpStatus;
    }

    public void setOtpStatus(OtpStatus otpStatus) {
        this.otpStatus = otpStatus;
    }

    public Integer getRenewals() {
        return renewals;
    }

    public void setRenewals(Integer renewals) {
        this.renewals = renewals;
    }
}
