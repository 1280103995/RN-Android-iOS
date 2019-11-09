package com.rn.fragment;

import android.view.View;
import android.widget.Button;

import androidx.collection.ArrayMap;

import com.rn.R;
import com.rn.activity.RNActivity;
import com.rn.base.BaseFragment;
import com.rn.entity.RNRouteInfo;

public class MainFragment1 extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_1;
    }

    @Override
    protected void initView() {
        Button button = findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RNRouteInfo route = new RNRouteInfo();
                route.setRouteName("TestOne");
                ArrayMap<String, Object> map = new ArrayMap<>();
                map.put("initTitle", "从Android首页过来");
                route.setRouteParams(map);
                startActivity(RNActivity.class, route.getBundle());
            }
        });
    }
}
