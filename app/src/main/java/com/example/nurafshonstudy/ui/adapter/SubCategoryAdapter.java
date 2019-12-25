package com.example.nurafshonstudy.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nurafshonstudy.App;
import com.example.nurafshonstudy.R;
import com.example.nurafshonstudy.interfaces.ItemClickListener;
import com.example.nurafshonstudy.pojos.Category;
import com.example.nurafshonstudy.pojos.SubCategory;

import java.util.ArrayList;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {
    public static final String TAG="SubCategoryAdapter";

    private ArrayList<SubCategory> subCategories;

    public SubCategoryAdapter(ArrayList<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.subcategory_item_fragment, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.onBind(subCategories.get(i));
    }

    @Override
    public int getItemCount() {
        return subCategories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView itemTV;
        private SubCategory currentSubCategory;
        private ItemClickListener listener;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.subcategoryCardView);
            itemTV=itemView.findViewById(R.id.subCategoryItemTV);
            listener = (ItemClickListener) itemView.getContext();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickSubCategory(currentSubCategory);
                }
            });
        }

        void onBind(final SubCategory subCategory) {
            currentSubCategory= subCategory;
            itemTV.setText(currentSubCategory.getName());
        }
    }
}
