package com.rn.react.module;

import android.content.Intent;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.rn.R;
import com.rn.activity.RNDemoActivity;

public class CommonModule extends BaseModule {

    public CommonModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @ReactMethod
    public void finish(){
        if (getCurrentActivity() != null){
            getCurrentActivity().finish();
            getCurrentActivity().overridePendingTransition(0, R.anim.slide_out_right);
        }
    }

    @ReactMethod
    public void openNativeScreen() {
        Intent intent = new Intent(mContext, RNDemoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 跳转需要添加flag, 否则报错
        mContext.startActivity(intent);
        if (getCurrentActivity() != null) {
            getCurrentActivity().overridePendingTransition(R.anim.slide_in_right, 0);
        }
    }

}
