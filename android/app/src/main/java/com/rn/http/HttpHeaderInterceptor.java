package com.rn.http;

import com.rn.base.App;
import com.rn.util.NetUtil;
import com.rn.util.SPUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 网络请求公共头信息插入器
 */
public class HttpHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!NetUtil.checkNet()) {
            return new Response.Builder() //
                    .code(1001)
                    .message("当前网络不可用，请检查网络情况")
                    .body(ResponseBody.create(MediaType.parse("application/json; charset=utf-8"), "检查网络情况"))
                    .protocol(Protocol.HTTP_1_1)
                    .request(new Request.Builder().url("http://localhost/").build())
                    .build();
        }

        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("Content-Type", "application/json; charset=utf-8");

        return chain.proceed(builder.build());
    }
}
