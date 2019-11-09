package com.rn.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rn.R;
import com.rn.base.BaseActivity;
import com.rn.base.BaseMvpActivity;
import com.rn.mvp.contract.LoginContract;
import com.rn.mvp.presenter.LoginPresenter;
import com.rn.view.CirCleBarView;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseMvpActivity<LoginContract.IPresenter>
        implements LoginContract.IView {

    @BindView(R.id.result)
    TextView tvResult;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_data)
    Button btnData;
    @BindView(R.id.btn_down)
    CirCleBarView cirCleBarView;

    @Override
    protected LoginContract.IPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        setTitle("登录");
        cirCleBarView.start();
    }

    @Override
    protected void initMvpData() {

    }

    @Override
    public void setText(String result) {

    }

    @OnClick({R.id.btn_login, R.id.btn_data, R.id.btn_next})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                presenter.login();
                break;
            case R.id.btn_data:
                presenter.request();
                presenter.testPost();
                break;
            case R.id.btn_next:
                startActivity(ReactDemoActivity.class);
                break;
        }
    }
}

