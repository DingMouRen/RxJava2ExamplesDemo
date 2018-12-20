package com.dingmouren.examplesforandroid.ui.operators.operator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.dingmouren.examplesforandroid.base.BaseOperatorActivity;
import com.dingmouren.examplesforandroid.model.OperatorModel;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author dingmouren
 */
public class DeferOperatorActivity extends BaseOperatorActivity {

    public static final String KEY = "key";

    private OperatorModel mBean;

    public static void newInstance(Context context, OperatorModel bean){
        Intent intent = new Intent(context,DeferOperatorActivity.class);
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

        Observable<Integer> observableDefer = Observable.defer(new Callable<ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> call() throws Exception {
                mTvLog.append("defer\n");
                return Observable.just(1);
            }
        });
        Observer<Integer> observerDefer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                mTvLog.append("onSubscribe\n");
            }

            @Override
            public void onNext(Integer value) {
                mTvLog.append("onNext 接收到："+value+"\n");
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
        };

        observableDefer.subscribe(observerDefer);



    }

}
