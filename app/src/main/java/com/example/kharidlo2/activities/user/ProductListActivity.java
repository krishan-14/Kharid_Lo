package com.kharidlo.activities.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kharidlo.R;
import com.kharidlo.adapters.ProductAdapter;
import com.kharidlo.models.CartManager;
import com.kharidlo.models.DummyData;

/**
 * ProductListActivity — shows all products for a specific category.
 *
 * Receives:
 *   CATEGORY_NAME  (String) — e.g. "Snacks"
 *   CATEGORY_EMOJI (String) — e.g. "🍪"
 */
public class ProductListActivity extends AppCompatActivity {

    private TextView tvCartBadge;
    private final CartManager cart = CartManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        // Receive category data from HomeActivity
        String categoryName  = getIntent().getStringExtra("CATEGORY_NAME");
        String categoryEmoji = getIntent().getStringExtra("CATEGORY_EMOJI");
        if (categoryName == null) categoryName = "All";

        // Header
        TextView tvTitle = findViewById(R.id.tvCategoryTitle);
        tvTitle.setText(categoryEmoji != null ? categoryEmoji + "  " + categoryName : categoryName);

        tvCartBadge = findViewById(R.id.tvCartBadge);
        updateCartBadge();

        // Back navigation
        TextView tvBack = findViewById(R.id.tvBack);
        tvBack.setOnClickListener(v -> onBackPressed());

        // Cart icon tap
        tvCartBadge.setOnClickListener(v ->
                startActivity(new Intent(this, CartActivity.class)));

        // Products RecyclerView
        RecyclerView rv = findViewById(R.id.rvProducts);
        rv.setLayoutManager(new LinearLayoutManager(this));

        ProductAdapter adapter = new ProductAdapter(
                this,
                DummyData.getProductsByCategory(categoryName),
                this::updateCartBadge   // refresh badge whenever cart changes
        );
        rv.setAdapter(adapter);

        // Bottom nav
        setupBottomNav(categoryName);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartBadge();
    }

    private void updateCartBadge() {
        int count = cart.getTotalItems();
        tvCartBadge.setText(count > 0 ? "🛒 " + count : "🛒");
    }

    private void setupBottomNav(String categoryName) {
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(this, HomeActivity.class));
                return true;
            } else if (id == R.id.nav_search) {
                startActivity(new Intent(this, SearchActivity.class));
                return true;
            } else if (id == R.id.nav_cart) {
                startActivity(new Intent(this, CartActivity.class));
                return true;
            } else if (id == R.id.nav_requests) {
                startActivity(new Intent(this, MyRequestsActivity.class));
                return true;
            }
            return true;
        });
    }
}