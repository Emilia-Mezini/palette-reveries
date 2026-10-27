package com.palettereveries.backend.entities;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class Inquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String senderName;
    @Column(nullable = false)
    private String senderEmail;
    @Column(nullable = false,length = 2000)
    private String message;
    @Column(nullable = false,updatable = false)
    private Instant createdAt = Instant.now();
    @Enumerated(EnumType.STRING)@Column(nullable = false)
    private InquiryStatus status = InquiryStatus.NEW;
    @ManyToOne
    @JoinColumn(name = "artwork_id")
    private Artwork artwork;

    public Inquiry(){}

    public Inquiry(String senderName, String senderEmail, String message){
        this.senderName = senderName;
        this.senderEmail = senderEmail;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public InquiryStatus getStatus() {
        return status;
    }

    public void setStatus(InquiryStatus status) {
        this.status = status;
    }

    public Artwork getArtwork() {
        return artwork;
    }

    public void setArtwork(Artwork artwork) {
        this.artwork = artwork;
    }
}
