package com.palettereveries.backend.dtos;

import java.math.BigDecimal;

public record OrderItemDto(Long id,Long artworkId,String artworkTitle, int quantity, BigDecimal unitPrice) {
}
