package com.rn.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by liangfj on 2018/7/1.
 */

public class CountButton extends AppCompatTextView implements View.OnClickListener {

    /*
     倒计时时长,默认计时时间
     */
    private long defaultTime = 60 * 1000;
    private long time = defaultTime;

    /*
    开始执行计时的类,可以每间隔一秒执行任务
     */
    private Timer timer;

    /*
    执行的任务
     */
    private TimerTask task;

    /*
    默认文案
     */
    private String defaultText = "获取验证码";
    /*
    计时完成之后显示的文案
     */
    private String finishText = "获取验证码";

    private boolean hasCustomDownText;
    private String customDownText;//自定义倒计时文案

    /*
    点击事件
     */
    private OnClickListener onClickListener;
    private OnCountDownEndListener countDownEndListener;
    private CountDownHandler mHandler;

    public CountButton(Context context) {
        super(context);
        initView();
    }

    public CountButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CountButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        if (!TextUtils.isEmpty(getText())) {
            defaultText = getText().toString().trim();
        }
        this.setText(defaultText);
//        this.setTextColor(ContextCompat.getColor(getContext(),R.color.text_view_selector_red_gray));
//        this.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.zac_base_project_selector_btn_red_radius));
        this.getPaint().setAntiAlias(true);//抗锯齿
        setOnClickListener(this);
        mHandler = new CountDownHandler(this);
    }

    /*
    更新显示文案
     */
    private static class CountDownHandler extends Handler{

        private WeakReference<CountButton> reference;

        public CountDownHandler(CountButton button) {
            reference = new WeakReference<>(button);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            CountButton button = reference.get();
            if (button.hasCustomDownText){
                button.setText(button.customDownText + button.time / 1000 + "s");
            }else {
                button.setText(button.time / 1000 + "s");
            }

            button.setPadding(40, 0, 40, 0);
            button.time -= 1000;
            if (button.time < 0) {
                if (button.countDownEndListener != null) {
                    button.countDownEndListener.onCountDownEnd();
                }
                button.setEnabled(true);
                button.setText(button.finishText);
                button.clearTimer();
                button.time = button.defaultTime;
            }
        }
    }

    /*
    清除倒计时
     */
    private void clearTimer() {
        if (task != null) {
            task.cancel();
            task = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /*
    初始化时间
     */
    private void initTimer() {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(1);
            }
        };
    }

    /**
     * 当activity或者fragment消亡的时候清除倒计时
     */
    @Override
    protected void onDetachedFromWindow() {
        clearTimer();
        mHandler.removeCallbacksAndMessages(null);
        super.onDetachedFromWindow();
    }

    public void setDefaultText(String defaultText) {
        this.defaultText = defaultText;
    }

    public void setFinishText(String finishText) {
        this.finishText = finishText;
    }

    /**
     * 传递秒值
     * @param defaultTime
     */
    public void setDefaultTime(long defaultTime) {
        this.defaultTime = defaultTime * 1000; //因为倒计时计算是按毫秒计算，所以秒要转成毫秒
        this.time = this.defaultTime;
    }

    public void setCustomDownText(String text){
        hasCustomDownText = true;
        customDownText = text;
    }

    @Override
    public void onClick(View view) {
        if (onClickListener != null) {
            onClickListener.onClick(view);
        }
    }

    public void start() {
        initTimer();
        this.setText(time / 1000 + "s");
        this.setEnabled(false);
        timer.schedule(task, 0, 1000);
    }

    /**
     * 重置
     * 如果网络请求出错了，需要重置，不然会显示倒计时卡住
     */
    public void reset() {
        clearTimer();
        CountButton.this.setEnabled(true);
        CountButton.this.setText(defaultText);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        if (l instanceof CountButton) {
            super.setOnClickListener(l);
        } else {
            this.onClickListener = l;
        }
    }

    public interface OnCountDownEndListener{
        void onCountDownEnd();
    }

    public void setOnCountDownEndListener(OnCountDownEndListener l){
        countDownEndListener = l;
    }
}

