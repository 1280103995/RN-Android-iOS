package com.rn.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gyf.barlibrary.ImmersionBar;
import com.rn.MainActivity;
import com.rn.R;
import com.rn.util.ActivityUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import butterknife.ButterKnife;
import retrofit2.HttpException;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public abstract class BaseActivity extends AppCompatActivity implements IMvpView {

    protected boolean isTopActivity = false;//判断当前activity是否顶层，是则显示网络异常等
    protected View mRootView;//根视图
    protected RelativeLayout mTitleBar;
    protected ImageView imgBack;
    protected TextView tvTitle;
    protected ImmersionBar mImmersionBar;
    private ProgressDialog progressDialog;

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtil.addActivity(this);
        progressDialog = new ProgressDialog(this);
//        setTheme(R.style.theme);
        setContentView(getLayoutId());
        //初始化沉浸式
        if (isImmersionBarEnabled())
            initImmersionBar();
        init(savedInstanceState);
        initView();
        initData();
    }

    /**
     * 提供给一些辅助类、工具类等实例化，防止 initListener() 设置监听时报错
     *
     * @param savedInstanceState
     */
    protected void init(Bundle savedInstanceState) {

    }

    /**
     * 是否要显示标题栏
     *
     * @return
     */
    protected boolean titleBarVisible() {
        return true;
    }

    @Override
    public void setContentView(int layoutResID) {
        if (layoutResID == 0) {
            return;
        }
        if (titleBarVisible()) {
            getWindow().setBackgroundDrawable(null);
            mRootView = View.inflate(this, R.layout.activity_base, null);
            initBaseView();
            initBaseListener();
            //把子类布局加到根布局
            View childView = View.inflate(this, getLayoutId(), null);
            ((LinearLayout) mRootView).addView(childView);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            childView.setLayoutParams(lp);
        } else {
            mRootView = View.inflate(this, getLayoutId(), null);
        }
        super.setContentView(mRootView);
        ButterKnife.bind(this);
    }

    private void initBaseView() {
        mTitleBar = mRootView.findViewById(R.id.rel_title_bar);
        imgBack = mRootView.findViewById(R.id.iv_back);
        tvTitle = mRootView.findViewById(R.id.tv_title);

        if (createLeftView() != null) {
            mTitleBar.removeView(imgBack);
            View view = createLeftView();
            mTitleBar.addView(view);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) view.getLayoutParams();
            lp.addRule(RelativeLayout.CENTER_VERTICAL);
            view.setLayoutParams(lp);
        }

        if (createCenterView() != null) {
            mTitleBar.removeView(tvTitle);
            View view = createCenterView();
            mTitleBar.addView(view);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) view.getLayoutParams();
            lp.addRule(RelativeLayout.CENTER_IN_PARENT);
            view.setLayoutParams(lp);
        }

        if (createRightView() != null) {
            View view = createRightView();
            mTitleBar.addView(view);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) view.getLayoutParams();
            lp.addRule(RelativeLayout.CENTER_VERTICAL);
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            view.setLayoutParams(lp);
        }
    }

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    /**
     * 初始化状态栏
     * ImmersionBar : https://github.com/gyf-dev/ImmersionBar
     */
    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        statusBarLightFont();
    }

    /**
     * 状态栏深色字体
     */
    protected void statusBarDarkFont() {
        mImmersionBar
                .statusBarDarkFont(true, 0.2f)
                .statusBarColor("#FFFFFF")
                .navigationBarColor("#FFFFFF")
                .navigationBarDarkIcon(true) //导航栏图标是深色，不写默认为亮色
                .fitsSystemWindows(true)
                .keyboardEnable(true)
                .init();
    }

    /**
     * 状态栏亮色字体
     */
    protected void statusBarLightFont() {
        mImmersionBar
                .statusBarDarkFont(false)
                .statusBarColor(R.color.colorPrimary)
                .navigationBarColor("#FFFFFF")
                .navigationBarDarkIcon(true)
                .fitsSystemWindows(true)
                .keyboardEnable(true)
                .init();
    }

    /**
     * 初始化标题栏点击事件
     */
    private void initBaseListener() {
        /*返回按钮*/
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBackClick();
            }
        });
    }

    /**
     * 返回按钮监听
     */
    protected void goBackClick() {
        onBackPressed();
    }

    /**
     * 子类自己渲染左边的view，return View
     *
     * @return
     */
    protected View createLeftView() {
        return null;
    }

    /**
     * 子类自己渲染中间的view，return View
     *
     * @return
     */
    protected View createCenterView() {
        return null;
    }

    /**
     * 子类自己渲染右边的view，return View
     *
     * @return
     */
    protected View createRightView() {
        return null;
    }

    /**
     * 移除右上角控件
     */
    protected void removeRightView(View view) {
        if (view != null && mTitleBar != null)
            mTitleBar.removeView(view);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    protected void setTitle(String title) {
        if (!TextUtils.isEmpty(title) && tvTitle != null)
            tvTitle.setText(title);
    }

    /**
     * @hide
     */
    @IntDef({VISIBLE, INVISIBLE, GONE})
    @Retention(RetentionPolicy.SOURCE)
    private @interface Visibility {
    }

    /**
     * 设置返回按钮状态
     */
    protected void setGoBackVisible(@Visibility int visibility) {
        if (imgBack != null)
            imgBack.setVisibility(visibility);
    }

    /**
     * 设置返回按钮图片
     *
     * @param drawable
     */
    protected void setGoBackImg(int drawable) {
        if (imgBack != null)
            imgBack.setImageResource(drawable);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isTopActivity = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isTopActivity = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 显示Toast
     *
     * @param msg
     */
    protected void showToast(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 接收订阅的事件
     * 子类实现该方法取值
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBaseEvent(Object event) {

    }

    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * 携带数据的页面跳转
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, 0);
    }

    /**
     * 含有Bundle通过Class打开编辑界面
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.slide_in_right, 0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void finish() {
        super.finish();
        if (!(ActivityUtil.getTopActivity() instanceof MainActivity)) {
            overridePendingTransition(0, R.anim.slide_out_right);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityUtil.removeActivity(this);
        if (mImmersionBar != null)
            mImmersionBar.destroy();
    }

    @Override
    public void showLoading() {
        progressDialog.setMessage("正在加载...");
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onApiException(Throwable e) {
        if (!isNetWorking(this)) {
            showToast("网络不可用");
        } else if (e instanceof SocketTimeoutException) {
            showToast("服务器响应超时");
        } else if (e instanceof ConnectException) {
            showToast("服务器请求超时");
        } else if (e instanceof HttpException) {
            showToast("服务器异常");
        } else {
            showToast("未知异常：" + e.getMessage());
        }
    }

    @Override
    public void showMsg(String msg) {
        showToast(msg);
    }

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
