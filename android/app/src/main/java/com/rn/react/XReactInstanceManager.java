package com.rn.react;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactInstanceManagerBuilder;
import com.facebook.react.ReactPackage;
import com.facebook.react.ReactRootView;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.react.uimanager.UIImplementationProvider;
import com.facebook.soloader.SoLoader;
import com.rn.BuildConfig;
import com.rn.react.module.CommonPackage;
import com.swmansion.gesturehandler.react.RNGestureHandlerEnabledRootView;
import com.swmansion.gesturehandler.react.RNGestureHandlerPackage;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

/**
 * JSBundle加载唯一实例
 * <p>
 * Created by liangfj on 2018/5/17.
 */

public class XReactInstanceManager {

    //在index.js中注册的组件名称
    private static final String MODULE_NAME = "RN";
    private volatile static XReactInstanceManager mInstance;
    private ReactInstanceManager mReactInstanceManager;
    private LifecycleState mLifecycleState = LifecycleState.BEFORE_CREATE;
    private static final CommonPackage mCommonPackage = new CommonPackage();

    /**
     * 添加 RN Package
     *
     * @return
     */
    private List<ReactPackage> getPackages() {
        return Arrays.asList(
                new MainReactPackage(),
                mCommonPackage,
                new RNGestureHandlerPackage()
        );
    }

    private @Nullable
    String getJSBundleFile() {
        return null;
    }

    private @Nullable
    String getBundleAssetName() {
        return "index.android.bundle";
    }

    private XReactInstanceManager() {}

    public static XReactInstanceManager getInstance() {
        if (mInstance == null) {
            synchronized (XReactInstanceManager.class) {
                if (mInstance == null) {
                    mInstance = new XReactInstanceManager();
                }
            }
        }
        return mInstance;
    }

    public void init(Application application) {
        SoLoader.init(application, false);
        initReactInstanceManager(application);
    }

    private void initReactInstanceManager(Application application) {
        if (mReactInstanceManager == null) {
            ReactInstanceManagerBuilder builder = ReactInstanceManager.builder()
                    .setApplication(application)
                    .setJSMainModulePath("index")//js入口模块名字
                    .setUseDeveloperSupport(BuildConfig.DEBUG)
                    .setUIImplementationProvider(new UIImplementationProvider())
                    .setInitialLifecycleState(mLifecycleState)
                    .addPackages(getPackages());

            String jsBundleFile = getJSBundleFile();
            if (jsBundleFile != null) {
                builder.setJSBundleFile(jsBundleFile);
            } else {
                builder.setBundleAssetName(Assertions.assertNotNull(getBundleAssetName()));
            }

            mReactInstanceManager = builder.build();

            if (!mReactInstanceManager.hasStartedCreatingInitialContext()) {
                mReactInstanceManager.createReactContextInBackground();
            }
        }
    }

    public CommonPackage getCommonPackage() {
        return mCommonPackage;
    }

    public ReactInstanceManager getReactInstanceManager() {
        return mReactInstanceManager;
    }

    public ReactRootView createRootView(Context context, Bundle initialProps) {
//        ReactRootView view = new ReactRootView(context);
        //TODO 如果用了react-navigation 3.x
        ReactRootView view = new RNGestureHandlerEnabledRootView(context);
        view.startReactApplication(mReactInstanceManager, MODULE_NAME, initialProps);
        return view;
    }

    public void showDevOptionsDialog() {
        mReactInstanceManager.showDevOptionsDialog();
    }

    public void handleReloadJS() {
        mReactInstanceManager.getDevSupportManager().handleReloadJS();
    }

    public boolean onBackPressed() {
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onBackPressed();
            return true;
        }
        return false;
    }

    public void onHostResume(Activity activity, DefaultHardwareBackBtnHandler handler) {
        mReactInstanceManager.onHostResume(activity, handler);
        mLifecycleState = LifecycleState.RESUMED;
    }

    public void onHostPause(Activity activity) {
        mReactInstanceManager.onHostPause(activity);
        mLifecycleState = LifecycleState.BEFORE_RESUME;
    }

    public void onHostDestroy(Activity activity) {
        mReactInstanceManager.onHostDestroy(activity);
        mLifecycleState = LifecycleState.BEFORE_CREATE;
    }

}
