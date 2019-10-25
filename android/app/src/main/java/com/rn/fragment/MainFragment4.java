package com.rn.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rn.R;
import com.rn.activity.LoginActivity;
import com.rn.activity.TabActivity;
import com.rn.base.BaseFragment;
import com.rn.util.SpanUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class MainFragment4 extends BaseFragment {

    @BindView(R.id.btn_recyclerview)
    Button btnRecyclerView;
    @BindView(R.id.tv_tip)
    TextView mTip;
    @BindView(R.id.btn_next)
    Button btnGoLogin;
    String tip;

    public static MainFragment4 newInstance(String userId) {
        MainFragment4 fragment = new MainFragment4();
        Bundle bundle = new Bundle();
        bundle.putString("tip", userId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tip = getArguments().getString("tip");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_4;
    }

    @Override
    protected void initView() {
        mTip.setText(new SpanUtils()
                .append(tip).setForegroundColor(Color.BLUE)
                .append("变色").setForegroundColor(Color.GREEN)
                .create()
        );
    }

    @Override
    public void onBaseEvent(Object event) {
        super.onBaseEvent(event);
        mTip.setText("收到EventBus消息了-->" + event);
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
