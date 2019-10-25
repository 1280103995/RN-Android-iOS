package com.rn.fragment;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.rn.R;
import com.rn.adapter.TabPagerAdapter;
import com.rn.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class Main1Fragment1 extends BaseFragment {

    ViewPager mViewPager;
    TabLayout mTabLayout;
    String[] tabTitle;
    private List<Fragment> listFragment = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.mian_1_fragment_1;
    }

    @Override
    protected void initView() {
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);
        tabTitle = getContext().getResources().getStringArray(R.array.homePage);

        listFragment.add(new TestFragment().newInstance("第一个"));
        listFragment.add(new TestFragment().newInstance("第二个"));
        listFragment.add(new TestFragment().newInstance("第三个"));
        listFragment.add(new TestFragment().newInstance("第四个"));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
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
        mViewPager.setAdapter(new TabPagerAdapter(getChildFragmentManager(),listFragment,tabTitle));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        System.out.println("测试: 第一次可见");
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        System.out.println( "测试: 每次可见");
    }

    @Override
    public void onFragmentPause() {
        super.onFragmentPause();
        System.out.println("测试: 每次不可见");
    }
}
