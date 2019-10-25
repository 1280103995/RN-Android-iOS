package com.rn.base;

import android.os.Bundle;

/**
 * Created by liangfj on 2018/6/5.
 */

public abstract class BaseMvpActivity<P extends IMvpPresenter> extends BaseActivity {

    protected P presenter;

    protected abstract P createPresenter();

    /**
     * 子类在此方法中初始化数据，而不是在 initData() 方法中（如果 BaseActivity 封装了该方法）
     */
    protected abstract void initMvpData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = createPresenter();
        if (presenter == null) {
            throw new NullPointerException("Presenter is null! Do you return null in createPresenter()?");
        }
        presenter.onMvpAttachView(this, savedInstanceState);
        initMvpData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (presenter != null) {
            presenter.onMvpStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.onMvpResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (presenter != null) {
            presenter.onMvpPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (presenter != null) {
            presenter.onMvpStop();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (presenter != null) {
            presenter.onMvpSaveInstanceState(outState);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onMvpDestroy();
        }
    }

}
