package com.palettereveries.backend.dtos;

import java.time.Instant;

public record ReviewDto(Long id,Long artworkId,Long authorId,String message,String authorName,Instant createdAt,int rating) {
}
