package com.dingmouren.examplesforandroid.ui.examples.example_4;

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
import com.dingmouren.examplesforandroid.ui.examples.example_3.SearchActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * 轮询操作
 * @author dingmouren
 */
public class PollingActivity extends BaseActivity {

    private ImageView mImgBack;

    private TextView mTvTitle;

    private Button mBtnFix,mBtnDelay;

    private TextView mTvFix,mTvDelay;

    private CompositeDisposable mCompositeDisposable;//管理订阅关系

    public static void newInstance(Context context){
        context.startActivity(new Intent(context,PollingActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_example_4_polling;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mImgBack = findViewById(R.id.img_back);
        mTvTitle = findViewById(R.id.tv_example_title);
        mBtnFix = findViewById(R.id.btn_fix_polling);
        mBtnDelay = findViewById(R.id.btn_delay_polling);
        mTvFix = findViewById(R.id.tv_fix_polling);
        mTvDelay = findViewById(R.id.tv_delay_polling);

        mTvTitle.setText(mActivity.getResources().getString(R.string.example_4_polling));

        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void initListener() {
        mBtnFix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFixPolling();
            }
        });

        mBtnDelay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDelayPolling();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable != null) mCompositeDisposable.clear();
    }

    /**
     * 固定时间间隔的轮询
     */
    private void startFixPolling() {

        Observable<MyResponse<String>> observableFix = Observable.intervalRange(0,5,0,1000, TimeUnit.MILLISECONDS)
                .take(5)
                .flatMap(new Function<Long, ObservableSource<MyResponse<String>>>() {
                    @Override
                    public ObservableSource<MyResponse<String>> apply(Long aLong) throws Exception {
                        return HttpManager.createService(Api.class).polling().subscribeOn(Schedulers.io());
                    }
                });

        DisposableObserver<MyResponse<String>> disposableObserverFix = new DisposableObserver<MyResponse<String>>() {
            @Override
            public void onNext(MyResponse<String> response) {
                mTvFix.append(response.data+"\n");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        observableFix.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserverFix);

        mCompositeDisposable.add(disposableObserverFix);
    }

    /**
     * 变长时间间隔的轮询
     */
    private void startDelayPolling(){

    }
}
