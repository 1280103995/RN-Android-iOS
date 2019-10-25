package com.rn.react;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.ReactRootView;
import com.facebook.react.devsupport.DoubleTapReloadRecognizer;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;

/**
 * Created by liangfj on 2018/5/17.
 * 宿主Activity需要重载实现 onBackPressed()、invokeDefaultOnBackPressed()、onKeyUp()方法，详见MainActivity
 */

//TODO 权限没加

public abstract class ReactFragment extends Fragment {

    private ReactRootView mReactRootView;
    @Nullable
    private DoubleTapReloadRecognizer mDoubleTapReloadRecognizer;

    public abstract Bundle getLaunchOptions();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mDoubleTapReloadRecognizer = new DoubleTapReloadRecognizer();
        mReactRootView = XReactInstanceManager.getInstance().createRootView(context, getLaunchOptions());
    }

    @Override
    public ReactRootView onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return mReactRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        XReactInstanceManager.getInstance().onHostResume(getActivity(), (DefaultHardwareBackBtnHandler) getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        XReactInstanceManager.getInstance().onHostPause(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        XReactInstanceManager.getInstance().onHostDestroy(getActivity());
        if (mReactRootView != null) {
            mReactRootView.unmountReactApplication();
            mReactRootView = null;
        }
    }

    /**
     * 在宿主Activity中重写onKeyUp()
     * <p>
     * 示例：
     *
     * @param keyCode keyCode
     * @param event   event
     * @return true if we handled onKeyUp
     * @Override public boolean onKeyUp(int keyCode, KeyEvent event) {
     * boolean handled = false;
     * Fragment activeFragment = listFragment.get(mViewPager.getCurrentItem());
     * if (activeFragment instanceof ReactFragment) {
     * handled = ((ReactFragment) activeFragment).onKeyUp(keyCode, event);
     * }
     * return handled || super.onKeyUp(keyCode, event);
     * }
     * <p>
     * <p>
     * Helper to forward onKeyUp commands from our host Activity.
     * This allows ReactFragment to handle double tap reloads and dev menus
     */
    @SuppressWarnings("unused")
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        boolean handled = false;

        if (keyCode == KeyEvent.KEYCODE_MENU) {
            XReactInstanceManager.getInstance().showDevOptionsDialog();
            handled = true;
        }
        boolean didDoubleTapR = Assertions.assertNotNull(mDoubleTapReloadRecognizer).didDoubleTapR(keyCode, getActivity().getCurrentFocus());
        if (didDoubleTapR) {
            XReactInstanceManager.getInstance().handleReloadJS();
            handled = true;
        }

        return handled;
    }
}
