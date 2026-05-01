package com.example.kharidlo2.models;

/**
 * Product model — represents a single item sold in the hostel shop.
 * Uses an emoji as a visual placeholder instead of an image URL.
 */
public class Product {

    private int id;
    private String name;
    private String description;
    private double price;
    private String category;
    private String emoji;       // placeholder for image
    private int quantity;       // quantity currently in cart (0 by default)
    private boolean inStock;

    public Product(int id, String name, String description,
                   double price, String category, String emoji) {
        this.id          = id;
        this.name        = name;
        this.description = description;
        this.price       = price;
        this.category    = category;
        this.emoji       = emoji;
        this.quantity    = 0;
        this.inStock     = true;
    }

    // ── Getters ──────────────────────────────────────────────────────────────
    public int    getId()          { return id; }
    public String getName()        { return name; }
    public String getDescription() { return description; }
    public double getPrice()       { return price; }
    public String getCategory()    { return category; }
    public String getEmoji()       { return emoji; }
    public int    getQuantity()    { return quantity; }
    public boolean isInStock()     { return inStock; }

    // ── Setters ──────────────────────────────────────────────────────────────
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setInStock(boolean inStock) { this.inStock = inStock; }

    /** Returns a nicely formatted price string, e.g. "₹10" or "₹12.50" */
    public String getPriceFormatted() {
        if (price == (int) price) return "₹" + (int) price;
        return "₹" + price;
    }
}