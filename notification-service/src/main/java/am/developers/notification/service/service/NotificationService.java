package am.developers.notification.service.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @KafkaListener(topics = "orders", groupId = "notification-service")
    public void listenOrder(String message) {
        // Логика уведомления о заказе
        System.out.println("Received message in group 'notification-service': " + message);
    }

    @KafkaListener(topics = "menu", groupId = "notification-service")
    public void listenMenu(String message) {
        // Логика уведомления о изменениях в меню
        System.out.println("Received message in group 'notification-service': " + message);
    }

}
