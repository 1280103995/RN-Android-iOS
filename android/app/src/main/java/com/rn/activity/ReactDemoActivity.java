package com.rn.activity;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.collection.ArrayMap;

import com.rn.R;
import com.rn.base.BaseActivity;
import com.rn.constants.FileConstant;
import com.rn.entity.RNRouteInfo;
import com.rn.hotupdate.HotUpdate;
import com.rn.react.XReactInstanceManager;

import org.greenrobot.eventbus.EventBus;

public class ReactDemoActivity extends BaseActivity {

    private long mDownLoadId;
    private CompleteReceiver localReceiver;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_react_demo;
    }

    @Override
    protected void initView() {
        setTitle("RN相关");
    }

    @Override
    protected void initData() {
//        ScreenAdapterTools.getInstance().reset(this);//如果希望android7.0分屏也适配的话,加上这句
//        ScreenAdapterTools.getInstance().loadView((ViewGroup) getWindow().getDecorView());
        registeReceiver();
    }

    @Override
    protected View createRightView() {
        TextView textView = new TextView(this);
        textView.setText("菜单");
        textView.setTextColor(Color.WHITE);
//         textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.sp_size_16));
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    /**
     * 下载更新包
     * @param v
     */
    public void load(View v) {
        checkVersion();
    }

    /**
     * 跳转到RN界面
     * @param v
     */
    public void skip(View v) {
        RNRouteInfo route = new RNRouteInfo();
        route.setRouteName("Home");
        startActivity(RNActivity.class, route.getBundle(route));
    }

    public void skip2(View v) {
        RNRouteInfo route = new RNRouteInfo();
        route.setRouteName("One");
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("initTitle", "Android标题");
        route.setRouteParams(map);
        startActivity(RNActivity.class, route.getBundle(route));
    }

    public void skip3(View v) {
        RNRouteInfo route = new RNRouteInfo();
        route.setRouteName("Login");
        startActivity(RNActivity.class, route.getBundle(route));
    }

    /**
     * 向RN发送消息
     * @param v
     */
    public void sendMsgToRN(View v) {
        XReactInstanceManager.getInstance().getCommonPackage().mModule.nativeCallRn("hello");
    }

    public void sendMsg2Fragment4(View v){
        EventBus.getDefault().post("Activity 给我发消息了");
    }

    /**
     * 检查版本号
     */
    private void checkVersion() {
        // 默认有最新版本
        Toast.makeText(this, "开始下载", Toast.LENGTH_SHORT).show();
        downLoadBundle();
    }

    /**
     * 下载最新Bundle
     */
    private void downLoadBundle() {

        // 1.下载前检查SD卡是否存在更新包文件夹
        HotUpdate.checkPackage(getApplicationContext(), FileConstant.LOCAL_FOLDER);
        // 2.下载
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager
                .Request(Uri.parse(FileConstant.JS_BUNDLE_REMOTE_URL));
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE| DownloadManager.Request.NETWORK_WIFI);
        request.setDestinationUri(Uri.parse("file://"+ FileConstant.JS_PATCH_LOCAL_PATH));
        mDownLoadId = downloadManager.enqueue(request);
    }

    /**
     * 注册广播
     */
    private void registeReceiver() {
        localReceiver = new CompleteReceiver();
        registerReceiver(localReceiver,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    public class CompleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            long completeId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1);
            if(completeId == mDownLoadId) {
                HotUpdate.handleZIP(getApplicationContext());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(localReceiver);
    }

}
