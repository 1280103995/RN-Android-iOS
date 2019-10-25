package com.rn.http.api;

import com.rn.base.BaseResponse;
import com.rn.entity.CommonResponse;
import com.rn.entity.MeiZi;
import com.rn.entity.OauthInfo;
import com.rn.entity.TokenResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LoginApiService {
    String NOT_NEED_TOKEN = "NotNeedToken";

    @GET("api/data/%E7%A6%8F%E5%88%A9/30/1")
    Observable<BaseResponse<List<MeiZi>>> getData();

    @Headers(NOT_NEED_TOKEN+":true")
    @GET("get_token")
    Observable<BaseResponse<TokenResponse>> login();

    @GET("request")
    Observable<BaseResponse<CommonResponse>> request();

    @POST("test_post")
    Observable<BaseResponse<CommonResponse>> testPost();

    @Headers(NOT_NEED_TOKEN+":true")
    @POST("refresh_token")
    Call<BaseResponse<TokenResponse>> refreshToken();

    @Headers({"resourceKey:applyBaseInfo"})
    @GET("bus/trans/{userId}")
    Observable<Object> fetchLoanApplyInfo(@Path("userId") String userId);
}
