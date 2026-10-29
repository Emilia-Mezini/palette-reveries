package com.palettereveries.backend.repositories;

import com.palettereveries.backend.entities.AppUser;
import com.palettereveries.backend.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByBuyer(AppUser buyer);
}
