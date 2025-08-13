package com.simple.accounts.inventory.repository;

import com.simple.accounts.inventory.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByReadFalse();
}
