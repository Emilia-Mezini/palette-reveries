package com.palettereveries.backend.services;

import com.palettereveries.backend.dtos.CreateReviewDto;
import com.palettereveries.backend.dtos.ReviewDto;
import com.palettereveries.backend.entities.AppUser;
import com.palettereveries.backend.entities.Artwork;
import com.palettereveries.backend.entities.Review;
import com.palettereveries.backend.errors.ClientErrors;
import com.palettereveries.backend.repositories.AppUserRepository;
import com.palettereveries.backend.repositories.ArtworkRepository;
import com.palettereveries.backend.repositories.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ArtworkRepository artworkRepository;
    private final AppUserRepository appUserRepository;

    public ReviewService(ReviewRepository reviewRepository, ArtworkRepository artworkRepository,AppUserRepository appUserRepository){
        this.reviewRepository = reviewRepository;
        this.artworkRepository = artworkRepository;
        this.appUserRepository = appUserRepository;
    }

    private ReviewDto toDto(Review review){
        return new ReviewDto(review.getId(),review.getArtwork().getId(),
                review.getAuthor().getId(),review.getMessage(),
                review.getAuthor().getUsername(), review.getCreatedAt(),review.getRating());
    }

    public List<ReviewDto> getReviewsForArtwork(Long artworkId){
        Artwork artwork = artworkRepository.findById(artworkId).orElseThrow(()-> ClientErrors.artworkNotFound(artworkId));
        return reviewRepository.findByArtwork(artwork).stream().map(this::toDto).toList();
    }

    public void deleteReview(Long id){
        if(!reviewRepository.existsById(id)){
            throw ClientErrors.reviewNotFound(id);
        }
        reviewRepository.deleteById(id);
    }

    public List<ReviewDto> getAllReviews(){
        return reviewRepository.findAll().stream().map(this::toDto).toList();
    }

    public ReviewDto createReview(CreateReviewDto createReviewDto, Long currentUserId){
        AppUser author = appUserRepository.findById(currentUserId).orElseThrow(()->ClientErrors.userNotFound(currentUserId));
        Artwork artwork = artworkRepository.findById(createReviewDto.artworkId()).orElseThrow(()->ClientErrors.artworkNotFound(createReviewDto.artworkId()));
        if(createReviewDto.rating()<1||createReviewDto.rating()>5){
            throw ClientErrors.invalidRating(createReviewDto.rating());
        }
        Review review = new Review(createReviewDto.message(),createReviewDto.rating());
        review.setAuthor(author);
        review.setArtwork(artwork);
        Review saved = reviewRepository.save(review);
        return toDto(saved);
    }

}
