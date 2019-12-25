package com.example.nurafshonstudy.interfaces;

import com.example.nurafshonstudy.pojos.Category;
import com.example.nurafshonstudy.pojos.SubCategory;
import com.example.nurafshonstudy.pojos.Test;

import java.util.ArrayList;

public interface ItemClickListener {
    void onClickDownload(Category category);
    void onClickCategory(Category category);
    void onClickSubCategory(SubCategory subCategory);
    void onTestFinished(ArrayList<Test> tests);
    void onClickStatistics();
    void onClickSettings();
    void onClickStatisticsItem(String item);
    void onClickSoftware();
    void onCLickEducation();
}
