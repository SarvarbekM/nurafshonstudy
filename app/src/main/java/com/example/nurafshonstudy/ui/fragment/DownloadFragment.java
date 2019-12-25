package com.example.nurafshonstudy.ui.fragment;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.nurafshonstudy.R;
import com.example.nurafshonstudy.config.Key_Values;
import com.example.nurafshonstudy.interfaces.IProgress;
import com.example.nurafshonstudy.network.DownloadTask;
import com.example.nurafshonstudy.network.NetworkConnectionTask;
import com.example.nurafshonstudy.pojos.Category;

import java.io.File;

public class DownloadFragment extends Fragment {
    private static final String TAG="DownloadFragment";


    public static final String CATEGORY_KEY="CATEGORY";



    private ProgressBar prg;
    private ProgressBar prgIndicator;
    private IProgress progress;
    private IProgress progressIndicator;
    private Category category;



    public static DownloadFragment newInstance(Category category) {
        Bundle args = new Bundle();
        args.putParcelable(CATEGORY_KEY,category);
        DownloadFragment fragment = new DownloadFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
          category=getArguments().getParcelable(CATEGORY_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.download_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        prg = view.findViewById(R.id.progressBar);
        prgIndicator=view.findViewById(R.id.progressIndicator);
        progress = new IProgress() {
            @Override
            public void onProgressStart() {
                prg.setIndeterminate(false);
                prg.setVisibility(View.VISIBLE);
                prg.setMax(100);
                prg.setProgress(0);
            }

            @Override
            public void onProgressUpdate(Integer value) {
                prg.setProgress(value);
                Log.d(TAG,"progress="+value);
            }

            @Override
            public void onProgressFinish(boolean isCorrect) {
                if (isCorrect) {
                    Toast.makeText(getContext(), "Download error: ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "File downloaded", Toast.LENGTH_SHORT).show();
                }
                prg.setVisibility(View.INVISIBLE);
            }
        };
        progressIndicator=new IProgress() {
            @Override
            public void onProgressStart() {
                prgIndicator.setVisibility(View.VISIBLE);
            }

            @Override
            public void onProgressUpdate(Integer value) {
                Log.d(TAG,"progress="+value);
            }

            @Override
            public void onProgressFinish(boolean isCorrect) {
                prgIndicator.setVisibility(View.INVISIBLE);
                String answer="Device is ";
                if(isCorrect){
                    File file=new File(Environment.getExternalStorageDirectory()+File.separator+Key_Values.BASE_DIRECTORY,category.getName()+Key_Values.EXCEL_EXTENTION);
                    DownloadTask downloadTask = new DownloadTask(progress,file.getAbsolutePath());
                    downloadTask.execute(Key_Values.DOWNLOAD_URL + category.getId().toString());
                    //downloadTask.execute("https://file.uzhits.net/music/kolleksiya/Sato%20guruhi/Sato%20-%20Ko'z%20tegmasin%20(uzhits.net).mp3");
                    answer+="online";
                }
                else {
                    answer+="offline";
                }
                Toast.makeText(getContext(), answer, Toast.LENGTH_SHORT).show();
            }
        };
        startNetworkOperation();
//        if (DownloadTask.isOnline()) {
//            DownloadTask downloadTask = new DownloadTask(getContext(), progress);
//            downloadTask.execute(Key_Values.DOWNLOAD_URL+category.getId());
//            //Toast.makeText(getContext(), category.toString(), Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(getContext(), "Device is offline", Toast.LENGTH_SHORT).show();
//        }
    }

    private void startNetworkOperation() {
        NetworkConnectionTask task=new NetworkConnectionTask(progressIndicator);
        task.execute("www.google.com");
    }
}