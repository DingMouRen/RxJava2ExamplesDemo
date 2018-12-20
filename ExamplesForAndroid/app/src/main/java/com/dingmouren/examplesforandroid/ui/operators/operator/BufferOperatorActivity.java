package com.dingmouren.examplesforandroid.ui.operators.operator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.dingmouren.examplesforandroid.base.BaseOperatorActivity;
import com.dingmouren.examplesforandroid.model.OperatorModel;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author dingmouren
 */
public class BufferOperatorActivity extends BaseOperatorActivity {

    public static final String KEY = "key";

    private OperatorModel mBean;

    public static void newInstance(Context context, OperatorModel bean){
        Intent intent = new Intent(context,BufferOperatorActivity.class);
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

        Observable.just(1,2,3,4,5,6,7,8)
                .buffer(3,2)
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mTvLog.append("onSubscribe\n");
                    }

                    @Override
                    public void onNext(List<Integer> integers) {
                        mTvLog.append("onNext 集合："+ Arrays.toString(integers.toArray())+"\n");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mTvLog.append("onError \n");
                    }

                    @Override
                    public void onComplete() {
                        mTvLog.append("onComplete \n");
                        Log.i(mActivity.getClass().getSimpleName(),mTvLog.getText().toString());
                    }
                });
    }
}
