package com.rn.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetWorkUtil {

    /**
     * 网络监测
     *
     * @param context
     * @return
     */
    public static boolean isNetWorking(Context context) {
        boolean flag = checkNet(context);
        if (!flag) {
            Toast.makeText(context, "当前设备网络异常，请检查后再重试！", Toast.LENGTH_SHORT).show();
        }
        return flag;
    }

    private static boolean checkNet(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

}
