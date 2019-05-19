package com.emersun.rashin.rashin.dto;

import com.emersun.rashin.collections.Channel;
import com.emersun.rashin.collections.OtpStatus;
import com.emersun.rashin.collections.User;

import java.time.LocalDateTime;

public class UserDto {
    private String id;
    private Boolean hasSubscribed;
    private String mobile;
    private LocalDateTime createdAt;
    private LocalDateTime subscribedAt;
    private LocalDateTime unsubscribedAt;
    private OtpStatus otpStatus;
    private Channel channel;
    private Integer renewals;
    private LocalDateTime otpRequestDate;

    public UserDto() {
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.hasSubscribed = user.getHasSubscribe();
        this.mobile = user.getMobile();
        this.createdAt = user.getCreatedAt();
        this.subscribedAt = user.getSubscribedAt();
        this.unsubscribedAt = user.getUnsubscribedAt();
        this.otpStatus = user.getOtpStatus();
        this.channel = user.getChannel();
        this.renewals = user.getRenewals();
        this.otpRequestDate = user.getOtpRequestDate();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getHasSubscribed() {
        return hasSubscribed;
    }

    public void setHasSubscribed(Boolean hasSubscribed) {
        this.hasSubscribed = hasSubscribed;
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

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Integer getRenewals() {
        return renewals;
    }

    public void setRenewals(Integer renewals) {
        this.renewals = renewals;
    }

    public LocalDateTime getOtpRequestDate() {
        return otpRequestDate;
    }

    public void setOtpRequestDate(LocalDateTime otpRequestDate) {
        this.otpRequestDate = otpRequestDate;
    }
}
