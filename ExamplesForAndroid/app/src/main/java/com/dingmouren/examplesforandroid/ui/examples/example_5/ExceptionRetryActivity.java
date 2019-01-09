package com.dingmouren.examplesforandroid.ui.examples.example_5;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dingmouren.examplesforandroid.R;
import com.dingmouren.examplesforandroid.base.BaseActivity;
import com.dingmouren.examplesforandroid.http.Api;
import com.dingmouren.examplesforandroid.http.HttpManager;
import com.dingmouren.examplesforandroid.model.MyResponse;
import com.dingmouren.examplesforandroid.ui.examples.example_4.PollingActivity;
import com.dingmouren.examplesforandroid.util.JsonUtils;
import com.google.gson.Gson;

import java.net.ConnectException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * @author dingmouren
 */
public class ExceptionRetryActivity extends BaseActivity {

    private ImageView mImgBack;

    private TextView mTvTitle;

    private Button mBtn;

    private TextView mTv;

    private CompositeDisposable mCompositeDisposable;

    public static void newInstance(Context context){
        context.startActivity(new Intent(context,ExceptionRetryActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_example_5_exception_retry;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mImgBack = findViewById(R.id.img_back);
        mTvTitle = findViewById(R.id.tv_example_title);
        mBtn = findViewById(R.id.btn);
        mTv = findViewById(R.id.tv);

        mTvTitle.setText(mActivity.getResources().getString(R.string.example_5_exception_retry));

        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void initListener() {
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retryDemo();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mCompositeDisposable != null) mCompositeDisposable.clear();
    }

    private void retryDemo() {
        Observable<MyResponse<String>> observable = HttpManager.createService(Api.class).retry()
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
            private int mRetryCount;//记录重试的次数
            @Override
            public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
                return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Throwable throwable) throws Exception {

                        long waitTime = 0;//等待时间
                        if (throwable instanceof ConnectException){
                            mainThreadTextChange("ConnectException异常\n");
                            waitTime = 2000;
                        }
                        mRetryCount++;
                        if (waitTime > 0){
                            mainThreadTextChange("2秒后重新发起请求\n");
                        }
                        return waitTime > 0 && mRetryCount <= 4 ?
                                Observable.timer(waitTime,TimeUnit.MILLISECONDS):
                                Observable.error(throwable);
                    }
                });
            }
        });

        DisposableObserver<MyResponse<String>> disposableObserver = new DisposableObserver<MyResponse<String>>() {
            @Override
            public void onNext(MyResponse<String> response) {
                Gson gson = new Gson();
                mTv.append("onNext:\n"+ JsonUtils.formatJson(gson.toJson(response))+"\n");
            }

            @Override
            public void onError(Throwable e) {
                mTv.append("onError:"+e.getMessage()+"\n");
            }

            @Override
            public void onComplete() {
                mTv.append("onComplete\n");
            }
        };

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);

        mCompositeDisposable.add(disposableObserver);
    }

    private void mainThreadTextChange(final String content){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTv.append(content);
            }
        });
    }
}
