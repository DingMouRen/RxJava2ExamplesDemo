package com.dingmouren.examplesforandroid.ui.operators.operator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.dingmouren.examplesforandroid.base.BaseOperatorActivity;
import com.dingmouren.examplesforandroid.model.OperatorModel;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.disposables.Disposable;

/**
 * @author dingmouren
 */
public class CompletableOperatorActivity extends BaseOperatorActivity {

    public static final String KEY = "key";

    private OperatorModel mBean;

    public static void newInstance(Context context, OperatorModel bean){
        Intent intent = new Intent(context,CompletableOperatorActivity.class);
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

        Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {

                mTvLog.append("Completable 发射 1\n");
                emitter.onComplete();

            }
        }).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                mTvLog.append("onSubscribe \n");
            }

            @Override
            public void onComplete() {
                mTvLog.append("onComplete \n");
                Log.i(mActivity.getClass().getSimpleName(),mTvLog.getText().toString());
            }

            @Override
            public void onError(Throwable e) {
                mTvLog.append("onError \n");
            }
        });
    }
}
