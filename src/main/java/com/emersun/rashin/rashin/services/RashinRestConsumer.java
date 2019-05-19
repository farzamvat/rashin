package com.emersun.rashin.rashin.services;

import com.emersun.rashin.rashin.dto.RashinResponseDto;
import reactor.core.publisher.Mono;

public interface RashinRestConsumer {
    Mono<RashinResponseDto> sendOtp(String mobile);

    Mono<RashinResponseDto> unsubscribeUser(String mobile);

    Mono<RashinResponseDto> chargeOtp(Long traceId, String pin);

    Mono<RashinResponseDto> sendSms(String mobile, String message);
}
