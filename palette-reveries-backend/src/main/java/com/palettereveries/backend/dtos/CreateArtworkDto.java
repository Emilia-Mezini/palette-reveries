package com.palettereveries.backend.dtos;

import java.math.BigDecimal;
import java.util.List;

public record CreateArtworkDto(String title, String description, Integer widthCm, Integer heightCm, String medium, Integer yearCreated, BigDecimal price, List<String> imageUrls) {
}
