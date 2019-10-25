package com.rn.util;

import android.app.Activity;

import java.util.Stack;

public class ActivityUtil {
    private static Stack<Activity> mActivityStack;

    /**
     * 添加一个Activity到堆栈中
     *
     * @param activity
     */
    public static void addActivity(Activity activity) {
        if (null == mActivityStack) {
            mActivityStack = new Stack<>();
        }
        mActivityStack.add(activity);
    }

    /**
     * 从堆栈中移除指定的Activity
     *
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
        }
    }

    /**
     * 获取顶部的Activity
     *
     * @return 顶部的Activity
     */
    public static Activity getTopActivity() {
        if (mActivityStack.isEmpty()) {
            return null;
        } else {
            return mActivityStack.get(mActivityStack.size() - 1);
        }
    }

    /**
     * 结束所有的Activity，退出应用
     */
    public static void removeAllActivity() {
        if (mActivityStack != null && mActivityStack.size() > 0) {
            for (Activity activity : mActivityStack) {
                if (activity != null)
                    activity.finish();
            }
        }
        mActivityStack.clear();
        //杀死该应用进程
//        android.os.Process.killProcess(android.os.Process.myPid());
//        System.exit(0);
    }

    /**
     * 弹出栈内非指定activity
     * @param cls
     */
    public static void removeOthers(Class cls) {
        Activity tempActivity = null;
        for (Activity a : mActivityStack) {
            if (a.getClass().equals(cls)) {
                tempActivity = a;
            }else {
                a.finish();
            }
        }
        mActivityStack.clear();
        mActivityStack.add(tempActivity);
    }
}
