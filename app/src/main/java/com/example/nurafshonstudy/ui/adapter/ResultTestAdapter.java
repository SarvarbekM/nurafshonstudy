package com.example.nurafshonstudy.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.nurafshonstudy.R;
import com.example.nurafshonstudy.pojos.Test;

import java.util.ArrayList;

public class ResultTestAdapter extends RecyclerView.Adapter<ResultTestAdapter.ViewHolder> {
    private static final String TAG = "ResultTestAdapter";
    private ArrayList<Test> tests;

    public ResultTestAdapter(ArrayList<Test> tests) {
        this.tests = tests;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.test_result_item_fragment, viewGroup, false);
        ViewHolder viewHolder=new ViewHolder(v);
        viewHolder.setIsRecyclable(false);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.onbind(tests.get(i));
    }

    @Override
    public int getItemCount() {
        return tests.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView questionTV;
        private TextView aTV;
        private TextView bTV;
        private TextView cTV;
        private TextView dTV;
        private ImageView aIV;
        private ImageView bIV;
        private ImageView cIV;
        private ImageView dIV;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTV = itemView.findViewById(R.id.resultQuestionTV);
            aTV = itemView.findViewById(R.id.resultATV);
            bTV = itemView.findViewById(R.id.resultBTV);
            cTV = itemView.findViewById(R.id.resultCTV);
            dTV = itemView.findViewById(R.id.resultDTV);

            aIV=itemView.findViewById(R.id.resultAIV);
            bIV=itemView.findViewById(R.id.resultBIV);
            cIV=itemView.findViewById(R.id.resultCIV);
            dIV=itemView.findViewById(R.id.resultDIV);

            //this.setIsRecyclable(false);
//            questionTV.requestLayout();
//            a.requestLayout();
//            b.requestLayout();
//            c.requestLayout();
//            d.requestLayout();
        }

        void onbind(Test test) {
            questionTV.setText(test.getQuestion());
            aTV.setText(test.getA().getText());
            aIV.setImageResource(R.drawable.ic_panorama_fish_eye_black_24dp);
            bTV.setText(test.getB().getText());
            bIV.setImageResource(R.drawable.ic_panorama_fish_eye_black_24dp);
            cTV.setText(test.getC().getText());
            cIV.setImageResource(R.drawable.ic_panorama_fish_eye_black_24dp);
            dTV.setText(test.getD().getText());
            dIV.setImageResource(R.drawable.ic_panorama_fish_eye_black_24dp);

            if (test.getA().isCorrect()) {
                if (test.getA().isSelected()) {
                    aIV.setImageResource(R.drawable.ic_done_all_black_24dp);
                    //a.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_all_black_24dp, 0, 0, 0);
                } else {
                    aIV.setImageResource(R.drawable.ic_done_black_24dp);
                    //a.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_black_24dp, 0, 0, 0);
                    if (test.getB().isSelected()) {
                        //b.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_wrong_read_24dp, 0, 0, 0);
                        bIV.setImageResource(R.drawable.ic_wrong_read_24dp);
                    }
                    else
                    if (test.getC().isSelected()) {
                        //c.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_wrong_read_24dp, 0, 0, 0);
                        cIV.setImageResource(R.drawable.ic_wrong_read_24dp);
                    }
                    else
                    if (test.getD().isSelected()) {
                        //d.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_wrong_read_24dp, 0, 0, 0);
                        dIV.setImageResource(R.drawable.ic_wrong_read_24dp);
                    }
                    else {
                        dIV.setImageResource(R.drawable.ic_panorama_fish_eye_black_24dp);
                    }
                }
            }
            if (test.getB().isCorrect()) {
                if (test.getB().isSelected()) {
                    //b.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_all_black_24dp, 0, 0, 0);
                    bIV.setImageResource(R.drawable.ic_done_all_black_24dp);
                } else {
                    //b.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_black_24dp, 0, 0, 0);
                    bIV.setImageResource(R.drawable.ic_done_black_24dp);
                    if (test.getA().isSelected()) {
                        //a.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_wrong_read_24dp, 0, 0, 0);
                        aIV.setImageResource(R.drawable.ic_wrong_read_24dp);
                    }
                    else
                    if (test.getC().isSelected()) {
                        //c.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_wrong_read_24dp, 0, 0, 0);
                        cIV.setImageResource(R.drawable.ic_wrong_read_24dp);
                    }
                    else
                    if (test.getD().isSelected()) {
                        //d.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_wrong_read_24dp, 0, 0, 0);
                        dIV.setImageResource(R.drawable.ic_wrong_read_24dp);
                    }
                    else {
                        dIV.setImageResource(R.drawable.ic_panorama_fish_eye_black_24dp);
                    }
                }
            }
            if (test.getC().isCorrect()) {
                if (test.getC().isSelected()) {
                    //c.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_all_black_24dp, 0, 0, 0);
                    cIV.setImageResource(R.drawable.ic_done_all_black_24dp);
                } else {
                    //c.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_black_24dp, 0, 0, 0);
                    cIV.setImageResource(R.drawable.ic_done_black_24dp);
                    if (test.getB().isSelected()) {
                        //b.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_wrong_read_24dp, 0, 0, 0);
                        bIV.setImageResource(R.drawable.ic_wrong_read_24dp);
                    }
                    else
                    if (test.getA().isSelected()) {
                        //a.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_wrong_read_24dp, 0, 0, 0);
                        aIV.setImageResource(R.drawable.ic_wrong_read_24dp);
                    }
                    else
                    if (test.getD().isSelected()) {
                        //d.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_wrong_read_24dp, 0, 0, 0);
                        dIV.setImageResource(R.drawable.ic_wrong_read_24dp);
                    }
                    else {
                        dIV.setImageResource(R.drawable.ic_panorama_fish_eye_black_24dp);
                    }
                }
            }
            if (test.getD().isCorrect()) {
                if (test.getD().isSelected()) {
                    //d.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_all_black_24dp, 0, 0, 0);
                    dIV.setImageResource(R.drawable.ic_done_all_black_24dp);
                } else {
                    //d.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_black_24dp, 0, 0, 0);
                    dIV.setImageResource(R.drawable.ic_done_black_24dp);
                    if (test.getB().isSelected()) {
                        //b.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_wrong_read_24dp, 0, 0, 0);
                        bIV.setImageResource(R.drawable.ic_wrong_read_24dp);
                    }
                    else
                    if (test.getC().isSelected()) {
                        //c.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_wrong_read_24dp, 0, 0, 0);
                        cIV.setImageResource(R.drawable.ic_wrong_read_24dp);
                    }
                    else
                    if (test.getA().isSelected()) {
                        //a.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_wrong_read_24dp, 0, 0, 0);
                        aIV.setImageResource(R.drawable.ic_wrong_read_24dp);
                    }
                    else {
                        aIV.setImageResource(R.drawable.ic_panorama_fish_eye_black_24dp);
                    }
                }
            }
            Log.d(TAG,test.toString());
        }
    }
}