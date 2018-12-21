package com.dingmouren.examplesforandroid.http;

import com.dingmouren.examplesforandroid.util.LogUtils;

/**
 * @author dingmouren
 */
public class HttpLogInterceptor {

    private static HttpLoggingInterceptor sHttpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            showHttpLog(message);
        }
    }).setLevel(HttpLoggingInterceptor.Level.BODY);

    private HttpLogInterceptor() {
        throw new UnsupportedOperationException("can't instantiate HttpLogInterceptor");
    }

    public static HttpLoggingInterceptor getInstance() {
        return sHttpLoggingInterceptor;
    }

    /**
     * 显示网络日志
     *
     * @param message
     */
    private static void showHttpLog(String message) {

        System.out.println(message);

        if (message.startsWith("{")){
            LogUtils.json(message);
        }

    }
}
