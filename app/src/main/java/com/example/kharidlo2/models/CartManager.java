package com.example.kharidlo2.models;

import java.util.ArrayList;
import java.util.List;

/**
 * CartManager — a simple singleton that holds the user's cart in memory.
 *
 * All activities share the same instance, so adding a product on the
 * ProductListActivity is immediately visible in CartActivity.
 *
 * Usage:
 *   CartManager cart = CartManager.getInstance();
 *   cart.addProduct(product);
 *   int count = cart.getTotalItems();
 */
public class CartManager {

    // ── Singleton ─────────────────────────────────────────────────────────────
    private static CartManager instance;

    public static CartManager getInstance() {
        if (instance == null) instance = new CartManager();
        return instance;
    }

    private CartManager() {}   // private constructor

    // ── State ─────────────────────────────────────────────────────────────────
    private final List<CartItem> cartItems = new ArrayList<>();

    // ── Public API ────────────────────────────────────────────────────────────

    /** Add one unit of a product to the cart (or increment if already present). */
    public void addProduct(Product product) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getId() == product.getId()) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
        cartItems.add(new CartItem(product, 1));
    }

    /** Remove one unit; removes the item entirely when quantity reaches 0. */
    public void removeProduct(Product product) {
        for (int i = 0; i < cartItems.size(); i++) {
            CartItem item = cartItems.get(i);
            if (item.getProduct().getId() == product.getId()) {
                if (item.getQuantity() > 1) {
                    item.setQuantity(item.getQuantity() - 1);
                } else {
                    cartItems.remove(i);
                }
                return;
            }
        }
    }

    /** Remove a product completely regardless of quantity. */
    public void removeProductCompletely(Product product) {
        cartItems.removeIf(item -> item.getProduct().getId() == product.getId());
    }

    /** Clear all items from the cart. */
    public void clearCart() { cartItems.clear(); }

    /** Returns the live list of cart items (do not mutate directly). */
    public List<CartItem> getCartItems() { return cartItems; }

    /** Total number of individual units across all items. */
    public int getTotalItems() {
        int total = 0;
        for (CartItem item : cartItems) total += item.getQuantity();
        return total;
    }

    /** Grand total price of all items in the cart. */
    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : cartItems) total += item.getTotalPrice();
        return total;
    }

    public String getTotalPriceFormatted() {
        double t = getTotalPrice();
        if (t == (int) t) return "₹" + (int) t;
        return "₹" + t;
    }

    /** Returns the quantity of a specific product currently in the cart. */
    public int getQuantityOf(Product product) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getId() == product.getId()) return item.getQuantity();
        }
        return 0;
    }
}
