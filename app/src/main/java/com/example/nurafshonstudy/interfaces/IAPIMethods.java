package com.example.nurafshonstudy.interfaces;

import com.example.nurafshonstudy.pojos.Category;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IAPIMethods {
    @GET("/api/default/index")
    Call<ArrayList<Category>> getCategoryList();
}
