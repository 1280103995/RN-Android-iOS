package com.rn.base;

import android.os.Bundle;

/**
 * Created by liangfj on 2018/6/5.
 */

public interface IMvpPresenter<V extends IMvpView> {

    void onMvpAttachView(V view, Bundle savedInstanceState);

    void onMvpStart();

    void onMvpResume();

    void onMvpPause();

    void onMvpStop();

    void onMvpSaveInstanceState(Bundle savedInstanceState);

    void onMvpDestroy();
}
