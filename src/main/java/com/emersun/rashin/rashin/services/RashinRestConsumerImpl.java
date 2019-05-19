package com.emersun.rashin.rashin.services;

import com.emersun.rashin.exceptions.BadRequestException;
import com.emersun.rashin.exceptions.InternalServerException;
import com.emersun.rashin.rashin.dto.RashinChargeOtpRequestDto;
import com.emersun.rashin.rashin.dto.RashinOtpUnsubRequestDto;
import com.emersun.rashin.rashin.dto.RashinResponseDto;
import com.emersun.rashin.rashin.dto.RashinSmsRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Service
public class RashinRestConsumerImpl implements RashinRestConsumer {
    private final static Logger logger = LoggerFactory.getLogger(RashinRestConsumerImpl.class);

    @Value("${rashin.server.address}")
    private String serverAddress;
    @Value("${rashin.api.key}")
    private String apiKey;
    @Value("${rashin.service.name}")
    private String serviceName;

    private static ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            logger.info("BidoPin Request: {} {}", clientRequest.method(), clientRequest.url());
            clientRequest.headers().forEach((name, values) -> values.forEach(value -> logger.info("{}={}", name, value)));
            return Mono.just(clientRequest);
        });
    }

    @Override
    public Mono<RashinResponseDto> sendOtp(String mobile) {
        return Mono.fromSupplier(() -> RashinOtpUnsubRequestDto.builder()
                .amount("5000")
                .chargeCode("SERVICESUBCHARGECODE")
                .contentId("100")
                .msisdn(mobile)
                .serviceName(serviceName)
                .traceId(Math.abs(UUID.randomUUID().getMostSignificantBits()))
                .build())
                .flatMap(requestDto -> WebClient.builder()
                        .baseUrl(new StringBuilder(serverAddress).append("/api/otp/push").toString())
                        .filter(logRequest())
                        .build()
                        .post()
                        .header("apiKey",apiKey)
                        .header("Content=Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .body(BodyInserters.fromObject(requestDto))
                        .retrieve()
                        .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new BadRequestException()))
                        .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new InternalServerException()))
                        .bodyToMono(RashinResponseDto.class))
                .doOnSuccess(rashinResponseDto -> logger.info("Rashin SendOtp Response '{}'", rashinResponseDto.toString()))
                .flatMap(rashinResponseDto -> rashinResponseDto.getStatus().equals(1) ?
                        Mono.just(rashinResponseDto) :
                        Mono.error(new BadRequestException(rashinResponseDto.getMessage())));

    }

    @Override
    public Mono<RashinResponseDto> unsubscribeUser(String mobile) {
        return Mono.fromSupplier(() -> RashinOtpUnsubRequestDto.builder()
                .amount("5000")
                .chargeCode("UNSUBCHARGECODE")
                .contentId("120")
                .msisdn(mobile)
                .serviceName(serviceName)
                .traceId(Math.abs(UUID.randomUUID().getMostSignificantBits()))
                .build())
                .flatMap(requestDto -> WebClient.builder()
                        .baseUrl(new StringBuilder(serverAddress).append("/api/otp/push").toString())
                        .filter(logRequest())
                        .build()
                        .post()
                        .header("apiKey",apiKey)
                        .header("Content-Type",MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .body(BodyInserters.fromObject(requestDto))
                        .retrieve()
                        .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new BadRequestException()))
                        .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new InternalServerException()))
                        .bodyToMono(RashinResponseDto.class))
                .doOnSuccess(rashinResponseDto -> logger.info("Rashin SendOtp Response '{}'", rashinResponseDto.toString()))
                .flatMap(rashinResponseDto -> rashinResponseDto.getStatus().equals(2) && rashinResponseDto.getMessage().contains("SUCCESS") ?
                        Mono.just(rashinResponseDto) :
                        Mono.error(new BadRequestException(rashinResponseDto.getMessage())));
    }

    @Override
    public Mono<RashinResponseDto> chargeOtp(Long traceId, String pin) {
        return Mono.fromSupplier(() -> RashinChargeOtpRequestDto.builder()
                .transactionPin(pin)
                .traceId(traceId)
                .build())
                .flatMap(requestDto -> WebClient.builder()
                        .baseUrl(new StringBuilder(serverAddress).append("/api/otp/charge").toString())
                        .filter(logRequest())
                        .build()
                        .post()
                        .header("apiKey",apiKey)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(BodyInserters.fromObject(requestDto))
                        .retrieve()
                        .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new BadRequestException()))
                        .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new InternalServerException()))
                        .bodyToMono(RashinResponseDto.class))
                .doOnSuccess(rashinResponseDto -> logger.info("Rashin SendOtp Response '{}'", rashinResponseDto.toString()));
    }

    @Override
    public Mono<RashinResponseDto> sendSms(String mobile,String message) {
        return Mono.fromSupplier(() -> RashinSmsRequestDto.builder()
                .message(message)
                .msisdn(mobile)
                .build())
                .flatMap(requestDto -> WebClient.builder()
                        .baseUrl(new StringBuilder(serverAddress).append("/api/sms/send").toString())
                        .filter(logRequest())
                        .build()
                        .post()
                        .header("apiKey",apiKey)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(BodyInserters.fromObject(requestDto))
                        .retrieve()
                        .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new BadRequestException()))
                        .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new InternalServerException()))
                        .bodyToMono(RashinResponseDto.class))
                .doOnSuccess(rashinResponseDto -> logger.info("Rashin SendOtp Response '{}'", rashinResponseDto.toString()))
                .flatMap(rashinResponseDto -> rashinResponseDto.getStatus().equals(1) ?
                        Mono.just(rashinResponseDto) :
                        Mono.error(new BadRequestException(rashinResponseDto.getMessage())));
    }
}
