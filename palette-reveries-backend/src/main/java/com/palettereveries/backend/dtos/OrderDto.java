package com.palettereveries.backend.dtos;

import com.palettereveries.backend.entities.OrderStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderDto(Long id, AppUserDto buyer, List<OrderItemDto> items, OrderStatus status, Instant createdAt, String shippingAddress,
                       BigDecimal total) {
}
