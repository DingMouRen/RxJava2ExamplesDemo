package com.dingmouren.examplesforandroid.ui.operators.operator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.dingmouren.examplesforandroid.base.BaseOperatorActivity;
import com.dingmouren.examplesforandroid.model.OperatorModel;

import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;

/**
 * @author dingmouren
 */
public class ScanOperatorActivity extends BaseOperatorActivity {

    public static final String KEY = "key";

    private OperatorModel mBean;

    public static void newInstance(Context context, OperatorModel bean){
        Intent intent = new Intent(context,ScanOperatorActivity.class);
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

        Observable.just(1,2,3,4)
                .scan(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer sum, Integer integer) throws Exception {
                        mTvLog.append("sacn integer1:"+sum+"  integer:"+integer+"\n");
                        return sum + integer;
                    }
                })
                .subscribe(new Observer<Integer>() {
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
