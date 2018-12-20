package com.dingmouren.examplesforandroid.ui.operators.operator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.dingmouren.examplesforandroid.base.BaseOperatorActivity;
import com.dingmouren.examplesforandroid.model.OperatorModel;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.AsyncSubject;

/**
 * @author dingmouren
 */
public class AsyncSubjectActivity extends BaseOperatorActivity {

    public static final String KEY = "key";

    private OperatorModel mBean;

    public static void newInstance(Context context, OperatorModel bean){
        Intent intent = new Intent(context,AsyncSubjectActivity.class);
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

        AsyncSubject<Integer> asyncSubject = AsyncSubject.create();

        //第一个订阅者
        asyncSubject.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                mTvLog.append("第1个订阅者  onSubscirbe\n");
            }

            @Override
            public void onNext(Integer integer) {
                mTvLog.append("第1个订阅者  onNext  value:"+integer+"\n");
            }

            @Override
            public void onError(Throwable e) {
                mTvLog.append("第1个订阅者  onError\n");
            }

            @Override
            public void onComplete() {
                mTvLog.append("第1个订阅者  onComplete\n");
            }
        });

        asyncSubject.onNext(1);
        asyncSubject.onNext(2);
        asyncSubject.onComplete();

        mTvLog.append(" - - - \n");

        //第2个订阅者
        asyncSubject.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                mTvLog.append("第2个订阅者  onSubscirbe\n");
            }

            @Override
            public void onNext(Integer integer) {
                mTvLog.append("第2个订阅者  onNext  value:"+integer+"\n");
            }

            @Override
            public void onError(Throwable e) {
                mTvLog.append("第2个订阅者  onError\n");
            }

            @Override
            public void onComplete() {
                mTvLog.append("第2个订阅者  onComplete\n");
            }
        });

        asyncSubject.onNext(3);
        asyncSubject.onNext(4);
        asyncSubject.onComplete();

        Log.i(mActivity.getClass().getSimpleName(),mTvLog.getText().toString());

    }
}
