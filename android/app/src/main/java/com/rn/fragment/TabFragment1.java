package com.rn.fragment;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ganba.xrecyclerview.XRecyclerView;
import com.rn.R;
import com.rn.adapter.RVAdapter;
import com.rn.base.BaseMvpFragment;
import com.rn.entity.MeiZi;
import com.rn.mvp.contract.TabFragment1Contract;
import com.rn.mvp.presenter.TabFragment1Presenter;
import com.rn.util.GlideUtils;
import java.util.List;

import butterknife.BindView;

/**
 * 测试与宿主 Activity 一起使用 MVP 的情况
 */
public class TabFragment1 extends BaseMvpFragment<TabFragment1Contract.IPresenter> implements TabFragment1Contract.IView {

    @BindView(R.id.recycler_view)
    XRecyclerView mRecyclerView;
    RVAdapter<MeiZi> adapter;

    @Override
    protected TabFragment1Contract.IPresenter createPresenter() {
        return new TabFragment1Presenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.recycler_view;
    }

    @Override
    protected void initView() {
        init();
    }

    @Override
    protected void initMvpData() {

    }

    private void init(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.header, mRecyclerView, false);
        TextView header = view.findViewById(R.id.tv_header);
        header.setText("这是头部");
        header.setBackgroundColor(Color.RED);
        mRecyclerView.addHeaderView(header);

        View view2 = LayoutInflater.from(getContext()).inflate(R.layout.empty, mRecyclerView, false);
        TextView empty = view2.findViewById(R.id.tv_header);
        empty.setText("这是空页面");
        empty.setBackgroundColor(Color.GREEN);
        mRecyclerView.setEmptyView(empty);
        //头部和空页面同时显示
        mRecyclerView.setHeadFootWithEmptyEnabled(true);

        mRecyclerView.setRefreshEnabled(true);

        adapter = new RVAdapter<MeiZi>(R.layout.item) {
            @Override
            protected void convert(ViewHolder vH, MeiZi item, int position) {
                ImageView img = vH.getView(R.id.img);
                GlideUtils.loadImg(getActivity(),item.getUrl(), img);
            }
        };

        mRecyclerView.setAdapter(adapter);

        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }

            @Override
            public void onLoadMore() {
                presenter.load();
            }
        });

        mRecyclerView.autoRefresh();
    }

    @Override
    public void complete() {
        mRecyclerView.refreshComplete();
        mRecyclerView.loadMoreComplete();
    }

    @Override
    public void clear() {
        adapter.clear();
    }

    @Override
    public void addAll(List<MeiZi> list) {
        adapter.addAll(list);
    }

    @Override
    public void noMoreData() {
        mRecyclerView.setNoMore(true);
    }
}
