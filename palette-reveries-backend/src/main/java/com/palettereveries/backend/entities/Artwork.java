package com.palettereveries.backend.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Artwork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false)
    private String title;
    @Column(length=2000)
    private String description;
    private Integer widthCm;
    private Integer heightCm;
    private String medium;
    private Integer yearCreated;
    @Column(nullable = false)
    private BigDecimal price;
    @Column (nullable = false,updatable = false)
    private Instant uploadedAt = Instant.now();
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ArtworkStatus status = ArtworkStatus.AVAILABLE;
    @ElementCollection
    @CollectionTable(
            name = "artwork_image_urls",
            joinColumns = @JoinColumn(name = "artwork_id")
    )
    @Column(name = "url")
    private List<String> imageUrls = new ArrayList<>();

    public Artwork(){}

    public Artwork(String title, String description, Integer widthCm, Integer heightCm, String medium, Integer yearCreated, BigDecimal price){
        this.title = title;
        this.description = description;
        this.widthCm = widthCm;
        this.heightCm = heightCm;
        this.medium = medium;
        this.yearCreated = yearCreated;
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getWidthCm() {
        return widthCm;
    }

    public void setWidthCm(Integer widthCm) {
        this.widthCm = widthCm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public Integer getYearCreated() {
        return yearCreated;
    }

    public void setYearCreated(Integer yearCreated) {
        this.yearCreated = yearCreated;
    }

    public ArtworkStatus getStatus() {
        return status;
    }

    public void setStatus(ArtworkStatus status) {
        this.status = status;
    }

    public Integer getHeightCm() {
        return heightCm;
    }

    public void setHeightCm(Integer heightCm) {
        this.heightCm = heightCm;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public Instant getUploadedAt() {
        return uploadedAt;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
