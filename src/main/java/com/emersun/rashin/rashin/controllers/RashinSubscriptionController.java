package com.emersun.rashin.rashin.controllers;

import com.emersun.rashin.dto.ResultDto;
import com.emersun.rashin.rashin.dto.ChargeOtpDto;
import com.emersun.rashin.rashin.services.RashinSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("${api.base.url}" + "/subscription")
public class RashinSubscriptionController {

    @Autowired
    private RashinSubscriptionService rashinSubscriptionService;

    @PostMapping("/push-otp/{mobile}")
    public Mono<ResponseEntity<ResultDto>> pushOtp(@PathVariable String mobile) {
        return rashinSubscriptionService.pushOtp(mobile)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/charge-otp")
    public Mono<ResponseEntity<?>> chargeOtp(@RequestBody ChargeOtpDto chargeOtpDto) {
        return rashinSubscriptionService.chargeOtp(chargeOtpDto)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/unsub/{mobile}")
    public Mono<ResponseEntity<?>> unsub(@PathVariable String mobile) {
        return rashinSubscriptionService.unsub(mobile)
                .map(ResponseEntity::ok);
    }
}
