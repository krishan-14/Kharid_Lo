package com.example.kharidlo2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kharidlo2.R;
import com.kharidlo2.models.Category;

import java.util.List;

/**
 * CategoryAdapter — drives the horizontal/grid category list on the Home screen.
 *
 * Usage:
 *   CategoryAdapter adapter = new CategoryAdapter(context, categories, category -> {
 *       // open ProductListActivity for this category
 *   });
 *   recyclerView.setAdapter(adapter);
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    // Callback interface so the Activity handles click logic
    public interface OnCategoryClickListener {
        void onCategoryClick(Category category);
    }

    private final Context context;
    private final List<Category> categories;
    private final OnCategoryClickListener listener;

    public CategoryAdapter(Context context, List<Category> categories,
                           OnCategoryClickListener listener) {
        this.context    = context;
        this.categories = categories;
        this.listener   = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category cat = categories.get(position);

        holder.tvEmoji.setText(cat.getEmoji());
        holder.tvName.setText(cat.getName());

        // Pass click up to the activity
        holder.itemView.setOnClickListener(v -> listener.onCategoryClick(cat));
    }

    @Override
    public int getItemCount() { return categories.size(); }

    // ── ViewHolder ────────────────────────────────────────────────────────────
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvEmoji, tvName;

        ViewHolder(View itemView) {
            super(itemView);
            tvEmoji = itemView.findViewById(R.id.tvCategoryEmoji);
            tvName  = itemView.findViewById(R.id.tvCategoryName);
        }
    }
}