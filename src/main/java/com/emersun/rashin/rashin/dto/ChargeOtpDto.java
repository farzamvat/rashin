package com.emersun.rashin.rashin.dto;

import javax.validation.constraints.NotEmpty;

public class ChargeOtpDto {
    @NotEmpty
    private String mobile;
    @NotEmpty
    private String pin;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
