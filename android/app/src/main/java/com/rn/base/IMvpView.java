package com.rn.base;

/**
 * Created by liangfj on 2018/6/5.
 */

public interface IMvpView {
    void showLoading();

    void hideLoading();

    void onApiException(Throwable e);

    void showMsg(String msg);
}
