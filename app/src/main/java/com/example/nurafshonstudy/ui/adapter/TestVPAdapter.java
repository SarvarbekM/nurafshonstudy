package com.example.nurafshonstudy.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.example.nurafshonstudy.interfaces.INotifyChanged;
import com.example.nurafshonstudy.pojos.Test;
import com.example.nurafshonstudy.ui.fragment.TestItemFragment;

import java.util.ArrayList;

public class TestVPAdapter extends FragmentPagerAdapter {

    private ArrayList<Test> testArrayList;
    private INotifyChanged notifyChanged;


    public TestVPAdapter(FragmentManager fm, ArrayList<Test> tests,INotifyChanged notifyChanged) {
        super(fm);
        this.testArrayList=tests;
        this.notifyChanged=notifyChanged;
    }

    @Override
    public Fragment getItem(int i) {
        return TestItemFragment.newInstance(testArrayList.get(i),this.notifyChanged,i);
    }

    @Override
    public int getCount() {
        return testArrayList.size();
    }
}
