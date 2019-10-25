package com.rn.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.PaintDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * Created by ganba on 2017/12/6.
 * 分享弹窗
 */

public class PopupWindowShare extends PopupWindow implements OnClickListener {

    private View mView;
    private OnClickDismissListener mListener;

    /**
     * 定义一个接口，公布出去 在Activity中操作按钮的单击事件
     */
    public interface OnClickDismissListener {
        void setOnItemClick(View v);
        void onDismiss();
    }

    public void setOnClickDismissListener(OnClickDismissListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (mListener != null) {
            mListener.setOnItemClick(v);
        }
    }

//    public PopupWindowShare(Activity context) {
//        super(context);
//
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        mView = inflater.inflate(R.layout.popup_share, null);
//
//        TextView btnFriend = mView.findViewById(R.id.btn_friend);
//        TextView btnFriendCenter = mView.findViewById(R.id.btn_friend_center);
//        TextView btnCancel = mView.findViewById(R.id.btn_cancel);
//
//        btnFriend.setOnClickListener(this);
//        btnFriendCenter.setOnClickListener(this);
//
//        // 设置按钮监听
//        btnCancel.setOnClickListener(new OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
//
//        //设置PopupWindow的View
//        this.setContentView(mView);
//        //设置PopupWindow弹出窗体的宽
//        this.setWidth(LayoutParams.MATCH_PARENT);
//        //设置PopupWindow弹出窗体的高
//        this.setHeight(LayoutParams.WRAP_CONTENT);
//        //设置PopupWindow弹出窗体可点击
//        this.setFocusable(true);
//        //设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.popup_share_style);
//        //设置SelectPicPopupWindow弹出窗体的背景
//        this.setBackgroundDrawable(new PaintDrawable());
//        // 设置外部可点击
//        this.setOutsideTouchable(true);
//        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
//        this.mView.setOnTouchListener(new View.OnTouchListener() {
//            public boolean onTouch(View v, MotionEvent event) {
//
//                int height = mView.findViewById(R.id.popup_window).getTop();
//
//                int y = (int) event.getY();
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    if (y < height) {
//                        dismiss();
//                    }
//                }
//                return true;
//            }
//        });
//        this.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                mListener.onDismiss();
//            }
//        });
//    }
}
