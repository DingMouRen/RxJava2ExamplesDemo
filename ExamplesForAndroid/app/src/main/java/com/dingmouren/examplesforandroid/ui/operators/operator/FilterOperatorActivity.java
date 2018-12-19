package com.dingmouren.examplesforandroid.ui.operators.operator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.dingmouren.examplesforandroid.base.BaseOperatorActivity;
import com.dingmouren.examplesforandroid.model.OperatorModel;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;

/**
 * @author dingmouren
 */
public class FilterOperatorActivity extends BaseOperatorActivity {

    public static final String KEY = "key";

    private OperatorModel mBean;

    public static void newInstance(Context context, OperatorModel bean){
        Intent intent = new Intent(context,FilterOperatorActivity.class);
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

        Observable.just(1,2,3,4,5,6)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        mTvLog.append("filter  integer:"+integer+"\n");
                        return integer >3;
                    }
                }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                mTvLog.append("onSubscribe \n");
            }

            @Override
            public void onNext(Integer integer) {
                mTvLog.append("onNext integer:"+integer+"\n");
            }

            @Override
            public void onError(Throwable e) {
                mTvLog.append("onError \n");
            }

            @Override
            public void onComplete() {
                mTvLog.append("onComplete \n");
                Log.e(mActivity.getClass().getSimpleName(),mTvLog.getText().toString());
            }
        });
    }
}
