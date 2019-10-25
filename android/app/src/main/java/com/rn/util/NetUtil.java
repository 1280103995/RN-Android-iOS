package com.rn.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

import com.rn.base.App;

/**
 * 当前网络环境(2g 3g wifi)
 */
public class NetUtil {

    /**
     * Network type is unknown
     */
    public static final int NETWORK_TYPE_UNKNOWN = 0;
    /**
     * Current network is GPRS
     */
    public static final int NETWORK_TYPE_GPRS = 1;
    /**
     * Current network is EDGE
     */
    public static final int NETWORK_TYPE_EDGE = 2;
    /**
     * Current network is UMTS
     */
    public static final int NETWORK_TYPE_UMTS = 3;
    /**
     * Current network is CDMA: Either IS95A or IS95B
     */
    public static final int NETWORK_TYPE_CDMA = 4;
    /**
     * Current network is EVDO revision 0
     */
    public static final int NETWORK_TYPE_EVDO_0 = 5;
    /**
     * Current network is EVDO revision A
     */
    public static final int NETWORK_TYPE_EVDO_A = 6;
    /**
     * Current network is 1xRTT
     */
    public static final int NETWORK_TYPE_1xRTT = 7;
    /**
     * Current network is HSDPA
     */
    public static final int NETWORK_TYPE_HSDPA = 8;
    /**
     * Current network is HSUPA
     */
    public static final int NETWORK_TYPE_HSUPA = 9;
    /**
     * Current network is HSPA
     */
    public static final int NETWORK_TYPE_HSPA = 10;
    /**
     * Current network is iDen
     */
    public static final int NETWORK_TYPE_IDEN = 11;
    /**
     * Current network is EVDO revision B
     */
    public static final int NETWORK_TYPE_EVDO_B = 12;
    /**
     * Current network is LTE
     */
    public static final int NETWORK_TYPE_LTE = 13;
    /**
     * Current network is eHRPD
     */
    public static final int NETWORK_TYPE_EHRPD = 14;
    /**
     * Current network is HSPA+
     */
    public static final int NETWORK_TYPE_HSPAP = 15;

    public static final int NETWORK_TYPE_TDS_HSDPA = 18;
    /**
     * Unknown network class. {@hide}
     */
    public static final String NETWORK_CLASS_UNKNOWN = "UNKNOWN";
    /**
     * Class of broadly defined "2G" networks.
     */
    public static final String NETWORK_CLASS_2_G = "2G";
    /**
     * Class of broadly defined "3G" networks.
     */
    public static final String NETWORK_CLASS_3_G = "3G";
    /**
     * Class of broadly defined "4G" networks
     */
    public static final String NETWORK_CLASS_4_G = "4G";

    /**
     * 检查用户的网络:是否有网络
     */
    public static boolean checkNet() {
        Context context = App.getInstance().getApplicationContext();
        // 判断：WIFI链接
        boolean isWIFI = isWIFIConnection(context);
        // 判断：Mobile链接
        boolean isMOBILE = isMOBILEConnection(context);
        boolean isNetwork = isNetworkConnected(context);
        if (!isWIFI && !isMOBILE && !isNetwork) {
            return false;
        }

        return true;
    }

    /**
     * isNetworkConnected( 是否有网络 )
     */
    private static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断：Mobile链接
     *
     * @param context
     * @return
     */
    private static boolean isMOBILEConnection(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (networkInfo != null) {
            return networkInfo.isConnected();
        }
        return false;
    }

    /**
     * 判断：WIFI链接
     *
     * @param context
     * @return
     */
    public static boolean isWIFIConnection(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo != null) {
            return networkInfo.isConnected();
        }
        return false;
    }

    public static final String NETWORK_TYPE_NONE = "off"; // 断网情况
    public static final String NETWORK_TYPE_WIFI = "wifi"; // WiFi模式
    public static final String NETWOKR_TYPE_MOBILE = "gprs"; // gprs模式

    /**
     * 获取当前网络状态的类型
     *
     * @return 返回网络类型
     */
    public static String getCurrentNetType() {
        ConnectivityManager connManager = (ConnectivityManager) App.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI); // wifi
        NetworkInfo gprs = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE); // gprs
        if (wifi != null && wifi.getState() == State.CONNECTED) {
            return NETWORK_TYPE_WIFI;
        } else if (gprs != null && gprs.getState() == State.CONNECTED) {
            return getNetworkClass(gprs.getSubtype());
        }
        return NETWORK_TYPE_NONE;
    }

    /**
     * 获取网络状态
     *
     * @param networkType 当前网络模式
     * @return
     */
    public static String getNetworkClass(int networkType) {
        switch (networkType) {
            case NETWORK_TYPE_GPRS:
            case NETWORK_TYPE_EDGE:
            case NETWORK_TYPE_CDMA:
            case NETWORK_TYPE_1xRTT:
            case NETWORK_TYPE_IDEN:
                return NETWORK_CLASS_2_G;
            case NETWORK_TYPE_UMTS:
            case NETWORK_TYPE_EVDO_0:
            case NETWORK_TYPE_EVDO_A:
            case NETWORK_TYPE_HSDPA:
            case NETWORK_TYPE_HSUPA:
            case NETWORK_TYPE_HSPA:
            case NETWORK_TYPE_EVDO_B:
            case NETWORK_TYPE_EHRPD:
            case NETWORK_TYPE_HSPAP:
            case NETWORK_TYPE_TDS_HSDPA:
                return NETWORK_CLASS_3_G;
            case NETWORK_TYPE_LTE:
                return NETWORK_CLASS_4_G;
            default:
                return NETWORK_CLASS_UNKNOWN;
        }
    }
}
