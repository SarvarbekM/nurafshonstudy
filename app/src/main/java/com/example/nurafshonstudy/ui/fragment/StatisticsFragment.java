package com.example.nurafshonstudy.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.nurafshonstudy.App;
import com.example.nurafshonstudy.R;
import com.example.nurafshonstudy.backgroundtask.ReadResultTask;
import com.example.nurafshonstudy.backgroundtask.ReadTestTask;
import com.example.nurafshonstudy.config.Key_Values;
import com.example.nurafshonstudy.interfaces.IProgress;
import com.example.nurafshonstudy.interfaces.IReadResult;
import com.example.nurafshonstudy.interfaces.IReadTest;
import com.example.nurafshonstudy.pojos.ResultTest;
import com.example.nurafshonstudy.pojos.Test;
import com.example.nurafshonstudy.ui.MainActivity;
import com.example.nurafshonstudy.ui.adapter.StatisticsAdapter;

import java.io.File;
import java.util.ArrayList;

public class StatisticsFragment extends Fragment {
    private RecyclerView subcategoryRV;
    private ProgressBar progressBar;

    public static StatisticsFragment newInstance() {
        Bundle args = new Bundle();
        StatisticsFragment fragment = new StatisticsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.subcategory_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        subcategoryRV = view.findViewById(R.id.subcategoryRV);
        progressBar = view.findViewById(R.id.progressBar);
        IProgress p = new IProgress() {
            @Override
            public void onProgressStart() {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onProgressUpdate(Integer value) {

            }

            @Override
            public void onProgressFinish(boolean isCorrect) {
                progressBar.setVisibility(View.GONE);
            }
        };
        IReadResult r = new IReadResult() {
            @Override
            public void onReadResultFinish(ArrayList<ResultTest> resultTests) {
                ((MainActivity)getActivity()).setResultTests(resultTests);
                ArrayList<String> subcategories=new ArrayList<>();
                for(ResultTest item : resultTests){
                    if(!subcategories.contains(item.getSubCategory())){
                        subcategories.add(item.getSubCategory());
                    }
                }
                subcategoryRV.setAdapter(new StatisticsAdapter(subcategories));
            }
        };

        ReadResultTask task = new ReadResultTask(p, r);
        File file = new File(App.getInstance().getBaseDirectory() + File.separator + Key_Values.RESULT_FILENAME);
        task.execute(file.getAbsolutePath());
    }
}
