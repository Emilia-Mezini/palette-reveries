package com.palettereveries.backend.dtos;

import com.palettereveries.backend.entities.Artwork;
import com.palettereveries.backend.entities.InquiryStatus;
import java.time.Instant;

public record InquiryDto(Long id, String senderName, String senderEmail, String message, Instant createdAt,
                         InquiryStatus status, Long artworkId) {
}
