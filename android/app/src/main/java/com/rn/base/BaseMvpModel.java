package com.rn.base;

import com.rn.http.OkHttpUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class BaseMvpModel {

    protected void execute(Observer observer, Observable o){
        OkHttpUtils.getInstance().execute(o, observer);
    }

    protected void executeNoBase(Observer observer, Observable o){
        OkHttpUtils.getInstance().executeNoBase(o, observer);
    }

    protected <T> T getService(Class<T> service){
        return OkHttpUtils.getInstance().createService(service);
    }
}
