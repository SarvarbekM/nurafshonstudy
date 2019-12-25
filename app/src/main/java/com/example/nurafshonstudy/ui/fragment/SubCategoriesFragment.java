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
import android.widget.Toast;

import com.example.nurafshonstudy.App;
import com.example.nurafshonstudy.R;
import com.example.nurafshonstudy.backgroundtask.ReadSubCategoriesTask;
import com.example.nurafshonstudy.config.Key_Values;
import com.example.nurafshonstudy.interfaces.IProgress;
import com.example.nurafshonstudy.interfaces.IReadExcel;
import com.example.nurafshonstudy.interfaces.ItemClickListener;
import com.example.nurafshonstudy.pojos.Category;
import com.example.nurafshonstudy.pojos.SubCategory;
import com.example.nurafshonstudy.ui.adapter.SubCategoryAdapter;

import java.io.File;
import java.util.ArrayList;

public class SubCategoriesFragment extends Fragment {
    private static final String TAG = "SubCategoriesFragment";

    //private static final String SUBCATEGORY_KEY = "SUBCATEGORY_KEY";
    private RecyclerView subCategoryRV;
    private ProgressBar progressBar;
    //private ArrayList<SubCategory> subCategories;
    //private Category category;


    public static SubCategoriesFragment newInstance() {
        Bundle args = new Bundle();
        //args.putParcelable(SUBCATEGORY_KEY, category);
        //args.putParcelableArrayList(SUBCATEGORY_KEY, subCategories);
        SubCategoriesFragment fragment = new SubCategoriesFragment();
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
        subCategoryRV = view.findViewById(R.id.subcategoryRV);
        progressBar = view.findViewById(R.id.progressBar);

        IProgress progress = new IProgress() {
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
        IReadExcel readExcel = new IReadExcel() {
            @Override
            public void onFinishReadSubCategories(ArrayList<SubCategory> subCategories) {
                if(subCategories.isEmpty()){
                    Toast.makeText(getContext(), "List of empty", Toast.LENGTH_LONG).show();
                }else {
                    SubCategoryAdapter adapter = new SubCategoryAdapter(subCategories);
                    subCategoryRV.setAdapter(adapter);
                }
            }
        };
        ReadSubCategoriesTask task = new ReadSubCategoriesTask(progress, readExcel);
        task.execute(Key_Values.KEY);
    }
}