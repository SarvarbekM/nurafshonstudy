package com.example.nurafshonstudy.backgroundtask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.nurafshonstudy.config.Key_Values;
import com.example.nurafshonstudy.interfaces.IProgress;
import com.example.nurafshonstudy.interfaces.IReadResult;
import com.example.nurafshonstudy.pojos.Category;
import com.example.nurafshonstudy.pojos.ResultTest;
import com.example.nurafshonstudy.pojos.Test;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ReadResultTask extends AsyncTask<String, Void, ArrayList<ResultTest>> {
    private static final String TAG="ReadResultTask";

    private IProgress progress;
    private IReadResult readResult;
    Type resultListType = new TypeToken<ArrayList<ResultTest>>() {}.getType();

    public ReadResultTask(IProgress progress, IReadResult readResult) {
        this.progress = progress;
        this.readResult = readResult;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress.onProgressStart();
    }

    @Override
    protected ArrayList<ResultTest> doInBackground(String... strings) {
        ArrayList<ResultTest> resultTests = new ArrayList<>();
        File file = new File(strings[0]);
        try {
            StringBuilder text = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
            String json=text.toString();
            Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
            resultTests=gson.fromJson(json,resultListType);
//            String[] json = text.toString().split(Key_Values.RESULT_SPLITTER);
//            for(String item : json){
//                Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
//                ResultTest result = gson.fromJson(item,resultListType);
//                resultTests.add(result);
//            }
            return resultTests;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultTests;
    }

    @Override
    protected void onPostExecute(ArrayList<ResultTest> tests) {
        super.onPostExecute(tests);
        progress.onProgressFinish(true);
        readResult.onReadResultFinish(tests);
    }


}
