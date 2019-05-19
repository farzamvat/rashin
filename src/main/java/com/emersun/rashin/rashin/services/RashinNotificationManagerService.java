package com.emersun.rashin.rashin.services;

import com.emersun.rashin.rashin.dto.RashinNotificationDto;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface RashinNotificationManagerService {
    Mono<ResponseEntity<?>> notification(RashinNotificationDto dto);
}
