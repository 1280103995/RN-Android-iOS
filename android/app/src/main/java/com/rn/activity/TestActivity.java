package com.rn.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.rn.R;
import com.rn.base.BaseActivity;

import butterknife.BindView;

public class TestActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    Button btnBack;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("result", "返回的值");
                setResult(100,intent);
                finish();
            }
        });
    }

    @Override
    protected void initData() {

    }
}
