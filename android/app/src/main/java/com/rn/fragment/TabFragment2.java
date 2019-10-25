package com.rn.fragment;

import android.widget.TextView;

import com.rn.R;
import com.rn.base.BaseFragment;

import butterknife.BindView;

/**
 * 接受宿主 Activity 发过来的消息
 */
public class TabFragment2 extends BaseFragment {

    @BindView(R.id.tv_tip)
    TextView mTip;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab2;
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onBaseEvent(Object event) {
        super.onBaseEvent(event);
        System.out.println("没收到：" + event);
        mTip.setText((String)event);
    }
}
