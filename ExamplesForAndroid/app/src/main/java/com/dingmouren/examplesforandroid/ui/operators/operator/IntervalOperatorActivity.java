package com.dingmouren.examplesforandroid.ui.operators.operator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.dingmouren.examplesforandroid.base.BaseOperatorActivity;
import com.dingmouren.examplesforandroid.model.OperatorModel;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author dingmouren
 */
public class IntervalOperatorActivity extends BaseOperatorActivity {

    public static final String KEY = "key";

    private OperatorModel mBean;

    private Disposable mDisposable;

    public static void newInstance(Context context, OperatorModel bean){
        Intent intent = new Intent(context,IntervalOperatorActivity.class);
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
        mTvLog.append("开始\n");
        mDisposable = Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) // 由于interval默认在新线程，所以我们应该切回主线程
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        mTvLog.append(new Date().toString() + " num="+aLong+"\n");
                       Log.e(mActivity.getClass().getSimpleName(),new Date().toString() + " num="+aLong);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) mDisposable.dispose();
    }
}
