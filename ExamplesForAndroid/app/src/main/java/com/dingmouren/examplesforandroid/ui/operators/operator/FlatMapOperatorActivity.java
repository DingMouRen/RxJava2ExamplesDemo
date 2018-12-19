package com.dingmouren.examplesforandroid.ui.operators.operator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dingmouren.examplesforandroid.base.BaseOperatorActivity;
import com.dingmouren.examplesforandroid.model.OperatorModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author dingmouren
 */
public class FlatMapOperatorActivity extends BaseOperatorActivity {

    public static final String KEY = "key";

    private OperatorModel mBean;

    public static void newInstance(Context context, OperatorModel bean) {
        Intent intent = new Intent(context, FlatMapOperatorActivity.class);
        intent.putExtra(KEY, bean);
        context.startActivity(intent);
    }

    @Override
    public void init() {
        if (getIntent() != null) {
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
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                mTvLog.append("Observable 发射 1 \n");
                e.onNext(1);

                mTvLog.append("Observable 发射 2 \n");
                e.onNext(2);

                mTvLog.append("Observable 发射 3 \n");
                e.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                mTvLog.append("flatmap  integer=" + integer + "\n");
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("value :" + integer + " - " + (i+1) );
                }
                int delayTime = (int) (1 + Math.random() * 10);
                return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mTvLog.append("Consumer accept:" + s + "\n");
                        Log.e(mActivity.getClass().getSimpleName(), mTvLog.getText().toString());
                    }
                });
    }

}
