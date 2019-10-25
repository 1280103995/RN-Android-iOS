package com.rn.mvp.contract;

import com.rn.base.IMvpPresenter;
import com.rn.base.IMvpView;

import io.reactivex.Observer;

/**
 * Created by liangfj on 2018/6/5.
 */

public class LoginContract {

    public interface IModel {
        void login(Observer observer);

        void request(Observer observer);

        void testPost(Observer observer);
    }

    public interface IView extends IMvpView {
        void setText(String result);
    }

    public interface IPresenter extends IMvpPresenter<IView> {
        void login();

        void request();

        void testPost();
    }
}
