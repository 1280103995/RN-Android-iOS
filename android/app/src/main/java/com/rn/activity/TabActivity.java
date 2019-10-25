package com.rn.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.rn.R;
import com.rn.adapter.TabPagerAdapter;
import com.rn.base.BaseActivity;
import com.rn.fragment.TabFragment1;
import com.rn.fragment.TabFragment2;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 测试与子 TabFragment1 一起使用 MVP 的情况
 */
public class TabActivity extends BaseActivity {

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.tv_info)
    TextView mTextView;
    @BindView(R.id.btn_get)
    Button mButton;
    @BindView(R.id.btn_send)
    Button btnSend;
    private List<Fragment> listFragment = new ArrayList<>();
    @BindArray(R.array.tab)
    String[] tabTitle;

    @Override
    protected boolean titleBarVisible() {
        return false;
    }

    @Override
    protected void statusBarLightFont() {
        mImmersionBar
                .statusBarDarkFont(false)
                .statusBarColor("#00000000")
                .fitsSystemWindows(false)
                .keyboardEnable(true)
                .init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tab;
    }

    @Override
    protected void initView() {

        listFragment.add(new TabFragment1());
        listFragment.add(new TabFragment2());

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    mCollapsingToolbarLayout.setTitle("BaseMVPActivity  + BaseMVPFragment");
                }else {
                    mCollapsingToolbarLayout.setTitle("BaseMVPActivity  + BaseFragment");
                }

                //tab被选的时候回调
                mViewPager.setCurrentItem(tab.getPosition(),true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //tab未被选择的时候回调
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //tab重新选择的时候回调
            }
        });
        mViewPager.setOffscreenPageLimit(listFragment.size());
        mViewPager.setAdapter(new TabPagerAdapter(getSupportFragmentManager(),listFragment,tabTitle));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.btn_get, R.id.btn_send})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_get:

                break;
            case R.id.btn_send:
                EventBus.getDefault().post("宿主 Activity 给我发消息了");
                break;
        }

    }
}
