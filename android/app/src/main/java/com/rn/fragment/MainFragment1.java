package com.rn.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rn.R;
import com.rn.base.BaseMvpFragment;
import com.rn.mvp.contract.LoginContract;
import com.rn.mvp.presenter.LoginPresenter;
import com.rn.view.CirCleBarView;

import butterknife.BindView;
import butterknife.OnClick;

public class MainFragment1 extends BaseMvpFragment<LoginContract.IPresenter>
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
        return R.layout.fragment_1;
    }

    @Override
    protected void initView() {
//        circleProgressbar.setProgress(5);
        cirCleBarView.start();
    }

    @Override
    protected void initMvpData() {

    }

    @Override
    public void setText(String result) {
        tvResult.setText(result);
    }

    @OnClick({R.id.btn_login, R.id.btn_data})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                presenter.login();
                break;
            case R.id.btn_data:
                presenter.request();
                presenter.testPost();
                break;
        }

    }
}
