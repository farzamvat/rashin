package com.emersun.rashin.rashin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RashinSmsRequestDto {
    private Long traceId;
    private String msisdn;
    private String message;
}
