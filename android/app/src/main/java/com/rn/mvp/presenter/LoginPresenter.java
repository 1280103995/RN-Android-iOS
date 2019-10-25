package com.rn.mvp.presenter;

import com.rn.base.App;
import com.rn.base.BaseMvpPresenter;
import com.rn.entity.TokenResponse;
import com.rn.mvp.contract.LoginContract;
import com.rn.mvp.model.LoginModel;
import com.rn.util.SPUtils;

public class LoginPresenter extends BaseMvpPresenter<LoginContract.IView>
        implements LoginContract.IPresenter {

    @Override
    public void login() {
        ObserverBridge bridge = new ObserverBridge<TokenResponse>() {
            @Override
            protected void onSuccess(TokenResponse data) {
                SPUtils.put(App.getInstance(), "save_token", data.getToken());
//                view.hideLoading();
//                view.setText("token：" + data);
            }
        };
        new LoginModel().login(bridge);
    }

    @Override
    public void request() {
        ObserverBridge bridge = new ObserverBridge<Object>() {
            @Override
            protected void onSuccess(Object o) {

            }
        };
        new LoginModel().request(bridge);
    }

    @Override
    public void testPost() {
        ObserverBridge bridge = new ObserverBridge<Object>() {
            @Override
            protected void onSuccess(Object o) {

            }
        };
        new LoginModel().testPost(bridge);
    }
}