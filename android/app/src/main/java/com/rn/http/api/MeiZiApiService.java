package com.rn.http.api;

import com.rn.entity.MeiZiResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;

public interface MeiZiApiService {

    @Headers({LoginApiService.NOT_NEED_TOKEN + ":true"})
    @GET
    Observable<MeiZiResponse> getData(@Url String url);
}
