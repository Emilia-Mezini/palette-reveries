package com.palettereveries.backend.dtos;

import com.palettereveries.backend.entities.OrderStatus;

public record UpdateOrderStatusDto(OrderStatus status) {
}
