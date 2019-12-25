package com.example.nurafshonstudy.ui.fragment.base;

import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {
    private String TAG;

    public BaseFragment() {
        this.TAG = getClass().getName();
    }

    public String getMyTAG() {
        return TAG;
    }
}
