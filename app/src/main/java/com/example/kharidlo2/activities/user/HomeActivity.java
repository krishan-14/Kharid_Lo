package com.example.kharidlo2.activities.user;


import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kharidlo.R;
import com.kharidlo.adapters.CategoryAdapter;
import com.kharidlo.adapters.ProductAdapter;
import com.kharidlo.models.CartManager;
import com.kharidlo.models.DummyData;

/**
 * HomeActivity — main landing page for the customer.
 *
 * Shows:
 *  • Greeting with user name
 *  • Clickable search bar → SearchActivity
 *  • 4-column category grid
 *  • Horizontal featured products list
 *  • Cart badge (live item count)
 *  • Bottom navigation
 */
public class HomeActivity extends AppCompatActivity {

    private TextView tvGreeting, tvCartBadge;
    private final CartManager cart = CartManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvGreeting  = findViewById(R.id.tvGreeting);
        tvCartBadge = findViewById(R.id.tvCartBadge);

        // Personalise greeting from login intent
        String userName = getIntent().getStringExtra("USER_NAME");
        if (userName != null && !userName.isEmpty()) {
            tvGreeting.setText("Hello, " + capitalise(userName) + "! 👋");
        }

        setupCategoryGrid();
        setupFeaturedProducts();
        setupSearchBar();
        setupBottomNav();
    }

    // Refresh cart badge every time we return to this screen
    @Override
    protected void onResume() {
        super.onResume();
        updateCartBadge();
    }

    // ── Category grid ─────────────────────────────────────────────────────────
    private void setupCategoryGrid() {
        RecyclerView rvCategories = findViewById(R.id.rvCategories);
        rvCategories.setLayoutManager(new GridLayoutManager(this, 4));

        CategoryAdapter adapter = new CategoryAdapter(this, DummyData.getCategories(), category -> {
            // Open product list for the selected category
            Intent intent = new Intent(this, ProductListActivity.class);
            intent.putExtra("CATEGORY_NAME", category.getName());
            intent.putExtra("CATEGORY_EMOJI", category.getEmoji());
            startActivity(intent);
        });
        rvCategories.setAdapter(adapter);
    }

    // ── Featured products (vertical list) ────────────────────────────────────
    private void setupFeaturedProducts() {
        RecyclerView rvFeatured = findViewById(R.id.rvFeatured);
        rvFeatured.setLayoutManager(new LinearLayoutManager(this));

        ProductAdapter adapter = new ProductAdapter(this, DummyData.getFeaturedProducts(),
                this::updateCartBadge);
        rvFeatured.setAdapter(adapter);
    }

    // ── Search bar ────────────────────────────────────────────────────────────
    private void setupSearchBar() {
        LinearLayout llSearch = findViewById(R.id.llSearch);
        llSearch.setOnClickListener(v ->
                startActivity(new Intent(this, SearchActivity.class)));

        // Cart badge tap → open cart
        tvCartBadge.setOnClickListener(v ->
                startActivity(new Intent(this, CartActivity.class)));
    }

    // ── Bottom Navigation ─────────────────────────────────────────────────────
    private void setupBottomNav() {
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.nav_home);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_search) {
                startActivity(new Intent(this, SearchActivity.class));
                return true;
            } else if (id == R.id.nav_cart) {
                startActivity(new Intent(this, CartActivity.class));
                return true;
            } else if (id == R.id.nav_requests) {
                startActivity(new Intent(this, MyRequestsActivity.class));
                return true;
            }
            return true; // nav_home — already here
        });
    }

    // ── Helpers ───────────────────────────────────────────────────────────────
    private void updateCartBadge() {
        int count = cart.getTotalItems();
        tvCartBadge.setText(count > 0 ? String.valueOf(count) : "🛒");
    }

    private String capitalise(String s) {
        if (s == null || s.isEmpty()) return s;
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}