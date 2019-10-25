package com.rn.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rn.R;
import com.rn.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.result)
    TextView textView;
    @BindView(R.id.login)
    Button btnLogin;
    @BindView(R.id.next)
    Button btnNext;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        setTitle("BaseMVPActivity");
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.login, R.id.next})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login:

                break;
            case R.id.next:
                startActivity(ReactDemoActivity.class);
                break;
        }
    }
}

