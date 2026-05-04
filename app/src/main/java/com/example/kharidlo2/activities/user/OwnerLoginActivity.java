package com.kharidlo.activities.owner;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.kharidlo.R;
import com.kharidlo.activities.user.WelcomeActivity;

/**
 * OwnerLoginActivity — separate login screen for the shop owner.
 *
 * Demo credentials: owner@kharidlo.com / owner123
 * Change these to real auth (Firebase, server) in production.
 */
public class OwnerLoginActivity extends AppCompatActivity {

    // Hard-coded demo credentials — replace with real auth later
    private static final String DEMO_EMAIL    = "owner@kharidlo.com";
    private static final String DEMO_PASSWORD = "owner123";

    private TextInputEditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_login);

        etEmail    = findViewById(R.id.etOwnerEmail);
        etPassword = findViewById(R.id.etOwnerPassword);
        Button btnLogin        = findViewById(R.id.btnOwnerLogin);
        TextView tvBackToWelcome = findViewById(R.id.tvBackToWelcome);

        // Login
        btnLogin.setOnClickListener(v -> {
            String email    = getText(etEmail);
            String password = getText(etPassword);

            if (TextUtils.isEmpty(email)) {
                etEmail.setError("Enter owner email");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                etPassword.setError("Enter password");
                return;
            }

            // Demo auth check
            if (email.equals(DEMO_EMAIL) && password.equals(DEMO_PASSWORD)) {
                Toast.makeText(this, "Welcome, Owner! 🏪", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, OwnerDashboardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this,
                        "Invalid credentials.\nHint: owner@kharidlo.com / owner123",
                        Toast.LENGTH_LONG).show();
            }
        });

        // Back to Welcome
        tvBackToWelcome.setOnClickListener(v -> {
            startActivity(new Intent(this, WelcomeActivity.class));
            finish();
        });
    }

    private String getText(TextInputEditText f) {
        return f.getText() != null ? f.getText().toString().trim() : "";
    }
}
