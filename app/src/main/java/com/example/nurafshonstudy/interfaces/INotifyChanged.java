package com.example.nurafshonstudy.interfaces;

import android.os.Parcelable;

import java.io.Serializable;

public interface INotifyChanged extends Serializable {
    void onSelectAnswer(int position);
}
