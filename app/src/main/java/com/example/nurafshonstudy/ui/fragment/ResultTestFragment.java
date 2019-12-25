package com.example.nurafshonstudy.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nurafshonstudy.App;
import com.example.nurafshonstudy.R;
import com.example.nurafshonstudy.pojos.Test;
import com.example.nurafshonstudy.ui.MainActivity;
import com.example.nurafshonstudy.ui.adapter.ResultTestAdapter;

import java.util.ArrayList;

public class ResultTestFragment extends Fragment {
    private static final String TAG = "ResultTestFragment";
    private static final String RESULT_KEY = "RESULT_KEY";

    private RecyclerView resultTestRV;
    private TextView alltestCountTV;
    private TextView correcttestCountTV;
    private TextView summaBallTV;
    private TextView protsentTV;
    private TextView bahoTV;
    private TextView xulosaTV;
    private ArrayList<Test> tests;

    public static ResultTestFragment newInstance(ArrayList<Test> tests) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(RESULT_KEY, tests);
        ResultTestFragment fragment = new ResultTestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "on create 1");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.tests = getArguments().getParcelableArrayList(RESULT_KEY);
        }
        Log.d(TAG, "on create 2");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.test_result_fragment,container,false);
        return inflater.inflate(R.layout.test_result_scrolling_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "on create 3");
        super.onViewCreated(view, savedInstanceState);
        resultTestRV = view.findViewById(R.id.resultTestRV);
        alltestCountTV = view.findViewById(R.id.alltestCountTV);
        correcttestCountTV = view.findViewById(R.id.correcttestCountTV);
        summaBallTV = view.findViewById(R.id.summaBallTV);
        protsentTV = view.findViewById(R.id.protsentTV);
        bahoTV = view.findViewById(R.id.bahoTV);
        xulosaTV = view.findViewById(R.id.xulosaTV);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        Log.d(TAG, "on create 4");

        int testCount = App.getInstance().getTestCount();
        int count = 0;
        for (Test item : tests) {
            if (item.getA().isCorrect() && item.getA().isSelected()) {
                count++;
            }
            if (item.getB().isCorrect() && item.getB().isSelected()) {
                count++;
            }
            if (item.getC().isCorrect() && item.getC().isSelected()) {
                count++;
            }
            if (item.getD().isCorrect() && item.getD().isSelected()) {
                count++;
            }
        }

        double foiz = ((count * 100) / testCount);
        alltestCountTV.setText("Jami test soni: " + testCount);
        correcttestCountTV.setText("To'g'ri javoblar soni: " + count);
        summaBallTV.setText("To'plangan ball (3.1 balldan): " + (3.1 * count));
        protsentTV.setText("Foiz hisobida: " + foiz + " %");
        if (foiz >= 0 && foiz <= 55) {
            bahoTV.setText("Qo'yilgan baho: 2");
            xulosaTV.setText("Xulosa: Harakat qil, natijalaring juda yomonku, ota-onang senga ishonadi !");
        } else if (foiz > 55 && foiz <= 70) {
            bahoTV.setText("Qo'yilgan baho: 3");
            xulosaTV.setText("Xulosa: O'z ustingda ishla va ko'proq o'rgan");
        } else if (foiz > 70 && foiz <= 85) {
            bahoTV.setText("Qo'yilgan baho: 4");
            xulosaTV.setText("Xulosa: Yomon emas. Yanaqa kuchliroq harakat qilmasang o'zingga qiyin bo'ladi");
        } else if (foiz > 85 && foiz <= 99) {
            bahoTV.setText("Qo'yilgan baho: 5");
            xulosaTV.setText("Xulosa: A'lo, hech qachon o'qishdan to'xtama");
        } else if (foiz > 99) {
            bahoTV.setText("Qo'yilgan baho: 5+");
            xulosaTV.setText("Xulosa: Yashavor, ota-onangga va ustozingga rahmat !");
        }

        resultTestRV.setAdapter(new ResultTestAdapter(tests));
    }
}