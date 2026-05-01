package com.example.kharidlo2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kharidlo.R;
import com.kharidlo.models.CartManager;
import com.kharidlo.models.Product;

import java.util.List;

/**
 * ProductAdapter — shows product cards with ADD / quantity stepper.
 *
 * When the user taps ADD the button swaps to a +/- stepper and the
 * CartManager singleton is updated immediately.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    public interface OnCartChangedListener {
        /** Called whenever any product quantity changes so the host can refresh totals. */
        void onCartChanged();
    }

    private final Context context;
    private final List<Product> products;
    private final OnCartChangedListener cartListener;
    private final CartManager cart = CartManager.getInstance();

    public ProductAdapter(Context context, List<Product> products,
                          OnCartChangedListener cartListener) {
        this.context      = context;
        this.products     = products;
        this.cartListener = cartListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);

        // Bind text fields
        holder.tvEmoji.setText(product.getEmoji());
        holder.tvName.setText(product.getName());
        holder.tvDesc.setText(product.getDescription());
        holder.tvPrice.setText(product.getPriceFormatted());

        // Sync UI with current cart state
        refreshQtyUI(holder, product);

        // ── ADD button ────────────────────────────────────────────────────────
        holder.btnAdd.setOnClickListener(v -> {
            cart.addProduct(product);
            refreshQtyUI(holder, product);
            if (cartListener != null) cartListener.onCartChanged();
        });

        // ── Stepper: PLUS ─────────────────────────────────────────────────────
        holder.tvPlus.setOnClickListener(v -> {
            cart.addProduct(product);
            refreshQtyUI(holder, product);
            if (cartListener != null) cartListener.onCartChanged();
        });

        // ── Stepper: MINUS ────────────────────────────────────────────────────
        holder.tvMinus.setOnClickListener(v -> {
            cart.removeProduct(product);
            refreshQtyUI(holder, product);
            if (cartListener != null) cartListener.onCartChanged();
        });
    }

    /**
     * Shows the ADD button when qty == 0, or the stepper when qty > 0.
     */
    private void refreshQtyUI(ViewHolder holder, Product product) {
        int qty = cart.getQuantityOf(product);
        if (qty == 0) {
            holder.btnAdd.setVisibility(View.VISIBLE);
            holder.llQtyControls.setVisibility(View.GONE);
        } else {
            holder.btnAdd.setVisibility(View.GONE);
            holder.llQtyControls.setVisibility(View.VISIBLE);
            holder.tvQtyCount.setText(String.valueOf(qty));
        }
    }

    /** Refresh the full list (e.g. after a search filter). */
    public void updateList(List<Product> newList) {
        products.clear();
        products.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() { return products.size(); }

    // ── ViewHolder ────────────────────────────────────────────────────────────
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvEmoji, tvName, tvDesc, tvPrice;
        TextView tvMinus, tvQtyCount, tvPlus;
        LinearLayout llQtyControls;
        Button btnAdd;

        ViewHolder(View itemView) {
            super(itemView);
            tvEmoji       = itemView.findViewById(R.id.tvProductEmoji);
            tvName        = itemView.findViewById(R.id.tvProductName);
            tvDesc        = itemView.findViewById(R.id.tvProductDesc);
            tvPrice       = itemView.findViewById(R.id.tvProductPrice);
            tvMinus       = itemView.findViewById(R.id.tvMinus);
            tvQtyCount    = itemView.findViewById(R.id.tvQtyCount);
            tvPlus        = itemView.findViewById(R.id.tvPlus);
            llQtyControls = itemView.findViewById(R.id.llQtyControls);
            btnAdd        = itemView.findViewById(R.id.btnAddToCart);
        }
    }
}