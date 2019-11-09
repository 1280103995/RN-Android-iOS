package com.rn.react.module;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;

public class BaseModule extends ReactContextBaseJavaModule {

    protected ReactApplicationContext mContext;

    /**
     * 构造方法必须实现
     * @param reactContext
     */
    public BaseModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mContext = reactContext;
    }

    @NonNull
    @Override
    public String getName() {
        return getClass().getSimpleName();
    }
}
