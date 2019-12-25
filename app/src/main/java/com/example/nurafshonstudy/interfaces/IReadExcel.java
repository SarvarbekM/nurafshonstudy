package com.example.nurafshonstudy.interfaces;

import com.example.nurafshonstudy.pojos.Category;
import com.example.nurafshonstudy.pojos.SubCategory;

import java.util.ArrayList;

public interface IReadExcel {
    void onFinishReadSubCategories(ArrayList<SubCategory> subCategories);
}
