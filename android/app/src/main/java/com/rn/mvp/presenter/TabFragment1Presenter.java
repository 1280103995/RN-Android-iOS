package com.rn.mvp.presenter;

import com.rn.base.BaseMvpPresenter;
import com.rn.base.BaseResponse;
import com.rn.entity.MeiZi;
import com.rn.entity.MeiZiResponse;
import com.rn.mvp.contract.TabFragment1Contract;
import com.rn.mvp.model.TabFragment1MvpModel;

import java.util.List;

public class TabFragment1Presenter extends BaseMvpPresenter<TabFragment1Contract.IView> implements TabFragment1Contract.IPresenter {

    private int page = 1;

    @Override
    public void refresh() {
        page = 1;
        getList();
    }

    @Override
    public void load() {
        if (page == 2) {
            view.noMoreData();
            return;
        }
        ++page;
        getList();
    }

    private void getList(){
        ObserverBridge bridge = new ObserverBridge<MeiZiResponse>() {
            @Override
            protected void onSuccess(MeiZiResponse response) {
                view.addAll(response.getResults());
                view.complete();
            }

            @Override
            protected void onFail(Throwable e) {
                view.complete();
            }
        };
        new TabFragment1MvpModel().getData(bridge, page);
    }

}
