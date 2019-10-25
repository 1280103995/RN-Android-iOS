package com.rn.mvp.model;

import com.rn.base.BaseMvpModel;
import com.rn.http.api.MeiZiApiService;
import com.rn.mvp.contract.TabFragment1Contract;

import io.reactivex.Observer;

public class TabFragment1MvpModel extends BaseMvpModel implements TabFragment1Contract.IModel {

    @Override
    public void getData(Observer observer, int page) {
        executeNoBase(observer, getService(MeiZiApiService.class).getData(
                "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/"+page));
    }
}
