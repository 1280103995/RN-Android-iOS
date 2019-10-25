package com.rn.react;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.Nullable;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.ReactRootView;
import com.facebook.react.devsupport.DoubleTapReloadRecognizer;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;

/**
 * Created by liangfj on 2018/5/17.
 */

//TODO 权限没加

public abstract class ReactActivity extends Activity implements DefaultHardwareBackBtnHandler {

    private ReactRootView mReactRootView;
    @Nullable
    private DoubleTapReloadRecognizer mDoubleTapReloadRecognizer;

    public abstract Bundle getLaunchOptions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDoubleTapReloadRecognizer = new DoubleTapReloadRecognizer();
        mReactRootView = XReactInstanceManager.getInstance().createRootView(this, getLaunchOptions());
        setContentView(mReactRootView);
    }

    @Override
    public void onResume() {
        super.onResume();
        XReactInstanceManager.getInstance().onHostResume(this, this);
    }

    @Override
    public void onPause() {
        super.onPause();
        XReactInstanceManager.getInstance().onHostPause(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mReactRootView != null) {
            mReactRootView.unmountReactApplication();
            mReactRootView = null;
        }
		XReactInstanceManager.getInstance().onHostDestroy(this);
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

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        boolean handled = false;

        if (keyCode == KeyEvent.KEYCODE_MENU) {
            XReactInstanceManager.getInstance().showDevOptionsDialog();
            handled = true;
        }
        boolean didDoubleTapR = Assertions.assertNotNull(mDoubleTapReloadRecognizer).didDoubleTapR(keyCode, getCurrentFocus());
        if (didDoubleTapR) {
            XReactInstanceManager.getInstance().handleReloadJS();
            handled = true;
        }

        return handled || super.onKeyUp(keyCode, event);
    }
}
