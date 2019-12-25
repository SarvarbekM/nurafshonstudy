package com.example.nurafshonstudy.ui.adapter;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nurafshonstudy.R;
import com.example.nurafshonstudy.backgroundtask.ExcelUtility;
import com.example.nurafshonstudy.interfaces.ItemClickListener;
import com.example.nurafshonstudy.pojos.Category;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    public static final String TAG="CategoryAdapter";


    private ArrayList<Category> categoryList;

    public CategoryAdapter(ArrayList<Category> categoryList) {
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_item_fragment, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.onBind(categoryList.get(i));
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;
        private ItemClickListener listener;
        private Category currentCategory;
        private ImageView imageView;
        private ConstraintLayout categoryCL;

        public ViewHolder(View itemView) {
            super(itemView);
            categoryCL=itemView.findViewById(R.id.categoryCL);
            tv = itemView.findViewById(R.id.itemTV);
            imageView=itemView.findViewById(R.id.iconIV);
            listener = (ItemClickListener) itemView.getContext();
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onClickDownload(currentCategory);
                }
            });
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickCategory(currentCategory);
                }
            });
            categoryCL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickCategory(currentCategory);
                }
            });
        }

        void onBind(final Category category) {
            currentCategory = category;
            tv.setText(category.getName());
            checkFile();
        }

        boolean checkFile(){
            if(ExcelUtility.getInstance().isExistsCategoryFile(currentCategory)){
                imageView.setImageResource(R.drawable.ic_done_black_24dp);
            }
            else {
                imageView.setImageResource(R.drawable.ic_file_download_black_24dp);
            }
            return  true;
        }
    }
}
