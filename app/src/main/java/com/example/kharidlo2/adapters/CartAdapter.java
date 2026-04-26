package com.example.kharidlo2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kharidlo2.R;
import com.example.kharidlo2.models.CartItem;
import com.example.kharidlo2.models.CartManager;

import java.util.List;

/**
 * CartAdapter — shows cart items with +/- quantity controls.
 * Notifies the host activity whenever a quantity changes via OnCartUpdatedListener.
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    public interface OnCartUpdatedListener {
        void onCartUpdated();   // called after every qty change so totals can refresh
    }

    private final Context context;
    private final List<CartItem> cartItems;
    private final CartManager cart = CartManager.getInstance();
    private final OnCartUpdatedListener listener;

    public CartAdapter(Context context, List<CartItem> cartItems,
                       OnCartUpdatedListener listener) {
        this.context   = context;
        this.cartItems = cartItems;
        this.listener  = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem item = cartItems.get(position);

        holder.tvEmoji.setText(item.getProduct().getEmoji());
        holder.tvName.setText(item.getProduct().getName());
        holder.tvLinePrice.setText(item.getTotalPriceFormatted());
        holder.tvQty.setText(String.valueOf(item.getQuantity()));

        // Increase quantity
        holder.tvPlus.setOnClickListener(v -> {
            cart.addProduct(item.getProduct());
            notifyItemChanged(holder.getAdapterPosition());
            if (listener != null) listener.onCartUpdated();
        });

        // Decrease quantity; remove item if it reaches zero
        holder.tvMinus.setOnClickListener(v -> {
            cart.removeProduct(item.getProduct());
            int newQty = cart.getQuantityOf(item.getProduct());
            if (newQty == 0) {
                // Item fully removed — rebuild list from CartManager
                cartItems.clear();
                cartItems.addAll(cart.getCartItems());
                notifyDataSetChanged();
            } else {
                notifyItemChanged(holder.getAdapterPosition());
            }
            if (listener != null) listener.onCartUpdated();
        });
    }

    @Override
    public int getItemCount() { return cartItems.size(); }

    // ── ViewHolder ────────────────────────────────────────────────────────────
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvEmoji, tvName, tvLinePrice, tvQty, tvPlus, tvMinus;

        ViewHolder(View itemView) {
            super(itemView);
            tvEmoji     = itemView.findViewById(R.id.tvEmoji);
            tvName      = itemView.findViewById(R.id.tvName);
            tvLinePrice = itemView.findViewById(R.id.tvLinePrice);
            tvQty       = itemView.findViewById(R.id.tvQty);
            tvPlus      = itemView.findViewById(R.id.tvPlus);
            tvMinus     = itemView.findViewById(R.id.tvMinus);
        }
    }
}
