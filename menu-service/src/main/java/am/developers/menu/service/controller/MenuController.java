package am.developers.menu.service.controller;

import am.developers.menu.service.model.MenuItem;
import am.developers.menu.service.repository.MenuRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    private final MenuRepository menuRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public MenuController(MenuRepository menuRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.menuRepository = menuRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping
    public List<MenuItem> getAllMenuItems() {
        return menuRepository.findAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    @PostMapping
    public MenuItem createMenuItem(@RequestBody MenuItem menuItem) {
        MenuItem savedItem = menuRepository.save(menuItem);
        kafkaTemplate.send("menu", "New menu item created: " + savedItem.getName());
        return savedItem;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    @PutMapping("/{id}")
    public MenuItem updateMenuItem(@PathVariable Long id, @RequestBody MenuItem menuItem) {
        MenuItem existingItem = menuRepository.findById(id).orElseThrow();
        existingItem.setName(menuItem.getName());
        existingItem.setDescription(menuItem.getDescription());
        existingItem.setPrice(menuItem.getPrice());
        MenuItem updatedItem = menuRepository.save(existingItem);
        kafkaTemplate.send("menu", "Menu item updated: " + updatedItem.getName());
        return updatedItem;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    @DeleteMapping("/{id}")
    public void deleteMenuItem(@PathVariable Long id) {
        menuRepository.deleteById(id);
        kafkaTemplate.send("menu", "Menu item deleted with id: " + id);
    }

    @GetMapping("/menu")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public String getMenu() {
        // Логика получения меню из базы данных или другого источника
        return "Список блюд на сегодня: пицца, салат, суп";
    }

    @GetMapping("/specials")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_CLIENT')")
    public String getSpecials() {
        // Логика получения специальных предложений
        return "Специальные предложения на сегодня: скидки на напитки";
    }
}
