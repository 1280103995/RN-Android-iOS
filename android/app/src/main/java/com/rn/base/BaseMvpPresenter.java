package com.rn.base;

import android.os.Bundle;

import com.rn.http.OkHttpUtils;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * Created by liangfj on 2018/6/5.
 */

public class BaseMvpPresenter<V extends IMvpView> implements IMvpPresenter<V> {

    protected V view;
    private WeakReference<V> viewRef;
    private CompositeDisposable mCompositeDisposable;

    protected boolean isViewAttached() {
        return viewRef != null && viewRef.get() != null;
    }

    @Override
    public void onMvpAttachView(V view, Bundle savedInstanceState) {
        viewRef = new WeakReference<>(view);
        this.view = viewRef.get();
    }

    @Override
    public void onMvpStart() {

    }

    @Override
    public void onMvpResume() {

    }

    @Override
    public void onMvpPause() {

    }

    @Override
    public void onMvpStop() {

    }

    @Override
    public void onMvpSaveInstanceState(Bundle savedInstanceState) {

    }

    @Override
    public void onMvpDestroy() {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
            view = null;
        }

        unDispose();
        System.gc();
    }

    /**
     * 将所有disposable放入,集中处理
     *
     * @param disposable
     */
    public void addDispose(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }


    private void unDispose() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    protected <T> T getService(Class<T> service) {
        return OkHttpUtils.getInstance().createService(service);
    }

    protected void mergeObservable(Observable observable1, Observable observable2, ObserverBridge bridge){
        OkHttpUtils.getInstance().merge(observable1, observable2, bridge);
    }

    protected void mergeObservable(Observable observable1, Observable observable2, Observable observable3, ObserverBridge bridge){
        OkHttpUtils.getInstance().merge(observable1, observable2, observable3, bridge);
    }

    protected void mergeObservable(Observable observable1, Observable observable2, Observable observable3, Observable observable4, ObserverBridge bridge){
        OkHttpUtils.getInstance().merge(observable1, observable2, observable3, observable4, bridge);
    }

    public abstract class ObserverBridge<T> implements Observer<T> {

        protected ObserverBridge(){

        }

        protected ObserverBridge(boolean showLoading){
            if (showLoading && isViewAttached()) {
                view.showLoading();
            }
        }

        protected abstract void onSuccess(T t);

        protected void onFail(Throwable e){

        }

        @Override
        public void onSubscribe(Disposable d) {
            addDispose(d);
        }

        @Override
        public void onNext(T t) {
            if (isViewAttached()) {
                onSuccess(t);
            }
        }

        @Override
        public void onError(Throwable e) {
            if (isViewAttached()) {
                view.showMsg(e.toString());
                view.hideLoading();
                view.onApiException(e);
                onFail(e);
            }
        }

        @Override
        public void onComplete() {

        }
    }
}
