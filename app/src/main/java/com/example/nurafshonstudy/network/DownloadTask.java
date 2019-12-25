package com.example.nurafshonstudy.network;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.nurafshonstudy.backgroundtask.ExcelUtility;
import com.example.nurafshonstudy.config.Key_Values;
import com.example.nurafshonstudy.interfaces.IProgress;
import com.google.gson.JsonObject;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

public class DownloadTask extends AsyncTask<String, Integer, String> {
    private static final String TAG = "DownloadTask";
    private IProgress onProgrress;
    private String saveFileName;

    public DownloadTask(IProgress progress, String saveUrl) {
        onProgrress = progress;
        this.saveFileName = saveUrl;
    }

    private String download(@NonNull String url) throws IOException {
        Request request = new Request.Builder().url(url).addHeader("Content-Type", "application/json").build();
        Response response = (new OkHttpClient()).newCall(request).execute();

        ResponseBody body = response.body();
        long contentLength = body.contentLength();
        BufferedSource source = body.source();
//        byte[] bytes=new byte[55];
//        source.inputStream().read(bytes,0,bytes.length);
        //Log.d(TAG,"download string="+response.body().string());


        File destFile = new File(saveFileName);
        if (destFile.exists()) {
            destFile.delete();
        }
        BufferedSink sink = Okio.buffer(Okio.sink(destFile));
        Buffer sinkBuffer = sink.buffer();
        long totalBytesRead = 0;
        int bufferSize = 8 * 1024;
        for (long bytesRead; (bytesRead = source.read(sinkBuffer, bufferSize)) != -1; ) {
            sink.emit();
            totalBytesRead += bytesRead;
            //Log.d(TAG,"total="+totalBytesRead+"; content="+contentLength);
            int progress = (int) ((totalBytesRead * 100) / contentLength);
            publishProgress(progress);
        }
        sinkBuffer.flush();
        sinkBuffer.close();
        sink.flush();
        sink.close();
        source.close();

        if(destFile.length()<1024*50){
            destFile.delete();
            return Key_Values.ERROR;
        }
        else {
            return Key_Values.OK;
        }
    }

    @Override
    protected String doInBackground(String... sUrl) {
        String answer=Key_Values.ERROR;
        try {
            answer = download(sUrl[0]);
            //TODO Excel fayl skachat qilindi, MAC bilan Password qilish kerak
            Log.d(TAG,"download is finished");
//            ExcelUtility excelUtility = ExcelUtility.getInstance();
//            if (excelUtility.isOpenExcel()) {
//                Log.d(TAG, "excel succesfully changed 1");
//                excelUtility.setMAC();
//                //excelUtility.setPassword();
//                Log.d(TAG, "excel succesfully changed 1");
//            } else{
//                Log.d(TAG,"start opening excel");
//                if (excelUtility.openExcel()) {
//                    Log.d(TAG, "excel succesfully changed 2");
//                    excelUtility.setMAC();
//                    //excelUtility.setPassword();
//                    Log.d(TAG, "excel succesfully changed 2");
//                }
//            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "excel error in changed: "+e.getMessage());
            answer=Key_Values.ERROR;
        }
//        catch (InvalidFormatException e) {
//            e.printStackTrace();
//            Log.d(TAG, "excel error in changed: "+e.getMessage());
//            return Key_Values.ERROR;
//        } catch (GeneralSecurityException e) {
//            e.printStackTrace();
//            Log.d(TAG, "excel error in changed: "+e.getMessage());
//            return Key_Values.ERROR;
//        }
        return answer;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        onProgrress.onProgressStart();
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        onProgrress.onProgressUpdate(progress[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d(TAG, "Message: " + result);
        onProgrress.onProgressFinish(result.equals(Key_Values.OK));
    }
}