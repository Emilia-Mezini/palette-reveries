package com.palettereveries.backend.entities;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2000)
    private String message;

    @ManyToOne(optional = false)
    private AppUser author;

    @ManyToOne(optional = false)
    private Artwork artwork;

    @Column(updatable = false,nullable = false)
    private Instant createdAt = Instant.now();

    private int rating;

    public Review(){}

    public Review(String message,AppUser author, Artwork artwork,int rating){
        this.message = message;
        this.author = author;
        this.artwork = artwork;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Artwork getArtwork() {
        return artwork;
    }

    public void setArtwork(Artwork artwork) {
        this.artwork = artwork;
    }

    public AppUser getAuthor() {
        return author;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
