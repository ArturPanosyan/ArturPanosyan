package am.developers.menu.service.repository;

import am.developers.menu.service.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.*;

public interface MenuRepository extends JpaRepository<MenuItem, Long> {
}
