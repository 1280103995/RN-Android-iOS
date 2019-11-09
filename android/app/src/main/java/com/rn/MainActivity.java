package com.rn;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.KeyEvent;
import android.widget.Toast;

import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.google.android.material.tabs.TabLayout;
import com.gyf.barlibrary.ImmersionBar;
import com.rn.adapter.TabPagerAdapter;
import com.rn.fragment.MainFragment1;
import com.rn.fragment.MainFragment2;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*隐藏返回按钮*/
//        setGoBackVisible(View.GONE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();

        ImmersionBar.with(this)
                .navigationBarColor("#FFFFFF")
                .navigationBarDarkIcon(true) //导航栏图标是深色，不写默认为亮色
                .fitsSystemWindows(true)
                .init();
    }

    private void initView() {
        listFragment.add(new MainFragment1());
//        listFragment.add(new MainFragment2());
        listFragment.add(new MainFragment3());
        listFragment.add(new MainFragment4());

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
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

    private long mExitTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
