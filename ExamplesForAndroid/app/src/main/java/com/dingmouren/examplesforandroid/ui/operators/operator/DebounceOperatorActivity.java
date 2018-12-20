package com.dingmouren.examplesforandroid.ui.operators.operator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.dingmouren.examplesforandroid.base.BaseOperatorActivity;
import com.dingmouren.examplesforandroid.model.OperatorModel;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author dingmouren
 */
public class DebounceOperatorActivity extends BaseOperatorActivity {

    public static final String KEY = "key";

    private OperatorModel mBean;

    public static void newInstance(Context context, OperatorModel bean){
        Intent intent = new Intent(context,DebounceOperatorActivity.class);
        intent.putExtra(KEY,bean);
        context.startActivity(intent);
    }

    @Override
    public void init() {
        if (getIntent() != null){
            mBean = (OperatorModel) getIntent().getSerializableExtra(KEY);
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mTvOperatorName.setText(mBean.operatorName);
    }



    @Override
    protected void test() {
        mTvLog.append("\n\n");

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {

                emitter.onNext(1);
                mTvLog.append("Observable 发射1 睡490毫秒\n");
                Thread.sleep(490);

                emitter.onNext(2);
                mTvLog.append("Observable 发射2 睡500毫秒\n");
                Thread.sleep(500);

                emitter.onNext(3);
                mTvLog.append("Observalbe 发射3 睡510毫秒\n");
                Thread.sleep(510);

                emitter.onNext(4);
                mTvLog.append("Observalbe 发射3 睡520毫秒\n");
                Thread.sleep(520);

                emitter.onComplete();

            }
        }).debounce(500, TimeUnit.MILLISECONDS).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                mTvLog.append("onSubscribe\n");
            }

            @Override
            public void onNext(Integer integer) {
                mTvLog.append("onNext 接收到:"+integer+"\n");
            }

            @Override
            public void onError(Throwable e) {
                mTvLog.append("onError\n");
            }

            @Override
            public void onComplete() {
                mTvLog.append("onComplete\n");
                Log.i(mActivity.getClass().getSimpleName(),mTvLog.getText().toString());
            }
        });
    }
}
