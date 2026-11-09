package com.palettereveries.backend.services;

import com.palettereveries.backend.dtos.CreateInquiryDto;
import com.palettereveries.backend.dtos.InquiryDto;
import com.palettereveries.backend.dtos.UpdateInquiryStatusDto;
import com.palettereveries.backend.entities.Artwork;
import com.palettereveries.backend.entities.Inquiry;
import com.palettereveries.backend.errors.ClientErrors;
import com.palettereveries.backend.repositories.ArtworkRepository;
import com.palettereveries.backend.repositories.InquiryRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InquiryService {

   private final InquiryRepository inquiryRepository;
   private final ArtworkRepository artworkRepository;

    public InquiryService(InquiryRepository inquiryRepository,ArtworkRepository artworkRepository){
       this.inquiryRepository = inquiryRepository;
       this.artworkRepository = artworkRepository;
    }

    private InquiryDto toDto(Inquiry inquiry){
        Long artworkId = inquiry.getArtwork() != null ? inquiry.getArtwork().getId() : null;
        return new InquiryDto(inquiry.getId(), inquiry.getSenderName(), inquiry.getSenderEmail(), inquiry.getMessage(), inquiry.getCreatedAt(),inquiry.getStatus(),artworkId);
    }

    public InquiryDto getInquiry(Long id){
        return inquiryRepository.findById(id).map(this::toDto).orElseThrow(()-> ClientErrors.inquiryNotFound(id));
    }

    public List<InquiryDto> getAllInquiries(){
       return inquiryRepository.findAll().stream().map(this::toDto).toList();
    }

    public void deleteInquiry(Long id){
        if(!inquiryRepository.existsById(id)){
            throw ClientErrors.inquiryNotFound(id);
        }
        inquiryRepository.deleteById(id);
    }

    public InquiryDto createInquiry(CreateInquiryDto createInquiryDto){
        Inquiry inquiry = new Inquiry(createInquiryDto.senderName(), createInquiryDto.senderEmail(), createInquiryDto.message());
        if(createInquiryDto.artworkId()!=null) {
            Artwork artwork = artworkRepository.findById(createInquiryDto.artworkId()).orElseThrow(() -> ClientErrors.artworkNotFound(createInquiryDto.artworkId()));
            inquiry.setArtwork(artwork);
        }
        Inquiry saved = inquiryRepository.save(inquiry);
        return toDto(saved);
    }

    public InquiryDto updateInquiryStatus(Long id, UpdateInquiryStatusDto dto){
        Inquiry inquiry = inquiryRepository.findById(id).orElseThrow(()->ClientErrors.inquiryNotFound(id));
        inquiry.setStatus(dto.status());
        return toDto(inquiryRepository.save(inquiry));
    }
}
