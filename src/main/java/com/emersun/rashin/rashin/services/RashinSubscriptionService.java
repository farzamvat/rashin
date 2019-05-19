package com.emersun.rashin.rashin.services;

import com.emersun.rashin.dto.ResultDto;
import com.emersun.rashin.rashin.dto.ChargeOtpDto;
import com.emersun.rashin.rashin.dto.UserDto;
import reactor.core.publisher.Mono;

public interface RashinSubscriptionService {
    Mono<ResultDto> pushOtp(String mobile);

    Mono<UserDto> chargeOtp(ChargeOtpDto chargeOtpDto);

    Mono<ResultDto> unsub(String mobile);
}
