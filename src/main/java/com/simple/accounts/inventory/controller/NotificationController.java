package com.simple.accounts.inventory.controller;

import com.simple.accounts.inventory.model.Notification;
import com.simple.accounts.inventory.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationRepository notificationRepository;

    @GetMapping
    public ResponseEntity<List<Notification>> getUnreadNotifications() {
        return ResponseEntity.ok(notificationRepository.findByReadFalse());
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id) {
        var notificationOpt = notificationRepository.findById(id);
        if (notificationOpt.isPresent()) {
            Notification n = notificationOpt.get();
            n.setRead(true);
            notificationRepository.save(n);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
