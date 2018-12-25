package com.dingmouren.examplesforandroid.ui.examples.example_1;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.dingmouren.examplesforandroid.R;
import com.dingmouren.examplesforandroid.base.BaseActivity;
import com.dingmouren.examplesforandroid.http.Api;
import com.dingmouren.examplesforandroid.http.HttpManager;
import com.dingmouren.examplesforandroid.http.download.FileCallBack;
import com.dingmouren.examplesforandroid.http.download.FileDownloadObserver;
import com.dingmouren.examplesforandroid.http.download.ProgressInterceptor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import okio.Okio;

/**
 * @author dingmouren
 */
public class DownloadActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_example_down_load;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FileCallBack<ResponseBody> fileCallBack = new FileCallBack<ResponseBody>("","") {
                    @Override
                    public void onSuccess(ResponseBody responseBody) {
                        Log.e(TAG,"onSuccess:"+responseBody.toString());
                    }

                    @Override
                    public void progress(long progress, long total) {
                        Log.e(TAG,total+"/"+progress);
                    }

                    @Override
                    public void onStart(Disposable disposable) {

                    }

                    @Override
                    public void onCompleted() {
                        Log.e(TAG,"onComplete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG,"onError:"+e.getMessage());
                    }
                };

                HttpManager.createService(Api.class,new ProgressInterceptor())
                        .downloadApk()
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .doOnNext(new Consumer<ResponseBody>() {
                            @Override
                            public void accept(ResponseBody responseBody) throws Exception {
//                                    fileCallBack.saveFile(responseBody);
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new FileDownloadObserver<ResponseBody>(fileCallBack));
            }
        });
    }



}
