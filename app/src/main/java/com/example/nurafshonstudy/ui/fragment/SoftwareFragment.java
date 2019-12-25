package com.example.nurafshonstudy.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nurafshonstudy.R;
import com.example.nurafshonstudy.ui.fragment.base.BaseFragment;

public class SoftwareFragment extends BaseFragment {

    public static SoftwareFragment newInstance() {
        Bundle args = new Bundle();
        SoftwareFragment fragment = new SoftwareFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.software_fragment,container,false);
    }
}
