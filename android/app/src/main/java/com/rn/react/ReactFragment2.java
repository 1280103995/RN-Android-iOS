/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.rn.react;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactRootView;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.devsupport.DoubleTapReloadRecognizer;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.modules.core.PermissionAwareActivity;
import com.facebook.react.modules.core.PermissionListener;

/**
 * A {@link Fragment} which loads a React Native Component from your React Native JS Bundle.
 * <p>
 * NOTE: You can achieve the same behaviour with an Activity by extending
 * {@link com.facebook.react.ReactActivity}
 */
public abstract class ReactFragment2 extends Fragment implements PermissionAwareActivity {

    /*该值需与RN在index文件中注册的组件名称保持一致*/
    public static String MODULE_NAME = "RN";

    public static final int REQUEST_OVERLAY_CODE = 1111;

    private static final String REDBOX_PERMISSION_MESSAGE =
            "Overlay permissions need to be granted in order for react native apps to run in dev mode.";
    private static final String REDBOX_PERMISSION_GRANTED_MESSAGE =
            "Overlay permissions have been granted.";


    private ReactRootView mReactRootView;

    @Nullable
    private DoubleTapReloadRecognizer mDoubleTapReloadRecognizer;

    @Nullable
    private PermissionListener mPermissionListener;

    public abstract Bundle getLaunchOptions();

    // region Lifecycle

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDoubleTapReloadRecognizer = new DoubleTapReloadRecognizer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        boolean needToEnableDevMenu = false;

        if (getReactNativeHost().getUseDeveloperSupport()
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !Settings.canDrawOverlays(getContext())) {
            // Get permission to show redbox in dev builds.
            needToEnableDevMenu = true;
            Intent serviceIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getContext().getPackageName()));
            Toast.makeText(getContext(), REDBOX_PERMISSION_MESSAGE, Toast.LENGTH_LONG).show();
            startActivityForResult(serviceIntent, REQUEST_OVERLAY_CODE);
        }

        mReactRootView = new ReactRootView(getContext());

        if (!needToEnableDevMenu) {
            mReactRootView.startReactApplication(
                    getReactNativeHost().getReactInstanceManager(),
                    MODULE_NAME,
                    getLaunchOptions());
        }

        return mReactRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getReactNativeHost().hasInstance()) {
            getReactNativeHost().getReactInstanceManager().onHostResume(getActivity(), (DefaultHardwareBackBtnHandler) getActivity());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getReactNativeHost().hasInstance()) {
            getReactNativeHost().getReactInstanceManager().onHostPause(getActivity());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mReactRootView != null) {
            mReactRootView.unmountReactApplication();
            mReactRootView = null;
        }
        if (getReactNativeHost().hasInstance()) {
            ReactInstanceManager reactInstanceMgr = getReactNativeHost().getReactInstanceManager();

            // onDestroy may be called on a ReactFragment after another ReactFragment has been
            // created and resumed with the same React Instance Manager. Make sure we only clean up
            // host's React Instance Manager if no other React Fragment is actively using it.
            if (reactInstanceMgr.getLifecycleState() != LifecycleState.RESUMED) {
                reactInstanceMgr.onHostDestroy(getActivity());
                getReactNativeHost().clear();
            }
        }
    }

    // endregion

    /**
     * This currently only checks to see if we've enabled the permission to draw over other apps.
     * This is only used in debug/developer mode and is otherwise not used.
     *
     * @param requestCode Code that requested the activity
     * @param resultCode  Code which describes the result
     * @param data        Any data passed from the activity
     */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_OVERLAY_CODE
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && Settings.canDrawOverlays(getContext())) {
            mReactRootView.startReactApplication(
                    getReactNativeHost().getReactInstanceManager(),
                    MODULE_NAME,
                    getLaunchOptions());
            Toast.makeText(getContext(), REDBOX_PERMISSION_GRANTED_MESSAGE, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mPermissionListener != null &&
                mPermissionListener.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            mPermissionListener = null;
        }
    }

    // region PermissionAwareActivity

    @Override
    public int checkPermission(String permission, int pid, int uid) {
        return getActivity().checkPermission(permission, pid, uid);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public int checkSelfPermission(String permission) {
        return getActivity().checkSelfPermission(permission);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void requestPermissions(String[] permissions, int requestCode, PermissionListener listener) {
        mPermissionListener = listener;
        requestPermissions(permissions, requestCode);
    }

    // endregion

    // region Helpers

    /**
     * Helper to forward hardware back presses to our React Native Host
     */
    public void onBackPressed() {
        if (getReactNativeHost().hasInstance()) {
            getReactNativeHost().getReactInstanceManager().onBackPressed();
        }
    }

    /**
     * Helper to forward onKeyUp commands from our host Activity.
     * This allows ReactFragment to handle double tap reloads and dev menus
     *
     * @param keyCode keyCode
     * @param event   event
     * @return true if we handled onKeyUp
     */
    @SuppressWarnings("unused")
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        boolean handled = false;
        if (getReactNativeHost().getUseDeveloperSupport() && getReactNativeHost().hasInstance()) {
            if (keyCode == KeyEvent.KEYCODE_MENU) {
                getReactNativeHost().getReactInstanceManager().showDevOptionsDialog();
                handled = true;
            }
            boolean didDoubleTapR = Assertions.assertNotNull(mDoubleTapReloadRecognizer).didDoubleTapR(keyCode, getActivity().getCurrentFocus());
            if (didDoubleTapR) {
                getReactNativeHost().getReactInstanceManager().getDevSupportManager().handleReloadJS();
                handled = true;
            }
        }
        return handled;
    }

    // endregion

    /**
     * Get the {@link ReactNativeHost} used by this app. By default, assumes
     * {@link Activity#getApplication()} is an instance of {@link ReactApplication} and calls
     * {@link ReactApplication#getReactNativeHost()}. Override this method if your application class
     * does not implement {@code ReactApplication} or you simply have a different mechanism for
     * storing a {@code ReactNativeHost}, e.g. as a static field somewhere.
     */
    protected ReactNativeHost getReactNativeHost() {
        return ((ReactApplication) getActivity().getApplication()).getReactNativeHost();
    }

}
