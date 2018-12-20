package com.dingmouren.examplesforandroid.ui.operators.operator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.dingmouren.examplesforandroid.base.BaseOperatorActivity;
import com.dingmouren.examplesforandroid.model.OperatorModel;

import java.io.Serializable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author dingmouren
 */
public class ConcatOperatorActivity extends BaseOperatorActivity {

    public static final String KEY = "key";

    private OperatorModel mBean;

    public static void newInstance(Context context, OperatorModel bean){
        Intent intent = new Intent(context,ConcatOperatorActivity.class);
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

        Observable.concat(Observable.just(1,2),Observable.just("3"))
        .subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                mTvLog.append("onSubscribe \n");
            }

            @Override
            public void onNext(Object object) {

                String result = "";
                if (object instanceof Integer){
                    result = (int) object + "";
                }else if (object instanceof String){
                    result = (String) object;
                }

                mTvLog.append("onNext object:"+result+"\n");
            }

            @Override
            public void onError(Throwable e) {
                mTvLog.append("onError \n");
            }

            @Override
            public void onComplete() {
                mTvLog.append("onCompelte\n");
                Log.e(mActivity.getClass().getSimpleName(),mTvLog.getText().toString());
            }
        });
    }
}
