package com.kharidlo.activities.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.kharidlo.R;

/**
 * LoginActivity — customer login screen.
 *
 * For demo purposes any non-empty email + password combination is accepted.
 * Replace this with your real auth logic (Firebase, REST API, etc.).
 */
public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail    = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        Button btnLogin  = findViewById(R.id.btnLogin);
        TextView tvRegister = findViewById(R.id.tvRegister);

        // ── Login button ──────────────────────────────────────────────────────
        btnLogin.setOnClickListener(v -> {
            String email    = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
            String password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";

            if (TextUtils.isEmpty(email)) {
                etEmail.setError("Please enter your email");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                etPassword.setError("Please enter your password");
                return;
            }

            // Demo auth: accept any credentials
            Toast.makeText(this, "Login successful! Welcome 👋", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("USER_NAME", email.split("@")[0]);   // pass first part of email as name
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        // ── Register link (navigates to same login for demo) ──────────────────
        tvRegister.setOnClickListener(v ->
                Toast.makeText(this, "Registration coming soon!", Toast.LENGTH_SHORT).show()
        );
    }
}
