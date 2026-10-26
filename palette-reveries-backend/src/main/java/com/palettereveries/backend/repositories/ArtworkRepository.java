package com.palettereveries.backend.repositories;

import com.palettereveries.backend.entities.Artwork;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ArtworkRepository extends CrudRepository<Artwork,Long> {

    Optional<Artwork> findByTitle(String title);
}
