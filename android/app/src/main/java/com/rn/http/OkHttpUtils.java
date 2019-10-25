package com.rn.http;

import android.util.Log;

import com.rn.base.App;
import com.rn.base.BaseResponse;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Description: 网络请求的工具类
 */
public class OkHttpUtils {

    private volatile static OkHttpUtils mInstance;
    //    private static String baseUrl = "http://gank.io/";
    private static final String baseUrl = "http://10.18.204.63:8000/";

    /**
     * Retrofit
     */
    private static Retrofit retrofit;

    //设置缓存目录
    private static final File cacheDirectory = new File(App.getInstance().getCacheDir().getAbsolutePath(), "httpCache");

    private static Cache cache = new Cache(cacheDirectory, 10 * 1024 * 1024);

    private OkHttpUtils() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(CookieJar.NO_COOKIES)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new HttpHeaderInterceptor())
                .addInterceptor(new TokenInterceptor())
                .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.i("okHttp3", message);
                    }
                }).setLevel(HttpLoggingInterceptor.Level.BODY))
                .cache(cache)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static OkHttpUtils getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpUtils();
                }
            }
        }
        return mInstance;
    }

    public void merge(Observable observable1, Observable observable2, Observer observer){
        Observable.merge(observable1, observable2)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer())
                .subscribe(observer);
    }

    public void merge(Observable observable1, Observable observable2, Observable observable3, Observer observer){
        Observable.merge(observable1, observable2, observable3)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer())
                .subscribe(observer);
    }

    public void merge(Observable observable1, Observable observable2, Observable observable3, Observable observable4, Observer observer){
        Observable.merge(observable1, observable2, observable3, observable4)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer())
                .subscribe(observer);
    }

    /**
     * @param service 自定义请求service
     * @param <T>
     * @return
     */
    public <T> T createService(Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return retrofit.create(service);
    }

    /**
     * 自定义返回数据格式请求
     *
     * @param observable
     * @param observer
     * @param <T>
     * @return
     */
    public <T> void execute(Observable<T> observable, Observer observer) {
        observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer())
                .subscribe(observer);
    }

    public <T> void executeNoBase(Observable<T> observable, Observer observer) {
        observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private <T> ObservableTransformer<T, T> transformer() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable upstream) {
                return upstream
                        .map(new ServerResultFunc<T>())
                        .onErrorResumeNext(new HttpResultFunc<T>());
            }
        };
    }

    private class HttpResultFunc<T> implements Function<Throwable, Observable<T>> {
        @Override
        public Observable<T> apply(Throwable throwable) throws Exception {
            return Observable.error(CustomException.handleException(throwable));
        }
    }

    private class ServerResultFunc<T> implements Function<BaseResponse<T>, T> {
        @Override
        public T apply(@NonNull BaseResponse<T> response) throws Exception {
            int code = 404;
            String message = "页面不存在";

            if (!response.isSuccess()) {//服务器字段
                throw new ApiException(code, message);
            }
            return response.getData();
        }
    }

}
