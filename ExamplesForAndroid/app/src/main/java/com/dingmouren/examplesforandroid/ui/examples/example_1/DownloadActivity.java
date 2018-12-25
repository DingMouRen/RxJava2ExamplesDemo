package com.dingmouren.examplesforandroid.ui.examples.example_1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import java.text.DecimalFormat;

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

    private Button mBtn;

    private ImageView mImgBack;

    private TextView mTvTitle;

    private ProgressBar mProgressBar;

    private TextView mTvProgress;

    public static void newInstance(Context context){
        context.startActivity(new Intent(context,DownloadActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_example_down_load;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        mBtn = findViewById(R.id.btn);
        mProgressBar = findViewById(R.id.progress_bar);
        mTvProgress = findViewById(R.id.tv_progress);
        mImgBack = findViewById(R.id.img_back);
        mTvTitle = findViewById(R.id.tv_example_title);

        mTvTitle.setText(getResources().getString(R.string.example_1_download));
    }


    @Override
    public void initListener() {


        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFile();
            }
        });
    }

    /**
     * 下载文件
     */
    private void downloadFile() {
        final FileCallBack<ResponseBody> fileCallBack = new FileCallBack<ResponseBody>("","") {
            @Override
            public void onSuccess(ResponseBody responseBody) {
                Log.e(TAG,"onSuccess:"+responseBody.toString());
            }

            @Override
            public void progress(long progress, long total) {
                Log.e(TAG,total+"/"+progress);
                mProgressBar.setMax((int) total);
                mProgressBar.setProgress((int) progress);

                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                String scaleStr = decimalFormat.format(progress * 1f/ total );
                mTvProgress.setText( (int)(Float.parseFloat(scaleStr) * 100) +"%");
            }

            @Override
            public void onStart(Disposable disposable) {

            }

            @Override
            public void onCompleted() {
                Log.e(TAG,"onComplete");
                show("下载完成");
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
}
