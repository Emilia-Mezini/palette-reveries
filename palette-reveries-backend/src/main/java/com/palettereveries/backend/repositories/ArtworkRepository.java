package com.palettereveries.backend.repositories;

import com.palettereveries.backend.entities.Artwork;
import com.palettereveries.backend.entities.ArtworkStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArtworkRepository extends JpaRepository<Artwork,Long> {
    Optional<Artwork> findByTitle(String title);
    List<Artwork> findByStatusOrderByUploadedAtDesc(ArtworkStatus status);
}
