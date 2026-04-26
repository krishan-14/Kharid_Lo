package com.example.kharidlo2.models;

/**
 * CartItem — wraps a Product with the quantity the user has added to their cart.
 */
public class CartItem {

    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product  = product;
        this.quantity = quantity;
    }

    public Product getProduct()  { return product; }
    public int getQuantity()     { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    /** Total price for this line item (price × quantity). */
    public double getTotalPrice() { return product.getPrice() * quantity; }

    public String getTotalPriceFormatted() {
        double t = getTotalPrice();
        if (t == (int) t) return "₹" + (int) t;
        return "₹" + t;
    }
}
