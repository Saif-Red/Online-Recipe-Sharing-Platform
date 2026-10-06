package com.recipehub.model;

import java.time.LocalDateTime;

public class Recipe {
    private int id;
    private int authorId;
    private String authorName;
    private String title;
    private String description;
    private int prepMinutes;
    private int cookMinutes;
    private int servings;
    private Difficulty difficulty;
    private String cuisine;
    private String imageUrl;
    private RecipeStatus status;
    private int views;
    private double averageRating;
    private LocalDateTime createdAt;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getAuthorId() { return authorId; }
    public void setAuthorId(int authorId) { this.authorId = authorId; }
    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getPrepMinutes() { return prepMinutes; }
    public void setPrepMinutes(int prepMinutes) { this.prepMinutes = prepMinutes; }
    public int getCookMinutes() { return cookMinutes; }
    public void setCookMinutes(int cookMinutes) { this.cookMinutes = cookMinutes; }
    public int getServings() { return servings; }
    public void setServings(int servings) { this.servings = servings; }
    public Difficulty getDifficulty() { return difficulty; }
    public void setDifficulty(Difficulty difficulty) { this.difficulty = difficulty; }
    public String getCuisine() { return cuisine; }
    public void setCuisine(String cuisine) { this.cuisine = cuisine; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public RecipeStatus getStatus() { return status; }
    public void setStatus(RecipeStatus status) { this.status = status; }
    public int getViews() { return views; }
    public void setViews(int views) { this.views = views; }
    public double getAverageRating() { return averageRating; }
    public void setAverageRating(double averageRating) { this.averageRating = averageRating; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
