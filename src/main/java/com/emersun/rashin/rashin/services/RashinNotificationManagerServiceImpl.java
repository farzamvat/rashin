package com.emersun.rashin.rashin.services;

import com.emersun.rashin.rashin.dto.RashinNotificationDto;
import com.emersun.rashin.repositories.NotificationRepository;
import com.emersun.rashin.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class RashinNotificationManagerServiceImpl implements RashinNotificationManagerService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Mono<ResponseEntity<?>> notification(RashinNotificationDto dto) {
        return null;
    }
}
