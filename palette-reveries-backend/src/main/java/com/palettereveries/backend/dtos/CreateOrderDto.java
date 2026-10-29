package com.palettereveries.backend.dtos;

import java.util.List;

public record CreateOrderDto(List<CreateOrderItemDto> items, String shippingAddress) {
}
