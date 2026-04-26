package com.example.kharidlo2.activities.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.example.kharidlo2.R;
import com.example.kharidlo2.models.CartManager;

/**
 * PaymentActivity — lets the user choose UPI or Cash on Delivery and confirm the order.
 *
 * Receives address details from AddressActivity via Intent extras.
 * On success clears the cart and navigates to ConfirmationActivity.
 */
public class PaymentActivity extends AppCompatActivity {

    private RadioButton rbUPI, rbCash;
    private TextInputLayout tilUpiId;
    private TextInputEditText etUpiId;
    private final CartManager cart = CartManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        rbUPI    = findViewById(R.id.rbUPI);
        rbCash   = findViewById(R.id.rbCash);
        tilUpiId = findViewById(R.id.tilUpiId);
        etUpiId  = findViewById(R.id.etUpiId);

        // Show order total
        TextView tvTotal = findViewById(R.id.tvOrderTotal);
        tvTotal.setText(cart.getTotalPriceFormatted());

        TextView tvItemCount = findViewById(R.id.tvItemCount);
        int count = cart.getTotalItems();
        tvItemCount.setText(count + " item" + (count == 1 ? "" : "s"));

        // Back
        TextView tvBack = findViewById(R.id.tvBack);
        tvBack.setOnClickListener(v -> onBackPressed());

        // Payment method toggle
        LinearLayout llUPI  = findViewById(R.id.llUPI);
        LinearLayout llCash = findViewById(R.id.llCash);

        llUPI.setOnClickListener(v  -> selectUPI());
        rbUPI.setOnClickListener(v  -> selectUPI());
        llCash.setOnClickListener(v -> selectCash());
        rbCash.setOnClickListener(v -> selectCash());

        // Default: UPI selected
        selectUPI();

        // Pay button
        Button btnPay = findViewById(R.id.btnPayNow);
        btnPay.setOnClickListener(v -> processPayment());
    }

    private void selectUPI() {
        rbUPI.setChecked(true);
        rbCash.setChecked(false);
        tilUpiId.setVisibility(View.VISIBLE);
    }

    private void selectCash() {
        rbCash.setChecked(true);
        rbUPI.setChecked(false);
        tilUpiId.setVisibility(View.GONE);
    }

    private void processPayment() {
        // Validate UPI ID if UPI selected
        if (rbUPI.isChecked()) {
            String upiId = etUpiId.getText() != null ? etUpiId.getText().toString().trim() : "";
            if (TextUtils.isEmpty(upiId) || !upiId.contains("@")) {
                etUpiId.setError("Enter a valid UPI ID (e.g. name@upi)");
                return;
            }
        }

        // Simulate payment processing
        Toast.makeText(this, "Processing payment…", Toast.LENGTH_SHORT).show();

        // Generate a simple order ID
        String orderId = "KL" + System.currentTimeMillis() % 100000;

        // Clear cart after successful payment
        cart.clearCart();

        // Go to confirmation
        Intent intent = new Intent(this, ConfirmationActivity.class);
        intent.putExtra("ORDER_ID", orderId);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
