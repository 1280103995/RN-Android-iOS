package com.rn.react.module;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.rn.R;

import java.util.HashMap;
import java.util.Map;

/**
 * 通信Module类
 * Created by Song on 2017/2/17.
 */
public class CommonModule extends ReactContextBaseJavaModule {

    private ReactApplicationContext mContext;

    /**
     * 构造方法必须实现
     * @param reactContext
     */
    public CommonModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mContext = reactContext;
    }

    /**
     * 在rn代码里面是需要这个名字来调用该类的方法
     * @return
     */
    @Override
    public String getName() {
        return "CommModule";
    }

    @ReactMethod
    public void finish(){
        if (getCurrentActivity() != null){
            getCurrentActivity().finish();
            getCurrentActivity().overridePendingTransition(0, R.anim.slide_out_right);
        }
    }

    /**
     * RN调用Native的方法
     * @param phone
     */
    @ReactMethod
    public void callPhone(String phone) {

        // 跳转到打电话界面
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 跳转需要添加flag, 否则报错
        mContext.startActivity(intent);
    }

    /**
     * Native调用RN
     * @param data
     */
    public void nativeCallRn(Object data) {
        mContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit("nativeCallRn",data);
    }

    public void nativeCallRn(String eventName, Object data) {
        mContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName,data);
    }

    /**
     * Callback 方式
     * rn调用Native,并获取返回值
     * @param msg
     * @param callback
     */
    @ReactMethod
    public void rnCallNativeFromCallback(String msg, Callback callback) {

        // 1.处理业务逻辑...
        String result = "处理结果：" + msg;
        // 2.回调RN,即将处理结果返回给RN
        callback.invoke(result);
    }

    /**
     * Promise
     * @param msg
     * @param promise
     */
    @ReactMethod
    public void rnCallNativeFromPromise(String msg, Promise promise) {

        Log.e("---","adasdasda");
        // 1.处理业务逻辑...
        String result = "处理结果：" + msg;
        // 2.回调RN,即将处理结果返回给RN
        promise.resolve(result);
    }
     
    /**
     * 向RN传递常量
     */  
    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        Map<String,Object> params = new HashMap<>();
        params.put("Constant","我是常量，传递给RN");
        return params;
    }    
}
