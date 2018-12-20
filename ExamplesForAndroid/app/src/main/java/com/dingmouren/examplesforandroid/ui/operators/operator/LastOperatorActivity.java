package com.dingmouren.examplesforandroid.ui.operators.operator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.dingmouren.examplesforandroid.base.BaseOperatorActivity;
import com.dingmouren.examplesforandroid.model.OperatorModel;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * @author dingmouren
 */
public class LastOperatorActivity extends BaseOperatorActivity {

    public static final String KEY = "key";

    private OperatorModel mBean;

    public static void newInstance(Context context, OperatorModel bean){
        Intent intent = new Intent(context,LastOperatorActivity.class);
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

        Observable.just(1,2,3,4,5,6).last(0)
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mTvLog.append("onSubscribe\n");
                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        mTvLog.append("onSuccess 成功接收:"+integer+"\n");
                        Log.i(mActivity.getClass().getSimpleName(),mTvLog.getText().toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        mTvLog.append("onError\n");
                    }
                });
    }
}
