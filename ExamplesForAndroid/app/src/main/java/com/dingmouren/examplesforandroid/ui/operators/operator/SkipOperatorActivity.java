package com.dingmouren.examplesforandroid.ui.operators.operator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.dingmouren.examplesforandroid.base.BaseOperatorActivity;
import com.dingmouren.examplesforandroid.model.OperatorModel;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author dingmouren
 */
public class SkipOperatorActivity extends BaseOperatorActivity {

    public static final String KEY = "key";

    private OperatorModel mBean;

    public static void newInstance(Context context, OperatorModel bean){
        Intent intent = new Intent(context,SkipOperatorActivity.class);
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

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {

                mTvLog.append("Observable 发射 1\n");
                emitter.onNext(1);

                mTvLog.append("Observable 发射 2\n");
                emitter.onNext(2);

                mTvLog.append("Observable 发射 3\n");
                emitter.onNext(3);

                emitter.onComplete();
            }
        }).skip(2)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mTvLog.append("onSubscribe\n");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        mTvLog.append("onNext  integer:"+integer+"\n");
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
