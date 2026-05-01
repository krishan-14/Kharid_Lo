package com.example.kharidlo2.activities.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kharidlo.R;
import com.kharidlo.adapters.ProductAdapter;
import com.kharidlo.models.DummyData;
import com.kharidlo.models.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * SearchActivity — live product search with instant results.
 * The EditText has a TextWatcher that filters products on every keystroke.
 */
public class SearchActivity extends AppCompatActivity {

    private EditText etSearch;
    private TextView tvResultCount, tvClear;
    private ProductAdapter adapter;
    private final List<Product> displayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        etSearch       = findViewById(R.id.etSearch);
        tvResultCount  = findViewById(R.id.tvResultCount);
        tvClear        = findViewById(R.id.tvClear);
        RecyclerView rv = findViewById(R.id.rvSearchResults);

        // Back arrow
        TextView tvBack = findViewById(R.id.tvBack);
        tvBack.setOnClickListener(v -> onBackPressed());

        // Set up RecyclerView with all products initially
        displayList.addAll(DummyData.getAllProducts());
        adapter = new ProductAdapter(this, displayList, null);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        updateCount(displayList.size());

        // Live search
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int st, int c, int a) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().trim();
                tvClear.setVisibility(query.isEmpty() ? View.GONE : View.VISIBLE);

                List<Product> results = query.isEmpty()
                        ? DummyData.getAllProducts()
                        : DummyData.searchProducts(query);

                adapter.updateList(results);
                updateCount(results.size());
            }
        });

        // Clear button
        tvClear.setOnClickListener(v -> etSearch.setText(""));

        // Bottom nav
        setupBottomNav();
    }

    private void updateCount(int count) {
        tvResultCount.setText(count + " product" + (count == 1 ? "" : "s") + " found");
    }

    private void setupBottomNav() {
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.nav_search);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(this, HomeActivity.class));
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