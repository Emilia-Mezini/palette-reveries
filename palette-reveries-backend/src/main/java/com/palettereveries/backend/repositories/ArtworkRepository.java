package com.palettereveries.backend.repositories;

import com.palettereveries.backend.entities.Artwork;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtworkRepository extends JpaRepository<Artwork,Long> {
    Optional<Artwork> findByTitle(String title);
}
