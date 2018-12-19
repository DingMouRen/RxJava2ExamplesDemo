package com.dingmouren.examplesforandroid.ui.operators.operator;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.dingmouren.examplesforandroid.base.BaseOperatorActivity;
import com.dingmouren.examplesforandroid.model.OperatorModel;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class JustOperatorActivity extends BaseOperatorActivity {

    public static final String KEY = "key";

    private OperatorModel mBean;

    public static void newInstance(Context context, OperatorModel bean){
        Intent intent = new Intent(context,JustOperatorActivity.class);
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
        Observable.just(1,2,3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    Disposable mDisposable;
                    @Override
                    public void onSubscribe(Disposable disposable) {
                        mDisposable = disposable;
                        mTvLog.append("onSubscribe   获取到Disposable实例\n");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        mTvLog.append("onNext -- "+integer+"\n");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mTvLog.append("onError\n");
                    }

                    @Override
                    public void onComplete() {
                        mTvLog.append("onComplete\n");
                        Log.e(mActivity.getClass().getSimpleName(),mTvLog.getText().toString());
                    }
                });

    }
}
