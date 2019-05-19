package com.emersun.rashin.rashin.controllers;

import com.emersun.rashin.rashin.dto.RashinMoDto;
import com.emersun.rashin.rashin.dto.RashinNotificationDto;
import com.emersun.rashin.rashin.services.RashinNotificationManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("${api.base.url}" + "/receive")
public class RashinCallbackController {

    @Autowired
    private RashinNotificationManagerService rashinNotificationManagerService;

    private final static Logger logger = LoggerFactory.getLogger(RashinCallbackController.class);
    @PostMapping("/mo")
    public Mono<ResponseEntity<?>> receiveMO(@RequestBody RashinMoDto rashinMoDto) {
        logger.info("MO REST API input '{}'",rashinMoDto.toString());
        return Mono.empty();
    }

    @PostMapping("/notification")
    public Mono<ResponseEntity<?>> receiveNotfication(@RequestBody RashinNotificationDto rashinNotificationDto) {
        logger.info("Notification REST API input '{}'",rashinNotificationDto.toString());
        return rashinNotificationManagerService.notification(rashinNotificationDto);
    }
}
