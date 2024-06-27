package am.developers.order.service.controller;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    OrderController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/order")
    public void createOrder(@RequestBody String order) {
        kafkaTemplate.send("orders", order);
    }

    @KafkaListener(topics = "orders", groupId = "order-service")
    public void listenOrder(String order) {
        // Обработка заказа
    }
}
