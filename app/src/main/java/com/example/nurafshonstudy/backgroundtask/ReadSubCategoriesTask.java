package com.example.nurafshonstudy.backgroundtask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.nurafshonstudy.interfaces.IProgress;
import com.example.nurafshonstudy.interfaces.IReadExcel;
import com.example.nurafshonstudy.pojos.SubCategory;

import java.io.IOException;
import java.util.ArrayList;

public class ReadSubCategoriesTask extends AsyncTask<String, Void, ArrayList<SubCategory>> {
    private static final String TAG = "ReadSubCategoriesTask";

    private IProgress progress;
    private IReadExcel readExcel;

    public ReadSubCategoriesTask(IProgress progress, IReadExcel readExcel) {
        this.progress = progress;
        this.readExcel = readExcel;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress.onProgressStart();
    }

    @Override
    protected ArrayList<SubCategory> doInBackground(String... strings) {
        ArrayList<SubCategory> subCategoryArrayList = new ArrayList<>();
        ExcelUtility excelUtility = ExcelUtility.getInstance();
        try {
            if (excelUtility.isOpenExcel()) {
                subCategoryArrayList = excelUtility.getSubCategories();
            } else {
                if (excelUtility.openExcel()) {
                    subCategoryArrayList = excelUtility.getSubCategories();
                } else {
                    Log.e(TAG,"excel is not opened");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return subCategoryArrayList;
    }

    @Override
    protected void onPostExecute(ArrayList<SubCategory> subCategories) {
        super.onPostExecute(subCategories);
        progress.onProgressFinish(!subCategories.isEmpty());
        Log.d(TAG, "read subcategory on finish: size=" + subCategories.size());
        readExcel.onFinishReadSubCategories(subCategories);
    }
}
