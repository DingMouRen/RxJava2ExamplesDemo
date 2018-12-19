package com.dingmouren.examplesforandroid.ui.operators.operator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.dingmouren.examplesforandroid.base.BaseOperatorActivity;
import com.dingmouren.examplesforandroid.model.OperatorModel;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.disposables.Disposable;

/**
 * @author dingmouren
 */
public class SingleActivity extends BaseOperatorActivity {

    public static final String KEY = "key";

    private OperatorModel mBean;

    public static void newInstance(Context context, OperatorModel bean){
        Intent intent = new Intent(context,SingleActivity.class);
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

        Single.create(new SingleOnSubscribe<Integer>() {
            @Override
            public void subscribe(SingleEmitter<Integer> emitter) throws Exception {
                mTvLog.append("Single 发射 1\n");
                emitter.onSuccess(1);//异常通知与成功通知之间只能发射一个
            }
        }).subscribe(new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                mTvLog.append("onSubscribe \n");
            }

            @Override
            public void onSuccess(Integer integer) {
                mTvLog.append("onSuccess integer:"+integer+"\n");
                Log.e(mActivity.getClass().getSimpleName(),mTvLog.getText().toString());
            }

            @Override
            public void onError(Throwable e) {
                mTvLog.append("onError "+e.getMessage()+"\n");
            }
        });

    }
}
