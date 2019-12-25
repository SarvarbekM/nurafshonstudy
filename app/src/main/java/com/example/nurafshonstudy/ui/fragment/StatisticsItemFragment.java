package com.example.nurafshonstudy.ui.fragment;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.example.nurafshonstudy.App;
import com.example.nurafshonstudy.R;
import com.example.nurafshonstudy.pojos.ResultTest;
import com.example.nurafshonstudy.ui.fragment.base.BaseFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class StatisticsItemFragment extends BaseFragment {

    private static final String ITEM_KEY = "ITEM_KEY";
    private static final String RESULT_KEY= "RESULT_KEY";
    private String item;
    private AnyChartView anyChartView;
    private ArrayList<ResultTest> resultTests;

    public static StatisticsItemFragment newInstance(String item,ArrayList<ResultTest> resultTests) {
        Bundle args = new Bundle();
        args.putString(ITEM_KEY, item);
        args.putParcelableArrayList(RESULT_KEY,resultTests);
        StatisticsItemFragment fragment = new StatisticsItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.item = getArguments().getString(ITEM_KEY);
            this.resultTests=getArguments().getParcelableArrayList(RESULT_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.statistics_item_fargment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        anyChartView = view.findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(view.findViewById(R.id.progress_bar));
        Log.d(getMyTAG(), "STat item 1");
        anyChartView.setZoomEnabled(true);
        genData();
    }

    private void genData(){

        List<DataEntry> data = new ArrayList<>();
        java.text.SimpleDateFormat dateFormat=new java.text.SimpleDateFormat("dd.MM HH:mm");
        Date date=new Date();
        App app= App.getInstance();
        for(ResultTest result : resultTests){
            if(result.getSubCategory().equals(item)){
                long diff= date.getTime()-result.getDate().getTime();
                long days=TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS);
                Log.d(getMyTAG(),"days="+days);
                Log.d(getMyTAG(),"history day="+app.getHistoryDay());
                if(days<app.getHistoryDay())
                {
                    data.add(new ValueDataEntry(dateFormat.format(result.getDate()),result.getCorrectTestCount()*100.0/result.getAllTestCount()));
                }
            }
        }

        for(DataEntry v: data){
            Log.d(getMyTAG(),"JS="+v.generateJs());
        }
        Log.d(getMyTAG(),"data.isEmpty="+data.isEmpty());


        Cartesian cartesian = AnyChart.column();
//        data.add(new ValueDataEntry("Rouge", 80540));
//        data.add(new ValueDataEntry("Foundation", 94190));
//        data.add(new ValueDataEntry("Mascara", 102610));
//        data.add(new ValueDataEntry("Lip gloss", 110430));
//        data.add(new ValueDataEntry("Lipstick", 128000));
//        data.add(new ValueDataEntry("Nail polish", 143760));
//        data.add(new ValueDataEntry("Eyebrow pencil", 170670));
//        data.add(new ValueDataEntry("Eyeliner", 213210));
//        data.add(new ValueDataEntry("Eyeshadows", 249980));

        Column column = cartesian.column(data);

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("{%Value}%{groupsSeparator: }");

        cartesian.animation(true);
        cartesian.title(item+ "dan topshirilgan testlar statistikasi");
        cartesian.yScale().minimum(0d);
        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }%");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);
        cartesian.xAxis(0).title("Sana");
        cartesian.yAxis(0).title("Foiz");
        anyChartView.setChart(cartesian);
    }


}
