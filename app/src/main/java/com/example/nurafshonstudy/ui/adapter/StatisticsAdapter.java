package com.example.nurafshonstudy.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nurafshonstudy.R;
import com.example.nurafshonstudy.interfaces.ItemClickListener;

import java.util.ArrayList;

public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.ViewHolder> {
    private ArrayList<String> arrayList;

    public StatisticsAdapter(ArrayList<String> subCategories) {
        this.arrayList = subCategories;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.subcategory_item_fragment, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.onBind(arrayList.get(i));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;
        private String currentItem;

        ViewHolder(@NonNull final View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.subCategoryItemTV);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ItemClickListener)itemView.getContext()).onClickStatisticsItem(currentItem);
                }
            });
        }

        void onBind(String text) {
            currentItem=text;
            tv.setText(text);
        }
    }
}
