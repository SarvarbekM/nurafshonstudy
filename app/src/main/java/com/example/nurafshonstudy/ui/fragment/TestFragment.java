package com.example.nurafshonstudy.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.nurafshonstudy.App;
import com.example.nurafshonstudy.R;
import com.example.nurafshonstudy.backgroundtask.ReadTestTask;
import com.example.nurafshonstudy.config.Key_Values;
import com.example.nurafshonstudy.interfaces.INotifyChanged;
import com.example.nurafshonstudy.interfaces.IProgress;
import com.example.nurafshonstudy.interfaces.IReadTest;
import com.example.nurafshonstudy.interfaces.ItemClickListener;
import com.example.nurafshonstudy.pojos.Category;
import com.example.nurafshonstudy.pojos.SubCategory;
import com.example.nurafshonstudy.pojos.Test;
import com.example.nurafshonstudy.ui.adapter.TestRVAdapter;
import com.example.nurafshonstudy.ui.adapter.TestVPAdapter;

import java.io.File;
import java.util.ArrayList;

public class TestFragment extends Fragment {
    private static final String TAG = "TestFragment";
    private static final String TEST_SUBCATEGORY_KEY = "SUBCATEGORYKEY";
    private static final String TEST_CATEGORY_KEY = "CATEGORYKEY";

//    private SubCategory subCategory;
//    private Category category;
    private RecyclerView testRV;
    private ViewPager testsVP;
    private ProgressBar progressBar;
    private Button testBTN;
    private ArrayList<Test> tests;



    public static TestFragment newInstance() {
        Bundle args = new Bundle();
//        args.putParcelable(TEST_SUBCATEGORY_KEY, subCategory);
//        args.putParcelable(TEST_CATEGORY_KEY, category);
        TestFragment fragment = new TestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            this.subCategory=getArguments().getParcelable(TEST_SUBCATEGORY_KEY);
//            this.category=getArguments().getParcelable(TEST_CATEGORY_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test_main_fargment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(savedInstanceState==null){
            Log.d(TAG,"on view created savedInstanceState null");
        }else {
            Log.d(TAG,"on view created savedInstanceState not null");
        }
        testRV=view.findViewById(R.id.testRV);
        testsVP=view.findViewById(R.id.testVP);
        progressBar=view.findViewById(R.id.progressBar);
        testBTN=view.findViewById(R.id.testBTN);
        testBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Testni tugatish")
                        .setMessage("Test topshirishni tugatmoqchimisiz?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d(TAG,"test yakunlash 1");
                                ((ItemClickListener) v.getContext()).onTestFinished(tests);
                                Log.d(TAG,"test yakunlash 2");
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        IProgress progress = new IProgress() {
            @Override
            public void onProgressStart() {
                Log.d(TAG, "Read test start");
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onProgressUpdate(Integer value) {

            }

            @Override
            public void onProgressFinish(boolean isCorrect) {
                progressBar.setVisibility(View.GONE);
                Log.d(TAG, "Read test finish");
                testBTN.setVisibility(View.VISIBLE);
            }
        };
        final TestRVAdapter[] rvAdapter = new TestRVAdapter[1];
        final INotifyChanged notifyChanged=new INotifyChanged() {
            @Override
            public void onSelectAnswer(int position) {
                //testRV.refreshDrawableState();
                rvAdapter[0].notifyDataSetChanged();
                //rvAdapter[0].notifyChanged();
            }
        };

        IReadTest readTest = new IReadTest() {
            @Override
            public void onFinishReadTest(ArrayList<Test> arrayList) {
                tests=arrayList;
                TestVPAdapter adapter=new TestVPAdapter(getChildFragmentManager(),arrayList,notifyChanged);
                testsVP.setAdapter(adapter);
                rvAdapter[0] = new TestRVAdapter(arrayList);
                testRV.setAdapter(rvAdapter[0]);
//                for (Test item : arrayList) {
//                    Log.d(TAG, item.toString());
//                }
            }
        };


        ReadTestTask task = new ReadTestTask(progress, readTest);
        task.execute(Key_Values.KEY);
    }

}
