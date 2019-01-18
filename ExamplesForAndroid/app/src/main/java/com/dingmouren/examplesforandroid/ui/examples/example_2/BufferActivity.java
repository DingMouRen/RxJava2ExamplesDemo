package com.dingmouren.examplesforandroid.ui.examples.example_2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dingmouren.examplesforandroid.R;
import com.dingmouren.examplesforandroid.base.BaseActivity;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.PublishSubject;

/**
 * @author dingmouren
 */
public class BufferActivity extends BaseActivity {

    private ImageView mImgBack;

    private TextView mTvTitle;

    private TextView mTvTemperature;

    private TextView mTvAveraTemperature;

    private TemperatureHandler mTemperatureHandler;

    private PublishSubject<Double> mPublishSubject;

    private CompositeDisposable mCompositeDisposable;//管理订阅与解除订阅

    public static void newInstance(Context context){
        context.startActivity(new Intent(context,BufferActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_example_2_buffer;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        mImgBack = findViewById(R.id.img_back);
        mTvTitle = findViewById(R.id.tv_example_title);
        mTvTemperature = findViewById(R.id.tv_temperature);
        mTvAveraTemperature = findViewById(R.id.tv_avera_temperature);

        mTvTitle.setText(getResources().getString(R.string.example_2_buffer));

        rxjavaCompose();

        mTemperatureHandler = new TemperatureHandler();
        mTemperatureHandler.sendEmptyMessage(0);

        mCompositeDisposable = new CompositeDisposable();//用于管理订阅与解除订阅

    }


    @Override
    public void initListener() {
        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTemperatureHandler!=null)mTemperatureHandler.removeCallbacksAndMessages(null);
        if (mCompositeDisposable != null)mCompositeDisposable.dispose();
    }

    /**
     * 温度传感器，测量温度
     */
    private class TemperatureHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            //随机温度
            final double temperature = Math.random() * 25 + 5;

            //发送测量的温度去处理
            mPublishSubject.onNext(temperature);

            //显示温度
            mTvTemperature.post(new Runnable() {
                @Override
                public void run() {
                    mTvTemperature.append("温度："+temperature +"℃\n");
                    int scrollAmount = mTvTemperature.getLayout().getLineTop(mTvTemperature.getLineCount()) - mTvTemperature.getHeight();
                    if (scrollAmount > 0){
                        mTvTemperature.scrollTo(0,scrollAmount);
                    }else {
                        mTvTemperature.scrollTo(0,0);
                    }
                }
            });

            //循环地发送，测量温度
            sendEmptyMessageDelayed(0, 250 + (long) (250 * Math.random()));
        }
    }

    /**
     * rxjava处理
     */
    private void rxjavaCompose() {

        mPublishSubject = PublishSubject.create();

        DisposableObserver<List<Double>> disposableObserver = new DisposableObserver<List<Double>>() {
            @Override
            public void onNext(List<Double> temperatureList) {
                double resultSum = 0;//温度的和
                double resultAvera = 0;
                Log.e("onNext","接收到集合的大小："+temperatureList.size());
                if (temperatureList.size() > 0){
                    for(Double temperature : temperatureList){
                        resultSum += temperature;
                    }
                    resultAvera = resultSum / temperatureList.size();
                }
                Log.e(mActivity.getClass().getSimpleName(),"更新平均温度："+resultAvera);

                final double finalResultAvera = resultAvera;
                //TextView滚动到最低边
                mTvAveraTemperature.post(new Runnable() {
                    @Override
                    public void run() {
                        mTvAveraTemperature.append("平均3秒温度："+ finalResultAvera +"℃   时间："+ new Date().toLocaleString()+"\n");
                        int scrollAmount = mTvAveraTemperature.getLayout().getLineTop(mTvAveraTemperature.getLineCount()) - mTvAveraTemperature.getHeight();
                        if (scrollAmount > 0){
                            mTvAveraTemperature.scrollTo(0,scrollAmount);
                        }else {
                            mTvAveraTemperature.scrollTo(0,0);
                        }
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
        mPublishSubject.buffer(3000, TimeUnit.MILLISECONDS)//将3秒时间内的所有数据以集合的方式发射出去
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);

        mCompositeDisposable.add(disposableObserver);
    }

}
