package com.gamereviewhub.model;

import jakarta.persistence.*;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String genre;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String createdBy;

    public Game() {}

    public Game(Long id, String title, String genre, String description, String createdBy) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.createdBy = createdBy;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
}
