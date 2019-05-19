package com.emersun.rashin.rashin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RashinOtpUnsubRequestDto {
    private String msisdn;
    private Long traceId;
    private String contentId;
    private String serviceName;
    private String amount;
    private String chargeCode;
    private String description;
}
