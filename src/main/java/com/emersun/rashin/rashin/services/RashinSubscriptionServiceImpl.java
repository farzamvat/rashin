package com.emersun.rashin.rashin.services;

import com.emersun.rashin.collections.Channel;
import com.emersun.rashin.collections.OtpStatus;
import com.emersun.rashin.collections.User;
import com.emersun.rashin.dto.ResultDto;
import com.emersun.rashin.exceptions.BadRequestException;
import com.emersun.rashin.rashin.dto.ChargeOtpDto;
import com.emersun.rashin.rashin.dto.UserDto;
import com.emersun.rashin.repositories.UserRepository;
import com.emersun.rashin.utils.Messages;
import com.emersun.rashin.utils.messages.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class RashinSubscriptionServiceImpl implements RashinSubscriptionService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RashinRestConsumer rashinRestConsumer;
    @Autowired
    private Messages messages;

    @Override
    public Mono<ResultDto> pushOtp(String mobile) {
        return userRepository.findByMobile(mobile)
                .switchIfEmpty(Mono.fromSupplier(() -> new User(mobile,Channel.OTP)))
                .filter(user -> !user.getHasSubscribe())
                .switchIfEmpty(Mono.error(new BadRequestException("this mobile number has subscribed")))
                .filter(user -> user.getOtpRequestDate() == null ? true :
                        user.getOtpRequestDate().isBefore(LocalDateTime.now(ZoneId.of("Asia/Tehran")).minusMinutes(5)))
                .switchIfEmpty(Mono.error(new BadRequestException("5 minutes must pass after previous try")))
                .flatMap(user ->
                        rashinRestConsumer.sendOtp(mobile)
                            .doOnSuccess(response -> {
                                user.setTraceId(response.getTraceId());
                                user.setOtpRequestDate(LocalDateTime.now(ZoneId.of("Asia/Tehran")));
                                user.setOtpStatus(OtpStatus.PUSHED);
                            })
                            .flatMap(res -> userRepository.save(user)))
                .map(user -> new ResultDto(messages.get(Response.SUCCESS)));
    }

    @Override
    public Mono<UserDto> chargeOtp(ChargeOtpDto chargeOtpDto) {
        return userRepository.findByMobile(chargeOtpDto.getMobile())
                .switchIfEmpty(Mono.error(new BadRequestException("mobile not found")))
                .filter(user -> !user.getHasSubscribe())
                .switchIfEmpty(Mono.error(new BadRequestException("this mobile number has subscribed")))
                .filter(user -> user.getOtpStatus().equals(OtpStatus.PUSHED))
                .switchIfEmpty(Mono.error(new BadRequestException("otp not pushed yet")))
                .flatMap(user -> rashinRestConsumer.chargeOtp(user.getTraceId(),chargeOtpDto.getPin())
                        .map(rashinResponseDto -> {
                            if(rashinResponseDto.getStatus().equals(1)) {
                                user.setOtpStatus(OtpStatus.APPROVED);
                            } else {
                                user.setOtpStatus(OtpStatus.INVALID);
                            }
                            return user;
                        }))
                .flatMap(userRepository::save)
                .map(UserDto::new);
    }

    @Override
    public Mono<ResultDto> unsub(String mobile) {
        return userRepository.findByMobile(mobile)
                .switchIfEmpty(Mono.error(new BadRequestException("mobile not found")))
                .flatMap(user -> rashinRestConsumer.unsubscribeUser(mobile))
                .map(response -> new ResultDto(messages.get(Response.SUCCESS)));
    }
}
