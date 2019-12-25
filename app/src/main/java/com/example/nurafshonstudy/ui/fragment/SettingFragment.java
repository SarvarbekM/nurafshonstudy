package com.example.nurafshonstudy.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nurafshonstudy.App;
import com.example.nurafshonstudy.R;
import com.example.nurafshonstudy.config.Key_Values;

import java.io.File;

public class SettingFragment extends Fragment {
    private EditText historyDayET;
    private EditText testCountET;
    private Button cacheBTN;
    private Button dbBTN;
    private Button saveBTN;


    public static SettingFragment newInstance() {
        Bundle args = new Bundle();
        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.setting_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        historyDayET = view.findViewById(R.id.historyDayET);
        testCountET = view.findViewById(R.id.testCountET);
        cacheBTN = view.findViewById(R.id.cacheBTN);
        dbBTN = view.findViewById(R.id.dbBTN);
        saveBTN = view.findViewById(R.id.saveBTN);


        cacheBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("DELETE CACHE")
                        .setMessage("Cache fayllarni o'chirmoqchimisiz?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (deleteCacheFiles()) {
                                    Toast.makeText(getContext(), "Cache fayllar o'chirildi !", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getContext(), "Cache fayllarni o'chirishda xatolik yuz berdi !", Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        dbBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("DELETE DATABASE")
                        .setMessage("Bazadan testlarni o'chirmoqchimisiz?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (deleteDBFiles()) {
                                    Toast.makeText(getContext(), "Baza o'chirildi !", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getContext(), "Bazani o'chirishda xatolik yuz berdi !", Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings();
            }
        });

        App app = App.getInstance();
        historyDayET.setText(Integer.toString(app.getHistoryDay()));
        testCountET.setText(Integer.toString(app.getTestCount()));

    }

    private void saveSettings() {
        App app = App.getInstance();
        String text = historyDayET.getText().toString().trim();
        int count = 0;
        if (!text.isEmpty()) {
            if (Integer.parseInt(text) > 4) {
                app.setHistoryDay(Integer.parseInt(text));
                count++;
            } else {
                Toast.makeText(getContext(), "Minimum value history day is 5", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        text = testCountET.getText().toString().trim();
        if (!text.isEmpty()) {
            if(Integer.parseInt(text)>9){
                app.setTestCount(Integer.parseInt(text));
                count++;
            }else {
                Toast.makeText(getContext(), "Minimum value Test count is 10", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (count == 2) {
            Toast.makeText(getContext(), "All Settings saved", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "Settings not saved", Toast.LENGTH_LONG).show();
        }
    }

    private boolean deleteCacheFiles() {
        App app = App.getInstance();
        String filepath = app.getBaseDirectory() + File.separator + Key_Values.CACHE_CATEGORIES_FILENAME;
        File file = new File(filepath);
        int count = 0;
        if (file.exists()) {
            if (file.delete()) {
                count++;
            }
        }
        filepath = app.getBaseDirectory() + File.separator + Key_Values.RESULT_FILENAME;
        file = new File(filepath);
        if (file.exists()) {
            if (file.delete()) {
                count++;
            }
        }
        return count != 0;
    }

    private boolean deleteDBFiles() {
        File f = new File(App.getInstance().getBaseDirectory());
        File[] files = f.listFiles();
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            String filePath = file.getPath();
            if (filePath.endsWith(Key_Values.EXCEL_EXTENTION)) {
                files[i].delete();
            }
        }
        return true;
    }
}
