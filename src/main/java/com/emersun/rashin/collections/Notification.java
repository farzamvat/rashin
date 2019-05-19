package com.emersun.rashin.collections;

import com.emersun.rashin.rashin.dto.RashinNotificationDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Notification {
    @Id
    private String id;
    private Integer sid;
    private String msisdn;
    private String transId;
    private String channel;
    private String shortCode;
    private String keyWord;
    private Integer transStatus;
    private String dateTime;
    private String chargeCode;
    private String basePricePoint;
    private String billedPricePoint;
    private Double eventType;
    private Integer status;
    private Integer validity;
    private String nextRenewalDate;

    public Notification() {
    }

    public Notification(RashinNotificationDto dto) {
        this.sid = dto.getSid();
        this.msisdn = dto.getMsisdn();
        this.transId = dto.getTransId();
        this.channel = dto.getChannel();
        this.shortCode = dto.getShortCode();
        this.keyWord = dto.getKeyWord();
        this.transStatus = dto.getTransStatus();
        this.dateTime = dto.getDateTime();
        this.chargeCode = dto.getChargeCode();
        this.basePricePoint = dto.getBasePricePoint();
        this.billedPricePoint = dto.getBilledPricePoint();
        this.eventType = dto.getEventType();
        this.status = dto.getStatus();
        this.validity = dto.getValidity();
        this.nextRenewalDate = dto.getNextRenewalDate();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Integer getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(Integer transStatus) {
        this.transStatus = transStatus;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getChargeCode() {
        return chargeCode;
    }

    public void setChargeCode(String chargeCode) {
        this.chargeCode = chargeCode;
    }

    public String getBasePricePoint() {
        return basePricePoint;
    }

    public void setBasePricePoint(String basePricePoint) {
        this.basePricePoint = basePricePoint;
    }

    public String getBilledPricePoint() {
        return billedPricePoint;
    }

    public void setBilledPricePoint(String billedPricePoint) {
        this.billedPricePoint = billedPricePoint;
    }

    public Double getEventType() {
        return eventType;
    }

    public void setEventType(Double eventType) {
        this.eventType = eventType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getValidity() {
        return validity;
    }

    public void setValidity(Integer validity) {
        this.validity = validity;
    }

    public String getNextRenewalDate() {
        return nextRenewalDate;
    }

    public void setNextRenewalDate(String nextRenewalDate) {
        this.nextRenewalDate = nextRenewalDate;
    }
}
