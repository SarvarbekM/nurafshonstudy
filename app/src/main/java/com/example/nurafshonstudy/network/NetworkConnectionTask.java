package com.example.nurafshonstudy.network;

import android.os.AsyncTask;

import com.example.nurafshonstudy.interfaces.IProgress;

import java.io.IOException;
import java.net.InetAddress;

public class NetworkConnectionTask extends AsyncTask<String,Void,Boolean> {

    private IProgress progress;

    public NetworkConnectionTask(IProgress progress) {
        this.progress = progress;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        //Runtime runtime = Runtime.getRuntime();
        try {
            InetAddress address = InetAddress.getByName(strings[0]);
            return !address.equals("");

//            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
//            int     exitValue = ipProcess.waitFor();
//            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        //catch (InterruptedException e) { e.printStackTrace(); }
        return false;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress.onProgressStart();
    }

    @Override
    protected void onPostExecute(Boolean isConnect) {
        super.onPostExecute(isConnect);
        progress.onProgressFinish(isConnect);
    }
}
