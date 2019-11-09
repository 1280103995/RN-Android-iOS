package com.rn.fragment;

import android.view.View;
import android.widget.Button;

import com.rn.R;
import com.rn.activity.LoginActivity;
import com.rn.activity.TabActivity;
import com.rn.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class MainFragment4 extends BaseFragment {

    @BindView(R.id.btn_recyclerview)
    Button btnRecyclerView;
    @BindView(R.id.btn_next)
    Button btnLogin;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_4;
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.btn_recyclerview,R.id.btn_next})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_recyclerview:
                startActivity(TabActivity.class);
                break;
            case R.id.btn_next:
                startActivity(LoginActivity.class);
                break;
        }
    }

}
