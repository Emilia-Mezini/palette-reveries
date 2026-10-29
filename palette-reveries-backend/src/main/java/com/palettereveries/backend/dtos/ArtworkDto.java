package com.palettereveries.backend.dtos;

import com.palettereveries.backend.entities.ArtworkStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record ArtworkDto(Long id, String title, String description, Integer widthCm, Integer heightCm, String medium, Integer yearCreated,
                         BigDecimal price, ArtworkStatus status, List<String> imageUrls, Instant uploadedAt) {}
