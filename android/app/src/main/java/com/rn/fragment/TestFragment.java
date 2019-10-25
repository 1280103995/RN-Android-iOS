package com.rn.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.rn.R;
import com.rn.activity.LoginActivity;
import com.rn.base.BaseFragment;

public class TestFragment extends BaseFragment {

    private String tip;

    public static TestFragment newInstance(String params) {
        TestFragment fragment = new TestFragment();
        Bundle args = new Bundle();
        args.putString("params",params);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test;
    }

    @Override
    protected void initView() {
        tip = getArguments().getString("params");
        TextView textView = findViewById(R.id.text);
        textView.setText(tip);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
    }

    @Override
    public void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        System.out.println(tip + ": 第一次可见");
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        System.out.println(tip + ": 每次可见");
    }

    @Override
    public void onFragmentPause() {
        super.onFragmentPause();
        System.out.println(tip + ": 每次不可见");
    }
}
