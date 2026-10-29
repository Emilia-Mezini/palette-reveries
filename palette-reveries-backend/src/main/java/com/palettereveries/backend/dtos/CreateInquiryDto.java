package com.palettereveries.backend.dtos;

import com.palettereveries.backend.entities.Artwork;

public record CreateInquiryDto(String senderName, String senderEmail, String message,Long artworkId) {
}
