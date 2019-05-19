package com.emersun.rashin.repositories;

import com.emersun.rashin.collections.Notification;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface NotificationRepository extends ReactiveMongoRepository<Notification,String> {
}
