package com.rn.activity;

import android.view.View;
import android.widget.Button;

import com.rn.R;
import com.rn.base.BaseActivity;
import com.rn.entity.RNRouteInfo;

public class RNDemoActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rn_demo;
    }

    @Override
    protected void initView() {
        setTitle("这是原生页面");

        Button button = findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RNRouteInfo route = new RNRouteInfo();
                route.setRouteName("TestThree");
                startActivity(RNActivity.class, route.getBundle());
            }
        });
    }

    @Override
    protected void initData() {

    }

}
