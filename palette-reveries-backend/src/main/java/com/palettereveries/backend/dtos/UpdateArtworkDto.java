package com.palettereveries.backend.dtos;

import com.palettereveries.backend.entities.ArtworkStatus;
import java.math.BigDecimal;
import java.util.List;

public record UpdateArtworkDto(String title, String description, Integer widthCm, Integer heightCm, String medium, Integer yearCreated, BigDecimal price, List<String> imageUrls,
                               ArtworkStatus status) {
}
