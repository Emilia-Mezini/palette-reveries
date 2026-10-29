package com.palettereveries.backend.repositories;

import com.palettereveries.backend.entities.AppUser;
import com.palettereveries.backend.entities.Artwork;
import com.palettereveries.backend.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findByAuthor(AppUser author);
    List<Review> findByArtwork(Artwork artwork);
}
