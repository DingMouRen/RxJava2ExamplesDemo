package com.dingmouren.examplesforandroid.ui.operators.operator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.dingmouren.examplesforandroid.base.BaseOperatorActivity;
import com.dingmouren.examplesforandroid.model.OperatorModel;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

/**
 * @author dingmouren
 */
public class PublishSubjectActivity extends BaseOperatorActivity {

    public static final String KEY = "key";

    private OperatorModel mBean;

    public static void newInstance(Context context, OperatorModel bean){
        Intent intent = new Intent(context,PublishSubjectActivity.class);
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

        PublishSubject<Integer> publishSubject = PublishSubject.create();

        //绑定第一个订阅者
        publishSubject.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                mTvLog.append("第1个订阅者  onSubscribe\n");
            }

            @Override
            public void onNext(Integer value) {
                mTvLog.append("第1个订阅者  onNext 接收到："+value+"\n");
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

        //发射数据
        publishSubject.onNext(1);
        publishSubject.onNext(2);

        mTvLog.append(" - - - - \n");

        //绑定第二个订阅者
        publishSubject.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                mTvLog.append("第2个订阅者  onSubscribe\n");
            }

            @Override
            public void onNext(Integer value) {
                mTvLog.append("第2个订阅者  onNext 接收到："+value+"\n");
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

        //发射数据
        publishSubject.onNext(3);
        publishSubject.onNext(4);
        publishSubject.onComplete();

        Log.e(mActivity.getClass().getSimpleName(),mTvLog.getText().toString());
    }
}
