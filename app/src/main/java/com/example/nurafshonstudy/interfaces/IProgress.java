package com.example.nurafshonstudy.interfaces;

public interface IProgress {
    void onProgressStart();
    void onProgressUpdate(Integer value);
    void onProgressFinish(boolean isCorrect);
}
