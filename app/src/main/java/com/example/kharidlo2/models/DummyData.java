package com.example.kharidlo2.models;

import java.util.ArrayList;
import java.util.List;

/**
 * DummyData — central place for all test/demo data.
 *
 * Replace these with real API calls or a local database later.
 */
public class DummyData {

    // ── Categories ────────────────────────────────────────────────────────────
    public static List<Category> getCategories() {
        List<Category> list = new ArrayList<>();
        list.add(new Category("Snacks",     "🍪"));
        list.add(new Category("Drinks",     "🧃"));
        list.add(new Category("Toiletries", "🧴"));
        list.add(new Category("Stationery", "✏️"));
        list.add(new Category("Instant",    "🍜"));
        list.add(new Category("Dairy",      "🥛"));
        list.add(new Category("Sweets",     "🍬"));
        list.add(new Category("Other",      "📦"));
        return list;
    }

    // ── Products ──────────────────────────────────────────────────────────────
    public static List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();

        // Snacks
        list.add(new Product(1,  "Parle-G Biscuits",   "Classic glucose biscuits",   10,  "Snacks",     "🍪"));
        list.add(new Product(2,  "Kurkure",             "Spicy corn puffs",           20,  "Snacks",     "🌽"));
        list.add(new Product(3,  "Lays Classic",        "Salted potato chips",        20,  "Snacks",     "🥔"));
        list.add(new Product(4,  "Bourbon Biscuits",    "Chocolate cream biscuits",   20,  "Snacks",     "🍫"));
        list.add(new Product(5,  "Monaco Crackers",     "Light salted crackers",      15,  "Snacks",     "🧇"));

        // Drinks
        list.add(new Product(6,  "Frooti Mango",        "Mango fruit drink 200ml",    15,  "Drinks",     "🥭"));
        list.add(new Product(7,  "Thumbs Up 250ml",     "Cola soft drink",            20,  "Drinks",     "🥤"));
        list.add(new Product(8,  "Nimbooz 200ml",       "Lemon flavoured drink",      15,  "Drinks",     "🍋"));
        list.add(new Product(9,  "Red Bull 250ml",      "Energy drink",               115, "Drinks",     "⚡"));
        list.add(new Product(10, "Mineral Water 1L",    "Bisleri packaged water",     20,  "Drinks",     "💧"));

        // Toiletries
        list.add(new Product(11, "Colgate 100g",        "Fresh mint toothpaste",      60,  "Toiletries", "🦷"));
        list.add(new Product(12, "Dove Soap 100g",      "Moisturising beauty bar",    55,  "Toiletries", "🧼"));
        list.add(new Product(13, "Head & Shoulders",    "Anti-dandruff shampoo 80ml", 90,  "Toiletries", "🧴"));
        list.add(new Product(14, "Dettol Hand Wash",    "Antiseptic liquid soap",     75,  "Toiletries", "🫧"));

        // Stationery
        list.add(new Product(15, "Reynolds Pen",        "Blue ballpoint pen",         10,  "Stationery", "🖊️"));
        list.add(new Product(16, "Classmate Notebook",  "200 pages ruled notebook",   60,  "Stationery", "📓"));
        list.add(new Product(17, "Stapler",             "Mini stapler with pins",     45,  "Stationery", "📎"));

        // Instant Food
        list.add(new Product(18, "Maggi 2-Minute",      "Masala flavour noodles",     14,  "Instant",    "🍜"));
        list.add(new Product(19, "Top Ramen Noodles",   "Smoodles chicken flavour",   14,  "Instant",    "🍝"));
        list.add(new Product(20, "Yippee Noodles",      "Magic masala flavour",       14,  "Instant",    "🥢"));

        // Dairy
        list.add(new Product(21, "Amul Milk 500ml",     "Full cream milk pouch",      30,  "Dairy",      "🥛"));
        list.add(new Product(22, "Curd 200g",           "Fresh dahi cup",             25,  "Dairy",      "🍶"));

        // Sweets
        list.add(new Product(23, "Dairy Milk 13g",      "Cadbury milk chocolate",     20,  "Sweets",     "🍫"));
        list.add(new Product(24, "KitKat",              "Wafer chocolate bar",        30,  "Sweets",     "🍬"));

        return list;
    }

    /** Filter products by category name. */
    public static List<Product> getProductsByCategory(String category) {
        List<Product> filtered = new ArrayList<>();
        for (Product p : getAllProducts()) {
            if (p.getCategory().equalsIgnoreCase(category)) filtered.add(p);
        }
        return filtered;
    }

    /** Search products by name (case-insensitive). */
    public static List<Product> searchProducts(String query) {
        List<Product> results = new ArrayList<>();
        String q = query.toLowerCase().trim();
        for (Product p : getAllProducts()) {
            if (p.getName().toLowerCase().contains(q)
                    || p.getCategory().toLowerCase().contains(q)) {
                results.add(p);
            }
        }
        return results;
    }

    /** Returns 6 featured/popular products for the home screen. */
    public static List<Product> getFeaturedProducts() {
        List<Product> all = getAllProducts();
        // Return first 6 as "featured"
        return all.subList(0, Math.min(6, all.size()));
    }

    // ── Sample Requests ───────────────────────────────────────────────────────
    public static List<ProductRequest> getSampleRequests() {
        List<ProductRequest> list = new ArrayList<>();
        list.add(new ProductRequest(1, "Maggi Atta Noodles", "Instant",
                "Please stock the atta variant too!", "Rahul Sharma", "27 Mar 2026"));
        list.add(new ProductRequest(2, "Amul Butter", "Dairy",
                "Need butter for bread", "Priya Singh", "26 Mar 2026"));
        list.add(new ProductRequest(3, "Fevicol", "Stationery",
                "Required for project work", "Aakash Verma", "25 Mar 2026"));

        // Mix up statuses for demo
        list.get(1).setStatus(ProductRequest.Status.APPROVED);
        list.get(2).setStatus(ProductRequest.Status.REJECTED);

        return list;
    }
}