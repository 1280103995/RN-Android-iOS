package com.rn.mvp.contract;

import com.rn.base.IMvpPresenter;
import com.rn.base.IMvpView;
import com.rn.entity.MeiZi;

import java.util.List;

import io.reactivex.Observer;

public class TabFragment1Contract {

    public interface IModel{
        void getData(Observer observer, int page);
    }

    public interface IView extends IMvpView {
        void complete();
        void clear();
        void addAll(List<MeiZi> list);
        void noMoreData();
    }

    public interface IPresenter extends IMvpPresenter<IView> {
        void refresh();
        void load();
    }
}
