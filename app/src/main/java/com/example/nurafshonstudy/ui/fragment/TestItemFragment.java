package com.example.nurafshonstudy.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.nurafshonstudy.R;
import com.example.nurafshonstudy.interfaces.INotifyChanged;
import com.example.nurafshonstudy.pojos.Test;
import com.example.nurafshonstudy.ui.fragment.base.BaseFragment;

public class TestItemFragment extends BaseFragment {
    private static final String KEY="KEY";
    private static final String NOTIFY_KEY="NOTIFY_KEY";
    private static final String POSITION_KEY="POSITION_KEY";

    private Test test;
    private INotifyChanged notifyChanged;
    private int position;
    private TextView questionTV;
    private RadioButton a;
    private RadioButton b;
    private RadioButton c;
    private RadioButton d;

    public static TestItemFragment newInstance(Test test, INotifyChanged notifyChanged,int position) {
        Bundle args = new Bundle();
        args.putParcelable(KEY,test);
        args.putInt(POSITION_KEY,position);
        args.putSerializable(NOTIFY_KEY,notifyChanged);
        TestItemFragment fragment = new TestItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            test=getArguments().getParcelable(KEY);
            this.notifyChanged= (INotifyChanged) getArguments().getSerializable(NOTIFY_KEY);
            this.position=getArguments().getInt(POSITION_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test_item_vp_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        questionTV=view.findViewById(R.id.questionTV);
        a=view.findViewById(R.id.aRB);
        b=view.findViewById(R.id.bRB);
        c=view.findViewById(R.id.cRB);
        d=view.findViewById(R.id.dRB);

        questionTV.setText((position+1)+") "+test.getQuestion());
        a.setText(test.getA().getText());
        b.setText(test.getB().getText());
        c.setText(test.getC().getText());
        d.setText(test.getD().getText());

        a.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                test.getA().setSelected(isChecked);
                notifyChanged.onSelectAnswer(position);
            }
        });

        b.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                test.getB().setSelected(isChecked);
                notifyChanged.onSelectAnswer(position);
            }
        });
        c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                test.getC().setSelected(isChecked);
                notifyChanged.onSelectAnswer(position);
            }
        });
        d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                test.getD().setSelected(isChecked);
                notifyChanged.onSelectAnswer(position);
            }
        });



    }
}
