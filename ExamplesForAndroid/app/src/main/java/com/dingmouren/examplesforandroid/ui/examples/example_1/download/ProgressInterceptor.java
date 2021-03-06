package com.dingmouren.examplesforandroid.ui.examples.example_1.download;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by air on 2016/12/5.
 * 对于返回的响应进行处理的拦截器
 */
public class ProgressInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        Log.e("response",originalResponse.body().contentLength()+"");
        return originalResponse.newBuilder()
                .body(new ProgressResponseBody(originalResponse.body()))
                .build();
    }
}
