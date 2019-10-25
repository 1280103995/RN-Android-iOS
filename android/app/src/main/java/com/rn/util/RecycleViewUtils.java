package com.rn.util;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 获取recyclerview滚动的距离
 * 在recyclerview设置了布局管理器后才能实例化调用with()
 * Created by liangfj on 2018/7/26.
 */

public class RecycleViewUtils {

    private WeakReference<RecyclerView> recycleView;
    private RecycleViewUtils utils;
    private Map<Integer, Integer> itemHeights;
    private int nowItemPos, totalHeight = 0;
    private View nowView;
    private LinearLayoutManager layoutManager;
	
	public RecycleViewUtils(){
        
    }

    public RecycleViewUtils with(RecyclerView recyclerView) {
        if (utils == null) utils = new RecycleViewUtils(recyclerView);
        return utils;
    }

    public RecycleViewUtils(RecyclerView recyclerView) {
        this.recycleView = new WeakReference<>(recyclerView);
        itemHeights = new WeakHashMap<>();
        nowItemPos = 0;
        layoutManager = (LinearLayoutManager) recycleView.get().getLayoutManager();
    }

    public synchronized int getScrollY() {
        int pos = layoutManager.findFirstVisibleItemPosition();
        int lastPos = layoutManager.findLastVisibleItemPosition();
        for (int i = pos; i <= lastPos; i++) {
            nowView = layoutManager.findViewByPosition(i);
            if (!itemHeights.containsKey(i)) {
                totalHeight = 0;
                totalHeight += nowView.getHeight();
                if (totalHeight == 0) break;
                itemHeights.put(i, totalHeight);
            }

        }
//        nowView = layoutManager.findViewByPosition(pos);
//        if (pos == nowItemPos) {
//            if (!itemHeights.containsKey(pos)) {
//                totalHeight = 0;
//                if (itemHeights.containsKey(pos - 1))
//                    totalHeight = itemHeights.get(pos - 1);
//                totalHeight += nowView.getMeasuredHeight();
//                itemHeights.put(nowItemPos, totalHeight);
//            }
//        }
        int height = 0;
//        if (itemHeights.containsKey(pos - 1)) height = itemHeights.get(pos - 1);
        for (int i = 0; i < pos; i++) height += itemHeights.get(i);
        if (pos != nowItemPos) nowItemPos = pos;
        nowView = layoutManager.findViewByPosition(pos);
        if (nowView != null) height -= nowView.getTop();
        return height;
    }
}
