package com.palettereveries.backend.services;

import com.palettereveries.backend.dtos.ArtworkDto;
import com.palettereveries.backend.dtos.CreateArtworkDto;
import com.palettereveries.backend.dtos.UpdateArtworkDto;
import com.palettereveries.backend.entities.Artwork;
import com.palettereveries.backend.entities.ArtworkStatus;
import com.palettereveries.backend.errors.ClientErrors;
import com.palettereveries.backend.repositories.ArtworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtworkService {
    private final ArtworkRepository artworkRepository;

    @Autowired
    public ArtworkService(ArtworkRepository artworkRepository){
        this.artworkRepository = artworkRepository;
    }

    private ArtworkDto toDto(Artwork artwork){
        return new ArtworkDto(artwork.getId(), artwork.getTitle(),
                artwork.getDescription(),artwork.getWidthCm(),artwork.getHeightCm(),
                artwork.getMedium(), artwork.getYearCreated(),artwork.getPrice(),artwork.getStatus(),
                artwork.getImageUrls(),artwork.getUploadedAt());
    }

    private Artwork toEntity(CreateArtworkDto createArtwork){
        Artwork artwork = new Artwork(createArtwork.title(), createArtwork.description(),
                createArtwork.widthCm(), createArtwork.heightCm(), createArtwork.medium(),
                createArtwork.yearCreated(),createArtwork.price());
        if(createArtwork.imageUrls()!=null){
        artwork.setImageUrls(createArtwork.imageUrls());}
        return artwork;
    }


    public List<ArtworkDto> getAllArtworks(){
        return artworkRepository.findAll().stream().map(this::toDto).toList();
    }

    public ArtworkDto getArtwork(Long id){
        return artworkRepository.findById(id).map(this::toDto)
                        .orElseThrow(()-> ClientErrors.artworkNotFound(id));
    }

    public ArtworkDto createArtwork(CreateArtworkDto createArtwork){
        if(artworkRepository.findByTitle(createArtwork.title()).isPresent()){
            throw ClientErrors.artworkTitleTaken(createArtwork.title());
        }
        Artwork artwork = toEntity(createArtwork);
        Artwork savedArtwork = artworkRepository.save(artwork);
        return toDto(savedArtwork);
    }

    public ArtworkDto updateArtwork(Long id, UpdateArtworkDto updateArtworkDto) {
        Artwork artwork = artworkRepository.findById(id).orElseThrow(() -> ClientErrors.artworkNotFound(id));
        if (!artwork.getTitle().equals(updateArtworkDto.title())) {
            if (artworkRepository.findByTitle(updateArtworkDto.title()).isPresent()) {
                throw ClientErrors.artworkTitleTaken(updateArtworkDto.title());
            }
        }
        artwork.setTitle(updateArtworkDto.title());
        artwork.setDescription(updateArtworkDto.description());
        artwork.setWidthCm(updateArtworkDto.widthCm());
        artwork.setHeightCm(updateArtworkDto.heightCm());
        artwork.setMedium(updateArtworkDto.medium());
        artwork.setYearCreated(updateArtworkDto.yearCreated());
        artwork.setPrice(updateArtworkDto.price());
        artwork.setImageUrls(updateArtworkDto.imageUrls());
        artwork.setStatus(updateArtworkDto.status());
        return toDto(artworkRepository.save(artwork));
    }

    public void deleteArtwork(Long id){
        if(!artworkRepository.existsById(id)){
            throw ClientErrors.artworkNotFound(id);
        }
        artworkRepository.deleteById(id);
    }

    public List<ArtworkDto> getAvailableArtworks(){
        return artworkRepository.findByStatusOrderByUploadedAtDesc(ArtworkStatus.AVAILABLE)
                .stream().map(this::toDto).toList();
    }

}
