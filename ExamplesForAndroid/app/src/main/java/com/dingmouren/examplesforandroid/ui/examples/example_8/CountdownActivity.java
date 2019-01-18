package com.dingmouren.examplesforandroid.ui.examples.example_8;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dingmouren.examplesforandroid.R;
import com.dingmouren.examplesforandroid.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

/**
 * @author dingmouren
 */
public class CountdownActivity extends BaseActivity {

    private ImageView mImgBack;

    private TextView mTvTitle;

    private EditText mEditTime;

    private Button mBtnStart;

    private TextView mTvTime;

    private CompositeDisposable mCompositeDisposable;

    public static void newInstance(Context context){
        context.startActivity(new Intent(context,CountdownActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_example_8_count_down;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mImgBack = findViewById(R.id.img_back);
        mTvTitle = findViewById(R.id.tv_example_title);
        mEditTime = findViewById(R.id.edit_time);
        mBtnStart = findViewById(R.id.btn_start);
        mTvTime = findViewById(R.id.tv_time);

        mTvTitle.setText(mActivity.getResources().getString(R.string.example_8_count_down));
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void initListener() {
        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String countDownTimeStr = mEditTime.getText().toString();
                long countDownTimeLong = 0;
                if (!TextUtils.isEmpty(countDownTimeStr)){
                    countDownTimeLong = Long.parseLong(countDownTimeStr);
                }
                startCountDown(countDownTimeLong);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable != null)mCompositeDisposable.clear();
    }

    /**
     * 开始倒计时
     intervalRange操作符：
     start：发送数据的起始值，为Long型。
     count：总共发送多少项数据。
     initialDelay：发送第一个数据项时的起始时延。
     period：两项数据之间的间隔时间。
     TimeUnit：时间单位。
     * @param countDownTimeLong
     */
    private void startCountDown(final long countDownTimeLong) {

        mCompositeDisposable.clear();

        Observable<Long> observable
                = Observable.intervalRange(0,//发送数据的起始值，为Long型。
                countDownTimeLong + 1,//总共发送多少项数据
                0,//发送第一个数据项时的起始时延
                1, //两项数据之间的间隔时间
                TimeUnit.SECONDS);//时间单位

        DisposableObserver<Long> disposableObserver = new DisposableObserver<Long>() {
            @Override
            public void onNext(final Long aLong) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvTime.setText(formatDuring((countDownTimeLong - aLong) * 1000));
                    }
                });

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        observable.subscribe(disposableObserver);

        mCompositeDisposable.add(disposableObserver);

    }

    public static String formatDuring(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        return days + " 天 " + hours + " 时 " + minutes + " 分 "
                + seconds + " 秒 ";
    }
}
