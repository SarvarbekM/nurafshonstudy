package com.example.nurafshonstudy.ui.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nurafshonstudy.R;
import com.example.nurafshonstudy.pojos.Test;

import java.util.ArrayList;

public class TestRVAdapter extends RecyclerView.Adapter<TestRVAdapter.ViewHolder> {

    private static final String TAG="CategoryAdapter";

    private ArrayList<Test> testArrayList;
    public TestRVAdapter(ArrayList<Test> tests){
        this.testArrayList=tests;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.test_item_rv_fragment, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.onBind(testArrayList.get(i),i);
    }

    @Override
    public int getItemCount() {
        return testArrayList.size();
    }

    public void notifyChanged() {

    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView indexTestTV;
        private ConstraintLayout indexTestCL;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            indexTestTV=itemView.findViewById(R.id.indexTestTV);
            indexTestCL=itemView.findViewById(R.id.indexTestCL);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO recycleviewda test tanlandi
                }
            });
        }

        private void onBind(Test test,int position){
            indexTestTV.setText(String.valueOf(position+1));
            if(test.getA().isSelected() || test.getB().isSelected() || test.getC().isSelected() || test.getD().isSelected()){
                indexTestCL.setBackgroundColor(Color.BLUE);
            }
            else {
                indexTestCL.setBackgroundColor(Color.WHITE);
            }
        }
    }
}
