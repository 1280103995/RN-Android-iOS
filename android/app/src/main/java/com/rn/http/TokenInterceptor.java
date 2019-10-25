package com.rn.http;

import android.text.TextUtils;

import com.rn.base.App;
import com.rn.base.BaseResponse;
import com.rn.entity.TokenResponse;
import com.rn.http.api.LoginApiService;
import com.rn.util.TokenInfo;
import com.rn.util.SPUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class TokenInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        Request request;
        try {
            request = checkNeedRefreshToken(builder);
        } catch (ApiException e) {
            //在BaseMvpPresenter拿到该错误信息后跳到登录界面
            return new Response.Builder()
                    .code(e.getCode())
                    .message(e.getMessage())
                    .body(ResponseBody.create(MediaType.parse("application/json; charset=utf-8"), "重新登录"))
                    .protocol(Protocol.HTTP_1_1)
                    .request(new Request.Builder().url("http://localhost/").build())
                    .build();
        }

        //发起该请求
        Response response = chain.proceed(request);
        //验证401 token过期
        int code = response.code();
        if (code == 401) {
            //将token置为过期
            SPUtils.put(App.getInstance(), "save_token", "0");
            //重新请求
            response = intercept(chain);
        }
        return response;
    }

    private synchronized Request checkNeedRefreshToken(Request.Builder builder) throws IOException, ApiException {
        //相应的service请求需要加上这个header来过滤掉登录和刷新token的请求
        String notNeedToken = builder.build().header(LoginApiService.NOT_NEED_TOKEN);

        String token = TokenInfo.getInstance().getToken();
        if (TextUtils.isEmpty(token)) {
            token = (String) SPUtils.get(App.getInstance(), "save_token", "0");
        }

        if (TextUtils.isEmpty(notNeedToken) && isTokenExpired(token)) {
            token = refreshToken();
        }
        builder.header("Authorization", "Bearer " + token).build();

        if(!TextUtils.isEmpty(notNeedToken)){
            builder.removeHeader(LoginApiService.NOT_NEED_TOKEN);
        }
        return builder.build();
    }

    private boolean isTokenExpired(String token) {
        boolean isTokenExpired = 30000 + Long.valueOf(token) <= System.currentTimeMillis();
        return TextUtils.equals(token, "0") || isTokenExpired;
    }

    /**
     * 同步请求方式，刷新Token
     *
     * @return
     */
    private synchronized String refreshToken() throws IOException, ApiException {
        LoginApiService service = OkHttpUtils.getInstance().createService(LoginApiService.class);
        Call<BaseResponse<TokenResponse>> oauthInfoCall = service.refreshToken();
        retrofit2.Response<BaseResponse<TokenResponse>> response = oauthInfoCall.execute();
        if (response.isSuccessful()) {
            String newToken = response.body().getData().getToken();
            //缓存新token到内存中
            TokenInfo.getInstance().setToken(newToken);
            //保存新token到数据库中
            SPUtils.put(App.getInstance(), "save_token", newToken);
            return newToken;
        } else {
            String msg = true ? "登录超时,请重新登录" : "认证超时,请重新操作";
            throw new ApiException(response.code(), msg);
        }
    }
}
