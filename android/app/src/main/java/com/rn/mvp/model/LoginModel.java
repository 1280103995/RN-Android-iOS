package com.rn.mvp.model;

import com.rn.base.BaseMvpModel;
import com.rn.http.api.LoginApiService;
import com.rn.mvp.contract.LoginContract;

import io.reactivex.Observer;

/**
 * Description: M层 拿到数据后回调给P层
 */
public class LoginModel extends BaseMvpModel implements LoginContract.IModel {

    @Override
    public void login(Observer observer) {
        execute(observer, getService(LoginApiService.class).login());
    }

    @Override
    public void request(Observer observer) {
        execute(observer, getService(LoginApiService.class).request());
    }

    @Override
    public void testPost(Observer observer) {
        execute(observer, getService(LoginApiService.class).testPost());
    }
}
