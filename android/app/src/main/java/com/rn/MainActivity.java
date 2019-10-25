package com.rn;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.KeyEvent;

import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.google.android.material.tabs.TabLayout;
import com.rn.adapter.TabPagerAdapter;
import com.rn.fragment.Main1Fragment1;
import com.rn.fragment.MainFragment1;
import com.rn.fragment.MainFragment3;
import com.rn.fragment.MainFragment4;
import com.rn.react.ReactFragment;
import com.rn.react.XReactInstanceManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements DefaultHardwareBackBtnHandler {

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    private List<Fragment> listFragment = new ArrayList<>();
    @BindArray(R.array.homePage)
    String[] tabTitle;

    private int index = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*隐藏返回按钮*/
//        setGoBackVisible(View.GONE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        listFragment.add(new MainFragment1());
        listFragment.add(new Main1Fragment1());
        listFragment.add(new MainFragment3());
        listFragment.add(new MainFragment4().newInstance("fragment4"));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                index = tab.getPosition();
                if (tab.getPosition() == 0) {
                    setTitle("BaseActivity + BaseMVPFragment");
                }else {
                    setTitle("BaseActivity + BaseFragment");
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
    public void onBackPressed() {
        if (!XReactInstanceManager.getInstance().onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }

    /**
     * Forward onKeyUp events to the ReactFragment in order to handle double tap reloads
     * and dev menus
     *
     * @param keyCode
     * @param event
     * @return true if event was handled
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        boolean handled = false;
        Fragment activeFragment = listFragment.get(mViewPager.getCurrentItem());
        if (activeFragment instanceof ReactFragment) {
            handled = ((ReactFragment) activeFragment).onKeyUp(keyCode, event);
        }
        return handled || super.onKeyUp(keyCode, event);
    }

}
