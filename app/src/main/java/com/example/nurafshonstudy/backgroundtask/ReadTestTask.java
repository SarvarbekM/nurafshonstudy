package com.example.nurafshonstudy.backgroundtask;

import android.os.AsyncTask;

import com.example.nurafshonstudy.interfaces.IProgress;
import com.example.nurafshonstudy.interfaces.IReadTest;
import com.example.nurafshonstudy.pojos.SubCategory;
import com.example.nurafshonstudy.pojos.Test;

import java.io.IOException;
import java.util.ArrayList;

public class ReadTestTask extends AsyncTask<String,Void, ArrayList<Test>> {
    private static final String TAG="ReadTestTask";
    //private SubCategory subCategory;
    private IProgress progress;
    private IReadTest readTest;

    public ReadTestTask(IProgress progress, IReadTest readTest) {
      //  this.subCategory = subCategory;
        this.progress=progress;
        this.readTest=readTest;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress.onProgressStart();
    }

    @Override
    protected ArrayList<Test> doInBackground(String... strings) {
        ExcelUtility excelUtility=ExcelUtility.getInstance();
        ArrayList<Test> allTests= new ArrayList<>();
        try {
            allTests = excelUtility.getRandomTestBySubCategory();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allTests;
    }

    @Override
    protected void onPostExecute(ArrayList<Test> tests) {
        super.onPostExecute(tests);
        progress.onProgressFinish(true);
        readTest.onFinishReadTest(tests);
    }
}
