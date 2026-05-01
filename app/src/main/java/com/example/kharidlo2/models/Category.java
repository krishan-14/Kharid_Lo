package com.example.kharidlo2.models;

/**
 * Category — represents one tile in the home-screen category grid.
 */
public class Category {
    private String name;
    private String emoji;

    public Category(String name, String emoji) {
        this.name  = name;
        this.emoji = emoji;
    }

    public String getName()  { return name; }
    public String getEmoji() { return emoji; }
}